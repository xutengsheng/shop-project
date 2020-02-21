package com.shop.ui.sort;

import android.content.Intent;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.shop.R;
import com.shop.base.BaseAdapter;
import com.shop.base.BaseFragment;
import com.shop.interfaces.sort.SortConstract;
import com.shop.models.bean.SortBean;
import com.shop.models.bean.SortDetailGoodsBean;
import com.shop.models.bean.SortGoodsBean;
import com.shop.persenter.sort.SortPersenter;
import com.shop.ui.sort.activity.SortDetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.adapter.TabAdapter;
import q.rorbin.verticaltablayout.widget.ITabView;
import q.rorbin.verticaltablayout.widget.QTabView;
import q.rorbin.verticaltablayout.widget.TabView;

public class SortFragment extends BaseFragment<SortConstract.Persenter> implements SortConstract.View,
        VerticalTabLayout.OnTabSelectedListener, BaseAdapter.ItemClickHandler {


    @BindView(R.id.verticalTab)
    VerticalTabLayout verticalTab;
    @BindView(R.id.layout_left)
    ConstraintLayout layoutLeft;
    @BindView(R.id.img_bg)
    ImageView imgBg;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    List<SortBean.DataBean.CategoryListBean> verticalList;

    List<String> titles;

    List<SortGoodsBean.DataBean.CurrentCategoryBean.SubCategoryListBean> goodsList;
    SortGoodsAdapter sortGoodsAdapter;



    @Override
    protected int getLayout() {
        return R.layout.fragment_sort;
    }

    @Override
    protected void initView() {
        titles = new ArrayList<>();
        verticalTab.addOnTabSelectedListener(this);
        goodsList = new ArrayList<>();
        sortGoodsAdapter = new SortGoodsAdapter(goodsList,context);
        recyclerView.setLayoutManager(new GridLayoutManager(context,3));
        recyclerView.setAdapter(sortGoodsAdapter);
        //绑定接口回调
        sortGoodsAdapter.setOnItemClickHandler(this);

    }

    @Override
    protected void initData() {
        persenter.getSort();
    }

    @Override
    protected SortConstract.Persenter createPersenter() {
        return new SortPersenter();
    }

    //创建竖导航的tabadapter
    TabAdapter tabAdapter = new TabAdapter() {
        @Override
        public int getCount() {
            return titles.size();
        }

        @Override
        public ITabView.TabBadge getBadge(int position) {
            return null;
        }

        @Override
        public ITabView.TabIcon getIcon(int position) {
            return null;
        }

        @Override
        public ITabView.TabTitle getTitle(int position) {
            QTabView.TabTitle title = new QTabView.TabTitle.Builder()
                    .setContent(titles.get(position))//设置数据   也有设置字体颜色的方法
                    .build();
            return title;
        }

        @Override
        public int getBackground(int position) {
            return Color.parseColor("#D81B60");
        }
    };

    @Override
    public void getSortReturn(SortBean result) {
        verticalList = result.getData().getCategoryList();
        titles.clear();
        //筛选竖导航的标题数据
        for(SortBean.DataBean.CategoryListBean item : result.getData().getCategoryList()) {
            titles.add(item.getName());
        }
        verticalTab.setTabAdapter(tabAdapter);
        updateInfo(result.getData().getCurrentCategory().getBanner_url(),
                result.getData().getCurrentCategory().getFront_desc(),
                result.getData().getCurrentCategory().getName());
        List<SortGoodsBean.DataBean.CurrentCategoryBean.SubCategoryListBean> list = new ArrayList<>();
        for(SortBean.DataBean.CurrentCategoryBean.SubCategoryListBean item:result.getData().getCurrentCategory().getSubCategoryList()){
            SortGoodsBean.DataBean.CurrentCategoryBean.SubCategoryListBean object = new SortGoodsBean.DataBean.CurrentCategoryBean.SubCategoryListBean();
            object.setName(item.getName());
            object.setFront_desc(item.getFront_desc());
            object.setId(item.getId());
            object.setIcon_url(item.getIcon_url());
            object.setWap_banner_url(item.getWap_banner_url());
            list.add(object);
        }
        sortGoodsAdapter.updata(list);
    }

    private void updateInfo(String imgUrl,String name,String title){
        Glide.with(context).load(imgUrl).into(imgBg);
        txtName.setText(name);
        txtTitle.setText(title);
    }

    @Override
    public void getCurrentSortDataReturn(SortGoodsBean result) {
        updateInfo(result.getData().getCurrentCategory().getBanner_url(),
                result.getData().getCurrentCategory().getFront_desc(),
                result.getData().getCurrentCategory().getName());
        List<SortGoodsBean.DataBean.CurrentCategoryBean.SubCategoryListBean> list = new ArrayList<>();
        list.addAll(result.getData().getCurrentCategory().getSubCategoryList());
        sortGoodsAdapter.updata(list);
    }

    //点击左边的竖导航切换分类数据
    @Override
    public void onTabSelected(TabView tab, int position) {
        if(position < verticalList.size()){
            int id = verticalList.get(position).getId();
            persenter.getCurrentSortData(id);
        }
    }

    @Override
    public void onTabReselected(TabView tab, int position) {

    }

    //分类商品列表的item点击回调
    @Override
    public void itemClick(int position, BaseAdapter.BaseViewHolder holder) {
        int id = goodsList.get(position).getId();
        //打开商品的详情页
        Intent intent = new Intent(context, SortDetailActivity.class);
        intent.putExtra("sortId",id);
        startActivity(intent);
    }
}