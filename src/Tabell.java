import eksempelklasser.Komperator;

import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Random;

public class Tabell {
    public static void snu(int[] a, int v, int h)  // snur intervallet a[v:h]
    {
        while (v < h) bytt(a, v++, h--);
    }

    public static void snu(int[] a, int v)  // snur fra og med v og ut tabellen
    {
        snu(a, v, a.length - 1);
    }

    public static void snu(int[] a)  // snur hele tabellen
    {
        snu(a, 0, a.length - 1);
    }

    public static boolean nestePermutasjon(int[] a) {
        int i = a.length - 2;                    // i starter nest bakerst
        while (i >= 0 && a[i] > a[i + 1]) i--;   // går mot venstre
        if (i < 0) return false;                 // a = {n, n-1, . . . , 2, 1}

        int j = a.length - 1;                    // j starter bakerst
        while (a[j] < a[i]) j--;                 // stopper når a[j] > a[i]
        bytt(a, i, j);
        snu(a, i + 1);               // bytter og snur

        return true;                             // en ny permutasjon
    }

    public static void bytt(int[] c, int i, int j) {
        int temp = c[i];
        c[i] = c[j];
        c[j] = temp;
    }

    public static int[] selectionsort(int[] a) {
        //int n = a.length;
        for (int i = 0; i < a.length - 1; i++) {
            int minIndeks = i;
            for (int j = i + 1; j < a.length; j++) {
                if (a[j] < a[minIndeks]) {
                    minIndeks = j;
                }
            }
            int temp = a[i];
            a[i] = a[minIndeks];
            a[minIndeks] = temp;

        }
        return a;
    }

    public static int[] randPerm(int n)  // en effektiv versjon
    {
        Random r = new Random();         // en randomgenerator
        int[] a = new int[n];            // en tabell med plass til n tall

        Arrays.setAll(a, i -> i + 1);    // legger inn tallene 1, 2, . , n

        for (int k = n - 1; k > 0; k--)  // løkke som går n - 1 ganger
        {
            int i = r.nextInt(k + 1);        // en tilfeldig tall fra 0 til k
            bytt(a, k, i);                   // bytter om
        }

        return a;                        // permutasjonen returneres
    }

    public static void utvalgssortering(int[] a, int fra, int til) {
        if (fra < til) {
            for (int i = fra; i < til - 1; i++) {
                int minIndeks = i;
                for (int j = i + 1; j < til; j++) {
                    if (a[j] < a[minIndeks]) {
                        minIndeks = j;
                    }
                }
                int temp = a[i];
                a[i] = a[minIndeks];
                a[minIndeks] = temp;

            }
        } else if (fra > til) {
            System.out.println("feil i input");
        }
    }

    public static <T> void utvalgssortering(T[] a, Comparator<? super T> c) {
        for (int i = 0; i < a.length; i++) {
            bytt(a, i, min(a, i, a.length, c));
        }
    }

    public static int usortertsøk(int[] a, int verdi)  // tabell og søkeverdi
    {
        int sist = a.length - 1;
        int temp = a[sist];
        a[sist] = verdi;
        for (int i = 0; i < a.length; i++)  // går gjennom tabellen
            if (verdi == a[i]) {
                a[sist] = temp;
                if (i == sist) {
                    return verdi == temp ? sist : -1;
                } else return i; // verdi funnet - har indeks i
            }
        return -1;      // verdi ikke funnet
    }

    public static int lineærsøk(int[] a, int k, int verdi) // legges i class Tabell
    {
        if (k < 1) {
            throw new IllegalArgumentException("Kå må være større enn 0");
        }
        int j = k - 1;
        for (; j < a.length && verdi > a[j]; j += k) ;

        int i = j - k + 1;
        for (; i < a.length && verdi > a[i]; i++) ;
        if (i < a.length && a[i] == verdi) {
            return i;
        } else
            return -(i + 1);
    }

    public static int kvadratrotsøk(int[] a, int verdi) {
        int k = (int) Math.sqrt(a.length);
        int j = k - 1;

        for (; j < a.length && verdi > a[j]; j += k) ;

        int i = j - k + 1;
        for (; i < a.length && verdi > a[i]; i++) ;
        if (i < a.length && a[i] == verdi) {
            return i;
        } else
            return -(i + 1);
    }

    public static <T> int binærsøk(T[] a, int fra, int til, T verdi, Comparator<? super T> c) {
        Tabell.fratilKontroll(a.length, fra, til);  // se Programkode 1.2.3 a)
        int v = fra, h = til - 1;  // v og h er intervallets endepunkter

        while (v <= h)    // fortsetter så lenge som a[v:h] ikke er tom
        {
            int m = (v + h) / 2;      // heltallsdivisjon - finner midten
            T midtverdi = a[m];   // hjelpevariabel for midtverdien

            if (verdi == midtverdi) return m;          // funnet
            else if (c.compare(verdi, midtverdi) > 0) {
                v = m + 1;     // verdi i a[m+1:h]
            } else h = m - 1;                           // verdi i a[v:m-1]
        }

        return -(v + 1);    // ikke funnet, v er relativt innsettingspunkt
    }

