package ru.netology.nmedia.dto

import kotlin.math.floor

object WallService {
    fun countAmountFormat(num: Int): String {
        val divideNumToThousand = num.toDouble() / 1000
        val divideNumToThousandInt = num / 1000
        val divideNumToMillion = num.toDouble() / 1_000_000;
        val divideNumToMillionInt = num / 1_000_000;
        val z = 0.1 * floor(10 * divideNumToThousand)
        val zMil = 0.1 * floor(10 * divideNumToMillion)
        val s = String.format("%.1f", z)
        val sMil = String.format("%.1f", zMil)

        val result = when (num) {
            in 0..999 -> num.toString()
            in 1000..9999 -> "${s}K"
            in 10_000..999999 -> "$divideNumToThousandInt" + "K"
            in 1_000_000..9999999 -> "${sMil}M"
            else -> "$divideNumToMillionInt" + "M"
        }
        return result
    }
}