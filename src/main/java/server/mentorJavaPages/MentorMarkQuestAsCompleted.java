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
import java.util.Map;
import java.util.Optional;

public class MentorMarkQuestAsCompleted implements HttpHandler {
    private Optional<HttpCookie> cookie;
    private CookieHelper cookieHelper;
    private FormDataParser formDataParser;
    private LoginAccesDAO loginAccesDAO;
    private MentorDAO mentorDAO;

    public MentorMarkQuestAsCompleted(Connection connection){
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
        String method = httpExchange.getRequestMethod();

        if (cookie.isPresent()) {
            if (loginAccesDAO.checkSessionPresent(sessionId)){

                if (method.equals("GET")) {
                    response = generatePage();
                }

                if (method.equals("POST")){
                    Map inputs = formDataParser.getData(httpExchange);
                    if (inputs.containsKey("search")){
                        List<String> searchResults = mentorDAO.searchForStudent(inputs.get("search").toString());
                        response = generatePage(searchResults);
                    }
                    if (inputs.containsKey("update")){
                        try {
                            int questID = Integer.valueOf(inputs.get("questid").toString());
                            int codecoolerid = Integer.valueOf(inputs.get("codecoolerid").toString());
                            mentorDAO.markQuestAsCompleted(codecoolerid, questID);
                            response = generatePage();

                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                            response = generatePage();
                        }
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

        JtwigTemplate template = JtwigTemplate.classpathTemplate("HTML/mentorPages/mentorMarkQuestAsCompleted.twig");

        JtwigModel model = JtwigModel.newModel();

        return template.render(model);
    }

    private String generatePage(List<String> searchResult){

        JtwigTemplate template = JtwigTemplate.classpathTemplate("HTML/mentorPages/mentorMarkQuestAsCompleted.twig");

        JtwigModel model = JtwigModel.newModel().with("searchResult", searchResult);

        return template.render(model);
    }
}