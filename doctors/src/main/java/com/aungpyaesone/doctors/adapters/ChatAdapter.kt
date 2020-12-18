package com.aungpyaesone.doctors.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.aungpyaesone.doctors.R
import com.aungpyaesone.doctors.utils.SessionManager
import com.aungpyaesone.doctors.views.viewholder.ChatItemViewHolder
import com.aungpyaesone.doctors.views.viewholder.ChatItemViewHolderTwo
import com.aungpyaesone.shared.data.vos.ChatMessageVO
import com.aungpyaesone.shared.adapters.BaseAdapter
import com.aungpyaesone.shared.views.viewholders.BaseViewHolder

class ChatAdapter : BaseAdapter<BaseViewHolder<ChatMessageVO>, ChatMessageVO>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ChatMessageVO> {
        return when(viewType){
            1 ->{
                val v = LayoutInflater.from(parent.context).inflate(R.layout.chat_item_view,parent,false)
                ChatItemViewHolder(v)
            }
            2 ->{ val v = LayoutInflater.from(parent.context).inflate(R.layout.chat_item_view_two,parent,false)
                ChatItemViewHolderTwo(v)
            }
            else ->{ val v = LayoutInflater.from(parent.context).inflate(R.layout.chat_item_view,parent,false)
                ChatItemViewHolder(v)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(mDataList[position].sentBy?.id){
            SessionManager.user_id ->{
                1
            }
            else -> {
                2
            }
        }
    }
}