package com.jordifierro.androidbase.data.repository;

import com.jordifierro.androidbase.data.net.RestApi;
import com.jordifierro.androidbase.data.net.wrapper.CreatedWrapper;
import com.jordifierro.androidbase.data.net.wrapper.NotesListWrapper;
import com.jordifierro.androidbase.domain.entity.NoteEntity;
import com.jordifierro.androidbase.domain.entity.UserEntity;
import com.jordifierro.androidbase.domain.repository.NoteRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Response;
import rx.Observable;
import rx.functions.Func1;

@Singleton
public class NoteDataRepository extends RestApiRepository implements NoteRepository {

	private final RestApi restApi;

	@Inject
	public NoteDataRepository(RestApi restApi) {
		this.restApi = restApi;
	}

	@Override
	public Observable<NoteEntity> createNote(UserEntity user, final NoteEntity note) {
		return this.restApi.createNote(user.getSessionToken(), note)
				.flatMap(new Func1<Response<CreatedWrapper>,  Observable<NoteEntity>>() {
					@Override
					public  Observable<NoteEntity> call(Response<CreatedWrapper> noteEntityResponse) {
						handleResponseError(noteEntityResponse);
						return getNote(user, noteEntityResponse.body().getObjectId());
					}
				});
	}

	@Override
	public Observable<NoteEntity> getNote(UserEntity user, String noteObjectId) {
		return this.restApi.getNote(user.getSessionToken(), noteObjectId)
				.map(new Func1<Response<NoteEntity>, NoteEntity>() {
					@Override
					public NoteEntity call(Response<NoteEntity> noteEntityResponse) {
						handleResponseError(noteEntityResponse);
						return noteEntityResponse.body();
					}
				});
	}

	@Override
	public Observable<List<NoteEntity>> getNotes(UserEntity user) {
		return this.restApi.getNotes(user.getSessionToken())
				.map(new Func1<Response<NotesListWrapper>, List<NoteEntity>>() {
					@Override
					public List<NoteEntity> call(Response<NotesListWrapper> listResponse) {
						handleResponseError(listResponse);
						return listResponse.body().getResults();
					}
				});
	}

	@Override
	public Observable<NoteEntity> updateNote(UserEntity user, NoteEntity note) {
		return this.restApi.updateNote(user.getSessionToken(), note.getObjectId(), note)
				.map(new Func1<Response<NoteEntity>, NoteEntity>() {
					@Override
					public NoteEntity call(Response<NoteEntity> noteEntityResponse) {
						handleResponseError(noteEntityResponse);
						return noteEntityResponse.body();
					}
				});
	}

	@Override
	public Observable<Void> deleteNote(UserEntity user, String noteObjectId) {
		return this.restApi.deleteNote(user.getSessionToken(), noteObjectId)
				.map(new Func1<Response, Void>() {
					@Override
					public Void call(Response response) {
						handleResponseError(response);
						return null;
					}
				});
	}
}
