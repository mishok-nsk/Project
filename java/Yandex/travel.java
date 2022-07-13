package Yandex;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.LinkedList;

public class travel {
    
    public static class Graph {
        ArrayList<Integer>[] road;
        int numberOfcity;

        public Graph(int num) {
            numberOfcity = num;
            road = (ArrayList<Integer>[]) new ArrayList[num];
            for(int i = 0; i < num; i++) 
                road[i] = new ArrayList<Integer>();

        }

        public void addRoad(int v, int w) {
            road[v].add(w);
            road[w].add(v);
        }

        public int findRoute(int v, int w) {
            LinkedList<Integer> queue = new LinkedList<>();             
            boolean[] marked = new boolean[numberOfcity];
            int[] depth = new int[numberOfcity];
            marked[v] = true;
            queue.add(v);
            int x;
            depth[v] = 0;
            while(!queue.isEmpty()) {
                x = queue.remove();
                for(int i:road[x]) {
                    if(i == w) 
                        return depth[x]+1;
                    if(!marked[i]) {
                        marked[i] = true;
                        queue.add(i);
                        depth[i] = depth[x]+1;
                    }
                    
                }
                
            }
            return -1;

        }

    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int numberOfcity = in.nextInt();
        Graph city = new Graph(numberOfcity);
        int[][] coord = new int[numberOfcity][2];
        for(int i = 0; i < numberOfcity; i++) {
            coord[i][0] = in.nextInt();
            coord[i][1] = in.nextInt();
        }
        int maxdistance = in.nextInt();
        int v = in.nextInt() - 1;
        int w = in.nextInt() - 1;
        in.close();
        long distance;
        for(int i = 0; i < numberOfcity; i++) {
            for(int j = i + 1; j < numberOfcity; j++) {
                distance = Math.abs((long)(coord[i][0] - coord[j][0])) + Math.abs((long)(coord[i][1] - coord[j][1]));
                if(distance <= (long)maxdistance) city.addRoad(i, j);
            }
        }
        System.out.println(city.findRoute(v,w));
    }
}
