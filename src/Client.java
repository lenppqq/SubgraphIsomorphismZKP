import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Client {
	public static void main(String[] args) {
		if ((args.length < 2) || (args.length > 3)) {
			throw new IllegalArgumentException("Usage: g1 g2 Server [Port]");
		}
		Graph p = null, q = null;
		Graph g1 = null, g2 = null;
		File f_g2 = new File(args[0]);
		String line;
		int m1 = 0, m2 = 0, n1 = 0, n2 = 0;
		try {
			BufferedReader reader2 = new BufferedReader(new FileReader(f_g2));
			// Open file for g2

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

			if (n2 <= 0 || m2 != n2) {
				throw new Exception("Invalid Input");
			}
			g1 = new Graph(n2);
			// instantiate g2

			// copy data into g2
			for (int i = 0; i < m2; i++) {
				String[] currentLine = lines2.get(i).split(" ");
				for (int j = 0; j < n2; j++) {
					g2.g[i][j] = Integer.parseInt(currentLine[j]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		String server = args[1];

		int port = (args.length == 3) ? Integer.parseInt(args[2]) : 11234;
		try {
			// establish connection
			Socket socket = new Socket(server, port);
			System.out.println("C: Connected.");
			BufferedReader in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

			// get g2
			g2 = g1.getSubgraph();
			
			// send g1 and g2 to server
			out.println(g1.serialize());
			out.println(g2.serialize());

			// generate permutation alpha for G2
			int[] alpha = new int[n2];
			for (int i = 0; i < n2; i++) {
				alpha[i] = i;
			}
			randomPermute(alpha);
			q = g2.getPermuatation(alpha);

			line = in.readLine();
			System.out.println(line);
			out.println(q.serialize());
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void randomPermute(int[] data) {
		int size = data.length;
		Random r = new Random();
		for (int i = 0; i < size; i++) {
			int a = r.nextInt(size - i);
			int b = size - i - 1;
			int tmp = data[a];
			data[a] = data[b];
			data[b] = tmp;
		}
	}
}
