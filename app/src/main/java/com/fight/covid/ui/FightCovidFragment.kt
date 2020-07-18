package com.fight.covid.ui

import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.fight.covid.FightCovidApplication
import com.fight.covid.R
import com.fight.covid.databinding.FragmentHomeCovidBinding
import com.fight.covid.model.Response
import javax.inject.Inject


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
        (activity?.application as FightCovidApplication).appComponent.doInjection(this)
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
     //   fightCovidViewModel!!.forcastResponse().observe(this, Observer { apiResponse: ApiResponse -> consumeResponse(apiResponse) })
        fightCovidViewModel!!.loadData()
    }

    /*private fun consumeResponse(apiResponse: ApiResponse) {
        when (apiResponse.status) {
            Status.LOADING -> {
            }
            Status.SUCCESS -> renderUI(apiResponse.data!!)
            Status.ERROR -> {
            }
            else -> {
            }
        }
    }*/


    companion object {
        const val TAG = "WeatherActivity"
    }
}