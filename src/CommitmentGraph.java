import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;

public class CommitmentGraph {
	public String[][] g; // commitment graph
	public int n;

	/**
	 * Constructor
	 * @param G the graph used to generate commitment from
	 * @param r a random used to generate the commitment
	 */
	public CommitmentGraph(Graph G, int r) {
		this.n = G.n;
		g = new String[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				g[i][j] = commitment(i, j, G.g[i][j], r);
			}
		}
	}

	/**
	 * Constructor
	 * @param s the string used for deserialization
	 */
	public CommitmentGraph(String s) throws Exception{
		String[] items = s.split(" ");
		int n = Integer.parseInt(items[0]);
		if (items.length != n * n + 1) {
			throw new Exception("Invalid input for deseriablization!");
		}
		this.n = n;
		g = new String[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				g[i][j] = items[n * i + j + 1];
			}
		}
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

	public static CommitmentGraph deserialize(String s) throws Exception {
		return new CommitmentGraph(s);
	}

	/**
	 * @param G a graph to be checked
	 * @param r the random used to generate the commitment graph
	 * returns true if for each pair (i, j), g[i][j] = Hash(i || j || G.g[i][j] || r)
	 */
	public boolean verifyCommitment(Graph G, int r) {
		if (G.n != this.n) return false;

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (!verifyCommitmentAt(i, j, G.g[i][j], r)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * @param i the index of the entry in the adjacency matrix
	 * @param value the value at adj[i][j]
	 * @param r the random used to generate the commitment graph
	 * returns true if g[i][j] = Hash(i || j || value || r)
	 */

	public boolean verifyCommitmentAt(int i, int j, int value, int r) {
		return g[i][j] != null && g[i][j].equals(this.commitment(i, j, value, r));
	}

	private String commitment(int i, int j, int value, int r) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA");
			String inputString = "" + i + j + g[i][j] + r;
			md.update(inputString.getBytes("UTF-8"));
     		byte[] commitmentBytes = md.digest();
     		String commitmentString = DatatypeConverter.printHexBinary(commitmentBytes);
     		return commitmentString;
     	} catch (Exception e) {
     		System.err.println("error");
     		return null;
     	}
	}

}