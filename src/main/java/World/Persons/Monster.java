package World.Persons;

import World.ConsoleColors;

public class Monster extends Personage {
    public Monster() {
        this.name = "M";
        this.armor = (int)(Math.random() * 100);
        this.damage = (int)(Math.random() * 100);
    }

    public Monster(int x, int y) {
        super();
        this.x = x;
        this.y = y;
    }


    @Override
    public void superPower(Personage enemy) {
        if (checkPower) {
            int powerNumb = (int) (Math.random() * 3);
            switch (powerNumb) {
                case 0:
                    hp += 100;
                    break;
                case 1:
                    damage += damage / 10;
                    break;
                case 2:
                    armor += 100;
                    break;
            }
            checkPower = false;
        }
    }

    public Loot dropLoot() { return new Loot(); }

    @Override
    public String toString() {
        if (checkPower) {
            this.image = ""+ ConsoleColors.WHITE_BACKGROUND_BRIGHT+ConsoleColors.RED+"^"+
                    ConsoleColors.RED_BACKGROUND+ConsoleColors.YELLOW+"**"+ConsoleColors.RESET;
        }
        else {
            this.image = ""+ ConsoleColors.WHITE_BACKGROUND_BRIGHT+ConsoleColors.GREEN+"^"+
                    ConsoleColors.GREEN_BACKGROUND+ConsoleColors.RED+"**"+ConsoleColors.RESET;
        }
        return super.toString();
    }
}