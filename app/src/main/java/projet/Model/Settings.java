package Model;

public class Settings {

    private int taille;
    private int etage;

    private enum mdj {
        normal, survie
    };

    mdj a;

    private enum diff {
        facile, moyen, difficile, infernal
    };

    diff b;

    public mdj getMdj() {
        return a;
    }

    public diff getDiff() {
        return b;
    }

    public Settings(String a, String b, int taille, int etage) {
        if (b.equals("facile")) {
            this.b = diff.facile;
        }
        if (b.equals("moyen")) {
            this.b = diff.moyen;
        }
        if (b.equals("difficile")) {
            this.b = diff.difficile;
        }
        if (b.equals("infernal")) {
            this.b = diff.infernal;
        }

        if (a.equals("normal")) {
            this.a = mdj.normal;
        }
        if (b.equals("survie")) {
            this.a = mdj.survie;
        }
        this.taille = taille;
        this.etage = etage;
    }

    public int getEtage() {
        return this.etage;
    }

    public int getTaille() {
        return this.taille;
    }

}
