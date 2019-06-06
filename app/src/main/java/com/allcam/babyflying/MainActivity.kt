package com.allcam.babyflying

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.allcam.babyflying.baby.BabyAddActivity
import com.allcam.babyflying.utils.Preference
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var babyName: String by Preference(this, "babyName", "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (babyName.isEmpty()) {
            val intent = Intent(this, BabyAddActivity::class.java)
            startActivity(intent)
        }


    }

    override fun onResume() {
        super.onResume()

        babyTv.text = babyName
        babyTv.setOnClickListener {
            val intent = Intent(this, BabyAddActivity::class.java)
            startActivity(intent)
        }
    }

}
