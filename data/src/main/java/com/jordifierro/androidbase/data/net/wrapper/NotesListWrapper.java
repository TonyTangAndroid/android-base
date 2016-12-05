package com.jordifierro.androidbase.data.net.wrapper;

import com.jordifierro.androidbase.domain.entity.NoteEntity;

import java.util.List;

public class NotesListWrapper {


	private List<NoteEntity> results;

	public List<NoteEntity> getResults() {
		return results;
	}

	public void setResults(List<NoteEntity> results) {
		this.results = results;
	}
}
