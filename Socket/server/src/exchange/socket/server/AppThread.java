package exchange.socket.server;

import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.function.Function;

public class AppThread {
  // Firul de executie lansat de server
  Function<Socket, Thread> service = socket -> {
    return new Thread(() -> {
      try (DataOutputStream out = new DataOutputStream(socket.getOutputStream());
           DataInputStream in = new DataInputStream(socket.getInputStream())) {

        double amount;
        int currency;
        String response;

        amount = in.readDouble();
        currency = in.read();

        Conversion conversion = new Conversion();
        response = conversion.getOutput(amount, currency);
        out.writeUTF(response);

        socket.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
  };
}
