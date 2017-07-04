package com.sugar.grapecollege.home.adapter;

import android.view.View;
import android.widget.TextView;

import com.qsmaxmin.qsbase.mvp.adapter.QsListAdapterItem;
import com.sugar.grapecollege.R;
import com.sugar.grapecollege.product.model.ModelProductInfo;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @CreateBy qsmaxmin
 * @Date 2017/5/3 15:02
 * @Description 字体列表适配器
 * T兼容model类型:
 */

public class MainListAdapterItem extends QsListAdapterItem<ModelProductInfo.ProductInfo> {
    @Bind(R.id.tv_name) TextView tv_name;

    @Override public int getItemLayout() {
        return R.layout.item_product_list;
    }

    @Override public void init(View view) {
        ButterKnife.bind(this, view);
    }

    @Override public void bindData(ModelProductInfo.ProductInfo info, int i, int i1) {
        tv_name.setText(info.productName);
    }

}
