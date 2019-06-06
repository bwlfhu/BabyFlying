package com.allcam.babyflying.ui

import android.app.Service
import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.support.v4.content.ContextCompat.getSystemService
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import com.allcam.babyflying.R


/**
 * TODO 一句话描述
 * Created by Beowulf on 2019-06-06.
 */
class BabyRecordFloatView(private val content: Context) {

    private val windowManager by lazy { content.getSystemService(Service.WINDOW_SERVICE) as WindowManager }
    private val layoutParams by lazy { WindowManager.LayoutParams() }
    private val rootView by lazy { View.inflate(content, R.layout.view_baby_record_float, null) }

    fun floatWindow()
    {
        // 设置LayoutParam
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE
        }
        layoutParams.format = PixelFormat.RGBA_8888
//            layoutParams.width = 100
//            layoutParams.height = 100
        layoutParams.x = 300
        layoutParams.y = 300

        rootView.findViewById<View>(R.id.btn_record).setOnTouchListener(FloatingOnTouchListener())

        // 将悬浮窗控件添加到WindowManager
        windowManager.addView(rootView, layoutParams)
        Log.d("BabyRecordService", "create float window.")
    }

    inner class FloatingOnTouchListener : View.OnTouchListener {
        private var x: Int = 0
        private var y: Int = 0

        override fun onTouch(view: View, event: MotionEvent): Boolean {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    x = event.rawX.toInt()
                    y = event.rawY.toInt()
                }

                MotionEvent.ACTION_MOVE -> {
                    val nowX = event.rawX.toInt()
                    val nowY = event.rawY.toInt()
                    val movedX = nowX - x
                    val movedY = nowY - y
                    x = nowX
                    y = nowY

                    layoutParams.x = layoutParams.x + movedX
                    layoutParams.y = layoutParams.y + movedY

                    // 更新悬浮窗控件布局
                    windowManager.updateViewLayout(view, layoutParams)
                }
            }
            return view.performClick()
        }
    }
}