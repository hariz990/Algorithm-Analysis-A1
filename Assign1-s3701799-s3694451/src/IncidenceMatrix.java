import java.io.*;
import java.util.*;


/**
 * Incident matrix implementation for the AssociationGraph interface.
 *
 * Your task is to complete the implementation of this class.  You may add methods, but ensure your modified class compiles and runs.
 *
 * @author Jeffrey Chan, 2019.
 */
public class IncidenceMatrix extends AbstractAssocGraph
{
	private HashMap<String, Integer> nodes = new HashMap<String, Integer>();
	private String[] allVerteces = new String[0];
	private int[][] matrix = new int[0][0];
	private MyPair[] pairs = new MyPair[0];
	private int numVerteces = 0;
	private int numPairs = 0;
	
	/**
	 * Contructs empty graph.
	 */
    public IncidenceMatrix() {
    	// Implement me!
    	
    } // end of IncidentMatrix()


    public void addVertex(String vertLabel) {
    	String[] _allVerteces = new String[allVerteces.length + 1];
    	
    	for(int i = 0; i < allVerteces.length; i++) {
    		_allVerteces[i] = allVerteces[i];
    	}
    	_allVerteces[_allVerteces.length - 1] = vertLabel;
    	allVerteces = _allVerteces;
    	
    	int[][] _matrix = new int[allVerteces.length + 1][allVerteces.length + 1];
    	
    	for(int x = 0; x < numVerteces; x++) {
    		for(int y = 0; y < numVerteces; y++) {
    			_matrix[x][y] = matrix[x][y];
        		
        	}
    	}
    	
    	nodes.put(vertLabel, numVerteces);
    	numVerteces++;
    	matrix = new int[numVerteces + 1][numVerteces + 1];
    	
    	for(int x = 0; x < numVerteces; x++) {
    		for(int y = 0; y < numVerteces; y++) {
    			matrix[x][y] = _matrix[x][y];
        		
        	}
    	}
        // Implement me!
    } // end of addVertex()
    
    public void printMatrix() {
    	System.out.println(matrix[1][2]);
    	for(int x = 0; x < numVerteces; x++) {
    		String message = "";
    		for(int y = 0; y < numVerteces; y++) {
    			message += matrix[x][y] + " ";
    		}
    		System.out.println(message);
    	}
    }


    public void addEdge(String srcLabel, String tarLabel, int weight) {
    	MyPair pair = new MyPair(tarLabel, weight);
    	
    	MyPair[] _pairs = new MyPair[numPairs + 2];
    	
    	for(int i = 0; i < numPairs; i++) {
    		_pairs[i] = pairs[i];
    	}
    	
    	_pairs[numPairs +1] = pair;
    	pairs = _pairs;
    	numPairs++;
    	
    	
        matrix[nodes.get(srcLabel)][nodes.get(tarLabel)] = weight;
        matrix[nodes.get(tarLabel)][nodes.get(srcLabel)] = -weight;
    } // end of addEdge()


	public int getEdgeWeight(String srcLabel, String tarLabel) {
		int value = EDGE_NOT_EXIST;
		if (nodes.get(srcLabel) == null || nodes.get(tarLabel) == null) {
			return value;
		}
		value = matrix[nodes.get(srcLabel)][nodes.get(tarLabel)];
		
		if (value == 0) {
			return EDGE_NOT_EXIST;
		}
		else {
			return value;
		}
		
	} // end of existEdge()


	public void updateWeightEdge(String srcLabel, String tarLabel, int weight) {
		if (nodes.get(srcLabel) == null || nodes.get(tarLabel) == null) {
			return;
		}
		
		matrix[nodes.get(srcLabel)][nodes.get(tarLabel)] = weight;
    } // end of updateWeightEdge()


