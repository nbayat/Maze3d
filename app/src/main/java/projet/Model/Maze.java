package Model;

import Model.Items.Key;
import Model.MazeComponent.*;
import Model.*;

import java.awt.*; //import de Point
import java.util.*;
import java.util.List;

public class Maze {
    // La structure actuelle permet au différent labyrinthe d'être empilé
    // Le labyrinthe à une taille fixe (pour l'instant)
    // Tous les labyrinthes ont la même taille
    // private static int mazeCount = 0;
    private int floor;
    private int nbFloor;
    private MazeComponentGenerator[][] maze;
    public Cell[][][] cellMaze;
    private final int size;
    private Enter enter;
    private Exit exit;

    public Maze(int nbF, int s) {
        this.nbFloor = nbF;
        size = s;
        maze = new MazeComponentGenerator[size][size];
        floor = 0;
        cellMaze = new Cell[nbF][2 * s + 1][2 * s + 1];
        for (int i = 0; i < nbF; i++) {
            init();
            generateMaze();
            floor++;
        }
    }

    private void init() {
        MazeComponentGenerator mC;
        Point point;
        // Par défaut la taille sera de 20 par 20
        for (int ix = 0; ix < size; ix++) {
            for (int iy = 0; iy < size; iy++) {
                point = new Point(ix, iy);
                mC = new MazeComponentGenerator(point);
                maze[ix][iy] = mC;
            }
        }
    }

    /**
     * <p>
     * Version simple, la sortie et l'entrée sera placée au hasard
     * Cette fonction permet de générer un tableau de MazeComponent
     * <p>
     * ref du model :
     *
     */

