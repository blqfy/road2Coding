package me.tips

import kotlin.system.measureTimeMillis

internal object BenefitFromSIMD {

    /**
     * 使用 jdk-18 执行结果：
     * compute one by one takes 4359 ms
     * compute by vector takes 565 ms
     */
    @JvmStatic
    fun main(args: Array<String>) {
        val size = 512
        val values = Array(size) {
            intArrayOf(it, it * 99, 0)
        }
        val a = IntArray(size) { it }
        val b = IntArray(size) { it * 99 }
        val result = IntArray(size) { 0 }

        measureTimeMillis {
            repeat(2000_0000) {
                computeOneByOne(values)
            }
        }.also { println("compute one by one takes $it ms") }

        measureTimeMillis {
            repeat(2000_0000) {
                computeByVector(size, a, b, result)
            }
        }.also { println("compute by vector takes $it ms") }
    }

    private fun computeOneByOne(values: Array<IntArray>) {
        for (value in values) {
            value[2] = value[0] + value[1]
        }
    }

    /**
     * 这种写法会使得 java 编译器使用 SIMD 的技术，也就是向量指令
     * 也称 自动矢量化（auto vectorization）
     */
    private fun computeByVector(size: Int, a: IntArray, b: IntArray, result: IntArray) {
        for (i in 0 until size) {
            result[i] = a[i] + b[i]
        }
    }
}