package Yandex;
import java.util.Scanner;

public class anagramm {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String first = in.nextLine();
        String second = in.nextLine();
        in.close();
       
        int i,j;
        for(i = 0, j = second.length() - 1; i < first.length() && j >= 0; i++, j--) {
            if(first.charAt(i) == ' ') i++;
            if(second.charAt(j) == ' ') j--;

            if(first.charAt(i) != second.charAt(j)) {
                System.out.println("0");
                return;
            }
        }
        if(i == first.length() && j < 0) System.out.println("1");
            else System.out.println("0");

    }
}