    private void generate() {
        Random random = new Random();
        int[] cell;
        int i = 1;
        MazeComponentGenerator temp;
        MazeComponentGenerator mC;
        long startTime = System.nanoTime();
        while (!conditionNo1()) {
            cell = selectRandomValidWall();
            mC = maze[cell[0]][cell[1]];
            temp = checkCellNextTo(cell);

            if (!mC.isThereAWall(cell[2]) || temp.getCenter() == mC.getCenter()) {
                cell = higherValueCell();
                mC = maze[cell[0]][cell[1]];
            }

            destroyWall(mC, cell[2]);
            changeCenter();
            // System.out.println(i +"éme itération");
            i++;
        }
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);

    }
    // Tous les tableaux "cell" sont de formats suivants :
    // [position x][position y][numéro du mur]

    /**
     * Cette fonction supprime les murs entre deux cases de maze<br/>
     * 
     * @param mC1           La case de départ
     * @param wallToDestroy Le mur que l'on veut enlever depuis la case de départ.
     *                      <br/>
     *                      Notez que la f° fait deux suppressions de mur à chaque
     *                      appel
     */
    private void destroyWall(MazeComponentGenerator mC1, int wallToDestroy /* relatif à mC1 */) {
        // Condition pour ne pas ouvrir les bords du labyrinthe
        MazeComponentGenerator temp = mazeComponentNextTo(mC1, wallToDestroy);
        if (mC1.equals(temp) || mC1.getCenter() == temp.getCenter()) {
            // DoNothing
        } else {
            mC1.setWall(wallToDestroy, false);
            Point p = cellNextTo(mC1, wallToDestroy);
            MazeComponentGenerator mC2 = maze[p.x][p.y];
            mC2.setWall(oppositeWall(wallToDestroy), false);
        }
    }

    private int oppositeWall(int i) {// prends un chiffre entre 0 et 3 et renvoi son "opposé"
        switch (i) {
            case 0:
                return 1;
            case 1:
                return 0;
            case 2:
                return 3;
            case 3:
                return 2;
        }
        return -1;
    }

    private Point cellNextTo(MazeComponentGenerator mC, int wall) {// Permet de donner les coordonnées de la cellule
                                                                   // adjacente à mC en fonction du mur
        int x = 0, y = 0;
        switch (wall) {
            case 0:
                y = -1;
                break;
            case 1:
                y = 1;
                break;
            case 2:
                x = -1;
                break;
            case 3:
                x = 1;
                break;
        }
        if (mC.getPosition().x + x >= size || mC.getPosition().y + y >= size ||
                mC.getPosition().y + y < 0 || mC.getPosition().x + x < 0) {
            return new Point(mC.getPosition().x, mC.getPosition().y);
        } else {
            return new Point(mC.getPosition().x + x, mC.getPosition().y + y);
        }
    }

    private MazeComponentGenerator mazeComponentNextTo(MazeComponentGenerator mC, int wall) {// Permet de donner les
                                                                                             // coordonnées de la
                                                                                             // cellule adjacente à mC
                                                                                             // en fonction du mur
        int x = 0, y = 0;
        switch (wall) {
            case 0:
                y = -1;
                break;
            case 1:
                y = 1;
                break;
            case 2:
                x = -1;
                break;
            case 3:
                x = 1;
                break;
        }
        if (mC.getPosition().x + x >= size || mC.getPosition().y + y >= size ||
                mC.getPosition().y + y < 0 || mC.getPosition().x + x < 0) {
            return mC;
        } else {
            return maze[mC.getPosition().x + x][mC.getPosition().y + y];
        }
    }

    private MazeComponentGenerator cell(Point p) {
        return maze[p.x][p.y];
    }

    private MazeComponentGenerator cell(int x, int y) {
        return maze[x][y];
    }

    private int[] selectRandomValidWall() {// donne un point sur maze et la position d'un mur valide
        int cellPositionX = 0, cellPositionY = 0, wall = 0;
        List list = new LinkedList() {
            {
                add(0);
                add(1);
                add(2);
                add(3);
            }
        };
        Random random = new Random();
        cellPositionX = random.nextInt(size - 1);
        cellPositionY = random.nextInt(size - 1);
        list = validWall(cellPositionX + 1, cellPositionY + 1, list);

        wall = wallPossibility(list);
        return new int[] { cellPositionX, cellPositionY, wall };
    }

    private int wallPossibility(List<Integer> list) {
        Random random = new Random();
        int temp = random.nextInt(list.size() - 1);
        return list.get(temp);
    }

    /**
     * Une condition qui vérifie si toutes les cases du tableau (maze) ont la même
     * valeur (de centre)
     * 
     * @return true si la condition est verifiée false sinon.
     */
    private boolean conditionNo1() {
        int baseNumber = maze[0][0].getCenter();
        for (int ix = 0; ix < size; ix++) {
            for (int iy = 0; iy < size; iy++) {
                if (baseNumber != maze[ix][iy].getCenter()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Cette fonction permet de trouver dans maze le MazeComponentGenerator avec la
     * plus haute valeur de centre.
     * 
     * @Usage: Elle est utilisée dans le cas ou on a pas pu trouver un candidat
     *         aléatoire à la suppresion de mur,
     *         dans ce cas on choisit une des cases avec la plus haute valeur.
     * @return La fonction retourne un tableau de taille 3 contenant les
     *         informations suivantes
     *         [position x][position y][position mur] la position du mur est
     *         representé par un entier entre 0 et 3.
     */
    private int[] higherValueCell() {
        int[] cell = new int[3];
        MazeComponentGenerator mC = maze[0][0];
        for (int ix = 0; ix < size; ix++) {
            for (int iy = 0; iy < size; iy++) {
                if (maze[ix][iy].getCenter() > mC.getCenter()) {
                    mC = maze[ix][iy];
                }
            }
        }
        cell[0] = mC.getPosition().x;
        cell[1] = mC.getPosition().y;

        List list = new LinkedList() {
            {
                add(0);
                add(1);
                add(2);
                add(3);
            }
        };
        Random random = new Random();
        int wallPossibility = random.nextInt(list.size() - 1);
        cell[2] = (int) list.get(wallPossibility);
        return cell;
    }

    private List validWall(int cellPositionX, int cellPositionY, List list) {
        // renvoie une liste avec la position d'une cellule et le mur que l'on peut
        // enelever
        Integer i = 2;
        if (cellPositionY == 0) {
            list.remove(i);
        }
        i = 3;
        if (cellPositionY == size) {
            list.remove(i);
        }
        i = 0;
        if (cellPositionX == 0) {
            list.remove(i);
        }
        i = 1;
        if (cellPositionX == size) {
            list.remove(i);
        }
        return list;
    }

    /**
     * Cette fonction permet de trouver le MazeComponentGenerator adjacent au MCG
     * donné dans cell,
     * on donne également un mur pour savoir dans quelle direction on regarde.
     * 
     * @param cell La fonction prend un tableau de taille 3 contenant les
     *             informations suivantes
     *             [position x][position y][position mur] la position du mur est
     *             representé par un entier entre 0 et 3.
     * @return La fonction renvoie le MazeComponentGenerator adjacent au MCG décrit
     *         dans cell
     */
    private MazeComponentGenerator checkCellNextTo(int[] cell) {
        MazeComponentGenerator temp = cell(cell[0], cell[1]);
        MazeComponentGenerator mCNext = cell(cellNextTo(temp, cell[2]));
        if ((cell[0] == 0) && (cell[2] == 2) || (cell[2] == 3) && (cell[0] == size - 1) ||
                (cell[1] == 0) && (cell[2] == 0) || (cell[2] == 1) && (cell[1] == size - 1)) {// condition au bord
            return temp;
            /// *///
        } else {
            temp = mazeComponentNextTo(maze[cell[0]][cell[1]], cell[2]);
            return temp;
        }
    }

    /**
     * Cette fonction change le centre de toutes les cellules qui ne sont pas separé
     * par des murs. La valeur choisit et celle du plus petit entier du groupe de
     * cellule
     */
    private void changeCenter() {
        // changer la valeur des centres des cases de façon à ce que les groupes de case
        // liés aient tous la plus petite
        // valeur centrale possible
        boolean noChange = true;
        MazeComponentGenerator mC;
        MazeComponentGenerator temp;
        while (noChange) {
            noChange = false;
            for (int ix = 0; ix < size; ix++) {
                for (int iy = 0; iy < size; iy++) {
                    mC = maze[ix][iy];
                    for (int i = 1; i < 4; i += 2) {
                        temp = mazeComponentNextTo(mC, i);
                        if (temp != null && !mC.isThereAWall(i) && temp.getCenter() != mC.getCenter()) {
                            mC.setCenter(Math.min(temp.getCenter(), mC.getCenter()));
                            temp.setCenter(Math.min(temp.getCenter(), mC.getCenter()));

                        }
                    }
                }
            }
        }
    }

    /**
     * Converti le tableau de MazeComponentGenerator en tableau de Cell
     * 
     * @attention: Le tableau de Cell généré sera de taille 2n+1 avec n la taille du
     *             tableau de MazeComponentGenerator
     */
    private Cell[][][] convert() {
        int tempIx;
        int tempIy;
        for (int ix = 0; ix < size; ix++) {
            for (int iy = 0; iy < size; iy++) {
                // on parcourt plusieurs fois les mêmes cellules !!
                for (int iix = -1; iix < 2; iix++) {
                    for (int iiy = -1; iiy < 2; iiy++) {
                        cellMaze[floor][2 * ix + 1 + iix][2 * iy + 1 + iiy] = maze[ix][iy].convertToMazeComponent(ix,
                                iy, iix + 1, iiy + 1, floor);
                    }
                }
            }
            setupEntryAndExit(1, 1, cellMaze[nbFloor - 1].length - 2, cellMaze[nbFloor - 1].length - 2);
        }
        return cellMaze;
    }

    /**
     * Cette fonction permet de placer deux routes, une entrée et une sortie
     * 
     * @param x1 cordonnée en x de l'entrée
     * @param y1 cordonnée en y de l'entrée
     * @param x2 cordonnée en x de la sortie
     * @param y2 cordonnée en y de la sortie
     */
    public void setupEntryAndExit(int x1, int y1, int x2, int y2) {
        cellMaze[0][x1][y1] = new Enter(y1 + 1, 0, x1 + 1);
        cellMaze[cellMaze.length - 1][x2][y2] = new Exit(y2 + 1, cellMaze.length - 1, x2 + 1);
        enter = (Enter) cellMaze[0][x1][y1];
        exit = (Exit) cellMaze[cellMaze.length - 1][x2][y2];

    }

    public Cell[][][] generateMaze() {
        generate();
        return convert();
    }

    public void Test() {
        MazeComponentGenerator mC = checkCellNextTo(new int[] { 1, 0, 0 });
        System.out.println();
        System.out.println(mC);
        destroyWall(mC, 1);
        printMaze();
        destroyWall(mC, 3);
        printMaze();
        changeCenter();
        printMaze();
        destroyWall(maze[1][0], 2);
        printMaze();
    }

    ///////// Opération sur le CellMaze/////////
    public boolean resolveMaze() {
        return false;
    }

    ///////// affichage console/////////
    public void printMaze() {
        for (int iy = 0; iy < size; iy++) {
            for (int rep = 0; rep < 2; rep++) {
                for (int ix = 0; ix < size; ix++) {
                    switch (rep) {
                        case 0:
                            maze[ix][iy].printUpperWall();
                            break;
                        case 1:
                            maze[ix][iy].printMiddle();
                    }
                }
                System.out.println();
            }
            if (iy == size - 1) {
                for (int ix = 0; ix < size; ix++) {
                    maze[ix][iy].printLowerWall();
                }
            }
        }
        System.out.println();
    }

    public void printCellMaze(Cell[][] cellMaze) {
        int size = cellMaze[0].length;
        for (int iy = 0; iy < size; iy++) {
            for (int ix = 0; ix < size; ix++) {
                cellMaze[ix][iy].printCell();
                if (ix % 3 == 0) {
                    // System.out.print(" ");
                }
            }
            if (iy % 3 == 0) {
                // System.out.println();
            }
            System.out.println();
        }
    }

    public void traveledMaze(List<Cell> cellList) {
        int size = cellMaze[0].length;
        Wall wall = new Wall();
        boolean done = false;
        for (int iy = 0; iy < size; iy++) {
            for (int ix = 0; ix < size; ix++) {

                for (int i = 0; i < cellList.size(); i++) {
                    if (cellList.get(i).equals(cellMaze[ix][iy])) {
                        // cellMaze[ix][iy].printCell();
                        done = true;
                        break;
                    }
                }
                if (!done) {
                    wall.printCell();
                }
                done = false;

            }
            System.out.println();
        }
    }

    public Enter getEnter() {
        return enter;
    }

    public Exit getExit() {
        return exit;
    }

    public Cell[][][] getMaze() {
        return cellMaze;
    }
}