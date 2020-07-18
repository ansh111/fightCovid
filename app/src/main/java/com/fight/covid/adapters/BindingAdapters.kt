package com.fight.covid.adapters

import androidx.databinding.BindingAdapter
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.Pie
import com.anychart.enums.Align
import com.anychart.enums.LegendLayout
import com.fight.covid.model.Countries


@BindingAdapter("makeChart")
fun makeChart(view: AnyChartView, res: Countries) {
    val pie: Pie = AnyChart.pie()
    val data: MutableList<DataEntry> = mutableListOf()
    data.add(ValueDataEntry("Cases", res.cases));
    data.add(ValueDataEntry("Deaths", res.deaths));
    data.add(ValueDataEntry("Recoveries", res.recovered));
    pie.data(data);

    pie.legend().title().enabled(true);
    pie.legend().title()
            .text(res.name)
            .padding(0, 0, 10, 0);

    pie.legend()
            .position("center-bottom")
            .itemsLayout(LegendLayout.HORIZONTAL)
            .align(Align.CENTER);

    view.setChart(pie);
}