    public static <T> int binærsøk(T[] a, T verdi, Comparator<? super T> c)  // søker i hele a
    {
        return binærsøk(a, 0, a.length, verdi, c);  // bruker metoden over
    }


    public static <T> int binærsøk2(int[] a, int fra, int til, int verdi) {
        Tabell.fratilKontroll(a.length, fra, til);  // se Programkode 1.2.3 a)
        int v = fra, h = til - 1;    // v og h er intervallets endepunkter

        while (v <= h)  // fortsetter så lenge som a[v:h] ikke er tom
        {
            int m = (v + h) / 2;     // heltallsdivisjon - finner midten
            int midtverdi = a[m];  // hjelpevariabel for  midtverdien

            if (verdi > midtverdi) v = m + 1;        // verdi i a[m+1:h]
            else if (verdi < midtverdi) h = m - 1;   // verdi i a[v:m-1]
            else return m;                           // funnet
        }

        return -(v + 1);   // ikke funnet, v er relativt innsettingspunkt
    }

    // 3. versjon av binærsøk - returverdier som for Programkode 1.3.6 a)
    public static int binærsøk3(int[] a, int fra, int til, int verdi) {
        Tabell.fratilKontroll(a.length, fra, til);  // se Programkode 1.2.3 a)
        int v = fra, h = til - 1;  // v og h er intervallets endepunkter

        while (v < h)  // obs. må ha v < h her og ikke v <= h
        {
            int m = (v + h) / 2;  // heltallsdivisjon - finner midten

            if (verdi > a[m]) v = m + 1;   // verdi må ligge i a[m+1:h]
            else h = m;                   // verdi må ligge i a[v:m]
        }
        if (h < v || verdi < a[v]) return -(v + 1);  // ikke funnet
        else if (verdi == a[v]) return v;            // funnet
        else return -(v + 2);                       // ikke funnet
    }

    public static void fratilKontroll(int tablengde, int fra, int til) {
        if (fra < 0)                                  // fra er negativ
            throw new ArrayIndexOutOfBoundsException
                    ("fra(" + fra + ") er negativ!");

        if (til > tablengde)                          // til er utenfor tabellen
            throw new ArrayIndexOutOfBoundsException
                    ("til(" + til + ") > tablengde(" + tablengde + ")");

        if (fra > til)                                // fra er større enn til
            throw new IllegalArgumentException
                    ("fra(" + fra + ") > til(" + til + ") - illegalt intervall!");
    }

    int[] a = {3, 5, 6, 10, 10, 11, 13, 14, 16, 20, 0, 0, 0, 0, 0};  // en tabell
    int antall = 10;                                   // antall verdier


    /*public static void copy(int[] a, int antall) {
        if (antall >= a.length) throw new IllegalStateException("Tabellen er full");

        int nyverdi = 10;                                  // ny verdi
        int k = Tabell.binærsøk(a, 0, antall, nyverdi);    // søker i a[0:antall>
        if (k < 0) k = -(k + 1);                           // innsettingspunkt

        System.arraycopy(a, k, a, k + 1, antall - k);

        a[k] = nyverdi;                                    // legger inn
        antall++;                                          // øker antallet

        Tabell.skrivln(a, 0, antall);  // Se Oppgave 4 og 5 i Avsnitt 1.2.2
    }  */


