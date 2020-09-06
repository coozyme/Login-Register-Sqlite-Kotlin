package apps.programmeranak.loreg

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import apps.programmeranak.loreg.activity.RegisterActivity

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val mhandler = Handler()

        mhandler.postDelayed(
            {
                val pindah = Intent(this, RegisterActivity::class.java)
                startActivity(pindah)
                finish()
            },2000
        )

    }
}