package com.example.dice

import android.content.Context
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.appcompat.app.AppCompatActivity
import com.squareup.seismic.ShakeDetector
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random


class MainActivity : AppCompatActivity(), ShakeDetector.Listener {
    private var sensorManager: SensorManager? = null
    private var shakeDetector: ShakeDetector? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        button.setOnClickListener {
          showDice()
        }



        shakeDetector = ShakeDetector(this)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    private fun vibrate() {
        val vibrator = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            vibrator.vibrate(
                VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE)
            )
        } else {
            vibrator.vibrate(500)
        }
    }

    override fun onResume() {
        super.onResume()
        if (sensorManager != null) {
            shakeDetector!!.start(sensorManager)
        }
    }
    override fun onPause() {
        shakeDetector!!.stop()
        super.onPause()
    }
    override fun hearShake() {

        runOnUiThread {
            if (isFinishing) {
                return@runOnUiThread
            }
            showDice()

            vibrate()
        }
    }

    fun showDice() {
        val randNum =  Random.nextInt(1, 6)
        val dieToShow= when(randNum) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            6 -> R.drawable.dice_6
            else -> 0
        }
        dice_view.setImageResource(dieToShow)
    }

}