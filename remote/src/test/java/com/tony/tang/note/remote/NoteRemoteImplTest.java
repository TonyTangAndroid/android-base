package com.tony.tang.note.remote;

import com.google.common.truth.Truth;
import com.google.gson.Gson;
import com.tony.tang.note.domain.entity.NoteData;
import com.tony.tang.note.domain.entity.NoteEntity;
import com.tony.tang.note.domain.exception.RestApiErrorException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.observers.TestObserver;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.google.common.truth.Truth.assertThat;

public class NoteRemoteImplTest extends BaseDataRepositoryTest {

    private NoteData fakeNote;
    private MockWebServer mockWebServer;
    private NoteRemoteImpl noteRemoteImpl;
    private Map<String, Object> fakeQueryParam;


    @Before
    public void setUp() throws IOException {

        MockitoAnnotations.initMocks(this);


        this.mockWebServer = new MockWebServer();
        this.mockWebServer.start();

        this.noteRemoteImpl = new NoteRemoteImpl(restApi());
        this.fakeNote = NoteData.builder().objectId(OBJECT_ID).title(MOCK_NOTE_TITLE).content(MOCK_NOTE_CONTENT).build();
        fakeQueryParam = new HashMap<>();
    }

    private RestApi restApi() {
        Gson gson = WrapperGsonHelper.build();
        Retrofit retrofit = retrofit(gson);
        return retrofit.create(RestApi.class);
    }

    private Retrofit retrofit(Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(mockWebServer.url(MOCK_SERVER))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }


    @After
    public void tearDown() throws IOException {
        this.mockWebServer.shutdown();
    }

    @Test
    public void testCreateNoteRequest() throws InterruptedException {
        {
            TestObserver<String> testObserver = new TestObserver<>();
            this.mockWebServer.enqueue(new MockResponse());
            this.noteRemoteImpl.createNote(this.fakeNote).subscribe(testObserver);
            RecordedRequest request = this.mockWebServer.takeRequest();
            assertThat(request.getPath()).endsWith("classes/note");
            assertThat(request.getMethod()).isEqualTo(HttpMethod.POST);
            assertThat(request.getBody().readUtf8()).isEqualTo(new Gson().toJson(this.fakeNote));
        }
    }

