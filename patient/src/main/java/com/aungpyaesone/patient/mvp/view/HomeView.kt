package com.aungpyaesone.patient.mvp.view

import com.aungpyaesone.shared.data.vos.SpecialitiesVO
import com.padc.shared.mvp.views.BaseView

interface HomeView : BaseView {
    fun showSpecialitiesList(specialitiesList:List<SpecialitiesVO>)
    fun showConfirmationDialog(specialitiesVO: SpecialitiesVO)
}