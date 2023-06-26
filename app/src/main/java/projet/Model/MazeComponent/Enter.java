package Model.MazeComponent;

import javafx.scene.paint.*;
import javafx.scene.*;
import javafx.scene.shape.*;

public class Enter extends Cell {

    public Enter(int x, int y, int z) {
        super(x, y, z);
    }

    public Enter() {

    }

    public Group getView() {
        Group g = new Group();
        Box b = new Box(10, 2, 10);
        b.setMaterial(new PhongMaterial(Color.PINK));
        g.getChildren().add(super.getView());
        g.getChildren().add(b);
        return g;
    }
}