package server.mentorJavaPages;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controllers.dao.LoginAccesDAO;
import controllers.dao.MentorDAO;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import server.helpers.CookieHelper;
import server.helpers.FormDataParser;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class MentorQuests implements HttpHandler {
    private Optional<HttpCookie> cookie;
    private CookieHelper cookieHelper;
    private FormDataParser formDataParser;
    private LoginAccesDAO loginAccesDAO;
    private MentorDAO mentorDAO;

    public MentorQuests(Connection connection){
        this.mentorDAO = new MentorDAO(connection);
        formDataParser = new FormDataParser();
        cookieHelper = new CookieHelper();
        loginAccesDAO = new LoginAccesDAO(connection);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "";
        cookie = cookieHelper.getSessionIdCookie(httpExchange);
        String sessionId = cookie.get().getValue().substring(1, cookie.get().getValue().length() - 1);

        if (cookie.isPresent()) {
            if (loginAccesDAO.checkSessionPresent(sessionId)){
                    String questsTable = createTable(mentorDAO.listOfQuests());
                    response = generatePage(questsTable);
            }
            else{
                httpExchange.getResponseHeaders().set("Location", "/login");
            }
        }
        httpExchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }


    private String generatePage(String questsTable){

        JtwigTemplate template = JtwigTemplate.classpathTemplate("HTML/mentorPages/mentorQuests.twig");

        JtwigModel model = JtwigModel.newModel();

        model.with("table", questsTable);

        return template.render(model);
    }

    private String createTable(List<String> List){
        String table = "           <tr class = \"first-row\"> <th>ID</th> <th class = \"right-cell\">Quest Name</th> </tr>\n";
        for(String team : List){
            String[] teamArray =  team.split(";");
            table += "<tr><td>" + teamArray[0] + "</td><td>" + teamArray[1] + "</td><td>" + teamArray[2] + "</td><td>" + teamArray[3] + "</td></tr> \n";
        }
        return table;
    }
}