    @Test
    public void testGetNoteSuccessResponseRestAPI() throws IOException {
        TestObserver<NoteEntity> testObserver = new TestObserver<>();
        String json = TestUtils.json("note_get_ok.json", this);
        this.mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody(json));
        this.noteRemoteImpl.getNote(OBJECT_ID).subscribe(testObserver);
        testObserver.awaitTerminalEvent();
        Truth.assertThat(testObserver.values().size()).isEqualTo(1);
        Truth.assertThat(testObserver.values().get(0)).isEqualTo(WrapperGsonHelper.build().fromJson(json, NoteEntity.class));
    }

    @Test
    public void testGetNotesRequest() throws IOException {
        TestObserver<List<NoteEntity>> testObserver = new TestObserver<>();

        String json = TestUtils.json("note_getall_ok.json", this);
        MockResponse response = new MockResponse().setResponseCode(200).setBody(json);
        this.mockWebServer.enqueue(response);

        this.noteRemoteImpl.getNotes(fakeQueryParam).subscribe(testObserver);
        testObserver.awaitTerminalEvent();

        Truth.assertThat(testObserver.values().size()).isEqualTo(1);
        Truth.assertThat(testObserver.values().get(0)).isEqualTo(WrapperGsonHelper.build().fromJson(json, NoteEntitiesWrapper.class).results());

    }

    @Test
    public void testCreateNoteSuccessResponse() throws IOException {
        TestObserver<String> testObserver = new TestObserver<>();
        String json = TestUtils.json("note_create_ok.json", this);
        this.mockWebServer.enqueue(new MockResponse().setResponseCode(201).setBody(json));
        this.noteRemoteImpl.createNote(this.fakeNote).subscribe(testObserver);
        testObserver.awaitTerminalEvent();
        Truth.assertThat(testObserver.getEvents().get(0).get(0)).isEqualTo(new Gson().fromJson(json, CreatedWrapper.class).getObjectId());
    }

    @Test
    public void testCreateNoteErrorResponse() throws IOException {
        TestObserver<String> testObserver = new TestObserver<>();

        String json = TestUtils.json("note_create_error.json", this);
        this.mockWebServer.enqueue(new MockResponse().setResponseCode(422).setBody(json));

        this.noteRemoteImpl.createNote(this.fakeNote).subscribe(testObserver);
        testObserver.awaitTerminalEvent();
        testObserver.assertValueCount(0);

        RestApiErrorException actual = (RestApiErrorException) testObserver.errors().get(0);
        Truth.assertThat(actual.getStatusCode()).isEqualTo(102);
        Truth.assertThat(actual.getMessage()).isEqualTo("Note Created error");
    }

    @Test
    public void testUpdateNoteRequest() throws InterruptedException {
        TestObserver testObserver = new TestObserver<>();
        this.mockWebServer.enqueue(new MockResponse());
        this.noteRemoteImpl.updateNote(this.fakeNote).subscribe(testObserver);
        testObserver.awaitTerminalEvent();
        RecordedRequest request = this.mockWebServer.takeRequest();
        Truth.assertThat(request.getPath()).isEqualTo(getFormattedUrl(this.fakeNote.objectId(), RestApi.URL_PATH_CLASSES_NOTE_OBJECT_ID));
        Truth.assertThat(request.getMethod()).isEqualTo(HttpMethod.PUT);
        String expected = new Gson().toJson(this.fakeNote);
        Truth.assertThat(request.getBody().readUtf8()).isEqualTo(expected);
    }


    @Test
    public void testUpdateNoteSuccessResponse() throws IOException {
        TestObserver testObserver = new TestObserver<>();
        String json = TestUtils.json("note_update_ok.json", this);
        this.mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody(json));

        this.noteRemoteImpl.updateNote(this.fakeNote).subscribe(testObserver);

        testObserver.awaitTerminalEvent();
        testObserver.assertComplete();
        testObserver.assertNoErrors();

    }

    @Test
    public void testUpdateNoteErrorResponse() throws IOException {

        TestObserver testObserver = new TestObserver<>();
        String json = TestUtils.json("note_update_error.json", this);
        this.mockWebServer.enqueue(new MockResponse().setResponseCode(404).setBody(json));

        this.noteRemoteImpl.updateNote(this.fakeNote).subscribe(testObserver);
        testObserver.awaitTerminalEvent();
        testObserver.assertValueCount(0);
        RestApiErrorException actual = (RestApiErrorException) testObserver.errors().get(0);
        Truth.assertThat(actual.getStatusCode()).isEqualTo(101);
        Truth.assertThat(actual.getMessage()).isEqualTo("object not found for update");
    }

    @Test
    public void testDeleteNoteRequest() throws InterruptedException {

        TestObserver testObserver = new TestObserver<>();

        this.mockWebServer.enqueue(new MockResponse());

        this.noteRemoteImpl.deleteNote(this.fakeNote.objectId()).subscribe(testObserver);

        RecordedRequest request = this.mockWebServer.takeRequest();

        Truth.assertThat(request.getPath()).isEqualTo(getFormattedUrl(this.fakeNote.objectId(), RestApi.URL_PATH_CLASSES_NOTE_OBJECT_ID));
        Truth.assertThat(request.getMethod()).isEqualTo(HttpMethod.DELETE);
        Truth.assertThat(request.getBody().readUtf8()).isEmpty();
    }

    @Test
    public void testDeleteNoteSuccessResponse() {
        TestObserver testObserver = new TestObserver<>();
        this.mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody("{}"));
        this.noteRemoteImpl.deleteNote(OBJECT_ID).subscribe(testObserver);
        testObserver.awaitTerminalEvent();
        testObserver.assertNoErrors();
        testObserver.assertComplete();
    }

    @Test
    public void testDeleteUserErrorResponse() {
        TestObserver testObserver = new TestObserver<>();

        this.mockWebServer.enqueue(new MockResponse().setResponseCode(401));
        this.noteRemoteImpl.deleteNote(OBJECT_ID).subscribe(testObserver);
        testObserver.awaitTerminalEvent();

        testObserver.assertValueCount(0);
        RestApiErrorException actual = (RestApiErrorException) testObserver.errors().get(0);
        Truth.assertThat(actual.getStatusCode()).isEqualTo(401);
    }

}
