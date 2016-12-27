package com.jordifierro.androidbase.data.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.jordifierro.androidbase.data.net.RestApi;
import com.jordifierro.androidbase.data.net.error.RestApiErrorException;
import com.jordifierro.androidbase.data.utils.TestUtils;
import com.jordifierro.androidbase.domain.entity.CreatedWrapper;
import com.jordifierro.androidbase.domain.entity.NoteEntity;
import com.jordifierro.androidbase.domain.entity.ParseACLJsonAdapter;
import com.jordifierro.androidbase.domain.entity.ParsePermissionWrapper;
import com.jordifierro.androidbase.domain.entity.UpdatedWrapper;
import com.jordifierro.androidbase.domain.entity.UserEntity;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import io.reactivex.observers.TestObserver;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.jordifierro.androidbase.data.net.RestApi.PARSE_SESSION_KEY;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

@SuppressWarnings("unchecked")
public class NoteDataRepositoryTest extends BaseDataRepositoryTest {

    private MockWebServer mockWebServer;
    private NoteDataRepository noteDataRepository;
    private TestObserver testObserver;

    private UserEntity fakeUser;
    private NoteEntity fakeNote;

    @Before
    public void setUp() throws IOException {
        this.mockWebServer = new MockWebServer();
        this.mockWebServer.start();

        this.noteDataRepository = new NoteDataRepository(
                new Retrofit.Builder()
                        .baseUrl(mockWebServer.url(FAKE_SERVER))
                        .addConverterFactory(
                                GsonConverterFactory.create(new GsonBuilder()
                                        .registerTypeAdapter(ParsePermissionWrapper.class, new ParseACLJsonAdapter())
                                        .create())
                        )
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build()
                        .create(RestApi.class)
        );

        this.testObserver = new TestObserver();

        this.fakeUser = new UserEntity("some@mail");
        this.fakeUser.setSessionToken(AUTH_TOKEN);
        this.fakeNote = new NoteEntity(NOTE_OBJECT_ID, NOTE_TITLE, NOTE_CONTENT);
    }

    @After
    public void tearDown() throws Exception {
        this.mockWebServer.shutdown();
    }

    @Test
    public void testCreateNoteRequest() throws Exception {
        {
            this.mockWebServer.enqueue(new MockResponse());

            this.noteDataRepository.createNote(this.fakeUser, this.fakeNote)
                    .subscribe(this.testObserver);
            RecordedRequest request = this.mockWebServer.takeRequest();

            assertEquals(FAKE_SERVER + RestApi.URL_PATH_CLASSES_NOTE, request.getPath());
            assertEquals("POST", request.getMethod());
            assertEquals(AUTH_TOKEN, request.getHeader(PARSE_SESSION_KEY));
            final Buffer requestBody = request.getBody();
            final String actual = requestBody.readUtf8();
            // the request body is the one that you pass into the server
            assertEquals(new Gson().toJson(this.fakeNote), actual);
        }
    }

    @Test
    public void testGetNoteRequest() throws Exception {
        this.mockWebServer.enqueue(new MockResponse());

        this.noteDataRepository.getNote(this.fakeUser, this.fakeNote.getObjectId()).subscribe(this.testObserver);

        RecordedRequest request = this.mockWebServer.takeRequest();
        assertEquals(getFormattedUrl(this.fakeNote.getObjectId(), RestApi.URL_PATH_CLASSES_NOTE_OBJECT_ID), request.getPath());
        assertEquals("GET", request.getMethod());
        assertEquals(AUTH_TOKEN, request.getHeader(PARSE_SESSION_KEY));
        final String actual = request.getBody().readUtf8();
        // the request body is the one that you pass into the server
        assertEquals("", actual);
    }


    @Test
    public void testGetNotesRequest() throws Exception {
        this.mockWebServer.enqueue(new MockResponse());

        this.noteDataRepository.getNotes(this.fakeUser).subscribe(this.testObserver);
        RecordedRequest request = this.mockWebServer.takeRequest();
        assertEquals(FAKE_SERVER + RestApi.URL_PATH_CLASSES_NOTE, request.getPath());
        assertEquals("GET", request.getMethod());
        assertEquals(AUTH_TOKEN, request.getHeader(PARSE_SESSION_KEY));
        // the request body is the one that you pass into the server
        assertEquals("", request.getBody().readUtf8());
    }


