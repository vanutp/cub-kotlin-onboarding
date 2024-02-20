package jetbrains.kotlin.course.almost.done

fun safeReadLine() =
    readlnOrNull() ?: error("meaningful error message")

fun trimPicture(picture: String) = picture.trimIndent()

fun applyBordersFilter(picture: String): String {
    val width = getPictureWidth(picture)
    val borderedCenter = picture
        .lines()
        .joinToString(newLineSymbol) {
            "$borderSymbol$separator${it.padEnd(width, separator)}$separator$borderSymbol"
        }
    val yBorder = borderSymbol.toString().repeat(getPictureWidth(picture) + 4)
    return "$yBorder$newLineSymbol$borderedCenter$newLineSymbol$yBorder"
}

fun applySquaredFilter(picture: String) =
    applyBordersFilter(picture)
        .lines()
        .dropLast(1)
        .map { it.repeat(2) }
        .let { it + it + it[0] }
        .joinToString(newLineSymbol)

fun applyFilter(picture: String, filter: String): String {
    val trimmed = trimPicture(picture)
    return when (filter) {
        "borders" -> applyBordersFilter(trimmed)
        "squared" -> applySquaredFilter(trimmed)
        else -> error("meaningful error")
    }
}

fun chooseFilter(): String {
    println("Please choose the filter: 'borders' or 'squared'.")
    val filters = listOf("borders", "squared")
    while (true) {
        val input = safeReadLine()
        if (filters.contains(input)) {
            return input
        } else {
            println("Please input 'borders' or 'squared'")
        }
    }
}

fun choosePicture(): String {
    while (true) {
        println("Please choose a picture. The possible options are: ${allPictures().joinToString()}")
        val input = safeReadLine()
        val picture = getPictureByName(input)
        if (picture != null) {
            return picture
        }
    }
}

fun getPicture(): String {
    println("Do you want to use a predefined picture or a custom one? Please input 'yes' for a predefined image or 'no' for a custom one")
    while (true) {
        val input = safeReadLine()
        when (input) {
            "yes" -> return choosePicture()
            "no" -> {
                println("Please input a custom picture")
                return safeReadLine()
            }
            else -> println("Please input 'yes' or 'no'")
        }
    }
}

fun photoshop() {
    val picture = getPicture()
    val filter = chooseFilter()
    println("The old image:")
    println(picture)
    println("The transformed picture:")
    println(applyFilter(picture, filter))
}

fun main() {
     photoshop()
}
