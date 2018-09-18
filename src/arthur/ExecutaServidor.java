package arthur;
import java.io.IOException;


public class ExecutaServidor{

    public static void main(String[] args) {
        try {
            Servidor.getInstance().executa();
        } catch (IOException e) {
            //TODO: handle exception
        }
        
    }
}