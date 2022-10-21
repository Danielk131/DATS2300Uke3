package eksempelklasser;

import hjelpeklasser.Stakk;

import java.util.*;

public class TabellStakk<T> implements Stakk<T>
{
    private T[] a;                     // en T-tabell
    private int antall;                // antall verdier på stakken

    public TabellStakk()               // konstruktør - tabellengde 8
    {
        this(8);
    }

    @SuppressWarnings("unchecked")     // pga. konverteringen: Object[] -> T[]
    public TabellStakk(int lengde)     // valgfri tabellengde
    {
        if (lengde < 0)
            throw new IllegalArgumentException("Negativ tabellengde!");

        a = (T[])new Object[lengde];     // oppretter tabellen
        antall = 0;                      // stakken er tom
    }

    public void leggInn(T verdi)
    {
        if (antall == a.length)
            a = Arrays.copyOf(a, antall == 0 ? 1 : 2*antall);   // dobler

        a[antall++] = verdi;
    }

    @Override
    public T kikk() {
        {
            if (antall == 0)       // sjekker om stakken er tom
                throw new NoSuchElementException("Stakken er tom!");

            return a[antall-1];    // returnerer den øverste verdien
        }
    }

    @Override
    public T taUt() {
        {
            if (antall == 0)       // sjekker om stakken er tom
                throw new NoSuchElementException("Stakken er tom!");

            antall--;             // reduserer antallet

            T temp = a[antall];   // tar var på det øverste objektet
            a[antall] = null;     // tilrettelegger for resirkulering

            return temp;          // returnerer den øverste verdien
        }
    }

    @Override
    public int antall() {
        return antall;
    }

    @Override
    public boolean tom() {
       return antall==0;
    }

    @Override
    public void nullstill() {
        for(int i =0; i<antall; i++){
            a[i]=null;
        }
            antall=0;
    }

    public String toString(){
        StringBuilder s = new StringBuilder();
        if(tom()){
            return "[]";
        }
        s.append("[" +a[antall-1]);
        for (int i=antall-2; i>=0; i--) {
            if (a[i] != null) {
                s.append(" " +a[i]);
            }
        }
        s.append("]");
        return s.toString();
    }

    public static <T> void snu(Stakk<T> A) {
        int  n =A.antall()-1;
        Stakk<T> s1 = new TabellStakk<>();


        while (n>0) {
            T temp = A.taUt();
            for (int i = 0; i < n; i++) {
                s1.leggInn(A.taUt());
            }
                A.leggInn(temp);
                while (!s1.tom()) {
                    A.leggInn(s1.taUt());
                }
            n--;
            }

        }


    public static <T> void kopier(Stakk<T> A, Stakk<T> B){
        T temp;
        int n = A.antall();
            while (n > 0) {
                B.leggInn(A.taUt());
                temp = B.kikk();
                for (int i = 0; i > n; i++) {
                    A.leggInn(B.taUt());
                    B.leggInn(temp);
                }
                n--;
            }
        }

    public static <T> void sorter(Stakk<T> A, Comparator<? super T> c){
            Stakk<T> B = new TabellStakk<T>();
            T temp;
            int n = 0;

            while (!A.tom())
            {
                temp = A.taUt();
                n = 0;
                while (!B.tom() && c.compare(temp,B.kikk()) < 0)
                {
                    n++; A.leggInn(B.taUt());
                }
                B.leggInn(temp);
                for (int i = 0; i < n; i++) B.leggInn(A.taUt());
            }

            while (!B.tom()) A.leggInn(B.taUt());
        }
}  // class TabellStakk
