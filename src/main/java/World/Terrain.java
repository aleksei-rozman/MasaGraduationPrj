package World;

import java.util.Random;

public class Terrain extends Spot {
    public Terrain(int x, int y){
        super(x,y);
        Random terraincolor = new Random();
        int colorcode = terraincolor.nextInt(2);
        switch (colorcode) {
            case 0:
                this.background = "\033[107m";
                break;
            default:
                this.background = "\033[100m";
        }
        this.image = this.background+"   "+ ConsoleColors.RESET;
    }
}
