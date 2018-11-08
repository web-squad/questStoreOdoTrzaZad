package server.adminJavaPages;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.sql.Connection;
import java.util.Optional;

import controllers.dao.CreepyGuyDAO;
import controllers.dao.LoginAccesDAO;
import models.CreepyGuyModel;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import server.helpers.CookieHelper;

public class GreetAdmin implements HttpHandler {
    private CreepyGuyDAO creepyGuyDAO;
    private CookieHelper cookieHelper;
    private LoginAccesDAO loginAccesDAO;

    public GreetAdmin(Connection connection){
        creepyGuyDAO = new CreepyGuyDAO(connection);
        cookieHelper = new CookieHelper();
        loginAccesDAO = new LoginAccesDAO(connection);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "";
        Optional<HttpCookie>cookie = cookieHelper.getSessionIdCookie(httpExchange);
        String sessionId = cookie.get().getValue().substring(1, cookie.get().getValue().length() - 1);
        if (cookie.isPresent()) {
            if (loginAccesDAO.checkSessionPresent(sessionId)){
                response = fillPage(sessionId);
            }
            else{
                httpExchange.getResponseHeaders().set("Location", "/login");
            }
        }
        httpExchange.sendResponseHeaders(301, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private String fillPage(String sessionId){
        CreepyGuyModel creepyGuyModel = creepyGuyDAO.getAdminBySessionId(sessionId);
        // get a template file
        JtwigTemplate template = JtwigTemplate.classpathTemplate("HTML/adminPages/greetAdmin.twig");

        // create a model that will be passed to a template
        JtwigModel model = JtwigModel.newModel();

        model.with("nickname", creepyGuyModel.getNickName());
        model.with("second_nickname", creepyGuyModel.getNickName());
        model.with("name", creepyGuyModel.getName());
        model.with("surname", creepyGuyModel.getSurname());
        model.with("email", creepyGuyModel.getEmail());

        // render a template to a string
        return template.render(model);
    }
}