package com.aungpyaesone.patient.views.view_pods

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout

class EmptyViewPod @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    private var mDelegate: Delegate? = null

    override fun onFinishInflate() {
        super.onFinishInflate()
        setUpListener()
    }

    fun setEmptyData(emptyMessage:String,emptyImageUrl: String){
//        tvMessage.text = emptyMessage
//        Glide.with(context)
//            .load(emptyImageUrl)
//            .centerCrop()
//            .into(ivPoster)
    }

    fun setDelegate(delegate: Delegate){
        mDelegate = delegate
    }

    private fun setUpListener(){
//        btnFind.setOnClickListener {
//            mDelegate?.onTapFindSomethingNew()
//        }
//        btnReload.setOnClickListener {
//            mDelegate?.onTapReload()
//        }
    }

    interface Delegate {
        fun onTapFindSomethingNew()
        fun onTapReload()
    }
}