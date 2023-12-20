package org.example;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import camundajar.impl.com.google.gson.JsonObject;
import camundajar.impl.com.google.gson.JsonParser;
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
    //TODO: SPÉCIFIER COMPORTEMENT LORS DE SAISIE INCORRECTE
    //TODO: CHANGER IMPLÉMENTATION DU MOTEUR DMN POUR CREER OBJET AVEC FICHIER ENVOYÉ PAR MESSAGE
    @Override
    public void onMessage(WebSocket conn, String message)  {
        System.out.println("received message from "	+ conn.getRemoteSocketAddress());
        JsonObject jsonObject = new JsonParser().parse(message).getAsJsonObject();
        String contenuFichier = jsonObject.get("fichier").getAsString();
        try {
            ArrayList<String> listeValeurs = new ArrayList<String>();
            listeValeurs.add("Winter");
            listeValeurs.add("5");
            DMN dmn = new DMN(listeValeurs, contenuFichier);
            System.out.println(dmn.montreResultat());
        } catch (IOException e) {
            e.printStackTrace();
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



}