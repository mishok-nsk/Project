import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args){

        String str, chempion="";
        int i=1;
                
        while(!StdIn.isEmpty()){
            str=StdIn.readString();
            if(StdRandom.bernoulli(1.0/i)) chempion=str;
            i++;
        }

        StdOut.println(chempion);
        
    }

}
