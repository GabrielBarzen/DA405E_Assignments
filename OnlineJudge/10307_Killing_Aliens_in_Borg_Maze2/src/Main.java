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
        for (int i = 0; i < numCases; i++) {
            String[] settingsInput = in.readLine().split(" ");
            int xIn = Integer.parseInt(settingsInput[0]);
            int yIn = Integer.parseInt(settingsInput[1]);
            System.out.println("Reading new maze with dimensions, x: " + xIn + ", y: " + yIn);
            Graph g = new Graph();

            char[][] maze = new char[yIn][xIn];
            for (int y = 0; y < yIn; y++) {
                char[] row = in.readLine().toCharArray();
                for (int x = 0; x < xIn; x++) {
                    if (x > row.length - 1) maze[y][x] = '#';
                    else {
                        maze[y][x] = row[x];

                    }
                    System.out.print(maze[y][x]);
                }
                System.out.println();
            }
            int numvert = 0;
            int[][] intPos = new int[yIn][xIn];

            for (int j = 0; j < intPos.length; j++) {
                Arrays.fill(intPos[j], -1);
            }

            for (int y = 0; y < yIn; y++) {
                for (int x = 0; x < xIn; x++) {
                    if (maze[y][x] != '#') {
                        switch (maze[y][x]) {
                            case ' ':
                                g.addVertex(numvert, Type.air);
                                System.out.println("added vertex");
                                break;
                            case 'S':
                                g.addVertex(numvert, Type.start);
                                System.out.println("added vertex");
                                break;
                            case 'A':
                                g.addVertex(numvert, Type.alien);
                                System.out.println("added vertex");
                                break;
                        }
                        intPos[y][x] = numvert;
                        if (x > 0) {
                            if (intPos[y][x-1] != -1) {

                                g.addEdge(numvert, intPos[y][x-1], false);
                            }
                        }
                        if (y > 0){
                            if (intPos[y-1][x] != -1) {

                                g.addEdge(numvert, intPos[y-1][x], false);
                            }
                        }
                        numvert++;
                    }
                }
            }
            System.out.println(g.bfsAll());
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
    class Graph {
        public int bfsAll() {
            System.out.println("Running base value calc");
            for (Integer alienVertex : alienVertices) {
                minSteps.put(alienVertex,bfs(start,alienVertex));
            }
            for (Map.Entry<Integer, Integer> integerIntegerEntry : minSteps.entrySet()) {
                System.out.println("init values : " + integerIntegerEntry);
            }

            System.out.println("Running comparison values");
            for (Integer alienStart : alienVertices) {
                for (Integer alienDest : alienVertices) {
                    if (alienStart.equals( alienDest)) continue;
                    int steps = bfs(alienStart, alienDest);
                    System.out.println("found at : " + steps + " steps");
                    if (steps < minSteps.get(alienDest)) {
                        System.out.println("Shorter path found from : " + alienStart + " to: " + alienDest + ", with steps : " + steps);
                        minSteps.put(alienDest,steps);
                    }
                }
            }
            for (Map.Entry<Integer, Integer> integerIntegerEntry : minSteps.entrySet()) {
                System.out.println("post values : " + integerIntegerEntry);
            }


            int total = 0;
            for (Integer value : minSteps.values()) {
                System.out.println("tallying steps with value : " + value);
                total += value;
            }
            System.out.println("total steps for maze is : " + total);
            return total;
        }

        public int bfs (int src, int dest) {
            System.out.println("running bfs from : " + src + ", to : " + dest);
            BfsQueue queue = new BfsQueue();
            boolean[] visited = new boolean[2500];
            queue.enQue(src);
            visited[src] = true;
            int steps = 0;
            if (src == dest) return 0;
            int numToDequeue = 1;
            destFound:
            while(true){
                steps++;
                int tempNumToDequeue = 0;
                for (int i = 0; i < numToDequeue; i++) {
                    for (Integer integer : adjList.get(queue.deQue())) {
                        if (integer == dest){
                            break destFound;
                        }
                        if (!visited[integer]){
                            tempNumToDequeue++;
                            queue.enQue(integer);
                            visited[integer] = true;
                        }
                    }
                }
                numToDequeue = tempNumToDequeue;

            }
            return steps;
        }



        HashMap<Integer, List<Integer>> adjList = new HashMap<>();
        ArrayList<Integer> alienVertices = new ArrayList<>();
        HashMap<Integer, Integer> minSteps = new HashMap<>();
        int start;


        public void addVertex(int vertex, Type type) {
            if (!adjList.containsKey(vertex)) adjList.put(vertex, new ArrayList<>());
            if (type.equals(Type.start)) start = vertex;
            if (type.equals(Type.alien)) alienVertices.add(vertex);
        }

        public void addEdge(int src, int dest, boolean directional) {
            List<Integer> srcAdjList = adjList.get(src);
            if (!srcAdjList.contains(dest)) {
                srcAdjList.add(dest);
                adjList.put(src, srcAdjList);
            }
            if (!directional) {
                List<Integer> destAdjList = adjList.get(dest);
                if (!destAdjList.contains(src)) {
                    destAdjList.add(src);
                    adjList.put(dest, destAdjList);
                }
            }
        }


        public void print() {
            for (Map.Entry<Integer, List<Integer>> integerListEntry : adjList.entrySet()) {
                System.out.println("Vertex : " + integerListEntry.getKey() + ", has neighbours : " + integerListEntry.getValue());
            }
        }
    }

    enum Type {
        air,
        alien,
        start,
    }
}

