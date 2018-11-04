//package com.jordifierro.androidbase.data.repository;
//
//import com.google.common.truth.Truth;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.jordifierro.androidbase.data.net.RestApi;
//import com.jordifierro.androidbase.data.utils.TestUtils;
//import com.jordifierro.androidbase.domain.entity.NoteEntitiesWrapper;
//import com.jordifierro.androidbase.domain.entity.NoteEntity;
//import com.jordifierro.androidbase.domain.entity.ParseACLJsonAdapter;
//import com.jordifierro.androidbase.domain.entity.ParsePermissionWrapper;
//import com.jordifierro.androidbase.domain.exception.RestApiErrorException;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import io.reactivex.observers.TestObserver;
//import okhttp3.internal.http.HttpMethod;
//import okhttp3.mockwebserver.MockResponse;
//import okhttp3.mockwebserver.MockWebServer;
//import okhttp3.mockwebserver.RecordedRequest;
//
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.verifyNoMoreInteractions;
//
//public class CloudNoteListCloudDataStoreTest extends BaseDataRepositoryTest {
//
//    NoteCloudDataStore noteCloudDataStore;
//    private MockWebServer mockWebServer;
//    @Mock
//    private NoteDiskCacheImpl noteDiskCacheImpl;
//    private NoteEntity fakeNote;
//    private Map<String, Object> fakeQueryParam;
//
//    @Mock
//    private NoteRemote noteRemote;
//    @Before
//    public void setUp() throws IOException {
//
//        MockitoAnnotations.initMocks(this);
//        this.mockWebServer = new MockWebServer();
//        this.mockWebServer.start();
//        this.noteCloudDataStore = new NoteCloudDataStore(noteRemote, noteDiskCacheImpl);
//        this.fakeNote = new NoteEntity(MOCK_NOTE_OBJECT_ID, MOCK_NOTE_TITLE, MOCK_NOTE_CONTENT);
//        fakeQueryParam = new HashMap<>();
//    }
//
//    private Gson gson() {
//        GsonBuilder gsonBuilder = new GsonBuilder();
//        gsonBuilder.registerTypeAdapter(ParsePermissionWrapper.class, new ParseACLJsonAdapter());
//        return gsonBuilder.create();
//    }
//
//    @After
//    public void tearDown() throws IOException {
//        this.mockWebServer.shutdown();
//    }
//
//    @Test
//    public void getNoteEntityRequestWithCorrectConfigure() throws InterruptedException {
//        TestObserver<NoteEntity> testObserver = new TestObserver<>();
//        this.mockWebServer.enqueue(new MockResponse());
//        this.noteCloudDataStore.getNoteEntity(this.fakeNote.getObjectId()).subscribe(testObserver);
//
//        RecordedRequest request = this.mockWebServer.takeRequest();
//        Truth.assertThat(request.getPath()).isEqualTo(getFormattedUrl(this.fakeNote.getObjectId(), RestApi.URL_PATH_CLASSES_NOTE_OBJECT_ID));
//        Truth.assertThat(request.getMethod()).isEqualTo(HttpMethod.GET);
//        Truth.assertThat(request.getBody().readUtf8()).isEmpty();
//
//    }
//
//    @Test
//    public void testGetNoteSuccessResponseRestAPI() throws IOException {
//        TestObserver<NoteEntity> testObserver = new TestObserver<>();
//        String json = TestUtils.json("note_get_ok.json", this);
//        this.mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody(json));
//        this.noteCloudDataStore.getNoteEntity(MOCK_NOTE_OBJECT_ID).subscribe(testObserver);
//        testObserver.awaitTerminalEvent();
//        Truth.assertThat(testObserver.values().size()).isEqualTo(1);
//
//
//        Truth.assertThat(testObserver.values().get(0)).isEqualTo(gson().fromJson(json, NoteEntity.class));
//    }
//
//    @Test
//    public void testGetNoteSuccessResponseCached() throws IOException {
//        TestObserver<NoteEntity> testObserver = new TestObserver<>();
//        String json = TestUtils.json("note_get_ok.json", this);
//        this.mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody(json));
//        this.noteCloudDataStore.getNoteEntity(MOCK_NOTE_OBJECT_ID).subscribe(testObserver);
//        testObserver.awaitTerminalEvent();
//        Truth.assertThat(testObserver.values().size()).isEqualTo(1);
//        NoteEntity expected = gson().fromJson(json, NoteEntity.class);
//
//
//        verify(noteDiskCacheImpl).put(expected);
//        verifyNoMoreInteractions(noteDiskCacheImpl);
//    }
//
//    @Test
//    public void testGetNoteErrorResponseRestApi() throws IOException {
//        TestObserver<NoteEntity> testObserver = new TestObserver<>();
//
//        String json = TestUtils.json("note_get_error.json", this);
//        this.mockWebServer.enqueue(new MockResponse().setResponseCode(404).setBody(json));
//
//        this.noteCloudDataStore.getNoteEntity(MOCK_NOTE_OBJECT_ID).subscribe(testObserver);
//        testObserver.awaitTerminalEvent();
//
//        testObserver.assertValueCount(0);
//        RestApiErrorException actual = (RestApiErrorException) testObserver.errors().get(0);
//        Truth.assertThat(actual.getStatusCode()).isEqualTo(101);
//        Truth.assertThat(actual.getMessage()).isEqualTo("object not found for get");
//    }
//
//    @Test
//    public void testGetNotesSuccessResponse() throws IOException {
//
//        TestObserver<List<NoteEntity>> testObserver = new TestObserver<>();
//
//        String json = TestUtils.json("note_getall_ok.json", this);
//        MockResponse response = new MockResponse().setResponseCode(200).setBody(json);
//        this.mockWebServer.enqueue(response);
//
//        this.noteCloudDataStore.getNotes(fakeQueryParam).subscribe(testObserver);
//        testObserver.awaitTerminalEvent();
//
//        Truth.assertThat(testObserver.values().size()).isEqualTo(1);
//        Truth.assertThat(testObserver.values().get(0)).isEqualTo(gson().fromJson(json, NoteEntitiesWrapper.class).getResults());
//    }
//
//    @Test
//    public void testGetNotesSuccessResponseCached() throws IOException {
//
//        TestObserver<List<NoteEntity>> testObserver = new TestObserver<>();
//
//        String json = TestUtils.json("note_getall_ok.json", this);
//        MockResponse response = new MockResponse().setResponseCode(200).setBody(json);
//        this.mockWebServer.enqueue(response);
//
//        this.noteCloudDataStore.getNoteEntity(fakeQueryParam).subscribe(testObserver);
//        testObserver.awaitTerminalEvent();
//
//        Truth.assertThat(testObserver.values().size()).isEqualTo(1);
//        List<NoteEntity> expected = gson().fromJson(json, NoteEntitiesWrapper.class).getResults();
//        Truth.assertThat(testObserver.values().get(0)).isEqualTo(expected);
//
//        verify(noteDiskCacheImpl).put(expected);
//        verifyNoMoreInteractions(noteDiskCacheImpl);
//    }
//
//    @Test
//    public void testGetNotesErrorResponse() {
//
//        TestObserver<List<NoteEntity>> testObserver = new TestObserver<>();
//
//        this.mockWebServer.enqueue(new MockResponse().setResponseCode(401));
//        this.noteCloudDataStore.getNotes(fakeQueryParam).subscribe(testObserver);
//        testObserver.awaitTerminalEvent();
//
//        testObserver.assertValueCount(0);
//        RestApiErrorException error = (RestApiErrorException) testObserver.errors().get(0);
//        Truth.assertThat(error.getStatusCode()).isEqualTo(401);
//        Truth.assertThat(error.getMessage()).isEqualTo("Client Error");
//    }
//}
