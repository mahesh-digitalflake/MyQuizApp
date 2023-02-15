package com.example.myquizapp

import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import androidx.core.widget.TintableCompoundDrawablesView

class QuizQuestionActivities : AppCompatActivity(), View.OnClickListener {


    //Create global variables for the views in the layout
    private var progressBar: ProgressBar?=null
    private var tvProgress: TextView? = null
    private var tvQuestion:TextView? = null
    private var ivImage: ImageView? = null
    private var tvOptionOne:TextView? = null
    private var tvOptionTwo:TextView? = null
    private var tvOptionThree:TextView? = null
    private var tvOptionFour:TextView? = null
    private var buttonSubmit: Button? = null
    /**
     * This function is auto created by Android when the Activity Class is created.
     */


    private var mCurrentPosition: Int = 1 // Default and the first question position
    private var mQuestionsList: ArrayList<Question>? = null
    // END


    private var mSelectedOptionPosition: Int = 0
    // END
    // TODO (STEP 1: Add a variable for calculating the correct answers.)
    // START
    private var mCorrectAnswers: Int = 0
    // END

    override fun onCreate(savedInstanceState: Bundle?) {
        //This call the parent constructor
        super.onCreate(savedInstanceState)
        // This is used to align the xml view to this class
        setContentView(R.layout.activity_quiz_question_activities)


        progressBar=findViewById(R.id.progressBar)
        tvProgress = findViewById(R.id.tv_progress)
        tvQuestion = findViewById(R.id.tv_question)
        ivImage = findViewById(R.id.iv_image)
        tvOptionOne = findViewById(R.id.tv_option_one)
        tvOptionTwo = findViewById(R.id.tv_option_two)
        tvOptionThree = findViewById(R.id.tv_option_three)
        tvOptionFour = findViewById(R.id.tv_option_four)

        buttonSubmit = findViewById(R.id.btn_submit)
        mQuestionsList = Constants.getQuestions()
        // END

        setQuestion()

        tvOptionOne?.setOnClickListener(this)
        tvOptionTwo?.setOnClickListener(this)
        tvOptionThree?.setOnClickListener(this)
        tvOptionFour?.setOnClickListener(this)

        buttonSubmit?.setOnClickListener (this)
    }


    private fun setQuestion() {

        val question: Question =
            mQuestionsList!![mCurrentPosition - 1] // Getting the question from the list with the help of current position.
        defaultOptionsView()

        if (mCurrentPosition == mQuestionsList!!.size) {
            buttonSubmit?.text = "FINISH"
        } else {
            buttonSubmit?.text = "SUBMIT"
        }
        // END
        progressBar?.progress =
            mCurrentPosition // Setting the current progress in the progressbar using the position of question
        tvProgress?.text =
            "$mCurrentPosition" + "/" + progressBar?.max // Setting up the progress text

        // Now set the current question and the options in the UI
        tvQuestion?.text = question.question
        ivImage?.setImageResource(question.image)
        tvOptionOne?.text = question.optionOne
        tvOptionTwo?.text = question.optionTwo
        tvOptionThree?.text = question.optionThree
        tvOptionFour?.text = question.optionFour
    }

