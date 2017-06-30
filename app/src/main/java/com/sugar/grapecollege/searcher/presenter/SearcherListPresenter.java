package com.sugar.grapecollege.searcher.presenter;

import android.text.TextUtils;

import com.qsmaxmin.qsbase.common.aspect.ThreadPoint;
import com.qsmaxmin.qsbase.common.log.L;
import com.qsmaxmin.qsbase.common.utils.QsHelper;
import com.sugar.grapecollege.common.http.SearcherHttp;
import com.sugar.grapecollege.common.presenter.GrapeCollegePresenter;
import com.sugar.grapecollege.product.model.ModelProductList;
import com.sugar.grapecollege.searcher.fragment.SearcherListFragment;
import com.sugar.grapecollege.searcher.model.ModelSearchReq;


/**
 * @CreateBy qsmaxmin
 * @Date 2017/5/9 15:50
 * @Description
 */

public class SearcherListPresenter extends GrapeCollegePresenter<SearcherListFragment> {

    private int page;

    @ThreadPoint public void requestSearcherData(String keyWord, boolean isLoadingMore) {
        if (!TextUtils.isEmpty(keyWord)) {
            SearcherHttp searcherHttp = QsHelper.getInstance().getHttpHelper().create(SearcherHttp.class);
            ModelSearchReq req = new ModelSearchReq();
            req.searchMap.searchStr = keyWord;
            if (isLoadingMore) {
                if (page < 1) return;
                req.pageNumber = page;
                ModelProductList modelSearch = searcherHttp.requestSearchData(req);
                if (isSuccess(modelSearch, true)) {
                    page++;
//                    getView().addData(ModelTransformUtils.transform(modelSearch.list, 2));
                    paging(modelSearch);
                }
            } else {
                page = 0;
                req.pageNumber = page;
                ModelProductList modelSearch = searcherHttp.requestSearchData(req);
                if (isSuccess(modelSearch, true)) {
                    page = 1;
//                    getView().setData(ModelTransformUtils.transform(modelSearch.list, 2));
                    paging(modelSearch);
                }
            }
        } else {
            L.e(getTag(), "search key word is empty!!");
        }
    }
}
