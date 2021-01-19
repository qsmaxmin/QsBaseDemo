package com.sugar.grapecollege.home.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qsmaxmin.qsbase.mvvm.adapter.MvListAdapterItem;
import com.sugar.grapecollege.R;
import com.sugar.grapecollege.common.http.resp.ModelProductInfo;

import androidx.annotation.NonNull;


/**
 * @CreateBy qsmaxmin
 * @Date 2017/5/3 15:02
 * @Description 字体列表适配器
 * T兼容model类型:
 */

public class MainListAdapterItem extends MvListAdapterItem<ModelProductInfo.ProductInfo> {
    private TextView tv_name;

    @Override public View onCreateItemView(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_product_list, parent, false);
        tv_name = view.findViewById(R.id.tv_name);
        return view;
    }

    @Override public void bindData(ModelProductInfo.ProductInfo info, int i, int i1) {
        tv_name.setText(info.productName);
    }

}
