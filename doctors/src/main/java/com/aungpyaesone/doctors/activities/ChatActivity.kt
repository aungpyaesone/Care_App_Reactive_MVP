package com.aungpyaesone.doctors.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aungpyaesone.doctors.R
import com.aungpyaesone.doctors.adapters.ChatAdapter
import com.aungpyaesone.doctors.mvp.presenters.ChatPresenter
import com.aungpyaesone.doctors.mvp.presenters.impls.ChatPresenterImpl
import com.aungpyaesone.doctors.mvp.views.ChatView
import com.padc.shared.activites.BaseActivity

class ChatActivity : BaseActivity(),ChatView {

    private lateinit var mPresenter: ChatPresenter
    private lateinit var mChatAdapter: ChatAdapter

    companion object{
        const val DOCUMENT_ID = "document_id"
        fun newInstance(context: Context, documentId:String)= Intent(context,ChatActivity::class.java).apply {
            putExtra(DOCUMENT_ID,documentId)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        setUpPresenter()
    }



    private fun setUpPresenter() {
        mPresenter = getPresenter<ChatPresenterImpl,ChatView>()
    }

    override fun showLoading() {
        TODO("Not yet implemented")
    }

    override fun hideLoading() {
        TODO("Not yet implemented")
    }
}