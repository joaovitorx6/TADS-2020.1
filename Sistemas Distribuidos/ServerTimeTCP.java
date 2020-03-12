package tcp;
import java.net.*;
import java.net.ServerSocket;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;
import java.util.SimpleTimeZone;

/**
 *
 * @author 20181014040030
 */

public class ServerTimeTCP {

    public ServerTimeTCP(){
//       
        try {
            int i=0, opcao;            
            InputStream is;
            OutputStream os;
            String data, hora;
            Calendar gcr = new GregorianCalendar();
            
            ServerSocket sst = new ServerSocket(3030);
            System.out.println("Aguardando novas conexões.");
            Socket s = sst.accept();
            is = s.getInputStream();
            os = s.getOutputStream();
           
          
            while (i==0){
                
                System.out.println("Bem-vindo ao ServerTimeTCP");
                System.out.println("Digite o que você deseja se informar:");
                System.out.println("[1] - HORA ATUAL");
                System.out.println("[2] - DATA ATUAL");
                System.out.println("[3] - SAIR");
                
                opcao = is.read();
            
                switch(opcao){
                    case 1:
                        hora = gcr.get(Calendar.HOUR_OF_DAY) + ":" + gcr.get(Calendar.MINUTE) + ":" + gcr.get(Calendar.SECOND);
                        os.write(hora.getBytes());
                        break;
                    case 2:
                        data = gcr.get(Calendar.DAY_OF_MONTH) + "/" + gcr.get(Calendar.MONTH) + "/" + gcr.get(Calendar.YEAR);
                        os.write(data.getBytes());
                        break;
                    case 3:
                        i = 1;
                        break;
                    default:
                        System.out.println("Escolha uma opção válida.");
                        break;
                }
            }
        }catch (IOException ioex){
            ioex.printStackTrace();
        }
           
        
    }
     public static void main(String[] args) {
            ServerTimeTCP st = new ServerTimeTCP();
        }
}
