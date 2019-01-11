package com.computer.inu.myworkinggings.Jemin.Activity;

import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.computer.inu.myworkinggings.Jemin.Data.ChatMessage;
import com.computer.inu.myworkinggings.Jemin.Data.PokemonVO;
import com.computer.inu.myworkinggings.R;
import com.computer.inu.myworkinggings.RealmDB.Dog;

import java.util.ArrayList;

import gun0912.tedbottompicker.TedBottomPicker;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class Test2Activity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        Realm.init(this);
        //getDog();
        getChatMessage();

    }
    void getDog(){
        Realm realm = Realm.getDefaultInstance();
        //realm.beginTransaction();
       // PokemonVO dg = realm.createObject(PokemonVO.class, "파이리");
       // dg.setName("포켓몬");
       // dg.setNum(5);
        //dg.setType("물");
       // dg.setUser("제민");
       // realm.commitTransaction();

        String result = realm.where(PokemonVO.class).equalTo("user","제민").findFirst().getName();
        Log.v("adf","이름 = " + result);
        /*
        RealmResults<Dog> puppies = realm.where(Dog.class)
                .lessThan("age", 2)
                .findAll();

        Log.v("RealmDB", "도그 번호 = " + puppies.get(0).age);
        Log.v("RealmDB", "도그 이름 = " + puppies.get(0).name);
        realm.beginTransaction();
        realm.copyToRealm(dog);
        realm.commitTransaction();
        dog.name = "Rex2";
        dog.age = 12;
        */
    }

    void getChatMessage(){
        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();
         ChatMessage cm = realm.createObject(ChatMessage.class, 7);
         cm.setRoomId(2);
         cm.setWriteAt("2019-01-12");
         realm.commitTransaction();

        String result = realm.where(ChatMessage.class).equalTo("roomId",2).findFirst().getWriteAt();
        Log.v("adf","응답 메시지 = " + result);

        RealmResults<Dog> puppies = realm.where(Dog.class)
                .lessThan("age", 2)
                .findAll();
/*
        Log.v("RealmDB", "도그 번호 = " + puppies.get(0).age);
        Log.v("RealmDB", "도그 이름 = " + puppies.get(0).name);
        realm.beginTransaction();
        realm.copyToRealm(dog);
        realm.commitTransaction();
        dog.name = "Rex2";
        dog.age = 12;
        */
    }

}
