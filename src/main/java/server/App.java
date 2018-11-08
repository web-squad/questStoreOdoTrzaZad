package server;

import com.sun.net.httpserver.HttpServer;
import controllers.Connector;
import controllers.dao.CodecoolerDAO;
import controllers.dao.CreepyGuyDAO;
import controllers.dao.LoginAccesDAO;
import controllers.dao.MentorDAO;
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

        CodecoolerDAO codecoolerDAO = new CodecoolerDAO(connection);
        MentorDAO mentorDAO = new MentorDAO(connection);
        CreepyGuyDAO creepyGuyDAO = new CreepyGuyDAO(connection);
        LoginAccesDAO loginAccesDAO = new LoginAccesDAO(connection);

        // set routes
        server.createContext("/codecoolerJavaPages/CodecoolerIndex", new CodecoolerIndex());
        server.createContext("/codecoolerJavaPages/CodecoolerMain", new CodecoolerMain(codecoolerDAO, loginAccesDAO));
        server.createContext("/codecoolerJavaPages/CreateTeam", new CreateTeam());
        server.createContext("/codecoolerJavaPages/EditUserTeam", new EditUserTeam());
        server.createContext("/codecoolerJavaPages/Store", new Store());
        server.createContext("/codecoolerJavaPages/UserArtifacts", new UserArtifacts());

        server.createContext("/mentorJavaPages/MentorAddArtifact", new MentorAddArtifact(mentorDAO, loginAccesDAO));
        server.createContext("/mentorJavaPages/MentorAddQuest", new MentorAddQuest(mentorDAO, loginAccesDAO));
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
        server.createContext("/mentorJavaPages/MentorWelcomePage", new MentorWelcomePage(mentorDAO));

        server.createContext("/adminJavaPages/ClassAdder", new ClassAdder(connection));
        server.createContext("/adminJavaPages/ClassDeleter", new ClassDeleter());
        server.createContext("/adminJavaPages/ClassEditor", new ClassEditor());
        server.createContext("/adminJavaPages/ExpLVLAdder", new ExpLVLAdder());
        server.createContext("/adminJavaPages/ExpLVLDeleter", new ExpLVLDeleter());
        server.createContext("/adminJavaPages/ExpLVLEditor", new ExpLVLEditor());
        server.createContext("/adminJavaPages/GreetAdmin", new GreetAdmin(connection));
        server.createContext("/adminJavaPages/MentorAdder", new MentorAdder(connection));
        server.createContext("/adminJavaPages/MentorDeleter", new MentorDeleter(connection));
        server.createContext("/adminJavaPages/MentorEditor", new MentorEditor(connection));

        server.createContext("/login", new Login(connection));
        server.createContext("/static", new Static());
        server.setExecutor(null); // creates a default executor



        // start listening
        server.start();
    }
}