package com.example.retrofitgradebook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import com.google.android.material.snackbar.Snackbar
import com.example.retrofitgradebook.ApiUtils

import com.example.retrofitgradebook.databinding.ActivityNotKayitBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotKayitActivity : AppCompatActivity() {

    private lateinit var ndi:NotlarDaoInterface
    private lateinit var binding : ActivityNotKayitBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotKayitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarNotKayit.title = "Not Kayıt"
        setSupportActionBar(binding.toolbarNotKayit)

        ndi = ApiUtils.getNotlarDaoInterface()

        binding.buttonKaydet.setOnClickListener {

            val ders_adi = binding.editTextDers.text.toString().trim()
            val not1 = binding.editTextNot1.text.toString().trim()
            val not2 = binding.editTextNot2.text.toString().trim()

            if(TextUtils.isEmpty(ders_adi)){
                Snackbar.make(binding.toolbarNotKayit,"Ders adını giriniz",Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(TextUtils.isEmpty(not1)){
                Snackbar.make(binding.toolbarNotKayit,"1. notu giriniz",Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(TextUtils.isEmpty(not2)){
                Snackbar.make(binding.toolbarNotKayit,"2. notu giriniz",Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            ndi.notEkle(ders_adi,not1.toInt(),not2.toInt()).enqueue(object : Callback<CRUDCevap>{
                override fun onResponse(call: Call<CRUDCevap>?, response: Response<CRUDCevap>?) {
                }

                override fun onFailure(call: Call<CRUDCevap>?, t: Throwable?) {
                }
            })

            startActivity(Intent(this@NotKayitActivity,MainActivity::class.java))
            finish()

        }
    }
}