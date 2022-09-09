import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        new Main();
    }

    public Main() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int numTests = Integer.parseInt(in.readLine());

        for (int i = 0; i < numTests; i++) {
            String[] input = in.readLine().split(" ");
            int numCitizen = Integer.parseInt(input[0]);
            int numPairs = Integer.parseInt(input[1]);
            Graph g = new Graph();

            for (int j = 1; j <= numCitizen; j++) {
                g.addVertex(j);
            }
            for (int j = 0; j < numPairs; j++) {
                String[] inputString = in.readLine().split(" ");
                int src = Integer.parseInt(inputString[0]);
                int dest = Integer.parseInt(inputString[1]);
                g.addEdge(src,dest,false);
            }
            System.out.println(g.getBiggestSubGraphSize());

        }
    }

    class Graph {
        private Map<Integer, List<Integer>> adjacent = new HashMap<>();

        public Collection<List<Integer>> getAllAdjLists() {
            return adjacent.values();
        }

        public void addVertex(int i) {
            adjacent.put(i,new ArrayList<Integer>());
        }

        public void addEdge(int src, int dest, boolean directional) {
            List<Integer> srcAdjList = adjacent.get(src);
            if (!srcAdjList.contains(dest)) srcAdjList.add(dest);
            if (!directional) {
                List<Integer> destAdjList = adjacent.get(dest);
                if (!destAdjList.contains(src)) destAdjList.add(src);
            }
        }

        public int getBiggestSubGraphSize() {
            int biggest = 0;
            for (Integer integer: adjacent.keySet()) {
                visited = new boolean[adjacent.size()];
                int size = tally(integer);
                if(size >= biggest) biggest = size;
            }
            return biggest;
        }

        boolean[] visited;
        private int tally(Integer integer) {
            int tally = 1;
            visited[integer-1] = true;
            List<Integer> list = getAdjListForVertex(integer);
            for (Integer i: list) {
                if (!visited[i-1]) {
                    tally += tally(i);
                }
            }
            return tally;
        }

        public List<Integer> getAdjListForVertex(int i) {
            return adjacent.get(i);
        }

        public void setAdjListForVertex(int i,List<Integer> list) {
            adjacent.put(i, list);
        }
    }
}
