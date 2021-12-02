package Yandex;
import java.util.Scanner;
import java.util.ArrayList;
//import java.util.Arrays;

public class Dublicate {
    
    public static void add(int[] out, int value) {

    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int length = in.nextInt();
        ArrayList<Integer> out = new ArrayList<>();
        //int[] out = new int[2];
        //int size = 2;
        //int count = 0;
        //out.add(in.nextInt());
        int next, last;
        last = in.nextInt();
        //out[count++] = last;
        //count++;
        out.add(last);
        //while(in.hasNext()) {
        for(int i = 1; i < length; i++) {
            next = in.nextInt();
            if(next > last) {
                out.add(next);
                //out[count++] = next;
                last = next;
                
            }
        }
        in.close();
        for(int s:out) System.out.println(s);
        //for(int i = 0; i < out.size(); i++) System.out.println(out.get(i));
    }
    

}
