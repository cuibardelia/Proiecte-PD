package exchange.rmi.s;

import exchange.rmi.i.IExchange;
import java.rmi.server.UnicastRemoteObject;
// Varianta cu apel rmiregistry direct
// Varianta JNDI
import javax.naming.Context;
import javax.naming.InitialContext;

public class ExchangeImpl implements IExchange{

    public String exchange(double amount, int currency){
        exchange.rmi.s.Conversion conversion = new exchange.rmi.s.Conversion();
        return conversion.getOutput(amount,currency);
    }
  
    public static void main(String args[]) {
      String host="localhost";
      int port=1099;
      if(args.length>0)
         port=Integer.parseInt(args[0]);
      try {
        ExchangeImpl obj=new ExchangeImpl();
        IExchange stub=(IExchange)UnicastRemoteObject.exportObject(obj,0);
        // Varianta cu apel rmiregistry direct
        /*
        Registry registry=LocateRegistry.getRegistry(host,port);
        registry.bind("ExchangeServer",stub);
        */
        // Varianta JNDI
        String sPort=Integer.valueOf(port).toString();
        System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
          "com.sun.jndi.rmi.registry.RegistryContextFactory");
        System.setProperty(Context.PROVIDER_URL,"rmi://"+host+":"+sPort);
        Context ctx=new InitialContext();
        ctx.bind("ExchangeServer",stub);
        System.out.println("ExchangeServer ready");
        System.out.println("Press CTRL+C to finish !");
      } 
      catch (Exception e) {
        //System.out.println("ExchangeImpl err: " + e.getMessage());
        e.printStackTrace();
      }
    }
} 
