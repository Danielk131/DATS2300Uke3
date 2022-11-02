package hjelpeklasser;
////////////////// class SBinTre //////////////////////////////

import eksempelklasser.TabellStakk;

import java.util.*;
import java.util.stream.Stream;

public class SBinTre<T> implements Beholder<T> {
    private static final class Node<T> // en indre nodeklasse
    {
        private T verdi;                 // nodens verdi
        private Node<T> venstre, høyre;  // venstre og høyre barn


        private Node(T verdi, Node<T> v, Node<T> h)  // konstruktør
        {
            this.verdi = verdi;
            venstre = v;
            høyre = h;
        }

        private Node(T verdi)  // konstruktør
        {
            this(verdi, null, null);
        }

    } // class Node

    private Node<T> rot;                       // peker til rotnoden
    private int antall;                        // antall noder
    private final Comparator<? super T> comp;  // komparator
    private int endringer;

    public SBinTre(Comparator<? super T> c)    // konstruktør
    {
        rot = null;
        antall = 0;
        comp = c;
    }

    public static <T extends Comparable<? super T>> SBinTre<T> sbintre() {
        return new SBinTre<>(Comparator.naturalOrder());
    }

    public static <T> SBinTre<T> sbintre(Comparator<? super T> c) {
        return new SBinTre<>(c);
    }

    public static <T extends Comparable<? super T>> SBinTre<T> naturligOrdenTre() {
        return new SBinTre<>(Comparator.naturalOrder());
    }

    public static <T> SBinTre<T> komparatorTre(Comparator<? super T> c) {
        return new SBinTre<>(c);
    }

    public static <T> SBinTre<T> komparatorTre(T[] a, Comparator<? super T> c) {
        SBinTre<T> tre = new SBinTre<>(c);          // komparatoren c
        for (T verdi : a) tre.leggInn(verdi);       // bygger opp treet
        return tre;                                 // treet returneres
    }

    public static <T extends Comparable<? super T>> SBinTre<T> naturligOrdenTre(T[] a) {
        return komparatorTre(a, Comparator.naturalOrder());  // naturlig ordning
    }

    public int antall()        // antall verdier i treet
    {
        return antall;
    }

    public boolean tom()       // er treet tomt?
    {
        return antall == 0;
    }

    public boolean leggInn(T verdi)    // skal ligge i class SBinTre
    {
        if (verdi == null) throw new NullPointerException("Ulovlig nullverdi!");

        Node<T> p = rot, q = null;               // p starter i roten
        int cmp = 0;                             // hjelpevariabel

        while (p != null)       // fortsetter til p er ute av treet
        {
            q = p;                                 // q er forelder til p
            cmp = comp.compare(verdi, p.verdi);     // bruker komparatoren
            p = cmp < 0 ? p.venstre : p.høyre;     // flytter p
        }

        // p er nå null, dvs. ute av treet, q er den siste vi passerte

        p = new Node<>(verdi);                   // oppretter en ny node

        if (q == null) rot = p;                  // p blir rotnode
        else if (cmp < 0) q.venstre = p;         // venstre barn til q
        else q.høyre = p;                        // høyre barn til q

        endringer++;
        antall++;                                // én verdi mer i treet
        return true;                             // vellykket innlegging
    }

    @Override
    public boolean inneholder(T verdi) {
        return false;
    }

    private static int høyde(Node<?> p)  // ? betyr vilkårlig type
    {
        if (p == null) return -1;          // et tomt tre har høyde -1

        return 1 + Math.max(høyde(p.venstre), høyde(p.høyre));
    }

    public int høyde() {
        return høyde(rot);                 // kaller hjelpemetoden
    }

    public String toString()                   // hører til SBinTre
    {
        StringBuilder s = new StringBuilder();   // StringBuilder
        s.append('[');                           // starter med [
        if (!tom()) toString(rot, s);             // den rekursive metoden
        s.append(']');                           // avslutter med ]
        return s.toString();                     // returnerer
    }

