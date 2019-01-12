package com.computer.inu.myworkinggings.Jemin.Activity

import android.content.Intent
import android.os.*
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.computer.inu.myworkinggings.Jemin.Activity.ChatActivity.ChatClass.mStompClient
import com.computer.inu.myworkinggings.Jemin.Adapter.ChatAdapter
import com.computer.inu.myworkinggings.Jemin.Data.ChatListItem
import com.computer.inu.myworkinggings.Jemin.RealmDB.ChatMessage
import com.computer.inu.myworkinggings.Jemin.RealmDB.ChatRoom
import com.computer.inu.myworkinggings.Jemin.RealmDB.User
import com.computer.inu.myworkinggings.R
import com.computer.inu.myworkinggings.Seunghee.db.SharedPreferenceController
import io.reactivex.CompletableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_chat.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.startActivity
import org.json.JSONObject
import ua.naiksoftware.stomp.client.StompClient
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ChatActivity : AppCompatActivity() {
    val TAG = "ChatActivity"
    var sendMessage : String = ""
    var receiveMessage : String = ""
    lateinit var chatAdapter: ChatAdapter
    var chatData = ArrayList<ChatListItem>()
    lateinit var realm : Realm

    object ChatClass {
        lateinit var mStompClient : StompClient
        fun sendMessage(roomId : String, message : String)
        {
            System.out.println("룸 아이디 생성")
            var url = "/room/" + roomId
            var jsonObject = JSONObject()
            jsonObject.put("type", "TEXT")
            jsonObject.put("message",message)


            mStompClient.send(url, jsonObject.toString())
                    .compose(applySchedulers())
                    .subscribe({ Log.d("SendLog", "STOMP echo send successfully") }, { throwable ->
                        Log.e("SendLogError", "Error send STOMP echo", throwable)
                    })
        }

        fun sendMessageForCreate(userId : Int){
            //채팅방 생성을 위해

            var mTimeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
            var jsonObject = JSONObject()
            jsonObject.put("opponentId", userId)

            mStompClient.send("/chat/create", jsonObject.toString() + mTimeFormat.format(Date()))
                    .compose(applySchedulers())
                    .subscribe({ Log.d("SendLog", "STOMP echo send successfully") }, { throwable ->
                        Log.e("SendLogError", "Error send STOMP echo", throwable)
                    })
        }

        fun applySchedulers(): CompletableTransformer {
            return CompletableTransformer { upstream ->  upstream.unsubscribeOn(Schedulers.newThread()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())}
        }
    }


    override fun onBackPressed() {
        mStompClient.disconnect()
        super.onBackPressed()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Realm.init(this)
        realm = Realm.getDefaultInstance()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        chatActivity = this

        chat_test_send_btn.setOnClickListener {

        }
        chat_test_receive_btn.setOnClickListener {
            getChatMessage()
        }

        chat_send_btn.setOnClickListener {
            var message = chat_message_edit.text.toString()
            System.out.println("제민이 : " + intent.getStringExtra("roomId"))
            System.out.println("제민이한테 보낸다 : " + message)
            chatData.add(ChatListItem(SharedPreferenceController.getUserId(ctx),"null","null",message, "null"))
            chatAdapter = ChatAdapter(applicationContext,chatData)
            chat_chat_recyclerview.adapter = chatAdapter
            chat_chat_recyclerview.layoutManager = LinearLayoutManager(this)
            chat_send_btn.setText("")

            ChatClass.sendMessage(intent.getStringExtra("roomId"),message)
        }
    }

    internal val handler: Handler = object : Handler(Looper.getMainLooper()) {

        override fun handleMessage(msg: Message) {
            Log.v(TAG,"핸들러 런")
            chat_chat_recyclerview.adapter = chatAdapter
            chat_chat_recyclerview.layoutManager = LinearLayoutManager(applicationContext)
            // 원래 하고싶었던 일들 (UI변경작업 등…)
        }
    }




    fun getChatMessage() {
        Realm.init(this)
        realm = Realm.getDefaultInstance()

        var roommaxNum = Integer.parseInt(realm.where(User::class.java).max("id").toString())
        Log.v("adsf","방 최대 숫자 = " + roommaxNum)
        var maxNum = Integer.parseInt(realm.where(User::class.java).max("userID_roomID").toString())
        Log.v("adsf","유저 최대 숫자 = " + maxNum)

        val chatRoomList = realm.where(ChatRoom::class.java)
                //.lessThan("roomId", 10)
                .findAll()

        Log.v("RealmDB", "채팅 방 전체 리스트 값 = " + chatRoomList.toString())

        val userList = realm.where(User::class.java)
                //.lessThan("roomId", 10)
                .findAll()

        Log.v("RealmDB", "유저 전체 리스트 값 = " + userList.toString())

        // Log.v("RealmDB", "채팅 시각1 = " + puppies.get(0)!!.writeAt);
        //Log.v("RealmDB", "채팅 시각2 = " + puppies.get(1)!!.writeAt);
        //Log.v("RealmDB", "채팅 시각3 = " + puppies.get(2)!!.writeAt);
        //val result = realm.where(ChatMessage::class.java).equalTo("roomId", insertID).findFirst()!!.writeAt
        // Log.v("adf", "응답 메시지 = $result")

    }

    companion object {
        lateinit var chatActivity: ChatActivity
    }


}