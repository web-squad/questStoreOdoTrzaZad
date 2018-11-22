package server;

import com.sun.net.httpserver.HttpServer;
import controllers.Connector;
import server.adminJavaPages.ClassEditor;
import server.adminJavaPages.ExpLvlEditor;
import server.adminJavaPages.GreetAdmin;
import server.adminJavaPages.MentorEditor;
import server.codecoolerJavaPages.*;
import server.mentorJavaPages.*;

import java.net.InetSocketAddress;
import java.sql.Connection;


public class App {
    public static void main(String[] args) throws Exception {
        String dbPass = "4313284";
        String dbUser = "karol";
        Connection connection = new Connector().connect(dbUser, dbPass);
        // create a server on port 8000
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        // set routes
        server.createContext("/codecoolerJavaPages/CodecoolerIndex", new CodecoolerIndex(connection));
        server.createContext("/codecoolerJavaPages/CreateTeam", new CreateTeam(connection));
        server.createContext("/codecoolerJavaPages/EditUserTeam", new EditUserTeam(connection));
        server.createContext("/codecoolerJavaPages/Store", new Store(connection));
        server.createContext("/codecoolerJavaPages/UserArtifacts", new UserArtifacts(connection));

        server.createContext("/mentorJavaPages/MentorAddArtifact", new MentorAddArtifact(connection));
        server.createContext("/mentorJavaPages/MentorAddQuest", new MentorAddQuest(connection));
        server.createContext("/mentorJavaPages/MentorAddStudent", new MentorAddStudent(connection));
        server.createContext("/mentorJavaPages/MentorCheckWallet", new MentorCheckWallet(connection));
        server.createContext("/mentorJavaPages/MentorEditArtifact", new MentorEditArtifact(connection));
        server.createContext("/mentorJavaPages/MentorEditQuest", new MentorEditQuest(connection));
        server.createContext("/mentorJavaPages/MentorEditStudent", new MentorEditStudent(connection));
        server.createContext("/mentorJavaPages/MentorIndexPage", new MentorIndexPage());
        server.createContext("/mentorJavaPages/MentorMarkItemAsUsed", new MentorMarkItemAsUsed(connection));
        server.createContext("/mentorJavaPages/MentorMarkQuestAsCompleted", new MentorMarkQuestAsCompleted(connection));
        server.createContext("/mentorJavaPages/MentorQuests", new MentorQuests(connection));
        server.createContext("/mentorJavaPages/MentorRemoveStudent", new MentorRemoveStudent(connection));
        server.createContext("/mentorJavaPages/MentorShop", new MentorShop(connection));
        server.createContext("/mentorJavaPages/MentorWelcomePage", new MentorWelcomePage(connection));


        server.createContext("/adminJavaPages/GreetAdmin", new GreetAdmin(connection));
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