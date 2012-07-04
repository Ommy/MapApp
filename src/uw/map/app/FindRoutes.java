import java.util.*;
import java.io.*;

class Vertex implements Comparable<Vertex>
{
	public final String name;
	public Edge[] adjacencies;
	public double minDistance = Double.POSITIVE_INFINITY;
	public Vertex previous;
	public Vertex(String argName) { name = argName; }
	public String toString() { return name; }
	public int compareTo(Vertex other)
	{
	    return Double.compare(minDistance, other.minDistance);
	}
}


class Edge
{
	public final Vertex target;
	public final double weight;
	public Edge(Vertex argTarget, double argWeight)
	{ target = argTarget; weight = argWeight; }
}

public class FindRoutes{	
	
	public static void computePaths(Vertex source)
    {
        source.minDistance = 0.;
        PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
      	vertexQueue.add(source);

	while (!vertexQueue.isEmpty()) {
	    Vertex u = vertexQueue.poll();

            // Visit each edge exiting u
            for (Edge e : u.adjacencies)
            {
            	if (e != null){
            		Vertex v = e.target;
            		double weight = e.weight;
                	double distanceThroughU = u.minDistance + weight;
                	if (distanceThroughU < v.minDistance) {
                		vertexQueue.remove(v);
                		v.minDistance = distanceThroughU ;
                		v.previous = u;
                		vertexQueue.add(v);
                	}
            	}
            }
        }
    }

    public static List<Vertex> getShortestPathTo(Vertex target)
    {
        List<Vertex> path = new ArrayList<Vertex>();
        for (Vertex vertex = target; vertex != null; vertex = vertex.previous)
            path.add(vertex);
        Collections.reverse(path);
        return path;
    }
    
