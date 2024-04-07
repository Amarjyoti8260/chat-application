import java.io.*;
import java.net.*;

public class Client {

    Socket socket;

    BufferedReader br;

    PrintWriter out;

    public Client(){

        try {
            System.out.println("sending reequest to server");
            socket= new Socket("192.168.145.82",7777);
            System.out.println("connection done");

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

            try{
                while (true) {


                    String msg  = br.readLine();
                    if (msg.equals("exit")){
                        System.out.println("server terminated the chat");
                        socket.close();
                        break;
                    }
                    System.out.println("server :"+msg);

                }

            }catch (Exception e){
                e.printStackTrace();
            }

        };
        new  Thread(r1).start();

    }
    public void startWriting(){

        //thread use to read the from the user and land it to the client

        Runnable r2 = ()->{
            System.out.println("writer strated....");

            try {
                while (  !socket.isClosed()){


                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();
                    out.println(content);
                    out.flush();
                    if (content.equals("exit")){
                        socket.close();
                        break;
                    }

                }
            }catch (Exception e){
                e.printStackTrace();
            }
        };
        new Thread(r2).start();
    }

    public static void main(String[] args) {
        System.out.println("this is client ..");
        new Client();
    }
}
