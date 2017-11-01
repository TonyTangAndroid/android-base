package com.tony_tang.android.demo.feature.note_list;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;
import com.jordifierro.androidbase.domain.entity.NoteEntity;
import com.tony_tang.android.demo.R;
import com.tony_tang.android.demo.feature.common.BaseEpoxyHolder;
import com.tony_tang.android.demo.feature.common.SpanType;

import butterknife.BindView;

import static com.airbnb.epoxy.EpoxyAttribute.Option.DoNotHash;


@EpoxyModelClass(layout = R.layout.item_note)
public abstract class NoteEntityModel extends EpoxyModelWithHolder<NoteEntityModel.NoteEntityViewHolder> {

    //epoxy, auto-value, auto-value-gson
    //annotation.
    //Command+F9
    @EpoxyAttribute
    NoteEntity item;
    @EpoxyAttribute
    Context context;

    @EpoxyAttribute(DoNotHash)
    View.OnClickListener itemClickListener;

    @Override
    public void bind(NoteEntityViewHolder holder) {
        holder.llNoteItem.setOnClickListener(itemClickListener);
        holder.tvTitle.setText(item.getTitle());
        holder.tvContent.setText(context.getString(R.string.edit_text_content));

    }

    public int getSpanSize(int totalSpanCount, int position, int itemCount) {
        return SpanType.QUARTER;
    }

    @Override
    public void bind(NoteEntityViewHolder holder, EpoxyModel<?> previouslyBoundModel) {
        bind(holder);
    }


    @Override
    public void unbind(NoteEntityViewHolder holder) {
        // Don't leak the click listener when this view goes back in the view pool
        holder.tvTitle.setOnClickListener(null);
    }

    static class NoteEntityViewHolder extends BaseEpoxyHolder {


        @BindView(R.id.ll_note_item)
        View llNoteItem;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_content)
        TextView tvContent;


    }
}
