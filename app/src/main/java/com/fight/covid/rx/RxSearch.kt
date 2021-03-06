package com.fight.covid.rx

import android.widget.SearchView
import io.reactivex.subjects.PublishSubject
import io.reactivex.Observable;


object RxSearch {
        fun fromView(searchView: SearchView): Observable<String> {
            val subject = PublishSubject.create<String>()
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(s: String): Boolean {
                    subject.onNext(s)
                    return true
                }

                override  fun onQueryTextChange(text: String): Boolean {
                    subject.onNext(text)
                    return true
                }
            })
            return subject
        }
}