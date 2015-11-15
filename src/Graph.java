
/**
 * This class is used to represent a graph
 * @author Peng Liang (liangp@purdue.edu)
 *
 */
public class Graph {
	
	public int[][] g;
	public int m;
	public int n;
	
	/**
	 * Constructor
	 * @param m number of rows of the graph
	 * @param n number of columns of the graph
	 */
	public Graph(int m, int n) {
		this.m = m;
		this.n = n;
		g = new int[m][n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				g[i][j] = 0;
			}
		}
	}
	public String serialize() {
		StringBuilder s = new StringBuilder();
		s.append(m);
		s.append(" ");
		s.append(n);
		s.append(" ");
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				s.append(g[i][j]);
				s.append(" ");
			}
		}
		return s.toString();
	}
	
	public Graph getCommitment(int v) {
		return null;
		
	}
}
