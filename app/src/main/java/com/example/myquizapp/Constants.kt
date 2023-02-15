package com.example.myquizapp

import com.example.myquizapp.R.drawable.argentina_flag

object Constants
{
    fun getQuestions():ArrayList<Question>
    {
        val questionsList=ArrayList<Question>()

        val que1=Question(
            1,"What country does this flag belongs to?",R.drawable.india_flag,"Irelan","Iran","Hungary","India",
            4
        )
        questionsList.add(que1)

        val que2=Question(
            2,"What country does this flag belongs to?",R.drawable.kuwait_flag,"Kuwait","Jordan","Sudan","Palestine",
            1
        )
        questionsList.add(que2)

        val que3=Question(
            3,"What country does this flag belongs to?",R.drawable.argentina_flag,"Argentina","Australia",
            "Armenia","Austria",1
        )
        questionsList.add(que3)



        return questionsList
    }
}