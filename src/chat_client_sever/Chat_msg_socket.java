/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat_client_sever;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextPane;

/**
 *
 * @author silva
 */
public class Chat_msg_socket extends Thread{
    private Socket socket;
    private JTextPane txpMessageBoard;
    private PrintWriter out;
    private BufferedReader reader;

    public Chat_msg_socket(Socket socket, JTextPane txpMessageBoard) throws IOException {
        this.socket = socket;
        this.txpMessageBoard = txpMessageBoard;
        
        out = new PrintWriter (this.socket.getOutputStream());
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        
        receive();
    }
    
   public void receive() {
       Thread thread = new Thread(){
           public void run(){
               while(true){
                    try {
                        String line = reader.readLine();
                        if(line != null){
                            txpMessageBoard.setText(txpMessageBoard.getText() + " \n "+line);
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(Chat_msg_socket.class.getName()).log(Level.SEVERE, null, ex);
                    }
           }
       }
          
       };
       thread.start();
       
   }
   
   public void send(String msg){
       String current = txpMessageBoard.getText();
       txpMessageBoard.setText(current + "\n Eu: " + msg);
       out.println(msg);
       out.flush();
   }
   
   public void close(){
       try {
           out.close();
           reader.close();
           socket.close();
       } catch (Exception e) {
       }
   }
   
}
