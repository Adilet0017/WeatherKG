package kg.adikyrgyz.weather.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kg.adikyrgyz.weather.network.model.WeatherByCity
import kg.adikyrgyz.weather.network.model.forecast.TenDayWeather
import kg.adikyrgyz.weather.repository.WeatherRepository
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class WeatherViewModel : ViewModel() {

    private val disposable = CompositeDisposable()

    val weather: MutableLiveData<WeatherByCity> = MutableLiveData()
    val forecast: MutableLiveData<TenDayWeather> = MutableLiveData()

    private val repository : WeatherRepository = WeatherRepository()

    fun fetchWeatherByCity(cityName: String) {
        viewModelScope.launch {
            try {
                val response: WeatherByCity = repository.fetchWeatherByCity(cityName)
                weather.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun fetchTenDayWeather(cityName: String) {
        disposable.add(
            repository.fetchTenDayWeather(cityName)
                .debounce(1L, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    forecast.value = it
                },{

                })
        )
    }

    fun metresToKilometres(metres: Int): Int = metres / 1000

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }

}