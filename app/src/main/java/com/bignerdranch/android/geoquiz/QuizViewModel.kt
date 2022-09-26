package com.bignerdranch.android.geoquiz

import android.content.Intent
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"
class QuizViewModel : ViewModel() {

    var currentIndex = 0
    var isCheater = false
    private val isCheaterArray = arrayOf(false, false, false, false, false, false)

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true))

    // 6 values for 6 questions
    private val answeredArray = arrayOf(false, false, false, false, false, false)
    private var numberCorrect = 0
    private var questionCounter = 0

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer
    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }
    fun moveToPrevious() {
        currentIndex = (currentIndex + 5) % questionBank.size
    }
    fun clickOnQuestion() {
        currentIndex = (currentIndex + 1) % questionBank.size

    }

    fun increaseNumberCorrect() {
        numberCorrect++
    }
    fun increaseQuestionCounter() {
        questionCounter++
    }

    fun checkIfAnswered(): Boolean {
        return answeredArray[currentIndex]
    }
    fun updateAnswered() {
        answeredArray[currentIndex] = true
    }

    fun getQuestionCounter(): Int {
        return questionCounter
    }
    fun createGradedString(): String {
        val gradedQuiz = (numberCorrect * 100) / 6
        return "Result: " + gradedQuiz + "%"
    }

    fun reportCheater(data: Intent?) {
        isCheaterArray[currentIndex] =
            data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
    }

    fun checkCheater(): Boolean {
        return isCheaterArray[currentIndex]

    }
}