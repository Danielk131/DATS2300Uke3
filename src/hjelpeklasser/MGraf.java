////////////////// class MGraf //////////////////////////////

package hjelpeklasser;

import java.util.*;
import java.io.*;
import java.net.URL;
import java.util.function.Consumer;

public final class MGraf implements Iterable<String>
{
    private boolean[][] graf;                    // grafmatrisen
    private int antall;                          // antall noder
    private String[] navn;                       // nodenavn - usortert
    private String[] snavn;                      // nodenavn - sortert
    private int[] indeks;                        // indekser
    private int[] forrige;                       // for senere bruk

    public MGraf(int dimensjon)                  // konstruktør
    {
        graf = new boolean[dimensjon][dimensjon];  // grafmatrisen
        antall = 0;                                // foreløpig ingen noder
        navn = new String[dimensjon];              // nodenavn - usortert
        snavn = new String[dimensjon];             // nodenavn - sortert
        indeks = new int[dimensjon];               // indekstabell
    }

    public MGraf()   // standardkonstruktør
    {
        this(10);      // 10 som startdimensjon
    }

    public int antallNoder()    // antall noder i grafem
    {
        return antall;
    }

    public int dimensjon()      // dimensjonen til tabellene
    {
        return graf.length;
    }

    public String[] nodenavn()  // navn på alle nodene
    {
        return Arrays.copyOf(snavn, antall);
    }

    private int finn(String nodenavn)  // privat hjelpemetode
    {
        return Arrays.binarySearch(snavn, 0, antall, nodenavn);
    }

    public boolean nodeFinnes(String nodenavn)  // finnes denne noden?
    {
        return finn(nodenavn) >= 0;
    }

    private void utvid()
    {
        int nydimensjon = graf.length == 0 ? 1 : 2*graf.length;  // dobler

        navn = Arrays.copyOf(navn, nydimensjon);       // usortert navnetabell
        snavn = Arrays.copyOf(snavn, nydimensjon);     // soretert navnetabell
        indeks = Arrays.copyOf(indeks, nydimensjon);   // indekstabell

        boolean[][] gammelgraf = graf;
        graf = new boolean[nydimensjon][nydimensjon];  // grafmatrisen

        for (int i = 0; i < antall; i++)
        {
            System.arraycopy(gammelgraf[i], 0, graf[i], 0, antall);
        }
    }

    public boolean leggInnNode(String nodenavn)     // ny node
    {
        if (navn == null || nodenavn.length() == 0)
            throw new IllegalArgumentException("Noden må ha et navn!");

        int rad = finn(nodenavn);    // raden i den sorterte navnetabellen
        if (rad >= 0) return false;  // finnes fra før!

        if (antall >= graf.length) utvid();  // sjekker om det er fullt

        rad = -(rad + 1);  // for å få innsettingspunktet

        for (int i = antall; i > rad; i--)
        {
            snavn[i] = snavn[i - 1];    // forskyver i snavn[]
            indeks[i] = indeks[i - 1];  // forskyver i infeks[]
        }

        snavn[rad] = nodenavn;      // på rett sortert plass i snavn[]
        navn[antall] = nodenavn;    // på neste ledige plass
        indeks[rad] = antall;       // antall blir indeks i navn[]

        antall++;  // en ny node

        return true;  // vellykket innlegging
    }

    public void leggInnKant(String franode, String tilnode)
    {
        if (franode.equals(tilnode)) throw    // sjekker om de er like
                new IllegalArgumentException(franode + " er lik " + tilnode + "!");

        int i = finn(franode);  // indeks i den sorterte navnetabellen
        if (i < 0) throw new NoSuchElementException(franode + " er ukjent!");

        int j = finn(tilnode);  // indeks i den sorterte navnetabellen
        if (j < 0) throw new NoSuchElementException(tilnode + " er ukjent!");

        int rad = indeks[i];       // raden i matrisen
        int kolonne = indeks[j];   // kolonnen i matrisen

        if (graf[rad][kolonne]) throw  // true for avkrysset
                new IllegalArgumentException("Kanten finnes fra før!");

        graf[rad][kolonne] = true;     // krysser av
    }

    public void leggInnKanter(String franode, String... tilnoder)
    {
        for (String tilnode : tilnoder) leggInnKant(franode, tilnode);
    }

    public MGraf(String url) throws IOException
    {
        this();

        BufferedReader inn = new BufferedReader  // leser fra fil
                (new InputStreamReader((new URL(url)).openStream()));

        String linje;
        while ((linje = inn.readLine()) != null)
        {
            String[] nodenavn = linje.split(" ");      // deler opp linjen

            leggInnNode(nodenavn[0]);                  // franoden kommer først

            for (int i = 1; i < nodenavn.length; i++)  // tilnodene kommer etterpå
            {
                leggInnNode(nodenavn[i]);                // navnet på tilnode
                leggInnKant(nodenavn[0], nodenavn[i]);   // kant franode -> tilnode
            }
        }

        inn.close();
    }

    public String kanterFra(String nodenavn)
    {
        int i = finn(nodenavn);
        if (i < 0) return null;
        int rad = indeks[i];

        StringJoiner sj = new StringJoiner(", ", "[", "]");

        for (int kolonne = 0; kolonne < antall; kolonne++)
            if (graf[rad][kolonne] == true) sj.add(navn[kolonne]);

        return sj.toString();
    }

    private class MGrafIterator implements Iterator<String>  // hører til MGraf
    {
        private int denne = 0;       // instansvariabel

        public boolean hasNext()     // sjekker om det er flere igjen
        {
            return denne < antall;     // sjekker verdien til denne
        }

