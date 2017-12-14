package com.sugar.grapecollege.searcher;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.qsmaxmin.qsbase.common.viewbind.annotation.Bind;
import com.qsmaxmin.qsbase.common.viewbind.annotation.OnClick;
import com.qsmaxmin.qsbase.mvp.QsABActivity;
import com.sugar.grapecollege.R;
import com.sugar.grapecollege.searcher.fragment.SearcherListFragment;


/**
 * @CreateBy qsmaxmin
 * @Date 2017/5/3 10:57
 * @Description 搜索管理类
 */

public class SearcherActivity extends QsABActivity implements TextWatcher, TextView.OnEditorActionListener {

    @Bind(R.id.et_input) EditText et_input;
    @Bind(R.id.iv_clean) View     iv_clean;

    @Override public int actionbarLayoutId() {
        return R.layout.actionbar_searcher;
    }

    @Override public void initData(Bundle savedInstanceState) {
        et_input.addTextChangedListener(this);
        et_input.setOnEditorActionListener(this);
        commitFragment(SearcherListFragment.getInstance(getIntent().getExtras() == null ? new Bundle() : getIntent().getExtras()));
    }

    @OnClick({R.id.tv_cancel, R.id.iv_clean}) public void onItemViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                activityFinish();
                break;
            case R.id.iv_clean:
                if (et_input != null) et_input.setText("");
                break;
        }
    }

    public void applySearch(String str) {
        setEditTextContent(str);
        SearcherListFragment searchFragment = getSearchFragment();
        if (searchFragment != null) searchFragment.executeSearch(str);
    }

    private void setEditTextContent(String string) {
        if (et_input != null) {
            et_input.setText(string);
            et_input.setSelection((TextUtils.isEmpty(string)) ? 0 : string.length());
        }
    }

    @Override public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override public void afterTextChanged(Editable s) {
        String inputStr = et_input.getText().toString().trim();
        if (TextUtils.isEmpty(inputStr)) {
            iv_clean.setVisibility(View.GONE);
        } else {
            iv_clean.setVisibility(View.VISIBLE);
        }
    }

    @Override public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
            applySearch(et_input.getText().toString().trim());
        }
        return false;
    }

    private SearcherListFragment getSearchFragment() {
        return (SearcherListFragment) getSupportFragmentManager().findFragmentByTag(SearcherListFragment.class.getSimpleName());
    }


    @Override public boolean isTransparentStatusBar() {
        return true;
    }
}
