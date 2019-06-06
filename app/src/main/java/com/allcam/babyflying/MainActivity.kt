package com.allcam.babyflying

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.provider.Settings.ACTION_MANAGE_OVERLAY_PERMISSION
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.allcam.babyflying.baby.BabyAddActivity
import com.allcam.babyflying.data.BabyRecordService
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

        if (babyName.isNotBlank()) {
            startFloatingService()
        }
    }

    private fun startFloatingService() {
        if (!Settings.canDrawOverlays(this)) {
            startActivityForResult(
                Intent(ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName")),
                0
            )
        } else {
            startService(Intent(this, BabyRecordService::class.java))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 0) {
            if (!Settings.canDrawOverlays(this)) {
                Toast.makeText(this, "授权失败", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "授权成功", Toast.LENGTH_SHORT).show()
                startService(Intent(this, BabyRecordService::class.java))
            }
        }
    }

}
