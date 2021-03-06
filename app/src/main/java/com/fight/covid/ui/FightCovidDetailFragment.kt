package com.fight.covid.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.fight.covid.R
import com.fight.covid.databinding.FightCovidDetailFragmentBinding
import com.fight.covid.model.ApiResponse
import com.fight.covid.model.Countries
import com.fight.covid.utils.Status

class FightCovidDetailFragment : Fragment() {

    companion object {
        fun newInstance() = FightCovidDetailFragment()
    }

    private lateinit var binding: FightCovidDetailFragmentBinding
    private lateinit var viewModel: FightCovidViewModel
    private  val safeArgs : FightCovidDetailFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
       binding = FightCovidDetailFragmentBinding.inflate(inflater, container, false);
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(requireActivity()).get(FightCovidViewModel::class.java)
        val res=  viewModel.getCountryByCode(safeArgs.countryCode)
        binding.data = res
        binding.executePendingBindings()
        binding.timeline.setOnClickListener{
            openCasesTimelinePage()

        }
    }

    private fun openCasesTimelinePage() {
        val action = safeArgs.countryCode?.let { FightCovidDetailFragmentDirections.nextAction(it) }
        action?.let { view?.let { it1 -> Navigation.findNavController(it1).navigate(it) } }

    }


}