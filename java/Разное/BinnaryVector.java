import java.util.Scanner;

public class BinnaryVector {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int length = in.nextInt();
        int maxlength = 0, curlength = 0;
        for(int i= 0; i < length; i++) {
            if(in.nextInt() == 1) {
                curlength++;
            }
            else {
                if(curlength > maxlength) maxlength = curlength;
                curlength = 0;
            }
        }
        if(curlength > maxlength) maxlength = curlength;
        in.close();
        System.out.println(maxlength);
    }

}
