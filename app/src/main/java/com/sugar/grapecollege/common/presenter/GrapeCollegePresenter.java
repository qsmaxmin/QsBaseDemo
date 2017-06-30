package com.sugar.grapecollege.common.presenter;

import com.qsmaxmin.qsbase.common.utils.QsHelper;
import com.qsmaxmin.qsbase.mvp.QsIView;
import com.qsmaxmin.qsbase.mvp.presenter.QsPresenter;
import com.sugar.grapecollege.common.model.BaseModel;

/**
 * @CreateBy qsmaxmin
 * @Date 2017/5/3 13:30
 * @Description
 */

public abstract class GrapeCollegePresenter<V extends QsIView> extends QsPresenter<V> {


    protected boolean isSuccess(BaseModel baseModel) {
        return isSuccess(baseModel, false);
    }

    /**
     * 请求网络是否成功
     */
    protected boolean isSuccess(BaseModel baseModel, boolean shouldToast) {
//        L.i("isSuccess  " + baseModel);
//        if (baseModel != null && "0".equals(baseModel.code)) {
//            return true;
//        } else if (baseModel != null) {
//            J2WIView j2WIView = (J2WIView) getView();
//            if (j2WIView != null) {
//                if (j2WIView instanceof J2WIViewPullListFragment) {
//                    L.i("isSuccess  J2WIViewPullListFragment ");
//                    J2WIViewPullListFragment pullListFragment = (J2WIViewPullListFragment) j2WIView;
//                    pullListFragment.setRefreshing(false);
//                } else if (j2WIView instanceof J2WIViewPullRecyclerViewFragment) {
//                    L.i("isSuccess  J2WIViewPullRecyclerViewFragment ");
//                    J2WIViewPullRecyclerViewFragment pullListFragment = (J2WIViewPullRecyclerViewFragment) j2WIView;
//                    pullListFragment.setRefreshing(false);
//                } else if (j2WIView instanceof J2WIPullHeaderViewpagerFragment) {
//                    L.i("isSuccess  J2WIPullHeaderViewpagerFragment ");
//                    J2WIPullHeaderViewpagerFragment pullHeaderViewpagerFragment = (J2WIPullHeaderViewpagerFragment) j2WIView;
//                    pullHeaderViewpagerFragment.setRefresh(false);
//                }
//                if (j2WIView.isShowContent()) {
//                    if (shouldToast) J2WToast.show(baseModel.msg);
//                } else {
//                    j2WIView.showError();
//                }
//                j2WIView.loadingClose();
//            }
//        }
        return true;
    }

    /**
     * 获取String资源
     */
    protected String getString(int stringId) {
        return QsHelper.getInstance().getApplication().getString(stringId);
    }

    /**
     * 分页
     * @param baseModel 分页数据持有
     */
    public void paging(BaseModel baseModel) {
//        if (baseModel == null) {
//            return;
//        }
//        J2WIView j2WIView = (J2WIView) getView();
//        if (j2WIView == null) return;
//        if (j2WIView instanceof J2WIViewPullListFragment) {
//            J2WIViewPullListFragment fragment = (J2WIViewPullListFragment) j2WIView;
//            if (baseModel.isLastPage) {
//                fragment.setLoading(LoadingFooter.State.TheEnd);
//            } else {
//                fragment.setLoading(LoadingFooter.State.Normal);
//            }
//        } else if (j2WIView instanceof J2WIViewPullRecyclerViewFragment) {
//            J2WIViewPullRecyclerViewFragment recyclerViewFragment = (J2WIViewPullRecyclerViewFragment) j2WIView;
//            if (baseModel.isLastPage) {
//                recyclerViewFragment.setLoading(LoadingFooter.State.TheEnd);
//            } else {
//                recyclerViewFragment.setLoading(LoadingFooter.State.Normal);
//            }
//        }
    }

}
