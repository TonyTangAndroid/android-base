package com.tony.tang.note.domain.interactor.note;

import com.tony.tang.note.domain.entity.NoteData;
import com.tony.tang.note.domain.entity.NoteEntity;
import com.tony.tang.note.domain.executor.ThreadExecutor;
import com.tony.tang.note.domain.executor.UIThread;
import com.tony.tang.note.domain.interactor.SingleUseCase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;

public class GenerateNoteListUseCase extends SingleUseCase<Integer> {


    public static final int COUNT = 98;
    private CreateNoteUseCase createNoteUseCase;
    private List<Quote> quoteArrayList = new ArrayList<>();

    @Inject
    public GenerateNoteListUseCase(ThreadExecutor threadExecutor,
                                   UIThread postExecutionThread,
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
    protected Single<Integer> build() {
        return Observable.range(1, COUNT)
                .flatMapSingle(this::create)
                .toList()
                .map(List::size);
    }

    private Single<NoteEntity> create(@NonNull Integer index) {
        return createNoteUseCase.setParams(constructNote(index)).build();
    }

    private NoteData constructNote(int count) {
        int index = (count + new Random().nextInt(COUNT)) % quoteArrayList.size();
        Quote quote = quoteArrayList.get(index);
        return NoteData.builder()
                .title(count + " " + quote.title)
                .content(count + " " + quote.content)
                .status(count % 10 == 0 ? 1 : 0)
                .build();
    }

    class Quote {
        String title;
        String content;

        public Quote(String title, String content) {
            this.title = title;
            this.content = content;
        }
    }
}
