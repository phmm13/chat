import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String args[]) {
		try {
			SocketServer.getInstance().init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}