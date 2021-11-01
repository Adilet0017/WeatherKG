package kg.adikyrgyz.weather.repository

import io.reactivex.Observable
import kg.adikyrgyz.weather.network.NetworkHelper
import kg.adikyrgyz.weather.network.model.WeatherByCity
import kg.adikyrgyz.weather.network.model.forecast.TenDayWeather

class WeatherRepository {

    suspend fun fetchWeatherByCity(cityName: String): WeatherByCity =
        NetworkHelper.getWeatherService().fetchWeatherByCityName(cityName)

    fun fetchTenDayWeather(cityName: String): Observable<TenDayWeather> {
        return NetworkHelper.getWeatherService().fetchTenDayWeather(cityName)
    }
}
