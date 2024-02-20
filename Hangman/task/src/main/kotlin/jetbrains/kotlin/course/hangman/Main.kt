package jetbrains.kotlin.course.hangman

// You will use this function later
fun getGameRules(wordLength: Int, maxAttemptsCount: Int) = "Welcome to the game!$newLineSymbol$newLineSymbol" +
        "In this game, you need to guess the word made by the computer.$newLineSymbol" +
        "The hidden word will appear as a sequence of underscores, one underscore means one letter.$newLineSymbol" +
        "You have $maxAttemptsCount attempts to guess the word.$newLineSymbol" +
        "All words are English words, consisting of $wordLength letters.$newLineSymbol" +
        "Each attempt you should enter any one letter,$newLineSymbol" +
        "if it is in the hidden word, all matches will be guessed.$newLineSymbol$newLineSymbol" +
        "" +
        "For example, if the word \"CAT\" was guessed, \"_ _ _\" will be displayed first, " +
        "since the word has 3 letters.$newLineSymbol" +
        "If you enter the letter A, you will see \"_ A _\" and so on.$newLineSymbol$newLineSymbol" +
        "" +
        "Good luck in the game!"

// You will use this function later
fun isWon(complete: Boolean, attempts: Int, maxAttemptsCount: Int) = complete && attempts <= maxAttemptsCount

// You will use this function later
fun isLost(complete: Boolean, attempts: Int, maxAttemptsCount: Int) = !complete && attempts > maxAttemptsCount

fun isComplete(secret: String, currentGuess: String) =
    secret == currentGuess.replace(" ", "")

fun generateNewUserWord(secret: String, guess: Char, currentUserWord: String) =
    currentUserWord
        .trim(' ')
        .split(" ")
        .mapIndexed { index, c ->
            return@mapIndexed if (secret[index] == guess) {
                guess
            } else {
                c
            }
        }
        .joinToString(" ")

fun generateSecret() = words.random()

fun getHiddenSecret(wordLength: Int) = (1..wordLength).joinToString(separator) { underscore }

fun isCorrectInput(userInput: String): Boolean {
    if (userInput.length != 1) {
        println("The length of your guess should be 1! Try again!")
        return false
    }
    if (!userInput[0].isLetter()) {
        println("You should input only English letters! Try again!")
        return false
    }
    return true
}

fun safeUserInput(): Char {
    var userInput: String
    do {
        println("Please input your guess.")
        userInput = safeReadLine().uppercase()
    } while (!isCorrectInput(userInput))
    return userInput[0]
}

fun getRoundResults(secret: String, guess: Char, currentUserWord: String): String {
    val newUserWord = generateNewUserWord(secret, guess, currentUserWord)
    if (secret.contains(guess)) {
        println("Great! This letter is in the word! The current word is $newUserWord")
    } else {
        println("Sorry, the secret does not contain the symbol: $guess. The current word is $currentUserWord")
    }
    return newUserWord
}

fun playGame(secret: String, maxAttemptsCount: Int) {
    var complete = false
    var attempts = 0
    var currentUserWord = getHiddenSecret(secret.length)
    println("I guessed a word: $currentUserWord")
    while (!complete) {
        val guess = safeUserInput()
        currentUserWord = getRoundResults(secret, guess, currentUserWord)
        complete = isComplete(secret, currentUserWord)
        attempts++
        if (isLost(complete, attempts, maxAttemptsCount)) {
            println("Sorry, you lost! My word is $secret")
            break
        } else if (isWon(complete, attempts, maxAttemptsCount)) {
            println("Congratulations! You guessed it!")
        }
    }
}

fun main() {
    // Uncomment this code on the last step of the game

     println(getGameRules(wordLength, maxAttemptsCount))
     playGame(generateSecret(), maxAttemptsCount)
}
