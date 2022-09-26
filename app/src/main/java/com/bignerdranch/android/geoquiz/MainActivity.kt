package com.bignerdranch.android.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"
class MainActivity : AppCompatActivity() {

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProviders.of(this).get(QuizViewModel::class.java)
    }

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var previousButton: ImageButton
    private lateinit var questionTextView: TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)

        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        quizViewModel.currentIndex = currentIndex

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        previousButton = findViewById(R.id.previous_button)
        questionTextView = findViewById(R.id.question_text_view)


        trueButton.setOnClickListener { view: View ->
            // Do something in response to the click here
            if(!quizViewModel.checkIfAnswered()) {
                checkAnswer(true)
                quizViewModel.updateAnswered()
                if(quizViewModel.getQuestionCounter() == 6) {
                    gradeQuiz()
                }
            }
            else {
                answeredQuestion()
            }
        }

        falseButton.setOnClickListener { view: View ->
            // Do something in response to the click here
            if(!quizViewModel.checkIfAnswered()) {
                checkAnswer(false)
                quizViewModel.updateAnswered()
                if(quizViewModel.getQuestionCounter() == 6) {
                    gradeQuiz()
                }
            }
            else {
                answeredQuestion()
            }
        }

        nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }

        previousButton.setOnClickListener {
            quizViewModel.moveToPrevious()
            updateQuestion()
        }

        questionTextView.setOnClickListener {
            quizViewModel.clickOnQuestion()
            updateQuestion()
        }

        updateQuestion()

    } // end onCreate()

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }
    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "onSaveInstanceState")
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.currentIndex)
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    private fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
    } // end updateQuestion

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer
        val messageResId = if (userAnswer == correctAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        val toast = Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.TOP, 0, 0)
        toast.show()

        if (userAnswer == correctAnswer) {
            quizViewModel.increaseNumberCorrect()
        }
        quizViewModel.increaseQuestionCounter()
    }

    private fun answeredQuestion() {
        val toast = Toast.makeText(this, R.string.answered_question, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.TOP, 0, 0)
        toast.show()
    }

    private fun gradeQuiz() {
        val toast = Toast.makeText(this, quizViewModel.createGradedString(), Toast.LENGTH_LONG)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }


} // end MainActivity class