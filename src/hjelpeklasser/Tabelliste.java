package hjelpeklasser;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Tabelliste<T> implements Liste<T> {
    private T[] a;
    private int antall;

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
        antall = 0;

    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public boolean leggInn(T verdi) {
        return false;
    }

    @Override
    public void leggInn(int indeks, T verdi) {

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
        return null;
    }

    @Override
    public boolean fjern(T verdi) {
        for (int i = 0; i < antall; i++) {
            if (verdi.equals(a[i])) {
                antall--;
                System.arraycopy(a, i + 1, a, i, antall - i);
                a[antall] = null;
                return true;
            }
        }
        return false;
    }

    @Override
    public T fjern(int indeks) {
        return null;
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

    // Skal ligge som en indre klasse i class TabellListe
    private class TabellListeIterator implements Iterator<T> {
        private int denne = 0;       // instansvariabel

        public boolean hasNext()     // sjekker om det er flere igjen
        {
            return denne < antall;     // sjekker verdien til denne
        }

        public Iterator<T> iterator() {
            return new TabellListeIterator();
        }


        private boolean fjernOK = false;   // ny instansvariabel i TabellListeIterator

        public T next()                    // ny versjon
        {
            if (!hasNext())
                throw new NoSuchElementException("Tomt eller ingen verdier igjen!");

            T denneVerdi = a[denne];   // henter aktuell verdi
            denne++;                   // flytter indeksen
            fjernOK = true;            // nå kan remove() kalles

            return denneVerdi;         // returnerer verdien
        }

        public void remove()         // ny versjon
        {
            if (!fjernOK) throw
                    new IllegalStateException("Ulovlig tilstand!");

            fjernOK = false;           // remove() kan ikke kalles på nytt

            // verdien i denne - 1 skal fjernes da den ble returnert i siste kall
            // på next(), verdiene fra og med denne flyttes derfor en mot venstre
            antall--;           // en verdi vil bli fjernet
            denne--;            // denne må flyttes til venstre

            System.arraycopy(a, denne + 1, a, denne, antall - denne);  // tetter igjen
            a[antall] = null;   // verdien som lå lengst til høyre nulles
        }
    }
}

