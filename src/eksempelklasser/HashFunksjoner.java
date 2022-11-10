package eksempelklasser;

public class HashFunksjoner {

    public static int hash(String s)
    {
        int h = 0;
        for (int i = 0; i < s.length(); i++)
        {
            h = (h << 5) ^ s.charAt(i) ^ h;
        }
        return h;
    }
}
