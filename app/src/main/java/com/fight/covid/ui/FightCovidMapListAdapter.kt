package com.fight.covid.ui

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fight.covid.R
import com.fight.covid.helper.FightCovidHelper
import com.fight.covid.model.ListViewModel
import com.fight.covid.model.Response
import com.fight.covid.ui.FightCovidMapListAdapter.ApplicationViewHolder
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou

class FightCovidMapListAdapter(private val fightCovidListFragment: FightCovidListFragment, private var mapList: List<ListViewModel>?) : RecyclerView.Adapter<ApplicationViewHolder>() {
    private val mListener: FightCovidListener? = fightCovidListFragment
    val fightCovidHelper = FightCovidHelper()
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ApplicationViewHolder {
        return ApplicationViewHolder(viewGroup.context, viewGroup)
    }

    override fun onBindViewHolder(applicationViewHolder: ApplicationViewHolder, i: Int) {
        val listViewModel = mapList!![i]
        applicationViewHolder.flagName.text = listViewModel.countryName
        GlideToVectorYou.justLoadImage(fightCovidListFragment.activity,
                Uri.parse(listViewModel.countryFlagUrl), applicationViewHolder.flagUrl)
        applicationViewHolder.bind(listViewModel, mListener)
    }

    override fun getItemCount(): Int {
        return mapList?.size ?: 0
    }

    fun setData(data: Response) {
           mapList = fightCovidHelper.getListViewModel(data)
           notifyDataSetChanged()
    }

    class ApplicationViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val flagUrl: ImageView
        val flagName: TextView

        constructor(context: Context?, parent: ViewGroup?) : this(LayoutInflater.from(context).inflate(R.layout.forcast_item, parent, false)) {}

        fun bind(listViewModel: ListViewModel, mListener: FightCovidListener?) {
            itemView.setOnClickListener { v: View? -> mListener!!.onClick(listViewModel.countryCode) }
        }

        init {
            flagUrl = itemView.findViewById(R.id.flagUrl)
            flagName = itemView.findViewById(R.id.flagName)
        }
    }

    interface FightCovidListener {
        fun onClick(countryCode: String?)
    }

    private fun showListViewFragment() {}

}