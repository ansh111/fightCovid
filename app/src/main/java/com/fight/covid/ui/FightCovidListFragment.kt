package com.fight.covid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.fight.covid.R
import com.fight.covid.databinding.FightCovidListFragmentBinding
import com.fight.covid.helper.FightCovidHelper
import com.fight.covid.model.ApiResponse
import com.fight.covid.model.Response
import com.fight.covid.utils.Status

class FightCovidListFragment : Fragment(),FightCovidMapListAdapter.FightCovidListener {
    private lateinit var adapter: FightCovidMapListAdapter
    private var mViewModel: FightCovidViewModel? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mViewModel = ViewModelProviders.of(requireActivity()).get(FightCovidViewModel::class.java)
        val fightCovidListFragmentBinding: FightCovidListFragmentBinding = DataBindingUtil.inflate(layoutInflater, R.layout.fight_covid_list_fragment, container, false)
        val fightCovidHelper = FightCovidHelper()
        val recyclerView = fightCovidListFragmentBinding.recyclerview
        mViewModel!!.forcastResponse().observe(this, Observer { apiResponse: ApiResponse -> consumeResponse(apiResponse) })
         adapter = FightCovidMapListAdapter(this, fightCovidHelper.getListViewModel(null))
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        return fightCovidListFragmentBinding.root
    }

    private fun consumeResponse(apiResponse: ApiResponse) {
        when (apiResponse.status) {
            Status.LOADING -> {
            }
            Status.SUCCESS -> renderUI(apiResponse.data!!)
            Status.ERROR -> {
            }
            else -> {
            }
        }
    }

    private fun renderUI(data: Response) {
        adapter.setData(data)
    }
    

    companion object {
        fun newInstance(): FightCovidListFragment {
            return FightCovidListFragment()
        }
    }

    override fun onClick(countryCode: String?) {
            val action = countryCode?.let { FightCovidListFragmentDirections.actionFightCovidListFragmentToFightCovidDetailFragment(it) }
            action?.let { view?.let { it1 -> findNavController(it1).navigate(it) } }
    }
}