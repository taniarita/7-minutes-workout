package com.example.a7minutesworkout

import android.app.Application

class WorkoutApp : Application() {

    val db: HistoryDataBase by lazy{
        HistoryDataBase.getInstance(this)
    }

}