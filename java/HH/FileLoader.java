public class FileLoader {
    
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int m = Integer.parseInt(args[1]);
        int c = Integer.parseInt(args[2]);
        System.out.println(n + " " + m);
        for (int i=0; i < n; i++) System.out.println((int) (Math.random() * c));

    }
}