    @Test
    public void testCreateNoteSuccessResponse() throws Exception {
        this.mockWebServer.enqueue(new MockResponse().setResponseCode(201).setBody(
                FileUtils.readFileToString(
                        TestUtils.getFileFromPath(this, "res/note_create_ok.json"))));

        this.noteDataRepository.createNote(this.fakeUser, this.fakeNote)
                .subscribe(this.testObserver);
        this.testObserver.awaitTerminalEvent();

        CreatedWrapper responseNote = (CreatedWrapper) ((List<Object>) this.testObserver.getEvents().get(0)).get(0);
        assertTrue(responseNote.getObjectId().length() > 0);
        assertTrue(responseNote.getCreatedAt().length() > 0);
        assertTrue(responseNote.getSessionToken() == null);
    }


    @Test
    public void testGetNoteSuccessResponse() throws Exception {
        this.mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody(
                FileUtils.readFileToString(
                        TestUtils.getFileFromPath(this, "res/note_get_ok.json"))));


        this.noteDataRepository.getNote(this.fakeUser, NOTE_OBJECT_ID).subscribe(this.testObserver);
        this.testObserver.awaitTerminalEvent();

        NoteEntity responseNote = (NoteEntity) ((List<Object>) testObserver.getEvents().get(0)).get(0);
        assertTrue(NOTE_OBJECT_ID.equals(responseNote.getObjectId()));
        assertTrue(responseNote.getACL().getPermissionArrayList().size() == 1);
        assertTrue(USER_OBJECT_ID.equals(responseNote.getACL().getPermissionArrayList().get(0).getObjectId()));
        assertTrue(responseNote.getTitle().length() > 0);
        assertTrue(responseNote.getContent().length() > 0);
    }

    @Test
    public void testCreateNoteErrorResponse() throws Exception {
        this.mockWebServer.enqueue(new MockResponse().setResponseCode(422).setBody(
                FileUtils.readFileToString(
                        TestUtils.getFileFromPath(this, "res/note_create_error.json"))));

        this.noteDataRepository.createNote(this.fakeUser, this.fakeNote)
                .subscribe(this.testObserver);
        this.testObserver.awaitTerminalEvent();

        this.testObserver.assertValueCount(0);
        RestApiErrorException error = (RestApiErrorException)
                this.testObserver.errors().get(0);
        assertEquals(102, error.getStatusCode());
        assertEquals("Note Created error", error.getMessage());
    }


    @Test
    public void testGetNoteErrorResponse() throws Exception {
        this.mockWebServer.enqueue(new MockResponse().setResponseCode(404).setBody(
                FileUtils.readFileToString(
                        TestUtils.getFileFromPath(this, "res/note_get_error.json"))));

        this.noteDataRepository.getNote(this.fakeUser, NOTE_OBJECT_ID).subscribe(this.testObserver);
        this.testObserver.awaitTerminalEvent();

        this.testObserver.assertValueCount(0);
        RestApiErrorException error = (RestApiErrorException)
                this.testObserver.errors().get(0);
        assertEquals(101, error.getStatusCode());
        assertEquals("object not found for get", error.getMessage());
    }

    @Test
    public void testGetNotesSuccessResponse() throws Exception {
        this.mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody(
                FileUtils.readFileToString(
                        TestUtils.getFileFromPath(this, "res/note_getall_ok.json"))));

        this.noteDataRepository.getNotes(this.fakeUser).subscribe(this.testObserver);
        this.testObserver.awaitTerminalEvent();

        List<NoteEntity> responseNotes = (List<NoteEntity>) ((List) this.testObserver.getEvents().get(0)).get(0);
        assertTrue(responseNotes.size() > 0);
        assertTrue(responseNotes.get(0).getTitle().length() > 0);
        assertTrue(responseNotes.get(0).getContent().length() > 0);
    }

    @Test
    public void testGetNotesErrorResponse() throws Exception {
        this.mockWebServer.enqueue(new MockResponse().setResponseCode(401));

        this.noteDataRepository.getNotes(this.fakeUser).subscribe(this.testObserver);
        this.testObserver.awaitTerminalEvent();

        this.testObserver.assertValueCount(0);
        RestApiErrorException error = (RestApiErrorException)
                this.testObserver.errors().get(0);
        assertEquals(401, error.getStatusCode());
        assertTrue(error.getMessage().length() > 0);
    }

    @Test
    public void testUpdateNoteRequest() throws Exception {
        this.mockWebServer.enqueue(new MockResponse());
        this.noteDataRepository.updateNote(this.fakeUser, this.fakeNote)
                .subscribe(this.testObserver);
        RecordedRequest request = this.mockWebServer.takeRequest();
        assertEquals(getFormattedUrl(this.fakeNote.getObjectId(), RestApi.URL_PATH_CLASSES_NOTE_OBJECT_ID), request.getPath());
        assertEquals("PUT", request.getMethod());
        assertEquals(AUTH_TOKEN, request.getHeader(RestApi.PARSE_SESSION_KEY));
        assertEquals(new Gson().toJson(this.fakeNote), request.getBody().readUtf8());
    }


    @Test
    public void testUpdateNoteSuccessResponse() throws Exception {
        this.mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody(
                FileUtils.readFileToString(
                        TestUtils.getFileFromPath(this, "res/note_update_ok.json"))));

        this.noteDataRepository.updateNote(this.fakeUser, this.fakeNote)
                .subscribe(this.testObserver);
        this.testObserver.awaitTerminalEvent();

        UpdatedWrapper updatedWrapper = (UpdatedWrapper) ((List) this.testObserver.getEvents().get(0)).get(0);
        assertTrue(updatedWrapper.getUpdatedAt().length() > 0);
    }

    @Test
    public void testUpdateNoteErrorResponse() throws Exception {
        this.mockWebServer.enqueue(new MockResponse().setResponseCode(404).setBody(
                FileUtils.readFileToString(
                        TestUtils.getFileFromPath(this, "res/note_update_error.json"))));

        this.noteDataRepository.updateNote(this.fakeUser, this.fakeNote)
                .subscribe(this.testObserver);
        this.testObserver.awaitTerminalEvent();

        this.testObserver.assertValueCount(0);
        RestApiErrorException error = (RestApiErrorException)
                this.testObserver.errors().get(0);
        assertEquals(101, error.getStatusCode());
        assertEquals("object not found for update", error.getMessage());
    }

    @Test
    public void testDeleteNoteRequest() throws Exception {
        this.mockWebServer.enqueue(new MockResponse());

        this.noteDataRepository.deleteNote(this.fakeUser, this.fakeNote.getObjectId())
                .subscribe(this.testObserver);

        RecordedRequest request = this.mockWebServer.takeRequest();
        assertEquals(getFormattedUrl(this.fakeNote.getObjectId(), RestApi.URL_PATH_CLASSES_NOTE_OBJECT_ID), request.getPath());
        assertEquals("DELETE", request.getMethod());
        assertEquals(AUTH_TOKEN, request.getHeader(RestApi.PARSE_SESSION_KEY));
        assertEquals("", request.getBody().readUtf8());
    }

    @Test
    public void testDeleteNoteSuccessResponse() throws Exception {
        this.mockWebServer.enqueue(new MockResponse().setResponseCode(204));
        this.noteDataRepository.deleteNote(this.fakeUser, NOTE_OBJECT_ID).subscribe(this.testObserver);
        this.testObserver.awaitTerminalEvent();
        this.testObserver.assertValueCount(1);
    }

    @Test
    public void testDeleteUserErrorResponse() throws Exception {
        this.mockWebServer.enqueue(new MockResponse().setResponseCode(401));

        this.noteDataRepository.deleteNote(this.fakeUser, NOTE_OBJECT_ID).subscribe(this.testObserver);
        this.testObserver.awaitTerminalEvent();

        this.testObserver.assertValueCount(0);
        RestApiErrorException error = (RestApiErrorException)
                this.testObserver.errors().get(0);
        assertEquals(401, error.getStatusCode());
        assertTrue(error.getMessage().length() > 0);
    }

}
