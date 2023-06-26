import javafx.scene.*;
import javafx.stage.*;

import Model.*;
import Model.MazeComponent.Cell;
import javafx.scene.layout.*;
import javafx.application.*;
import javafx.scene.shape.*;
import javafx.scene.paint.*;
import javafx.scene.transform.*;
import javafx.animation.*;
import javafx.geometry.Point3D;
import javafx.scene.control.Label;

public class View {
    PerspectiveCamera camera;
    Scene scene;
    Group root;
    private Game game;
    private Label time;

    public View(PerspectiveCamera c, Scene s, Group r, Game g) {
        camera = c;
        scene = s;
        root = r;
        game = g;
        time = new Label(String.valueOf(game.getTime()));
        root.getChildren().add(time);
    }

    public void start(Stage primaryStage) {
        scene.setFill(Color.ALICEBLUE);
        startG();
        scene.setCamera(camera);
        root.getChildren().add(game.getPlayer().getView());
        root.getChildren().add(camera);
        primaryStage.setScene(scene);
        primaryStage.show();
        animate();
    }

    public void startG() {
        for (int k = 0; k < game.getMaze().length; k++) {
            for (int i = 0; i < game.getMaze()[k].length; i++) {
                for (int j = 0; j < game.getMaze()[k][i].length; j++) {
                    Group b = game.getMaze()[k][i][j].getView();
                    b.setTranslateZ(game.getMaze()[k][i][j].getCoord().getCoordZ() * 10);// devant/deriere
                    b.setTranslateX(game.getMaze()[k][i][j].getCoord().getCoordX() * 10);// droite/gauche
                    b.setTranslateY(game.getMaze()[k][i][j].getCoord().getCoordY() * 10);// haut/bas
                    root.getChildren().add(b);
                }
            }
        }
    }

    public void animate() {
        new AnimationTimer() {
            long last = 0;

            @Override
            public void handle(long now) {
                if (last == 0) {
                    last = now;
                    return;
                }
                update((now - last) * 1.0e-9);
                last = now;
            }
        }.start();
    }

    public void update(double deltaT) {
        game.updateChrono(deltaT);
        updateChrono();
        if (!game.hasWin()) {
            game.Collision(deltaT);
            camera.setTranslateX(game.getPlayer().getX());
            camera.setTranslateY(game.getPlayer().getY());
            camera.setTranslateZ(game.getPlayer().getZ());
        } else {
            System.out.println("GG!!");
        }

    }

    public void updateChrono() {
        time.setText(String.valueOf((double) Math.round(game.getTime() * 100) / 100));
    }

    public boolean isColliding() {
        for (int i = 0; i < root.getChildren().size(); i++) {
            Node node = root.getChildren().get(i);
            if (node instanceof Group) {
                // if (node.getBoundsInParent().intersects(camera.getBoundsInParent())) {
                // return true;
                // }
                if (node.getBoundsInParent()
                        .intersects(game.getPlayer().getView().getBoundsInParent())) {
                    return true;
                }
            }
        }
        return false;
    }
}
