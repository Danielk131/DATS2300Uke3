package hjelpeklasser;


import java.util.Objects;
import java.util.StringJoiner;
import java.util.*;

public class LenketHashTabell<T> implements Beholder<T>
{
    private static final class Node<T>      // en indre nodeklasse
    {
        private final T verdi;          // nodens verdi
        private final int hashverdi;    // lagrer hashverdien
        private Node<T> neste;          // peker til neste node

        private Node(T verdi, int hashverdi, Node<T> neste)  // konstruktør
        {
            this.verdi = verdi;
            this.hashverdi = hashverdi;
            this.neste = neste;
        }
    } // class Node

    private Node<T>[] hash;           // en nodetabell
    private final float tetthet;      // eng: loadfactor
    private int grense;               // eng: threshold (norsk: terskel)
    private int antall;               // antall verdier

    @SuppressWarnings({"rawtypes","unchecked"})
    public LenketHashTabell(int dimensjon)  // konstruktør
    {
        hash = new Node[dimensjon];
        antall = 0;
        tetthet = 0.75f;
        grense = (int)(tetthet * hash.length);
    }

    public LenketHashTabell()  // standardkonstruktør
    {
        this(13);  // velger 13 som startdimensjon inntil videre
    }

    private void utvid()
    {
        @SuppressWarnings({"rawtypes","unchecked"})      // bruker raw type
        Node<T>[] nyhash = new Node[2*hash.length + 1];  // dobling + 1

        for (int i = 0; i < hash.length; i++)            // den gamle tabellen
        {
            Node<T> p = hash[i];                           // listen til hash[i]

            while (p != null)                              // går nedover
            {
                Node<T> q = p.neste;                         // hjelpevariabel
                int nyindeks = p.hashverdi % nyhash.length;  // indeks i ny tabell

                p.neste = nyhash[nyindeks];                  // p skal legges først

                nyhash[nyindeks] = p;
                p = q;                                       // flytter p til den neste
            }

            hash[i] = null;                                // nuller i den gamle
        }

        hash = nyhash;                                   // bytter tabell
        grense = (int)(tetthet * hash.length);           // ny grense
    }

    @Override
    public boolean leggInn(T verdi)
    {
        Objects.requireNonNull(verdi, "verdi er null!");

        if (antall >= grense) utvid();

        int hashverdi = verdi.hashCode();
        int indeks = hashverdi % hash.length;

        hash[indeks] = new Node<>(verdi, hashverdi, hash[indeks]);

        antall++;
        return true;
    }

    @Override
    public int antall()
    {
        return antall;
    }

    @Override
    public boolean tom()
    {
        return antall == 0;
    }

    @Override
    public String toString()
    {
        StringJoiner sj = new StringJoiner(", ", "[", "]");

        for (Node<T> p : hash)
        {
            while (p != null)
            {
                sj.add(p.verdi.toString());
                p = p.neste;
            }
        }

        return sj.toString();
    }

    @Override
    public boolean inneholder(T verdi)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean fjern(T verdi)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void nullstill()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Iterator<T> iterator()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}  // class LenketHashTabell