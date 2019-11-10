package com.example.syftreposearchapp.utils

import android.os.SystemClock



class Stopwatch {

        private var startThreadMillis: Long
        private var startRealtimeMillis: Long
        private var startUptimeMillis: Long

    init {
        startThreadMillis = SystemClock.currentThreadTimeMillis()
        startRealtimeMillis = SystemClock.elapsedRealtime()
        startUptimeMillis = SystemClock.uptimeMillis()
    }


        class ElapsedTime{
            var elapsedThreadMillis: Long
            var elapsedRealtimeMillis: Long
            var elapsedUptimeMillis: Long


            constructor(stopwatch: Stopwatch) {
                elapsedThreadMillis =
                    SystemClock.currentThreadTimeMillis() - stopwatch.startThreadMillis
                elapsedRealtimeMillis =
                    SystemClock.elapsedRealtime() - stopwatch.startRealtimeMillis
                elapsedUptimeMillis = SystemClock.uptimeMillis() - stopwatch.startUptimeMillis
            }

            override  fun toString(): String {
                return ("realtime: " + elapsedRealtimeMillis
                        + " ms; uptime: " + elapsedUptimeMillis
                        + " ms; thread: " + elapsedThreadMillis + " ms")
            }
        }

    fun getElapsedTime(): ElapsedTime {
        return ElapsedTime(this)
    }

    fun elapsedTimeString(): String{
                val seconds = getElapsedTime().elapsedRealtimeMillis.toDouble() / 1000.0
                return if (seconds < 1.0) {
                    String.format("%.0f ms", seconds * 1000)
                } else {
                    String.format("%.2f s", seconds)
                }
     }

     override fun toString(): String {
         return "Stopwatch: ${elapsedTimeString()}"
     }
}
