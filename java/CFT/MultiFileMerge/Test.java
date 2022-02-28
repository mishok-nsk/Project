package MultiFileMerge;

import java.util.Arrays;
import java.io.File;
import java.io.FileWriter;

public class Test {
    
    public static void main(String[] args) throws Exception{
        int maxVal = Integer.parseInt(args[0]);
        int size = Integer.parseInt(args[1]);
        int numberOfFiles = Integer.parseInt(args[2]);
        String[] outArgs = new String[numberOfFiles + 2];
        outArgs[0] = "-i";
        outArgs[1] = "output.txt";

        for (int j = 0; j < numberOfFiles; j++) {
            String file = "input" + j + ".txt";
            outArgs[j + 2] = file;
            int[] intArray = new int[size];
            for (int i = 0; i < size; i++) {
                intArray[i] = (int) (Math.random() * maxVal);
            }
            Arrays.sort(intArray);
            File outFile = new File(file);
            FileWriter fr = new FileWriter(outFile);
            for (int s : intArray) {
                fr.write(s + "\n");
            }
            fr.close();
        }
        MultiFileMerge.main(outArgs);
    }
}
