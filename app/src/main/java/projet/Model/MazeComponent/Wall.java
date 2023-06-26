package Model.MazeComponent;

import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.*;
import javafx.scene.shape.*;

import Model.*;

public class Wall extends Cell {
    /**
     * Affichage console d'un Wall
     */

    public Wall(int x, int y, int z) {
        super(x, y, z);
    }

    public Wall() {

    }

    public Group getView() {
        Group g = new Group();
        Box b = new Box(10, 5, 10);
        b.setMaterial(new PhongMaterial(Color.RED));
        g.getChildren().add(b);
        return g;
    }

    @Override
    public void printCell() {
        System.out.print("");
    }

}
