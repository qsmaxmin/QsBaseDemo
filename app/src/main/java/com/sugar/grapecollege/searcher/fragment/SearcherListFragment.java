package com.sugar.grapecollege.searcher.fragment;

import android.os.Bundle;

import com.qsmaxmin.qsbase.mvp.adapter.QsListAdapterItem;
import com.sugar.grapecollege.common.fragment.BasePullListFragment;
import com.sugar.grapecollege.product.model.ModelProductInfo;
import com.sugar.grapecollege.searcher.adapter.SearcherAdapterItem;
import com.sugar.grapecollege.searcher.model.SearcherConstants;
import com.sugar.grapecollege.searcher.presenter.SearcherListPresenter;


/**
 * @CreateBy qsmaxmin
 * @Date 2017/5/3 10:59
 * @Description
 */
public class SearcherListFragment extends BasePullListFragment<SearcherListPresenter, ModelProductInfo.ProductInfo> {

    private String keyWord;

    @Override public void onRefresh() {
        getPresenter().requestSearcherData(keyWord, false);
    }

    @Override public void onLoad() {
        getPresenter().requestSearcherData(keyWord, true);
    }

    @Override public QsListAdapterItem<ModelProductInfo.ProductInfo> getListAdapterItem(int i) {
        return new SearcherAdapterItem();
    }

    public static SearcherListFragment getInstance(Bundle keyWord) {
        SearcherListFragment fragment = new SearcherListFragment();
        fragment.setArguments(keyWord == null ? new Bundle() : keyWord);
        return fragment;
    }

    @Override public void initData(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        keyWord = bundle.getString(SearcherConstants.SEARCH_KEY_WORD, "你好");
        getPresenter().requestSearcherData(keyWord, false);
    }

    public void executeSearch(String str) {
        this.keyWord = str;
        getPresenter().requestSearcherData(keyWord, false);
    }

    @Override public boolean isOpenViewState() {
        return false;
    }
}
