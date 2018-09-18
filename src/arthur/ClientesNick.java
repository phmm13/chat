package arthur;
import java.net.Socket;

public class ClientesNick {
	
	private String apelidoCliente;
	private Socket clienteSocket;
	
	public ClientesNick(String apelido, Socket cliente) {
		this.apelidoCliente = apelido;
		this.clienteSocket = cliente;
	}
	
	public String getApelidoCliente() {
		return apelidoCliente;
	}
	public void setApelidoCliente(String apelidoCliente) {
		this.apelidoCliente = apelidoCliente;
	}
	public Socket getClienteSocket() {
		return clienteSocket;
	}
	public void setClienteSocket(Socket clienteSocket) {
		this.clienteSocket = clienteSocket;
	}

	
	
}
