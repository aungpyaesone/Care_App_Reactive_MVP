package com.aungpyaesone.patient.delegate

import com.aungpyaesone.shared.data.vos.SpecialitiesVO

interface SpecialitiesDelegate {
    fun onTapSpecialitiesItem(specialitiesVO: SpecialitiesVO)
}