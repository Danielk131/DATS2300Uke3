package eksempelklasser;

import hjelpeklasser.Kø;
import hjelpeklasser.Stakk;

import java.util.Comparator;
import java.util.NoSuchElementException;

public class TabellKø<T> implements Kø<T>
{
    private T[] a;      // en tabell
    private int fra;    // posisjonen til den første i køen
    private int til;    // posisjonen til første ledige plass

    @SuppressWarnings("unchecked")      // pga. konverteringen: Object[] -> T[]
    public TabellKø(int lengde)
    {
        if (lengde < 1)
            throw new IllegalArgumentException("Må ha positiv lengde!");

        a = (T[])new Object[lengde];

        fra = til = 0;    // a[fra:til> er tom
    }

    public TabellKø()   // standardkonstruktør
    {
        this(8);
    }

    // Her skal resten av metodene settes inn
    public boolean leggInn(T verdi)   // null-verdier skal være tillatt
    {
        a[til] = verdi;                                 // ny verdi bakerst
        til++;                                          // øker til med 1
        if (til == a.length) til = 0;                   // hopper til 0
        if (fra == til) a = utvidTabell(2*a.length);    // sjekker og dobler
        return true;                                    // vellykket innlegging
    }

    @Override
    public T kikk() {
        if(antall()>0){
            return a[fra];
        }
        else return null;
    }

    private T[] utvidTabell(int lengde)
    {
        @SuppressWarnings("unchecked")      // pga. konverteringen: Object[] -> T[]
        T[] b = (T[])new Object[lengde];  // ny tabell

        // kopierer intervallet a[fra:a.length> over i b
        System.arraycopy(a,fra,b,0,a.length - fra);

        // kopierer intervallet a[0:fra> over i b
        System.arraycopy(a,0,b,a.length - fra, fra);

        fra = 0; til = a.length;
        return b;
    }

    public T taUt()
    {
        if (fra == til) throw new         // sjekker om køen er tom
                NoSuchElementException("Køen er tom!");

        T temp = a[fra];                  // tar vare på den første i køen
        a[fra] = null;                    // nuller innholdet
        fra++;                            // øker fra med 1
        if (fra == a.length) fra = 0;     // hopper til 0
        return temp;                      // returnerer den første
    }

    @Override
    public int antall() {
        if(til>fra){
            return til-fra;
        }
        else if(til<fra){
            return a.length-fra+til;
        }
        else return 0;
    }

    @Override
    public boolean tom() {
        return fra==til;
    }

    @Override
    public void nullstill() {
        if (tom()) {
            return;
        }
        if (til > fra) {
            for (int i = fra; i < til; i++) {
                a[i] = null;
            }
        }
        else if(til<fra){
            for(int i = fra; i<a.length; i++){
                a[i]=null;
            }
            for(int i = 0; i<=fra; i++){
                a[i]=null;
            }
        }
        fra = til;
    }

    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append("[");
        int k = fra;
        if(k==til){
            s.append("]");
            return s.toString();
        }
        else {
            s.append(a[k]);
            k++;
            while (k != til) {
                s.append(" " + a[k++]);
                if (k == a.length) {
                    k = 0;
                }

            }
        }
        s.append("]");
        return s.toString();
    }

    public int indeksTil (T verdi){
        int k = fra;
        while(k!=til){
            if(a[k++]==verdi){
                return k-1;
            }
            if(k==a.length){
                k=0;
            }

        }
        return -1;
}

    public static <T> void snu(Stakk<T> A){
        Kø<T> hjelpeKø = new TabellKø<>();
        while (!A.tom()){
            hjelpeKø.leggInn(A.taUt());
        }
        while(!hjelpeKø.tom()){
            A.leggInn(hjelpeKø.taUt());
        }
    }
    public static <T> void snu(Kø<T> A){
        Stakk<T> stakk = new TabellStakk<T>();
        while(!A.tom()){
            stakk.leggInn(A.taUt());
        }
        while (!stakk.tom()){
            A.leggInn(stakk.taUt());
        }
    }

    public static <T> void sorter(Kø<T> kø, Stakk<T> stakk, Comparator<? super T> c){
        if(c.compare(kø.kikk(), stakk.kikk())>0){
            //Bytt plass, eller finn maks!
            //Her mangler vi siste opg
        }
    }
} // class TabellKø
