package exchange.rmi.c;
import exchange.rmi.i.IExchange;
import java.util.Scanner;
// Varianta cu apel rmiregistry direct
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
// Varianta JNDI
import javax.naming.Context;
import javax.naming.InitialContext;

public class ExchangeClient {
  public static void main(String args[]) {
    String host="localhost";
    int port=1099;
    if(args.length>0)
      host=args[0];
    if (args.length>1)
      port=Integer.parseInt(args[1]);

    Scanner scanner=new Scanner(System.in);
    System.out.println("Insert amount to exchange: ");
    double amount=scanner.nextDouble();
    System.out.println("Available currency mappings: 1 - EUR, 2 - USD, 3 - RON.");
    System.out.println("Currency: ");
    int currency=scanner.nextInt();
    String response;
    try {
      // Varianta cu apel rmiregistry direct
      /*
      Registry registry=LocateRegistry.getRegistry(host,port);
      iexchange obj=(iexchange)registry.lookup("CmmdcServer");
      */
      // Varianta JNDI
      String sPort=Integer.valueOf(port).toString();
      System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
        "com.sun.jndi.rmi.registry.RegistryContextFactory");
      System.setProperty(Context.PROVIDER_URL,"rmi://"+host+":"+sPort);
      Context ctx=new InitialContext();
      IExchange obj=(IExchange)ctx.lookup("ExchangeServer");
      
      response=obj.exchange(amount,currency);
      System.out.println("Required result: " + response);
    }
    catch (Exception e) {
      System.out.println("ExchangeClient exception: "+e.getMessage());
    }
  }
}
