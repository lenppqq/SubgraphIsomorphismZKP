import java.lang.*;
import java.io.*;
import java.net.*;


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
				out.println(data);
				Thread.sleep(2300);
				out.println(data);
				out.close();
				socket.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}