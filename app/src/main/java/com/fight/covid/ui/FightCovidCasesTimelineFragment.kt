package com.fight.covid.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.fight.covid.R
import com.fight.covid.databinding.FragmentFightCovidCasesTimelineBinding
import com.fight.covid.model.ApiResponse

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FightCovidCasesTimelineFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FightCovidCasesTimelineFragment : Fragment() {


    private lateinit var binding: FragmentFightCovidCasesTimelineBinding
    private lateinit var viewModel: FightCovidViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding  = FragmentFightCovidCasesTimelineBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

            viewModel = ViewModelProviders.of(requireActivity()).get(FightCovidViewModel::class.java)
            viewModel!!.forcastResponse().observe(this, Observer { apiResponse: ApiResponse -> onResponse(apiResponse) })


    }

    private fun onResponse(apiResponse: ApiResponse) {
        val safeArgs : FightCovidCasesTimelineFragmentArgs by navArgs()
        if(apiResponse.data !=null){
            val res = apiResponse.data.countries.get(safeArgs.countryCode)?.timeseries
            binding.data = res
            binding.executePendingBindings()
        }

    }

}