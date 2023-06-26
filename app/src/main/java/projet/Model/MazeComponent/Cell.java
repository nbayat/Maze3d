package Model.MazeComponent;

import javafx.scene.paint.*;
import javafx.scene.*;

import Model.Items.Items;
import javafx.scene.shape.*;
import Model.Coord;

public class Cell {
    private Coord c;
    private Items contenu;

    public Items getContenu() {
        return contenu;
    }

    public Cell() {
        c = new Coord(0, 0, 0);
    }

    public Cell(int x, int y, int z) {
        c = new Coord(x, y, z);
    }
    /* /////////////////////////////// */

    /**
     * Affichage console d'une Cell
     * 
     * @sigle: 3 espaces
     */
    public void printCell() {
        System.out.print("   ");
    }

    public Group getView() {
        Group g = new Group();
        Box b = new Box(10, 2, 50);
        b.setMaterial(new PhongMaterial(Color.GREEN));
        b.setDrawMode(DrawMode.FILL);
        b.setTranslateY(4);
        g.getChildren().add(b);

        return g;
    }

    public Coord getCoord() {
        return c;
    }

    public int getCoordX() {
        return c.getCoordX();
    }

    public int getCoordY() {
        return c.getCoordY();
    }

    public int getCoordZ() {
        return c.getCoordZ();
    }
}