    private static <T> void toString(Node<T> p, StringBuilder s) {
        if (p.venstre != null)                   // p har et venstre subtre
        {
            toString(p.venstre, s);                // komma og mellomrom etter
            s.append(',').append(' ');             // den siste i det venstre
        }                                        // subtreet til p

        s.append(p.verdi);                       // verdien i p

        if (p.høyre != null)                     // p har et høyre subtre
        {
            s.append(',').append(' ');             // komma og mellomrom etter
            toString(p.høyre, s);                  // p siden p ikke er den
        }                                        // siste noden i inorden
    }

    private static <T> Node<T> balansert(T[] a, int v, int h)  // en rekursiv metode
    {
        if (v > h) return null;                       // tomt intervall -> tomt tre

        int m = (v + h) / 2;                            // midten
        T verdi = a[m];                               // midtverdien

        while (v < m && verdi.equals(a[m - 1])) m--;    // til venstre

        Node<T> p = balansert(a, v, m - 1);           // venstre subtre
        Node<T> q = balansert(a, m + 1, h);           // høyre subtre

        return new Node<>(verdi, p, q);               // rotnoden
    }

    public static <T> SBinTre<T> balansert(T[] a, Comparator<? super T> c) {
        SBinTre<T> tre = new SBinTre<>(c);          // oppretter et tomt tre
        tre.rot = balansert(a, 0, a.length - 1);    // bruker den rekursive metoden
        tre.antall = a.length;                      // setter antallet
        return tre;                                 // returnerer treet
    }

    public static <T extends Comparable<? super T>> SBinTre<T> balansert(T[] a) {
        return balansert(a, Comparator.naturalOrder());
    }

    public int antall(T verdi) {
        int antall = 0;
        Node<T> p = rot;
        int cmp = comp.compare(p.verdi, verdi);
        while (p != null) {
            if (cmp > 0) {
                p = p.venstre;
            } else {
                if (cmp == 0) {
                    antall++;
                }
                p = p.høyre;
            }
        }
        return antall;
    }

    public Liste<T> intervallsøk(T fraverdi, T tilverdi) {
        Stakk<Node<T>> s = new TabellStakk<>();

        Node<T> p = rot;
        while (p != null)                  // leter etter fraverdi
        {
            int cmp = comp.compare(fraverdi, p.verdi);
            if (cmp < 0) {
                s.leggInn(p);
                p = p.venstre;
            } else if (cmp > 0) p = p.høyre;
            else break;
        }

        if (p == null) p = s.taUt();  // neste i inorden

        Liste<T> liste = new Tabelliste<>();

        while (p != null && comp.compare(p.verdi, tilverdi) < 0) {
            liste.leggInn(p.verdi);

            if (p.høyre != null) {
                p = p.høyre;
                while (p.venstre != null) {
                    s.leggInn(p);
                    p = p.venstre;
                }
            } else if (!s.tom()) p = s.taUt();
            else p = null;
        }

        return liste;
    }

    public T maks() {
        if (antall == 0) {
            throw new IllegalArgumentException("TOm liste");
        }
        Node<T> p = rot;
        Node<T> q = rot;
        T maks = p.verdi;
        int cmp = comp.compare(p.verdi, q.verdi);
        while (p.høyre != null) {
            p = p.høyre;
            if (cmp == 0) {
                q = p;
            }
        }

        return q.verdi;
    }

    public T gulv(T verdi) {
        Objects.requireNonNull(verdi, "Treet har ingen nullverdier!");
        if (tom()) throw new NoSuchElementException("Treet er tomt!");

        Node<T> q = null;
        Node<T> p = rot;
        T gulv = null;

        while (p != null) {
            int cmp = comp.compare(verdi, p.verdi);

            if (cmp < 0) p = p.venstre;  // gulvet ligger til venstre
            else if (cmp > 0) {
                gulv = p.verdi;            // nodeverdien er en kandidat
                p = p.høyre;
            } else {
                q = p;
            }
            ;         // verdi ligger i treet
        }
        if (q != null) {
            return q.verdi;
        }
        return gulv;
    }

    public T tak(T verdi) {
        if (tom()) {
            throw new NoSuchElementException("TOmt");
        }
        Node<T> p = rot;
        Node<T> q = null;
        int cmp = comp.compare(p.verdi, verdi);
        while (p != null) {
            if (cmp > 0) {
                if (comp.compare(p.verdi, q.verdi) < 0 || q == null) {
                    q = p;
                }
                p = p.venstre;
            } else {
                p = p.høyre;
            }
        }
        return q.verdi;
    }


