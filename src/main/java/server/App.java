package server;

import com.sun.net.httpserver.HttpServer;
import controllers.Connector;

import java.net.InetSocketAddress;
import java.sql.Connection;


public class App {
    public static void main(String[] args) throws Exception {
        String dbPass = "quest";
        String dbUser = "queststore";
        Connection connection = new Connector().connect(dbUser, dbPass);
        // create a server on port 8000
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        // set routes
        server.createContext("/login", new Login(connection));
        server.createContext("/codecoolerIndex", new CodecoolerIndex());
        server.createContext("/adminMain", new AdminMainPage());
        server.createContext("/static", new Static());
        server.setExecutor(null); // creates a default executor

        // start listening
        server.start();
    }
}