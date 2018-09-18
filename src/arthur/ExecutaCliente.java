package arthur;
import java.io.IOException;
import java.net.UnknownHostException;

public class ExecutaCliente{

    @SuppressWarnings("resource")
	public static void main(String[] args) {
        try {
			new Cliente().executa();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
