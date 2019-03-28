package server.adminJavaPages;

import com.sun.net.httpserver.Headers;
import controllers.dao.CreepyGuyDAO;
import controllers.dao.LoginAccesDAO;
import models.CreepyGuyModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.helpers.CookieHelper;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.sql.Connection;
import java.util.Optional;

import static org.mockito.Mockito.*;

class ClassEditorTest {

    private Connection connection;
    private HttpExchange httpExchange;
    private ClassEditor classEditor;
    private CookieHelper cookieHelper;
    private LoginAccesDAO loginAccesDAO;
    private OutputStream os;
    private Optional<HttpCookie> cookie;
    private HttpCookie httpCookie;
    private Headers headers;
    private CreepyGuyDAO creepyGuyDAO;
    private CreepyGuyModel creepyGuyModel;

    @BeforeEach
    void setUp() {
        this.connection = mock(Connection.class);
        this.httpExchange = mock(HttpExchange.class);
        this.classEditor = spy(new ClassEditor(connection));
        this.cookieHelper = mock(CookieHelper.class);
        this.loginAccesDAO = mock(LoginAccesDAO.class);
        this.os = mock(OutputStream.class);
        this.httpCookie = mock(HttpCookie.class);
        this.cookie = Optional.of(httpCookie);
        this.headers = mock(Headers.class);
        this.creepyGuyDAO = mock(CreepyGuyDAO.class);
        this.creepyGuyModel = mock(CreepyGuyModel.class);
    }


    @Test
    void testIfGenerateClassEditorPage() throws IOException {
        when(cookieHelper.getSessionIdCookie(httpExchange)).thenReturn(cookie);
        when(httpExchange.getResponseHeaders()).thenReturn(headers);
        when(headers.getFirst("Cookie")).thenReturn("");
        when(httpCookie.getValue()).thenReturn("anyCookieValue");
        when(httpExchange.getRequestMethod()).thenReturn("GET");
        when(creepyGuyDAO.getAdminBySessionId(anyString())).thenReturn(creepyGuyModel);
        when(loginAccesDAO.checkSessionPresent(anyString())).thenReturn(true);
        when(httpExchange.getResponseBody()).thenReturn(os);


        classEditor.handle(httpExchange);
        verify(httpExchange).sendResponseHeaders(any(), any());
        verify(os).write(any());
        verify(os).close();
    }


}