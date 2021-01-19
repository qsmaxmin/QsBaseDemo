package com.sugar.grapecollege.common.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.qsmaxmin.qsbase.mvvm.MvPullRecyclerActivity
import com.sugar.grapecollege.R
import com.sugar.grapecollege.databinding.ActionbarTitleEditBinding

/**
 * @CreateBy qsmaxmin
 * @Date 2021/1/18 18:02
 * @Description
 */
abstract class BasePullRecyclerActivity<D> : MvPullRecyclerActivity<D>() {

    open fun createDefaultActionbar(inflater: LayoutInflater, parent: ViewGroup): ActionbarTitleEditBinding {
        val binding: ActionbarTitleEditBinding = ActionbarTitleEditBinding.inflate(inflater, parent, true)
        binding.owner = this
        return binding
    }

    override fun onViewClick(view: View) {
        super.onViewClick(view)
        if (view.id == R.id.iv_back) {
            activityFinish()
        }
    }
}