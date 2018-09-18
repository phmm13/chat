package arthur;

import java.net.Socket;
import java.util.Scanner;
import java.io.IOException;
import java.io.PrintStream;

public class TrataMsg implements Runnable {

	private Socket cliente;
	private PrintStream escritor;
	private Scanner leitor;
	private String apelido;
	private ClientesNick pk;

	public TrataMsg(Socket cliente) {
		this.cliente = cliente;
		try {
			this.leitor = new Scanner(cliente.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			this.escritor = new PrintStream(cliente.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void run() {
		String outPut = "";
		boolean aux = true;

		while (true) {
			if (aux == true) {
				escritor.println("Bem vindo ao chat IRC Arthur Marques");
				escritor.println("Informe seu apelido: ");

				do {
					outPut = leitor.nextLine();
					if (verificaNickDisponivel(outPut)) {
						solicitaNickName(outPut);
						enviaParaTodos("Entrou");
						escritor.println("Voce entrou!!");
						aux = false;
					}
				} while (aux);

			}
			outPut = leitor.nextLine();

			if (outPut.split(":")[0].equals("MSG")) {
				enviaParaTodos(outPut.split(":")[1]);
			} else if (outPut.split(":")[0].equals("USERS")) {
				listaClientes();
			} else if (outPut.split(":")[0].equals("PRIVATE")) {
				msgPrivada(outPut.split(":", 2)[1]);
			} else if (outPut.split(":")[0].equals("EXIT")) {
				clienteSaiu();
				break;
			} else if (outPut.split(":")[0].equals("NICK")) {
				mudaNick(outPut.split(":")[1]);
			} else if (outPut.split(":")[0].equals("SNDFILE")) {
				sobeArquivoServidor(outPut.split(":", 2)[1]);
			}

		}

	}

	private void sobeArquivoServidor(String outPut) {
		String destinatario = outPut.split(":", 2)[0];
		String arquivo = outPut.split(":", 2)[1];
		
		escritor.println(destinatario);
		escritor.println(arquivo);
		
		
	}

	private void mudaNick(String outPut) {
		if (verificaNickDisponivel(outPut)) {
			solicitaNickName(outPut);
		}
	}

	private void clienteSaiu() {
		Servidor.getInstance().getClientes().remove(pk);
		enviaParaTodos("SAIU!!!");
		try {
			this.cliente.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean verificaNickDisponivel(String outPut) {
		for (ClientesNick z : Servidor.getInstance().getClientes()) {
			if (outPut.equals(z.getApelidoCliente())) {
				escritor.println("Apelido ja em uso, tentar outro");
				return false;
			}
		}
		return true;
	}

	private void msgPrivada(String outPut) {
		String destinatario = outPut.split(":")[0];
		String msg = outPut.split(":")[1];

		for (ClientesNick z : Servidor.getInstance().getClientes()) {
			if (z.getApelidoCliente().equals(destinatario)) {

				try {
					PrintStream all = new PrintStream(z.getClienteSocket().getOutputStream());
					all.println("P<" + this.apelido + ">" + ": " + msg);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private void solicitaNickName(String apelidoP) {
		apelido = apelidoP;

		for (ClientesNick z : Servidor.getInstance().getClientes()) {
			if (z.getClienteSocket().equals(this.cliente)) {
				z.setApelidoCliente(apelido);
				pk = z;
			}
		}

	}

	private void listaClientes() {
		for (ClientesNick z : Servidor.getInstance().getClientes()) {
			escritor.println(z.getApelidoCliente());
		}
	}

	private void enviaParaTodos(String msg) {
		for (ClientesNick a : Servidor.getInstance().getClientes()) {
			if (!a.getClienteSocket().equals(this.cliente)) {
				try {
					PrintStream all = new PrintStream(a.getClienteSocket().getOutputStream());
					all.println("<" + this.apelido + ">" + ": " + msg);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}
}