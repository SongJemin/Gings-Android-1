package com.computer.inu.myworkinggings.Jemin.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.computer.inu.myworkinggings.R

class TestActivity : AppCompatActivity() {
/*
    override fun onClick(v: View?)
    {
        //val idx = Int = main_collection_list.getChildAdapterPosition(v)
    }
    lateinit var pokemonRealm : Realm
    lateinit var pokemonCollections : ArrayList<PokemonVO>
    lateinit var pokemonVO : PokemonVO
    lateinit var collectionAdapter : CollecationAdapter
    lateinit var id : String
*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

      //  init()

       // main_instert_btn.setOnClickListener {
          //  insertPokemonList()
           // pokemonCollections = getPokemonList().toMutableList() as ArrayList<PokemonVO>

          //  collectionAdapter = CollecationAdapter(pokemonCollections)
          //  main_collection_list.adapter = collectionAdapter
       // }

      //  main_logout_btn.setOnClickListener{
    //    }
    }
/*

    fun init(){
        Realm.init(this)
        pokemonRealm = Realm.getDefaultInstance()
        id = intent.getStringExtra("id")

        pokemonCollections = ArrayList()
        pokemonCollections = getPokemonList().toMutableList() as ArrayList<PokemonVO>
        main_collection_list.layoutManager

        collectionAdapter = CollecationAdapter(pokemonCollections)
        main_collection_list.adapter = collectionAdapter

    }

    fun getPokemonList() : RealmResults<PokemonVO> {
        return pokemonRealm.where(PokemonVO::class.java)
                .equalTo("user", id).findAll()
    }

    fun insertPokemonList(){
        pokemonVO = PokemonVO()
        pokemonVO.num = main_num_edit.text.toString().toInt()
        pokemonVO.name = main_name_edit.text.toString()
        pokemonVO.type = main_type_edit.text.toString()
        pokemonVO.user = id;

        pokemonRealm.beginTransaction()
        pokemonRealm.copyToRealm(pokemonVO)
        pokemonRealm.commitTransaction()

        Toast.makeText(this, "등록성공", Toast.LENGTH_SHORT).show()

    }

    fun deletePokemonList(name : String)
    {
        val result = pokemonRealm.where(PokemonVO::class.java)
                .equalTo("name", name)
                .findAll()

        if(result.isEmpty()){
            return
        }

        pokemonRealm.beginTransaction()
        result.deleteAllFromRealm()
        pokemonRealm.commitTransaction()

        Toast.makeText(this, "삭제성공", Toast.LENGTH_SHORT).show()
        pokemonCollections = getPokemonList().toMutableList() as ArrayList<PokemonVO>

        collectionAdapter = CollecationAdapter(pokemonCollections)
        collectionAdapter.setOnItemClickListener(this)
        main_collection_list.adapter = collectionAdapter


    }
*/
}