    public void removeVertex(String vertLabel) {
    	
        //Returns, didnt find the node
    	if (nodes.get(vertLabel) == null) {
    		return;
    	}
    	
    	//Resizes the array
        int rows = numVerteces;
        int columns = numVerteces;
        int sourcearr[][] = matrix;
        int destinationarr[][] = new int[rows][columns];
        
        int REMOVE_ROW = nodes.get(vertLabel);
        int REMOVE_COLUMN = nodes.get(vertLabel);
        nodes.remove(vertLabel);
       
		int index = 0;
		for (int i = 0; i < allVerteces.length; i++) {
			if (allVerteces[i].equals(vertLabel)) {
				index = i;
				allVerteces[i] = null;
			}
		}
		
		for(int i = index; i < allVerteces.length - 1; i++) {
			allVerteces[i] = allVerteces[i + 1];
		}
		
		String[] _allVerteces = new String[allVerteces.length - 1];
		
		for(int i = 0; i < _allVerteces.length; i++) {
			_allVerteces[i] = allVerteces[i];
		}
		
		allVerteces = _allVerteces;
		
        
        int p = 0;
        for( int i = 0; i < rows; ++i)
        {
            if ( i == REMOVE_ROW)
                continue;


            int q = 0;
            for( int j = 0; j < columns; ++j)
            {
                if ( j == REMOVE_COLUMN)
                    continue;

                destinationarr[p][q] = sourcearr[i][j];
                ++q;
            }

            ++p;
        }
        
        for(int i = pairs.length - 1; i >= 0; i--)
        {
        	if(pairs[i] != null && pairs[i].getKey().equals(vertLabel))
        	{
        		pairs[i] = null;
        	}
        }
        //Changes the index of the hashmap to the new values
        
        Iterator<Map.Entry<String, Integer >>
        iterator = nodes.entrySet().iterator();
        
        while(iterator.hasNext()) {
        	
        	//Gets the entry
        	Map.Entry<String, Integer>
        		entry = iterator.next();
        	
        	if (entry.getValue() > index) {
        		entry.setValue(entry.getValue() - 1);
        	}
        }
        
        matrix = destinationarr;
        numVerteces--;
    	
    	
        // Implement me!
    } // end of removeVertex()


	public List<MyPair> inNearestNeighbours(int k, String vertLabel) {
        List<MyPair> neighbours = new ArrayList<MyPair>();
        
        for(int i = 0; i <= numPairs; i++) {
        	MyPair pair = pairs[i];
        	if (pair != null && pair.getKey().equals(vertLabel) && pair.getValue() > 0) {
        		neighbours.add(pairs[i]);
        	}
        	
        }
        
        Collections.sort(neighbours, new Comparator<MyPair>() {
        	  public int compare(MyPair c1, MyPair c2) {
        	    if (c1.getValue() > c2.getValue()) return -1;
        	    if (c1.getValue() < c2.getValue()) return 1;
        	    return 0;
        	  }});
        
        if (k != -1) {
      	  while (neighbours.size() > k) {
      	      	neighbours.remove(neighbours.size() -1);
      	      }
        }
        // Implement me!

        return neighbours;
    } // end of inNearestNeighbours()


    public List<MyPair> outNearestNeighbours(int k, String vertLabel) {
        List<MyPair> neighbours = new ArrayList<MyPair>();
        
        for(int i = 0; i < allVerteces.length; i++) {
        	MyPair pair = new MyPair(allVerteces[i], matrix[nodes.get(vertLabel)][nodes.get(allVerteces[i])]);
        	
        	if (pair.getValue() > 0) {
        		neighbours.add(pair);
        	}
        	
        }
        
        for(MyPair pair : neighbours) {
        	//System.out.println(pair.getValue() + " " + pair.getKey());
        }
        
        // Implement me!
        
        Collections.sort(neighbours, new Comparator<MyPair>() {
      	  public int compare(MyPair c1, MyPair c2) {
      	    if (c1.getValue() > c2.getValue()) return -1;
      	    if (c1.getValue() < c2.getValue()) return 1;
      	    return 0;
      	  }});
      
      if (k != -1) {
    	  while (neighbours.size() > k) {
    	      	neighbours.remove(neighbours.size() -1);
    	      }
      }

        return neighbours;
    } // end of outNearestNeighbours()


    public void printVertices(PrintWriter os) {
    	String message = "";
    	
    	for(int i = 0; i < allVerteces.length; i++) {
    		message += allVerteces[i] + " ";
    	}
    	
    	if (os == null) {
    		System.out.println(message);
    	}
    	else {
    		os.println(message);
    	}	
        // Implement me!
    } // end of printVertices()


    public void printEdges(PrintWriter os) {
    	
    	String message = "";
    	
    	for(int x = 0; x < allVerteces.length; x++) {
    		for(int y = 0; y < allVerteces.length; y++) {
    			
    			int weight = getEdgeWeight(allVerteces[x], allVerteces[y]);
    			if (weight > 0) {
    				message += allVerteces[x];
    				message += " " + allVerteces[y] + " " + weight;
    				message += "\n";
    			}
    			
        		
        	}
    		
    		//message += "\n";
    	}
    	
    	if (os == null) {
    		System.out.println(message);
    	}
    	else {
    		os.println(message);
    	}
        // Implement me!
    } // end of printEdges()

} // end of class IncidenceMatrix