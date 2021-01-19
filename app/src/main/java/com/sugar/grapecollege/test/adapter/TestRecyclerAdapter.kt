package com.sugar.grapecollege.test.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.qsmaxmin.qsbase.mvvm.adapter.MvRecycleAdapterItem
import com.sugar.grapecollege.R
import com.sugar.grapecollege.test.model.TestModel

class TestRecyclerAdapter(layoutInflater: LayoutInflater, parent: ViewGroup) : MvRecycleAdapterItem<TestModel.TestModelInfo>(layoutInflater, parent) {
    lateinit var mData: TestModel.TestModelInfo
    lateinit var tvTest: TextView

    override fun onCreateItemView(inflater: LayoutInflater, parent: ViewGroup): View {
        val view = inflater.inflate(R.layout.item_test_list, parent, false)
        tvTest = view.findViewById(R.id.tv_test)
        return view
    }

    override fun onBindItemData(data: TestModel.TestModelInfo?, position: Int, totalCount: Int) {
        mData = data!!
        tvTest.text =  "${data.testName}---->>>> 我是RecyclerView"
    }
}
