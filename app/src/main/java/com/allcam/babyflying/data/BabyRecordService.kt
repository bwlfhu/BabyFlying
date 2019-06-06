package com.allcam.babyflying.data

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.provider.Settings
import com.allcam.babyflying.ui.BabyRecordFloatView


/**
 * TODO 一句话描述
 * Created by Beowulf on 2019-06-06.
 */
class BabyRecordService : Service() {


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        showFloatingWindow()
        return super.onStartCommand(intent, flags, startId)
    }

    private fun showFloatingWindow() {
        if (Settings.canDrawOverlays(this)) {
            // 新建悬浮窗控件
            val button = BabyRecordFloatView(applicationContext)
            button.floatWindow()
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}