package com.aaa.springanimation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.aaa.springanimation.databinding.ActivityMainBinding
import com.aaa.springanimation.viewmodel.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    /** overview spring Animation */

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        mainActivityViewModel.setUpAnimation(view = binding.floatingActionButton, callback = {
            /** This will executed when the view is just clicked */
            Toast.makeText(this, "View is clicked", Toast.LENGTH_SHORT).show()
        })


    }
}