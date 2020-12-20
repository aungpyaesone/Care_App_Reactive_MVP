package com.aungpyaesone.doctors.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.aungpyaesone.doctors.R
import com.aungpyaesone.doctors.delegate.RequestItemDelegate
import com.aungpyaesone.doctors.views.viewholder.DirectRequestViewHolder
import com.aungpyaesone.doctors.views.viewholder.RequestViewHolder
import com.aungpyaesone.shared.data.vos.ConsultationRequestVO
import com.aungpyaesone.shared.data.vos.ConsultedPatientVO
import com.aungpyaesone.shared.adapters.BaseAdapter
import com.aungpyaesone.shared.views.viewholders.BaseViewHolder

class RequestAdapter(private val mDelegate: RequestItemDelegate) : BaseAdapter<BaseViewHolder<ConsultationRequestVO>, ConsultationRequestVO>() {
    private val mViewType : Boolean? = false
    private var mConsultedList : List<ConsultedPatientVO> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ConsultationRequestVO> {

        return when(viewType){
            1 ->{
                val v = LayoutInflater.from(parent.context).inflate(R.layout.request_item_view,parent,false)
                RequestViewHolder(v,mDelegate)
            }
            2 ->{
                val v = LayoutInflater.from(parent.context).inflate(R.layout.request_item_view_two,parent,false)
                DirectRequestViewHolder(v,mDelegate)
            }
            else ->{
                val v = LayoutInflater.from(parent.context).inflate(R.layout.request_item_view,parent,false)
                RequestViewHolder(v,mDelegate)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        super.getItemViewType(position)
        if(mConsultedList.isNotEmpty()){
            return when(mDataList[position].patient_id){
                    mConsultedList[position].id -> {
                        2
                    }
                else -> {1}
            }
        }
        return 1

    }

    fun setConsultedList(list:List<ConsultedPatientVO>){
        mConsultedList = list
    }
}