package com.example.shukandevpro.ui.notifications

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.pm.PackageManager
import android.media.MediaPlayer
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.shukandevpro.R

abstract class SoundManageService : Service() {
    companion object {
        //通知チャンネルID文字列定数。
        private const val CHANNEL_ID = "soundmanagersevice_notification_channel"
    }

    private var _player: MediaPlayer? = null
    override fun onCreate() {
        _player = MediaPlayer()
        //通知チャンネル名をstrings.xmlから取得
        val name = getString(R.string.notification_channel_name)
        //通知チャンネルの重要度を標準に設定。
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        //通知チャンネル生成。
        val channel = NotificationChannel(CHANNEL_ID, name, importance)
        //NotificationManager オブジェクトを取得。
        val manager = getSystemService(NotificationManager::class.java)
        //通知チャンネルを設定。
        manager.createNotificationChannel(channel)
    }
    override fun onCompletion(mp: MediaPlayer){
    //Notificationを作成するBuilderクラス生成。
        val builder = NotificationCompat.Builder(this@SoundManageService, CHANNEL_ID)
    //通知エリアに表示されるアイコンを設定。
        builder.setSmallIcon(android.R.drawable.ic_dialog_info)
    //通知ドロワーの表示タイトルを設定。
        builder.setContentTitle(getString(R.string.msg_notification_title_finish))
    //通知ドロワーでの表示タイトルを設定。
        builder.setContentText(getString(R.string.msg_notification_text_finish))
    //BuilderからNotificationオブジェクトを生成。
        val notification = builder.build()
    //NotificationManagerCompatオブジェクトを取得。
        val manager = NotificationManagerCompat.from(this@SoundManageService)
    //通知。
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        manager.notify(100, notification)
        stopSelf()
    }
}
override fun onPrepared(mp: MediaPlayer) {
    mp.start()
    //Notificationを作成するBuilderクラス作成
    val builder = NotificationCompat.Builder(this@SoundManageService, CHANNEL_ID)
    //通知ドロワーでの表示アイコンを設定
    builder.setSmallIcon(android.R.drawable.ic_dialog_info)
    //通知ドロワーでの表示タイトルの設定
    builder.setContentTitle(getString(R.string.msg_notification_title_Start))
    //通知ドロワーでの表示メッセージを設定
    builder.setContentText(getString(R.string.msg_notification_text_Start))
    //起動先Activityクラスを指定したIntentオブジェクトを生成
    val intent = Intent(this@SoundManageService, MainActivity::class.java)
    //起動先アクティビティに引継ぎデータを格納
    intent.putExtra("fromNotification", true)
    //PendingIntentオブジェクトを取得
    val stopServiceIntent = PendingIntent.getActivity(this@SoundManageService, 0, intent, PendingIntent.FLAG_IMMUTABLE)
    //タップされた通知メッセージを自動的に消去するように設定
    builder.setContentIntent(stopServiceIntent)
    //BuilderからNotificationオブジェクトを生成
    val notification = builder.build()
    //Notificationオブジェクトをもとにサービスをフォアグラウンド化
    starForeground(200, notification);
}
