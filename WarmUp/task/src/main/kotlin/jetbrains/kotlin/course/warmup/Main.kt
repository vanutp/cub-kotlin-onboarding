package jetbrains.kotlin.course.warmup

import kotlin.math.max

// You will use this function later
fun getGameRules(wordLength: Int, maxAttemptsCount: Int, secretExample: String) =
    "Welcome to the game! $newLineSymbol" +
            newLineSymbol +
            "Two people play this game: one chooses a word (a sequence of letters), " +
            "the other guesses it. In this version, the computer chooses the word: " +
            "a sequence of $wordLength letters (for example, $secretExample). " +
            "The user has several attempts to guess it (the max number is $maxAttemptsCount). " +
            "For each attempt, the number of complete matches (letter and position) " +
            "and partial matches (letter only) is reported. $newLineSymbol" +
            newLineSymbol +
            "For example, with $secretExample as the hidden word, the BCDF guess will " +
            "give 1 full match (C) and 1 partial match (B)."

fun generateSecret() = "ABCD"

fun countAllMatches(secret: String, guess: String): Int =
    minOf(
        secret.filter { guess.contains(it) }.length,
        guess.filter { secret.contains(it) }.length
    )

fun countPartialMatches(secret: String, guess: String): Int =
    countAllMatches(secret, guess) - countExactMatches(secret, guess)

fun countExactMatches(secret: String, guess: String): Int =
    secret
        .filterIndexed { index, c ->
            guess[index] == c
        }
        .length

fun isComplete(secret: String, guess: String): Boolean =
    secret == guess

fun isWon(complete: Boolean, attempts: Int, maxAttemptsCount: Int): Boolean =
    complete && (attempts <= maxAttemptsCount)

fun isLost(complete: Boolean, attempts: Int, maxAttemptsCount: Int): Boolean =
    !complete && (attempts > maxAttemptsCount)

fun printRoundResults(secret: String, guess: String) {
    val fullMatches = countExactMatches(secret, guess)
    val partialMatches = countPartialMatches(secret, guess)
    println("Your guess has $fullMatches full matches and $partialMatches partial matches.")
}

fun playGame(secret: String, wordLength: Int, maxAttemptsCount: Int) {
    var guess = ""
    var attempts = 0
    var complete = false
    while (!complete) {
        println("Please input your guess. It should be of length $wordLength.")
        guess = safeReadLine()
        printRoundResults(secret, guess)
        complete = isComplete(secret, guess)
        attempts += 1
        if (isWon(complete, attempts, maxAttemptsCount)) {
            println("Congratulations! You guessed it!")
        } else if (isLost(complete, attempts, maxAttemptsCount)) {
            println("Sorry, you lost! :( My word is $secret")
            break
        }
    }
}

fun main() {
    val wordLength = 4
    val maxAttemptsCount = 3
    val secretExample = "ACEB"
    println(getGameRules(wordLength, maxAttemptsCount, secretExample))
    val secret = generateSecret()
    playGame(secret, wordLength, maxAttemptsCount)
}
