package com.example.dice

import android.content.Context
import android.hardware.SensorManager
import android.os.Bundle
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
            textView.text = Random.nextInt(1, 6).toString()
        }

        shakeDetector = ShakeDetector(this)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

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
            textView.text = Random.nextInt(1, 6).toString()
        }
    }
}