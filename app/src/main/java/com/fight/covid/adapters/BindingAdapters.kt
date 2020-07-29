package com.fight.covid.adapters

import androidx.databinding.BindingAdapter
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.Cartesian
import com.anychart.charts.Pie
import com.anychart.core.cartesian.series.Line
import com.anychart.data.Mapping
import com.anychart.enums.*
import com.anychart.data.Set;
import com.anychart.graphics.vector.Stroke
import com.fight.covid.model.Countries
import com.fight.covid.model.Timeseries


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
@BindingAdapter("makeTimeLine")
fun maketimeLine(view:AnyChartView, resp:Timeseries){
    val cartesian: Cartesian = AnyChart.line()

    cartesian.animation(true)

    cartesian.padding(10.0, 20.0, 5.0, 20.0)

    cartesian.crosshair().enabled(true)
    cartesian.crosshair()
            .yLabel(true) // TODO ystroke
            .yStroke(null as Stroke?, null, null, null as String?, null as String?)

    cartesian.tooltip().positionMode(TooltipPositionMode.POINT)

    cartesian.title("Covid Cases Timeline")

    /*cartesian.yAxis(0).title("Covid Cases Count")
    cartesian.xAxis(0).labels().padding(5.0, 5.0, 5.0, 5.0)*/

    val seriesData: MutableList<DataEntry> = ArrayList()
    for((index,date) in resp.dates.withIndex()){
        seriesData.add(CustomDataEntry(date,resp.confirmed.get(index),resp.recovered.get(index),resp.deaths.get(index)))
    }
    val set: Set= Set.instantiate()
    set.data(seriesData)
    val series1Mapping: Mapping = set.mapAs("{ x: 'x', value: 'value' }")
    val series2Mapping: Mapping = set.mapAs("{ x: 'x', value: 'value2' }")
    val series3Mapping: Mapping = set.mapAs("{ x: 'x', value: 'value3' }")

    val series1: Line = cartesian.line(series1Mapping)
    series1.name("Confirmed")
    series1.hovered().markers().enabled(true)
    series1.hovered().markers()
            .type(MarkerType.CIRCLE)
            .size(4.0)
    series1.tooltip()
            .position("right")
            .anchor(Anchor.LEFT_CENTER)
            .offsetX(5.0)
            .offsetY(5.0)

    val series2: Line = cartesian.line(series2Mapping)
    series2.name("Recovered")
    series2.hovered().markers().enabled(true)
    series2.hovered().markers()
            .type(MarkerType.CIRCLE)
            .size(4.0)
    series2.tooltip()
            .position("right")
            .anchor(Anchor.LEFT_CENTER)
            .offsetX(5.0)
            .offsetY(5.0)

    val series3: Line = cartesian.line(series3Mapping)
    series3.name("Deaths")
    series3.hovered().markers().enabled(true)
    series3.hovered().markers()
            .type(MarkerType.CIRCLE)
            .size(4.0)
    series3.tooltip()
            .position("right")
            .anchor(Anchor.LEFT_CENTER)
            .offsetX(5.0)
            .offsetY(5.0)

    cartesian.legend().enabled(true)
    cartesian.legend().fontSize(11.0)
    cartesian.legend().padding(0.0, 0.0, 10.0, 0.0)

    view.setChart(cartesian)
}

private class CustomDataEntry internal constructor(x: String?, value: Number?, value2: Number?, value3: Number?) : ValueDataEntry(x, value) {
    init {
        setValue("value2", value2)
        setValue("value3", value3)
    }
}
