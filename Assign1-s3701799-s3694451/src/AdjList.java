import java.io.*;
import java.util.*;

/**
 * Adjacency list implementation for the AssociationGraph interface.
 *
 * Your task is to complete the implementation of this class.  You may add methods, but ensure your modified class compiles and runs.
 *
 * @author Jeffrey Chan, 2019.
 */
public class AdjList extends AbstractAssocGraph
{
	
	private HashMap<String, Integer> nodes = new HashMap<String, Integer>();
	private Node[] nodeArray = new Node[100];
	private int numNodes = 0;
	
	//Make a big array of node, if it gets filled increase size
	//Hasmap has to be from string -> int where int is index in the array where you can find the node
	
    /**
	 * Contructs empty graph.
	 */
    public AdjList() {
    	 // Implement me!

    } // end of AdjList()
    
   
    public void addVertex(String vertLabel) {
    	if (numNodes >= nodeArray.length - 1) {
    		Node[] _nodeArray = new Node[numNodes * 2];
    		
    		for(int i = 0; i <= numNodes; i++) {
    			_nodeArray[i] = nodeArray[i];
    			
    		}
    		
    		nodeArray = _nodeArray;
    	}
    	Node node = new Node();
    	nodeArray[numNodes] = node;
    	nodes.put(vertLabel, numNodes);
    	numNodes++;
        // Implement me!
    } // end of addVertex()


    public void addEdge(String srcLabel, String tarLabel, int weight) {
    	
    	//Checks if both labels exist
    	if ((nodes.get(srcLabel) == null || nodes.get(tarLabel) == null)) {
    		return;
    	}
    	
    	
    	nodeArray[nodes.get(srcLabel)].addEdge(tarLabel, weight);
        // Implement me!
    } // end of addEdge()


    //Returns -1 if edge doesnt exist
    public int getEdgeWeight(String srcLabel, String tarLabel) {
    	
    	//Returns if either of the labels doesnt exist
    	if ((nodes.get(srcLabel) == null || nodes.get(tarLabel) == null)) {
    		return -1;
    	}
    	if (nodeArray[nodes.get(srcLabel)].getPair(tarLabel) == null) {
    		return EDGE_NOT_EXIST;
    	}
    	return nodeArray[nodes.get(srcLabel)].getPair(tarLabel).getValue();
    } // end of existEdge()


    public void updateWeightEdge(String srcLabel, String tarLabel, int weight) {
       
    	//returns if either nodes doesnt exist
    	if (nodes.get(srcLabel) == null || nodes.get(tarLabel) == null) {
    		return;
    	}
    	else if (nodeArray[nodes.get(srcLabel)].getPair(tarLabel) == null) {
    		return;
    	}
    	
    	if (weight == 0) {
    		nodeArray[nodes.get(srcLabel)].removeEdge(tarLabel);
    		
    	}
    	else {
    		nodeArray[nodes.get(srcLabel)].getPair(tarLabel).setValue(weight);
    	}
    	
    } // end of updateWeightEdge()


    public void removeVertex(String vertLabel) {
    	Iterator<Map.Entry<String, Integer> > 
        iterator = nodes.entrySet().iterator();
    	boolean isKeyPresent = false;
    	
    	while (iterator.hasNext()) 
    	{ 
    		  
            // Get the entry at this iteration 
            Map.Entry<String, Integer> 
                entry = iterator.next(); 
  
            // Check if this key is the required key 
            if (vertLabel.equals(entry.getKey())) 
            { 
                isKeyPresent = true; 
            } 
        }
    	
    	if(isKeyPresent == true)
    	{
    		int index = nodes.get(vertLabel);
        	nodeArray[index] = null;
        	
        	for(int i = 0; i < numNodes; i++) {
        		if (nodeArray[i] != null) {
        			nodeArray[i].removeEdge(vertLabel);
        		}
        		
        	}
        	nodes.remove(vertLabel);
    	}
        // Implement me!
    } // end of removeVertex()


