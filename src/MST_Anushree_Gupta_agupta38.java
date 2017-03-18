import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class MST_Anushree_Gupta_agupta38 {
	
	final static String input = "input.txt";
	
	int nodes = 0;
	int edges = 0;
	int weight = 0;
	int minWeight = 0;
	
	//Creating heap for the weights of all the edges in consideration
	Heap H = new Heap();
	
	//Declaring structures for storing the edges with their respective weights
	HashMap<Integer, Map<Integer, Integer>> final_map = new HashMap<Integer, Map<Integer, Integer>>();
	
	//Declaring array for storing the minimum spanning tree
	ArrayList<int []> MST = new ArrayList<int []>();

	public static void main(String[] args) throws IOException {
		
		MST_Anushree_Gupta_agupta38 P = new MST_Anushree_Gupta_agupta38();
		
		//Reading the input.txt file
	    File query = new File(input); 
		BufferedReader bufferReader = new BufferedReader(new FileReader(query));
		
		//Create a file and write to output.txt
	     File output = new File("output.txt");
	     BufferedWriter writer = new BufferedWriter(new FileWriter(output));
		        
		P.getInput(bufferReader);
		P.processingMST();
		P.getOutput(writer);
		
		bufferReader.close();
		writer.close();

	}
	
	void getInput(BufferedReader reader) throws IOException {
		
		String line = reader.readLine();
		String[] tempfirst = line.split("\\s+");
    	Integer[] temp_intfirst = new Integer[3]; 
    	temp_intfirst[0] = Integer.parseInt(tempfirst[0]);
    	temp_intfirst[1] = Integer.parseInt(tempfirst[1]);
    	
    	nodes = temp_intfirst[0];
    	edges = temp_intfirst[1];
   				
	   	while ((line = reader.readLine()) != null) 
	   	{	
	   	    String[] temp = line.split("\\s+");
	   	    Integer[] temp_int = new Integer[3]; 
	   	    temp_int[0] = Integer.parseInt(temp[0]);
	   	    temp_int[1] = Integer.parseInt(temp[1]);
	   	    temp_int[2] = Integer.parseInt(temp[2]);
	   	        	
	   	    Edge e = new Edge();
	   	    e.start_u = temp_int[0];
	   	   	e.end_v = temp_int[1];
	   	   	e.weight = temp_int[2];
	   	   	
	   	   	Edge e1 = new Edge();
	   	    e1.start_u = temp_int[1];
	   	   	e1.end_v = temp_int[0];
	   	   	e1.weight = temp_int[2];
	   	   	 	 	
            if(!final_map.containsKey(e.start_u)){
            	HashMap<Integer,Integer> adjList = new HashMap<Integer,Integer>();
            	adjList.put(e.end_v, e.weight);
            	final_map.put(e.start_u, adjList);
            }
            else{
            	HashMap<Integer,Integer> adjList = (HashMap<Integer, Integer>) final_map.get(e.start_u);
            	adjList.put(e.end_v,e.weight);
            }
            
            if(!final_map.containsKey(e1.start_u)){
            	HashMap<Integer,Integer> adjList = new HashMap<Integer,Integer>();
            	adjList.put(e1.end_v, e1.weight);
            	final_map.put(e1.start_u, adjList);
            }
            else{
            	HashMap<Integer,Integer> adjList = (HashMap<Integer, Integer>) final_map.get(e1.start_u);
            	adjList.put(e1.end_v,e1.weight);
            }

	   	}
   		
	}
	
	public void printInput()
	{
		// Create your iterator for your map
		 Iterator<Entry<Integer, Map<Integer, Integer>>> it = final_map.entrySet().iterator();
		 
		    while (it.hasNext()) {
		    	
		    	// the key/value pair is stored here in pairs
		        Map.Entry pair = (Map.Entry)it.next();
		        System.out.println(pair.getKey() + " = " + pair.getValue()); 
		    }
	}
	
	public void processingMST()
	{
		
		 int start = 1;
		 int size = 0;
		 
		 int[] visited  = new int[nodes];
		 for (int i = 0; i < visited.length; i++) 
		 {
			visited[i]=0;
		 }
		 
		 visited[0] = 1;
		 
		 while(size < edges)
		 {	
			 size++;
			 
			 if(visited[start - 1] == 1)
			 {	
				 HashMap<Integer,Integer> adjList = (HashMap<Integer, Integer>) final_map.get(start);
			 
			 	Iterator<Entry<Integer, Integer>> it = adjList.entrySet().iterator();
			 
			    while (it.hasNext()) 
			    {
			    	// the key/value pair is stored here in pairs
			        Map.Entry pair = (Map.Entry)it.next();
			        
			        Edge e = new Edge();
			        e.start_u = start;
			        e.end_v = (int) pair.getKey();
			        e.weight = (int) pair.getValue();
			        
			        if(visited[e.end_v - 1] == 0)
			        {
			        	H.insert(e);
			        }
			    } 
			    
			    Edge min = H.extractMin();
			    
			    if(visited[min.end_v - 1] == 0)
			    {
			    	minWeight = minWeight + min.weight;
			    	MST.add(new int[]{min.start_u, min.end_v});
			    	visited[min.end_v - 1] = 1;
			    	start = min.end_v;
			    }
			 }
			 
			 else 
			 { 
				 Edge min = H.extractMin();
				 if(visited[min.end_v - 1] == 0)
				 {
				    minWeight = minWeight + min.weight;
				    MST.add(new int[]{min.start_u, min.end_v, min.weight});
				    visited[min.end_v - 1] = 1;
				    start = min.end_v;
				 }
				 
			 }    
			    
		 }
		 
		 System.out.println(minWeight);
	  
	}
	
	public void getOutput(BufferedWriter writer) throws IOException
	{ 
		writer.write(minWeight + "\n");
		
		for(int i = 0; i < MST.size(); i++)
        {		 
	 		int [] a = new int [3];
	 		a = MST.get(i);
	 		writer.write(a[0]+" "+a[1]+"\n");
        } 
	}


}
   			
