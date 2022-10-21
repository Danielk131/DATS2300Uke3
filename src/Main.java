import eksempelklasser.*;
import hjelpeklasser.EnkeltLenketListe;
import hjelpeklasser.Liste;
import hjelpeklasser.Tabelliste;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.StringJoiner;

public class Main {
    public static void main(String[] args) {
        /*int[] a = Tabell.randPerm(10);
        //Tabell.utvalgssortering(a, 0, 5);
        //for (int i = 0; i < a.length; i++) {
        //    System.out.println(a[i]);
        //}
        //int [] a = {3,8, 10, 12, 14, 16, 21, 24, 30, 32, 34, 37, 40};
        //int svar = Tabell.lineærsøk(a, 10, 40);
        //int [] a = {1, 3, 5, 4, 6, 7, 7, 7, 7, 8, 9, 10, 10 ,12, 15, 15, 15};
        //int svar = Tabell.binærsøk3(a, 0, a.length-1, 15);
        //System.out.println(svar);
        //int[] a = {5,2,7,3,9,1,8,4,6};
        double[] d = {5.7, 3.14, 7.12, 3.9, 6.5, 7.1, 7.11};
        String[] s = {"Sohil", "Per", "Thanh", "Fatima", "Kari", "Jasmin"};
        char[] c = {'A', 'C', 'B', 'S'};

        // int k = Tabell.maks(a);     // posisjonen til den største i a
       /* int l = Tabell.maks(d);     // posisjonen til den største i d
        int m = Tabell.maks(s);     // posisjonen til den største i s
        int k = Tabell.maks(c);
        System.out.println( c[k] + "  " + d[l] + "  " + s[m]);
        String[] s2 = {"Sohil","Per","Thanh","Fatima","Kari","Jasmin"};
        int k = Tabell.maks(s2);        // hvilken maks-metode?
        System.out.println(s2[k]);      // Utskrift:  Thanh
        String[] s1 = {"Per","Kari","Ole","Anne","Ali","Eva"};
        Tabell.innsettingssortering(s1);
        System.out.println(Arrays.toString(s1));  // [Ali, Anne, Eva, Kari, Ole, Per]
        Integer[] a = Tabell.randPermInteger(20);
        System.out.println(Arrays.toString(a));
        // En mulig utskrift: [7, 1, 8, 2, 10, 3, 4, 6, 5, 9]

        Tabell.innsettingssortering(a);
        System.out.println(Arrays.toString(a));
        // Utskrift: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]

        double[] d1 = {5.7,3.14,7.12,3.9,6.5,7.1,7.11};
        Double [] dd = new Double[d.length];
        for (int i = 0; i<d.length; i++){
            dd[i]=d1[i];
        }

        Tabell.innsettingssortering(dd);
        System.out.println(Arrays.toString(dd));

        int[] a = {5,2,7,3,9,1,8,10,4,6};          // en int-tabell
        Heltall[] h = new Heltall[a.length];       // en Heltall-tabell

        for (int i = 0; i < h.length; i++) h[i] = new Heltall(a[i]);
        Tabell.innsettingssortering(h);           // generisk sortering
        System.out.println(Arrays.toString(h));// utskrift

        Heltall x = new Heltall(3), y = new Heltall(3);  // x og y er like
        System.out.println(x.compareTo(y) + "  " + x.equals(y));

        Person[] p = new Person[5];                   // en persontabell

        p[0] = new Person("Kari", "Svendsen");         // Kari Svendsen
        p[1] = new Person("Boris", "Zukanovic");       // Boris Zukanovic
        p[2] = new Person("Ali", "Kahn");              // Ali Kahn
        p[3] = new Person("Azra", "Zukanovic");        // Azra Zukanovic
        p[4] = new Person("Kari", "Pettersen");        // Kari Pettersen

        int m = Tabell.maks(p);                       // posisjonen til den største
        System.out.println(p[m] + " er størst");      // skriver ut den største

        //Tabell.innsettingssortering(p);// generisk sortering
        Arrays.sort(p);
        System.out.println(Arrays.toString(p));       // skriver ut sortert

        for (Studium st : Studium.values()) {
            System.out.println(st.toString() + " (" + st.name() + ")");
        }


        for (Maned ma : Maned.høst()) {
            System.out.println(ma.toString() + " (" + ma.name() + ") " + ma.mndnr());
        }

        // september (SEP) 9
        // oktober (OKT) 10

        // Ingeniørfag - data (Data)
        // Informasjonsteknologi (IT)
        // Anvendt datateknologi (Anvendt)
        // Enkeltemnestudent (Enkeltemne)*/
/*
        Student[] s = new Student[7];  // en Studenttabell

        s[0] = new Student("Kari", "Svendsen", Studium.Data);    // Kari Svendsen
        s[1] = new Student("Boris", "Zukanovic", Studium.IT);    // Boris Zukanovic
        s[2] = new Student("Ali", "Kahn", Studium.Anvendt);      // Ali Kahn
        s[3] = new Student("Azra", "Zukanovic", Studium.IT);     // Azra Zukanovic
        s[4] = new Student("Kari", "Pettersen", Studium.Data);   // Kari Pettersen
        s[5] = new Student("Hans", "Hansen", Studium.Elektro);
        s[6] = new Student("Henrietter", "Klausen", Studium.Enkeltemne);

        Tabell.innsettingssortering(s);                     // Programkode 1.4.2 e)
        for (
                Student t : s)
            System.out.println(t);

        // Utskrift:
        // Ali Kahn Anvendt
        // Kari Pettersen Data
        // Kari Svendsen Data
        // Azra Zukanovic IT
        // Boris Zukanovic IT


            Person[] p = new Person[5];                       // en persontabell
            p[0] = new Person("Kari", "Svendsen");            // Kari Svendsen
            p[1] = new Person("Boris", "Zukanovic");          // Boris Zukanovic
            p[2] = new Person("Ali", "Kahn");                 // Ali Kahn
            p[3] = new Person("Azra", "Zukanovic");           // Azra Zukanovic
            p[4] = new Person("Kari", "Pettersen");           // Kari Pettersen

            class FornavnKomparator implements Komperator.Komparator<Person>
            {
                public int compare(Person p1, Person p2)        // to personer
                {
                    return p1.fornavn().compareTo(p2.fornavn());  // sammenligner fornavn
                }
            }

            class etternavnKomperator implements Komperator.Komparator<Person>{

                @Override
                public int compare(Person p1, Person p2) {
                    return p1.etternavn().compareTo(p2.etternavn());
                }
            }

        class studenKomperator implements Komperator.Komparator<Student>
        {
            public int compare(Student s1, Student s2)        // to personer
            {
                int ut = s1.studium().compareTo(s2.studium());
                if (ut!=0){
                    return ut;
                }
                ut=s1.fornavn().compareTo(s2.fornavn());
                if(ut!=0){
                    return ut;
                }
                ut=s1.etternavn().compareTo(s2.etternavn());
                return ut;
            }
        }
*/
            /*Komperator.Komparator<Person> c = (Komperator.Komparator<Person>) new FornavnKomparator();   // en instans av klassen
            Tabell.innsettingssortering(p, c);                // se Programkode 1.4.6 b)

            System.out.println(Arrays.toString(p));           // Utskrift av tabellen p
            // [Ali Kahn, Azra Zukanovic, Boris Zukanovic, Kari Svendsen, Kari Pettersen]

        Tabell.innsettingssortering(p, (p1,p2) -> p1.fornavn().compareTo(p2.fornavn()));
        System.out.println(Arrays.toString(p));

        Tabell.innsettingssortering(s, (s1, s2) -> s1.studium().compareTo(s2.studium()));
        System.out.println(Arrays.toString(s));

        Double[] d = {5.7,3.14,7.12,3.9,6.5,7.1,7.11};
        Tabell.innsettingssortering(d, Komperator.Komparator.naturligOrden());
        System.out.println(Arrays.toString(d));
        Tabell.innsettingssortering(d, Komperator.Komparator.omvendtOrden());
        System.out.println(Arrays.toString(d));

        Boolean[] b = {false, true, true, false, false, true, false, true};
        Tabell.innsettingssortering(b, Komperator.Komparator.naturligOrden());
        System.out.println(Arrays.toString(b));


        Tabell.innsettingssortering(p, Komperator.Komparator.orden(Person::etternavn));
        System.out.println(Arrays.toString(p));

        String[] s2 = {"Lars","Anders","Bodil","Kari","Per","Berit"};



        Tabell.innsettingssortering(s2, (x,y) -> y.length()-x.length());
        Tabell.innsettingssortering(s2, Komperator.Komparator.orden(x -> -x.length()));
        System.out.println(Arrays.toString(s2));*/
/*
        String [] str = {"21","18","8","13","20","6","16","25","3","10"};
        Tabell.innsettingssortering(str, Komperator.Komparator.orden(String::length).deretter(x->x));
        System.out.println(Arrays.toString(str));


        String[] st = {"Sohil","Per","Thanh","Ann","Kari","Jon"};       // String-tabell
        Comparator<String> c =  Comparator.comparing(String::length);  // etter lengde
        Tabell.innsettingssortering(st, c.thenComparing(x -> x));       // vanlig orden
        System.out.println(Arrays.toString(s));
        // skriver ut


        System.out.println( "Iterativ : " + Tabell.iterativA(10));
        System.out.println("Rekursiv : " + Tabell.rekursivA(10));

        System.out.println("Iterativ " + Tabell.iterativTverrsum(4231));
        System.out.println("Rekursiv " + Tabell.rekursivTverrsum(4231));

        System.out.println("Sifferrot " + Tabell.sifferrot(956847));
        System.out.println("Kvadratrekursiv " +Tabell.kvadratRekursiv(5));

        System.out.println("SPlitt og herks : " + Tabell.sum(1, 5));

        int[] a = {1, 2, 6, 3, 7, 9, 3};
        System.out.println(Tabell.størsteRekursiv(a, 0, 2));
        int iterativ = Tabell.størsteRekursiv(a, 0, 3);*/

       /* int svar = Tabell.rekursivFakultet(5);
        System.out.println(Tabell.rekursivFakultet(5));

        System.out.println(Tabell.euklid(1, 5));
        int [] a = {5,2,4,3,1,6,7,8,9,10};
        Tabell.kvikksortering0(a,0,9);
        System.out.println(Arrays.toString(a)); */

        /*String[] s2 = {"yo", "hei", "morn", "kaaaaa"};
        Tabelliste <String> s3 = new Tabelliste<>(s2);

        System.out.println("Antall: " + s3.antall());
        System.out.println(s3.indeksTil("hei"));
        System.out.println(s3.inneholder("yo"));
        System.out.println(s3.hent(3));
        s3.oppdater(1, "ityf");
        System.out.println(s3);
        s3.fjernHvis(x -> x.equals("yo"));
        System.out.println(s3);
        s3.forEach(x-> System.out.println(x + ""));
        */
       /* String[] s = {"Per","Kari","Ole"};

        Liste<String> liste = new Tabelliste<>();

        for (String navn : s) liste.leggInn(navn);
        System.out.println(liste);
        // Utskrift: [Per, Kari, Ole]

        Iterator<String> i = liste.iterator();     // oppretter en iterator
        System.out.println(i.next());              // den første i listen

        Iterator<String> i1 = liste.iterator();     // oppretter en iterator i
        Iterator<String> j = liste.iterator();     // oppretter en iterator j

        System.out.println(i1.next());              // den første i listen
        i.remove();                                // fjerner den første
        System.out.println(j.next());              // den første i listen*/

       /* EnkeltLenketListe <Integer> liste = new EnkeltLenketListe<>();
        liste.leggInn(3);
        liste.leggInn(5);
        liste.leggInn(1);
        liste.leggInn(32);
        System.out.println(liste.antall());
        System.out.println(liste);
        Integer a = 32;
        Integer b = 3;
        Integer c= 5;
        Integer d = 1;
        System.out.println(liste.fjern(a));
        System.out.println(liste);
        System.out.println(liste.oppdater(0, 5 ));
        System.out.println(liste);

        Liste<Integer> liste = new EnkeltLenketListe<>();
        for (int i = 1; i <= 10; i++) liste.leggInn(i);
        System.out.println(liste);

        // fjerner partallene
        liste.fjernHvis(x -> x % 2 == 0);

        // skriver ut
        liste.forEach(x -> System.out.print(x + " "));*/

        /*int[] posisjon = {1, 2, 3, 4, 5, 6, 7, 10, 11, 13, 14, 22, 23, 28, 29};  // posisjoner og
        String[] verdi = "EIBGAHKLODNMCJF".split("");              // verdier i nivåorden

        BinTre<String> tre = new BinTre<>(posisjon, verdi);        // en konstruktør
        tre.nivåorden(Oppgave.konsollutskrift().deretter(c -> System.out.print(' ')));  // Utskrift: E I B G A H K L O D N M C J F

        Liste<String> liste = new Tabelliste<>();  // en liste
        tre.nivåorden(c -> liste.leggInn(c));          // lambda-uttrykk som argument
        System.out.println(liste);                     // skriver ut listen


        BinTre<Character> treBin = new BinTre<>();
        int[] p = {1, 2, 3, 4, 5, 6, 7, 10, 11, 13, 14, 22, 23, 28, 29};  // posisjoner i nivåorden
        String v = "EIBGAHKLODNMCJF";

        for (int i = 0; i < p.length; i++) {
            treBin.leggInn(p[i], v.charAt(i));
        }
        StringJoiner s = new StringJoiner(", ", "[", "]");
        char d = 'D';
        tre.nivåorden(c -> { if (c > d) s.add(c.toString()); } );
        String verdier = s.toString();
        System.out.println(verdier);

        char[] d = {(char)0};  // (char)0 er minste mulige tegn
        tre.nivåorden(c -> { if (c > d[0]) d[0] = c;});
        System.out.println(d[0]);

        //SKjønner ikke denne oppgaven spør om hjelp i øving?



        int[] posisjon = {1,2,3,4,5,6,7,10,11,13,14,22,23,28,29};  // nodeposisjoner
        String[] verdi = "EIBGAHKLODNMCJF".split("");              // verdier i nivåorden

        BinTre<String> tre = new BinTre<>(posisjon, verdi);        // en konstruktør

        int[] nivåer = tre.nivåer();  // bruker Programkode 5.1.6 k)

        System.out.print("Nivåer: " + Arrays.toString(nivåer));
        System.out.print(" Treets bredde: " + nivåer[Tabell.maks(nivåer)]);
        System.out.println(" Treets høyde: " + (nivåer.length - 1));

        // Utskrift: Nivåer: [1, 2, 4, 4, 4] Treets bredde: 4 Treets høyde: 4
    }

         */


}
