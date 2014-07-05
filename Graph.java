import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Find dependency files in order
 * @author Fei
 */
public class Graph {
  Map<String, Node> allNodesMap;

  class Node {
    public final String name;
    public final HashSet<Edge> inEdges;
    public final HashSet<Edge> outEdges;
    public Node(String name) {
      this.name = name;
      inEdges = new HashSet<Edge>();
      outEdges = new HashSet<Edge>();
    }

    public Node addEdge(Node node) {
      Edge e = new Edge(this, node);
      outEdges.add(e);
      node.inEdges.add(e);
      return this;
    }

    @Override
    public String toString() {
      return name;
    }

    @Override
    public boolean equals(Object o) {
      return (o instanceof Node) && (((Node)o).toString()).equals(this.toString());
    }

    @Override
    public int hashCode() {
      return name.hashCode();
    }
  }

  class Edge{
    public final Node from;
    public final Node to;
    public Edge(Node from, Node to) {
      this.from = from;
      this.to = to;
    }
    @Override
    public boolean equals(Object obj) {
      Edge e = (Edge)obj;
      return e.from == from && e.to == to;
    }
  }

  public Graph() {
    allNodesMap = new HashMap<String, Node>();
  }

  public List<String> topologicalSort(List<String> inputs) throws Exception {
    buildGraph(inputs);

    List<String> outputs = new ArrayList<String>();
    //noDepentSet <- Set of all nodes with no incoming edges
    Set<Node> noDepentSet = new HashSet<Node>();
    for(Node n : allNodesMap.values()) {
      if(n.inEdges.size() == 0){
        noDepentSet.add(n);
      }
    }

    //while noDepentSet is non-empty do
    while(!noDepentSet.isEmpty()){
      //remove a node n from noDepentSet
      Node n = noDepentSet.iterator().next();
      noDepentSet.remove(n);
      // add candidate into final output list
      outputs.add(n.toString());

      //for each node m with an edge e from n to m do
      for(Iterator<Edge> it = n.outEdges.iterator();it.hasNext();) {
        //remove edge e from the graph
        Edge e = it.next();
        Node m = e.to;
        it.remove(); //Remove edge from n
        m.inEdges.remove(e); //Remove edge from m

        //if m has no other incoming edges then insert m into S
        if(m.inEdges.isEmpty()){
          noDepentSet.add(m);
        }
      }
    }

    //Check to see if all edges are removed
    boolean cycle = false;
    for(Node n : allNodesMap.values()){
      if(!n.inEdges.isEmpty()){
        cycle = true;
        break;
      }
    }

    if(cycle) {
      throw new Exception("Cycle present, topological sort not possible");
    }

    return outputs;
  }

  private final Pattern splitPattern = Pattern.compile("\\s");

  private String[] getTokens(String s) {
    return splitPattern.split(s);
  }

  private void buildGraph(List<String> inputs) {
    for (String line: inputs) {
      String[] tokens = getTokens(line);
      String fromName = tokens[0];
      if (!allNodesMap.containsKey(fromName))
        allNodesMap.put(fromName, new Node(fromName));
      Node from = allNodesMap.get(fromName);
      for (int i = 1; i < tokens.length; i++) {
        String toName = tokens[i];
        if (!allNodesMap.containsKey(toName))
          allNodesMap.put(toName, new Node(toName));
        Node to = allNodesMap.get(toName);
        from.addEdge(to);
      }
    }
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    List<String> inputs = new ArrayList<String>();
    String line = "";
    while (scanner.hasNextLine() && !(line = scanner.nextLine()).equals("")) {
      inputs.add(line);
    }

    List<String> output;
    try {
      output = new Graph().topologicalSort(inputs);
      for (String node: output) {
        System.out.print(node + " ");
      }
      System.out.println();
    } catch (Exception e) {
      System.err.println(e.getMessage());
      System.exit(1);
    }
  }
}