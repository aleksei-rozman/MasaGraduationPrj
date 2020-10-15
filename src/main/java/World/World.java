package World;

import World.Persons.Monster;
import World.Persons.Personage;

public class World {
    Spot [][] field;
    Personage[][] personages;
    int width = 30;
    int height = 30;
    volatile int countPersons = 10;
    public World () {
        this.field = new Spot[width][height];
        this.personages = new Personage[width][height];
        for(int i = 0; i< field.length;i++) {
            for (int j = 0; j < field[i].length; j++) {
                field[i][j] = new Terrain(j, i);
            }
        }
        for (int i = 0; i < 5; i++) {
            addPersonage(new Monster());
        }
    }

    public synchronized void addPersonage(Personage personage) {
        countPersons--;
        if (personage.x == -1 || personage.x == -1) {
            int x = (int) ( Math.random() * 30 );
            int y = (int) ( Math.random() * 30 );
            personage.x = x;
            personage.y = y;
        }
        if (personages[personage.y][personage.x] == null) {
            personages[personage.y][personage.x] = personage;
        } else {
            personages [personage.y][personage.x] = Personage.fight(personages[personage.y][personage.x], personage);
        }
    }

    public void showMap() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (personages[i][j] == null){
                    System.out.print(field[i][j].image);
                } else {
                    System.out.print(personages[i][j].toString());
                }
            }
            System.out.println();
        }
    }

    public String getMap() {
        String toclient ="";
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                if (personages[i][j] == null){
                    toclient+= field[i][j].image;
                } else {
                    toclient+= personages[i][j].toString();
                }
            }
            toclient += "\n";
        }
        return toclient;
    }

    public synchronized void delete(Personage mover) {
        personages[mover.y][mover.x] = null;
    }


    public synchronized void move(Personage mover, Direction direction){
        if (mover.hp <= 0) return;
        personages[mover.y][mover.x] = null;
        mover.move(direction);
        if (personages[mover.y][mover.x] != null) {
            personages[mover.y][mover.x] = Personage.fight(personages[mover.y][mover.x], mover);
        } else {
            personages[mover.y][mover.x] = mover;
        }
    }
}
