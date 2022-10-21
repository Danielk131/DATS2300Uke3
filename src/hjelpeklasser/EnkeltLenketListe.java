package hjelpeklasser;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class EnkeltLenketListe<T> implements Liste<T>, Kø<T> {
    private static final class Node<T>       // en indre nodeklasse
    {
        private T verdi;                       // nodens verdi
        private Node<T> neste;                 // den neste noden

        private Node(T verdi, Node<T> neste)    // konstruktør
        {
            this.verdi = verdi;
            this.neste = neste;
        }
    }  // Node

    private Node<T> hode, hale;  // pekere til første og siste node
    private int antall;          // antall verdier/noder i listen
    private int endringer;

    private Node<T> finnNode(int indeks) {
        Node<T> p = hode;
        for (int i = 0; i < indeks; i++) p = p.neste;
        return p;
    }

    public EnkeltLenketListe()   // standardkonstruktør
    {
        hode = hale = null;        // hode og hale til null
        antall = 0;                // ingen verdier - listen er tom
    }

    public EnkeltLenketListe(T[] a) {
        this();  // alle variabelene er nullet

        // Finner den første i a som ikke er null
        int i = 0;
        for (; i < a.length && a[i] == null; i++) ;

        if (i < a.length) {
            Node<T> p = hode = new Node<>(a[i], null);  // den første noden
            antall = 1;                                 // vi har minst en node

            for (i++; i < a.length; i++) {
                if (a[i] != null) {
                    p = p.neste = new Node<>(a[i], null);   // en ny node
                    antall++;
                }
            }
            hale = p;
        }
    }

    @Override
    public boolean leggInn(T verdi)   // verdi legges bakerst
    {
        Objects.requireNonNull(verdi, "Ikke tillatt med null-verdier!");

        if (antall == 0) hode = hale = new Node<>(verdi, null);  // tom liste
        else hale = hale.neste = new Node<>(verdi, null);         // legges bakerst

        antall++;        // en mer i listen
        return true;     // vellykket innlegging
    }

    @Override
    public T kikk() {
        return hode.verdi;
    }

    @Override
    public T taUt() {
        T ut = fjern(indeksTil(hode.verdi));
        return ut;
    }

    @Override
    public void leggInn(int indeks, T verdi)    // verdi til posisjon indeks
    {
        Objects.requireNonNull(verdi, "Ikke tillatt med null-verdier!");

        indeksKontroll(indeks, true);        // true: indeks = antall er lovlig

        if (indeks == 0)                     // ny verdi skal ligge først
        {
            hode = new Node<>(verdi, hode);    // legges først
            if (antall == 0) hale = hode;      // hode og hale peker på samme node
        } else if (indeks == antall)           // ny verdi skal ligge bakerst
        {
            hale = hale.neste = new Node<>(verdi, null);  // legges bakerst
        } else {
            Node<T> p = hode;                  // p flyttes indeks - 1 ganger
            for (int i = 1; i < indeks; i++) p = p.neste;

            p.neste = new Node<>(verdi, p.neste);  // verdi settes inn i listen
        }

        antall++;                            // listen har fått en ny verdi
    }

    @Override
    public boolean inneholder(T t) {
        if (indeksTil(t) > -1) {
            return true;
        }
        return false;
    }

    @Override
    public T hent(int indeks) {
        indeksKontroll(indeks, false);  // false: indeks = antall er ulovlig
        return finnNode(indeks).verdi;
    }

    @Override
    public T oppdater(int indeks, T verdi) {
        Objects.requireNonNull(verdi, "Ikke tillatt med null-verdier!");

        indeksKontroll(indeks, false);  // false: indeks = antall er ulovlig

        Node<T> p = finnNode(indeks);
        T gammelVerdi = p.verdi;

        p.verdi = verdi;
        return gammelVerdi;
    }

    @Override
    public int indeksTil(T t) {
        Node<T> current = hode;
        for (int i = 0; i < antall; i++) {
            if (current.verdi.equals(t)) {
                return i;
            }
            current = current.neste;
        }
        return -1;
    }

    @Override
    public T fjern(int indeks) {
        indeksKontroll(indeks, false);  // false: indeks = antall er ulovlig

        T temp;                              // hjelpevariabel

        if (indeks == 0)                     // skal første verdi fjernes?
        {
            temp = hode.verdi;                 // tar vare på verdien som skal fjernes
            hode = hode.neste;                 // hode flyttes til neste node

            if (antall == 1) hale = null;      // det var kun en verdi i listen
        } else {
            Node<T> p = finnNode(indeks - 1);  // p er noden foran den som skal fjernes
            Node<T> q = p.neste;               // q skal fjernes

            temp = q.verdi;                    // tar vare på verdien som skal fjernes

            if (q == hale) hale = p;           // q er siste node

            p.neste = q.neste;                 // "hopper over" q
        }

        antall--;                            // reduserer antallet

        return temp;                         // returner fjernet verdi
    }

    @Override
    public boolean fjern(T t) {
        Node<T> current = hode;
        Node<T> temp;
        Node<T> prev;

        if (t.equals(hode.verdi)) {
            temp = hode.neste;
            hode.neste = null;
            hode = temp;
            antall--;
            return true;
        }
        if (antall == 1) {
            hode = hale = null;
            antall--;
            return true;
        } else {
            prev = hode;
            current = current.neste;
            for (int i = 1; i < antall; i++) {
                if (t.equals(current.verdi)) {
                    temp = current.neste;
                    current.neste = null;
                    prev.neste = temp;
                    antall--;
                    return true;

                }
                prev = current;
                current = current.neste;

            }
        }
        return false;
    }


    @Override
    public int antall() {
        return antall;
    }

    @Override
    public boolean tom() {
        if (antall == 0) {
            return true;
        }
        return false;
    }

    @Override
    public void nullstill() {
        Node<T> temp = null;
        Node<T> current = hode;
        for (int i = 0; i < antall; i++) {
            temp = current.neste;
            current.neste = null;
            current = temp;

        }
        antall = 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new EnkeltLenketListeIterator();
    }

    private class EnkeltLenketListeIterator implements Iterator<T> {
        private Node<T> p = hode;         // p starter på den første i listen
        private boolean fjernOK = false;  // blir sann når next() kalles
        private int iteratorendringer = endringer;  // startverdi

        @Override
        public boolean hasNext() {
            return p != null;  // p er ute av listen hvis den har blitt null
        }

        @Override
        public T next() {
            if (endringer != iteratorendringer)
                throw new ConcurrentModificationException("Listen er endret!");

            if (!hasNext()) throw new
                    NoSuchElementException("Tomt eller ingen verdier igjen!");

            fjernOK = true;            // nå kan remove() kalles

            T denneVerdi = p.verdi;    // tar vare på verdien i p
            p = p.neste;               // flytter p til den neste noden

            return denneVerdi;         // returnerer verdien
        }

        @Override
        public void remove() {
            if (endringer != iteratorendringer)
                throw new ConcurrentModificationException("Listen er endret!");


            if (!fjernOK) throw new IllegalStateException("Ulovlig tilstand!");

            fjernOK = false;               // remove() kan ikke kalles på nytt
            Node<T> q = hode;              // hjelpepeker

            if (hode.neste == p)           // skal den første fjernes?
            {
                hode = hode.neste;           // den første fjernes
                if (p == null) hale = null;  // dette var den eneste noden
            } else {
                Node<T> r = hode;            // må finne forgjengeren
                // til forgjengeren til p
                while (r.neste.neste != p) {
                    r = r.neste;               // flytter r
                }

                q = r.neste;                 // det er q som skal fjernes
                r.neste = p;                 // "hopper" over q
                if (p == null) hale = r;     // q var den siste
            }

            q.verdi = null;                // nuller verdien i noden
            q.neste = null;                // nuller nestepeker

            endringer++;             // en endring i listen
            iteratorendringer++;    // en endring av denne iteratoren
            antall--;                      // en node mindre i listen
        }

        public void forEachRemaining(Consumer<? super T> handling) {
            Objects.requireNonNull(handling, "handling er null!");
            while (p != null) {
                handling.accept(p.verdi);
                p = p.neste;
            }
        }

        @Override
        public String toString() {
            if (antall == 0) {
                return "[]";
            }
            Node<T> current = hode;
            StringBuilder s = new StringBuilder();
            s.append("[").append(hode.verdi);
            for (int i = 1; i < antall; i++) {
                current = current.neste;
                s.append(", ").append(current.verdi);
            }
            s.append("]");
            return s.toString();
        }

    }  // EnkeltLenketListe

    public boolean fjernHvis(Predicate<? super T> p) {
        Objects.requireNonNull(p, "Null predikat");

        Node<T> current = hode;
        Node<T> temp = null;

        int antallFjernet=0;

        for (int i = 0; i < antall; i++) {
            if (p.test(current.verdi)) {
                endringer++;
                antallFjernet++;

                if (current == hode) {
                    if (current == hale) {
                        hale = null;
                        hode = hode.neste;
                    }
                    hode = current.neste;
                    current.neste = null;
                } else if (current == hale) {
                    hale = temp;
                    hale.neste = null;
                } else {
                    temp.neste = current.neste;
                    current.neste = null;
                }
            }
            temp = current;
            current = current.neste;
        }
        antall -= antallFjernet;
        return antallFjernet>0;
    }
    public void forEach(Consumer<? super T> handling)
    {
        Objects.requireNonNull(handling, "handling er null!");

        Node<T> p = hode;
        while (p != null)
        {
            handling.accept(p.verdi);
            p = p.neste;
        }
    }
}