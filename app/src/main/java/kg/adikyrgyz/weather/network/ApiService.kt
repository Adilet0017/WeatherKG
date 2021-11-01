package kg.adikyrgyz.weather.network

import io.reactivex.Observable
import kg.adikyrgyz.weather.network.model.WeatherByCity
import kg.adikyrgyz.weather.network.model.forecast.TenDayWeather
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("weather")
    suspend fun fetchWeatherByCityName(
        @Query("q") cityName: String,
        @Query("appid") appId: String = "10a89fd6469dd1a5107cf5ad82dd5b1b",
        @Query("units") units: String = "metric"
    ): WeatherByCity

    @GET("forecast")
    fun fetchTenDayWeather(
        @Query("q") cityName: String,
        @Query("appid") appId: String = "10a89fd6469dd1a5107cf5ad82dd5b1b",
        @Query("units") units: String = "metric"
    ): Observable<TenDayWeather>
}