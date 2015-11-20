import java.lang.*;
import java.io.*;
import java.net.*;
import java.util.Random;


class Server {
	public static void main(String args[]) {
		String data = new String();
		data = "Asd";
		try {
			ServerSocket serverSocket = new ServerSocket(11234);
			while (true) {
				Socket socket = serverSocket.accept();
				SocketAddress clientAddress = socket.getRemoteSocketAddress();
				System.out
						.print("S: Connection Incoming at \n" + clientAddress);
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter out = new PrintWriter(socket.getOutputStream(),
						true);
				if (verify(in, out)) {
					out.println("Verified");
				} else {
					out.println("Refused");
				}
				out.close();
				socket.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean verify(BufferedReader in, PrintWriter out) {
		// first read graph G1 and G2 from the prover
		Graph G1, G2;
		try {
			G1 = new Graph(in.readLine());
			G2 = new Graph(in.readLine());
		} catch (Exception e) {
			System.out.println("Failed to read shared knowledge G1 & G2 from verifier!");
			System.out.println("Verification failed!");
			return false;
		}
		Random random = new Random();
		// 100 rounds
		for (int i = 0; i < 100; i++) {
			// read the commitment graph from prover
			CommitmentGraph commitmentQ;
			try {
				String commitmentString = in.readLine();
				commitmentQ = CommitmentGraph.deserialize(commitmentString);
			} catch (Exception e) {
				System.out.println("Failed to read commitment graph from verifier!");
				System.out.println("Verification failed!");
				return false;
			}
			// send the challenge to prover
			int b = random.nextInt(1);
			out.println(b);

			// read the response from prover
			if (b == 0) {
				if (!verify(in, commitmentQ, G2)) {
					return false;
				}
			} else {
				// the request should be pi, and Q'
				if (!verify(in, commitmentQ, G1)) {
					return false;
				}
			}
			out.println("Continue");
		}
		return true;
	}

	private static boolean verify(BufferedReader in, CommitmentGraph commitmentQ, Graph G) {
		// if it's challenge 0, then the request should be alpha, and Q
		// else, the request should be pi, and Q'
		Graph Q;
		int[] permutation;
		int r;
		try {
			// parse permutation 
			String permString = in.readLine();
			permutation = deserializePermutation(permString);

			// pasrse response graph Q(or Q')
			String QString = in.readLine();
			Q = Graph.deserialize(QString);
					
			// parse the random r used to verify the commitment
			String rs = in.readLine();
			r = Integer.parseInt(rs);
		} catch (Exception e) {
			System.out.println("Ill-formatted responses from the verifier: " + e.getMessage());
			System.out.println("Verification failed!");
			return false;
		} 

		// check Q = alpha(G2) or Q' = pi(G1)
		if (!G.isIsomorphic(Q, permutation)) {
			System.out.println("Verification of the permutation failed!");
			return false;
		}
		// check Q/Q' is the graph committed
		if (!commitmentQ.verifyCommitment(Q, r)) {
			System.out.println("Q is not the commited graph");
			return false;
		}
		return true;
	}

	public static int[] deserializePermutation(String ps) throws Exception{
		String[] ss = ps.split(" ");
		if (ss.length <= 0) {
			throw new Exception("Invalid Input: Empty Permutation!");
		}
		int[] p = new int[ss.length];
		for (int i = 0; i < p.length; i++) {
			try {
				p[i] = Integer.parseInt(ss[i]);
			} catch (Exception e) {
				throw new Exception("Invalid Input: Permutation has non-integer entry!");
			}
		}
		return p;
	}

	public static String serializePermutation(int[] p) {
		if (p == null || p.length == 0) {
			return "";
		}
		String s = "" + p[0];
		for (int i = 1; i < p.length; i++) {
			s += " ";
			s += p[i];
		}
		return s;
	}

}