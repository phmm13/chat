package arthur;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Cliente extends Socket {
	public void executa() throws UnknownHostException, IOException {
		try (Socket cliente = new Socket("127.0.0.1", 12345);
				Scanner teclado = new Scanner(System.in);
				PrintStream saida = new PrintStream(cliente.getOutputStream())) {

			System.out.println("O cliente se conectou ao servidor!");


			RecebedorDeMensagens rb = new RecebedorDeMensagens(cliente.getInputStream());

			new Thread(rb).start();

			String outPut;
			while (teclado.hasNextLine()) {
				outPut = teclado.nextLine();
				saida.println(outPut);

				if (outPut.equals("EXIT:")) {
					break;
				}
			}
			cliente.close();
		}
	}

}
