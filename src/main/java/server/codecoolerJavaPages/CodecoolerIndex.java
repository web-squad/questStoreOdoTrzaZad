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

    private static final String SESSION_COOKIE_NAME = "sessionId";
    private CodecoolerDAO codecoolerDAO;
    private LoginAccesDAO loginAccesDAO;
    private CookieHelper cookieHelper;

    public CodecoolerIndex(Connection connection) {
        this.codecoolerDAO = new CodecoolerDAO(connection);
        this.cookieHelper = new CookieHelper();
        this.loginAccesDAO = new LoginAccesDAO(connection);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        ResponseSender responseSender = new ResponseSender();
        Optional<HttpCookie> httpCookie = getSessionIdCookie(httpExchange);
        String sessionId = httpCookie.get().getValue().replace("\"", "");
        int userId = 0;
        try{
            userId = Integer.parseInt(loginAccesDAO.getIdBySessionId(sessionId));
        }catch(SQLException e){
            e.printStackTrace();
        }
        CodecoolerModel codecoolerModel = codecoolerDAO.getCodecoolerModel(userId);
        if(userId != 0){
            responseSender.sendResponseIfUserExists(httpExchange, codecoolerModel);

        }else{
            responseSender.sendResponseIfInvalidUser(httpExchange);
        }
    }





    private Optional<HttpCookie> getSessionIdCookie(HttpExchange httpExchange){
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        List<HttpCookie> cookies = cookieHelper.parseCookies(cookieStr);
        return cookieHelper.findCookieByName(SESSION_COOKIE_NAME, cookies);
    }


}