package by.aermakova.fancysquare

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

/*        findViewById<AnimationPane>(R.id.pane).setOnClickListener {
            (it as AnimationPane).startAnimation(
                0f,
                500f
            )
        }*/
    }
}