import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

/*
 * http://www.mballem.com/post/chat-multi-usuarios-com-socket/?i=1
 */

public class ClientSocket extends Thread{

	private Socket conexao;
	
	
	public static void main(String args[]){
		try{
			Socket socket = new Socket("127.0.0.1",12345);
			
			PrintStream psSaida = new PrintStream(socket.getOutputStream());
			
			System.out.println("Conectado ao servidor");
			
			Thread thread = new ClientSocket(socket);
			thread.start();
			
			Scanner bfTeclado = new Scanner(System.in);
			
			String mensagem;
			while(bfTeclado.hasNextLine()){
				
				mensagem = bfTeclado.nextLine(); //LE A MENSAGEM
				psSaida.println(mensagem); //ENVIA A MENSAGEM
				
			}
		}catch(Exception e){
			System.out.println("Erro na main cliente : ");
			e.printStackTrace();
		}
	}
	
	@Override
	public void run(){
		try{
			Scanner bfEntrada = new Scanner(new InputStreamReader(this.conexao.getInputStream()));
			
			String mensagemLida;
			while(true){
				mensagemLida = bfEntrada.nextLine();
				
				if(mensagemLida.equals("desconectado")){
					System.out.println("Conexão encerrada!");
                    this.conexao.close();
				}else{
	                //imprime a mensagem recebida
	                System.out.println(mensagemLida);
				}
			}
		}catch(Exception e){
			System.out.println("Erro thread do cliente : ");
			e.printStackTrace();
		}
	}
	public ClientSocket (Socket conexao){
		this.conexao = conexao;
	}
	
}