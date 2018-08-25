package exchange.rmi.i;

public interface IExchange extends java.rmi.Remote {
    String exchange(double a, int b) throws java.rmi.RemoteException;
}

    
