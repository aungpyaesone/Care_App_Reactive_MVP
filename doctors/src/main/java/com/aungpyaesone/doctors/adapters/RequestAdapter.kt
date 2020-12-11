package com.aungpyaesone.doctors.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.aungpyaesone.doctors.R
import com.aungpyaesone.doctors.delegate.RequestItemDelegate
import com.aungpyaesone.doctors.views.viewholder.DirectRequestViewHolder
import com.aungpyaesone.doctors.views.viewholder.RequestViewHolder
import com.aungpyaesone.shared.data.vos.ConsultationRequestVO
import com.aungpyaesone.shared.data.vos.PatientVO
import com.padc.shared.adapters.BaseAdapter
import com.padc.shared.viewholders.BaseViewHolder

class RequestAdapter(private val mDelegate: RequestItemDelegate) : BaseAdapter<BaseViewHolder<ConsultationRequestVO>,ConsultationRequestVO>() {
    private val mViewType : Boolean? = false

    private fun setViewType(viewStatus:Boolean){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ConsultationRequestVO> {

        when(mViewType){
            true ->{
                val v = LayoutInflater.from(parent.context).inflate(R.layout.request_item_view_two,parent,false)
                return DirectRequestViewHolder(v,mDelegate)
            }
            else ->{
                val v = LayoutInflater.from(parent.context).inflate(R.layout.request_item_view,parent,false)
                return RequestViewHolder(v,mDelegate)
            }
        }


    }
}