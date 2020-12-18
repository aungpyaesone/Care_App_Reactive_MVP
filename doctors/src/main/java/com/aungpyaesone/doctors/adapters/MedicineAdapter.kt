package com.aungpyaesone.doctors.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.aungpyaesone.doctors.R
import com.aungpyaesone.doctors.delegate.MedicineDelegate
import com.aungpyaesone.doctors.views.viewholder.MedicineViewHolder
import com.aungpyaesone.shared.data.vos.MedicineVO
import com.aungpyaesone.shared.adapters.BaseAdapter
import com.aungpyaesone.shared.views.viewholders.BaseViewHolder

class MedicineAdapter(private val mDelegate: MedicineDelegate) : BaseAdapter<BaseViewHolder<MedicineVO>, MedicineVO>(){

    private var filterList : MutableList<MedicineVO> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<MedicineVO> {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.medicine_item_view,parent,false)
        return MedicineViewHolder(v,mDelegate)
    }
    fun setMedicineList(list : ArrayList<MedicineVO>)
    {
        mDataList = list
        notifyDataSetChanged()
    }
}