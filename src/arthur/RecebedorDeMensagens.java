package arthur;
import java.io.InputStream;
import java.util.Scanner;


public class RecebedorDeMensagens implements Runnable{

    public InputStream entradas;

    public RecebedorDeMensagens(InputStream iS) {
        this.entradas = iS;
    }
    public void run() {
        try(Scanner s = new Scanner(this.entradas)){
			while (s.hasNextLine()) {
				System.out.println(s.nextLine());
			}
		}
    }
    
}