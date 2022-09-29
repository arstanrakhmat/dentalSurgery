package com.example.dentalsurgeon

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent.ACTION_DOWN
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_DOWN
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.example.dentalsurgeon.databinding.ActivityMainBinding
import kotlin.math.abs

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var motionX = 0f
    private var motionY = 0f

    private var motionX2 = 0f
    private var motionY2 = 0f

    var swapped = false

    companion object {
        const val MIN_DISTANCE = 250
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        checkPermissions()

        binding.callBtnFirstScreen.setOnClickListener {
            val phoneNumber = "0771677010"

            val callIntent = Intent(Intent.ACTION_CALL)
            callIntent.data = Uri.parse("tel:$phoneNumber")
            startActivity(callIntent)
        }

        binding.emailBtnFirstScreen.setOnClickListener {
            sendEmail()
        }

        binding.locatoinFirstScreen.setOnClickListener {
            location()
        }
    }

    private fun sendEmail() {
        val email = binding.emailBtnFirstScreen.text.toString()

        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:$email")
        startActivity(intent)
    }

    private fun location() {
        val location =
            "https://www.google.ru/maps/place/%D1%83%D0%BB" +
                    ".+%D0%90%D0%B1%D0%B4%D1%83%D0%BC%D0%BE%D0%BC%D1%83%" +
                    "D0%BD%D0%BE%D0%B2%D0%B0,+%D0%91%D0%B8%D1%88%D0%BA%D0%B5%D0%BA/" +
                    "@42.8794223,74.605022,17z/data=!3m1!4b1!4m5!3m4!1s0x389eb7c212f05b13:0xf36496e9" +
                    "1b8c32d4!8m2!3d42.8794184!4d74.6072107"

        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(location)

        startActivity(intent)
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

    private fun switchToSecAct() {
        val intent = Intent(this, SecondActivity::class.java)
        startActivity(intent)
    }

    private fun switchToThirdAct() {
        val intent = Intent(this, ThirdActivity::class.java)
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

                    if (abs(valueX) > MIN_DISTANCE) {
                        if (motionX < motionX2) {
                            switchToThirdAct()
                        } else {
                            switchToSecAct()
                        }
                    }
                }
            }
        }
        return super.onTouchEvent(event)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        this.onTouchEvent(ev)
        return super.dispatchTouchEvent(ev)
    }
}