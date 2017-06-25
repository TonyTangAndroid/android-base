package com.tony_tang.android.demo.feature.note_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jordifierro.androidbase.domain.entity.NoteEntity;
import com.tony_tang.android.demo.R;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class NoteListAdapter extends BaseAdapter {

    private Context context;
    private OnItemClickListener listener;
    private List<NoteEntity> notes;

    @Inject
    public NoteListAdapter(Context context) {
        this.context = context;
        this.notes = Collections.emptyList();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setNotes(List<NoteEntity> notes) {
        this.notes = notes;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return notes.size();
    }

    @Override
    public Object getItem(int arg0) {
        return notes.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return notes.get(arg0).getObjectId().hashCode();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_note, parent, false);
        }

        ((TextView) view.findViewById(R.id.title)).setText(notes.get(position).getTitle());
        view.setTag(position);

        view.setOnClickListener(arg0 -> listener.onNoteItemClicked(notes.get((int) arg0.getTag())));

        return view;
    }

    public interface OnItemClickListener {
        void onNoteItemClicked(NoteEntity note);
    }

}
