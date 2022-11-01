package hjelpeklasser;

import hjelpeklasser.Liste;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Tabelliste<T> implements Liste<T> {
    private T[] a;
    private int antall;
    private int endringer;

    // konstruktører og metoder kommer her
    @SuppressWarnings("unchecked")          // pga. konverteringen: Object[] -> T[]
    public Tabelliste(int størrelse)       // konstruktør
    {
        a = (T[]) new Object[størrelse];       // oppretter tabellen
        antall = 0;                           // foreløpig ingen verdier
    }

    public Tabelliste()                    // standardkonstruktør
    {
        this(10);                             // startstørrelse på 10
    }

    public Tabelliste(T[] b)                    // en T-tabell som parameter
    {
        this(b.length);                            // kaller den andre konstruktøren

        for (T verdi : b) {
            if (verdi != null) a[antall++] = verdi;  // hopper over null-verdier
        }
    }


    public int antall() {
        return antall;          // returnerer antallet
    }

    public boolean tom() {
        return antall == 0;     // listen er tom hvis antall er 0
    }



    @Override
    public void nullstill() {
        if (antall > 10) {
            a = (T[]) new Object[10];
        }
        for (int i = 0; i < antall; i++) {
            a[i] = null;
        }
        endringer++;
        antall = 0;

    }
    @Override
    public boolean leggInn(T verdi)  // inn bakerst
    {
        Objects.requireNonNull(verdi, "null er ulovlig!");

        // En full tabell utvides med 50%
        if (antall == a.length)
        {
            a = Arrays.copyOf(a,(3*antall)/2 + 1);
        }

        a[antall++] = verdi;   // setter inn ny verdi

        endringer++;

        return true;
    }

    @Override
    public void leggInn(int indeks, T verdi)
    {
        Objects.requireNonNull(verdi, "null er ulovlig!");

        indeksKontroll(indeks, true);  // true: indeks = antall er lovlig

        // En full tabell utvides med 50%
        if (antall == a.length) a = Arrays.copyOf(a,(3*antall)/2 + 1);

        // rydder plass til den nye verdien
        System.arraycopy(a, indeks, a, indeks + 1, antall - indeks);

        a[indeks] = verdi;     // setter inn ny verdi

        antall++;
        endringer++;
    }

    public T hent(int indeks) {
        indeksKontroll(indeks, false);   // false: indeks = antall er ulovlig
        return a[indeks];                // returnerer er tabellelement
    }

    public int indeksTil(T verdi) {
        for (int i = 0; i < antall; i++) {
            if (a[i].equals(verdi)) return i;   // funnet!
        }
        return -1;   // ikke funnet!
    }

    public boolean inneholder(T verdi) {
        return indeksTil(verdi) != -1;
    }

    @Override
    public T oppdater(int indeks, T verdi) {
        Objects.requireNonNull(verdi, "null er ulovlig!");

        indeksKontroll(indeks, false);  // false: indeks = antall er ulovlig

        T gammelverdi = a[indeks];      // tar vare på den gamle verdien
        a[indeks] = verdi;              // oppdaterer
        endringer++;
        return gammelverdi;             // returnerer den gamle verdien
    }

    @Override
    public boolean fjern(T verdi) {
        for (int i = 0; i < antall; i++) {
            if (verdi.equals(a[i])) {
                antall--;
                System.arraycopy(a, i + 1, a, i, antall - i);
                a[antall] = null;
                endringer++;
                return true;
            }
        }
        return false;
    }

    @Override
    public T fjern(int indeks) {
        indeksKontroll(indeks, false);  // false: indeks = antall er ulovlig
        T verdi = a[indeks];

        antall--;
        System.arraycopy(a, indeks +1,a, indeks, antall-indeks);
        endringer++;

        return verdi;
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("[").append(a[0]);
        if (antall == 0) {
            return "[]";
        }

        for (int i = 1; i < antall; i++) {
            str.append(", " + a[i]);
        }
        str.append("]");
        return str.toString();
    }

    public Iterator<T> iterator() {
        return new TabellListeIterator();
    }

    // Skal ligge som en indre klasse i class TabellListe
    private class TabellListeIterator implements Iterator<T> {
        private int denne = 0;       // instansvariabel
        private boolean fjernOK = false;   // ny instansvariabel i TabellListeIterator
        private int iteratorendringer=endringer;

        public boolean hasNext()     // sjekker om det er flere igjen
        {
            return denne < antall;     // sjekker verdien til denne
        }



        public T next()                    // ny versjon
        {
            if(iteratorendringer!=endringer){
                throw new ConcurrentModificationException("Listen er endret");
            }
            if (!hasNext())
                throw new NoSuchElementException("Tomt eller ingen verdier igjen!");

            T denneVerdi = a[denne];   // henter aktuell verdi
            denne++;                   // flytter indeksen
            fjernOK = true;            // nå kan remove() kalles

            return denneVerdi;         // returnerer verdien
        }

        public void remove()         // ny versjon
        {
            if(iteratorendringer!=endringer)
                throw new ConcurrentModificationException("Listen er endret");
            if (!fjernOK) throw
                    new IllegalStateException("Ulovlig tilstand!");

            fjernOK = false;           // remove() kan ikke kalles på nytt

            // verdien i denne - 1 skal fjernes da den ble returnert i siste kall
            // på next(), verdiene fra og med denne flyttes derfor en mot venstre
            antall--;           // en verdi vil bli fjernet
            denne--;            // denne må flyttes til venstre

            System.arraycopy(a, denne + 1, a, denne, antall - denne);  // tetter igjen
            a[antall] = null;   // verdien som lå lengst til høyre nulles
            iteratorendringer++;
            endringer++;

        }
            public boolean fjernHvis(Predicate<? super T> p) {
            Objects.requireNonNull(p);                       // kaster unntak

            int nyttAntall = antall;
            for (int i = 0, j = 0; j < antall; j++) {
                if (p.test(a[j])) {  //Hvis p=a[j] så skal a[j] fjernes
                    nyttAntall--;   //Oppdaterer lengden på¨array
                } else
                    a[i++] = a[j];   //Forskyver array, i oppdaterer etterpå(først a[0] så kommer i++ etter det)
            }
            for (int i = nyttAntall; i < antall; i++) {
                a[i] = null;
            }

            boolean fjernet = nyttAntall < antall;
            antall = nyttAntall;
            if(fjernet)
                endringer++;
            return fjernet;
        }

            public void forEach(Consumer<? super T> action){
            for (int i = 0; i>antall; i++){
                action.accept(a[i]);
            }
        }

            public void forEachRemaining(Consumer<? super T> action){
            while (denne>antall){
                action.accept(a[denne++]);
            }
        }
    }

}



