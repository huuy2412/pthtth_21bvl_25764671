import java.io.*;
import java.net.*;
import java.util.Scanner;


public class TCP_Client {


    public static void main(String[] args){
        try{
            Socket socket =
            new Socket("localhost",7000);
            BufferedReader input =
            new BufferedReader(
            new InputStreamReader(
            socket.getInputStream()));
            PrintWriter output =
            new PrintWriter(
            socket.getOutputStream(),true);

            Scanner sc=new Scanner(System.in);

            // nhận menu
            while(true){
                String line=input.readLine();
                System.out.println(line);
                if(line.equals("Nhap lua chon:"))
                    break;
            }

            String choice=sc.nextLine();
            output.println(choice);
            System.out.println(input.readLine());

            while(true){
                System.out.print("Nhap chuoi: ");
                String str=sc.nextLine();
                output.println(str);
                if(str.equals("."))
                    break;
                String result=input.readLine();
                System.out.println(
                "Ket qua: "+result);

            }

            // thoát
            output.println("EXIT");
            socket.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}