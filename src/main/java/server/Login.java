package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;


public class Login implements HttpHandler {


    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "";
        String method = httpExchange.getRequestMethod();

        if(method.equals("GET")) {
            response = "<html><body>" +
                    "<form method=\"POST\" action=\"welcomePage\">\n" +
                    "  First name:<br>\n" +
                    "  <input type=\"text\" name=\"mail\" value=\"Email\">\n" +
                    "  <br>\n" +
                    "  Last name:<br>\n" +
                    "  <input type=\"text\" name=\"password\" value=\"\">\n" +
                    "  <br><br>\n" +
                    "  <input type=\"submit\" value=\"Submit\">\n" +
                    "</form> " +
                    "</body></html>";
        }
        sendResponse(httpExchange, response);
    }

    /**
     * Form data is sent as a urlencoded string. Thus we have to parse this string to get data that we want.
     * See: https://en.wikipedia.org/wiki/POST_(HTTP)
     */
    private void sendResponse(HttpExchange httpExchange, String response) throws IOException {
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();

    }
}
