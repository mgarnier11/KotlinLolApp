package com.example.kotlinlolapp.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import com.example.kotlinlolapp.logic.KotlinLolApp
import io.reactivex.disposables.Disposable
import android.support.v4.app.SupportActivity
import android.support.v4.app.SupportActivity.ExtraData
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log
import com.example.kotlinlolapp.R


class AppLoadingActivity : AppCompatActivity() {

    val project = KotlinLolApp.instance

    var lstDisposables: MutableList<Disposable> = mutableListOf()

    lateinit var pbLoading: ProgressBar
    lateinit var tvActualProgress: TextView
    lateinit var tvLoadingLogs: TextView

    var logs: MutableList<String> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_loading)

        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
            Log.w("DEBUG", "Couldnt hide action bar")
        }



        pbLoading = findViewById(R.id.activity_app_loading_pb_loading)
        tvActualProgress = findViewById(R.id.activity_app_loading_tv_actual_progress)
        tvLoadingLogs = findViewById(R.id.activity_app_loading_tv_loading_logs)

        project.initApp(this)

        lstDisposables.add(project.listen(KotlinLolApp.LoadingEvent::class.java).subscribe {
            val value = it.value * 100 / it.maxValue
            tvActualProgress.text = "${value}%"
            pbLoading.progress = value

            if (it.finished) {
                startActivity(Intent(this, MainActivity::class.java))

                finish()
            }
        })

        lstDisposables.add(project.listen(KotlinLolApp.MessageEvent::class.java).subscribe {
            if (logs.size < 5) logs.add(it.message)
            else {
                logs.removeAt(0)
                logs.add(it.message)
            }

            val builder = StringBuilder()
            for (log in logs) {
                builder.append(log + "\n")
            }

            tvLoadingLogs.setText(builder.toString())
        })
    }


    override fun onDestroy() {
        super.onDestroy()

        lstDisposables.forEach { it.dispose() }
    }


}
