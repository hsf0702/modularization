package com.credithc.hhr.module_home.presenter;

import com.credithc.hhr.module_home.contract.MainContract;
import com.credithc.hhr.library_common.bean.CardListBean;
import com.credithc.hhr.module_home.model.MainModel;
import com.woaiqw.base.mvp.BasePresenter;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by haoran on 2018/6/28.
 */

public class MainPresenter extends BasePresenter<MainContract.IMainView, MainContract.IMainModel> implements MainContract.IMainPresenter {

    @Override
    public MainContract.IMainModel bindModel() {
        return (MainContract.IMainModel) MainModel.newInstance();
    }

    @Override
    public void getCardList() {
        checkViewAttached();
        mView.showLoading();
        mDisposable.add(mModel.getCardList().subscribe(new Consumer<List<CardListBean.CardBean>>() {
            @Override
            public void accept(List<CardListBean.CardBean> cardBeanList) throws Exception {
                mView.hideLoading();
                if (cardBeanList == null || cardBeanList.size() == 0) {
                    mView.showEmptyDataView();
                } else {
                    mView.showCardList(cardBeanList);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                mView.onError(throwable.getMessage());
            }
        }));
    }
}
