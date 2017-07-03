package com.tony_tang.android.demo.feature.common;

import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;
import com.tony_tang.android.demo.R;

import butterknife.BindView;

import static com.airbnb.epoxy.EpoxyAttribute.Option.DoNotHash;


@EpoxyModelClass(layout = R.layout.item_footer_view)
public abstract class FooterModel extends EpoxyModelWithHolder<FooterModel.FooterViewHolder> {

    @EpoxyAttribute
    FooterViewEntity footerViewEntity;

    @EpoxyAttribute(DoNotHash)
    View.OnClickListener clickListener;

    @Override
    public void bind(FooterViewHolder holder) {
        if (footerViewEntity.showFooterView()) {

            holder.cv_footer_view.setVisibility(View.VISIBLE);
            holder.cv_footer_view.setOnClickListener(clickListener);
            if (footerViewEntity.showLoading()) {
                holder.pb_footer_view_loading.setVisibility(View.VISIBLE);
                holder.tv_footer_view_loading_hint.setVisibility(View.GONE);
            } else {
                holder.pb_footer_view_loading.setVisibility(View.GONE);
                holder.tv_footer_view_loading_hint.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(footerViewEntity.footerHint())) {
                    holder.tv_footer_view_loading_hint.setText(footerViewEntity.footerHint());
                }
            }
        } else {
            holder.cv_footer_view.setVisibility(View.GONE);
        }
    }

    public int getSpanSize(int totalSpanCount, int position, int itemCount) {
        return totalSpanCount;
    }

    @Override
    public void unbind(FooterViewHolder holder) {
        holder.cv_footer_view.setOnClickListener(null);
    }

    static class FooterViewHolder extends BaseEpoxyHolder {

        @BindView(R.id.cv_footer_view)
        CardView cv_footer_view;
        @BindView(R.id.tv_footer_view_loading_hint)
        TextView tv_footer_view_loading_hint;
        @BindView(R.id.pb_footer_view_loading)
        ProgressBar pb_footer_view_loading;


    }
}
