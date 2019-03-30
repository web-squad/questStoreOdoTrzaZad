package server.adminJavaPages;

import com.sun.net.httpserver.Headers;
import controllers.dao.CreepyGuyDAO;
import controllers.dao.LoginAccesDAO;
import models.CreepyGuyModel;
import models.Room;
import org.jtwig.JtwigTemplate;
import org.jtwig.environment.Environment;
import org.jtwig.resource.reference.ResourceReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.reflection.FieldSetter;
import server.helpers.CookieHelper;

import com.sun.net.httpserver.HttpExchange;
import server.helpers.FormDataParser;

import javax.sql.rowset.spi.TransactionalWriter;
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
        this.loginAccesDAO = mock(LoginAccesDAO.class);
        this.formDataParser = mock(FormDataParser.class);
    }

    @Test
    void testIfGenerateClassEditorPage() throws IOException, NoSuchFieldException {
        stubActiveSessionTest("GET");
        setFields();
        classEditor.handle(httpExchange);
        verify(creepyGuyModel).getNickName();
        verifyResponse();
    }

    private void stubActiveSessionTest(String method) {
        when(cookieHelper.getSessionIdCookie(httpExchange)).thenReturn(Optional.of(httpCookie));
        when(httpCookie.getValue()).thenReturn("someValue");
        when(loginAccesDAO.checkSessionPresent(anyString())).thenReturn(true);
        when(httpExchange.getRequestMethod()).thenReturn(method);
        when(creepyGuyDAO.getAdminBySessionId(anyString())).thenReturn(creepyGuyModel);
        when(creepyGuyModel.getNickName()).thenReturn("someName");
        when(httpExchange.getResponseBody()).thenReturn(outputStream);
    }

    private void setFields() throws NoSuchFieldException {
        FieldSetter.setField(classEditor, classEditor.getClass().getDeclaredField("cookieHelper"), cookieHelper);
        FieldSetter.setField(classEditor, classEditor.getClass().getDeclaredField("loginAccesDAO"), loginAccesDAO);
        FieldSetter.setField(classEditor, classEditor.getClass().getDeclaredField("creepyGuyDAO"), creepyGuyDAO);
    }

    private void verifyResponse() throws IOException {
        verify(httpExchange).sendResponseHeaders(anyInt(), anyLong());
        verify(httpExchange).getResponseBody();
        verify(outputStream).write(any());
        verify(outputStream).close();
    }

    @Test
    void testIfNoCookieChangeServerLocation() throws IOException, NoSuchFieldException {
        when(cookieHelper.getSessionIdCookie(httpExchange)).thenReturn(Optional.empty());
        when(loginAccesDAO.checkSessionPresent(anyString())).thenReturn(true);
        when(httpExchange.getResponseHeaders()).thenReturn(headers);
        setFields();
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
        classEditor.handle(httpExchange);
        verify(headers).set(anyString(), anyString());
    }

    @Test
    void testHandleSearch() throws IOException, NoSuchFieldException {
        setUpHandlePost();
        when(formDataParser.getData(httpExchange)).thenReturn(getDummyInputs("search"));
        classEditor.handle(httpExchange);
        verify(creepyGuyDAO).getRoomById(anyString());
        verify(creepyGuyModel).getNickName();
        verifyResponse();
    }

    private void setUpHandlePost() throws NoSuchFieldException {
        stubActiveSessionTest("POST");
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
    void testHandleEdit() throws IOException, NoSuchFieldException {
        setUpHandlePost();
        when(formDataParser.getData(httpExchange)).thenReturn(getDummyInputs("edit"));
        classEditor.handle(httpExchange);
        verify(creepyGuyDAO).editRoom(any(),anyString());
        verify(creepyGuyModel).getNickName();
        verifyResponse();
    }

    @Test
    void testHandleDelete() throws IOException, NoSuchFieldException {
        setUpHandlePost();
        when(formDataParser.getData(httpExchange)).thenReturn(getDummyInputs("delete"));
        classEditor.handle(httpExchange);
        verify(creepyGuyDAO).deleteRoom(anyString());
        verify(creepyGuyModel).getNickName();
        verifyResponse();
    }

    @Test
    void testHandleAdd() throws IOException, NoSuchFieldException {
        setUpHandlePost();
        when(formDataParser.getData(httpExchange)).thenReturn(getDummyInputs("add"));
        classEditor.handle(httpExchange);
        verify(creepyGuyDAO).addRoom(any());
        verify(creepyGuyModel).getNickName();
        verifyResponse();
    }
}