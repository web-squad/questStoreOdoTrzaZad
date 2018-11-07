package server;

import com.sun.net.httpserver.HttpServer;
import controllers.Connector;

import java.net.InetSocketAddress;


public class App {
    public static void main(String[] args) throws Exception {
        String dbPass = "quest";
        String dbUser = "queststore";

        // create a server on port 8000
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        // set routes
        server.createContext("/login", new Login(new Connector().connect(dbUser, dbPass)));
        server.createContext("/CodecoolerIndex", new CodecoolerIndex());
        server.createContext("/static", new Static());
        server.setExecutor(null); // creates a default executor

        // start listening
        server.start();
    }
}