package com.jordifierro.androidbase.domain.interactor.note;

import com.jordifierro.androidbase.domain.entity.NoteEntity;
import com.jordifierro.androidbase.domain.executor.PostExecutionThread;
import com.jordifierro.androidbase.domain.executor.ThreadExecutor;
import com.jordifierro.androidbase.domain.interactor.UseCase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

public class GenerateNoteListUseCase extends UseCase<Integer> {


    public static final int COUNT = 1000;
    private CreateNoteUseCase createNoteUseCase;
    private List<Quote> quoteArrayList = new ArrayList<>();

    @Inject
    public GenerateNoteListUseCase(ThreadExecutor threadExecutor,
                                   PostExecutionThread postExecutionThread,
                                   CreateNoteUseCase createNoteUseCase) {
        super(threadExecutor, postExecutionThread);
        this.createNoteUseCase = createNoteUseCase;
        quoteArrayList.add(new Quote("Christopher Moltisanti", " It's an idea, I don't know. Who knows where it fucking came from? Isaac Newton invented gravity because some asshole hit him with an apple."));
        quoteArrayList.add(new Quote("Anthony 'Tony' Soprano Sr.", "Even a broken clock is right twice a day."));
        quoteArrayList.add(new Quote("Christopher", "Fear knocked on the door. Faith answered. There was no one there."));
        quoteArrayList.add(new Quote("Anthony 'Tony' Soprano Sr.", "The belt was his favorite child development tool."));
        quoteArrayList.add(new Quote("Eugene Pontecorvo", "The only thing I ever found in the street was my first wife."));
        quoteArrayList.add(new Quote("Anthony 'Tony' Soprano Sr.", "Cunnilingus and psychiatry brought us to this."));
        quoteArrayList.add(new Quote("Anthony 'Tony' Soprano Sr.", "You got any idea what my life would be worth if certain people found out I checked into a laughing academy?"));
        quoteArrayList.add(new Quote("Silvio Dante", "You're only as good as your last envelope."));
        quoteArrayList.add(new Quote("Silvio Dante", "My daughter got off on this feminist rant. She told me it's demeaning for a girl to be working at the Bing. The fact that these girls make $1500 a week has no bearing with my principessa.\n"));
    }

    @Override
    protected Observable<Integer> buildUseCaseObservable() {

        return Observable.range(1, COUNT).flatMap(new Function<Integer, ObservableSource<NoteEntity>>() {
            @Override
            public ObservableSource<NoteEntity> apply(@NonNull Integer index) throws Exception {
                return createNoteUseCase.setParams(constructNote(index)).buildUseCaseObservable();
            }
        }).toList().toObservable().map(new Function<List<NoteEntity>, Integer>() {
            @Override
            public Integer apply(@NonNull List<NoteEntity> noteEntities) throws Exception {
                return noteEntities.size();
            }
        });
    }

    private NoteEntity constructNote(int index) {
        Quote quote = quoteArrayList.get((index + new Random().nextInt(COUNT)) % quoteArrayList.size());
        return new NoteEntity(index + " " + quote.title, index + " " + quote.content);
    }

    static class Quote {
        String title;
        String content;

        public Quote(String title, String content) {
            this.title = title;
            this.content = content;
        }
    }
}
