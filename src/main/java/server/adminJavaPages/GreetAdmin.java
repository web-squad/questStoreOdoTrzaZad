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
    CreepyGuyDAO creepyGuyDAO;
    CreepyGuyModel creepyGuyModel;
    Optional<HttpCookie> cookie;
    CookieHelper cookieHelper;
    LoginAccesDAO loginAccesDAO;

    public GreetAdmin(Connection connection){
        creepyGuyDAO = new CreepyGuyDAO(connection);
        cookieHelper = new CookieHelper();
        loginAccesDAO = new LoginAccesDAO(connection);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
//        String response = "";
//        cookie = cookieHelper.getSessionIdCookie(httpExchange);
//
//        if (cookie.isPresent()) {
//            if (loginAccesDAO.checkSessionPresent(cookie.get().getValue())){
//                // get a template file
//                JtwigTemplate template = JtwigTemplate.classpathTemplate("HTML/adminPages/greetAdmin.twig");
//
//                // create a model that will be passed to a template
//                JtwigModel model = JtwigModel.newModel();
//
//                // render a template to a string
//                response = template.render(model);
//
//            }
//            else{
//                httpExchange.getResponseHeaders().set("Location", "/login");
//            }
//        }
//        httpExchange.sendResponseHeaders(301, response.length());
//        OutputStream os = httpExchange.getResponseBody();
//        os.write(response.getBytes());
//        os.close();
    }
}