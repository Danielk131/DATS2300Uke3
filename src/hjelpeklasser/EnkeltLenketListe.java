package hjelpeklasser;
import java.util.Iterator;
import java.util.Objects;

public class EnkeltLenketListe<T> implements Liste<T>
{
    private static final class Node<T>       // en indre nodeklasse
    {
        private T verdi;                       // nodens verdi
        private Node<T> neste;                 // den neste noden

        private Node(T verdi,Node<T> neste)    // konstruktør
        {
            this.verdi = verdi;
            this.neste = neste;
        }
    }  // Node

    private Node<T> hode, hale;  // pekere til første og siste node

    private int antall;          // antall verdier/noder i listen

    private Node<T> finnNode(int indeks)
    {
        Node<T> p = hode;
        for (int i = 0; i < indeks; i++) p = p.neste;
        return p;
    }

    public EnkeltLenketListe()   // standardkonstruktør
    {
        hode = hale = null;        // hode og hale til null
        antall = 0;                // ingen verdier - listen er tom
    }

    public EnkeltLenketListe(T[] a){
            this();  // alle variabelene er nullet

            // Finner den første i a som ikke er null
           int i=0;
           for (; i < a.length && a[i] == null; i++);

            if (i < a.length)
            {
                Node<T> p = hode = new Node<>(a[i], null);  // den første noden
                antall = 1;                                 // vi har minst en node

                for (i++; i < a.length; i++)
                {
                    if (a[i] != null)
                    {
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

        if (antall == 0)  hode = hale = new Node<>(verdi, null);  // tom liste
        else hale = hale.neste = new Node<>(verdi, null);         // legges bakerst

        antall++;        // en mer i listen
        return true;     // vellykket innlegging
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
        }
        else if (indeks == antall)           // ny verdi skal ligge bakerst
        {
            hale = hale.neste = new Node<>(verdi, null);  // legges bakerst
        }
        else
        {
            Node<T> p = hode;                  // p flyttes indeks - 1 ganger
            for (int i = 1; i < indeks; i++) p = p.neste;

            p.neste = new Node<>(verdi, p.neste);  // verdi settes inn i listen
        }

        antall++;                            // listen har fått en ny verdi
    }

    @Override
    public boolean inneholder(T t)
    {
        if(indeksTil(t)>-1){
            return true;
        }
        return false;
    }

    @Override
    public T hent(int indeks)
    {
        indeksKontroll(indeks, false);  // false: indeks = antall er ulovlig
        return finnNode(indeks).verdi;
    }

    @Override
    public T oppdater(int indeks, T verdi)
    {
        Objects.requireNonNull(verdi, "Ikke tillatt med null-verdier!");

        indeksKontroll(indeks, false);  // false: indeks = antall er ulovlig

        Node<T> p = finnNode(indeks);
        T gammelVerdi = p.verdi;

        p.verdi = verdi;
        return gammelVerdi;
    }

    @Override
    public int indeksTil(T t)
    {
        Node<T> current=hode;
        for (int i=0; i<antall; i++){
            if(current.verdi.equals(t)){
                return i;
            }
            current=current.neste;
        }
        return -1;
    }

    @Override
    public T fjern(int indeks)
    {
        indeksKontroll(indeks, false);  // false: indeks = antall er ulovlig

        T temp;                              // hjelpevariabel

        if (indeks == 0)                     // skal første verdi fjernes?
        {
            temp = hode.verdi;                 // tar vare på verdien som skal fjernes
            hode = hode.neste;                 // hode flyttes til neste node

            if (antall == 1) hale = null;      // det var kun en verdi i listen
        }
        else
        {
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
    public boolean fjern(T t)
    {
        Node<T> current=hode;
        Node<T> temp;
        Node<T> prev;

        if(t.equals(hode.verdi)){
            temp=hode.neste;
            hode.neste=null;
            hode = temp;
            antall--;
            return true;
        }
        if(antall==1){
            hode=hale=null;
            antall--;
            return true;
        }
        else {
            prev=hode;
            current=current.neste;
            for(int i=1; i<antall; i++){
                if(t.equals(current.verdi)){
                    temp=current.neste;
                    current.neste=null;
                    prev.neste=temp;
                    antall--;
                    return true;

                }
                prev=current;
                current=current.neste;

            }
        }
        return false;
    }



    @Override
    public int antall()
    {
        return antall;
    }

    @Override
    public boolean tom()
    {
        if(antall==0){
            return true;
        }
        return false;
    }

    @Override
    public void nullstill()
    {
        Node<T> temp = null;
        Node<T> current = hode;
        for(int i=0; i<antall; i++){
            temp = current.neste;
            current.neste=null;
            current=temp;

        }
        antall=0;
    }

    @Override
    public Iterator<T> iterator()
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public String toString()
    {
        if(antall==0){
            return "[]";
        }
        Node<T> current = hode;
        StringBuilder s = new StringBuilder();
        s.append("[").append(hode.verdi);
        for(int i=1; i<antall; i++){
            current=current.neste;
            s.append(", ").append(current.verdi);
        }
        s.append("]");
        return s.toString();
    }

}  // EnkeltLenketListe