package server.mentorJavaPages;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;

import controllers.dao.MentorDAO;
import models.MentorModel;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import server.helpers.CookieHelper;

public class MentorWelcomePage implements HttpHandler {
    private static final String SESSION_COOKIE_NAME = "sessionId";
    private MentorDAO mentorDAO;
    private CookieHelper cookieHelper;
    private Connection connection;

    public MentorWelcomePage(Connection connection) {
        this.connection = connection;
        this.mentorDAO = new MentorDAO(connection);
        this.cookieHelper = new CookieHelper();
    }


    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        Optional<HttpCookie> httpCookie = getSessionIdCookie(httpExchange);
        String room = "";
        String nickname = "";
        String name = "";
        String surname = "";
        String email = "";
        String sessionId = httpCookie.get().getValue().replace("\"", "");
        System.out.println(sessionId);

        MentorModel mentorModel = mentorDAO.getMentorBySessionId(sessionId);

        if(mentorModel != null){
            nickname = mentorModel.getNickName();
            name = mentorModel.getName();
            surname = mentorModel.getSurname();
            email = mentorModel.getEmail();
            room = mentorModel.getRoom();
        }


    // get a template file
    JtwigTemplate template = JtwigTemplate.classpathTemplate("HTML/mentorPages/mentorWelcomePage.twig");

    JtwigModel model = JtwigModel.newModel();
            model.with("nickname",nickname);
            model.with("room",room);
            model.with("name",name);
            model.with("surname",surname);
            model.with("email",email);
    // render a template to a string
    String response = template.render(model);

    // send the results to a the client
            httpExchange.sendResponseHeaders(200,response.length());
    OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
}

    private Optional<HttpCookie> getSessionIdCookie(HttpExchange httpExchange){
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        List<HttpCookie> cookies = cookieHelper.parseCookies(cookieStr);
        return cookieHelper.findCookieByName(SESSION_COOKIE_NAME, cookies);
    }
}