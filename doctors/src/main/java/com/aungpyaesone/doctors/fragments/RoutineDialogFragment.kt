package com.aungpyaesone.doctors.fragments

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import com.aungpyaesone.doctors.R
import com.aungpyaesone.doctors.activities.PrescriptionMedicineActivity.Companion.prescriptionList
import com.aungpyaesone.doctors.mvp.presenters.RoutinePresenter
import com.aungpyaesone.doctors.mvp.presenters.impls.RoutinePresenterImpls
import com.aungpyaesone.shared.data.vos.PrescriptionVO
import com.aungpyaesone.shared.data.vos.RoutineVO
import kotlinx.android.synthetic.main.fragment_routine_dialog.*
import kotlinx.android.synthetic.main.fragment_routine_dialog.view.*
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RoutineDialogFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RoutineDialogFragment : DialogFragment() {
    private lateinit var mPresenter: RoutinePresenter

    var morningstatus = false
    var afternoonstatus = false
    var nightstatus = false

    var number = 1
    var daycount: Int = 0
    var tabcount: String = "1"
    var eatingtime: String = ""
    var daystemp: String = ""

    var medicinePrice: String? = ""
    var medicineName: String? = ""
    var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_routine_dialog, container, false)
    }

    override fun onStart() {
        super.onStart()
        dialog?.apply {
            setCancelable(false)
            window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPresenter()
        setupListener()
        view.tvMedicineName.text = arguments?.getString(BUNDLE_NAME)
        medicineName = arguments?.getString(BUNDLE_NAME)
        medicinePrice = arguments?.getString(BUNDLE_PRICE)
        view.tvMorning.setOnClickListener {
            morningstatus = when (morningstatus) {
                false -> {
                    view.tvMorning.setBackgroundDrawable(context?.getDrawable(R.drawable.selector_background))
                    view.tvMorning.setTextColor(Color.WHITE)
                    count++
                    true
                }
                else -> {
                    view.tvMorning.setBackgroundDrawable(context?.getDrawable(R.drawable.border_background))
                    view.tvMorning.setTextColor(Color.BLACK)
                    count--
                    false
                }

            }
            if (count > -1) {
                var result = number * daycount * count
                etTab?.text = result.toString()
                tabcount = result.toString()
            }
        }

        view.tvAfternoon.setOnClickListener {
            afternoonstatus = when (afternoonstatus) {
                false -> {
                    view.tvAfternoon.setBackgroundDrawable(context?.getDrawable(R.drawable.selector_background))
                    view.tvAfternoon.setTextColor(Color.WHITE)
                    count++
                    true
                }
                else -> {
                    view.tvAfternoon.setBackgroundDrawable(context?.getDrawable(R.drawable.border_background))
                    view.tvAfternoon.setTextColor(Color.BLACK)
                    count--
                    false
                }
            }
            if (count > -1) {
                var result = number * daycount * count
                etTab?.text = result.toString()
                tabcount = result.toString()
            }
        }

        view.tvNight.setOnClickListener {
            nightstatus = when (nightstatus) {
                false -> {
                    view.tvNight.setBackgroundDrawable(context?.getDrawable(R.drawable.selector_background))
                    view.tvNight.setTextColor(Color.WHITE)
                    count++
                    true
                }
                else -> {
                    view.tvNight.setBackgroundDrawable(context?.getDrawable(R.drawable.border_background))
                    view.tvNight.setTextColor(Color.BLACK)
                    count--
                    false
                }
            }
            if (count > -1) {
                var result = number * daycount * count
                etTab?.text = result.toString()
                tabcount = result.toString()
            }
        }

        view.tvFirstNote.setOnClickListener {
            view.tvFirstNote.setBackgroundResource(R.drawable.selector_background)
            view.tvFirstNote.setTextColor(Color.WHITE)
            view.tvSecondNode.setBackgroundResource(R.drawable.border_background)
            view.tvSecondNode.setTextColor(Color.BLACK)
            eatingtime = tvFirstNote.text.toString()
        }

        view.tvSecondNode.setOnClickListener {
            view.tvFirstNote.setBackgroundResource(R.drawable.border_background)
            view.tvFirstNote.setTextColor(Color.BLACK)
            view.tvSecondNode.setBackgroundResource(R.drawable.selector_background)
            view.tvSecondNode.setTextColor(Color.WHITE)
            eatingtime = tvSecondNode.text.toString()
        }


        view.spWeek.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                var day = parent.getItemAtPosition(position).toString()
                if (day == "Days") {
                    daycount = 1
                    daystemp = " Days"
                } else {
                    daycount = 7
                    daystemp = " Week"

                }
                val result = number * daycount * count
                etTab?.text = result.toString()
                tabcount = result.toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        view.btnAddRoutine.setOnClickListener {
            // prescription list add
            var days: String = ""
            if (morningstatus) {
                days += " မနက် ၊ "
            }
            if (afternoonstatus) {
                days += "နေ့  ၊  "
            }
            if (nightstatus) {
                days += "ည"
            }

            var medicaltime: String = ""

            var routineVO = RoutineVO(
                id = "0",
                amount = medicinePrice.toString(),
                day = view.et_day.text.toString() + daystemp,
                note = etNotes?.text.toString(),
                tab = tabcount,
                times = days,
                repeat = eatingtime

            )

            var prescriptionVO = PrescriptionVO(
                id = UUID.randomUUID().toString(),
                count = tabcount,
                medicine_name = medicineName,
                price = medicinePrice.toString(),
                routineVO = routineVO
            )
            if (etNotes?.text.toString().isNotEmpty()) {
                prescriptionList.add(prescriptionVO)
                dismiss()
            } else {
                activity?.let {
                    Toast.makeText(
                        it.applicationContext,
                        "အချက်အလက် အားလုံး ပြည့်စုံအောင် ဖြည့်စွက်ပေးရန် လိုနေပါသေး သည်",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        view.et_day.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                charSequence: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {

            }

            override fun afterTextChanged(s: Editable?) {
                var data = s.toString()
                if (data.isNotEmpty()) {
                    number = data.toInt()
                    var result = number * daycount * count
                    view.etTab.text = result.toString()
                    tabcount = result.toString()
                }
            }
        })
    }


    private fun setupListener() {

    }

    override fun onResume() {
        super.onResume()
        val params = dialog?.window?.attributes
        params?.width = ViewGroup.LayoutParams.MATCH_PARENT
        params?.height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialog?.window?.attributes = params
    }

    private fun setupPresenter() {
        activity?.let {
            mPresenter = ViewModelProviders.of(it).get(RoutinePresenterImpls::class.java)
        }
    }

    companion object {

        const val ID = "MEDICINE_ID"
        const val BUNDLE_NAME = "BUNDLE_NAME"
        const val BUNDLE_PRICE = "BUNDLE_PRICE"
        const val BUNDLE_IMAGE = "BUNDLE_IMAGE"

        fun newFragment(): RoutineDialogFragment {
            return RoutineDialogFragment()
        }
    }


    /* private fun calculateResult(count:Int,number:Int){

         }*/
}