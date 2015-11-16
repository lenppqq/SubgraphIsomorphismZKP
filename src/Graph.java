
/**
 * This class is used to represent a graph
 * @author Peng Liang (liangp@purdue.edu)
 *
 */
public class Graph {
	
	public int[][] g;
	public int n;
	
	/**
	 * Constructor
	 * @param n number of vertices of the graph
	 */
	public Graph(int n) {
		this.n = n;
		g = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				g[i][j] = 0;
			}
		}
	}

	/**
	 * Constructor constructs a new graph P = permutation(G)
	 * @param G a graph
	 * @param permutation a permutation of G's nodes
	 */
	public Graph(Graph G, int[] permutation) throws Exception{
		this.n = G.n;
		if (n != permutation.length) {
			throw new Exception("invalid permutation on graph G!");
		}
		g = new int[n][n];
		for (int i = 0; i < n; i++){
			for (int j = 0; j < n; j++){
				g[permutation[i]][permutation[j]] = G.g[i][j];
			}
		}
	}

	public void addEdge(int v1, int v2) {
		g[v1][v2] = 1;
		g[v2][v1] = 1;
	}

	/** 
	 * Verify if P = permutation(G)
	 * @param G a graph
	 * @param permutation a permutation of G's nodes
	 */
	public boolean isIsomorphic(Graph G, int[] permutation) {
		if ((G.n != this.n) || (permutation.length != G.n)) {
			return false;
		}

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++){
				if (g[permutation[i]][permutation[j]] != G.g[i][j]) {
					return false;
				}
			}
		}
		return true;
	}


}
