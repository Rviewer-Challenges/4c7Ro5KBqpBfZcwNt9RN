package lemonsof.com.memorygameapp.GameEngine.CommonSettings

import lemonsof.com.memorygameapp.GameEngine.Constants.CommonConstants

class CommonSettings {

    var gameBoardX = 0
    var gameBoardY = 0
    var posibleItems = 0
    var timeLength: Long = 0
    var timeLengthForPreview: Long = 0
    var defaultImage: String = ""
    var restantesMovimientos: Int = 0

    lateinit var listOfRandomNumbers: MutableList<Int?>
    lateinit var listOfRandomNumbersIterator : MutableIterator<Int?>
    lateinit var cartLocationInTheBoard: Array<Array<Any>>
    lateinit var cartDistributionInTheGameBoard: Array<Array<Int>>
    lateinit var gameBoard: Array<Array<String>>

    fun getConfigForEasy() {
        gameBoardX = 4
        gameBoardY = 4
        posibleItems = gameBoardX * gameBoardY
        timeLength = 60000
        timeLengthForPreview = 2000
        defaultImage = "adivinasizefixedforeasy"
        restantesMovimientos = 8
    }
    fun getConfigForMedium() {
        gameBoardX = 4
        gameBoardY = 6
        posibleItems = gameBoardX * gameBoardY
        timeLength = 60000
        timeLengthForPreview = 2000
        defaultImage = "adivinasizefixedformedium"
        restantesMovimientos = 12
    }
    fun getConfigForHard() {
        gameBoardX = 5
        gameBoardY = 6
        posibleItems = gameBoardX * gameBoardY
        timeLength = 60000
        timeLengthForPreview = 2000
        defaultImage = "adivinasizefixedforhard"
        restantesMovimientos = 15
    }

    fun getGeneralConfigs () {
        val commonConstants: CommonConstants = CommonConstants()
        // Creamos una nueva lista para crear numeros desde el cero hasta el tamanio del tablero
        listOfRandomNumbers = (0 until posibleItems).toMutableList();
        // Barajamos los numeros de manera aleatoria
        listOfRandomNumbers.shuffle()
        // Creamos un nuevo iterador de la lista creada
        listOfRandomNumbersIterator = listOfRandomNumbers.iterator()
        // Creamos un nuevo arreglo para determinar en que lugar se va a colocar cada carta, lo hacemos por parejas es decir posicion 1, posicion 2 y el recurso que estamos creando
        cartLocationInTheBoard = Array((gameBoardX * gameBoardY) / 2) { arrayOf(0, 0, "") }
        // Comenzamos a leer nuestra lista al hazar y la asinamos la ubicacion de las parejas al hazar
        var i = 0
        while (listOfRandomNumbersIterator.hasNext()) {
            cartLocationInTheBoard[i][0] = listOfRandomNumbersIterator.next()!!
            cartLocationInTheBoard[i][1] = listOfRandomNumbersIterator.next()!!
            cartLocationInTheBoard[i][2] = commonConstants.pokemonAvailables[i]
            i++
        }
        // Creamos un nuevo arreglo para determinar la distribucion de nuestras cartas en el tablero
        cartDistributionInTheGameBoard = Array(gameBoardX * gameBoardY) { Array(2) { 0 } }

        // Convertimos nuestra lista de cartLocationInTheBoard y le asignamos su posicion en el talblero
        var x = -1
        var y = 0
        for (i in cartDistributionInTheGameBoard.indices) {
            if (y == gameBoardY) {
                y = 0
            }
            cartDistributionInTheGameBoard[i][1] = y
            if (y == 0) {
                x++
            }
            cartDistributionInTheGameBoard[i][0] = x
            y++
        }
        // Creamos un nuevo arreglo en el cual asignaremos la posicion de las cartas a modo de tablero de juegos
        gameBoard = Array(gameBoardX) { Array(gameBoardY) { "" } }
        // Colocamos cada carta en su lugar
        for (i in cartLocationInTheBoard.indices) {
            gameBoard[cartDistributionInTheGameBoard[cartLocationInTheBoard[i][0] as Int][0]][cartDistributionInTheGameBoard[cartLocationInTheBoard[i][0] as Int][1]] =
                cartLocationInTheBoard[i][2] as String
            gameBoard[cartDistributionInTheGameBoard[cartLocationInTheBoard[i][1] as Int][0]][cartDistributionInTheGameBoard[cartLocationInTheBoard[i][1] as Int][1]] =
                cartLocationInTheBoard[i][2] as String
        }
    }

}