    public static void print(String s)
    {
    	System.out.println(s);
    }



	
	public static void main(String[] args) throws IOException
	{
		try
		{
			File dataIn = new File("save.txt");
			FileInputStream fS = new FileInputStream(dataIn);
			DataInputStream in = new DataInputStream(fS);
			BufferedReader bf = new BufferedReader(new InputStreamReader(in));
			ArrayList<String> dataLines = new ArrayList<String>(10);
			ArrayList<String> dataLines2 = new ArrayList<String>(10);//is this needed?
			String sT;	
			String stt;
			int k = 0;
			int m1 = 0;
			int m2 = 0;
			
			while ((sT = bf.readLine())!= null)
			{
				dataLines.add(sT);
				k = sT.indexOf(" ");
				stt = sT.substring(k+1,sT.indexOf(" ",k+1)) + " " + sT.substring(0,k) + sT.substring(sT.indexOf(" ",k+1),sT.lastIndexOf(" ")) + " " + sT.substring(k+1,sT.indexOf(" ",k+1)) + "-"  + sT.substring(0,k);
				dataLines.add(stt);
			}
			//ENDING SIZE IS NOW LIKE 544
			/*
			for (int a = 0; a < dataLines.size()-1; a++){
				
			}*/
			ArrayList<String> verteces = new ArrayList<String>(10);
			ArrayList<String> weight = new ArrayList<String>(10);
			ArrayList<String> node = new ArrayList<String>(10);
			String temp;
			int toFirstSpace=0,toSecondSpace=0,toThirdSpace=0;
			for (int i = 0; i < dataLines.size();i++)
			{
			    //node will be the specific node
			    temp = dataLines.get(i);
			    temp = temp.trim();
			    toFirstSpace = temp.indexOf(" ");
			    node.add(i,temp.substring(0,toFirstSpace));
			    //verteces will be the connection
			    toSecondSpace = temp.indexOf(" ",toFirstSpace+1);
			    toThirdSpace = temp.lastIndexOf(" ");
			    verteces.add(i,(temp.substring(toThirdSpace)).trim());
			    //weight will be the distance between the two
			    weight.add(i,temp.substring(toSecondSpace+1,toThirdSpace));
			    //System.out.println(node.get(i));
			}
			
			int max = 0;
			for(int p = 0; p < node.size(); p++){
				if (Integer.parseInt(node.get(p)) > max)
					max = Integer.parseInt(node.get(p));
			}
			
			Vertex[] v = new Vertex[max+1];
			int[] vi = new int[max+1];
			
			for (int p = 1; p < max+1; p++)
			{
				v[p] = new Vertex(p+"");
				vi[p] = 0;
			}
			
			for (int p = 0; p < node.size(); p++){
				vi[Integer.parseInt(node.get(p))]++;
			}
			
			
			
			// edges
			for (int p = 1; p < max+1; p++){
				v[p].adjacencies = new Edge[vi[p]];
			}
			
			
			int l = 0;
			
			for (int i = 0; i< verteces.size(); i++)
			{
				int x1 = Integer.parseInt(((verteces.get(i)).substring(verteces.get(i).indexOf("-")+1)).trim()); // this is the connected node to the current node.
				int y = Math.abs(Integer.parseInt(node.get(i))); // this is the connected node to the current node.
				l = v[y].adjacencies.length;
				if (vi[y] > 0){
				v[y].adjacencies[l-vi[y]] = new Edge(v[x1],Integer.parseInt(weight.get(i)));
				vi[y]--;
				}		
			}
			
			
			/*
			int maxTimes = 1; // max number of times 1 node is connected to others
			int count = 0;
			for (int i = 1; i < dataLines.size(); i++)
			{
				if (!(dataLines.get(i)).equals(dataLines.get(i-1)))//a new node appears
				{
					if (count > maxTimes)
						maxTimes = count;
					count = 1;
				}
				count++;
			}
			//String[][] connTO = new String[265][maxTimes];
			Vertex[] v = new Vertex[267];
			for (int i = 0; i < 267;i++)
			{
				v[i] = new Vertex(verteces.get(i));
			}
			v[0] = new Vertex(node.get(0));
			int x = 1;
			int[] numEaNode = new int[267];
			int eaCount = 0;
			for (int i = 1;i<267;i++)
			{
			    if (node.get(i) != node.get(i-1))
			    {
					v[i] = new Vertex(i+"");

					eaCount++;
			    }
			    numEaNode[eaCount]++;
			}
			for (int i = 0; i < 267;i++)
			{
				v[i].adjacencies = new Edge[maxTimes];
			}
			
			int j1=0;
			Edge[] p = new Edge[maxTimes];
			for (int i = 0; i< verteces.size()-1; i++)
			{
				int x1 = Integer.parseInt(((verteces.get(i)).substring(verteces.get(i).indexOf("-")+1)).trim()); // this is the connected node to the current node.
				int y = Math.abs(Integer.parseInt(node.get(i))); // this is the connected node to the current node.
				v[y].adjacencies[j1] = new Edge(v[x1],Integer.parseInt(weight.get(i)));
				//print(y + " " + v[y].name);
				j1++;
			
				if (!((node.get(i+1)).equals(node.get(i))) || j1 == maxTimes)
				{
					j1 = 0;
				}
				
			}
			
			*/
			
			 computePaths(v[266]);
			/*for (Vertex d : v)
				{
					if (d !=null){
						/*if (d.minDistance != Double.POSITIVE_INFINITY){
							//System.out.println("Distance to " + (Integer.parseInt(d.name)) + ": " + d.minDistance);
							List<Vertex> path = getShortestPathTo(d);
							//System.out.println("Shortest path: " + path);}

					}
				}*/
				
			 print(v[1].minDistance+"");
			 List<Vertex> path = getShortestPathTo(v[1]);
			 print(path+"");


		}
		catch (FileNotFoundException c)
		{
			System.out.print("NOPE1");
		}
		catch(IOException e)
		{
			System.out.print("NOPE2");
		}

	}

}
