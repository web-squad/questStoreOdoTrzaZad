package server;

import com.sun.net.httpserver.HttpServer;
import controllers.Connector;
import server.codecoolerJavaPages.*;
import server.adminJavaPages.*;
import server.mentorJavaPages.*;

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
        server.createContext("/codecoolerJavaPages/CodecoolerIndex", new CodecoolerIndex());
        server.createContext("/codecoolerJavaPages/CodecoolerMain", new CodecoolerMain());
        server.createContext("/codecoolerJavaPages/CreateTeam", new CreateTeam());
        server.createContext("/codecoolerJavaPages/EditUserTeam", new EditUserTeam());
        server.createContext("/codecoolerJavaPages/Store", new Store());
        server.createContext("/codecoolerJavaPages/UserArtifacts", new UserArtifacts());

        server.createContext("/mentorJavaPages/MentorAddArtifact", new MentorAddArtifact());
        server.createContext("/mentorJavaPages/MentorAddQuest", new MentorAddQuest());
        server.createContext("/mentorJavaPages/MentorAddStudent", new MentorAddStudent());
        server.createContext("/mentorJavaPages/MentorCheckWallet", new MentorCheckWallet());
        server.createContext("/mentorJavaPages/MentorEditArtifact", new MentorEditArtifact());
        server.createContext("/mentorJavaPages/MentorEditQuest", new MentorEditQuest());
        server.createContext("/mentorJavaPages/MentorEditStudent", new MentorEditStudent());
        server.createContext("/mentorJavaPages/MentorIndexPage", new MentorIndexPage());
        server.createContext("/mentorJavaPages/MentorMarkItemAsUsed", new MentorMarkItemAsUsed());
        server.createContext("/mentorJavaPages/MentorMarkQuestAsCompleted", new MentorMarkQuestAsCompleted());
        server.createContext("/mentorJavaPages/MentorQuests", new MentorQuests());
        server.createContext("/mentorJavaPages/MentorRemoveStudent", new MentorRemoveStudent());
        server.createContext("/mentorJavaPages/MentorShop", new MentorShop());
        server.createContext("/mentorJavaPages/MentorWelcomePage", new MentorWelcomePage());

        server.createContext("/adminJavaPages/ClassEditor", new ClassEditor(connection));
        server.createContext("/adminJavaPages/GreetAdmin", new GreetAdmin(connection));
        server.createContext("/adminJavaPages/MentorEditor", new MentorEditor(connection));
        server.createContext("/adminJavaPages/ExpLvlEditor", new ExpLvlEditor(connection));

        server.createContext("/login", new Login(connection));
        server.createContext("/static", new Static());
        server.setExecutor(null); // creates a default executor



        // start listening
        server.start();
    }
}