package com.computer.inu.myworkinggings.Jemin.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.computer.inu.myworkinggings.Hyunjin.Adapter.MessageSendDataRecyclerViewAdapter
import com.computer.inu.myworkinggings.Hyunjin.Data.MessageSendData
import com.computer.inu.myworkinggings.Jemin.Adapter.ChatAdapter
import com.computer.inu.myworkinggings.Jemin.Data.ChatListItem
import com.computer.inu.myworkinggings.R
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

class ChatActivity : AppCompatActivity() {

    val TAG = "ChatActivity"

    lateinit var mStompClient : StompClient
    var sendMessage : String = ""
    var receiveMessage : String = ""
    lateinit var chatAdapter: ChatAdapter
    var chatData = ArrayList<ChatListItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        var headers = java.util.ArrayList<StompHeader>()
        headers.add(StompHeader("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjksInJvbGUiOiJVU0VSIiwiaXNzIjoiR2luZ3MgVXNlciBBdXRoIE1hbmFnZXIiLCJleHAiOjE1NDkxOTYxMzN9.OrlfMuYaMa2SqrXGcHlDRmttGOC1z7DiROKD4dsz2Ds"))

        //서버연결
        mStompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, "ws://52.78.243.92:8080/connect")
        //생명주기
        mStompClient.withClientHeartbeat(30000).withServerHeartbeat(30000)
        //소켓 연결
        mStompClient.connect(headers)
        //채팅방 연결
        mStompClient.topic("/topic/temp").subscribe { topicMessage ->
            {
                Log.d("SubscribeLog222", topicMessage.payload)

            }
            Log.v(TAG, "Recieve Json Data = " + topicMessage.payload)
            var jObject = JSONObject(topicMessage.payload)
            receiveMessage = jObject.getString("message")
            Log.v(TAG, "Receive Message = " + receiveMessage)

        }

        //메세지 보내기
        sendMessage = "메세지 보냅니다."
        Log.v(TAG, "Send Message = " + sendMessage)
        chatData.add(ChatListItem(0,"보낸 메세지 = " + sendMessage))
        var mTimeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        var jsonObject = JSONObject()
        jsonObject.put("message", sendMessage)

        mStompClient.send("/temp", jsonObject.toString() + mTimeFormat.format(Date()))
                .compose(applySchedulers())
                .subscribe({ Log.d("SendLog", "STOMP echo send successfully") }, { throwable ->
                    Log.e("SendLogError", "Error send STOMP echo", throwable)
                    Toast.makeText(applicationContext, throwable.message, Toast.LENGTH_SHORT).show()
                })

        while(receiveMessage.isEmpty()){
            Log.v(TAG,"수신 메세지 null")
            if(receiveMessage.isNotEmpty()){
                Log.v(TAG,"수신 메세지 not null2")
                Log.v(TAG,"채팅 데이터 크기 = " + chatData.size)
                chatData.add(ChatListItem(1,"받는 메세지 = " + receiveMessage))
                chatAdapter = ChatAdapter(chatData)
                chat_chat_recyclerview.adapter = chatAdapter
                chat_chat_recyclerview.layoutManager = LinearLayoutManager(this)
            }
        }
        Log.v(TAG,"수신 메세지 not null")



    }

    protected fun applySchedulers(): CompletableTransformer {
        return CompletableTransformer { upstream ->  upstream.unsubscribeOn(Schedulers.newThread()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())}
    }

    fun setRecyclerView() {


    }
}
