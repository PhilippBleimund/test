import java.util.Scanner;

// 
// Decompiled by Procyon v0.5.36
// 

public class test
{
    public static void main(final String[] args) {
        final Scanner scann = new Scanner(System.in);
        final String S = scann.nextLine();
        final String S2 = scann.nextLine();
        String fileName = "result";
        for (int i = 0; i < Integer.valueOf(S2); ++i) {
            fileName = "resultAnalyse" + String.valueOf(i) + ".txt";
            GUIV2 gui = new GUIV2(S, fileName);
            gui = null;
            System.gc();
        }
    }
}
