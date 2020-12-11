package com.aungpyaesone.patient.views.view_pods

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.edit_view_pod.view.*


class InputViewPod @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {
    override fun onFinishInflate() {
        super.onFinishInflate()
    }

    fun setEditTextData(data: String) {
        tvUnit.text = data
    }

}