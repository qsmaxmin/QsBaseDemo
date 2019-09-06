package com.sugar.grapecollege.test.presenter;

import android.os.SystemClock;

import com.qsmaxmin.qsbase.common.aspect.ThreadPoint;
import com.qsmaxmin.qsbase.common.aspect.ThreadType;
import com.sugar.grapecollege.common.http.BaseModelReq;
import com.sugar.grapecollege.common.http.HomeHttp;
import com.sugar.grapecollege.common.base.presenter.GrapeCollegePresenter;
import com.sugar.grapecollege.test.fragment.TestPullRecyclerFragment;
import com.sugar.grapecollege.test.model.TestModel;

import java.util.ArrayList;

/**
 * @CreateBy qsmaxmin
 * @Date 2017/7/4 18:15
 * @Description
 */

public class TestPullRecyclerPresenter extends GrapeCollegePresenter<TestPullRecyclerFragment> {

    int page;

    @ThreadPoint(ThreadType.HTTP) public void requestListData(boolean isLoadingMore) {
        SystemClock.sleep(1000);
        HomeHttp http = createHttpRequest(HomeHttp.class);
        BaseModelReq req = new BaseModelReq();
        if (isLoadingMore) {
            if (page < 1) return;
            req.pageNumber = page;
            TestModel testData = getTestListData();
//            ModelProductList fontList = http.requestRecommendFontListData(req);
            if (isSuccess(testData, true)) {
                page++;
                getView().addData(testData.list);
                paging(testData);
            }
        } else {
            page = 0;
            req.pageNumber = page;
            TestModel testData = getTestListData();
//            ModelProductList fontList = http.requestRecommendFontListData(req);
            if (isSuccess(testData, true)) {
                page = 1;
                getView().setData(testData.list);
                paging(testData);
            }
        }
    }

    private TestModel getTestListData() {
        TestModel model = new TestModel();
        model.code = 0;
        model.isLastPage = page >= 10;
        model.list = new ArrayList<>(20);
        for (int i = 0; i < 20; i++) {
            TestModel.TestModelInfo detail = new TestModel.TestModelInfo();
            detail.testName = getView().initTag() + "  " + i;
            model.list.add(detail);
        }
        return model;
    }
}
