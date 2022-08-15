package com.example.myapplication.activities


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.fragments.MainFragment
import com.example.myapplication.R


class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showMainFragment()
    }

    private fun showMainFragment(){
        val mainFragment = MainFragment()

        supportFragmentManager.beginTransaction().apply {
            add(R.id.fragmentContainer, mainFragment)
            commit()
        }
    }

}

