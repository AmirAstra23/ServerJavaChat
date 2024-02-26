
package javaserver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import static java.nio.file.Files.write;

public class JavaServer {

    
    public static void main(String[] args) throws IOException {
        
        ServerSocket server = new ServerSocket(30333);
        
        while(true){
        Socket sock = server.accept();
        
        System.out.println("new client connected, address: "+
                sock.getInetAddress().getCanonicalHostName());
        new ClientThread(sock).setupAndStart();
        }
        
        /*BufferedReader reader = new BufferedReader(
            new InputStreamReader(sock.getInputStream()));
        
        BufferedWriter writer = new BufferedWriter(
        new OutputStreamWriter(sock.getOutputStream()));
        
        String str; 
        
        while(true)
        {str = reader.readLine();      
        System.out.println("client connected");
        if("exit".equals(str)) break;
        writer.write("accepted");
        writer.newLine();
        writer.flush();
        }
        System.out.println("client disconnected");}*/
                              
    }
    
}
class ClientThread implements Runnable{
    
    
        BufferedReader reader;
        
        BufferedWriter writer ;
        
        private Socket sock;
        private final int id;
        private static int clientCount;
        private Thread self;
        
        
        
        public ClientThread(Socket sock){
            this.sock = sock;
            id = clientCount++; 
            self = new Thread(this);
        }
        
        public void setupAndStart() throws IOException{
            reader = new BufferedReader(
            new InputStreamReader(sock.getInputStream()));
        
        writer = new BufferedWriter(
        new OutputStreamWriter(sock.getOutputStream()));
        self.start();
        }

    @Override
    public void run() {
        String str;
        try{
        while(true){
            str = reader.readLine();      
            System.out.println("client connected");
            System.out.println(str);
            if("exit".equals(str)) break;
            writer.write(str);
            writer.newLine();
            writer.flush();
            }
        } catch (IOException ex){}
        
        System.out.println("client- " + id + " disconnected");
    }
    
}