    override fun onClick(view: View?) {
        when (view?.id) {

            R.id.tv_option_one -> {
                tvOptionOne?.let {
                    selectedOptionView(it, 1)
                }

            }

            R.id.tv_option_two -> {
                tvOptionTwo?.let {
                    selectedOptionView(it, 2)
                }

            }

            R.id.tv_option_three -> {
                tvOptionThree?.let {
                    selectedOptionView(it, 3)
                }

            }

            R.id.tv_option_four -> {
                tvOptionFour?.let {
                    selectedOptionView(it, 4)
                }

            }

            R.id.btn_submit->{

                if (mSelectedOptionPosition == 0) {

                    mCurrentPosition++

                    when {

                        mCurrentPosition <= mQuestionsList!!.size -> {

                            setQuestion()
                        }
                        else -> {

                            Toast.makeText(this@QuizQuestionActivities, "You have successfully completed the quiz. Your Score is : $mCorrectAnswers", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    val question = mQuestionsList?.get(mCurrentPosition - 1)

                    // This is to check if the answer is wrong
                    if (question!!.correctAnswer != mSelectedOptionPosition) {
                        answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg)
                    }
                    // TODO (STEP 2: Increase the count of correct answer by 1 if the answer is right.)
                    // START
                    else {
                        mCorrectAnswers++
                    }
                    // END
                    // This is for correct answer
                    answerView(question.correctAnswer, R.drawable.correct_option_border_bg)

                    if (mCurrentPosition == mQuestionsList!!.size) {
                        buttonSubmit?.text = "FINISH"
                    } else {
                        buttonSubmit?.text = "GO TO NEXT QUESTION"
                    }

                    mSelectedOptionPosition = 0
                }
            }
        }
    }

    /**
     * A function for answer view which is used to highlight the answer is wrong or right.
     */
    private fun answerView(answer: Int, drawableView: Int) {

        when (answer) {

            1 -> {
                tvOptionOne?.background = ContextCompat.getDrawable(
                    this@QuizQuestionActivities,
                    drawableView
                )
            }
            2 -> {
                tvOptionTwo?.background = ContextCompat.getDrawable(
                    this@QuizQuestionActivities,
                    drawableView
                )
            }
            3 -> {
                tvOptionThree?.background = ContextCompat.getDrawable(
                    this@QuizQuestionActivities,
                    drawableView
                )
            }
            4 -> {
                tvOptionFour?.background = ContextCompat.getDrawable(
                    this@QuizQuestionActivities,
                    drawableView
                )
            }
        }
    }

    private fun selectedOptionView(tv: TextView, selectedOptionNum: Int) {

        defaultOptionsView()

        mSelectedOptionPosition = selectedOptionNum

        tv.setTextColor(
            Color.parseColor("#363A43")
        )
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
            this@QuizQuestionActivities,
            R.drawable.selected_option_border_bg
        )
    }


    private fun defaultOptionsView() {

        val options = ArrayList<TextView>()
        tvOptionOne?.let {
            options.add(0, it)
        }
        tvOptionTwo?.let {
            options.add(1, it)
        }
        tvOptionThree?.let {
            options.add(2, it)
        }
        tvOptionFour?.let {
            options.add(3,it)
        }

        for (option in options) {
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this@QuizQuestionActivities,
                R.drawable.default_option_border_bg
            )
        }
    }
}






































