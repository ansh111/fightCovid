package com.fight.covid.ui

import android.app.Application
import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.fight.covid.FightCovidApplication
import com.fight.covid.model.ApiResponse
import com.fight.covid.model.Response
import com.fight.covid.room.Countries
import com.fight.covid.room.CountriesRoomDatabase
import com.fight.covid.rx.SchedulerProvider
import dagger.hilt.android.scopes.ActivityScoped
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

sealed class SearchResult
class ValidResult(val result: MutableLiveData<ApiResponse>) : SearchResult()
object EmptyResult : SearchResult()
object EmptyQuery : SearchResult()
class ErrorResult(val e: Throwable) : SearchResult()
object TerminalError : SearchResult()
@ActivityScoped
class FightCovidViewModel @Inject constructor(private val repository: Repository, private val schedulerProvider: SchedulerProvider) : ViewModel() {
    private val disposables = CompositeDisposable()
    private val responseLiveData = MutableLiveData<ApiResponse>()


    @ExperimentalCoroutinesApi
    @VisibleForTesting
    internal val queryChannel = BroadcastChannel<String>(Channel.CONFLATED)

    fun forcastResponse(): MutableLiveData<ApiResponse> {
        return responseLiveData
    }


    @FlowPreview
    @ExperimentalCoroutinesApi
    @VisibleForTesting
    internal val internalSearchResult = queryChannel
            .asFlow()
            .debounce(500)
            .mapLatest {
                try {
                    if (it.length >= 2) {
                        val searchResult = withContext(Dispatchers.IO) {
                            performSearch(it)
                        }
                        println("Search result: ${searchResult.value} hits")

                        if (searchResult.value?.data?.countries?.isNotEmpty()!!) {
                            ValidResult(searchResult)
                        } else {
                            EmptyResult
                        }
                    } else {
                        EmptyQuery
                    }
                } catch (e: Throwable) {
                    if (e is CancellationException) {
                        println("Search was cancelled!")
                        throw e
                    } else {
                        ErrorResult(e)
                    }
                }
            }
            .catch { it: Throwable -> emit(TerminalError) }

    private suspend fun performSearch(query: String): MutableLiveData<ApiResponse> {
        var filteredListData: MutableLiveData<ApiResponse>
        return withContext(Dispatchers.IO) {
            filteredListData = responseLiveData
            val filteredList = filteredListData.value?.data?.countries
                    ?.filter { it.key.contains(query, true) }
                    ?.toMap()
            filteredListData.value?.data?.countries = filteredList
            filteredListData
        }
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

    @FlowPreview
    @ExperimentalCoroutinesApi
    val searchResult = internalSearchResult.asLiveData()

    fun searchData(it: String) {
        viewModelScope.launch {
            queryChannel.send(it)
        }
    }

    fun getCountryByCode(countryCode:String):Countries?= runBlocking(Dispatchers.Default){
        repository.getCountryByCode(countryCode = countryCode)
    }



    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(countries: Countries) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(countries)
    }

}