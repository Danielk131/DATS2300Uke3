package eksempelklasser;

import java.util.Arrays;

public enum Maned
{
    JAN("Januar", 1),
    FEB("Februar", 2),
    MAR("Mars", 3),
    APR("April", 4),
    MAI("Mai", 5),
    JUN("Juni", 6),
    JUL("Juli", 7),
    AUG("August", 8),
    SEP("September", 9),
    OKT("Oktober", 10),
    NOV("November", 11),
    DES("Desember", 12);

    private final String fulltnavn;      // instansvariabel
    private final int mndNr;

    private Maned(String fulltnavn, int mndNr) {
        this.fulltnavn = fulltnavn;
        this.mndNr = mndNr;
    }

    public String toString() {
        return fulltnavn;
    }

    public static String toString(int mnd)
    {
        if (mnd < 1 || mnd > 12) throw
                new IllegalArgumentException("Ulovlig måndesnummer!");

        return values()[mnd - 1].toString();
    }
    public int mndnr()
    { return mndNr; }


    public static Maned[] vår() {
        return Arrays.copyOfRange(values(),3,5);
}
    public static Maned[] sommer(){
        return Arrays.copyOfRange(values(), 5, 8);
    }
    public static Maned[] høst(){
        return Arrays.copyOfRange(values(), 8, 11);
    }
    public static Maned[] vinter(){
        return new Maned[]{DES, JAN, FEB, MAR};
    }

}