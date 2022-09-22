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

    public static int binærsøk(int[] a, int fra, int til, int verdi) {
        Tabell.fratilKontroll(a.length, fra, til);  // se Programkode 1.2.3 a)
        int v = fra, h = til - 1;  // v og h er intervallets endepunkter

        while (v <= h)    // fortsetter så lenge som a[v:h] ikke er tom
        {
            int m = (v + h) / 2;      // heltallsdivisjon - finner midten
            int midtverdi = a[m];   // hjelpevariabel for midtverdien

            if (verdi == midtverdi) return m;          // funnet
            else if (verdi > midtverdi) v = m + 1;     // verdi i a[m+1:h]
            else h = m - 1;                           // verdi i a[v:m-1]
        }

        return -(v + 1);    // ikke funnet, v er relativt innsettingspunkt
    }

    public static int binærsøk(int[] a, int verdi)  // søker i hele a
    {
        return binærsøk(a, 0, a.length, verdi);  // bruker metoden over
    }

    // 2. versjon av binærsøk - returverdier som for Programkode 1.3.6 a)
    public static int binærsøk2(int[] a, int fra, int til, int verdi) {
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


    public static void copy(int[] a, int antall) {
        if (antall >= a.length) throw new IllegalStateException("Tabellen er full");

        int nyverdi = 10;                                  // ny verdi
        int k = Tabell.binærsøk(a, 0, antall, nyverdi);    // søker i a[0:antall>
        if (k < 0) k = -(k + 1);                           // innsettingspunkt

        System.arraycopy(a, k, a, k + 1, antall - k);

        a[k] = nyverdi;                                    // legger inn
        antall++;                                          // øker antallet

        Tabell.skrivln(a, 0, antall);  // Se Oppgave 4 og 5 i Avsnitt 1.2.2
    }

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

    public static void bytt(Object[] a, int i, int j) {
        Object temp = a[i];
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

    public static int rekursivA(int n)           // n må være et ikke-negativt tall
    {
        if (n == 0) return 1;              // a0 = 1
        else if (n == 1) return 2;         // a1 = 2
        else return 2 * rekursivA(n - 1) + 3 * rekursivA(n - 2);   // to rekursive kall
    }

    public static int iterativA(int n) {
        int sum2 = 1;
        int sum1 = 2;
        int temp;
        if (n == 0) {
            return 1;
        } else if (n == 1) {
            return 2;
        } else {
            for (int i = 2; i <= n; i++) {
                if (i == n) {
                    int sum = 2 * sum1 + 3 * sum2;
                    return sum;
                }
                temp = sum1;
                sum1 = 2 * sum1 + 3 * sum2;
                sum2 = temp;
            }
        }
        return -1;
    }

    public static int rekursivTverrsum(int n)              // n må være >= 0
    {
        if (n < 10) return n;                        // kun ett siffer
        else return rekursivTverrsum(n / 10) + (n % 10);     // metoden kalles
    }

    public static int iterativTverrsum(int n) {
        int tverrsum = 0;
        while (n > 10) {
            tverrsum += n % 10;
            n = n / 10;
        }
        return tverrsum + n;
    }

    public static int sifferrot(int n) {
        while (n > 10) {
            n = iterativTverrsum(n);
            sifferrot(n);
        }
        return n;
    }

    public static int kvadratRekursiv (int n){
        if (n==0){
            return n;
        }
           else
               return n*n+kvadratRekursiv(n-1);
    }

    public static int sum(int k, int n){
        if(k==n){
            return k;
        }
        int m = (k+n)/2;
        return sum(k,m)+sum(m+1,n);
    }

    public static int størsteRekursiv(int[] a, int v, int h){
        if (v==h){
            return v;
        }
        int m = (v+h)/2;
        int mv = størsteRekursiv(a, v, m);
        int mh = størsteRekursiv(a, m+1, h);

        return a[mv]>a[mh] ? mv : mh;
    }

    public static int rekursivFakultet(int n){
        if (n==1){
            return n;
        }
        return n*rekursivFakultet(n-1);
    }

    public static int fib(int n)         // det n-te Fibonacci-tallet
    {
        if (n <= 1) return n;              // fib(0) = 0, fib(1) = 1
        else return fib(n - 1) + fib(n - 2);   // summen av de to foregående
    }
    public static int tverrsum(int n)
    {
        System.out.println("tverrsum(" + n + ") starter!");
        int sum = (n < 10) ? n : tverrsum(n / 10) + (n % 10);
        System.out.println("tverrsum(" + n + ") er ferdig!");
        return sum;
    }

    public static int euklid(int a, int b)
    {
        System.out.println("euklid(" + a + "," + b + ") starter!");
        if (b == 0)
        {
            System.out.println("euklid(" + a + "," + b + ") er ferdig!");
            return a;
        }
        int r = a % b;            // r er resten
        int k = euklid(b,r);       // rekursivt kall
        System.out.println("euklid(" + a + "," + b + ") er ferdig!");
        return k;
    }
}

