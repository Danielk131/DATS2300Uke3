package eksempelklasser;


public class Tid implements Comparable<Tid>
{
    private Dato dato;
    private Klokkeslett klokkeslett;

    public Tid(int dag, int mnd, int år, String klokkeslett)
    {
        dato = new Dato(dag,mnd,år);
        this.klokkeslett = new Klokkeslett(klokkeslett);
    }

    public Tid(Dato dato, Klokkeslett klokkeslett)
    {
        this.dato = dato;
        this.klokkeslett = klokkeslett;
    }

    @Override
    public int compareTo(Tid tid)
    {
        int cmp = dato.compareTo(tid.dato);
        if (cmp != 0) return cmp;

        return klokkeslett.compareTo(tid.klokkeslett);
    }

    @Override
    public boolean equals(Object o)              // equals
    {
        if (o == this) return true;
        if (!(o instanceof Tid)) return false;
        return equals((Tid)o);
    }

    public boolean equals(Tid tid)              // equals
    {
        return
                klokkeslett.equals(tid.klokkeslett) && dato.equals(tid.dato);
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 97 * hash + dato.hashCode();
        hash = 97 * hash + klokkeslett.hashCode();
        return hash;
    }

    @Override
    public String toString()
    {
        return dato + " kl. " + klokkeslett;
    }

}  // Tid