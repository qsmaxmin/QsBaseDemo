package com.sugar.grapecollege.common.presenter;

import com.qsmaxmin.qsbase.common.utils.QsHelper;
import com.qsmaxmin.qsbase.common.widget.listview.LoadingFooter;
import com.qsmaxmin.qsbase.mvp.QsIView;
import com.qsmaxmin.qsbase.mvp.fragment.QsIPullListFragment;
import com.qsmaxmin.qsbase.mvp.fragment.QsIPullRecyclerFragment;
import com.qsmaxmin.qsbase.mvp.model.QsConstants;
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
        if (baseModel != null && baseModel.code == 0) {
            return true;
        } else if (baseModel != null) {
            QsIView qsIview = getView();
            if (qsIview != null) {
                if (qsIview instanceof QsIPullListFragment) {
                    ((QsIPullListFragment) qsIview).stopRefreshing();
                } else if (qsIview instanceof QsIPullRecyclerFragment) {
                    ((QsIPullRecyclerFragment) qsIview).stopRefreshing();
                }
//                else if (qsIview instanceof J2WIPullHeaderViewpagerFragment) {
//                    L.i("isSuccess  J2WIPullHeaderViewpagerFragment ");
//                    J2WIPullHeaderViewpagerFragment pullHeaderViewpagerFragment = (J2WIPullHeaderViewpagerFragment) qsIview;
//                    pullHeaderViewpagerFragment.setRefresh(false);
//                }
                if (qsIview.currentViewState() == QsConstants.VIEW_STATE_CONTENT) {
//                    if (shouldToast) J2WToast.show(baseModel.msg);
                } else {
                    qsIview.showErrorView();
                }
                qsIview.loadingClose();
            }
        }
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
    protected void paging(BaseModel baseModel) {
        if (baseModel == null) {
            return;
        }
        QsIView qsIView = getView();
        if (qsIView == null) return;
        if (qsIView instanceof QsIPullListFragment) {
            if (baseModel.isLastPage) {
                ((QsIPullListFragment) qsIView).setLoadingState(LoadingFooter.State.TheEnd);
            } else {
                ((QsIPullListFragment) qsIView).setLoadingState(LoadingFooter.State.Normal);
            }
        } else if (qsIView instanceof QsIPullRecyclerFragment) {
            if (baseModel.isLastPage) {
                ((QsIPullRecyclerFragment) qsIView).setLoadingState(LoadingFooter.State.TheEnd);
            } else {
                ((QsIPullRecyclerFragment) qsIView).setLoadingState(LoadingFooter.State.Normal);
            }
        }
    }
}
