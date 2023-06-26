package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SelectiveRand {
    private int min;
    private int max;
    private List<Integer> lint = new ArrayList<>();

    public SelectiveRand(int min, int max) {
        this.min = min;
        this.max = max;
        reset();

    }

    public void reset() {
        lint.clear();
        for (int i = min; i <= max; i++) {
            lint.add(i);
        }
    }

    public void exclude(Integer i) {
        lint.remove(i);
    }

    public int rand() {// gérer une exception ?
        if (lint.size() == 0) {
            return 0;
        }
        Random rand = new Random();
        return lint.remove(rand.nextInt(lint.size()));
    }

    public int size() {
        return lint.size();
    }

    public String toString() {
        String s = "";
        for (int i = 0; i < lint.size(); i++) {
            s += "[" + lint.get(i) + "]";
        }
        return s;
    }
    /*
     * public static void main(String[] args) {
     * SelectiveRand rand = new SelectiveRand(0,5);
     * System.out.println(rand);
     * rand.exclude(3);
     * System.out.println(rand);
     * rand.reset();
     * System.out.println(rand);
     * }
     */

}
