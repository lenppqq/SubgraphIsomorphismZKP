import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class testClient {
	public static void main(String[] args) {
		if ((args.length < 2) || (args.length > 3)) {
			throw new IllegalArgumentException("Usage: g2 Server [Port]");
		}
		Graph q = null;
		Graph g1 = null, g2 = null;
		File f_g2 = new File(args[0]);
		String line;
		int m2 = 0, n2 = 0; // counts the rows and columns of g2
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

			// verify that matrix is a square
			if (n2 <= 0 || m2 != n2) {
				throw new Exception("Invalid Input");
			}
			g2 = new Graph(n2);
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

			// get g'
			Graph gPrime = g2.getSubgraph();
			System.out.println("G' is :");
			System.out.println(gPrime);

			// generate a permutation between g1 and g'
			int[] perm = new int[n2];
			for (int i = 0; i < n2; i++) {
				perm[i] = i;
			}
			Client.randomPermute(perm);
			// get g1
			g1 = gPrime.depermutation(perm);

			// send g1 and g2 to server
			out.println(g1.serialize());
			System.out.println("G1 is :");
			System.out.println(g1);

			System.out.println("beta s.t. G' = beta(G1) is:");
			System.out.println(Server.serializePermutation(perm));

			out.println(g2.serialize());
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
			Random random = new Random();
			int randomNumber;

			for (int round = 0; round < 100; round++) {
				randomNumber = random.nextInt();

				// get G
				System.out.println("Please enter graph Q s.t. Q is isomorphic to G2 ");
				q = new Graph(stdIn.readLine());

				CommitmentGraph commitQ = new CommitmentGraph(q, randomNumber);
				out.println(commitQ.serialize());
				String bString = in.readLine();
				int b = Integer.parseInt(bString);
				System.out.println(b);
				if (b == 0) {
					// the challenge bit is zero
					// get and send alpha
					System.out.println("Please enter alpha s.t. Q = alpha(G2): ");
					int[] alpha = Server.deserializePermutation(stdIn.readLine());
					out.println(Server.serializePermutation(alpha));
					// send graph q
					out.println(q.serialize());
					// send random n used for commitment
					out.println(randomNumber);
				} else {
					// the challenge bit is one
					// get pi
					// perm(G1) = G', alpha(G') = Q'
					System.out.println("Please enter pi s.t. Q' = pi(G1) : ");
					int[] pi = Server.deserializePermutation(stdIn.readLine());
					// send pi
					out.println(Server.serializePermutation(pi));

					// get Q'
					System.out.println("Please enter subgraph Q': ");
					Graph qPrime = new Graph(stdIn.readLine());
					// send Q'
					out.println(qPrime.serialize());

					// send random n used for commitment
					out.println(randomNumber);
				}
				String result = in.readLine();
				System.out.println(result);
				if (result.equals("Refused")) {
					System.out.println("Verifcation refused. Stop here.");
					return;
				}
			}
			System.out.println(in.readLine());
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
