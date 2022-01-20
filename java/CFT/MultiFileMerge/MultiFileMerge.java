package MultiFileMerge;

import java.io.File;
// import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MultiFileMerge {
    /*
    private class Merger<Item extends Comparable<Item>> {
        PQ<Item> pq;
        private void merge(Scanner[] stream, String outputFile, boolean dFlag) {
    
        }
    }
    */
    private static String allowedString(Scanner in) {
        String out;
        while (in.hasNextLine()) {
            out = in.nextLine();
            if (!out.contains(" ")) return out;
        }
        return null;
    } 

    private static void mergeInt(ArrayList<Scanner> stream, String fileName, boolean dFlag) throws IOException {
        PQ<Integer> pq = new PQ<>(stream.size(), !dFlag);
        
        for (int i = 0; i < stream.size(); i++) {
            if (stream.get(i).hasNextInt())
                pq.insert(stream.get(i).nextInt(), i);
        }

        File outFile = new File(fileName);
        // FileWriter fr = new FileWriter(outFile);
        /*
        if (outFile.exists())
            outFile.delete();
        */
        // outFile.createNewFile();
        FileWriter fr = new FileWriter(outFile);
        int val, nextVal, index;
        while (!pq.isEmpty()) {
            val = pq.top();
            fr.write(val + "\n");
            index = pq.enqueue();
            if (stream.get(index).hasNextInt()) {
                nextVal = stream.get(index).nextInt();
                if ((dFlag)&(nextVal <= val) || (!dFlag)&(nextVal >= val))
                pq.insert(nextVal, index);
            }
        }
        fr.close();
    }
    
    private static void mergeString(ArrayList<Scanner> stream, String fileName, boolean dFlag) throws IOException {
        PQ<String> pq = new PQ<>(stream.size(), !dFlag);
        
        for (int i = 0; i < stream.size(); i++) {
            String s = allowedString(stream.get(i));
            if (s != null)
                pq.insert(s, i);
        }

        File outFile = new File(fileName);
        // FileWriter fr = new FileWriter(outFile);
        /*
        if (outFile.exists())
            outFile.delete();
        outFile.createNewFile();
        */
        FileWriter fr = new FileWriter(outFile);
        String val, nextVal;
        int index;
        while (!pq.isEmpty()) {
            val = pq.top();
            fr.write(val + "\n");
            index = pq.enqueue();
            nextVal = allowedString(stream.get(index));
            if (nextVal != null) {
                if (dFlag&(nextVal.compareTo(val) <= 0) || (!dFlag)&(nextVal.compareTo(val) >= 0))
                pq.insert(nextVal, index);
            }
        }
        fr.close();
    }

    public static void main(String[] args) throws IOException {
        boolean stringFlag = false;
        boolean intFlag = false;
        boolean aFlag = false;
        boolean dFlag = false;
        String outputFile = "";
        
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
                if (args[i].charAt(0) == '-') error("Unknown parametr");
                break loop;
            }
        }

        if (!(stringFlag || intFlag)) error("Data type undefined.");
        outputFile = args[i++];

        int num = args.length - i; // number of input file;
        if (num == 0) error("No one input file is defined"); 
        // Scanner[] stream = new Scanner[num];
        ArrayList<Scanner> stream = new ArrayList<>();
        for (int j = i; j < args.length; j++) {
            File file = new File(args[j]);
            try { stream.add(new Scanner(file));}
            catch(Exception e) {
                // System.out.print("Exception: ");
                System.out.println(e.getMessage());
            }
        }

        if (stream.isEmpty()) error("No one input file is defined");
        // ArrayList arr;
        // arr.
        // Merger<Integer> iMerge = new Merger<>();
        // Merger<String> sMerge = new Merger<>();
        if (intFlag) mergeInt(stream, outputFile, dFlag);
        else mergeString(stream, outputFile, dFlag);

        // iMerge = null;
    }

    private static void error(String message) {
        System.err.print("Error: ");
        if (message != null) {
            System.err.println(message);
        }
        System.err.println("usage: MultiFileMerge [-a] [-i] [<output.txt>] [ <input1.txt> ...]");
        System.exit(1);
    }
}
