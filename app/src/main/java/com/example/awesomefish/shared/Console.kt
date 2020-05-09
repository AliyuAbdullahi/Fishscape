package com.example.awesomefish.shared

object Console {

    /**
     * @param message is the message to be logged
     * @param textColor is either [White, Purple or Green] these are the colors supported for simple
     * message logging. The default is white
     */
    fun log(message: String, textColor: TextColor = TextColor.White) {
        val supportedLogColors = listOf(TextColor.White, TextColor.Purple, TextColor.Green)
        if (textColor in supportedLogColors) {
            println("${textColor.color}$message$RESET")
        } else {
            println("${ColorText.WHITE}$message$RESET")
        }
    }

    /**
     * @param message is the text to be displayed on console. The text color is Yellow
     */
    fun warn(message: String) {
        println("${ColorText.YELLOW}$message$RESET")
    }

    /**
     * @param message is the text to be displayed on console. The text color is RED
     */
    fun error(message: String) {
        println("${ColorText.RED}$message$RESET")
    }

    /**
     * @param message is the message to be logged
     * @param backgroundColor is the text background color to provide emphasis
     * @param textColor is the message text color
     */
    fun emphasis(
        message: String,
        backgroundColor: BackgroundColor = BackgroundColor.Default,
        textColor: TextColor = TextColor.White
    ) {
        println("${backgroundColor.color}${textColor.color}$message$RESET")
    }

    private const val RESET = "\u001B[0m"

    private object ColorText {
        const val BLACK = "\u001B[30m"
        const val RED = "\u001B[31m"
        const val GREEN = "\u001B[32m"
        const val YELLOW = "\u001B[33m"
        const val BLUE = "\u001B[34m"
        const val PURPLE = "\u001B[35m"
        const val CYAN = "\u001B[36m"
        const val WHITE = "\u001B[37m"
    }

    enum class BackgroundColor(val color: String) {
        Red(ColorBackGround.RED_BACKGROUND),
        Black(ColorBackGround.BLACK_BACKGROUND),
        Blue(ColorBackGround.BLUE_BACKGROUND),
        Yellow(ColorBackGround.YELLOW_BACKGROUND),
        Purple(ColorBackGround.PURPLE_BACKGROUND),
        Green(ColorBackGround.GREEN_BACKGROUND),
        Cyan(ColorBackGround.CYAN_BACKGROUND),
        White(ColorBackGround.WHITE_BACKGROUND),
        Default("")
    }

    enum class TextColor(val color: String) {
        Red(ColorText.RED),
        Black(ColorText.BLACK),
        Blue(ColorText.BLUE),
        Green(ColorText.GREEN),
        Yellow(ColorText.YELLOW),
        Cyan(ColorText.CYAN),
        White(ColorText.YELLOW),
        Purple(ColorText.PURPLE)
    }

    private object ColorBackGround {
        const val BLACK_BACKGROUND = "\u001B[40m"
        const val RED_BACKGROUND = "\u001B[41m"
        const val GREEN_BACKGROUND = "\u001B[42m"
        const val YELLOW_BACKGROUND = "\u001B[43m"
        const val BLUE_BACKGROUND = "\u001B[44m"
        const val PURPLE_BACKGROUND = "\u001B[45m"
        const val CYAN_BACKGROUND = "\u001B[46m"
        const val WHITE_BACKGROUND = "\u001B[47m"
    }
}