    public List<MyPair> inNearestNeighbours(int k, String vertLabel) {
        List<MyPair> neighbours = new ArrayList<MyPair>();
        
        //Loop though nodes
        //Store all edges in neighbours list if edge goes to VertLabel
        //Sort neighbours list by weight
        //Remove elements in neighbours until the size is k
        
        Set set = nodes.entrySet();
        
        Iterator iterator = set.iterator();
        
        while (iterator.hasNext()) {
        	Map.Entry mentry = (Map.Entry)iterator.next();
        	String key = (String)mentry.getKey();
        	Node node = nodeArray[nodes.get(key)];
        	
        	for(MyPair pair : node.getPairArray()) {
        		if (pair.getKey().equals(vertLabel)) {
        			MyPair _pair = new MyPair(key, pair.getValue());
        			neighbours.add(_pair);
        		}
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
        
        for(MyPair pair : neighbours) {
        	//System.out.println(pair.getKey() + " " + pair.getValue());
        }

        return neighbours;
    } // end of inNearestNeighbours()


    public List<MyPair> outNearestNeighbours(int k, String vertLabel) {
        List<MyPair> neighbours = new ArrayList<MyPair>();
        
        for(MyPair pair : nodeArray[nodes.get(vertLabel)].getPairArray()) {
        	neighbours.add(pair);
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
    } // end of outNearestNeighbours()


    public void printVertices(PrintWriter os) {
    	
    	String message = "";
    	
    	Set set = nodes.entrySet();
        
        Iterator iterator = set.iterator();
        
        while (iterator.hasNext()) {
        	Map.Entry mentry = (Map.Entry)iterator.next();
        	String key = (String)mentry.getKey();
        	message += key + " ";
        }
        
        if (os != null) {
        	os.println(message);
        }
        else {
        	System.out.println(message);
        }
        
        
    	
    	
        // Implement me!
    } // end of printVertices()


    public void printEdges(PrintWriter os) {
    	String message = "";
    	
        Set set = nodes.entrySet();
        
        Iterator iterator = set.iterator();
        
        while (iterator.hasNext()) {
        	Map.Entry mentry = (Map.Entry)iterator.next();
        	String key = (String)mentry.getKey();
        	Node node = nodeArray[nodes.get(key)];

        	
        	for(MyPair pair : node.getPairArray()) {
        		if (pair != null) {
                	message += "\n";
                	message += key + " ";
        			message += pair.getKey() + " " + pair.getValue();
        		}
        		
        	}
        	
        }
        
        if (os != null) {
        	os.println(message);
        }
        else {
        	System.out.println(message);
        }
        
        
    	
        // Implement me!
    } // end of printEdges()


    protected class Node {
    	
    	private MyPairList pairs = new MyPairList();
    	
    	public Node() {
    	}
    	
    	public void addEdge(String tarLabel, int weight) {
    		MyPair pair = new MyPair(tarLabel, weight);
    		pairs.addPair(pair);
    	}
    	
    	public void removeEdge(String tarLabel) {
    		pairs.removePair(tarLabel);
    	}
    	
    	
    	public MyPair getPair(String tarLabel) {
    		if (pairs.getPair(tarLabel) == null) {
    			return null;
    		}
    		return pairs.getPair(tarLabel);
    	}
    	
    	public MyPair[] getPairArray() {
    		return pairs.pairs;
    	}
    	
    	protected class MyPairList {
    		
    		private MyPair[] pairs;
    		
    		public MyPairList() {
    			pairs = new MyPair[0];
    		}
    		
    		public int getNumPairs() {
    			return pairs.length;
    		}
    		
    		public void addPair(MyPair pair) {
    			
    			//returns as it already has a pair with the same values
    			if (getPairIndex(pair) != -1) {
    				return;
    			}
    			
    			MyPair[] _pairs = new MyPair[pairs.length + 1];
    			
    			for (int i = 0; i < pairs.length; i++) {
    				_pairs[i] = pairs[i];
    			}
    			
    			_pairs[_pairs.length - 1] = pair;
    			
    			pairs = _pairs;
    		}
    		
    		//updates the pair with the correct target label
    		public void updatePair(String tarLabel, int weight) {
    			for(MyPair pair : pairs) {
    				if (pair.getKey().equals(tarLabel)) {
    					pair.setValue(weight);
    					return;
    				}
    			}
    		}
    		
    		public void removePair(String tarLabel) {
    			removePair(getPairIndex(getPair(tarLabel)));
    		}
    		
			//Removes a pair at a specific index
    		public void removePair(int index) {
    			
    			//Returns, didnt find the node
    			if (index == -1) {
    				return;
    			}
    			
    			pairs[index] = null;
    			
    			for(int i = index; i < pairs.length - 1; i++) {
    				pairs[i] = pairs[i + 1];
    			}
    			
    			MyPair[] _pairs = new MyPair[pairs.length - 1];
    			
    			for(int i = 0; i < _pairs.length; i++) {
    				_pairs[i] = pairs[i];
    			}
    			
    			pairs = _pairs;
    			
    		}
    		
    		//Gets the edge from the tarlabel
    		public MyPair getPair(String tarLabel) {
    			for(MyPair pair : pairs) {
    				if (pair != null && pair.getKey().equals(tarLabel)) {
    					return pair;
    				}
    			}
    			
    			return null;
    		}
    		
    		//returns -1 if pair is not found
    		public int getPairIndex(MyPair _pair) {
    			if (_pair == null) {
    				return -1;
    			}
    			for(int i = 0; i < pairs.length; i++) {
    				if (pairs[i] != null && pairs[i].getKey().equals(_pair.getKey()) && pairs[i].getValue() == _pair.getValue()) {
    					return i;
    				}
    			}
    			
    			return -1;
    		}
    		
    		public MyPair getPair(int index) {
    			return pairs[index];
    		}
    		
    		public void clearList() {
    			for(int i = pairs.length - 1; i >= 0; i--) {
    				pairs[i] = null;
    			}
    			
    			pairs = new MyPair[0];
    		}
    	}
    }
    	
} // end of class AdjList