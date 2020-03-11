package tcp;
import java.net.*;
import java.net.ServerSocket;
import java.io.IOException;
import java.net.Socket;
import java.io.OutputStream;

/**
 *
 * @author 20181014040030
 */
public class ServerTCP {

    public ServerTCP(){
        try {
            ServerSocket sst = new ServerSocket(3030);
            System.out.println("Aguardando novas conexões.");
            Socket s = sst.accept();
            OutputStream os = s.getOutputStream();
            byte [] mensagem = "Mensagem teste".getBytes();
            os.write(mensagem);
            os.close();
        } catch (IOException ioex) {
               ioex.printStackTrace();
        }
        
    }
     public static void main(String[] args) {
            ServerTCP st = new ServerTCP();
        }
}
