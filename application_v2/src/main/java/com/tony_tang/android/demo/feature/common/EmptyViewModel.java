package com.tony_tang.android.demo.feature.common;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;
import com.tony_tang.android.demo.R;

import butterknife.BindView;

import static com.airbnb.epoxy.EpoxyAttribute.Option.DoNotHash;


@EpoxyModelClass(layout = R.layout.item_empty_view)
public abstract class EmptyViewModel extends EpoxyModelWithHolder<EmptyViewModel.EmptyViewItemViewHolder> {

    @EpoxyAttribute
    EmptyViewEntity emptyViewEntity;

    @EpoxyAttribute(DoNotHash)
    View.OnClickListener retryListener;

    @EpoxyAttribute(DoNotHash)
    View.OnClickListener bottomViewClickListener;

    public int getSpanSize(int totalSpanCount, int position, int itemCount) {
        return totalSpanCount;
    }

    @Override
    public void bind(EmptyViewItemViewHolder holder) {


        holder.pb_loading.setVisibility(emptyViewEntity.showLoading() ? View.VISIBLE : View.GONE);
        if (emptyViewEntity.showImage()) {
            holder.iv_empty_image.setVisibility(View.VISIBLE);
            holder.iv_empty_image.setImageResource(emptyViewEntity.imageDrawableRes());
        } else {
            holder.iv_empty_image.setVisibility(View.GONE);
        }


        if (emptyViewEntity.showRetry()) {
            holder.btClickRetry.setVisibility(View.VISIBLE);
            holder.btClickRetry.setOnClickListener(retryListener);

        } else {
            holder.btClickRetry.setVisibility(View.GONE);
        }


        if (emptyViewEntity.middleHint1() != null) {
            holder.tv_empty_hint_middle_1.setText(emptyViewEntity.middleHint1());
            holder.tv_empty_hint_middle_1.setVisibility(View.VISIBLE);
        } else {
            holder.tv_empty_hint_middle_1.setVisibility(View.GONE);
        }

        if (emptyViewEntity.middleHint2() != null) {
            holder.tv_empty_hint_middle_2.setText(emptyViewEntity.middleHint2());
            holder.tv_empty_hint_middle_2.setVisibility(View.VISIBLE);
        } else {
            holder.tv_empty_hint_middle_2.setVisibility(View.GONE);
        }
        if (emptyViewEntity.topHint() != null) {
            holder.tv_empty_hint_top.setText(emptyViewEntity.topHint());
            holder.tv_empty_hint_top.setVisibility(View.VISIBLE);
        } else {
            holder.tv_empty_hint_top.setVisibility(View.GONE);
        }

        if (emptyViewEntity.bottomHint() != null) {
            holder.tv_empty_hint_bottom.setText(emptyViewEntity.bottomHint());
            holder.tv_empty_hint_bottom.setVisibility(View.VISIBLE);
            holder.tv_empty_hint_bottom.setOnClickListener(bottomViewClickListener);
        } else {
            holder.tv_empty_hint_bottom.setVisibility(View.GONE);
        }


    }


    @Override
    public void unbind(EmptyViewItemViewHolder holder) {
        holder.btClickRetry.setOnClickListener(null);
        holder.tv_empty_hint_bottom.setOnClickListener(null);
    }

    static class EmptyViewItemViewHolder extends BaseEpoxyHolder {

        @BindView(R.id.pb_loading)
        ProgressBar pb_loading;
        @BindView(R.id.iv_empty_image)
        ImageView iv_empty_image;
        @BindView(R.id.tv_empty_hint_middle_1)
        TextView tv_empty_hint_middle_1;
        @BindView(R.id.tv_empty_hint_middle_2)
        TextView tv_empty_hint_middle_2;
        @BindView(R.id.tv_empty_hint_top)
        TextView tv_empty_hint_top;
        @BindView(R.id.tv_empty_hint_bottom)
        TextView tv_empty_hint_bottom;
        @BindView(R.id.btn_retry)
        Button btClickRetry;

    }
}
