package Model;

import javafx.scene.*;
import javafx.stage.*;
import Model.*;
import Model.MazeComponent.*;
import javafx.scene.layout.*;
import javafx.application.*;
import javafx.scene.shape.*;
import javafx.scene.paint.*;
import javafx.scene.transform.*;
import javafx.animation.*;
import javafx.geometry.Point3D;

public class Game {
    Player player;
    Maze maze;
    double time = 100;

    public Game(Player p, Settings s) {
        player = p;
        maze = new Maze(s.getEtage(), s.getTaille());
        player.setCoord(new Vect(maze.getEnter().getCoordX() * 10, 0, maze.getEnter().getCoordZ() * 10));
    }

    public boolean hasWin() {
        Coord d = new Coord((int) player.getX() / 10, ((int) player.getY() / 10) + 1, (int) player.getZ() / 10);
        return d.equals(maze.getExit().getCoord());
    }

    public Cell[][][] getMaze() {
        return maze.getMaze();
    }

    public Player getPlayer() {
        return player;
    }

    public void Collision(double deltaT) {
        Vect newPos = new Vect((player.getCoord().getCoordX() + (player.getSpeed().getCoordX()) * deltaT) / 10,
                (player.getCoord().getCoordY() + (player.getSpeed().getCoordY()) * deltaT) / 10,
                (player.getCoord().getCoordZ() + (player.getSpeed().getCoordZ()) * deltaT) / 10);

        if (!(getMaze()[(int) (newPos.getCoordY())][((int) (newPos.getCoordZ() - 0.6))][((int) (newPos.getCoordX()
                - 0.6))] instanceof Wall)
                && !(getMaze()[(int) (newPos.getCoordY())][((int) (newPos.getCoordZ() - 0.1))][((int) (newPos
                        .getCoordX()
                        - 0.1))] instanceof Wall)
                && !(getMaze()[(int) (newPos.getCoordY())][((int) (newPos.getCoordZ() - 0.2))][((int) (newPos
                        .getCoordX()
                        - 0.2))] instanceof Wall)
                && !(getMaze()[(int) (newPos.getCoordY())][((int) (newPos
                        .getCoordZ()
                        - 0.3))][((int) (newPos.getCoordX() - 0.3))] instanceof Wall)
                && !(getMaze()[(int) (newPos
                        .getCoordY())][((int) (newPos.getCoordZ() - 0.4))][((int) (newPos.getCoordX()
                                - 0.4))] instanceof Wall)
                && !(getMaze()[(int) (newPos.getCoordY())][((int) (newPos
                        .getCoordZ()
                        - 0.5))][((int) (newPos.getCoordX() - 0.5))] instanceof Wall)) {

            player.move(deltaT);
        } else {
            player.setResetSpeed();
            player.move(-0.01 * deltaT);
        }
    }

    public void updateChrono(double deltaT) {
        time = time - deltaT;
        System.out.println(time);
    }

    public double getTime() {
        return this.time;
    }
}