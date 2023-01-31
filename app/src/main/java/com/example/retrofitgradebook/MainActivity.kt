package com.example.retrofitgradebook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofitgradebook.databinding.ActivityDetayBinding
import com.example.retrofitgradebook.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var notlarListe:ArrayList<Notlar>
    private lateinit var adapter:NotlarAdapter
    private lateinit var ndi:NotlarDaoInterface
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.title = "Notlar Uygulaması"
        setSupportActionBar(binding.toolbar)

        binding.rv.setHasFixedSize(true)
        binding.rv.layoutManager = LinearLayoutManager(this)

        ndi = ApiUtils.getNotlarDaoInterface()

        tumNotlar()

        binding.fab.setOnClickListener {

            startActivity(Intent(this@MainActivity,NotKayitActivity::class.java))

        }
    }

    override fun onBackPressed() {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    fun tumNotlar(){

        ndi.tumNotlar().enqueue(object : Callback<NotlarCevap>{

            override fun onResponse(call: Call<NotlarCevap>?, response: Response<NotlarCevap>?) {

                if(response != null){
                    val liste = response.body().notlar

                    adapter = NotlarAdapter(this@MainActivity,liste)

                    binding.rv.adapter = adapter

                    var toplam = 0

                    for(n in liste){
                        toplam = toplam + (n.not1+n.not2)/2
                    }

                    binding.toolbar.subtitle = "Ortalama : ${toplam/liste.size}"

                }
            }

            override fun onFailure(call: Call<NotlarCevap>?, t: Throwable?) {

            }

        })

    }
}