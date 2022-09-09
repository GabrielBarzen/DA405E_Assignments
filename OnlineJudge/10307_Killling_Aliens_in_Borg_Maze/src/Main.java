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
            int numCases = Integer.parseInt(in.readLine());
            System.out.println();
            System.out.println();
            for (int i = 0; i < numCases; i++) {
                String[] settingsInput = in.readLine().split(" ");
                int xIn = Integer.parseInt(settingsInput[0]);
                int yIn = Integer.parseInt(settingsInput[1]);
                Graph g = new Graph();
                int numVert = 0;
                char[][] lines = new char[yIn][xIn];
                for (int y = 0; y < yIn; y++) {
                    lines[y] = in.readLine().toCharArray();
                    for (int x = 0; x < lines[y].length; x++) {
                        System.out.print(lines[y][x]);
                    }
                    System.out.println();
                }

                int[] prevXOffsetIdx = new int[xIn];
                for (int y = 0; y < yIn; y++) {
                    //int yOffsetIdx = y*xIn;
                    int[] xOffsetIdx = new int[xIn];
                    Arrays.fill(xOffsetIdx, xIn);
                    for (int x = 0; x < xIn; x++) {
                        if (x < lines[y].length) {
                        boolean added = false;
                        switch (lines[y][x]) {
                            case '#':
                                for (int j = x; j < xOffsetIdx.length; j++) {
                                    xOffsetIdx[j]--;
                                }
                                break;
                            case 'A':
                                g.addVertex(numVert, Type.alien);
                                added = true;
                                break;
                            case ' ':
                                g.addVertex(numVert, Type.air);

                                added = true;
                                break;
                            case 'S':
                                g.addVertex(numVert, Type.start);
                                added = true;
                                break;
                            default:
                                throw new InputMismatchException("Unexpected Char in input string");
                        }
                        if (added) {
                            System.out.println("offsetarr : " + Arrays.toString(prevXOffsetIdx));
                            System.out.println("y: " + y + ", x: " + x);
                            if (x > 0 && lines[y][x - 1] != '#') {
                                g.addEdge(numVert, numVert - 1, false);
                            }
                            if (y > 0 && lines[y - 1][x] != '#') {
                                System.out.println("offset idx: " + prevXOffsetIdx[x]);
                                System.out.println("xIn: " + xIn);
                                g.addEdge(numVert, numVert -(xIn - prevXOffsetIdx[x]), false);
                            }
                            numVert++;
                        }
                    } else {
                        System.out.println("skipping : " + y);
                    }
                }
                prevXOffsetIdx = xOffsetIdx;

                }
                System.out.println();
                g.print();
                System.out.println( g.bfsAll() );
            }

            System.out.println();
        }
    }

    class Graph {
        HashMap<Integer, List<Integer>> adjList = new HashMap<>();
        ArrayList<Integer> alienVertices = new ArrayList<>();
        HashMap<Integer,Integer> minSteps = new HashMap<>();
        int start;

        public int bfs (int src, int dest) {
            System.out.println("running bfs from : " + src + ", to : " + dest);
            BfsQueue queue = new BfsQueue();
            boolean[] visited = new boolean[2500];
            queue.enQue(src);
            visited[src] = true;
            int steps = 0;
            if (src == dest) return 0;
            destFound:
            while(true){
                steps++;
                for (Integer integer : adjList.get(queue.deQue())) {
                    if (integer == dest) break destFound;
                    else if (!visited[integer]){
                        queue.enQue(integer);
                        visited[integer] = true;
                    }
                    System.out.println(integer);
                }
                System.out.println("brr");
            }
            return steps;
            /*
            * Lägg första på kö
            * ta bort allt från kö;
            * för varje föremål i kö
            * lägg grannarna i kö
            *
            *
            *
            *
            * */

        }

        public void addVertex(int vertex, Type type) {
            if (!adjList.containsKey(vertex)) adjList.put(vertex, new ArrayList<>());
            if (type.equals(Type.start)) start = vertex;
            if (type.equals(Type.alien)) alienVertices.add(vertex);
        }

        public void addEdge(int src, int dest, boolean directional) {
            List<Integer> srcAdjList = adjList.get(src);
            if (!srcAdjList.contains(dest)) srcAdjList.add(dest);
            if (!directional) {
                List<Integer> destAdjList = adjList.get(dest);
                if (!destAdjList.contains(src)) destAdjList.add(src);
            }
        }

        public int bfsAll() {
            for (Integer alienVertex : alienVertices) {
                minSteps.put(alienVertex,bfs(start,alienVertex));
            }

            for (Integer alienStart : alienVertices) {
                for (Integer alienDest : alienVertices) {
                    int steps = bfs(alienStart, alienDest);
                    if (steps < minSteps.get(alienDest)) {
                        minSteps.put(alienDest,steps);
                    }
                }
            }

            int total = 0;
            for (Integer value : minSteps.values()) {
                total += value;
            }
            return total;
        }

        public void print() {
            for (Map.Entry<Integer, List<Integer>> integerListEntry : adjList.entrySet()) {
                System.out.println("Vertex : " + integerListEntry.getKey() + ", has neighbours : " + integerListEntry.getValue());
            }
        }
    }

    class BfsQueue {
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
            if (isFull()) System.out.println("queue full");
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

enum Type {
    air,
    alien,
    start,
}



