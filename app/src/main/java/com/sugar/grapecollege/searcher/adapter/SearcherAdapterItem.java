package com.sugar.grapecollege.searcher.adapter;

import android.view.View;
import android.widget.TextView;

import com.qsmaxmin.qsbase.mvp.adapter.QsListAdapterItem;
import com.sugar.grapecollege.R;
import com.sugar.grapecollege.product.model.ModelProduct;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @CreateBy qsmaxmin
 * @Date 17/7/2  下午9:51
 * @Description
 */

public class SearcherAdapterItem extends QsListAdapterItem<ModelProduct.ProductDetail> {

    @Bind(R.id.tv_name) TextView tv_name;

    @Override public int getItemLayout() {
        return R.layout.item_main_list;
    }

    @Override public void init(View view) {
        ButterKnife.bind(this, view);
    }

    @Override public void bindData(ModelProduct.ProductDetail modelProduct, int i, int i1) {
        tv_name.setText(modelProduct.productName);
    }
}
