package com.kmvdata.kotlin.demoflickrmvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kmvdata.kotlin.demoflickrmvvm.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.mainContainer, MainFragment.newInstance())
                    .commitNow()
        }
    }
}