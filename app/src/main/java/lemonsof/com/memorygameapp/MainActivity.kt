package lemonsof.com.memorygameapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        startActivity(intent)

        val btn_activity_main_facil: Button = findViewById(R.id.btn_activity_main_facil)
        val btn_activity_main_medio: Button = findViewById(R.id.btn_activity_main_medio)
        val btn_activity_main_dificil: Button = findViewById(R.id.btn_activity_main_dificil)

        btn_activity_main_facil.setOnClickListener {
            val intent = Intent(this, EasyBoardActivity::class.java).apply { }
            startActivity(intent)
        }
        btn_activity_main_medio.setOnClickListener {
            val intent = Intent(this, MediumBoardActivity::class.java).apply { }
            startActivity(intent)
        }
        btn_activity_main_dificil.setOnClickListener {
            val intent = Intent(this, HardBoardActivity::class.java).apply { }
            startActivity(intent)
        }

    }
}