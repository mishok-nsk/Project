package Yandex;
import java.util.Scanner;
public class brackets {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int  num = in.nextInt();
        int right;
        for(int left = num; left > 0; left--) {
            right = 1;
            while(right <= left) {
                for(int j = left; j > 0; j--) {
                    System.out.print("(");
                }
                for(int j = right; j > 0; j--) {
                    System.out.print(")");
                }    
            }
            
        }
    }
}
