package World.Persons;

import World.ConsoleColors;

public class Warrior extends Player {

    public Warrior(String name) {
        this.image = ConsoleColors.BLUE_BACKGROUND + ConsoleColors.WHITE_BRIGHT + name + ConsoleColors.RESET;
        this.image += ConsoleColors.BEIGE_BACKGROUND + ConsoleColors.BLUE_BOLD + "**" + ConsoleColors.RESET;
        this.maxHP = this.hp = 90;
        this.armor = 40;
        this.damage = 20;
        this.name = name;
    }

    @Override
    public void superPower(Personage enemy) {
        enemy.hp -= this.damage * 2;
    }
}