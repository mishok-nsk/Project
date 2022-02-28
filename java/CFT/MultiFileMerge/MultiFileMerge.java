package MultiFileMerge;

import java.io.File;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MultiFileMerge {
    
    private static FileWriter fr;
    private static ArrayList<Scanner> stream;
    private static boolean intData;
    private static boolean ascendingOrder;


    private static void mergeInt() throws IOException {
        PQ<Integer> pq = new PQ<>(stream.size(), ascendingOrder);
        
        for (int i = 0; i < stream.size(); i++) {
            if (stream.get(i).hasNextInt())
                pq.insert(stream.get(i).nextInt(), i);
        }
        
        int val, nextVal, index;
        
        while (!pq.isEmpty()) {
            val = pq.top();
            fr.write(val + "\n");
            index = pq.enqueue();
            if (stream.get(index).hasNextInt()) {
                nextVal = stream.get(index).nextInt();
                if ((!ascendingOrder)&(nextVal <= val) || (ascendingOrder)&(nextVal >= val))
                    pq.insert(nextVal, index);
            }
        }
        fr.close();
    }
    
    private static void mergeString() throws IOException {
        PQ<String> pq = new PQ<>(stream.size(), ascendingOrder);
        
        for (int i = 0; i < stream.size(); i++) {
            String s = allowedString(stream.get(i));
            if (s != null)
                pq.insert(s, i);
        }

        String val, nextVal;
        int index;
        
        while (!pq.isEmpty()) {
            val = pq.top();
            fr.write(val + "\n");
            index = pq.enqueue();
            nextVal = allowedString(stream.get(index));
            if (nextVal != null) {
                if ((!ascendingOrder)&(nextVal.compareTo(val) <= 0) || ascendingOrder&(nextVal.compareTo(val) >= 0))
                pq.insert(nextVal, index);
            }
        }
        fr.close();
    }

    private static String allowedString(Scanner in) {
        String out;
        while (in.hasNextLine()) {
            out = in.nextLine();
            if (!out.contains(" ")) return out;
        }
        return null;
    } 

    private static void parseArgument(String[] args) {
        boolean stringFlag = false;
        boolean intFlag = false;
        boolean aFlag = false;
        boolean dFlag = false;
        
        if (args.length < 3) error("Minimum arguments not entered.");
        int i;
        loop:
        for (i = 0; i < 2; i++) {
            switch(args[i]) {
            case "-a" :
                if (aFlag || dFlag) error("Two ordering flag.");
                else aFlag = true;
                break;    
            case "-d" :
                if (aFlag || dFlag) error("Two ordering flag.");
                else dFlag = true;
                break;    
            case "-s" :
                if (stringFlag || intFlag) error("Two type flag.");
                else stringFlag = true;
                break;    
            case "-i" :
                if (stringFlag || intFlag) error("Two type flag.");
                else intFlag = true;
                break;
            default :
                if (args[i].charAt(0) == '-') error("Unknown parametr.");
                break loop;
            }
        }

        if (!(stringFlag || intFlag)) error("Data type undefined.");
        
        if (intFlag) intData = true;
        if (!dFlag) ascendingOrder = true;
        String outputFile = args[i++];
        
        int num = args.length - i;
        if (num == 0) error("No one input file is defined."); 
        stream = new ArrayList<>();
        for (int j = i; j < args.length; j++) {
            File file = new File(args[j]);
            try { stream.add(new Scanner(file));}
            catch(Exception e) {
                // System.out.print("Exception: ");
                System.out.println(e.getMessage());
            }
        }
        if (stream.isEmpty()) error("Input file(s) not found.");

        File outFile = new File(outputFile);
        try {
            fr = new FileWriter(outFile);
        }
        catch(IOException e) {
            error(e.getMessage());
        }
    }

    private static void error(String message) {
        System.err.print("Error: ");
        if (message != null) {
            System.err.println(message);
        }
        System.err.println("usage: MultiFileMerge (-i|-s) [-a|-d] <output.txt> <input1.txt> ...");
        System.exit(1);
    }

    public static void main(String[] args) throws IOException {
                      
        parseArgument(args);       
        if (intData) 
            mergeInt();
        else 
            mergeString();

    }

}
