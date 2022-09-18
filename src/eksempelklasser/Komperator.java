package eksempelklasser;

public class Komperator {
    @FunctionalInterface                // legges i mappen eksempelklasser
    public interface Komparator<T>      // et funksjonsgrensesnitt
    {
        int compare(T x, T y);            // en abstrakt metode
    }
}
