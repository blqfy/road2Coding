package me.tips

import kotlin.system.measureTimeMillis

internal object HarmOfAutoboxing {
    private fun getAny(mul: Int): Any {
        return 999999999 * mul
    }

    private fun getInt(mul: Int): Int {
        return 999999999 * mul
    }

    /**
     * 使用 jdk-11.0.16 执行结果：
     * Function getInt takes 538 ms
     * Function getAny takes 3493 ms
     *
     * 使用 jdk-18 执行结果：
     * Function getInt takes 532 ms
     * Function getAny takes 534 ms
     * 猜测 jdk-11 后续的版本进行了相关优化
     */
    @JvmStatic
    fun main(args: Array<String>) {
        measureTimeMillis {
            var result = 0L
            repeat(21_0000_0000) {
                result += getInt(it)
            }
        }.also { println("Function getInt takes $it ms") }

        measureTimeMillis {
            var result = 0L
            repeat(21_0000_0000) {
                result += getAny(it) as Int
            }
        }.also { println("Function getAny takes $it ms") }
    }
}