package com.shop.persenter.test;

import com.shop.base.BasePersenter;
import com.shop.common.CommonSubscriber;
import com.shop.interfaces.test.TestConstract;
import com.shop.models.HttpManager;
import com.shop.models.bean.ChaptersBean;
import com.shop.utils.RxUtils;

public class TestPersenter extends BasePersenter<TestConstract.View> implements TestConstract.Persenter {

    @Override
    public void getChapters() {
        addSubscribe(HttpManager.getInstance().getWanApi().getChapters()
        .compose(RxUtils.<ChaptersBean>rxScheduler())
        .subscribeWith(new CommonSubscriber<ChaptersBean>(mView){

            @Override
            public void onNext(ChaptersBean chaptersBean) {
                mView.getChaptersReturn(chaptersBean);
            }
        }));
    }
}
