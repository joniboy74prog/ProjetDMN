package org.example;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;

public class App {

    public static void main(String[] args)  throws InterruptedException, IOException {
        {
            int port = 8025; // 843 flash policy port
            Serveur s = new Serveur(new InetSocketAddress("localhost", port));
            s.setReuseAddr(true);
            s.start();
            System.out.println("Serveur démarre sur le port:  " + s.getPort()
                    + "\n Tapez \"exit\" pour sortir");
            BufferedReader sysin = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                String in = sysin.readLine();
                s.broadcast(in);
                if (in.equals("exit")) {
                    s.stop(1000);
                    break;
                }
            }


        }
    }
}