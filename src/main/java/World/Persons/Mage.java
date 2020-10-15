package World.Persons;

import World.ConsoleColors;

public class Mage extends Player {
    public Mage(String name) {
        this.image = ConsoleColors.PINK_BACKGROUND_BRIGHT + ConsoleColors.WHITE_BRIGHT + name + ConsoleColors.RESET;
        this.image += ConsoleColors.BEIGE_BACKGROUND + ConsoleColors.BLUE_BOLD + "**" + ConsoleColors.RESET;
        this.maxHP = this.hp = 80;
        this.armor = 20;
        this.damage = 30;
        this.name = name;
    }

    @Override
    public void superPower(Personage enemy) {
        enemy.hp -= this.damage;
        this.hp += this.damage;
        if (this.hp > this.maxHP)
            this.hp = this.maxHP;
    }
}
