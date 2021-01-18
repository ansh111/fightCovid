package com.fight.covid.ui

import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.fight.covid.FightCovidApplication
import com.fight.covid.R
import com.fight.covid.databinding.FragmentHomeCovidBinding
import com.fight.covid.model.ApiResponse
import com.fight.covid.model.ListViewModel
import com.fight.covid.model.Response
import com.fight.covid.room.Countries
import com.fight.covid.utils.Status
import com.jakewharton.retrofit2.adapter.rxjava2.Result.response
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FightCovidFragment : Fragment(){
    @JvmField
    @Inject
    var viewModelFactory: ViewModelFactory? = null
    var fightCovidViewModel: FightCovidViewModel? = null
    private var progressBar: ProgressBar? = null
    private val mLocationManager: LocationManager? = null
    private var mCachedCity: String? = null
    private var isApiCalledOnce = false
    private lateinit var binding:FragmentHomeCovidBinding
    private lateinit var responseData:Response

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       fightCovidViewModel = ViewModelProviders.of(requireActivity(), viewModelFactory).get(FightCovidViewModel::class.java)
        binding  = FragmentHomeCovidBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        callWeatherApi()
        binding.searchView.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.fightCovidListFragment, null)
        }
    }

    private fun callWeatherApi() {
        fightCovidViewModel!!.loadData()
        fightCovidViewModel!!.forcastResponse().observe(this, Observer
        { apiResponse: ApiResponse -> consumeResponse(apiResponse) })

    }

    private fun consumeResponse(apiResponse: ApiResponse) {
        when(apiResponse.status) {
            Status.SUCCESS -> {
                binding.progressBar.visibility = View.GONE
                insertDataInDb(apiResponse.data!!)
            }
        }

    }

    private fun insertDataInDb(data: Response) {
        for ((key, value) in data.getCountries().entries) {
            fightCovidViewModel?.insert(Countries(key,value.name,value.flag,value.reports,value.cases,value.deaths,value.recovered))
        }
    }


    companion object {
        const val TAG = "WeatherActivity"
    }
}