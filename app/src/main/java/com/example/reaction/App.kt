package com.example.reaction

import android.app.Application
import com.example.reaction.data.MainDb


class App : Application() {
    val database by lazy { MainDb.createDataBase(this) }
}