
import java.util.*;

public class WeightedGraph {
   
	//Array list to store infected people
    ArrayList<Vertex> infectedPeople = new ArrayList<>();
    //Array list to store people likely to be infected
    ArrayList<Vertex> likelyInfectedPeople = new ArrayList<>();
	
	//Graph attributes
    ArrayList<Vertex> arrayList;
    ArrayList<String> names;

    Random random = new Random();
    
    public static void main(String[] args) {
        int vertices = 0;
        int infected = 0;
        int socialDistancing = 0;
        Scanner s = new Scanner(System.in);
        // Prompt user input
        try {
            System.out.println("Enter a number of people to test: ");
            vertices = s.nextInt();
            System.out.println("Enter the number of infected people: ");
            infected = s.nextInt();
            System.out.println("Have the infected people been social distancing? Enter 1 for yes, Enter any other number for no.");
            socialDistancing = s.nextInt();
        }
        catch (Exception e){
            System.out.println(e);
            System.out.println("Invalid input");
        }

        //Initialize Graph with user input
        WeightedGraph graph = new WeightedGraph(vertices , infected, socialDistancing);

        //Print details about generated graph
        graph.print();
    }

  //Vertex Class
    class Vertex {
        String name;
        Edge adjList;
        boolean infected = false;

        Vertex(String name, Edge node) {
            this.name = name;
            adjList = node;
        }
    }

    //Edge class
    class Edge {
        int weight;
        int vertexIndex;
        public Edge next;

        public Edge(int vertexIndex, int weight, Edge node) {
            this.vertexIndex = vertexIndex;
            this.weight = weight;
            next = node;
        }
    }
    
    //Function to add vertex to graph
    public void addVertex(String vertexName) {
        arrayList.add(new Vertex(vertexName, null));
    }
    
    //Function to add edges 
    public void addEdge(String sourceName, String destinationName) {
        int vertexOne = indexName(sourceName);
        int vertexTwo = indexName(destinationName);

        //Create random weight
        int maxWeight = 10;
        int randomWeight = random.nextInt(maxWeight);

        // Check and add likely to be infected
        Vertex source = arrayList.get(vertexOne);
        Vertex destination = arrayList.get(vertexTwo);
        if(randomWeight >= 6 && arrayList.get(vertexOne).infected){
            if(!likelyInfectedPeople.contains(vertexTwo) && !infectedPeople.contains(vertexOne)) {
                likelyInfectedPeople.add(arrayList.get(vertexTwo));
            }
        }
        //If statement to prevent self loops
        if(source.name.equals(destination.name)) {
            return;
        }
        
        //Direct insert
        arrayList.get(vertexOne).adjList = new Edge(vertexTwo, randomWeight, arrayList.get(vertexOne).adjList);
    }
    
    
    private int indexName(String s) {
        // Search through array to find where the vertices exists and return index position 
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).name.equals(s)) {
                return i;
            }
        }
        return -1;
    }
    
    
    public WeightedGraph(int vertices, int numInfected, int socialDistance) {
        this.arrayList = new ArrayList<>();

        //Create names for each vertex
        names = new ArrayList<>();
        for (int i = 0; i < vertices; i++) {
            names.add("person(" + i + ")");
            addVertex(names.get(i)); 
        }
        //Randomly assign infected people
        for (int i = 0; i < numInfected; i++) {
            int randomIndex = random.nextInt(arrayList.size() - 1);
            arrayList.get(randomIndex).infected = true;
        }

        //Number for how social the people are
        int maxEdges = vertices * 4; // Lower means social distancing, higher means people are very social

        //Randomly assign edges
        int randomNumOfEdges = random.nextInt(maxEdges);
        for (int i = 0; i < randomNumOfEdges; i++) {
            int max = arrayList.size() - 1;
            int randomIndexForSource = random.nextInt(max);
            int randomIndexForDestination = random.nextInt(max);
            addEdge(arrayList.get(randomIndexForSource).name, arrayList.get(randomIndexForDestination).name);
        }
        
        //Divide interactions by 3 if social distancing
        if(socialDistance == 1) {
            maxEdges = maxEdges / 3; 
        }
    }

    

    public void print() {
        System.out.println();
        for (int i = 0; i < arrayList.size(); i++) {
            System.out.print(arrayList.get(i).name);
            //Add to infected list
            if(arrayList.get(i).infected){
                infectedPeople.add(arrayList.get(i));
            }

            for (Edge edge = arrayList.get(i).adjList; edge != null; edge = edge.next) {
                // If current person is infected
                if(arrayList.get(i).infected) {
                    System.out.print(" ---" + edge.weight + "---> " + arrayList.get(edge.vertexIndex).name);
                }else {
                    System.out.print(" ---" + edge.weight + "---> " + arrayList.get(edge.vertexIndex).name);
                }
            }
            System.out.println("\n");
        }
        
        //Print infected people
        System.out.print("Infected People: ");
        for (int i = 0; i < infectedPeople.size(); i++) {
            System.out.print(infectedPeople.get(i).name + ", ");
        }

        //Print out likely infected
        System.out.print("\nAre likely infected: ");
        for (int i = 0; i < likelyInfectedPeople.size(); i++) {
            System.out.print(likelyInfectedPeople.get(i).name + ", ");
        }
    }

}
