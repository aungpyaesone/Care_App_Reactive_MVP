package com.aungpyaesone.patient.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.aungpyaesone.patient.R
import com.aungpyaesone.patient.utils.SessionManager
import com.aungpyaesone.patient.views.viewholders.ChatItemViewHolder
import com.aungpyaesone.patient.views.viewholders.ChatItemViewHolderTwo
import com.aungpyaesone.shared.data.vos.ChatMessageVO
import com.padc.shared.adapters.BaseAdapter
import com.padc.shared.viewholders.BaseViewHolder

class ChatAdapter : BaseAdapter<BaseViewHolder<ChatMessageVO>,ChatMessageVO>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ChatMessageVO> {
        return when(viewType){
            1 ->{
                val v = LayoutInflater.from(parent.context).inflate(R.layout.chat_item_view,parent,false)
                ChatItemViewHolder(v)
            }
            2 ->{ val v = LayoutInflater.from(parent.context).inflate(R.layout.chat_item_view_two,parent,false)
                ChatItemViewHolderTwo(v)
            }
            else ->{
                val v = LayoutInflater.from(parent.context).inflate(R.layout.chat_item_view,parent,false)
                ChatItemViewHolder(v)
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        super.getItemViewType(position)
        return when(SessionManager.patient_id){
            mDataList[position].sentBy?.id  ->{
                1
            }
            else -> {
                2
            }
        }
    }
}