    public T mindre(T verdi) {
        if (tom()) {
            throw new NoSuchElementException("TOmt");
        }
        Node<T> p = rot;
        Node<T> q = null;
        int cmp = comp.compare(p.verdi, verdi);
        while (p != null) {
            if (cmp < 0) {
                if (comp.compare(p.verdi, q.verdi) > 0 || q == null) {
                    q = p;
                }
                p = p.venstre;
            } else {
                p = p.høyre;
            }
        }
        return q.verdi;
    }

    public boolean fjern(T verdi)  // hører til klassen SBinTre
    {
        if (verdi == null) return false;  // treet har ingen nullverdier

        Node<T> p = rot, q = null;   // q skal være forelder til p

        while (p != null)            // leter etter verdi
        {
            int cmp = comp.compare(verdi, p.verdi);      // sammenligner
            if (cmp < 0) {
                q = p;
                p = p.venstre;                        // går til venstre
            } else if (cmp > 0) {
                q = p;
                p = p.høyre;                           // går til høyre
            } else break;    // den søkte verdien ligger i p
        }
        if (p == null) return false;   // finner ikke verdi

        if (p.venstre == null || p.høyre == null)  // Tilfelle 1) og 2)
        {
            Node<T> b = p.venstre != null ? p.venstre : p.høyre;  // b for barn
            if (p == rot) rot = b;
            else if (p == q.venstre) q.venstre = b;
            else q.høyre = b;
        } else  // Tilfelle 3)
        {
            Node<T> s = p, r = p.høyre;   // finner neste i inorden
            while (r.venstre != null) {
                s = r;    // s er forelder til r
                r = r.venstre;
            }

            p.verdi = r.verdi;   // kopierer verdien i r til p

            if (s != p) s.venstre = r.høyre;
            else s.høyre = r.høyre;
        }
        endringer++;
        antall--;   // det er nå én node mindre i treet
        return true;
    }

    public int fjernAlle(T verdi) {
        int teller = 0;
        while (fjern(verdi)) {
            teller++;
        }
        return teller;
    }

    public void fjernMin()  // hører til klassen SBinTre
    {
        if (tom()) throw new NoSuchElementException("Treet er tomt!");

        if (rot.venstre == null) rot = rot.høyre;  // rotverdien er minst
        else {
            Node<T> p = rot.venstre, q = rot;
            while (p.venstre != null) {
                q = p;  // q er forelder til p
                p = p.venstre;
            }
            // p er noden med minst verdi
            q.venstre = p.høyre;
        }
        endringer++;
        antall--;  // det er nå én node mindre i treet
    }

    public static <T> SBinTre<T> sbintre(Stream<T> s, Comparator<? super T> c) {
        SBinTre<T> tre = new SBinTre<>(c);             // komparatoren c
        s.forEach(tre::leggInn);                       // bygger opp treet
        return tre;                                    // treet returneres
    }

    public static <T extends Comparable<? super T>> SBinTre<T> sbintre(Stream<T> s) {
        return sbintre(s, Comparator.naturalOrder());  // naturlig ordning
    }


    public void nullstill(Node<T> p) {
        if (p.venstre != null) {
            nullstill(p.venstre);
            p.venstre = null;

        }
        if (p.høyre != null) {
            nullstill(p.høyre);
            p.høyre = null;

        }
        p.verdi = null;


    }

