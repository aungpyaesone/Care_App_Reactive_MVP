package com.aungpyaesone.doctors.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aungpyaesone.doctors.R
import com.aungpyaesone.doctors.adapters.MedicineAdapter
import com.aungpyaesone.doctors.fragments.RoutineDialogFragment
import com.aungpyaesone.doctors.mvp.presenters.PrescriptionPresenter
import com.aungpyaesone.doctors.mvp.presenters.impls.PrescriptionPresenterImpl
import com.aungpyaesone.doctors.mvp.views.PrescriptionView
import com.aungpyaesone.shared.data.vos.ConsultationChatVO
import com.aungpyaesone.shared.data.vos.MedicineVO
import com.aungpyaesone.shared.data.vos.PrescriptionVO
import com.google.gson.Gson
import com.aungpyaesone.shared.activites.BaseActivity
import kotlinx.android.synthetic.main.activity_prescription_medicine.*
import kotlinx.android.synthetic.main.activity_prescription_medicine.ivBack

class PrescriptionMedicineActivity : BaseActivity(),PrescriptionView {
    private lateinit var mPresenter : PrescriptionPresenter
    private lateinit var mAdapter : MedicineAdapter
    private var mConsultationChatVO : ConsultationChatVO? = null
    private var list : List<MedicineVO> = arrayListOf()
    private  var filterlist : ArrayList<MedicineVO> = arrayListOf()


    companion object{
        const val CHAT_VO = "ConsultationChatVO"
        var prescriptionList : ArrayList<PrescriptionVO> = arrayListOf()
        fun newInstance(context: Context, chatVO:String)= Intent(context,PrescriptionMedicineActivity::class.java).apply {
            putExtra(CHAT_VO,chatVO)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prescription_medicine)
        setUpPresenter()
        setupListener()
        setupRecycler()
        val data = intent.getStringExtra(CHAT_VO)
        mConsultationChatVO = Gson().fromJson(data,ConsultationChatVO::class.java)
        mConsultationChatVO?.doctor?.speciality.let { it?.let { speciality ->
            mPresenter.getAllMedicine(this,
                speciality
            )
        } }
    }

    private fun setupListener() {
        ivBack.setOnClickListener {
            startActivity(mConsultationChatVO?.id?.let { it1 ->
                ChatActivity.newInstance(this,
                    it1
                )
            })
            finish()
        }
        searchView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                filter(s.toString())
            }
        })

        btnFinishConsult.setOnClickListener {
            if(prescriptionList.size > 0){
                mConsultationChatVO?.let { it1 -> mPresenter.onTapFinishConsultation(it1, prescriptionList) }
            }
        }

    }

    private fun filter(text : String)
    {
        filterlist.clear()
        list?.let{

            for( item in list)
            {
                var st = item.name.toString().toLowerCase()
                if(st.contains(text))
                {
                    filterlist.add(item)
                }
            }
            mAdapter.setMedicineList(filterlist)
        }

    }

    private fun setupRecycler() {
        mAdapter = MedicineAdapter(mPresenter)
        rvMedicine.apply {
            layoutManager = LinearLayoutManager(applicationContext,RecyclerView.VERTICAL,false)
            adapter = mAdapter
        }
    }

    fun setUpPresenter(){
        mPresenter = getPresenter<PrescriptionPresenterImpl,PrescriptionView>()
    }

    override fun showMedicineList(medicineList: List<MedicineVO>) {
        this.list = medicineList
        mAdapter.setData(medicineList)
    }

    override fun showRoutineDialog(medicineVO: MedicineVO) {
        val routineDialogFragment =  RoutineDialogFragment.newFragment()
        val bundle = Bundle()
        bundle.putString(RoutineDialogFragment.BUNDLE_NAME, medicineVO.name)
        bundle.putString(RoutineDialogFragment.ID, medicineVO.id)
        bundle.putString(RoutineDialogFragment.BUNDLE_PRICE, medicineVO.price.toString())

        routineDialogFragment.arguments = bundle
        supportFragmentManager?.let {
            routineDialogFragment.show(
                it,""
            )
        }
    }

    override fun removeMedicine(medicineVO: MedicineVO) {
        var index=0
        for(item in prescriptionList)
        {
            if(item.medicine_name == medicineVO.name)
            {
                prescriptionList.removeAt(index)
            }
            index++
        }
    }

    override fun finishConsultation() {
        startActivity(mConsultationChatVO?.id?.let { ChatActivity.newInstance(this, it) })
        this.finish()
    }

    override fun showLoading() {
        showProgressDialog()

    }

    override fun hideLoading() {
        hideProgressDialog()

    }

}