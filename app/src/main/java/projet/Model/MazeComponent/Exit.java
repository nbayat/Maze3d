package Model.MazeComponent;

import javafx.scene.paint.*;

import javafx.scene.*;
import javafx.scene.shape.*;

public class Exit extends Cell {

    public Exit(int x, int y, int z) {
        super(x, y, z);
    }

    public Exit() {

    }

    public Group getView() {
        Group g = new Group();
        Box b = new Box(10, 10, 10);
        b.setMaterial(new PhongMaterial(Color.BLUE));
        g.getChildren().add(b);
        return g;
    }
}