package server.mentorJavaPages;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import models.Artifact;
import models.Quest;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import java.net.HttpCookie;
import java.sql.Connection;
import java.util.Map;
import java.util.Optional;
import controllers.dao.MentorDAO;
import server.helpers.CookieHelper;
import server.helpers.FormDataParser;
import controllers.dao.LoginAccesDAO;

public class MentorEditQuest implements HttpHandler {
    private Optional<HttpCookie> cookie;
    private CookieHelper cookieHelper;
    private FormDataParser formDataParser;
    private LoginAccesDAO loginAccesDAO;
    private MentorDAO mentorDAO;

    public MentorEditQuest(Connection connection){
        this.mentorDAO = new MentorDAO(connection);
        formDataParser = new FormDataParser();
        cookieHelper = new CookieHelper();
        this.loginAccesDAO = new LoginAccesDAO(connection);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "";
        cookie = cookieHelper.getSessionIdCookie(httpExchange);
        String sessionId = cookie.get().getValue().substring(1, cookie.get().getValue().length() - 1);
        String method = httpExchange.getRequestMethod();

        if (cookie.isPresent()) {
            if (loginAccesDAO.checkSessionPresent(sessionId)){

                if (method.equals("GET")) {
                    response = generatePage();
                }

                if (method.equals("POST")){
                    Map inputs = formDataParser.getData(httpExchange);

                    try {
                        int providedQuestId = Integer.valueOf(inputs.get("questId").toString());
                        String providedQuestName = inputs.get("questName").toString();
                        String providedQuestDescription = inputs.get("questDesc").toString();
                        int providedQuestReward = Integer.valueOf(inputs.get("questReward").toString());
                        Quest editedQuest = new Quest(providedQuestName, providedQuestDescription, providedQuestReward);
                        mentorDAO.editQuest(providedQuestId, editedQuest);

                        response = generatePage();

                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        response = generatePage();
                    }
                }
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

    private String generatePage(){

        JtwigTemplate template = JtwigTemplate.classpathTemplate("HTML/mentorPages/mentorEditQuest.twig");

        JtwigModel model = JtwigModel.newModel();

        return template.render(model);
    }
}