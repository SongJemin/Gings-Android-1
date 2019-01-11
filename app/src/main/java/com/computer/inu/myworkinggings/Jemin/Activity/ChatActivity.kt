package com.computer.inu.myworkinggings.Jemin.Activity

import android.app.Activity
import android.os.*
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.computer.inu.myworkinggings.Hyunjin.Adapter.MessageSendDataRecyclerViewAdapter
import com.computer.inu.myworkinggings.Hyunjin.Data.MessageSendData
import com.computer.inu.myworkinggings.Jemin.Adapter.ChatAdapter
import com.computer.inu.myworkinggings.Jemin.Data.ChatListItem
import com.computer.inu.myworkinggings.Nuri.ChatReceiveData
import com.computer.inu.myworkinggings.R
import com.google.gson.JsonObject
import io.reactivex.CompletableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.fragment_messagesend1.*
import org.json.JSONObject
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompHeader
import ua.naiksoftware.stomp.client.StompClient
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ChatActivity : AppCompatActivity() {

    val TAG = "ChatActivity"

    lateinit var mStompClient : StompClient
    var sendMessage : String = ""
    var receiveMessage : String = ""
    lateinit var chatAdapter: ChatAdapter
    var chatData = ArrayList<ChatListItem>()

    override fun onBackPressed() {
        mStompClient.disconnect()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        chat_send_btn.setOnClickListener {
            sendMessage = chat_message_edit.text.toString()
            chat_message_edit.setText("")
            sendMessageForCreate()
        }

        chat_disconnect_btn.setOnClickListener {
            mStompClient.disconnect()
        }
        var headers = java.util.ArrayList<StompHeader>()
        headers.add(StompHeader("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjksInJvbGUiOiJVU0VSIiwiaXNzIjoiR2luZ3MgVXNlciBBdXRoIE1hbmFnZXIiLCJleHAiOjE1NDkxOTYxMzN9.OrlfMuYaMa2SqrXGcHlDRmttGOC1z7DiROKD4dsz2Ds"))


        //서버연결
        mStompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, "ws://52.78.243.92:8080/connect")
        //생명주기
        mStompClient.withClientHeartbeat(30000).withServerHeartbeat(30000)
        //소켓 연결
        mStompClient.connect(headers)
        //채팅방 연결
        mStompClient.topic("/user/queue/chat-notice").subscribe { topicMessage ->
            {
                Log.d("SubscribeLog222 = ", topicMessage.payload)
            }
            Log.d("SubscribeLog222 = ", topicMessage.payload)

            var receiveData = JSONObject(topicMessage.payload)
            sendMessage(receiveData)
        }

        Log.v(TAG,"수신 메세지 not null")
    }



    protected fun applySchedulers(): CompletableTransformer {
        return CompletableTransformer { upstream ->  upstream.unsubscribeOn(Schedulers.newThread()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())}
    }

    fun sendMessage(receiveData : JSONObject)
    {
        var url = "/topic/room/" + receiveData.getJSONObject("chatRoom").getString("id")
        //채팅방에서 채팅하기
        mStompClient.topic(url).subscribe { topicMessage ->
            {
                Log.d("SubscribeLog333 = ", topicMessage.payload)
            }
            Log.d("SubscribeLog333 = ", topicMessage.payload)
        }
        System.out.println("꺄아아아아아아아아아아아아아 : " + url)

//        mStompClient.send("/chat/create", jsonObject.toString() + mTimeFormat.format(Date()))
//                .compose(applySchedulers())
//                .subscribe({ Log.d("SendLog", "STOMP echo send successfully") }, { throwable ->
//                    Log.e("SendLogError", "Error send STOMP echo", throwable)
//                    Toast.makeText(applicationContext, throwable.message, Toast.LENGTH_SHORT).show()
//                })
    }

    fun sendMessageForCreate(){
        //채팅방 생성을 위해
        Log.v(TAG, "Send Message = " + sendMessage)
        chatData.add(ChatListItem(0,"보낸 메세지 = " + sendMessage))
        var mTimeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        var jsonObject = JSONObject()
        val pref = applicationContext.getSharedPreferences("auto", Activity.MODE_PRIVATE)
        //var userID = pref.getInt("userID",0)
        var userID : Int = 11
        Log.v("ChatActivity","유저 번호 = " + userID)
        jsonObject.put("opponentId", userID)

        mStompClient.send("/chat/create", jsonObject.toString() + mTimeFormat.format(Date()))
                .compose(applySchedulers())
                .subscribe({ Log.d("SendLog", "STOMP echo send successfully") }, { throwable ->
                    Log.e("SendLogError", "Error send STOMP echo", throwable)
                    Toast.makeText(applicationContext, throwable.message, Toast.LENGTH_SHORT).show()
                })

        chatAdapter = ChatAdapter(chatData)
        chat_chat_recyclerview.adapter = chatAdapter
        chat_chat_recyclerview.layoutManager = LinearLayoutManager(this)
        chat_chat_recyclerview.scrollToPosition(chatData.size-1)
    }

    internal val handler: Handler = object : Handler(Looper.getMainLooper()) {

        override fun handleMessage(msg: Message) {
            Log.v(TAG,"핸들러 런")
            chat_chat_recyclerview.adapter = chatAdapter
            chat_chat_recyclerview.layoutManager = LinearLayoutManager(applicationContext)
            chat_chat_recyclerview.scrollToPosition(chatData.size-1)
            // 원래 하고싶었던 일들 (UI변경작업 등…)
        }
    }
}