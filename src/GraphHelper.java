import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class GraphHelper {
	public static void main(String[] args) {
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		try {
			while (true) {
				System.out.println("Enter the file name of the graph: ");
				String f_g = stdIn.readLine();

				BufferedReader reader2 = new BufferedReader(new FileReader(f_g));
				// Open file for g
				int m2 = 0, n2 = 0; // counts the rows and columns of g2
				ArrayList<String> lines2 = new ArrayList<>();
				do {
					lines2.add(reader2.readLine());
				} while (lines2.get(lines2.size() - 1) != null);
				// read each line of g2

				reader2.close();
				m2 = lines2.size() - 1;
				// get number of rows of g2

				if (m2 <= 0) {
					throw new Exception("Invalid Input");
				}
				n2 = lines2.get(0).split(" ").length;
				// get number of columns of g2

				// verify that matrix is a square
				if (n2 <= 0 || m2 != n2) {
					throw new Exception("Invalid Input");
				}
				Graph g = new Graph(n2);
				// instantiate g2

				// copy data into g2
				for (int i = 0; i < m2; i++) {
					String[] currentLine = lines2.get(i).split(" ");
					for (int j = 0; j < n2; j++) {
						g.g[i][j] = Integer.parseInt(currentLine[j]);
					}
				}


				System.out.println("Enter the permutation: ");
				int[] perm = Server.deserializePermutation(stdIn.readLine());
				System.out.println("The graph G' = perm(G) is ");
				System.out.println(g.getPermutation(perm));
				System.out.println("And it's serialized as: ");
				System.out.println(g.getPermutation(perm).serialize());
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
