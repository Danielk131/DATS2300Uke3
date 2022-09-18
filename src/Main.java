import eksempelklasser.Heltall;
import eksempelklasser.Person;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        //int[] a = Tabell.randPerm(10);
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
        double[] d = {5.7,3.14,7.12,3.9,6.5,7.1,7.11};
        String[] s = {"Sohil","Per","Thanh","Fatima","Kari","Jasmin"};
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
        System.out.println(x.compareTo(y) + "  " + x.equals(y));*/

        Person[] p = new Person[5];                   // en persontabell

        p[0] = new Person("Kari","Svendsen");         // Kari Svendsen
        p[1] = new Person("Boris","Zukanovic");       // Boris Zukanovic
        p[2] = new Person("Ali","Kahn");              // Ali Kahn
        p[3] = new Person("Azra","Zukanovic");        // Azra Zukanovic
        p[4] = new Person("Kari","Pettersen");        // Kari Pettersen

        int m = Tabell.maks(p);                       // posisjonen til den største
        System.out.println(p[m] + " er størst");      // skriver ut den største

        //Tabell.innsettingssortering(p);// generisk sortering
        Arrays.sort(p);
        System.out.println(Arrays.toString(p));       // skriver ut sortert
    }
}