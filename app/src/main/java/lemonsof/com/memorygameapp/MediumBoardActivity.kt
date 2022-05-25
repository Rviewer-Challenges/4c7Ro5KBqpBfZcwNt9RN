package lemonsof.com.memorygameapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import lemonsof.com.memorygameapp.GameEngine.CommonSettings.CommonSettings
import lemonsof.com.memorygameapp.GameEngine.Constants.CommonConstants

class MediumBoardActivity : AppCompatActivity() {

    var hisotirialMovimientos: Int = 0
    var movimientos: Int = 0
    var prevMovement: String = ""

    lateinit var counter: CountDownTimer
    lateinit var images: Array<ImageView>
    lateinit var tv_medium_board_movements: TextView
    lateinit var tv_medium_board_missing: TextView

    lateinit var commonConstants: CommonConstants
    lateinit var commonConfig: CommonSettings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medium_board)

        // Obtenemos los recursos y configuraciones para que nuestro juego funcione
        commonConstants = CommonConstants()
        commonConfig = CommonSettings()
        commonConfig.getConfigForMedium()
        commonConfig.getGeneralConfigs()

        val tv_medium_board_time: TextView = findViewById(R.id.tv_medium_board_time)
        val btn_medium_board_retornar: Button = findViewById(R.id.btn_medium_board_retornar)
        tv_medium_board_movements = findViewById(R.id.tv_medium_board_movements)
        tv_medium_board_missing = findViewById(R.id.tv_medium_board_missing)
        tv_medium_board_movements.text = hisotirialMovimientos.toString()
        tv_medium_board_missing.text = commonConfig.restantesMovimientos.toString()

        // Inicializamos el contador del juego
        counter = object : CountDownTimer(commonConfig.timeLength, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                var time = (millisUntilFinished / 1000).toInt()
                tv_medium_board_time.text = if (time >= 10) "00:$time" else "00:0$time"
            }

            override fun onFinish() {
                tv_medium_board_time.text = "00:00"
                disableImages()
            }
        }

        // Inicializamos el contador del preview
        val counterForPreview = object : CountDownTimer(commonConfig.timeLengthForPreview, 1000) {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                assingQustionImagesToElements()
                assingClickEventToImages()
                counter.start()
            }
        }

        btn_medium_board_retornar.setOnClickListener {
            finish()
        }

        images = arrayOf(
            findViewById(R.id.img_medium_board_1),
            findViewById(R.id.img_medium_board_2),
            findViewById(R.id.img_medium_board_3),
            findViewById(R.id.img_medium_board_4),
            findViewById(R.id.img_medium_board_5),
            findViewById(R.id.img_medium_board_6),
            findViewById(R.id.img_medium_board_7),
            findViewById(R.id.img_medium_board_8),
            findViewById(R.id.img_medium_board_9),
            findViewById(R.id.img_medium_board_10),
            findViewById(R.id.img_medium_board_11),
            findViewById(R.id.img_medium_board_12),
            findViewById(R.id.img_medium_board_13),
            findViewById(R.id.img_medium_board_14),
            findViewById(R.id.img_medium_board_15),
            findViewById(R.id.img_medium_board_16),
            findViewById(R.id.img_medium_board_17),
            findViewById(R.id.img_medium_board_18),
            findViewById(R.id.img_medium_board_19),
            findViewById(R.id.img_medium_board_20),
            findViewById(R.id.img_medium_board_21),
            findViewById(R.id.img_medium_board_22),
            findViewById(R.id.img_medium_board_23),
            findViewById(R.id.img_medium_board_24)
        )
        assingPokemonImagesToElements()
        counterForPreview.start()
    }

    private fun assingPokemonImagesToElements() {
        for (i in images.indices) {
            // Agregando imagenes a modo de preview
            images[i].setImageResource(stringFromResourcesByName(commonConfig.gameBoard[commonConfig.cartDistributionInTheGameBoard[i][0]][commonConfig.cartDistributionInTheGameBoard[i][1]]))
        }
    }

    private fun assingQustionImagesToElements() {
        for (i in images.indices) {
            // Agregando imagenes a modo de preview
            images[i].setImageResource(stringFromResourcesByName(commonConfig.defaultImage))
        }
    }

    private fun assingClickEventToImages() {
        for (i in images.indices) {
            // Agregando evento click a cada imagen
            images[i].setOnClickListener {
                // Colocandole una nueva imagen a cada elemento ( para dar el efecto de carta escondida )
                images[i].setImageResource(stringFromResourcesByName(commonConfig.gameBoard[commonConfig.cartDistributionInTheGameBoard[i][0]][commonConfig.cartDistributionInTheGameBoard[i][1]]))
                // Agregandole el nombre de la imagen a cada elemento
                images[i].tag =
                    commonConfig.gameBoard[commonConfig.cartDistributionInTheGameBoard[i][0]][commonConfig.cartDistributionInTheGameBoard[i][1]]
                // Agregando un nuevo movimiento al darle clic a la imagen
                increaseMocements(images[i]);
            }
        }
    }

    private fun increaseMocements(source: ImageView) {
        // Desabilitando el evento click para el elemento recien clikeado para que ya no se pueda volver a clickear la mima imagen mas de una vez
        source.isClickable = false
        // Incrementando la cantidad de movimientos en 1
        movimientos = movimientos + 1
        // Verificando si el jugador ya selecciono una pareja de cartas
        if (movimientos == 2) {
            // Si es asi entonces procedemos a aumentar la cantidad de movimientos o cartas descubiertas
            hisotirialMovimientos++
            // Mostramos el resultado en el correspondiente text view
            tv_medium_board_movements.text = hisotirialMovimientos.toString()
            // Reseteamos los movimientos a cero para volver a buscar una nueva pareja
            movimientos = 0
            // Calculamos la cantidad de movimientos faltantes para el jugador o la cantidad de parejas a encontrar faltantes
            val missing = commonConfig.restantesMovimientos - hisotirialMovimientos
            // Mostramos el resultado en su devido text view
            tv_medium_board_missing.text = (missing).toString()
            // Verificamos si la cantidad de parejas restantes es igual a cero
            if (missing == 0) {
                // Si es asi entonces procedemos a mostrar un mensaje de que ha ganado
                Toast.makeText(this, "Genial, has ganado.", Toast.LENGTH_LONG).show()
                // Detenemos el tiempo
                counter.cancel()
                // Desabilitamos el click para las imagenes restantes que aun no se han descubierto
                disableImages()
            }
            // Verificamos si el movimiento previo es igual al actual
            if (prevMovement != source.tag.toString()) {
                // De no ser asi mostramos un mensaje de que ha perdido la partida
                Toast.makeText(this, "Lo sentimos, has perdido.", Toast.LENGTH_LONG).show()
                // Detenemos el tiempo
                counter.cancel()
                // Desabilitamos el click para las imagenes restantes que aun no se han descubierto
                disableImages()
            }
        } else {
            // Le asigmanos el valor al movimiento previo
            prevMovement = source.tag.toString()
        }
    }

    fun disableImages() {
        for (i in images.indices) {
            images[i].isClickable = false
        }
    }

    fun stringFromResourcesByName(resourceName: String): Int {
        val resourceId = resources.getIdentifier(
            resourceName,
            "drawable",
            packageName
        )
        return resourceId
    }
}