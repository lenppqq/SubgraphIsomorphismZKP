import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;

public class CommitmentGraph {
	public String[][] g; // commitment graph
	public int n;

	/**
	 * Constructor
	 * @param n number of vertices of the graph
	 */
	public CommitmentGraph(int n) {
		this.n = n;
		g = new String[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				g[i][j] = "";
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
		String[] items = s.split(" ");
		int n = Integer.parseInt(items[0]);
		if (items.length != n * n + 1) {
			throw new Exception("Invalid input for deseriablization!");
		}
		CommitmentGraph CG = new CommitmentGraph(n);
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				CG.g[i][j] = items[n * i + j + 1];
			}
		}
		return CG;
	}

	public boolean verifyCommitmentAt(int i, int j, int value, int r) {
		return g[i][j] != null && g[i][j].equals(this.commitment(i, j, value, r));
	}

	public void generateCommitmentAt(int i, int j, int value, int r) {
		g[i][j] = commitment(i, j, value, r);
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