package com.shop.ui.sort;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shop.R;
import com.shop.base.BaseAdapter;
import com.shop.models.bean.SortBean;
import com.shop.models.bean.SortGoodsBean;

import java.util.List;

public class SortGoodsAdapter extends BaseAdapter {
    public SortGoodsAdapter(List data, Context context) {
        super(data, context);
    }

    @Override
    public int getLayout() {
        return R.layout.catalog_list_item;
    }

    @Override
    public void bindData(BaseViewHolder holder, Object o) {
        SortGoodsBean.DataBean.CurrentCategoryBean.SubCategoryListBean bean = (SortGoodsBean.DataBean.CurrentCategoryBean.SubCategoryListBean) o;
        ImageView img = (ImageView) holder.getView(R.id.img_icon);
        Glide.with(mContext).load(bean.getWap_banner_url()).into(img);
        TextView txtName = (TextView) holder.getView(R.id.txt_name);
        txtName.setText(bean.getName());
    }
}
