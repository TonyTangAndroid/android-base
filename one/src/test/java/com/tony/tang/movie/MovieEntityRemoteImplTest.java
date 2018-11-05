package com.tony.tang.movie;

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

public class MovieEntityRemoteImplTest {

    public static final String MOCK_SERVER = "/";
    private MovieEntityRemoteImpl movieRemote;
    private MockWebServer mockWebServer;
    private TestObserver<List<MovieEntity>> testObserver;

    @Before
    public void setUp() throws IOException {
        this.testObserver = new TestObserver<>();

        Gson gson = GsonHelper.build();

        this.mockWebServer = new MockWebServer();
        this.mockWebServer.start();

        this.movieRemote = new MovieEntityRemoteImpl(
                new Retrofit.Builder()
                        .baseUrl(mockWebServer.url(MOCK_SERVER))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build()
                        .create(RestApi.class)
        );

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
        Truth.assertThat(request.getPath()).startsWith(MOCK_SERVER + "search/movie");
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
        List<MovieEntity> actual = testObserver.values().get(0);
        Truth.assertThat(actual).isEqualTo(expected(json));
    }

    @Test
    public void handleInvalidApiKeyCorrectly() throws Exception {
        String json = TestUtils.json("401.json", this);
        this.mockWebServer.enqueue(new MockResponse().setResponseCode(401).setBody(json));
        this.movieRemote.list("").subscribe(testObserver);
        testObserver.awaitTerminalEvent();
        Truth.assertThat(testObserver.errors().size()).isEqualTo(1);
        Truth.assertThat(testObserver.errors().get(0)).isInstanceOf(RestApiErrorException.class);
        Truth.assertThat(((RestApiErrorException) testObserver.errors().get(0)).getCode()).isEqualTo(7);
        Truth.assertThat(testObserver.errors().get(0).getMessage()).isEqualTo("Invalid API key: You must be granted a valid key.");
    }

    @Test
    public void handleEmptyQueryCorrectly() throws Exception {
        String json = TestUtils.json("422.json", this);
        this.mockWebServer.enqueue(new MockResponse().setResponseCode(422).setBody(json));
        this.movieRemote.list("").subscribe(testObserver);
        testObserver.awaitTerminalEvent();
        Truth.assertThat(testObserver.errors().size()).isEqualTo(1);
        Truth.assertThat(testObserver.errors().get(0)).isInstanceOf(RestApiErrorException.class);
        Truth.assertThat(((RestApiErrorException) testObserver.errors().get(0)).getCode()).isEqualTo(422);
        Truth.assertThat(testObserver.errors().get(0).getMessage()).isEqualTo("[query must be provided]");
    }

    @Test
    public void handle500() throws IOException {
        String json = TestUtils.json("500.txt", this);
        this.mockWebServer.enqueue(new MockResponse().setResponseCode(500).setBody(json));
        this.movieRemote.list("").subscribe(testObserver);
        testObserver.awaitTerminalEvent();
        Truth.assertThat(testObserver.errors().size()).isEqualTo(1);
        Truth.assertThat(testObserver.errors().get(0)).isInstanceOf(RestApiErrorException.class);
        Truth.assertThat(((RestApiErrorException) testObserver.errors().get(0)).getCode()).isEqualTo(500);
        Truth.assertThat(testObserver.errors().get(0).getMessage()).isEqualTo("SERVER IS DOWN");
    }

    @Test
    public void handleRandomError() throws IOException {
        String json = TestUtils.json("403.txt", this);
        this.mockWebServer.enqueue(new MockResponse().setResponseCode(403).setBody(json));
        this.movieRemote.list("").subscribe(testObserver);
        testObserver.awaitTerminalEvent();
        Truth.assertThat(testObserver.errors().size()).isEqualTo(1);
        Truth.assertThat(testObserver.errors().get(0)).isInstanceOf(RestApiErrorException.class);
        Truth.assertThat(((RestApiErrorException) testObserver.errors().get(0)).getCode()).isEqualTo(403);
        Truth.assertThat(testObserver.errors().get(0).getMessage()).isEqualTo("PAGE NOT FOUND");
    }

    //PAGE NOT FOUND
    private List<MovieEntity> expected(String json) {
        return GsonHelper.build().fromJson(json, MovieEntityDto.class).getResults();
    }


}
