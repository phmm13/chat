package arthur;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

public class Servidor{
    private int porta;
    private List<ClientesNick> clientes;

    public List<ClientesNick> getClientes() {
		return clientes;
	}

	public void setClientes(List<ClientesNick> clientes) {
		this.clientes = clientes;
	}

	private static Servidor uniqueInstance = new Servidor();

    private Servidor() {
        this.porta = 12345;
        this.clientes = new ArrayList<>();
    }

    public static Servidor getInstance() {
        return uniqueInstance;
    }


    public void executa() throws IOException{
        try (ServerSocket servidor = new ServerSocket(porta)) {
        	System.out.println("Servidor Iniciado");

            while (true) {
                Socket cliente = servidor.accept();
                System.out.println("[" + cliente.getRemoteSocketAddress() + "] se conectou!!");
                clientes.add(new ClientesNick("gigdin", cliente));
                TrataMsg tM = new TrataMsg(cliente);
                new Thread(tM).start();
                
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}