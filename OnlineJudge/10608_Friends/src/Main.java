import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        new Main();
    }
    Main() throws IOException {
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
                String[] inPairs = in.readLine().split(" ");
                int[] pair = new int[2];
                pair[0] = Integer.parseInt(inPairs[0]);
                pair[1] = Integer.parseInt(inPairs[1]);
                g.addEdge(pair[0], pair[1]);
                g.addEdge(pair[1], pair[0]);

                for (int k = 0; k < g.getAdjListForVertex(pair[1]).size(); k++) {
                    g.addEdge(g.getAdjListForVertex(pair[1]).get(k), pair[0]);
                }
                for (int k = 0; k < g.getAdjListForVertex(pair[0]).size(); k++) {
                    g.addEdge(g.getAdjListForVertex(pair[0]).get(k), pair[1]);
                }
            }

            int biggest = 0;
            for (List<Integer> adjList: g.getAllAdjLists()) {
                System.out.println(adjList);
                if (adjList.size()>biggest) biggest = adjList.size();
            }
            System.out.println(biggest);
            }
        }
    class Graph {

        private Map<Integer,List<Integer>> adjacent = new HashMap<>();

        public Collection<List<Integer>> getAllAdjLists() {
            return adjacent.values();
        }
        public void addVertex(int i) {

            adjacent.put(i,new ArrayList<Integer>());


        }

        public void addEdge(int src, int dest) {
            List<Integer> srcAdjList = adjacent.get(src);
            boolean exists = false;
            for (Integer integer : srcAdjList) {
                if (integer == dest) {
                    exists = true;
                    break;
                }
            }
            if (!exists) srcAdjList.add(dest);
        }

        public List<Integer> getAdjListForVertex(int i) {
            return adjacent.get(i);
        }

        public void setAdjListForVertex(int i,List<Integer> list) {
            adjacent.put(i, list);
        }
    }
}
