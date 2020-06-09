package ru.ikbfu.rectorapp.ui.fragments.science

import android.graphics.Color
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.Chart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IDataSet
import kotlinx.android.synthetic.main.science_item.view.*
import ru.ikbfu.rectorapp.R
import ru.ikbfu.rectorapp.model.data.server.model.ChartType
import ru.ikbfu.rectorapp.model.data.server.model.ScienceItem
import ru.ikbfu.rectorapp.utils.delegate.DelegateAdapter

class ScienceAdapter : DelegateAdapter<ScienceItem>() {

    override fun getLayoutId(): Int = R.layout.science_item

    override fun isForViewType(items: List<*>, position: Int): Boolean =
        items[position] is ScienceItem

    override fun onBind(item: ScienceItem, holder: DelegateViewHolder) = with(holder.itemView) {
        title_text.text = item.title
        desc_text.text = item.desc
        chart_container.removeAllViewsInLayout()
        when (item.chartType) {
            ChartType.PIE -> {
                val pieView = PieChart(this.context)
                setupChart(pieView, item)

                chart_container.addView(pieView)
                pieView.invalidate()
            }
            ChartType.BAR -> {
                val barView = BarChart(this.context)
                setupChart(barView, item)
                chart_container.addView(barView)
                barView.invalidate()
            }
        }
    }

    private fun <V : ChartData<out IDataSet<out Entry>>> setupChart(
        chart: Chart<V>,
        item: ScienceItem
    ) {
        chart.description = null

        when (chart) {
            is PieChart -> {
                chart.isDrawHoleEnabled = false

                val pieDataSet = PieDataSet(
                    item.pieData?.map { PieEntry(it.value, it.label) }, ""
                )
                pieDataSet.sliceSpace = 5f
                pieDataSet.valueTextSize = 15f
                pieDataSet.valueTextColor = Color.BLACK
                chart.setEntryLabelColor(Color.BLACK)
                pieDataSet.colors = item.pieData?.map { Color.parseColor(it.color) }
                val pieData = PieData(pieDataSet)

                chart.data = pieData

            }

            is BarChart -> {
                var barData: BarData? = null

                item.barData?.let { barDataEntity ->
                    barDataEntity.forEach { dataType ->
                        val entities: MutableList<BarEntry> = mutableListOf()
                        for (i: Int in 1..dataType.barData.size)
                            entities.add(
                                BarEntry(
                                    i.toFloat(),
                                    dataType.barData[i - 1].value.toFloat()
                                )
                            )

                        val barDataSet = BarDataSet(entities, dataType.dataType)
                        barDataSet.color = Color.parseColor(dataType.color)
                        if (barData == null)
                            barData = BarData(barDataSet)
                        else barData?.addDataSet(barDataSet)
                    }
                }

                chart.data = barData
                chart.data.barWidth = 0.12f

                val xAxis = chart.xAxis
                val groupLabels = item.barData!!.first().barData.map { it.year }
                xAxis.valueFormatter = IndexAxisValueFormatter(groupLabels)
                xAxis.setCenterAxisLabels(true)
                xAxis.position = XAxis.XAxisPosition.BOTTOM
                xAxis.granularity = 1f
                xAxis.isGranularityEnabled = true
                xAxis.axisMinimum = 0f

                val barSpace = 0.03f
                val groupSpace =
                    1f - item.barData!!.size * (chart.data.barWidth + barSpace)  //https://drive.google.com/file/d/1zMLDYuwB_0130e-rutFC4briBSHus3cN/view

                xAxis.axisMaximum =
                    chart.barData.getGroupWidth(groupSpace, barSpace) * groupLabels.size

                chart.axisLeft.axisMinimum = 0f
                chart.axisRight.axisMinimum = 0f
                chart.groupBars(0f, groupSpace, barSpace)
                chart.setTouchEnabled(false)
                chart.setOnChartValueSelectedListener(null)
            }

        }
        chart.layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)
    }

}