package com.sugar.grapecollege.searcher.fragment;

import android.os.Bundle;

import com.qsmaxmin.qsbase.mvp.fragment.QsFragment;
import com.qsmaxmin.qsbase.mvp.presenter.Presenter;
import com.sugar.grapecollege.R;
import com.sugar.grapecollege.searcher.model.SearcherConstants;
import com.sugar.grapecollege.searcher.presenter.SearcherListPresenter;


/**
 * @CreateBy qsmaxmin
 * @Date 2017/5/3 10:59
 * @Description
 */
@Presenter(SearcherListPresenter.class)
public class SearcherListFragment extends QsFragment<SearcherListPresenter> {

    private String keyWord;

    @Override public int layoutId() {
        return R.layout.fragment_main;
    }

    public static SearcherListFragment getInstance(Bundle keyWord) {
        SearcherListFragment fragment = new SearcherListFragment();
        if (keyWord != null) fragment.setArguments(keyWord);
        return fragment;
    }

    @Override public void initData(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            keyWord = bundle.getString(SearcherConstants.SEARCH_KEY_WORD);
            getPresenter().requestSearcherData(keyWord, false);
        }
    }

    public void executeSearch(String str) {
        this.keyWord = str;
        getPresenter().requestSearcherData(keyWord, false);
    }
}
