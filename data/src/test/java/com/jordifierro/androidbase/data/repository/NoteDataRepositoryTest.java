package com.jordifierro.androidbase.data.repository;

import com.google.common.truth.Truth;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jordifierro.androidbase.data.net.RestApi;
import com.jordifierro.androidbase.data.utils.TestUtils;
import com.jordifierro.androidbase.domain.entity.CreatedWrapper;
import com.jordifierro.androidbase.domain.entity.NoteEntity;
import com.jordifierro.androidbase.domain.entity.ParseACLJsonAdapter;
import com.jordifierro.androidbase.domain.entity.ParsePermissionWrapper;
import com.jordifierro.androidbase.domain.exception.RestApiErrorException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class NoteDataRepositoryTest extends BaseDataRepositoryTest {

    private MockWebServer mockWebServer;

    @Mock
    private NoteCloudDataStore noteCloudDataStore;

    @Mock
    private BadgeDataStoreFactory badgeDataStoreFactory;


    private NoteEntity fakeNote;
    private Map<String, Object> fakeQueryParam;
    private NoteDataRepository noteDataRepository;


    @Before
    public void setUp() throws IOException {

        MockitoAnnotations.initMocks(this);


        this.mockWebServer = new MockWebServer();
        this.mockWebServer.start();

        this.noteDataRepository = new NoteDataRepository(restApi(), noteCloudDataStore, badgeDataStoreFactory);
        this.fakeNote = new NoteEntity(MOCK_NOTE_OBJECT_ID, MOCK_NOTE_TITLE, MOCK_NOTE_CONTENT);
        fakeQueryParam = new HashMap<>();
    }

    private RestApi restApi() {
        Gson gson = gson();
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

    private Gson gson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(ParsePermissionWrapper.class, new ParseACLJsonAdapter());
        return gsonBuilder.create();
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
            this.noteDataRepository.createNote(this.fakeNote).subscribe(testObserver);
            RecordedRequest request = this.mockWebServer.takeRequest();
            assertThat(request.getPath()).endsWith("classes/note");
            assertThat(request.getMethod()).isEqualTo(HttpMethod.POST);
            assertThat(request.getBody().readUtf8()).isEqualTo(new Gson().toJson(this.fakeNote));
        }
    }

    @Test
    public void testGetNoteRequest() {

        BadgeDataStore mockStore = mock(BadgeDataStore.class);
        given(mockStore.getNoteEntity(MOCK_NOTE_OBJECT_ID)).willReturn(Single.just(fakeNote));
        given(badgeDataStoreFactory.getDataStore(MOCK_NOTE_OBJECT_ID)).willReturn(mockStore);
        TestObserver<NoteEntity> testObserver = new TestObserver<>();
        this.noteDataRepository.getNote(this.fakeNote.getObjectId()).subscribe(testObserver);

        Truth.assertThat(testObserver.values()).isEqualTo(Collections.singletonList(fakeNote));
        testObserver.assertComplete();

        verify(badgeDataStoreFactory).getDataStore(MOCK_NOTE_OBJECT_ID);
        verifyNoMoreInteractions(badgeDataStoreFactory);
        verify(mockStore).getNoteEntity(MOCK_NOTE_OBJECT_ID);
        verifyNoMoreInteractions(mockStore);
    }


    @Test
    public void testGetNotesRequest() {
        given(noteCloudDataStore.getNotes(fakeQueryParam)).willReturn(Single.just(Collections.emptyList()));
        TestObserver<List<NoteEntity>> testObserver = new TestObserver<>();
        this.noteDataRepository.getNotes(fakeQueryParam).subscribe(testObserver);

        Truth.assertThat(testObserver.getEvents().get(0).get(0)).isEqualTo(Collections.emptyList());
        Truth.assertThat(testObserver.getEvents().get(1)).isEmpty();
        testObserver.assertComplete();

        verify(noteCloudDataStore).getNotes(fakeQueryParam);
        verifyNoMoreInteractions(noteCloudDataStore);
    }

    @Test
    public void testCreateNoteSuccessResponse() throws IOException {
        TestObserver<String> testObserver = new TestObserver<>();
        String json = TestUtils.json("note_create_ok.json", this);
        this.mockWebServer.enqueue(new MockResponse().setResponseCode(201).setBody(json));
        this.noteDataRepository.createNote(this.fakeNote).subscribe(testObserver);
        testObserver.awaitTerminalEvent();
        Truth.assertThat(testObserver.getEvents().get(0).get(0)).isEqualTo(new Gson().fromJson(json, CreatedWrapper.class).getObjectId());
    }

    @Test
    public void testCreateNoteErrorResponse() throws IOException {
        TestObserver<String> testObserver = new TestObserver<>();

        String json = TestUtils.json("note_create_error.json", this);
        this.mockWebServer.enqueue(new MockResponse().setResponseCode(422).setBody(json));

        this.noteDataRepository.createNote(this.fakeNote).subscribe(testObserver);
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
        this.noteDataRepository.updateNote(this.fakeNote).subscribe(testObserver);
        RecordedRequest request = this.mockWebServer.takeRequest();
        Truth.assertThat(request.getPath()).isEqualTo(getFormattedUrl(this.fakeNote.getObjectId(), RestApi.URL_PATH_CLASSES_NOTE_OBJECT_ID));
        Truth.assertThat(request.getMethod()).isEqualTo(HttpMethod.PUT);
        Truth.assertThat(request.getBody().readUtf8()).isEqualTo(new Gson().toJson(this.fakeNote));
    }


    @Test
    public void testUpdateNoteSuccessResponse() throws IOException {
        TestObserver testObserver = new TestObserver<>();
        String json = TestUtils.json("note_update_ok.json", this);
        this.mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody(json));

        this.noteDataRepository.updateNote(this.fakeNote).subscribe(testObserver);

        testObserver.awaitTerminalEvent();
        testObserver.assertComplete();
        testObserver.assertNoErrors();

    }

    @Test
    public void testUpdateNoteErrorResponse() throws IOException {

        TestObserver testObserver = new TestObserver<>();
        String json = TestUtils.json("note_update_error.json", this);
        this.mockWebServer.enqueue(new MockResponse().setResponseCode(404).setBody(json));

        this.noteDataRepository.updateNote(this.fakeNote).subscribe(testObserver);
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

        this.noteDataRepository.deleteNote(this.fakeNote.getObjectId()).subscribe(testObserver);

        RecordedRequest request = this.mockWebServer.takeRequest();

        Truth.assertThat(request.getPath()).isEqualTo(getFormattedUrl(this.fakeNote.getObjectId(), RestApi.URL_PATH_CLASSES_NOTE_OBJECT_ID));
        Truth.assertThat(request.getMethod()).isEqualTo(HttpMethod.DELETE);
        Truth.assertThat(request.getBody().readUtf8()).isEmpty();
    }

    @Test
    public void testDeleteNoteSuccessResponse() {
        NoteDiskDataStore mock = mock(NoteDiskDataStore.class);
        given(badgeDataStoreFactory.noteDiskDataStore()).willReturn(mock);
        TestObserver testObserver = new TestObserver<>();
        this.mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody("{}"));
        this.noteDataRepository.deleteNote(MOCK_NOTE_OBJECT_ID).subscribe(testObserver);
        testObserver.awaitTerminalEvent();
        testObserver.assertNoErrors();
        testObserver.assertComplete();
        verify(mock).delete(MOCK_NOTE_OBJECT_ID);
        verifyNoMoreInteractions(mock);
    }

    @Test
    public void testDeleteUserErrorResponse() {
        TestObserver testObserver = new TestObserver<>();

        this.mockWebServer.enqueue(new MockResponse().setResponseCode(401));
        this.noteDataRepository.deleteNote(MOCK_NOTE_OBJECT_ID).subscribe(testObserver);
        testObserver.awaitTerminalEvent();

        testObserver.assertValueCount(0);
        RestApiErrorException actual = (RestApiErrorException) testObserver.errors().get(0);
        Truth.assertThat(actual.getStatusCode()).isEqualTo(401);
    }

}
