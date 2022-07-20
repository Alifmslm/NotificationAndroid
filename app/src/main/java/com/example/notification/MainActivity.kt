package com.example.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import androidx.core.app.NotificationCompat
import com.example.notification.databinding.ActivityMainBinding
import java.util.concurrent.CompletableFuture


class MainActivity : AppCompatActivity() {

    private var notificationmanager : NotificationManager? = null
    private val channel_id = "channel_1"

    private lateinit var binding: ActivityMainBinding
    private lateinit var countdowwntimer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        notificationmanager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotifChannel(channel_id, "countdown", "Notif When Countdown End")

        binding.btnStart.setOnClickListener {
            countdowwntimer.start()
        }

        countdowwntimer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFuture: Long) {
                binding.timer.text = getString(R.string.time_remaining, millisUntilFuture / 1000)
            }

            override fun onFinish() {
                displayNotif()
            }

        }

    }

    private fun displayNotif() {
        val notificationId = 45
        val notif = NotificationCompat.Builder(this, channel_id)
            .setContentTitle("CountDown Timer")
            .setContentText("Waktu Anda Habis")
            .setSmallIcon(R.drawable.ic_baseline_info_24)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
        notificationmanager?.notify(notificationId, notif)
    }

    private fun createNotifChannel(id : String, name : String, channelDesc : String) {
        //validasi notif akan dibuat apabila SDK 26+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val important = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(id, name, important).apply {
                description = channelDesc
            }
            notificationmanager?.createNotificationChannel(channel)
        }
    }
}