package com.jordifierro.androidbase.data.repository;

import com.jordifierro.androidbase.data.net.RestApi;
import com.jordifierro.androidbase.domain.entity.CreatedWrapper;
import com.jordifierro.androidbase.domain.entity.EmptyWrapper;
import com.jordifierro.androidbase.domain.entity.NoteEntitiesWrapper;
import com.jordifierro.androidbase.domain.entity.NoteEntity;
import com.jordifierro.androidbase.domain.entity.UpdatedWrapper;
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
	public Observable<CreatedWrapper> createNote(UserEntity user, final NoteEntity note) {
		return this.restApi.createNote(user.getSessionToken(), note).map(new Func1<Response<CreatedWrapper>, CreatedWrapper>() {
			@Override
			public CreatedWrapper call(Response<CreatedWrapper> createdWrapperResponse) {
				handleResponseError(createdWrapperResponse);
				return createdWrapperResponse.body();
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
				.map(new Func1<Response<NoteEntitiesWrapper>, List<NoteEntity>>() {
					@Override
					public List<NoteEntity> call(Response<NoteEntitiesWrapper> listResponse) {
						handleResponseError(listResponse);
						return listResponse.body().getResults();
					}
				});
	}

	@Override
	public Observable<UpdatedWrapper> updateNote(UserEntity user, NoteEntity note) {
		return this.restApi.updateNote(user.getSessionToken(), note.getObjectId(), note)
				.map(new Func1<Response<UpdatedWrapper>, UpdatedWrapper>() {
					@Override
					public UpdatedWrapper call(Response<UpdatedWrapper> noteEntityResponse) {
						handleResponseError(noteEntityResponse);
						return noteEntityResponse.body();
					}
				});
	}

	@Override
	public Observable<EmptyWrapper> deleteNote(UserEntity user, String noteObjectId) {
		return this.restApi.deleteNote(user.getSessionToken(), noteObjectId)
				.map(new Func1<Response<EmptyWrapper>, EmptyWrapper>() {
					@Override
					public EmptyWrapper call(Response<EmptyWrapper> response) {
						handleResponseError(response);
						return response.body();
					}
				});
	}
}
