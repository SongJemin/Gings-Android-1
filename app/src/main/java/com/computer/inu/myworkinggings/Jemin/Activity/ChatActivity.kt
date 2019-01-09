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
import com.computer.inu.myworkinggings.Jemin.Data.UserData
import com.computer.inu.myworkinggings.Jemin.Get.Response.GetOtherInformResponse
import com.computer.inu.myworkinggings.Network.ApplicationController
import com.computer.inu.myworkinggings.Network.NetworkService
import com.computer.inu.myworkinggings.R
import io.reactivex.CompletableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.fragment_messagesend1.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompHeader
import ua.naiksoftware.stomp.client.StompClient
import java.text.SimpleDateFormat
import java.util.*

class ChatActivity : AppCompatActivity() {

    val TAG = "ChatActivity"

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    lateinit var mStompClient : StompClient
    var sendMessage : String = ""
    var receiveMessage : String = ""
    lateinit var chatAdapter: ChatAdapter
    var chatData = ArrayList<ChatListItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val pref = applicationContext.getSharedPreferences("auto", Activity.MODE_PRIVATE)
        var userID : Int = 0
        userID = pref.getInt("userID",0)

        sendMessage = "메세지 보냅니다."
        chat_send_btn.setOnClickListener {
            sendMessage = chat_message_edit.text.toString()
            chat_message_edit.setText("")
            sendMessage()
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
        mStompClient.topic("/topic/temp").subscribe { topicMessage ->
            {
                Log.d("SubscribeLog222", topicMessage.payload)
            }
            Log.v(TAG, "Recieve Json Data = " + topicMessage.payload)
            var jObject = JSONObject(topicMessage.payload)
            receiveMessage = jObject.getString("message")
            Log.v(TAG, "Receive Message = " + receiveMessage)


            if(receiveMessage.isNotEmpty()){
                Log.v(TAG,"수신 메세지 not null2")
                Log.v(TAG,"채팅 데이터 크기 = " + chatData.size)
                chatData.add(ChatListItem(1,"받는 메세지 = " + receiveMessage))

                chatAdapter = ChatAdapter(chatData)
                setAdapter()

            }

        }

        // edittext 클릭 시 채팅리스트 맨 아래로 가도록
        if (Build.VERSION.SDK_INT >= 11) {
            chat_chat_recyclerview.addOnLayoutChangeListener(View.OnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
                if (bottom < oldBottom) {
                    chat_chat_recyclerview.postDelayed(Runnable {
                        // 대화 내용이 없는 경우
                        if(chat_chat_recyclerview.getAdapter()==null){
                        }
                        // 대화 내용이 있는 경우
                        else{
                            chat_chat_recyclerview.smoothScrollToPosition(
                                    chat_chat_recyclerview.getAdapter()!!.getItemCount() - 1)
                        }
                    }, 100)
                }
            })
        }

        sendMessage()
/*
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
        */
        Log.v(TAG,"수신 메세지 not null")


    }



    protected fun applySchedulers(): CompletableTransformer {
        return CompletableTransformer { upstream ->  upstream.unsubscribeOn(Schedulers.newThread()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())}
    }

    fun setRecyclerView() {


    }
    fun sendMessage(){
        //메세지 보내기
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
        chatAdapter = ChatAdapter(chatData)
        chat_chat_recyclerview.adapter = chatAdapter
        chat_chat_recyclerview.layoutManager = LinearLayoutManager(this)
        chat_chat_recyclerview.scrollToPosition(chatData.size-1)
    }

    fun setAdapter(){
        object : Thread() {

            override fun run() {

                Log.v(TAG,"스레드 런")
                val msg = handler.obtainMessage()

                handler.sendMessage(msg)


            }

        }.start()

    }

    internal val handler: Handler = object : Handler(Looper.getMainLooper()) {

        override fun handleMessage(msg: Message) {
            Log.v(TAG,"핸들러 런")
            chat_chat_recyclerview.adapter = chatAdapter
            chat_chat_recyclerview.layoutManager = LinearLayoutManager(applicationContext)
            chat_chat_recyclerview.scrollToPosition(chatData.size-1)
            // 원래 하고싶었던 일들 (UI변경작업 등...)

        }

    }

}