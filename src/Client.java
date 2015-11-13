import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class Client {
	public static void main(String[] args) {
		 if ((args.length < 1) || (args.length > 2)) {
			 throw new IllegalArgumentException("Usage: <Server> [<Port>]]");
		 }
		 String server = args[0];
		 int port = (args.length == 2) ? Integer.parseInt(args[2]) : 11234;
		 try {
			Socket socket = new Socket(server, port);
			System.out.println("C: Connected.");
			Scanner in = new Scanner(socket.getInputStream());
			PrintWriter out = new PrintWriter(socket.getOutputStream(),
					true);
			
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
