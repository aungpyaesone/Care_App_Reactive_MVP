package com.aungpyaesone.shared.util

import android.widget.SearchView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class SearchObservable {

    companion object {

        fun fromView(searchView :SearchView) : Observable<String> {
            val subject = PublishSubject.create<String>()
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    subject.onComplete()
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    subject.onNext(newText)
                    return true
                }
            })
            return subject
        }
    }

}
