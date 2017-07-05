package com.sugar.grapecollege.searcher.adapter;

import android.view.View;
import android.widget.TextView;

import com.qsmaxmin.qsbase.common.utils.QsHelper;
import com.qsmaxmin.qsbase.common.viewbind.annotation.Bind;
import com.qsmaxmin.qsbase.mvp.adapter.QsListAdapterItem;
import com.sugar.grapecollege.R;
import com.sugar.grapecollege.product.model.ModelProductInfo;


/**
 * @CreateBy qsmaxmin
 * @Date 17/7/2  下午9:51
 * @Description
 */

public class SearcherAdapterItem extends QsListAdapterItem<ModelProductInfo.ProductInfo> {

    @Bind(R.id.tv_name) TextView tv_name;

    @Override public int getItemLayout() {
        return R.layout.item_product_list;
    }

    @Override public void init(View view) {
        QsHelper.getInstance().getViewBindHelper().bind(this, view);
    }

    @Override public void bindData(ModelProductInfo.ProductInfo modelProduct, int i, int i1) {
        tv_name.setText(modelProduct.productName);
    }
}
