package com.fight.covid.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fight.covid.model.ApiResponse
import com.fight.covid.model.Response
import com.fight.covid.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class FightCovidViewModel(private val repository: Repository, private val schedulerProvider: SchedulerProvider) : ViewModel() {
    private val disposables = CompositeDisposable()
    private val responseLiveData = MutableLiveData<ApiResponse>()



    fun forcastResponse(): MutableLiveData<ApiResponse> {
        return responseLiveData
    }



    fun loadData() {
        disposables.add(repository.loadData().subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnSubscribe { obj: Disposable? -> responseLiveData.setValue(ApiResponse.loading()) }
                .subscribe(
                        { response: Response? -> responseLiveData.setValue(ApiResponse.success(response!!)) }
                ) { throwable: Throwable? -> responseLiveData.setValue(ApiResponse.error(throwable!!)) }
        )
    }

    override fun onCleared() {
        disposables.clear()
    }

}