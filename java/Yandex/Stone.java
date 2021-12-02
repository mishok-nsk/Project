package Yandex;
import java.util.Scanner;

public class Stone {
    
        public static void main(String[] args) {

            Scanner in = new Scanner(System.in);
            String j = in.nextLine();
            String s = in.nextLine();
            in.close();
            int num = 0;
            for(int i = 0; i < s.length(); i++) {
                for(int k = 0; k < j.length(); k++) {
                    if(s.charAt(i) == j.charAt(k)) num++;
                }
            }
            System.out.println(num);
        }
        
        
}