//class QuizQuestionActivities : AppCompatActivity(),View.OnClickListener {
//
//    private var mCurrentPosition:Int=1
//    private var mQuestionList:ArrayList<Question>?=null
//    private var mSelectedOptionPosition:Int=0
//
//    private var progressBar:ProgressBar?=null
//    private var tvProgress:TextView?=null
//    private var tvQuestion:TextView?=null
//    private var ivImage:ImageView?=null
//
//    private var tvOptionOne:TextView?=null
//    private var tvOptionTwo:TextView?=null
//    private var tvOptionThree:TextView?=null
//    private var tvOptionFour:TextView?=null
//    private var btnSubmit:Button?=null
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_quiz_question_activities)
//
//        progressBar=findViewById(R.id.progressBar)
//        tvProgress=findViewById(R.id.tv_progress)
//        tvQuestion=findViewById(R.id.tv_question)
//        ivImage=findViewById(R.id.iv_image)
//
//        tvOptionOne=findViewById(R.id.tv_option_one)
//        tvOptionTwo=findViewById(R.id.tv_option_two)
//        tvOptionThree=findViewById(R.id.tv_option_three)
//        tvOptionFour=findViewById(R.id.tv_option_four)
//        btnSubmit=findViewById(R.id.btn_submit)
//
//        tvOptionOne?.setOnClickListener(this)
//        tvOptionTwo?.setOnClickListener(this)
//        tvOptionThree?.setOnClickListener(this)
//        tvOptionFour?.setOnClickListener(this)
//        btnSubmit?.setOnClickListener(this)
//
//        mQuestionList = Constants.getQuestions()
//        setQuestion()
//
//
//
//    }
//
//    private fun setQuestion() {
////        var mCurrentPosition = 1
//        val question: Question = mQuestionList!![mCurrentPosition - 1]
//        ivImage?.setImageResource(question.image)
//        progressBar?.progress = mCurrentPosition
//        tvProgress?.text = "$mCurrentPosition / ${progressBar?.max}"
//        tvQuestion?.text = question.question
//        tvOptionOne?.text = question.optionOne
//        tvOptionTwo?.text = question.optionTwo
//        tvOptionThree?.text = question.optionThree
//        tvOptionFour?.text = question.optionFour
//
//        if(mCurrentPosition==mQuestionList!!.size)
//        {
//            btnSubmit?.text="FINISH"
//        }
//        else
//        {
//            btnSubmit?.text="SUBMIT"
//        }
//    }
//
//    private fun defaultOptionView()
//    {
//        val options=ArrayList<TextView>()
//        tvOptionOne?.let {
//            options.add(0,it)
//        }
//
//        tvOptionTwo?.let {
//            options.add(1,it)
//        }
//
//        tvOptionThree?.let {
//            options.add(2,it)
//        }
//
//        tvOptionFour?.let {
//            options.add(3,it)
//        }
//
//        for(option in options)
//        {
//            option.setTextColor(Color.parseColor("#7A8089"))
//            option.typeface= Typeface.DEFAULT
//            option.background=ContextCompat.getDrawable(
//                this,R.drawable.default_option_border_bg
//            )
//        }
//    }
//
//    private fun selectedOptionView(tv:TextView,selectedOptionNum:Int)
//    {
//        defaultOptionView()
//
//        mSelectedOptionPosition=selectedOptionNum
//
//        tv.setTextColor(Color.parseColor("#363A43"))
//        tv.setTypeface(tv.typeface,Typeface.BOLD)
//        tv.background=ContextCompat.getDrawable(
//            this,R.drawable.selected_option_border_bg
//        )
//    }
//
//    override fun onClick(view:View) {
//        when(view?.id)
//        {
//            R.id.tv_option_one->{
//                tvOptionOne?.let {
//                    selectedOptionView(it,1)
//                }
//            }
//            R.id.tv_option_two->{
//                tvOptionTwo?.let {
//                    selectedOptionView(it,2)
//                }
//            }
//            R.id.tv_option_three->{
//                tvOptionThree?.let {
//                    selectedOptionView(it,3)
//                }
//            }
//            R.id.tv_option_four->{
//                tvOptionFour?.let {
//                    selectedOptionView(it,4)
//                }
//            }
//
//            R.id.btn_submit-> {
//                Toast.makeText(this, "on click submit", Toast.LENGTH_SHORT).show()
//
//                if(mSelectedOptionPosition==0)
//                {
//                    mCurrentPosition++
//
//                    when{
//                        mCurrentPosition<=mQuestionList!!.size ->{
//                            setQuestion()
//                        }
//                    }
//
//                }
//                else
//                {
//                    val question=mQuestionList?.get(mCurrentPosition-1)
//                    if(question!!.correctAnswer!=mSelectedOptionPosition)
//                    {
//                        answerView(mSelectedOptionPosition,R.drawable.wrong_option_border_bg)
//                    }
//
//                    answerView(question.correctAnswer,R.drawable.correct_option_border_bg)
//
//                    if(mCurrentPosition==mQuestionList!!.size)
//                    {
//                        btnSubmit?.text="FINISH"
//                    }
//                    else
//                    {
//                        btnSubmit?.text="GO TO NEXT QUESTION"
//                    }
//
////                    mSelectedOptionPosition=0
//                }
//            }
//        }
//    }
//
//    private fun answerView(answer:Int,drawablesView:Int)
//    {
//        when (answer)
//        {
//            1->{
//                tvOptionOne?.background=ContextCompat.getDrawable(
//                    this,
//                    drawablesView
//                )
//            }
//
//            2->{
//                tvOptionTwo?.background=ContextCompat.getDrawable(
//                    this@QuizQuestionActivities,
//                    drawablesView
//                )
//            }
//
//            3->{
//                tvOptionThree?.background=ContextCompat.getDrawable(
//                    this@QuizQuestionActivities,
//                    drawablesView
//                )
//            }
//
//            4->{
//                tvOptionFour?.background=ContextCompat.getDrawable(
//                    this@QuizQuestionActivities,
//                    drawablesView
//                )
//            }
//        }
//    }
//}