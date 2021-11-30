package com.zj.weather.common.widget

import android.content.Context
import com.google.gson.Gson
import com.qweather.sdk.bean.base.Code
import com.qweather.sdk.bean.base.Unit
import com.qweather.sdk.bean.weather.WeatherDailyBean
import com.qweather.sdk.view.QWeather
import com.zj.weather.room.entity.CityInfo
import com.zj.weather.ui.view.weather.getLocation
import com.zj.weather.utils.XLog
import com.zj.weather.utils.getDefaultLocale
import com.zj.weather.utils.showToast

object WeatherWidgetUtils {

    fun getWeather7Day(
        context: Context,
        cityInfo: CityInfo?,
        onSuccessListener: (MutableList<WeekWeather>) -> kotlin.Unit
    ) {
        QWeather.getWeather7D(context, getLocation(cityInfo = cityInfo),
            getDefaultLocale(context), Unit.METRIC,
            object : QWeather.OnResultWeatherDailyListener {
                override fun onError(e: Throwable) {
                    XLog.e("getWeather7Day1 onError: $e")
                    showToast(context, e.message)
                }

                override fun onSuccess(weatherDailyBean: WeatherDailyBean?) {
                    XLog.d(
                        "getWeather7Day1 onSuccess: " + Gson().toJson(weatherDailyBean)
                    )
                    //先判断返回的status是否正确，当status正确时获取数据，若status不正确，可查看status对应的Code值找到原因
                    if (Code.OK === weatherDailyBean?.code) {
                        val items = arrayListOf<WeekWeather>()
                        weatherDailyBean.daily.forEach { weather ->
                            val weekWeather =
                                WeekWeather(
                                    weather.textDay,
                                    weather.fxDate,
                                    weather.iconDay,
                                    weather.tempMax,
                                    weather.tempMin
                                )
                            items.add(weekWeather)
                        }
                        onSuccessListener(items)
                    } else {
                        //在此查看返回数据失败的原因
                        val code: Code? = weatherDailyBean?.code
                        XLog.w("getWeather7Day1 failed code: $code")
                    }
                }
            })
    }

}