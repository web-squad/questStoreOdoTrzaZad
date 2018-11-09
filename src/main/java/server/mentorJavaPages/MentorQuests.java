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
                    List<String> quests = mentorDAO.listOfQuests();
                    response = generatePage(quests);
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


    private String generatePage(List<String> quests){

        JtwigTemplate template = JtwigTemplate.classpathTemplate("HTML/mentorPages/mentorQuests.twig");

        JtwigModel model = JtwigModel.newModel();

        model.with("entries", quests);

        return template.render(model);
    }
}