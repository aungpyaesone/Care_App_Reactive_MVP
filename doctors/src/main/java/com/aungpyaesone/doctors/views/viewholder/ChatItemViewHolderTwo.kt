package com.aungpyaesone.doctors.views.viewholder

import android.view.View
import androidx.core.net.toUri
import com.aungpyaesone.doctors.R
import com.aungpyaesone.shared.data.vos.ChatMessageVO
import com.aungpyaesone.shared.extensions.load
import com.aungpyaesone.shared.views.viewholders.BaseViewHolder
import kotlinx.android.synthetic.main.chat_item_view_two.view.*

class ChatItemViewHolderTwo (itemView: View) : BaseViewHolder<ChatMessageVO>(itemView) {

    override fun bindData(data: ChatMessageVO) {
        mData = data
        mData?.let {
            itemView.tvTextMessage.text =  it.messageText
           // itemView.tvDate.text = Calendar.getInstance().time.toString()
            itemView.tvTime.text = it.sendAt
            it.sentBy?.photo?.toUri()?.let { photo -> itemView.ivSenderProfile.load(photo, R.drawable.image_placeholder) }
        }
    }


}