package org.example;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

public class Serveur extends WebSocketServer {

    public Serveur(InetSocketAddress address) {
        super(address);
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        conn.send("Welcome to the server!"); //This method sends a message to the new client
        broadcast( "new connection: " + handshake.getResourceDescriptor() ); //This method sends a message to all clients connected
        System.out.println("new connection to " + conn.getRemoteSocketAddress());
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println("closed " + conn.getRemoteSocketAddress() + " with exit code " + code + " additional info: " + reason);
    }

    @Override
    public void onMessage(WebSocket conn, String message)  {
        System.out.println("received message from "	+ conn.getRemoteSocketAddress() + ": " + message);
        String[] entrees = message.split(" ");
        try {
            ParseurDMN parseur =new ParseurDMN(entrees[0], entrees[1]);
            parseur.afficherResultat();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onMessage( WebSocket conn, ByteBuffer message ) {
        System.out.println("received ByteBuffer from "	+ conn.getRemoteSocketAddress());
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        System.err.println("an error occurred on connection " + conn.getRemoteSocketAddress()  + ":" + ex);
    }

    @Override
    public void onStart() {
        System.out.println("server started successfully");
    }


    public static void main(String[] args) throws InterruptedException, IOException {

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

