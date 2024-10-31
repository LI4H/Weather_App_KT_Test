package com.exersize.weather_appkt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import com.exersize.weather_appkt.ui.screen.util.ForecastItem

class WeatherViewModel : ViewModel() {
    private val apiKey = "24378a7498a723a65b0863eabc68ff05"
    private val _weatherData = MutableStateFlow("")
    val weatherData: StateFlow<String> = _weatherData
    private val _cityName = MutableStateFlow("Unknown")
    val cityName: StateFlow<String> = _cityName
    private val _forecastItems = MutableStateFlow<List<ForecastItem>>(emptyList())
    val forecastItems: StateFlow<List<ForecastItem>> = _forecastItems

    fun fetchWeatherData(lat: Double, lon: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            val weatherUrl = "https://api.openweathermap.org/data/2.5/weather?lat=$lat&lon=$lon&appid=$apiKey&units=metric&lang=ru"
            val result = getWeatherData(weatherUrl)
            result?.let {
                val jsonObject = JSONObject(it)
                val city = jsonObject.getString("name")
                _weatherData.value = it
                _cityName.value = city
                _forecastItems.value = parseForecastData(it)
            }
        }
    }

    private suspend fun getWeatherData(urlString: String): String? {
        return try {
            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection
            connection.connect()
            val stream = connection.inputStream
            val reader = BufferedReader(InputStreamReader(stream))
            val buffer = StringBuilder()
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                buffer.append(line).append("\n")
            }
            reader.close()
            buffer.toString()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun parseForecastData(data: String): List<ForecastItem> {
        // Здесь нужно добавить логику для парсинга данных прогноза и возвращения списка объектов ForecastItem
        return listOf(
            ForecastItem(R.drawable.ic_launcher_foreground, "Mon", "13 Feb", "26°", "194", "#ff7676"),
            ForecastItem(R.drawable.ic_launcher_foreground, "Tue", "14 Feb", "18°", "160", "#ff7676", true),
            ForecastItem(R.drawable.ic_launcher_foreground, "Wed", "15 Feb", "16°", "40", "#2dbe8d"),
            ForecastItem(R.drawable.ic_launcher_foreground, "Thu", "16 Feb", "20°", "58", "#f9cf5f"),
            ForecastItem(R.drawable.ic_launcher_foreground, "Fri", "17 Feb", "34°", "121", "#ff7676"),
            ForecastItem(R.drawable.ic_launcher_foreground, "Sat", "18 Feb", "28°", "73", "#f9cf5f"),
            ForecastItem(R.drawable.ic_launcher_foreground, "Sun", "19 Feb", "24°", "15", "#2dbe8d")
        )
    }
}
