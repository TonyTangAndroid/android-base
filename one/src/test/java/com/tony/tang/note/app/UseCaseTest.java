package com.tony.tang.note.app;

import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.TestScheduler;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;

public class UseCaseTest {

    private TestObserver<Integer> testObserver;
    private FakeUseCase fakeUseCase;

    @Mock
    private UIThread mockUIThread;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.testObserver = new TestObserver<>();
        this.fakeUseCase = new FakeUseCase(new DefaultThreadExecutor(), mockUIThread);
    }

    @Test
    public void testUseCaseExecutionResult() {
        TestScheduler testScheduler = new TestScheduler();
        given(this.mockUIThread.getScheduler()).willReturn(testScheduler);

        this.fakeUseCase.execute(testObserver);
        testScheduler.triggerActions();

        this.testObserver.assertNoErrors();
        this.testObserver.assertResult(1, 2, 3);
    }

    @Test
    public void testUseCaseUnsubscription() {
        TestScheduler testScheduler = new TestScheduler();
        given(this.mockUIThread.getScheduler()).willReturn(testScheduler);

        this.fakeUseCase.execute(testObserver);
        assertThat(this.fakeUseCase.isUnsubscribed(), is(false));

        this.fakeUseCase.unsubscribe();
        assertThat(this.fakeUseCase.isUnsubscribed(), is(true));
    }

    private static class FakeUseCase extends UseCase<Integer> {

        protected FakeUseCase(ThreadExecutor threadExecutor,
                              UIThread UIThread) {
            super(threadExecutor, UIThread);
        }

        @Override
        protected Observable<Integer> build() {
            return Observable.just(1, 2, 3);
        }

    }

    private static class DefaultThreadExecutor implements ThreadExecutor {

        @Override
        public void execute(@NotNull Runnable command) {
            command.run();
        }
    }


}
