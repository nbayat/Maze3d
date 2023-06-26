package Model;

import Controller.*;
import java.util.*;
import Model.Items.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.*;
import javafx.scene.shape.*;

public class Player {
    private int endurance;
    private ArrayList<Items> inventaire;
    private Vect pos;
    private double accel;
    private double maxSpeed;
    public PlayerControl command;
    public CameraControl commandCam;
    private double cameraAngle;
    private Vect vectSpeed;

    public Player() {
        endurance = 0;
        inventaire = null;
        accel = 2;
        maxSpeed = 20;
        vectSpeed = new Vect(0, 0, 0);
        pos = new Vect(0, 0, 0);
        command = new PlayerControl();
        commandCam = new CameraControl();
        cameraAngle = 0;

    }

    public void move(double deltaT) {

        switch (command.getState()) {
            case FORWARD:
                if ((int) Math.sqrt(vectSpeed.getCoordX() * vectSpeed.getCoordX()
                        + vectSpeed.getCoordZ() * vectSpeed.getCoordZ()) < maxSpeed) {
                    vectSpeed.setCoordZ(vectSpeed.getCoordZ() + (Math.cos(Math.toRadians(cameraAngle)) * accel));
                    vectSpeed.setCoordX(vectSpeed.getCoordX() + (Math.sin(Math.toRadians(cameraAngle)) * accel));
                }
                // pos.setCoordZ(pos.getCoordZ()+speed);
                break;
            case BACKWARD:
                if ((int) Math.sqrt(vectSpeed.getCoordX() * vectSpeed.getCoordX()
                        + vectSpeed.getCoordZ() * vectSpeed.getCoordZ()) < maxSpeed) {
                    vectSpeed.setCoordZ(vectSpeed.getCoordZ() - (Math.cos(Math.toRadians(cameraAngle)) * accel));
                    vectSpeed.setCoordX(vectSpeed.getCoordX() - (Math.sin(Math.toRadians(cameraAngle)) * accel));
                }
                // pos.setCoordZ(pos.getCoordZ()-speed);
                break;
            case RIGHT:
                if ((int) Math.sqrt(vectSpeed.getCoordX() * vectSpeed.getCoordX()
                        + vectSpeed.getCoordZ() * vectSpeed.getCoordZ()) < maxSpeed) {
                    vectSpeed.setCoordZ(vectSpeed.getCoordZ() - (Math.sin(Math.toRadians(cameraAngle)) * accel));
                    vectSpeed.setCoordX(vectSpeed.getCoordX() + (Math.cos(Math.toRadians(cameraAngle)) * accel));
                }
                // pos.setCoordX(pos.getCoordX()+speed);
                break;
            case LEFT:
                if ((int) Math.sqrt(vectSpeed.getCoordX() * vectSpeed.getCoordX()
                        + vectSpeed.getCoordZ() * vectSpeed.getCoordZ()) < maxSpeed) {
                    vectSpeed.setCoordZ(vectSpeed.getCoordZ() + (Math.sin(Math.toRadians(cameraAngle)) * accel));
                    vectSpeed.setCoordX(vectSpeed.getCoordX() - (Math.cos(Math.toRadians(cameraAngle)) * accel));
                }
                // pos.setCoordX(pos.getCoordX()-speed);
                break;
            case IDLE:
                vectSpeed.setCoordX(vectSpeed.getCoordX() / 2);
                vectSpeed.setCoordZ(vectSpeed.getCoordZ() / 2);

                break;
        }
        pos.setCoordZ(pos.getCoordZ() + vectSpeed.getCoordZ() * deltaT);
        pos.setCoordX(pos.getCoordX() + vectSpeed.getCoordX() * deltaT);
    }

    public double getX() {
        return pos.getCoordX();
    }

    public double getY() {
        return pos.getCoordY();
    }

    public double getZ() {
        return pos.getCoordZ();
    }

    public void setCoord(Vect c) {
        pos = c;
    }

    public Vect getSpeed() {
        return vectSpeed;
    }

    public Vect getCoord() {

        return pos;
    }

    public void setResetSpeed() {
        vectSpeed = new Vect(0, 0, 0);
    }

    public void setCameraAngle(double cameraAngle) {
        this.cameraAngle = cameraAngle;
    }

    public double getCameraAngle() {
        return cameraAngle;
    }

    public Group getView() {
        Group g = new Group();
        Rectangle b = new Rectangle(10, 5, Color.YELLOW);
        g.getChildren().add(b);
        return g;
    }
}
