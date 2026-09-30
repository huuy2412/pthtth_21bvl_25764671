import java.io.*;
import java.net.*;

public class TCP_Server {
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(7000);
            System.out.println("Server dang chay port 7000");

            while(true){
                Socket socket = server.accept();
                new ClientThread(socket).start();
            }

        } catch(Exception e){
            e.printStackTrace();
        }
    }
}

class ClientThread extends Thread {
    Socket socket;

    ClientThread(Socket socket){
        this.socket = socket;
    }

    public void run(){
        try{
            BufferedReader input = new BufferedReader(
            new InputStreamReader(socket.getInputStream()));

            PrintWriter output = new PrintWriter(
            socket.getOutputStream(), true);

            output.println("1. Dao nguoc chuoi");
            output.println("2. Dao nguoc tung tu");
            output.println("3. Dem so tu");
            output.println("4. Dem so ky tu");
            output.println("Chon dich vu:");

            String chon = input.readLine();

            while(true){
                String str = input.readLine();

                if(str.equals(".")) break;

                String kq="";

                switch(chon){
                case "1":
                    String[] ds = str.split(" ");
                    for(String x: ds){
                        x = new StringBuilder(x).reverse().toString();
                        kq += x.substring(0,1).toUpperCase()+x.substring(1)+" ";
                    }
                    break;
                    case "2":
                        String[] a = str.split(" ");
                        for(String x:a)
                            kq += new StringBuilder(x).reverse()+" ";
                        break;

                    case "3":
                        kq = "So tu: " + str.split(" ").length;
                        break;

                    case "4":
                        kq = "So ky tu: " + str.length();
                        break;

                    default:
                        kq="Sai dich vu";
                }

                output.println(kq);
            }

            output.println("EXIT");
            socket.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}