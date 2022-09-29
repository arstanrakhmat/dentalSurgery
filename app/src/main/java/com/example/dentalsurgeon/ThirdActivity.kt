package com.example.dentalsurgeon

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import kotlin.math.abs

class ThirdActivity : AppCompatActivity() {

    private var motionX = 0f
    private var motionY = 0f

    private var motionX2 = 0f
    private var motionY2 = 0f

//    private lateinit var callBtn: Button
//    private lateinit var emailBtn: Button

    var swapped = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.busines_card3)

//        checkPermissions()

//        callBtn = findViewById(R.id.callThirdScreen)
//        emailBtn = findViewById(R.id.emailThirdScreen)
//
//        callBtn.setOnClickListener {
//            val phoneNumber = "0771677010"
//
//            val callIntent = Intent(Intent.ACTION_CALL)
//            callIntent.data = Uri.parse("tel:$phoneNumber")
//            startActivity(callIntent)
//        }
//
//        emailBtn.setOnClickListener {
//            sendEmail()
//        }
    }

//    private fun sendEmail() {
//        val email = emailBtn.text.toString()
//
//        val intent = Intent(Intent.ACTION_SENDTO)
//        intent.data = Uri.parse("mailto:$email")
//        startActivity(intent)
//    }

    private fun switchToFirstAct() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun switchToSecAct() {
        val intent = Intent(this, SecondActivity::class.java)
        startActivity(intent)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (swapped) {
            swapped = false
            return super.onTouchEvent(event)
        }

        if (event != null) {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    motionX = event.x
                    motionY = event.y
                }

                MotionEvent.ACTION_UP -> {
                    motionX2 = event.x
                    motionY2 = event.y

                    val valueX = motionX2 - motionX
                    val valueY = motionY2 - motionY

                    if (abs(valueX) > MainActivity.MIN_DISTANCE) {
                        if (motionX < motionX2) {
                            switchToSecAct()
                        } else {
                            switchToFirstAct()
                        }
                    }
                }
            }
        }
        return false
    }

    private fun checkPermissions() {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.CALL_PHONE),
                101
            )
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        this.onTouchEvent(ev)
        return super.dispatchTouchEvent(ev)
    }
}