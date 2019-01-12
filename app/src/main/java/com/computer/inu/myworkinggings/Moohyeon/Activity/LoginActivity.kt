package com.computer.inu.myworkinggings.Moohyeon.Activity

import android.app.Activity
import android.content.SharedPreferences
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.computer.inu.myworkinggings.Jemin.Activity.MainActivity
import android.util.Log
import android.widget.Toast
import com.computer.inu.myworkinggings.Network.ApplicationController
import com.computer.inu.myworkinggings.Network.NetworkService
import com.computer.inu.myworkinggings.R
import com.computer.inu.myworkinggings.Seunghee.Post.PostLogInResponse
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.content.pm.PackageManager
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.support.v7.widget.LinearLayoutManager
import android.util.Base64
import android.widget.LinearLayout
import com.computer.inu.myworkinggings.Jemin.Activity.ChatActivity
import com.computer.inu.myworkinggings.Jemin.Activity.ChatActivity.ChatClass.mStompClient
import com.computer.inu.myworkinggings.Jemin.Activity.ChatActivity.ChatClass.sendMessage
import com.computer.inu.myworkinggings.Jemin.Activity.ChatActivity.ChatClass.sendMessageForCreate
import com.computer.inu.myworkinggings.Jemin.Adapter.ChatAdapter
import com.computer.inu.myworkinggings.Jemin.Data.ChatListItem
import com.computer.inu.myworkinggings.Jemin.RealmDB.ChatRoom
import com.computer.inu.myworkinggings.Jemin.RealmDB.User
import com.computer.inu.myworkinggings.Seunghee.db.SharedPreferenceController
import com.google.firebase.iid.FirebaseInstanceId
import com.kakao.util.helper.Utility.getPackageInfo
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_chat.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.toast
import org.json.JSONArray
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompHeader
import ua.naiksoftware.stomp.client.StompClient
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class LoginActivity : AppCompatActivity() {
    val FINISH_INTERVAL_TIME = 2000
    var backPressedTime : Long = 0
    var chatData = ArrayList<ChatListItem>()
    // 방 테이블
    var id : Int = 0
    var type : String = ""

    // 유저 테이블 리스트
    var idList = ArrayList<Int>()
    var name = ArrayList<String>()
    var job = ArrayList<String>()
    var image = ArrayList<String>()
    var list_cnt : Int = 0

    // Realm
    lateinit var realm : Realm

    override fun onBackPressed() {
        var tempTime = System.currentTimeMillis()
        var intervalTime = tempTime-backPressedTime

        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime) {
            super.onBackPressed()
        } else {
            backPressedTime = tempTime
            Toast.makeText(applicationContext, "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
        }
    }
    var userID : Int = 0

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)



        if(intent.getStringExtra("sender_id")!=null)
        { toast(intent.getStringExtra("sender_id"))}
        else{
        }
        if(SharedPreferenceController.getAutoAuthorization(this).isNotEmpty()){
            toast("자동로그인 성공")
            startActivity<MainActivity>() // 그사람 정보로 해야함
            finish()
        }
        val boardID = intent.getIntExtra("BoardId",-1)
        if(boardID> 0)
        {
            Log.v("카카오로그인", "으로들어옴")

            tv_login_login_button.setOnClickListener {
                startActivity<DetailBoardActivity>("BoardId" to boardID)
            }

        }else{
            //***로그인 통신***
            Log.v("카카오로그인", "으로들어오지않음")

            tv_login_login_button.setOnClickListener {
                startActivity<MainActivity>()
                //sendLink()
            }

        }


        tv_login_join_us.setOnClickListener {
            startActivity<SignUp1Activity>()
        }
        tv_login_about_gings.setOnClickListener {

        }

        //***로그인 통신***
        tv_login_login_button.setOnClickListener {
            //startActivity<MainActivity>()

            getLoginResponse()
        }

        //startActivity<BottomNaviActivity>()
        //getLoginResponse()
        //startActivity<BottomNaviActivity>()

        //getKeyHash(applicationContext)
        //Log.v("카카오",getKeyHash(applicationContext))
    }

    fun getKeyHash(context: Context): String? {
        val packageInfo = getPackageInfo(context, PackageManager.GET_SIGNATURES) ?: return null

        for (signature in packageInfo!!.signatures) {
            try {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                return Base64.encodeToString(md.digest(), Base64.NO_WRAP)
            } catch (e: NoSuchAlgorithmException) {

                //Log.w(FragmentActivity.TAG, "Unable to get MessageDigest. signature=$signature", e)
            }

        }
        return null
    }

    //로그인 통신
    private fun getLoginResponse() {
        if (et_login_id.text.toString().isNotEmpty() && et_login_pw.text.toString().isNotEmpty()) {

            val input_email = et_login_id.text.toString()
            val input_pw = et_login_pw.text.toString()
            val jsonObject: JSONObject = JSONObject()
            jsonObject.put("email", input_email)
            jsonObject.put("pwd", input_pw)
            jsonObject.put("fcm", FirebaseInstanceId.getInstance().getToken().toString()) //fcm 토큰받기
            val gsonObject: JsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject
            Log.v("LoginActivity", "확인")

            val postLogInResponse = networkService.postLoginResponse("application/json", gsonObject)

            postLogInResponse.enqueue(object : Callback<PostLogInResponse> {

                override fun onFailure(call: Call<PostLogInResponse>, t: Throwable) {
                    Log.e("Login fail", t.toString())
                }

                override fun onResponse(call: Call<PostLogInResponse>, response: Response<PostLogInResponse>) {
                    Log.v("LoginActivity", "확인2")


                    if (response.isSuccessful) {
                        Log.v("LoginActivity", "확인3")
                        if(response.body()!!.message == "로그인 성공"&&cb_login_auto_check_box.isChecked==true){
                            val token = response.body()!!.data.jwt.toString()
                            val userId = response.body()!!.data.userId
                            SharedPreferenceController.setAutoAuthorization(this@LoginActivity,token)
                            SharedPreferenceController.setAuthorization(this@LoginActivity,response.body()!!.data.jwt.toString())
                            SharedPreferenceController.setUserId(this@LoginActivity,response.body()!!.data.userId)

                            ChatConnect()

                            startActivity<MainActivity>()
                            finish()
                        }
                        else if(response.body()!!.message == "로그인 성공"&&cb_login_auto_check_box.isChecked==false){
                            SharedPreferenceController.setAuthorization(this@LoginActivity,response.body()!!.data.jwt.toString())
                            SharedPreferenceController.setUserId(this@LoginActivity,response.body()!!.data.userId)

                            ChatConnect()

                            startActivity<MainActivity>()
                            finish()
                        }else{
                            toast("회원 정보가 틀렸습니다.")
                        }

                    }
                    else{
                        Log.v("LoginActivity", "확인5")
                    }
                }
            })
        }
        else{
            Toast.makeText(applicationContext,"빈칸 없이 입력해주세요.", Toast.LENGTH_LONG).show()
        }

    }

    fun ChatConnect()
    {

        var headers = java.util.ArrayList<StompHeader>()

        headers.add(StompHeader("Authorization", SharedPreferenceController.getAuthorization(ctx)))
        //서버연결
        mStompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, "ws://52.78.243.92:8080/connect")
        //생명주기
        mStompClient.withClientHeartbeat(30000).withServerHeartbeat(30000)
        //소켓 연결
        mStompClient.connect(headers)
        // 채팅방 구독(연결 / 채널 이름이 토픽)
        mStompClient.topic("/user/queue/chat-notice").subscribe { topicMessage ->
            {
                Log.d("SubscribeLog222 = ", topicMessage.payload)
            }
            Log.d("SubscribeLog222 = ", topicMessage.payload)

            var receiveData = JSONObject(topicMessage.payload)
            var receiveRoomData = JSONObject(receiveData.getString("chatRoom"))
            Log.v("ChatMessage", "받는 타입 = " + receiveRoomData.getString("type"))


            id = receiveRoomData.getInt("id")
            type = receiveRoomData.getString("type")

            Log.v("aasdf","채팅 확인 id = " + id)
            Log.v("aasdf","채팅 확인 type = " + type)

            var userList = JSONArray(receiveRoomData.getString("users"))
            list_cnt = userList.length()

            for(i in 0 .. list_cnt-1){
                var jsonObject : JSONObject = userList.getJSONObject(i)
                idList.add(jsonObject.getInt("id"))
                Log.v("asdf","유저 id = " + idList)
                name.add(jsonObject.getString("name"))
                Log.v("asdf","유저 name = " + name)
                job.add(jsonObject.getString("job"))
                Log.v("asdf","유저 job = " + job)
                image.add(jsonObject.getString("image"))
                Log.v("asdf","유저 image = " + image)
            }

            //insertChatRoom()
            System.out.println("들어간다")
            createChatRoom(receiveData)
            System.out.println("갔다왔따")
            // 메시지 보내기 버튼 누르고나서

        }
    }

    fun insertChatRoom(){
        Realm.init(this)
        realm = Realm.getDefaultInstance()
        realm.beginTransaction()

        var nextNum : Int = 0

        val cr = realm.createObject(ChatRoom::class.java, id)
        cr.type = type
        realm.commitTransaction();

        for(i in 0 ..idList.size-1){
            realm.beginTransaction()
            val currentIdNum = realm.where(User::class.java).max("userID_roomID")
            if(currentIdNum == null) {
                nextNum = 1;
            } else {
                nextNum = currentIdNum.toInt() + 1;
            }

            val us = realm.createObject(User::class.java, nextNum)
            us.id = idList[i]
            us.name = name[i]
            us.job = job[i]
            us.image = image[i]
            us.roomID = id
            realm.commitTransaction();
        }

        Log.v("asdf","확인3")
/*
        for(i in 0 .. list_cnt-1){
            cr.users[i]!!.id = idList[i]
            cr.users[i]!!.name = name[i]
            cr.users[i]!!.job = job[i]
            cr.users[i]!!.image = image[i]
        }
*/
        Log.v("asdf","확인4")

    }

    fun selectChatMessage() {

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

    fun createChatRoom(receiveData : JSONObject)
    {
        var url = "/topic/room/" + receiveData.getJSONObject("chatRoom").getString("id")

        //채팅방에서 채팅하기
        mStompClient.topic(url).subscribe { topicMessage ->
            {
                Log.d("SubscribeLog333 = ", topicMessage.payload)
            }
            Log.d("SubscribeLog333 = ", topicMessage.payload)
            // 메시지 보내기 버튼 누르고나서
            var sendData = JSONObject(topicMessage.payload)
            var writerId = sendData.getString("writerId")
            Log.v("Asdf","메시지 보내고 writeId = " + writerId)
            var message = sendData.getString("message")
            Log.v("Asdf","메시지 보내고 message = " + message)
            var writeAt = sendData.getString("writeAt")
            Log.v("Asdf","메시지 보내고 writeAt = " + writeAt)
            ChatActivity.chatActivity.chatData.add(ChatListItem(Integer.parseInt(writerId), writerId, "null", message, writeAt))
            ChatActivity.chatActivity.chatAdapter = ChatAdapter(ctx, ChatActivity.chatActivity.chatData)
            setAdapter()

        }
        val intent = Intent(applicationContext,ChatActivity::class.java)
        intent.putExtra("roomId",receiveData.getJSONObject("chatRoom").getString("id"))
        startActivity(intent)
    }

    fun setAdapter(){
        object : Thread() {

            override fun run() {

                Log.v("Chat","스레드 런")
                val msg = handler.obtainMessage()
                handler.sendMessage(msg)
            }
        }.start()

    }
    internal val handler: Handler = object : Handler(Looper.getMainLooper()) {

        override fun handleMessage(msg: Message) {
            Log.v("Chat","핸들러 런")
            ChatActivity.chatActivity.chat_chat_recyclerview.adapter =  ChatActivity.chatActivity.chatAdapter
            ChatActivity.chatActivity.chat_chat_recyclerview.layoutManager = LinearLayoutManager(ctx)
            // 원래 하고싶었던 일들 (UI변경작업 등…)
        }
    }




}