package com.shop.ui.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.shop.R;
import com.shop.base.BaseActivity;
import com.shop.interfaces.login.RegisterConstract;
import com.shop.models.bean.VerifyBean;
import com.shop.persenter.login.RegisterPersenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity<RegisterConstract.Persenter> implements RegisterConstract.View {
    @BindView(R.id.edit_username)
    EditText editUsername;
    @BindView(R.id.edit_pw1)
    EditText editPw1;
    @BindView(R.id.edit_pw2)
    EditText editPw2;
    @BindView(R.id.edit_verify)
    EditText editVerify;
    @BindView(R.id.img_verify)
    ImageView imgVerify;
    @BindView(R.id.layout_list)
    LinearLayout layoutList;
    @BindView(R.id.btn_register)
    Button btnRegister;

    @Override
    protected int getLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        persenter.getVerify();
    }

    @Override
    protected RegisterConstract.Persenter createPersenter() {
        return new RegisterPersenter();
    }

    @Override
    public void getVerifyReturn(VerifyBean result) {
        updateVerify(result);
    }

    @OnClick({R.id.img_verify, R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_verify:
                persenter.getVerify();
                break;
            case R.id.btn_register:
                break;
        }
    }

    private void updateVerify(VerifyBean bean){
        Glide.with(this).load(bean.getData().getImg()).into(imgVerify);
    }
}
