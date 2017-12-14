package com.sugar.grapecollege.searcher.presenter;

import android.os.SystemClock;
import android.text.TextUtils;

import com.qsmaxmin.qsbase.common.aspect.ThreadPoint;
import com.qsmaxmin.qsbase.common.aspect.ThreadType;
import com.qsmaxmin.qsbase.common.log.L;
import com.qsmaxmin.qsbase.common.utils.QsHelper;
import com.sugar.grapecollege.common.http.SearcherHttp;
import com.sugar.grapecollege.common.presenter.GrapeCollegePresenter;
import com.sugar.grapecollege.product.model.ModelProductInfo;
import com.sugar.grapecollege.product.model.ModelProductList;
import com.sugar.grapecollege.searcher.fragment.SearcherListFragment;
import com.sugar.grapecollege.searcher.model.ModelSearchReq;

import java.util.ArrayList;


/**
 * @CreateBy qsmaxmin
 * @Date 2017/5/9 15:50
 * @Description
 */

public class SearcherListPresenter extends GrapeCollegePresenter<SearcherListFragment> {

    private int page;

    @ThreadPoint(ThreadType.HTTP) public void requestSearcherData(String keyWord, boolean isLoadingMore) {
        if (!TextUtils.isEmpty(keyWord)) {
            SearcherHttp searcherHttp = QsHelper.getInstance().getHttpHelper().create(SearcherHttp.class);
            ModelSearchReq req = new ModelSearchReq();
            req.searchMap.searchStr = keyWord;
            if (isLoadingMore) {
                if (page < 1) return;
                req.pageNumber = page;
//                ModelProductList modelSearch = searcherHttp.requestSearchData(req);
                ModelProductList modelSearch = getTestData();
                if (isSuccess(modelSearch, true)) {
                    page++;
                    getView().addData(modelSearch.list);
                    paging(modelSearch);
                }
            } else {
                page = 0;
                req.pageNumber = page;
//                ModelProductList modelSearch = searcherHttp.requestSearchData(req);
                ModelProductList modelSearch = getTestData();
                if (isSuccess(modelSearch, true)) {
                    page = 1;
                    getView().setData(modelSearch.list);
                    paging(modelSearch);
                }
            }
        } else {
            L.e(initTag(), "search key word is empty!!");
        }
    }

    private ModelProductList getTestData() {
        ModelProductList modelProductList = new ModelProductList();
        modelProductList.code = 0;
        modelProductList.list = new ArrayList<>();
        modelProductList.isLastPage = page >= 5;
        for (int i = 0; i < 20; i++) {
            ModelProductInfo.ProductInfo productInfo = new ModelProductInfo.ProductInfo();
            productInfo.productId = (i * page) + "";
            productInfo.productName = "第" + page + "页  index=" + i;
            modelProductList.list.add(productInfo);
        }
        SystemClock.sleep(1000);
        return modelProductList;
    }
}
