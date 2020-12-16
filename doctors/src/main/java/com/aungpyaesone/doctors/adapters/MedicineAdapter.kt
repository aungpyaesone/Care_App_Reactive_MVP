package com.aungpyaesone.doctors.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import com.aungpyaesone.doctors.R
import com.aungpyaesone.doctors.delegate.MedicineDelegate
import com.aungpyaesone.doctors.views.viewholder.MedicineViewHolder
import com.aungpyaesone.shared.data.vos.MedicineVO
import com.padc.shared.adapters.BaseAdapter
import com.padc.shared.viewholders.BaseViewHolder

class MedicineAdapter(private val mDelegate: MedicineDelegate) : BaseAdapter<BaseViewHolder<MedicineVO>,MedicineVO>(){

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