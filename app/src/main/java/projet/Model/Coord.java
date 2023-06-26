package Model;

public class Coord {
    private int x;// droit gauche
    private int y;// haut bas
    private int z;// devant deriére

    public Coord(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public boolean equals(Coord c) {
        return this.x == c.x && this.y == c.y && this.z == c.z;
    }

    public int getCoordX() {
        return x;
    }

    public int getCoordY() {
        return y;
    }

    public int getCoordZ() {
        return z;
    }

    public void setCoordX(int a) {
        this.x = a;
    }

    public void setCoordY(int a) {
        this.y = a;
    }

    public void setCoordZ(int a) {
        this.z = a;
    }

}