package World.Persons;

import World.ConsoleColors;

public class Priest extends Player {
    public Priest(String name) {
        this.image = ConsoleColors.GREY_BACKGROUND + ConsoleColors.WHITE_BRIGHT + name + ConsoleColors.RESET;
        this.image += ConsoleColors.BEIGE_BACKGROUND + ConsoleColors.BLUE_BOLD + "**" + ConsoleColors.RESET;

        this.maxHP = this.hp = 99;
        this.armor = 30;
        this.damage = 3;
        this.name = name;
    }

    @Override
    public void superPower(Personage enemy) {
        this.hp = this.maxHP;
    }
}