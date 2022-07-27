package com.basic.coroutinessamplekt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    val RESULT1 = "RESULT #1"
    val RESULT2 = "RESULT #2"

    lateinit var txtResult: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnCoroutine:Button = findViewById(R.id.btn_coroutine)
        txtResult = findViewById(R.id.txt_result)

        btnCoroutine.setOnClickListener {

            //IO: network request, Main: ui , Default: heavy computation
            CoroutineScope(IO).launch {
                    fakeAPIRequest()
            }

        }
    }

    private fun setNewText(input: String){
        val newText = txtResult.text.toString() + "\n$input"
        txtResult.text = newText
    }

    private suspend fun setTextOnMainTheard(input: String){
        withContext(Main){
            setNewText(input)
        }
    }
    private suspend fun fakeAPIRequest(){
        val result1 = getResult1FromAPI()
        println("debug $result1")
        setTextOnMainTheard(result1)

        val result2 = getResult2FromAPI()
        setTextOnMainTheard(result2)
    }
    private suspend fun getResult1FromAPI(): String{
        logThread("getResult1FromAPI")
        delay(1000)
        return RESULT1
    }

    private suspend fun getResult2FromAPI(): String{
        logThread("getResult2FromAPI")
        delay(1000)
        return RESULT2
    }

    private fun logThread(methodName: String){
        println("debug:{$methodName}, ${Thread.currentThread().name}")
    }
}