class Edge{
	int start_u;
	int end_v;
	int weight;
}

class Heap {
	
	   ArrayList<Edge> heap = new ArrayList<Edge>();      	// The heap array
	   private static final int d = 2; 						//The number of children each node has

	 
	    // Function to check if heap is empty
	    public boolean isEmpty( )
	    {
	    	return heap.size() == 0;
	    }
	 
	    // Function to  get index parent of i
	    private int parent(int i) 
	    {
	        return (i - 1)/d;
	    }
	 
	    // Function to get index of kth child of i
	    private int kthChild(int i, int k) 
	    {
	        return d * i + k;
	    }
	 
	    // Function to insert element
	    public void insert(Edge e)
	    {   
	        heap.add(e);
	        heapifyUp(heap.size() - 1);
	    }
	 
	    // Function to find least element
	    public Edge findMin( )
	    {          
	        return heap.get(0);
	    }
	 
	    // Function to delete min element
	    public Edge extractMin()
	    {
	        Edge keyItem = heap.get(0);
	        delete(0);
	        return keyItem;
	    }
	 
	    // Function to delete element at an index 
	    public Edge delete(int ind)
	    {
	        Edge keyItem = heap.get(ind);
	        heap.set(ind, heap.get(heap.size() - 1));
	        heapifyDown(ind);
	        heap.remove(heap.size() - 1);
	        return keyItem;
	    }
	 
	    // Function heapifyUp  
	    private void heapifyUp(int childInd)
	    {
	        Edge tmp = heap.get(childInd);    
	        while (childInd > 0 && tmp.weight < heap.get(parent(childInd)).weight)
	        {
	            heap.set(childInd, heap.get(parent(childInd)));
	            childInd = parent(childInd);
	        }                   
	        heap.set(childInd, tmp);
	    }
	 
	    // Function heapifyDown 
	    private void heapifyDown(int ind)
	    {
	        int child;
	        Edge tmp = heap.get(ind);
	        while (kthChild(ind, 1) < heap.size())
	        {
	            child = minChild(ind);
	            if (heap.get(child).weight < tmp.weight)
	                heap.set(ind, heap.get(child));
	            else
	                break;
	            ind = child;
	        }
	        heap.set(ind, tmp);
	    }
	 
	    // Function to get smallest child 
	    private int minChild(int ind) 
	    {
	        int bestChild = kthChild(ind, 1);
	        int k = 2;
	        int pos = kthChild(ind, k);
	        while ((k <= d) && (pos < heap.size()))
	        {
	            if (heap.get(pos).weight < heap.get(bestChild).weight) 
	                bestChild = pos;
	            pos = kthChild(ind, k++);
	        }    
	        return bestChild;
	    }
	 
	    // Function to print heap
	    public void printHeap()
	    {
	        System.out.print("\nHeap = ");
	        for (int i = 0; i < heap.size(); i++)
	            System.out.print(heap.get(i).weight +" ");
	        System.out.println();
	    }

}