package com.aungpyaesone.patient.delegate

import com.aungpyaesone.shared.data.vos.RecentDoctorVO

interface RecentlyDoctorDelegate {
    fun onTapRecentlyDoctor(recentDoctorVO: RecentDoctorVO)
}