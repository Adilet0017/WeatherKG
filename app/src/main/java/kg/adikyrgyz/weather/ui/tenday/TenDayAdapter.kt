package kg.adikyrgyz.weather.ui.tenday

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kg.adikyrgyz.weather.databinding.ItemTenDayBinding
import kg.adikyrgyz.weather.network.model.forecast.Forecast
import java.text.SimpleDateFormat
import java.util.*

class TenDayAdapter() : RecyclerView.Adapter<TenDayAdapter.TenDayViewHolder>() {

    private var items: List<Forecast> = emptyList()


    @SuppressLint("NotifyDataSetChanged")
    fun setItems(forecast: List<Forecast>) {
        items = forecast.take(9)
        notifyDataSetChanged()
    }

    private fun getWeekDay(): List<String> {
        val sdf = SimpleDateFormat("EEEE", Locale.US)
        val weekDays = mutableListOf<String>()
        for (i in 1..9) {
            val calendar: Calendar = GregorianCalendar()
            calendar.add(Calendar.DATE, i)
            val day = sdf.format(calendar.time)
            weekDays.add(day)
        }
        return weekDays
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TenDayViewHolder {
        val inflater =  LayoutInflater.from(parent.context)
        return TenDayViewHolder(ItemTenDayBinding.inflate(inflater, parent, false))
    }
    override fun getItemCount(): Int {
        return items.size
    }
    override fun onBindViewHolder(holder: TenDayViewHolder, position: Int) {
        holder.bind(items[position], getWeekDay()[position])
    }

    class TenDayViewHolder(private val binding: ItemTenDayBinding) : RecyclerView.ViewHolder(binding.root) {
         fun bind(forecast: Forecast, weekDay: String) {
             binding.weekDayTv.text = weekDay
             binding.maxTemTv.text = forecast.main.tempMax.toString()
             binding.minTempTv.text = forecast.main.tempMin.toString()
         }
    }
}