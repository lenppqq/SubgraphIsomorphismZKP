public class Graph {

	public int[][] g;
	public int n;

	public Graph(int n) {
		this.n = n;
		g = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				g[i][j] = 0;
			}
		}
	}

	public Graph(String s) throws Exception {
		deserialize(s);
	}

	public String serialize() {
		StringBuilder s = new StringBuilder();
		s.append(n);
		s.append(" ");
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				s.append(g[i][j]);
				s.append(" ");
			}
		}
		return s.toString();
	}

	public void deserialize(String s) throws Exception {
		String[] items = s.split(" ");
		int n = Integer.parseInt(items[0]);
		if (items.length != n * n + 1) {
			throw new Exception("Invalid input for deseriablization!");
		}
		g = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				g[i][j] = Integer.parseInt(items[n * i + j + 1]);
			}
		}
		this.n = n;
	}

	public Graph getCommitment(int v) {
		return null;
	}
	
	public Graph getPermuatation(int[] p) {
		return null;
	}

	public Graph getSubgraph() {
		return null;
	}
}
