package World.Persons;

public class Loot {
    int value;
    int type;

    public Loot() {
        this.type = (int) (Math.random() * 3);
        this.value = (int) (Math.random() * 50);
    }

    public Loot(int type, int value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {
        return type + " - " + value;
    }
}