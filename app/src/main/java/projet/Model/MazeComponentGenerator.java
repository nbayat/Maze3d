package Model;

import Model.MazeComponent.Cell;
import Model.MazeComponent.Wall;
import Model.Player;

import java.awt.*;

public class MazeComponentGenerator {
    // représente une case avec 4 côtés et 1 centre
    private Point position;// position dans le labyrinthe
    private int center;
    private boolean[] side = { true, true, true, true };// {haut,bas,gauche,droite} true s'il y a un mur
    private static int mazeComponentBoxCount = 0;

    public MazeComponentGenerator(Point position) {
        this.position = position;
        center = mazeComponentBoxCount;
        mazeComponentBoxCount++;
    }

    public MazeComponentGenerator(int x, int y) {
        Point p = new Point(x, y);
        position = p;
        center = mazeComponentBoxCount;
        mazeComponentBoxCount++;
    }

    public boolean isThereAWall(int i) {
        return side[i];
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public int getCenter() {
        return center;
    }

    public void setCenter(int i) {
        center = i;
    }

    public void setWall(String s, boolean b) {
        switch (s) {
            case "Up":
                side[0] = b;
            case "Down":
                side[1] = b;
            case "Left":
                side[2] = b;
            case "Right":
                side[3] = b;
        }
    }

    public void setWall(int s, boolean b) {
        if (s >= 0 && s < 4) {
            side[s] = b;
        }
    }

    public Cell convertToMazeComponent(int a, int b, int x, int y, int floor) {
        Wall wall = new Wall(2 * b + 1 + y, floor, 2 * a + 1 + x);
        Cell cell = new Cell(2 * b + 1 + y, floor, 2 * a + 1 + x);
        boolean test = true;
        if (x == 1 && y == 1) {
            return cell;
        }
        if (y == 0 && x == 1) {
            test = isThereAWall(0);
        } else if (y == 1 && x == 0) {
            test = isThereAWall(2);
        } else if (y == 2 && x == 0) {
            test = isThereAWall(1);
        } else if (y == 0 && x == 2) {
            test = isThereAWall(3);
        }
        if (test) {
            return wall;
        } else {
            return cell;
        }
    }

    public String toString() {
        return ("la case de centre " + center + " de position (" + position.x + "," + position.y + ")");
    }

    ////////////////// affichage console//////////////////
    public void printBox() {
        // printUpperWall();
        // System.out.println();
        // printLeftWall();
        // System.out.print(" ");
        // printRightWall();
        // System.out.println();
        // printLowerWall();
    }

    public void printUpperWall() {
        System.out.print("+");
        if (side[0]) {
            System.out.print("-----");
        } else {
            System.out.print("     ");
        }
        System.out.print("+");
    }

    public void printLeftWall() {
        if (side[2]) {
            System.out.print("| ");
        } else {
            System.out.print("  ");
        }
    }

    public void printLowerWall() {
        System.out.print("+");
        if (side[1]) {
            System.out.print("-----");
        } else {
            System.out.print("     ");
        }
        System.out.print("+");
    }

    public void printRightWall() {
        if (side[3]) {
            System.out.print(" |");
        } else {
            System.out.print("  ");
        }
    }

    public void printMiddle() {
        printLeftWall();
        if (center < 10) {
            System.out.print(" " + center + " ");
        } else if (center < 100) {
            System.out.print(center + " ");
        } else {
            System.out.print(center);
        }
        printRightWall();
    }

    public boolean equals(MazeComponentGenerator MC1) {
        return (center == MC1.center && position.x == MC1.position.x && position.y == MC1.position.y);
    }

    public static boolean verifyCollision(Wall wall, Player player) {
        if (wall.getCoord().getCoordX() >= player.getCoord().getCoordX() - 100
                || wall.getCoord().getCoordX() < player.getCoord().getCoordX() + 100)
            return true;
        if (wall.getCoord().getCoordY() >= player.getCoord().getCoordY() - 100
                || wall.getCoord().getCoordY() < player.getCoord().getCoordY() + 100)
            return true;
        if (wall.getCoord().getCoordZ() >= player.getCoord().getCoordZ() - 100
                || wall.getCoord().getCoordZ() < player.getCoord().getCoordZ() + 100)
            return true;

        return false;
    }

}