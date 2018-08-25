package exchange.socket.server;

import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.IOException;

public class MyMServer {
  static final int NTHREADS=100;
  static ExecutorService exec=Executors.newFixedThreadPool(NTHREADS);
  
  public static void main(String[] args){
    int port=7999;
    boolean listening=true;
    AppThread appThread=new AppThread();
    try(ServerSocket serverSocket=new ServerSocket(port)) {
      System.out.println("The server is listening on port 7999");
      while(listening){
        exec.execute(appThread.service.apply(serverSocket.accept()));
      }
    }catch (IOException e){
      e.printStackTrace();
    }
  }
}
