package kg.adikyrgyz.weather.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import kg.adikyrgyz.weather.R
import kg.adikyrgyz.weather.databinding.FragmentWeatherBinding
import kg.adikyrgyz.weather.ui.tenday.TenDayAdapter
import kg.adikyrgyz.weather.ui.tenday.TenDayFragment

class WeatherFragment : Fragment() {

    private lateinit var binding: FragmentWeatherBinding
    private lateinit var viewModel: WeatherViewModel

    private val adapter = TenDayAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeatherBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)
        binding.tenDayRv.adapter = adapter
        viewModel.fetchWeatherByCity("Madrid")

        subscribeWeather()
        setupSearch()
        setupViews()

        binding.tenDayBtn.setOnClickListener {
            requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_fragment_container, TenDayFragment.newInstance())
                .addToBackStack(null)
                .commit()
        }
    }

    private fun loadWeatherIcon(imageUrl: String, imageView: ImageView) {
        Picasso.get().load("http://openweathermap.org/img/wn/$imageUrl@2x.png").into(imageView)
    }

    private fun setupViews() {
        binding.cityEt.doOnTextChanged { text, _, _, _ ->
            if (text?.toString()?.trim()?.isNotEmpty() == true && text.length > 3) {
                viewModel.fetchTenDayWeather(text.toString())
            }
        }

    }

    private fun subscribeWeather() {
        viewModel.weather.observe(viewLifecycleOwner, Observer { weather ->
            binding.tempTv.text = getString(R.string.temp, weather.main.temp.toString())
            binding.tempFeelsLike.text =
                getString(R.string.temp_feels_like, weather.main.feelsLike.toString())
            binding.cityTv.text = weather.name
            binding.weatherDescriptionTv.text = weather.weather[0].description
            binding.windTv.text =
                getString(R.string.wind_with_symbol, weather.wind.speed.toString())
            binding.humidityTv.text =
                getString(R.string.humidity_with_symbol, weather.main.humidity.toString())

            loadWeatherIcon(weather.weather[0].icon, binding.weatherIv)

            val visibilityInKM: Int = viewModel.metresToKilometres(weather.visibility)
            binding.visibilityTv.text =
                getString(R.string.visibility_with_symbol, visibilityInKM.toString())
        })

        viewModel.forecast.observe(viewLifecycleOwner, Observer {
            it.let {
                adapter.setItems(it.list)
            }
        })
    }

    private fun setupSearch() {
        binding.searchBtn.setOnClickListener {
            val cityFromInput: String = binding.cityEt.text.toString()
            viewModel.fetchWeatherByCity(cityFromInput)
        }
    }

}