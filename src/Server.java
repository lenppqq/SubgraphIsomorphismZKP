import java.lang.*;
import java.io.*;
import java.net.*;
import java.util.Scanner;

class Server {
	public static void main(String args[]) {
		String data = new String();
		try {
			ServerSocket serverSocket = new ServerSocket(11234);
			while (true) {
				Socket socket = serverSocket.accept();
				SocketAddress clientAddress = socket.getRemoteSocketAddress();
				System.out
						.print("S: Connection Incoming at \n" + clientAddress);
				Scanner in = new Scanner(socket.getInputStream());
				PrintWriter out = new PrintWriter(socket.getOutputStream(),
						true);
				out.print(data);
				out.close();
				socket.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}