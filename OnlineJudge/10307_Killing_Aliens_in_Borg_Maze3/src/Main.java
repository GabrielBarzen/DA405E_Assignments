import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Array;
import java.sql.SQLOutput;
import java.util.*;
import java.util.Map.Entry;

public class Main {
    public static void main(String[] args) throws IOException {
        new Main();
    }

    public Main() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int numCases = Integer.parseInt(in.readLine()); // Read # cases
        for (int i = 0; i < numCases; i++) { // Run through all cases
            String[] settingsInput = in.readLine().split(" "); //Collect the settings for the maze
            int xIn = Integer.parseInt(settingsInput[0]);
            int yIn = Integer.parseInt(settingsInput[1]);
            Graph g = new Graph();


            //Construct the helper char maze for building the graph
            char[][] maze = new char[yIn][xIn];
            for (int y = 0; y < yIn; y++) {
                char[] row = in.readLine().toCharArray();
                for (int x = 0; x < xIn; x++) {
                    if (x > row.length - 1) maze[y][x] = '#';
                    else {
                        maze[y][x] = row[x];
                    }
                }
            }

            //Create a helper array to show which vertices correspond to which maze tile
            int[][] intPos = new int[yIn][xIn];
            for (int j = 0; j < intPos.length; j++) {
                Arrays.fill(intPos[j], -1);
            }
            //Loop through the xy coordinates and create the vertices
            for (int y = 0; y < yIn; y++) {
                for (int x = 0; x < xIn; x++) {
                    if (maze[y][x] != '#') {
                        //If the current maze tile is not a "#" then add a vertex of the current type and set current vertices pos in intPos array
                        switch (maze[y][x]) {
                            case ' ':
                                g.addVertex( Type.air);
                                break;
                            case 'S':
                                g.addVertex( Type.start);
                                break;
                            case 'A':
                                g.addVertex( Type.alien);
                                break;
                        }

                        intPos[y][x] = g.getVert()-1;
                        if (x > 0) {
                            if (intPos[y][x-1] != -1) {
                                g.addEdge(g.getVert()-1, intPos[y][x-1], false);
                            }
                        }
                        if (y > 0){
                            if (intPos[y-1][x] != -1) {
                                g.addEdge(g.getVert()-1, intPos[y-1][x], false);
                            }
                        }
                    }
                }
            }
            //Convert the unweighted graph constructed above to a weighted graph using BFS from all alien vertices + start to all other alien vertices + start.
            WeightedGraph wg = new WeightedGraph(g);
            System.out.println(wg.mst());
        }
    }



    enum Type {
        air,
        alien,
        start,
    }

    private class WeightedGraph {

        public int mst() {
            //MST using shoddily implemented prims algorithm.

            //Store the total weight of the total MST.
            int totalWeight = 0;
            //Array to see which vertices were visited.
            boolean[] visited = new boolean[vert];
            //List to keep the allowed edges between executions of the while loop.
            List<WeightedEdge> currentNeighboring = new ArrayList<>();
            //Pick a start guaranteed to exist.
            int currentVert = 0;
            while (true) {

                //Assign current vertices to visited and break out of while loop in case all of them have been.
                visited[currentVert] = true;
                boolean allVisited = true;
                for (boolean b : visited) {
                    if (!b) {
                        allVisited = false;
                        break;
                    }
                }
                if (allVisited) break;

                //Add all the connected edges to the array of possible choices
                currentNeighboring.addAll(adjList.get(currentVert));
                WeightedEdge selected = currentNeighboring.get(currentVert);
                int minWeight = Integer.MAX_VALUE;

                //Find smallest edge
                for (WeightedEdge weightedEdge : currentNeighboring) {
                    //First see if the destination of the edge already has been visited.
                    //As this algorithm goes edge by edge we can use this to prevent cycles which is not allowed in MST's.
                    if (!visited[weightedEdge.dest]) {
                        //Comparing all the edges weights with the minimum weight beginning with ∞, or in this case Integer.MAX_VALUE.
                        if (weightedEdge.weight < minWeight) {
                            selected = weightedEdge;
                            minWeight = selected.weight;
                            //Done finding smallest edge
                        }
                    }
                }
                //Marking destination of minWeightEdge as visited so no cycles can appear
                currentVert = selected.dest;
                //Add the selected edges weight to the total weight.
                totalWeight += selected.weight;

            }
            //When all is gone through, return the total weight of the MST.
            //Here we could also have constructed a proper MST, returned that and tallied the weight later.
            //This, however is unnecessary as the only interesting property of the MST is the minimum possible weight of the graph.
            return totalWeight;
        }

        //Constructor for empty WeightedGraph
        WeightedGraph(){}
        //Constructor for constructing weighted graph from unweighted graph (stupidly).
        WeightedGraph(Graph g){
            convertFromUnweighted(g);
        }


        private void convertFromUnweighted(Graph g) {
            //add the number of nodes required in the weighted graph (the unweighted graphs alien tiles + start tile)
            for (int i = 0; i < g.aliens.size(); i++) {
                addVertex();
            }
            //run bfs from all to all alien + start edges in order to get the weights for all vertices.
            //Without optimisation this stores a connection between ALL vertices, there could be a way to do this nicer.
            //Especially so because this is not used in any way in the MST implementation.
            List<Integer> aliens = g.getAliens();
            for (int i = 0; i < aliens.size(); i++) {
                for (int j = 0; j < aliens.size(); j++) {
                    if (!(i==j)) addEdge(i,j,g.bfs(aliens.get(i),aliens.get(j)),false);
                }
            }
        }


        public int getVert() {
            return vert;
        }

        int start = -1;
        int vert  = 0;

        HashMap<Integer,List<WeightedEdge>> adjList = new HashMap<>();
        public void addEdge(int src, int dest,int weight, boolean directional) {


            {
                List<WeightedEdge> srcAdjList = adjList.get(src);
                WeightedEdge we = new WeightedEdge(src, dest, weight);
                if (!srcAdjList.contains(we)) {
                    srcAdjList.add(we);
                    adjList.put(src, srcAdjList);
                }
            }
            {
                if (!directional) {
                    WeightedEdge we = new WeightedEdge(dest, src, weight);
                    List<WeightedEdge> destAdjList = adjList.get(dest);
                    if (!destAdjList.contains(we)) {
                        destAdjList.add(we);
                        adjList.put(dest, destAdjList);
                    }
                }
            }
        }

        public void addVertex() {
            if (!(adjList.containsKey(vert))){
                adjList.put(vert++,new ArrayList<>());
            }
        }

        @Override
        public String toString() {
            StringBuilder adjString = new StringBuilder("\n");

            for (Entry<Integer, List<WeightedEdge>> integerListEntry : adjList.entrySet()) {
                adjString.append(integerListEntry.getKey() + ": ");
                adjString.append(integerListEntry.getValue() + "\n");
            }
            return "Graph{" +
                    "start=" + start +
                    ", vert=" + vert +
                    ", adjList=" + adjString +
                    '}';
        }

        private class WeightedEdge {
            private final int src;
            private final int dest;
            private final int weight;

            WeightedEdge(int src, int dest,int weight) {
                this.src    = src;
                this.dest   = dest;
                this.weight = weight;
            }

            public int getSrc() {
                return src;
            }

            public int getDest() {
                return dest;
            }

            public int getWeight() {
                return dest;
            }

            @Override
            public boolean equals(Object o) {
                WeightedEdge edge = (WeightedEdge) o;
                return edge.src == src && edge.dest == dest;
            }

            @Override
            public String toString() {
                return String.format(  src + " " + dest + " " + weight);
            }
        }
    }
    private class Graph {


        public int bfs (int src, int dest) {
            //Init queue
            Queue queue = new Queue();
            //Create a visited array larger than max nodes 50x50
            boolean[] visited = new boolean[2500];
            //if src is dest no steps required, return 0.
            //this SHOULD never happen.
            int steps = 0;
            if (src == dest) return steps;

            //Otherwise, que the starting vertex and set it to visited in the visited array.
            queue.enQue(src);
            visited[src] = true;

            int numToDequeue = 1;

            destFound:
            while(true){
                steps++;
                //Temp store how many to deque, to ensure steps counts correctly.
                int tempNumToDequeue = 0;
                for (int i = 0; i < numToDequeue; i++) {
                    //Get the list of edges from the hashmap and look for the destination
                    for (Edge edge : adjList.get(queue.deQue())) {
                        if (edge.dest == dest){
                            break destFound;
                        }
                        //otherwise, add unvisited nodes to the que and start over.
                        if (!visited[edge.dest]){
                            tempNumToDequeue++;
                            queue.enQue(edge.dest);
                            visited[edge.dest] = true;
                        }
                    }
                }
                //Assign numToDequeue and start again
                numToDequeue = tempNumToDequeue;
            }
            return steps;

        }


        public int getStart() {
            return start;
        }

        public ArrayList<Integer> getAliens() {
            return aliens;
        }

        public HashMap<Integer, List<Edge>> getAdjList() {
            return adjList;
        }

        public int getVert() {
            return vert;
        }

        int start = -1;
        int vert  = 0;
        ArrayList<Integer> aliens = new ArrayList<>();

        HashMap<Integer,List<Edge>> adjList = new HashMap<>();
        public void addEdge(int src, int dest, boolean directional) {
            adjList.get(src).add(new Edge(src,dest));
            if(!directional) adjList.get(dest).add(new Edge(dest,src));
        }

        public void addVertex(Type type) {
            if (!(adjList.containsKey(vert))){
                adjList.put(vert++,new ArrayList<>());
                if (type.equals(Type.start) || type.equals(Type.alien)) aliens.add(vert-1);
                if (type.equals(Type.start)) start = vert-1;
            }
        }

        @Override
        public String toString() {
            StringBuilder adjString = new StringBuilder("\n");

            for (Entry<Integer, List<Edge>> integerListEntry : adjList.entrySet()) {
                adjString.append(integerListEntry.getKey() + ": ");
                adjString.append(integerListEntry.getValue() + "\n");
            }
            return "Graph{" +
                    "start=" + start +
                    ", vert=" + vert +
                    ", aliens=" + aliens +
                    ", adjList=" + adjString +
                    '}';
        }

        private class Edge {
            private final int src;
            private final int dest;

            Edge(int src, int dest) {
                this.src    = src;
                this.dest   = dest;
            }

            public int getSrc() {
                return src;
            }

            public int getDest() {
                return dest;
            }

            @Override
            public String toString() {
                return String.format(src + " -> " + dest);
            }
        }
        class Queue {
            int[] queueItems = new int[2500];
            int front = -1;
            int rear = -1;



            public boolean isFull() {
                return front == 0 && rear == queueItems.length-1;
            }

            public boolean isEmpty() {
                return front == -1;
            }

            public void enQue(int i) {
                if (isFull());
                else {
                    if (front == -1) {
                        front = 0;
                    }
                }
                rear++;
                queueItems[rear] = i;
            }

            public int deQue() {
                int element = -1;
                if (isEmpty()) return -1;
                else {
                    element = queueItems[front];
                    if (front >= rear) {
                        front = -1;
                        rear = -1;
                    }
                    else {
                        front++;
                    }
                    return element;
                }

            }

            public int getFront() {
                return front;
            }

            public void setFront(int front) {
                this.front = front;
            }

            public int getRear() {
                return rear;
            }

            public void setRear(int rear) {
                this.rear = rear;
            }
        }
    }
}

