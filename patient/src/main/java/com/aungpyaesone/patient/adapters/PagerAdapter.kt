package com.aungpyaesone.patient.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.aungpyaesone.patient.delegate.CaseSummaryDelegate
import com.aungpyaesone.patient.fragments.GeneralQuestionFragment
import com.aungpyaesone.patient.fragments.SpecialitiesQuestionFragment

class PagerAdapter(fm: FragmentManager,
                   private val mDelegate:CaseSummaryDelegate,
                   private val oneTime:String?,
                   private val speciality:String?) : FragmentPagerAdapter(fm,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> GeneralQuestionFragment.newInstance(mDelegate,oneTime)
            else-> SpecialitiesQuestionFragment.newInstance(mDelegate,speciality)
        }
    }

    override fun getCount(): Int {
       return 2
    }
}