        public String next()         // returnerer aktuell verdi
        {
            if (!hasNext())
                throw new NoSuchElementException("Tomt eller ingen verdier igjen!");
            return snavn[denne++];
        }
    }  // MGrafIterator

    public Iterator<String> iterator()
    {
        return new MGrafIterator();
    }

    public void skrivGraf(String filnavn) throws IOException
    {
        PrintWriter ut = new PrintWriter(filnavn);

        for (int i = 0; i < antall; i++)
        {
            ut.print(snavn[i]);

            int rad = indeks[i];
            for (int kolonne = 0; kolonne < antall; kolonne++)
            {
                if (graf[rad][kolonne]) ut.print(" " + navn[kolonne]);
            }
            ut.println();
        }

        ut.close();
    }

    public boolean erIsolert(String nodenavn)
    {
        int is = finn(nodenavn);  // indeks i sortert navnetabell
        if (is < 0) throw new NoSuchElementException(nodenavn + " er ukjent!");

        int k = indeks[is];  // tilhørende rad og kolonne i metrisen
        for (int i = 0; i < antall; i++) if (graf[k][i]) return false;  // rad k
        for (int j = 0; j < antall; j++) if (graf[j][k]) return false;  // kolonne k

        return true;
    }

    public boolean erKant(String franode, String tilnode)
    {
        int i = finn(franode);  // indeks i sortert navnetabell
        if (i < 0) throw new IllegalArgumentException(franode + " er ukjent!");

        int j = finn(tilnode);  // indeks i sortert navnetabell
        if (j < 0) throw new IllegalArgumentException(franode + " er ukjent!");

        return graf[indeks[i]][indeks[j]];
    }

    public int grad(String nodenavn)
    {
        int i = finn(nodenavn);  // indeks i sortert navnetabell
        if (i < 0) throw new IllegalArgumentException(nodenavn + " er ukjent!");

        int rad = indeks[i];
        int grad = 0;

        for (boolean harKryss : graf[rad]) if (harKryss) grad++;

        return grad;
    }

    public String[] kantTabellFra(String node)
    {
        int i = finn(node);  // indeks i sortert navnetabell
        if (i < 0) throw new IllegalArgumentException(node + " er ukjent!");

        int rad = indeks[i];

        String[] tabell = new String[antall];

        int ant = 0;
        for (int kolonne = 0; kolonne < antall; kolonne++)
        {
            if (graf[rad][kolonne]) tabell[ant++] = navn[kolonne];
        }

        return Arrays.copyOf(tabell, ant);
    }

    public String kanterTil(String node)
    {
        int i = finn(node);  // indeks i sortert navnetabell
        if (i < 0) throw new IllegalArgumentException(node + " er ukjent!");

        int kolonne = indeks[i];

        StringJoiner sj = new StringJoiner(", ", "[", "]");

        for (int rad = 0; rad < antall; rad++)
        {
            if (graf[rad][kolonne]) sj.add(navn[rad]);
        }

        return sj.toString();
    }

    public String[] kantTabellTil(String node)
    {
        int i = finn(node);  // indeks i sortert navnetabell
        if (i < 0) throw new IllegalArgumentException(node + " er ukjent!");

        int kolonne = indeks[i];

        String[] tabell = new String[antall];

        int ant = 0;
        for (int rad = 0; rad < antall; rad++)
        {
            if (graf[rad][kolonne]) tabell[ant++] = navn[rad];
        }

        return Arrays.copyOf(tabell, ant);
    }

    public void dybdeFørstPretraversering(String startnode, Consumer<String> oppgave)
    {
        int is = finn(startnode);  // indeks i den sorterte navnetabellen
        if (is < 0) throw new IllegalArgumentException(startnode + " er ukjent!");

        int i = indeks[is];  // indeks i matrisen

        boolean besøkt[] = new boolean[antall];      // hjelpetabell
        dybdeFørstPre(i, besøkt, oppgave);  // kaller den rekursive metoden
    }

    private void                                   // rekursiv hjelpemetode
    dybdeFørstPre(int i, boolean[] besøkt, Consumer<String> oppgave)
    {
        besøkt[i] = true;                           // noden er besøkt
        oppgave.accept(navn[i]);                    // oppgaven utføres

        for (int j = 0; j < antall; j++)            // kantene til noden
        {
            if (graf[i][j] && !besøkt[j])
                dybdeFørstPre(j, besøkt, oppgave);  // rekursivt kall
        }
    }

    public void breddeFørstTraversering(String startnode, Consumer<String> oppgave)
    {
        int is = finn(startnode);  // indeks i den sorterte navnetabellen
        if (is < 0) throw new IllegalArgumentException(startnode + " er ukjent!");

        int i = indeks[is];  // indeks i den usorterte navnetabellen

        boolean[] besøkt = new boolean[antall];    // hjelpetabell

        Queue<Integer> kø = new ArrayDeque<>();    // oppretter en kø
        besøkt[i] = true;                          // node i besøkes først
        kø.offer(i);                               // legger i i køen

        while (!kø.isEmpty())                      // så lenge køen ikke er tom
        {
            i = kø.poll();                           // tar ut en node fra køen
            oppgave.accept(navn[i]);                 // utfører oppgaven

            for (int j = 0; j < antall; j++)         // nodene det går en kant til
            {
                if (graf[i][j] && !besøkt[j])     // denne er ikke besøkt
                {
                    besøkt[j] = true;                    // nå er den besøkt
                    kø.offer(j);                         // legger noden i køen
                }
            }
        }
    }

}  // MGraf