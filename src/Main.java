import eksempelklasser.*;

import java.util.Arrays;

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

            Komperator.Komparator<Person> c = new FornavnKomparator();   // en instans av klassen
            Tabell.innsettingssortering(p, c);                // se Programkode 1.4.6 b)

            System.out.println(Arrays.toString(p));           // Utskrift av tabellen p
            // [Ali Kahn, Azra Zukanovic, Boris Zukanovic, Kari Svendsen, Kari Pettersen]

        Tabell.innsettingssortering(p, (p1,p2) -> p1.fornavn().compareTo(p2.fornavn()));
        System.out.println(Arrays.toString(p));

        Tabell.innsettingssortering(s, (s1, s2) -> s1.studium().compareTo(s2.studium()));
        System.out.println(Arrays.toString(s));
        }
    }

