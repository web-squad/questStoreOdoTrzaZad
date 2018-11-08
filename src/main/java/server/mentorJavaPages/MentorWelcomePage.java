package server.mentorJavaPages;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import controllers.dao.CreepyGuyDAO;
import controllers.dao.LoginAccesDAO;
import controllers.dao.MentorDAO;
import models.MentorModel;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import server.helpers.CookieHelper;

public class MentorWelcomePage implements HttpHandler {
        private static final String SESSION_COOKIE_NAME = "sessionId";
        private MentorDAO mentorDAO;
        private CreepyGuyDAO creepyGuyDAO;
        private LoginAccesDAO loginAccesDAO;
        private CookieHelper cookieHelper;

        public MentorWelcomePage(MentorDAO mentorDAO, CreepyGuyDAO creepyGuyDAO, LoginAccesDAO loginAccesDAO) {
            this.mentorDAO = mentorDAO;
            this.creepyGuyDAO = creepyGuyDAO;
            this.cookieHelper = new CookieHelper();
            this.loginAccesDAO = loginAccesDAO;
        }


        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            Optional<HttpCookie> httpCookie = cookieHelper.getSessionIdCookie(httpExchange);
            int id;
            String email;
            String name;
            String surname;
            String room;
            String nickName;
            String sessionId = httpCookie.get().getValue().replace("\"", "");
            MentorModel mentorModel = creepyGuyDAO.getMentorById(String.valueOf(id));
            if(id != 0)
                nickName = mentorModel.getNickName();
                name = mentorModel.getName();
                surname = mentorModel.getSurname();
                email = mentorModel.getEmail();
                room = mentorModel.getRoom();
            }


        // get a template file
        JtwigTemplate template = JtwigTemplate.classpathTemplate("HTML/mentorPages/mentorWelcomePage.twig");

        // create a model that will be passed to a template
        JtwigModel model = JtwigModel.newModel();

        // render a template to a string
        String response = template.render(model);

        // send the results to a the client
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}