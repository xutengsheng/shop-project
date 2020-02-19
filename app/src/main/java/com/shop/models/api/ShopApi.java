package com.shop.models.api;

import com.shop.models.bean.BrandBean;
import com.shop.models.bean.BrandGoodsBean;
import com.shop.models.bean.IndexBean;
import com.shop.models.bean.SortBean;
import com.shop.models.bean.SortGoodsBean;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ShopApi {

    @GET("index")
    Flowable<IndexBean> getIndexData();

    //品牌直供的详情页数据接口
    @GET("brand/detail")
    Flowable<BrandBean> getBrandInfo(@Query("id") String id);
    //品牌直供详情的商品列表数据接口
    @GET("goods/list")
    Flowable<BrandGoodsBean> getBrandGoods(@Query("brandId") String brandId,@Query("page") int page,@Query("size") int size);

    //获取分类的接口
    @GET("catalog/index")
    Flowable<SortBean> getSort();

    //获取分类页面的商品数据
    @GET("catalog/current")
    Flowable<SortGoodsBean> getCurrentSortData(@Query("id") int id);


}
