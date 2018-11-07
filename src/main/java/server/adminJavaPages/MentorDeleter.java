package server.adminJavaPages;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

public class MentorDeleter implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {


        // get a template file
        JtwigTemplate template = JtwigTemplate.classpathTemplate("HTML/adminPages/mentorDeleter.twig");

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