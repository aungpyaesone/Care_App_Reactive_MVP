package com.padc.shared.adapters

import androidx.recyclerview.widget.RecyclerView
import com.padc.shared.viewholders.BaseViewHolder

abstract class BaseAdapter<T: BaseViewHolder<W>,W> : RecyclerView.Adapter<T>(){
    private var mDataList:MutableList<W> = mutableListOf()

    override fun onBindViewHolder(holder: T, position: Int) {
        holder.bindData(mDataList[position])
    }

    override fun getItemCount(): Int {
        return mDataList.size

    }

    fun setData (data : List<W>){
        mDataList.clear()
        mDataList.addAll(data)
        notifyDataSetChanged()
    }

}