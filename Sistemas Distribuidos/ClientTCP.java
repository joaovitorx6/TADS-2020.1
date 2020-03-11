/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcp;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException; 
import java.io.InputStream;
/**
 *
 * @author 20181014040030
 */
public class ClientTCP {
    public ClientTCP () {
        
        String mensagem2;
        
        try{
           System.out.println("Estabelecendo a conexão...");
           byte [] mensagem = new byte[1000];
           Socket s = new Socket ("10.25.1.175", 666); 
           InputStream is = s.getInputStream();
           is.read(mensagem);
           is.close();
           System.out.println("Sua mensagem: " + new String(mensagem).trim());
        } catch (UnknownHostException uhex) {
            uhex.printStackTrace();
        } catch (IOException ioex){
            ioex.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        ClientTCP ct = new ClientTCP();
    }
}

