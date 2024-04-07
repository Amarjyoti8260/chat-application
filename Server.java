
import java.io.BufferedReader;
import java.io.*;
import  java.net.*;

public class Server {

    ServerSocket server;
    Socket socket;

    BufferedReader br;

    PrintWriter out;

    public  Server() {
        try{
            server = new ServerSocket(7777);
            System.out.println("server is ready to acept the connection");
            System.out.println("server is waiting");
            socket = server.accept();

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();

        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public  void startReading(){

        //thread use to read the data

        Runnable r1 = ()->{

            System.out.println("reader started...");

            try {
                while (true) {


                    String msg  = br.readLine();
                    if (msg.equals("exit")){
                        System.out.println("client terminated the chat");
                        socket.close();
                        break;
                    }
                    System.out.println("Client :"+msg);

                }

            }catch (Exception e){
//                e.printStackTrace();
                System.out.println("connection braeked");
            }

        };
        new  Thread(r1).start();

    }
    public void startWriting(){

        //thread use to read the from the user and land it to the client

        Runnable r2 = ()->{
            System.out.println("writer strated....");

            try {
                while ( !socket.isClosed()){


                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();
                    out.println(content);
                    out.flush();
                    if (content.equals("exit")){
                        socket.close();
                        break;
                    }


                }
                System.out.println("connection closed");
            }catch (Exception e){
                e.printStackTrace();
            }
        };
        new Thread(r2).start();
    }


    public static void main(String[] args) {
        System.out.println("this is the server...going to start the server");

        new Server();
    }
}
