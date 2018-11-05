package com.tony.tang.note.app;

import com.google.common.truth.Truth;
import com.google.gson.Gson;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import io.reactivex.observers.TestObserver;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieRemoteRepositoryTest {

    public static final String MOCK_SERVER = "/";
    private NoteRemoteImpl movieRemote;
    private MockWebServer mockWebServer;
    private TestObserver<List<NoteEntity>> testObserver;

    @Before
    public void setUp() throws IOException {
        this.testObserver = new TestObserver<>();

        Gson gson = new Gson();

        this.mockWebServer = new MockWebServer();
        this.mockWebServer.start();

        this.movieRemote = new NoteRemoteImpl(
                new Retrofit.Builder()
                        .baseUrl(mockWebServer.url(MOCK_SERVER))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build()
                        .create(RestApi.class)
        );

    }

    String constructUrl(String path) {
        return MOCK_SERVER + path;
    }


    @After
    public void tearDown() throws Exception {
        this.mockWebServer.shutdown();
    }

    @Test
    public void sendToCorrectAPIEndPoint() throws Exception {
        this.mockWebServer.enqueue(new MockResponse());
        this.movieRemote.list("test key word").subscribe(testObserver);
        RecordedRequest request = this.mockWebServer.takeRequest();
        Truth.assertThat(request.getPath()).startsWith(constructUrl("search/movie"));
        Truth.assertThat(request.getMethod()).isEqualTo(HttpMethod.GET);
        Truth.assertThat(request.getRequestUrl().queryParameter("query")).isEqualTo("test key word");

    }

    @Test
    public void getCorrectResult() throws Exception {

        String json = TestUtils.json("fargo_remote.json", this);
        this.mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody(json));
        this.movieRemote.list("").subscribe(testObserver);
        testObserver.awaitTerminalEvent();
        testObserver.assertNoErrors();
        Truth.assertThat(testObserver.values().size()).isEqualTo(1);
        List<NoteEntity> actual = testObserver.values().get(0);
        Truth.assertThat(actual).isEqualTo(expected(json));
    }

    private List<NoteEntity> expected(String json) {
        return new Gson().fromJson(json, MoviesWrapper.class).getResults();
    }


}
