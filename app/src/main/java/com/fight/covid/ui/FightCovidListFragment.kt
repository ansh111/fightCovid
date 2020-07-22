package com.fight.covid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.fight.covid.R
import com.fight.covid.databinding.FightCovidListFragmentBinding
import com.fight.covid.helper.FightCovidHelper
import com.fight.covid.model.ApiResponse
import com.fight.covid.model.Response
import com.fight.covid.utils.DebouncingQueryTextListener
import com.fight.covid.utils.Status
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

class FightCovidListFragment : Fragment(),FightCovidMapListAdapter.FightCovidListener {
    private lateinit var adapter: FightCovidMapListAdapter
    private var mViewModel: FightCovidViewModel? = null

    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mViewModel = ViewModelProviders.of(requireActivity()).get(FightCovidViewModel::class.java)
        val fightCovidListFragmentBinding: FightCovidListFragmentBinding = DataBindingUtil.inflate(layoutInflater, R.layout.fight_covid_list_fragment, container, false)
        val fightCovidHelper = FightCovidHelper()
        val recyclerView = fightCovidListFragmentBinding.recyclerview
        mViewModel!!.forcastResponse().observe(this, Observer { apiResponse: ApiResponse -> consumeResponse(fightCovidListFragmentBinding,apiResponse) })
        mViewModel!!.searchResult.observe(this, Observer { it-> handleSearchResult(it) })
         adapter = FightCovidMapListAdapter(this, fightCovidHelper.getListViewModel(null))
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        initSearch(fightCovidListFragmentBinding)
        return fightCovidListFragmentBinding.root
    }

    private fun handleSearchResult(it: SearchResult) {
        when (it) {
            is ValidResult -> {
                it.result.value?.data?.let { it1 -> adapter.setData(it1) }
            }
        }
    }



    private fun initSearch(fightCovidListFragmentBinding: FightCovidListFragmentBinding) {
        fightCovidListFragmentBinding.searchView.apply {
            setOnQueryTextListener(
                    activity?.let {
                        DebouncingQueryTextListener(
                                it
                        ) { newText ->
                            newText?.let {
                                if (it.isNotEmpty() && it.length >=2) {
                                    mViewModel?.searchData(it)
                                }else{
                                    mViewModel?.loadData()
                                }
                            }
                        }
                    }
            )

        }
        
    }


    private fun consumeResponse(binding: FightCovidListFragmentBinding, apiResponse: ApiResponse) {
        when (apiResponse.status) {
            Status.LOADING -> {
                binding.progressBar.visibility = View.VISIBLE
            }
            Status.SUCCESS -> {
                binding.progressBar.visibility = View.GONE
                renderUI(apiResponse.data!!)
            }
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