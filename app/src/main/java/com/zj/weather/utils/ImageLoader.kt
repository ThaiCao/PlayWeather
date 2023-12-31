package com.zj.weather.utils

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

/**
 * 图片加载工具
 *
 * @param data 图片数据 可以为图片网址、本地地址或资源id
 * @param modifier 修饰符
 * @param contentScale 使用可选的scale参数来确定要使用的纵横比缩放
 */
@Composable
fun ImageLoader(
    data: Any?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
) {
    when (data) {
        is String -> {
            val bitmap = BitmapFactory.decodeFile(data)
            Image(
                modifier = modifier,
                painter = BitmapPainter(bitmap.asImageBitmap()),
                contentDescription = "",
                contentScale = contentScale
            )
        }
        is Int -> {
            Image(
                modifier = modifier,
                painter = painterResource(data),
                contentDescription = "",
                contentScale = contentScale
            )
        }
        else -> {
            throw IllegalArgumentException("参数类型不符合要求，只能是：url、文件路径或者是 drawable id")
        }
    }
}