import java.net.Socket;

public class Client {

	private Socket socket;
	private String nickName;
	
	public Client(Socket socket,String nick){
		this.socket = socket;
		this.nickName = nick;
	}
	public Client(Socket socket) {
		this.socket = socket;
	}

	public Client() {
	}
	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	
}