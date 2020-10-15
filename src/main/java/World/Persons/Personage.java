package World.Persons;


import World.Direction;

public abstract class Personage {
    public String id = "";
    String image;
    public int hp;
    int armor;
    public String name;
    int damage;
    public int x = - 1;
    public int y = - 1;
    boolean checkPower = true;
    int maxHP;

    Loot[] inventory = {
            new Loot(0, 10),
            new Loot(1, 10),
            null
    };

    public void setX(int x) {
        this.x = x;
        DBSQL.saveCharacter(id, this.x, this.y);
    }

    public void setY(int y) {
        this.y = y;
        DBSQL.saveCharacter(id, this.x, this.y);
    }

    public void move(Direction direction) {
        switch (direction) {
            case UP:
                setX(x-1);
                if (x < 0) x = 0;
                break;
            case DOWN:
                setX(x+1);
                if (x > 29) x = 29;
                break;
            case RIGHT:
                setY(y+1);
                if (y > 29) y = 29;
                break;
            case LEFT:
                setY(y-1);
                if (y < 0) y = 0;
                break;
        }
    }

    private static void doAction(Personage pers1, Personage pers2, int action) {
        if (pers1.hp <= pers1.maxHP * 0.2 && pers1.inventory[2] != null)
            pers1.hp += pers1.inventory[2].value;

        switch (action) {
            case 0:
                int dmg = pers1.damage + pers1.inventory[0].value;
                int armor = (pers2.armor + pers2.inventory[1].value);
                pers2.hp = pers2.hp - (dmg - dmg * armor / 100);
                break;
            case 1:
                pers1.superPower(pers2);
                break;
            default:
                System.out.println("Incorrect action");
        }
    }

    public static Personage fight(Personage pers1, Personage pers2) {
        if (pers1 == pers2) return pers1;
        int currentFighter = (int)(Math.random() * 2);
        boolean isDef = false;
        while (pers1.hp > 0 && pers2.hp > 0) {
            int action = (int)Math.random() * 3;
            if (action == 2 )
                isDef = true;
            else {
                if (currentFighter == 0 && !isDef) {
                    doAction(pers1, pers2, action);
                }
                if (currentFighter == 1 && !isDef) {
                    doAction(pers2, pers1, action);
                }
                isDef = false;
            }
            currentFighter = (currentFighter + 1) % 2;
        }
        Personage winner = pers1.hp > 0 ? pers1 : pers2;
        Personage loser = pers1.hp < 0 ? pers1 : pers2;

        if (winner.getClass().getName() != "Monster" && loser.getClass().getName() == "Monster") {
            Loot drop = ((Monster)loser).dropLoot();
            if (winner.inventory[drop.type].value < drop.value) winner.inventory[drop.type] = drop;
        }
        return winner;
    }

    @Override
    public String toString() {
        return image;
    }

    public abstract void superPower(Personage enemy);
}