    public void nullstill() {
        if (rot != null) nullstill(rot);
        antall = 0;
        rot = null;
        endringer++;
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    public Iterator<T> riterator()
    {
        return new OmvendtInordenIterator();
    }

    public Iterator<T> riterator(T verdi)
    {
        return new OmvendtInordenIterator(verdi);
    }

    public void fjernMaks()  // hører til klassen SBinTre
    {
        if (tom()) throw new NoSuchElementException("Treet er tomt!");

        Node<T> p = rot;
        while (p.høyre != null) p = p.høyre;
        fjern(p.verdi);
    }

    public int fjernAlleMaks()  // hører til klassen SBinTre
    {
        if (tom()) return 0;

        int antallFjernet = 1;

        Node<T> p = rot, q = null;
        while (p.høyre != null) {
            if (comp.compare(p.høyre.verdi, p.verdi) > 0) {
                q = p;
                antallFjernet = 1;
            } else antallFjernet++;

            p = p.høyre;
        }

        if (q == null) rot = rot.venstre;
        else q.høyre = q.høyre.venstre;

        antall -= antallFjernet;
        return antallFjernet;
    }

    public Iterator<T> iterator(T verdi)  // returnerer en iterator
    {
        return new InordenIterator(verdi);
    }


    private class InordenIterator implements Iterator<T> {
        private Stakk<Node<T>> s = new TabellStakk<>();
        private Node<T> p = null;
        private int iteratorendringer;

        private Node<T> først(Node<T> q)   // en hjelpemetode
        {
            while (q.venstre != null)        // starter i q
            {
                s.leggInn(q);                  // legger q på stakken
                q = q.venstre;                 // går videre mot venstre
            }
            return q;                        // q er lengst ned til venstre
        }

        public InordenIterator(T verdi)    // konstruktør
        {
            if (verdi == null)
                throw new IllegalArgumentException("Treet har ikke nullverdier!");

            Node<T> q = rot;

            while (q != null) {
                int cmp = comp.compare(verdi, q.verdi);

                if (cmp < 0) {
                    s.leggInn(q);
                    q = q.venstre;
                } else if (cmp > 0) {
                    q = q.høyre;
                } else break;
            }

            if (q != null) p = q;
            else if (!s.tom()) p = s.taUt();

            iteratorendringer = endringer;   // setter treets endringer
        }


        @Override
        public T next() {
            if (endringer != iteratorendringer) {
                throw new ConcurrentModificationException();
            }
            if (!hasNext()) throw new NoSuchElementException("Ingen verdier!");

            T verdi = p.verdi;                        // tar vare på verdien

            if (p.høyre != null) p = først(p.høyre);  // p har høyre subtre
            else if (s.tom()) p = null;               // stakken er tom
            else p = s.taUt();                        // tar fra stakken

            return verdi;                             // returnerer verdien
        }

        @Override
        public boolean hasNext() {
            return p != null;
        }
    }

    private class OmvendtInordenIterator implements Iterator<T> {
        private Stakk<Node<T>> s = new TabellStakk<>();  // for traversering
        private Node<T> p = null;                        // nodepeker
        private Node<T> q = null;                        // nodepeker
        private int iteratorendringer;                   // iteratorendringer

        private Node<T> sist(Node<T> q)    // en hjelpemetode
        {
            while (q.høyre != null)          // starter i q
            {
                s.leggInn(q);                  // legger q på stakken
                q = q.høyre;                 // går videre mot høyre
            }
            return q;                        // q er lengst ned til høyre
        }

        public OmvendtInordenIterator()  // konstruktør
        {
            if (rot == null) return;         // treet er tomt
            p = sist(rot);                   // bruker hjelpemetoden
            iteratorendringer = endringer;   // setter treets endringer
        }

        public OmvendtInordenIterator(T verdi)    // konstruktør
        {
            if (verdi == null)
                throw new IllegalArgumentException("Treet har ikke nullverdier!");

            Node<T> q = rot;

            while (q != null) {
                int cmp = comp.compare(verdi, q.verdi);

                if (cmp < 0) {
                    q = q.venstre;
                } else {
                    s.leggInn(q);
                    q = q.høyre;
                }
            }
            if (!s.tom()) p = s.taUt();

            iteratorendringer = endringer;   // setter treets endringer
        }

        public T next() {
            if (iteratorendringer != endringer)
                throw new ConcurrentModificationException();

            if (!hasNext()) throw new NoSuchElementException();

            T verdi = p.verdi;               // tar vare på verdien i noden p
            q = p;                           // q oppdateres før p flyttes


            if (p.venstre != null) p = sist(p.venstre);  // p har høyre subtre
            else if (!s.tom()) p = s.taUt();             // p har ikke høyre subtre
            else p = null;                               // stakken er tom

            return verdi;                    // returnerer verdien
        }

        public boolean hasNext() {
            return p != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

    } // OmvendtInordenIterator
}





// class SBinTre
