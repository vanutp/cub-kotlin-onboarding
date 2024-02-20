package jetbrains.kotlin.course.last.push

// You will use this function later
fun getPattern(): String {
    println(
        "Do you want to use a pre-defined pattern or a custom one? " +
                "Please input 'yes' for a pre-defined pattern or 'no' for a custom one"
    )
    do {
        when (safeReadLine()) {
            "yes" -> {
                return choosePattern()
            }

            "no" -> {
                println("Please, input a custom picture")
                return safeReadLine()
            }

            else -> println("Please input 'yes' or 'no'")
        }
    } while (true)
}

// You will use this function later
fun choosePattern(): String {
    do {
        println("Please choose a pattern. The possible options: ${allPatterns().joinToString(", ")}")
        val name = safeReadLine()
        val pattern = getPatternByName(name)
        pattern?.let {
            return@choosePattern pattern
        }
    } while (true)
}

// You will use this function later
fun chooseGenerator(): String {
    var toContinue = true
    var generator = ""
    println("Please choose the generator: 'canvas' or 'canvasGaps'.")
    do {
        when (val input = safeReadLine()) {
            "canvas", "canvasGaps" -> {
                toContinue = false
                generator = input
            }

            else -> println("Please, input 'canvas' or 'canvasGaps'")
        }
    } while (toContinue)
    return generator
}

// You will use this function later
fun safeReadLine(): String = readlnOrNull() ?: error("Your input is incorrect, sorry")

fun getPatternHeight(pattern: String) = pattern.lines().size

fun fillPatternRow(patternRow: String, patternWidth: Int) = patternRow.padEnd(patternWidth, separator)

fun repeatHorizontally(pattern: String, n: Int, patternWidth: Int) =
    pattern
        .lines()
        .joinToString(newLineSymbol) { fillPatternRow(it, patternWidth).repeat(n) }

fun dropTopFromLine(line: String, width: Int, patternHeight: Int, patternWidth: Int) =
    if (patternHeight <= 1) {
        line
    } else {
        line
            .lines()
            .drop(1)
            .joinToString(newLineSymbol)
    }

fun canvasGenerator(pattern: String, width: Int, height: Int) =
    repeatHorizontally(pattern, width, getPatternWidth(pattern))
        .let { row ->
            val continuedRow = dropTopFromLine(row, width, getPatternHeight(pattern), getPatternWidth(pattern))
            (1..height).joinToString(newLineSymbol) { if (it == 1) row else continuedRow }
        } + newLineSymbol

fun trimHorizontally(line: String, width: Int) = line.dropLast(line.length - width)

fun canvasWithGapsGenerator(pattern: String, width: Int, height: Int): String {
    val patternWidth = getPatternWidth(pattern)
    val pictureWidth = width * patternWidth
    val patternWithGap = pattern.lines().joinToString(newLineSymbol) { it + separator.toString().repeat(patternWidth) }
    val row = repeatHorizontally(patternWithGap, (width + 1) / 2, patternWidth * 2)
    val oddRow = row
        .lines().joinToString(newLineSymbol) {
            trimHorizontally(it, pictureWidth)
        }
    val evenRow = row
        .lines()
        .map { separator.toString().repeat(patternWidth) + it }
        .joinToString(newLineSymbol) {
            trimHorizontally(it, pictureWidth)
        }
    return (1..height)
        .joinToString(newLineSymbol) {
            if (it % 2 == 1 || width == 1) {
                oddRow
            } else {
                evenRow
            }
        } + newLineSymbol
}

fun applyGenerator(pattern: String, generatorName: String, width: Int, height: Int) =
    when (generatorName) {
        "canvas" -> canvasGenerator(pattern, width, height)
        "canvasGaps" -> canvasWithGapsGenerator(pattern, width, height)
        else -> error("meaningful error")
    }

fun main() {
    // Uncomment this code on the last step of the game

     val pattern = getPattern()
     val generatorName = chooseGenerator()
     println("Please input the width of the resulting picture:")
     val width = safeReadLine().toInt()
     println("Please input the height of the resulting picture:")
     val height = safeReadLine().toInt()

     println("The pattern:$newLineSymbol${pattern.trimIndent()}")

     println("The generated image:")
     println(applyGenerator(pattern, generatorName, width, height))
}