    public static void skrivln(int[] a, int fra, int til) {
        for (int i = fra; i < til; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println("");
    }

    public static void skrivln(int[] a) {
        skrivln(a, 0, a.length);
    }

    public static int maks(double[] a)     // legges i class Tabell
    {
        int m = 0;                           // indeks til største verdi
        double maksverdi = a[0];             // største verdi

        for (int i = 1; i < a.length; i++)
            if (a[i] > maksverdi) {
                maksverdi = a[i];     // største verdi oppdateres
                m = i;                // indeks til største verdi oppdaters
            }
        return m;     // returnerer posisjonen til største verdi
    }


    public static int maks(int[] a, int fra, int til) {
        if (a == null) {
            throw new NullPointerException("Tabellen eksisterer ikke");
        }
        fratilKontroll(a.length, fra, til);
        if (fra == til) {
            throw new NoSuchElementException
                    ("fra(" + fra + ") = til(" + til + ") - tomt tabellintervall!");
        }

        int m = fra;
        int maks = a[fra];

        for (int i = fra + 1; i < til; i++) {
            if (a[i] > maks) {
                maks = a[i];
            }
        }
        return maks;
    }

    public static <T> int maks(T[] a, int fra, int til, Komperator.Komparator<? super T> c) {
        fratilKontroll(a.length, fra, til);

        if (fra == til) throw new NoSuchElementException
                ("fra(" + fra + ") = til(" + til + ") - tomt tabellintervall!");

        int m = fra;                // indeks til største verdi
        T maksverdi = a[fra];       // største verdi

        for (int i = fra + 1; i < til; i++)   // går gjennom intervallet
        {
            if (c.compare(a[i], maksverdi) > 0)  // bruker komparatoren
            {
                maksverdi = a[i];     // største verdi oppdateres
                m = i;                // indeks til største verdi oppdateres
            }
        }
        return m;                 // posisjonen til største verdi

    }  // maks

    public static int maks(int[] a)  // bruker hele tabellen
    {
        return maks(a, 0, a.length);     // kaller metoden over
    }

    public static int maks(char[] a)     // legges i class Tabell
    {
        int m = 0;                           // indeks til største verdi
        char maksverdi = a[0];             // største verdi

        for (int i = 1; i < a.length; i++)
            if (a[i] > maksverdi) {
                maksverdi = a[i];     // største verdi oppdateres
                m = i;                // indeks til største verdi oppdaters
            }
        return m;     // returnerer posisjonen til største verdi
    }

    public static int maks(Integer[] a) {
        int m = 0;                          // indeks til største verdi
        Integer maksverdi = a[0];           // største verdi

        for (int i = 1; i < a.length; i++)
            if (a[i].compareTo(a[maksverdi]) > 0) {
                maksverdi = a[i];
                m = i;
            }
        return m;
    }

    public static <T> int maks(T[] a, Comparator<? super T> c) {
        return maks(a, 0, a.length, c);  // kaller metoden under
    }

    public static <T> int maks(T[] a, int fra, int til, Comparator<? super T> c) {
        fratilKontroll(a.length, fra, til);

        if (fra == til) throw new NoSuchElementException
                ("fra(" + fra + ") = til(" + til + ") - tomt tabellintervall!");

        int m = fra;                // indeks til største verdi
        T maksverdi = a[fra];       // største verdi

        for (int i = fra + 1; i < til; i++)   // går gjennom intervallet
        {
            if (c.compare(a[i], maksverdi) > 0)  // bruker komparatoren
            {
                maksverdi = a[i];     // største verdi oppdateres
                m = i;                // indeks til største verdi oppdateres
            }
        }
        return m;                 // posisjonen til største verdi

    }  // maks

    public static <T extends Comparable<? super T>> void innsettingssortering(T[] a) {
        for (int i = 1; i < a.length; i++)  // starter med i = 1
        {
            T verdi = a[i];        // verdi er et tabellelemnet
            int j = i - 1;        // j er en indeks
            // sammenligner og forskyver:
            for (; j >= 0 && verdi.compareTo(a[j]) < 0; j--) a[j + 1] = a[j];

            a[j + 1] = verdi;      // j + 1 er rett sortert plass
        }
    }

    public static <T> void innsettingssortering(T[] a, Komperator.Komparator<? super T> c) {
        for (int i = 1; i < a.length; i++)  // starter med i = 1
        {
            T verdi = a[i];        // verdi er et tabellelemnet
            int j = i - 1;        // j er en indeks

            // sammenligner og forskyver:
            for (; j >= 0 && c.compare(verdi, a[j]) < 0; j--) a[j + 1] = a[j];

            a[j + 1] = verdi;      // j + 1 er rett sortert plass
        }
    }

    public static void skriv(Object[] a, int fra, int til) {
        String ut = "";
        for (int i = 0; i < til; i++) {
            ut += a[i].toString() + " ";

        }
        System.out.print(ut);
    }

    public static void skriv(Object[] a) {
        skriv(a, 0, a.length);
    }

    public static void skrivln(Object[] a, int fra, int til) {
        skriv(a, fra, til);
        System.out.println();
    }

    public static void skrivln(Object[] a) {
        skrivln(a, 0, a.length);
    }

    public static <T> void bytt(T[] a, int i, int j) {
        T temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static Integer[] randPermInteger(int n) {
        Integer[] a = new Integer[n];               // en Integer-tabell
        Arrays.setAll(a, i -> i + 1);               // tallene fra 1 til n

        Random r = new Random();   // hentes fra  java.util

        for (int k = n - 1; k > 0; k--) {
            int i = r.nextInt(k + 1);  // tilfeldig tall fra [0,k]
            bytt(a, k, i);             // bytter om
        }
        return a;  // tabellen med permutasjonen returneres
    }


    public static <T> void innsettingssortering(T[] a, Comparator<? super T> c) {
        for (int i = 1; i < a.length; i++)  // starter med i = 1
        {
            T verdi = a[i];        // verdi er et tabellelemnet
            int j = i - 1;        // j er en indeks

            // sammenligner og forskyver:
            for (; j >= 0 && c.compare(verdi, a[j]) < 0; j--) a[j + 1] = a[j];

            a[j + 1] = verdi;      // j + 1 er rett sortert plass
        }
    }

    public static <T> int min(T[] a, int fra, int til, Comparator<? super T> c) {
        if (fra < 0 || til > a.length || fra >= til)
            throw new IllegalArgumentException("Illegalt intervall!");

        int m = fra;           // indeks til minste verdi i a[fra:til>
        T minverdi = a[fra];   // minste verdi i a[fra:til>

        for (int i = fra + 1; i < til; i++) {
            if (c.compare(a[i], minverdi) < 0) {
                m = i;               // indeks til minste verdi oppdateres
                minverdi = a[m];     // minste verdi oppdateres
            }
        }

        return m;  // posisjonen til minste verdi i a[fra:til>
    }

    public static <T> int min(T[] a, Comparator<? super T> c)  // bruker hele tabellen
    {
        return min(a, 0, a.length, c);     // kaller metoden over
    }

    public static <T> int parter(T[] a, int v, int h, T skilleverdi, Comparator<? super T> c) {
        while (v <= h && c.compare(a[v], skilleverdi) < 0)
            v++;
        while (v <= h && c.compare(skilleverdi, a[h]) <= 0)
            h--;

        while (true) {
            if (v < h)
                Tabell.bytt(a, v++, h--);
            else return v;
            while (c.compare(a[v], skilleverdi) < 0)
                v++;
            while (c.compare(skilleverdi, a[h]) <= 0)
                h--;
        }
    }

    public static <T> int parter(T[] a, T skilleverdi, Comparator<? super T> c) {
        return parter(a, 0, a.length - 1, skilleverdi, c);  // kaller metoden over
    }

    public static <T> int sParter(T[] a, int v, int h, int k, Comparator<? super T> c) {
        if (v < 0 || h >= a.length || k < v || k > h) throw new
                IllegalArgumentException("Ulovlig parameterverdi");

        bytt(a, k, h);   // bytter - skilleverdien a[k] legges bakerst
        int p = parter(a, v, h - 1, a[h], c);  // partisjonerer a[v:h-1]
        bytt(a, p, h);   // bytter for å få skilleverdien på rett plass

        return p;    // returnerer posisjonen til skilleverdien
    }

    public static <T> int sParter(T[] a, int k, Comparator<? super T> c)   // bruker hele tabellen
    {
        return sParter(a, 0, a.length - 1, k, c); // v = 0 og h = a.lenght-1
    }

    private static <T> void kvikksortering(T[] a, int v, int h, Comparator<? super T> c) {
        if (v >= h) return;  // hvis v = h er a[v:h] allerede sortert

        int p = sParter(a, v, h, (v + h) / 2, c);
        kvikksortering(a, v, p - 1, c);
        kvikksortering(a, p + 1, h, c);
    }

    public static <T> void kvikksortering(T[] a, Comparator<? super T> c) // sorterer hele tabellen
    {
        kvikksortering(a, 0, a.length - 1, c);
    }

    private static <T> void flett(T[] a, T[] b, int fra, int m, int til, Comparator<? super T> c)
    {
        int n = m - fra;   // antall elementer i a[fra:m>
        System.arraycopy(a,fra,b,0,n); // kopierer a[fra:m> over i b[0:n>

        int i = 0, j = m, k = fra;     // løkkevariabler og indekser

        while (i < n && j < til)  // fletter b[0:n> og a[m:til>, legger
            a[k++] = c.compare(b[i],a[j]) <= 0 ? b[i++] : a[j++];  // resultatet i a[fra:til>

        while (i < n) a[k++] = b[i++];  // tar med resten av b[0:n>
    }

    public static <T> void flettesortering(T[] a, T[] b, int fra, int til, Comparator<? super T> c)
    {
        if (til - fra <= 1) return;     // a[fra:til> har maks ett element

        int m = (fra + til)/2;          // midt mellom fra og til

        flettesortering(a,b,fra,m,c);   // sorterer a[fra:m>
        flettesortering(a,b,m,til,c);   // sorterer a[m:til>

        flett(a,b,fra,m,til,c);         // fletter a[fra:m> og a[m:til>
    }

    public static <T> void flettesortering(T[] a, Comparator<? super T> c)
    {
        T[] b = Arrays.copyOf(a, a.length/2);
        flettesortering(a,b,0,a.length,c);  // kaller metoden over
    }
}