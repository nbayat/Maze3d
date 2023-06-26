import javafx.scene.*;
import javafx.stage.*;
import Model.*;
import Model.MazeComponent.Cell;
import Controller.*;
import javafx.scene.layout.*;
import javafx.application.*;
import javafx.scene.shape.*;
import javafx.scene.paint.*;
import javafx.scene.transform.*;
import javafx.animation.*;
import javafx.geometry.Point3D;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class Launch extends Application {

    int etage = 2;
    String difficulté = "facile";
    String ModeDeJeu = "normal";

    private double mousePosX, mousePosY = 0;
    private double mouseOldX, mouseOldY = 0;
    Slider taille = new Slider(20, 100, 60);

    public void play(Stage primaryStage) throws Exception {
        start(primaryStage);
    }

    public void start(Stage primaryStage) {
        Pane root = new Pane();
        var gameScene = new Scene(root, 250, 250);
        primaryStage.setScene(gameScene);
        primaryStage.show();
        Button a = new Button("Jouer");
        a.setStyle("-fx-background-color: #E63946");
        a.setLayoutX(75);
        a.setLayoutY(100);
        a.setMinSize(100, 20);

        Button b = new Button("Paramètres");
        b.setStyle("-fx-background-color: #E63946");
        b.setLayoutX(75);
        b.setLayoutY(150);
        b.setMinSize(100, 20);

        root.getChildren().addAll(a, b);

        a.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                startGame(primaryStage);
            }
        });

        b.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                startSettings(primaryStage);
            }
        });

    }

    public void startGame(Stage primaryStage) {
        PerspectiveCamera camera = new PerspectiveCamera(true);
        Group root = new Group();
        Scene scene = new Scene(root, 1440, 800, true);
        Player player = new Player();
        Settings s = new Settings(ModeDeJeu, difficulté, (int) taille.getValue(), etage);
        Game game = new Game(player, s);// aura en paramtre les parametre choisi par le joueur
        View v = new View(camera, scene, root, game);

        scene.setOnKeyPressed(ev -> {
            switch (ev.getCode()) {
                case RIGHT:
                    player.command.setState(PlayerControl.State.RIGHT);
                    // camera.getTransforms().add(new Translate(5,0,0));
                    break;
                case LEFT:
                    player.command.setState(PlayerControl.State.LEFT);
                    // camera.getTransforms().add(new Translate(-5,0,0));
                    break;
                case UP:
                    player.command.setState(PlayerControl.State.FORWARD);
                    // camera.getTransforms().add(new Translate(0,0,5));
                    break;
                case DOWN:
                    player.command.setState(PlayerControl.State.BACKWARD);
                    // camera.getTransforms().add(new Translate(0,0,-5));
                    break;
                case Q:
                    // player.commandCam.setState(CameraControl.StateCam.LEFT);
                    camera.getTransforms().add(new Rotate(-10, Rotate.Y_AXIS));
                    player.setCameraAngle((player.getCameraAngle() - 10) % 360);
                    break;
                case D:
                    // player.commandCam.setState(CameraControl.StateCam.RIGHT);
                    camera.getTransforms().add(new Rotate(10, Rotate.Y_AXIS));
                    player.setCameraAngle((player.getCameraAngle() + 10) % 360);
                    break;
                case S:
                    camera.getTransforms().add(new Translate(0, -5, 0));
                    break;
                case P:
                    camera.getTransforms().add(new Translate(0, 5, 0));
                    break;
                case O:
                    camera.getTransforms().add(new Rotate(-10, Rotate.X_AXIS));
                    break;

            }

        });

        scene.setOnKeyReleased(ev -> {
            switch (ev.getCode()) {
                case RIGHT:
                    player.command.setState(PlayerControl.State.IDLE);
                    // camera.getTransforms().add(new Translate(5,0,0));
                    break;
                case LEFT:
                    player.command.setState(PlayerControl.State.IDLE);
                    // camera.getTransforms().add(new Translate(-5,0,0));
                    break;
                case UP:
                    player.command.setState(PlayerControl.State.IDLE);
                    // camera.getTransforms().add(new Translate(0,0,5));
                    break;
                case DOWN:
                    player.command.setState(PlayerControl.State.IDLE);
                    // camera.getTransforms().add(new Translate(0,0,-5));
                    break;
                // case Q:
                // player.commandCam.state=CameraControl.State.IDLE;
                // //camera.getTransforms().add(new Rotate(-10,Rotate.Y_AXIS));
                // break;
                // case D:
                // player.commandCam.state=CameraControl.State.IDLE;
                // //camera.getTransforms().add(new Rotate(10,Rotate.Y_AXIS));
                // break;

            }
        });

        scene.setOnMousePressed(me -> {
            mousePosX = me.getSceneX();
            mousePosY = me.getSceneY();
            mouseOldX = me.getSceneX();
            mouseOldY = me.getSceneY();
        });

        scene.setOnMouseDragged(me -> {
            mouseOldX = mousePosX;
            mouseOldY = mousePosY;
            mousePosX = me.getSceneX();
            mousePosY = me.getSceneY();
            double mouseDeltaX = (mousePosX - mouseOldX) / scene.getWidth();
            double mouseDeltaY = (mousePosY - mouseOldY) / scene.getHeight();
            camera.getTransforms().add(new Rotate(-mouseDeltaX * 180, Rotate.Y_AXIS));
            player.setCameraAngle((player.getCameraAngle() - mouseDeltaX * 180));

        });

        v.start(primaryStage);
    }

    public void startSettings(Stage primaryStage) {
        Pane root = new Pane();
        var gameScene = new Scene(root, 500, 500);
        primaryStage.setScene(gameScene);
        primaryStage.show();

        Label o = new Label("Mode de Jeu :");
        o.setLayoutX(40);
        o.setLayoutY(30);

        Button n = new Button("Normal");
        n.setLayoutX(40);
        n.setLayoutY(50);
        n.setMinSize(50, 20);

        n.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                ModeDeJeu = "normal";
            }
        });

        Button p = new Button("Survie");
        p.setLayoutX(115);
        p.setLayoutY(50);
        p.setMinSize(50, 20);

        p.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                ModeDeJeu = "survie";
            }
        });

        Label a = new Label("Présence de bonus :");
        a.setLayoutX(40);
        a.setLayoutY(80);

        Button b = new Button("Oui");
        b.setLayoutX(40);
        b.setLayoutY(100);
        b.setMinSize(50, 20);

        b.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {

            }
        });

        Button c = new Button("Non");
        c.setLayoutX(95);
        c.setLayoutY(100);
        c.setMinSize(50, 20);

        c.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {

            }
        });

        Label d = new Label("Taille du labyrinthe :");
        d.setLayoutX(40);
        d.setLayoutY(150);

        taille.setLayoutX(200);
        taille.setLayoutY(150);
        taille.setShowTickLabels(true);

        Label e = new Label("Nombre d'étages :");
        e.setLayoutX(40);
        e.setLayoutY(180);

        Button e1 = new Button("1");
        e1.setLayoutX(40);
        e1.setLayoutY(205);
        e1.setMinSize(10, 10);

        e1.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                etage = 1;
            }
        });

        Button e2 = new Button("2");
        e2.setLayoutX(70);
        e2.setLayoutY(205);
        e2.setMinSize(10, 10);

        e2.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                etage = 2;
            }
        });

        Button e3 = new Button("3");
        e3.setLayoutX(100);
        e3.setLayoutY(205);
        e3.setMinSize(10, 10);

        e3.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                etage = 3;
            }
        });

        Button e4 = new Button("4");
        e4.setLayoutX(130);
        e4.setLayoutY(205);
        e4.setMinSize(10, 10);

        e4.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                etage = 4;
            }
        });

        Button e5 = new Button("5");
        e5.setLayoutX(160);
        e5.setLayoutY(205);
        e5.setMinSize(10, 10);

        e5.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                etage = 5;
            }
        });

        Label f = new Label("Difficulté :");
        f.setLayoutX(40);
        f.setLayoutY(245);

        Button g = new Button("Facile");
        g.setLayoutX(40);
        g.setLayoutY(265);
        g.setMinSize(50, 20);

        g.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                difficulté = "facile";
            }
        });

        Button h = new Button("Moyen");
        h.setLayoutX(100);
        h.setLayoutY(265);
        h.setMinSize(50, 20);

        h.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                difficulté = "moyen";
            }
        });

        Button i = new Button("Difficile");
        i.setLayoutX(165);
        i.setLayoutY(265);
        i.setMinSize(50, 20);

        i.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                difficulté = "difficile";
            }
        });

        Button j = new Button("Infernal");
        j.setLayoutX(235);
        j.setLayoutY(265);
        j.setMinSize(50, 20);

        i.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                difficulté = "infernal";
            }
        });

        Button z = new Button("Retour");
        z.setStyle("-fx-background-color: #E63946");
        z.setLayoutX(410);
        z.setLayoutY(450);
        z.setMinSize(50, 20);

        z.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                start(primaryStage);
            }
        });

        root.getChildren().addAll(a, b, c, d, e, e1, e2, e3, e4, e5, f, g, h, i, j, z, taille, n, o, p);

    }

    public static void main(String[] args) {
        launch(args);
    }
}
