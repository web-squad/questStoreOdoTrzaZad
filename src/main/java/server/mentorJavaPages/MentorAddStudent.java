package server.mentorJavaPages;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.sql.Connection;
import java.util.Map;
import java.util.Optional;

import controllers.dao.LoginAccesDAO;
import controllers.dao.MentorDAO;
import models.CodecoolerModel;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import server.helpers.CookieHelper;
import server.helpers.FormDataParser;

public class MentorAddStudent implements HttpHandler {
    private MentorDAO mentorDAO;
    private Optional<HttpCookie> cookie;
    private CookieHelper cookieHelper;
    private FormDataParser formDataParser;
    private LoginAccesDAO loginAccesDAO;
    private Connection connection;

    public MentorAddStudent(Connection connection){
        formDataParser = new FormDataParser();
        cookieHelper = new CookieHelper();
        this.connection = connection;
        mentorDAO = new MentorDAO(connection);
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

                    String providedFirstName = inputs.get("name").toString();
                    String providedLastName = inputs.get("lastName").toString();
                    String providedNickname = inputs.get("nickname").toString();
                    String providedEmail = inputs.get("email").toString();
                    String providedPassword = inputs.get("password").toString();
                    CodecoolerModel codecoolerModel = new CodecoolerModel(providedFirstName, providedLastName, providedEmail, providedNickname, providedPassword);
                    mentorDAO.createCodecooler(codecoolerModel);

                    response = generatePage();
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
        // get a template file
        JtwigTemplate template = JtwigTemplate.classpathTemplate("HTML/mentorPages/mentorAddStudent.twig");

        // create a model that will be passed to a template
        JtwigModel model = JtwigModel.newModel();

        // render a template to a string
        return template.render(model);
    }
}