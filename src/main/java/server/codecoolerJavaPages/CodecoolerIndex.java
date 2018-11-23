package server.codecoolerJavaPages;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controllers.dao.CodecoolerDAO;
import controllers.dao.LoginAccesDAO;
import models.CodecoolerModel;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import server.ResponseSender;
import server.helpers.CookieHelper;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CodecoolerIndex implements HttpHandler {
    private CodecoolerDAO codecoolerDAO;
    private LoginAccesDAO loginAccesDAO;


    public CodecoolerIndex(Connection connection) {
        this.codecoolerDAO = new CodecoolerDAO(connection);
        this.loginAccesDAO = new LoginAccesDAO(connection);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        ResponseSender responseSender = new ResponseSender();
        Optional<HttpCookie> httpCookie = responseSender.getSessionIdCookie(httpExchange);
        String sessionId = httpCookie.get().getValue().replace("\"", "");
        String method = httpExchange.getRequestMethod();
        int userId = 0;
        if(method.equals("GET")){
            try{
                userId = Integer.parseInt(loginAccesDAO.getIdBySessionId(sessionId));
            }catch(SQLException e){
                e.printStackTrace();
            }
            CodecoolerModel codecoolerModel = codecoolerDAO.getCodecoolerModel(userId);
            if(userId != 0){
                String site = "codecoolerMain.twig";
                responseSender.sendResponseIfUserExists(httpExchange, codecoolerModel, site);

            }else{
                responseSender.sendResponseIfInvalidUser(httpExchange);
            }
        }
    }

}