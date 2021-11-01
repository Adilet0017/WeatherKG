package kg.adikyrgyz.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kg.adikyrgyz.weather.ui.home.WeatherFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.main_fragment_container, WeatherFragment())
            .commit()
    }
}