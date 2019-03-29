package server.adminJavaPages;

import com.sun.net.httpserver.Headers;
import controllers.dao.CreepyGuyDAO;
import controllers.dao.LoginAccesDAO;
import models.CreepyGuyModel;
import models.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.reflection.FieldSetter;
import server.helpers.CookieHelper;

import com.sun.net.httpserver.HttpExchange;
import server.helpers.FormDataParser;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.*;
class ClassEditorTest {

    private Connection connection;
    private ClassEditor classEditor;
    private HttpExchange httpExchange;
    private OutputStream outputStream;
    private CookieHelper cookieHelper;
    private HttpCookie httpCookie;
    private LoginAccesDAO loginAccesDAO;
    private CreepyGuyDAO creepyGuyDAO;
    private CreepyGuyModel creepyGuyModel;
    private FormDataParser formDataParser;
    private Headers headers;

    @BeforeEach
    void setUp() {
        this.connection = mock(Connection.class);
        this.classEditor = new ClassEditor(connection);
        this.httpExchange = mock(HttpExchange.class);
        this.httpCookie = mock(HttpCookie.class);
        this.cookieHelper = mock(CookieHelper.class);
        this.loginAccesDAO = mock(LoginAccesDAO.class);
        this.creepyGuyModel = mock(CreepyGuyModel.class);
        this.creepyGuyDAO = mock(CreepyGuyDAO.class);
        this.outputStream = mock(OutputStream.class);
        this.headers = mock(Headers.class);
    }

    @Test
    void testIfGenerateClassEditorPage() throws IOException, NoSuchFieldException {
        when(cookieHelper.getSessionIdCookie(httpExchange)).thenReturn(Optional.of(httpCookie));
        when(httpCookie.getValue()).thenReturn("someValue");
        when(loginAccesDAO.checkSessionPresent(anyString())).thenReturn(true);
        when(httpExchange.getRequestMethod()).thenReturn("GET");
        when(creepyGuyDAO.getAdminBySessionId(anyString())).thenReturn(creepyGuyModel);
        when(creepyGuyModel.getNickName()).thenReturn("someName");
        when(httpExchange.getResponseBody()).thenReturn(outputStream);
        setFields();
        verifyGeneratePage();
    }

    private void setFields() throws NoSuchFieldException {
        FieldSetter.setField(classEditor, classEditor.getClass().getDeclaredField("cookieHelper"), cookieHelper);
        FieldSetter.setField(classEditor, classEditor.getClass().getDeclaredField("loginAccesDAO"), loginAccesDAO);
        FieldSetter.setField(classEditor, classEditor.getClass().getDeclaredField("creepyGuyDAO"), creepyGuyDAO);
    }

    private void verifyGeneratePage() throws IOException {
        classEditor.handle(httpExchange);
        verify(httpExchange).sendResponseHeaders(anyInt(), anyLong());
        verify(outputStream).write(any());
        verify(outputStream).close();
    }

    @Test
    void testIfNoCookieChangeServerLocation() throws IOException, NoSuchFieldException {
        when(cookieHelper.getSessionIdCookie(httpExchange)).thenReturn(Optional.empty());
        when(loginAccesDAO.checkSessionPresent(anyString())).thenReturn(true);
        when(httpExchange.getResponseHeaders()).thenReturn(headers);
        setFields();
        verifyChangeServerLocation();
    }

    private void verifyChangeServerLocation() throws IOException, NoSuchFieldException {
        classEditor.handle(httpExchange);
        verify(headers).set(anyString(), anyString());
    }

    @Test
    void testIfNoSessionChangeServerLocation() throws IOException, NoSuchFieldException {
        when(cookieHelper.getSessionIdCookie(httpExchange)).thenReturn(Optional.of(httpCookie));
        when(httpCookie.getValue()).thenReturn("someValue");
        when(loginAccesDAO.checkSessionPresent(anyString())).thenReturn(false);
        when(httpExchange.getResponseHeaders()).thenReturn(headers);
        setFields();
        verifyChangeServerLocation();
    }

    @Test
    void testIfPostSearch() throws IOException, NoSuchFieldException {
        setUpTestIfPost();
        when(formDataParser.getData(httpExchange)).thenReturn(getDummyInputs("search"));
        when(creepyGuyDAO.getRoomById(anyString())).thenReturn(null);
        verifyGeneratePage();
    }

    private void setUpTestIfPost() throws NoSuchFieldException {
        this.formDataParser = mock(FormDataParser.class);
        when(cookieHelper.getSessionIdCookie(httpExchange)).thenReturn(Optional.of(httpCookie));
        when(httpCookie.getValue()).thenReturn("someValue");
        when(loginAccesDAO.checkSessionPresent(anyString())).thenReturn(true);
        when(httpExchange.getRequestMethod()).thenReturn("POST");
        when(creepyGuyDAO.getAdminBySessionId(anyString())).thenReturn(creepyGuyModel);
        when(creepyGuyModel.getNickName()).thenReturn("someName");
        when(httpExchange.getResponseBody()).thenReturn(outputStream);
        FieldSetter.setField(classEditor, classEditor.getClass().getDeclaredField("formDataParser"), formDataParser);
        setFields();
    }

    private Map<String, String> getDummyInputs(String k) {
        Map<String, String> dummyInputs = new HashMap<>();
        dummyInputs.put(k, "v");
        dummyInputs.put("ID", "id");
        dummyInputs.put("name", "n");
        dummyInputs.put("description", "d");
        return dummyInputs;
    }

    @Test
    void testIfPageIsSuccessfullyGeneratedAfterCreepyGuyEditsRoom() throws IOException, NoSuchFieldException {
        setUpTestIfPost();
        when(formDataParser.getData(httpExchange)).thenReturn(getDummyInputs("edit"));
        classEditor.handle(httpExchange);
        verify(creepyGuyDAO).editRoom(any(), any());
    }
}