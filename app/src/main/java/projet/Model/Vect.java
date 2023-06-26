package Model;

public class Vect {
    private double x;// droit gauche
    private double y;// haut bas
    private double z;// devant deriére

    public Vect(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getCoordX() {
        return x;
    }

    public double getCoordY() {
        return y;
    }

    public double getCoordZ() {
        return z;
    }

    public void setCoordX(double a) {
        this.x = a;
    }

    public void setCoordY(double a) {
        this.y = a;
    }

    public void setCoordZ(double a) {
        this.z = a;
    }

}