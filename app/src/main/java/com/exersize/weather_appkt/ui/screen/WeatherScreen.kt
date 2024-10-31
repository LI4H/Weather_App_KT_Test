package com.exersize.weather_appkt.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.exersize.weather_appkt.WeatherViewModel
import com.exersize.weather_appkt.ui.screen.components.*
import com.exersize.weather_appkt.ui.theme.ColorBackground

@Composable
fun WeatherScreen(weatherViewModel: WeatherViewModel) {
    val weatherInfo by weatherViewModel.weatherData.collectAsState()
    val forecastItems by weatherViewModel.forecastItems.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = ColorBackground
    ) { paddings ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(paddings)
                .padding(horizontal = 24.dp, vertical = 10.dp)
        ) {
            ActionBar(weatherViewModel = weatherViewModel)
            Spacer(modifier = Modifier.height(12.dp))
            DailyForecast()  // Добавляем дневной прогноз
            Spacer(modifier = Modifier.height(24.dp))
            AirQuality()  // Добавляем информацию о качестве воздуха
            Spacer(modifier = Modifier.height(24.dp))
            WeeklyForecast(data = forecastItems)  // Добавляем прогноз на неделю
            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "Weather Info:", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = weatherInfo)
        }
    }
}
