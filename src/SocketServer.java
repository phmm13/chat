import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 * http://www.mballem.com/post/chat-multi-usuarios-com-socket/?i=1
 */
public class SocketServer extends Thread {

	private int porta;
	private Socket conexao;
	private String nomeCliente;
	private List<Client> clientes;

	private static SocketServer uniqueInstance = new SocketServer();

	private SocketServer() {
		this.porta = 12345;
		this.clientes = new ArrayList<>();
	}

	public List<String> participantes() {
		List<String> listaNomes = new ArrayList<>();
		for (Client cliente : getInstance().clientes) {
			listaNomes.add(cliente.getNickName());
		}
		return listaNomes;
	}

	public static SocketServer getInstance() {
		return uniqueInstance;
	}

	@SuppressWarnings("resource")
	public void init() {
		try {
			ServerSocket socketServidor = new ServerSocket(porta);
			while (true) {
				System.out.println("Servidor ligado");
				Socket conexao = socketServidor.accept();
				System.out.println("Conexão aceita");
				clientes.add(new Client(conexao));
				Thread thread = new SocketServer(conexao);
				thread.start();
			}
		} catch (Exception e) {
			System.out.println("Erro ao inciar servidor : ");
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		try {
			boolean apelido = true;
			Scanner bfEntrada = new Scanner(this.conexao.getInputStream());
			PrintStream psSaida = new PrintStream(
					this.conexao.getOutputStream());
			psSaida.println("Digite seu nick");
			while (apelido) {

				this.nomeCliente = bfEntrada.nextLine();
				if (!validaNick(this.nomeCliente)) {
					psSaida.println("Ja existe um cliente com esse nome");
					psSaida.println("Digite seu nick novamente");
					// this.conexao.close();
				} else {
					apelido = false;
					adcApelido(this.conexao, this.nomeCliente);
					System.out.println(this.nomeCliente + " conectado!");

					psSaida.println("Clientes conectados : "
							+ this.participantes());
				}
			}

			String[] msg = bfEntrada.nextLine().split(":");
			while (!msg[1].equals("sair")) {
				if (msg.length == 2) {
					switch (msg[0]) {

					case "MSG":
						enviarParaTodos(psSaida, msg[1]);
						break;

					case "NICK":
						mudar(this.nomeCliente, msg[1], psSaida);
						break;

					}

				} else {
					if (msg.length == 3) {
						enviarPrivado(msg[1], msg[2]);
					}
				}
				msg = bfEntrada.nextLine().split(":");
			}
			sair(this.nomeCliente);

		} catch (Exception e) {
			System.out.println("Erro ao rodar thread do servidor : ");
			e.printStackTrace();
		}
	}

	public SocketServer(Socket socket) {
		this.conexao = socket;
	}

	public void enviarParaTodos(PrintStream saida, String msg) throws Exception {
		for (Client cliente : getInstance().clientes) {
			if (!cliente.getSocket().equals(this.conexao)) {
				PrintStream psChat = new PrintStream(cliente.getSocket()
						.getOutputStream());
				psChat.println(this.nomeCliente + ": " + msg);
			}
		}
	}

	public void enviarPrivado(String privado, String msg) throws Exception {

		for (Client cliente : getInstance().clientes) {
			if (cliente.getNickName().equals(privado)) {
				PrintStream psChat = new PrintStream(cliente.getSocket()
						.getOutputStream());
				psChat.println(this.nomeCliente + ": " + msg);
			}
		}
	}

	public void adcApelido(Socket socket, String apelido) {
		for (Client cliente : getInstance().clientes) {
			if (cliente.getSocket().equals(socket)) {
				cliente.setNickName(apelido);
			}
		}
	}

	public boolean validaNick(String nick) {
		boolean retorno = true;
		for (Client cliente : getInstance().clientes) {
			if (cliente.getNickName() != null) {
				if (cliente.getNickName().equals(nick))
					retorno = false;
			}
		}

		return retorno;
	}

	public void mudar(String nickAntigo, String nickNovo, PrintStream ps) {
		for (Client cliente : getInstance().clientes) {
			if (cliente.getNickName().equals(nickAntigo)) {
				if (validaNick(nickNovo)) {

					Client novo = new Client(cliente.getSocket(), nickNovo);
					getInstance().clientes.remove(cliente);
					getInstance().clientes.add(novo);
					this.nomeCliente = nickNovo;
					ps.println("Nick alterado com sucesso de (" + nickAntigo
							+ ") para (" + nickNovo + ").");
				} else {
					ps.println("Nick ja em uso");
				}
			}
		}
	}

	public void sair(String nick) throws Exception {
		Client saiu = new Client();
		for (Client client : getInstance().clientes) {
			if (client.getNickName().equals(nick)) {
				saiu =client;
				
				
			}
		}
		PrintStream psChat = new PrintStream(saiu.getSocket()
				.getOutputStream());
		psChat.println("desconectado");
		getInstance().clientes.remove(saiu);
		System.out.println(nick+" saiu do servidor");
		for (Client client : getInstance().clientes) {
			psChat = new PrintStream(client.getSocket()
					.getOutputStream());
			psChat.println(nick + " saiu!");

		}
	}

}