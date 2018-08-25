package exchange.socket.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class CurrencyExchangeClient {
  public static void main(String[] args) throws IOException {

    String host="localhost";
    int port=7999;
    if (args.length>0)
       host=args[0];
    if (args.length>1)
       port=Integer.parseInt(args[1]);

    Scanner scanner=new Scanner(System.in);
    System.out.println("Insert amount to exchange: ");
    double amount=scanner.nextDouble();
    System.out.println("Available currency mappings: 1 - EUR, 2 - USD, 3 - RON.");
    System.out.println("Currency: ");
    int currency=scanner.nextInt();

    try(Socket currencyExchange = new Socket(host, port);
      DataInputStream in=new DataInputStream(currencyExchange.getInputStream());
      DataOutputStream out=new DataOutputStream(currencyExchange.getOutputStream())){
        out.writeDouble(amount);
        out.write(currency);
        String response=in.readUTF();
        System.out.println("Required result: " + response);
    } 
    catch(Exception e){
       System.err.println("Client comunication error: " + e.getMessage());
    }
  }
}


