package com.example.retrofitgradebook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import com.example.retrofitgradebook.ApiUtils
import com.example.retrofitgradebook.databinding.ActivityDetayBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetayActivity : AppCompatActivity() {

    private lateinit var ndi:NotlarDaoInterface
    private lateinit var not:Notlar
    private lateinit var binding : ActivityDetayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarNotDetay.title = "Not Detay"
        setSupportActionBar(binding.toolbarNotDetay)

        ndi = ApiUtils.getNotlarDaoInterface()

        not = intent.getSerializableExtra("nesne") as Notlar

        binding.editTextDers.setText(not.ders_adi)
        binding.editTextNot1.setText((not.not1).toString())
        binding.editTextNot2.setText((not.not2).toString())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.action_sil -> {
                Snackbar.make(binding.toolbarNotDetay,"Silinsin mi?", Snackbar.LENGTH_SHORT)
                    .setAction("EVET"){

                        ndi.notSil(not.not_id).enqueue(object : Callback<CRUDCevap> {
                            override fun onResponse(call: Call<CRUDCevap>?, response: Response<CRUDCevap>?) {
                            }
                            override fun onFailure(call: Call<CRUDCevap>?, t: Throwable?) {
                            }
                        })

                        startActivity(Intent(this@DetayActivity,MainActivity::class.java))
                        finish()
                    }.show()

                return true
            }
            R.id.action_duzenle -> {

                val ders_adi = binding.editTextDers.text.toString().trim()
                val not1 = binding.editTextNot1.text.toString().trim()
                val not2 = binding.editTextNot2.text.toString().trim()

                if(TextUtils.isEmpty(ders_adi)){
                    Snackbar.make(binding.toolbarNotDetay,"Ders adını giriniz", Snackbar.LENGTH_SHORT).show()
                    return false
                }

                if(TextUtils.isEmpty(not1)){
                    Snackbar.make(binding.toolbarNotDetay,"1. notu giriniz", Snackbar.LENGTH_SHORT).show()
                    return false
                }

                if(TextUtils.isEmpty(not2)){
                    Snackbar.make(binding.toolbarNotDetay,"2. notu giriniz", Snackbar.LENGTH_SHORT).show()
                    return false
                }

                ndi.notGuncelle(not.not_id,ders_adi,not1.toInt(),not2.toInt()).enqueue(object :
                    Callback<CRUDCevap> {
                    override fun onResponse(call: Call<CRUDCevap>?, response: Response<CRUDCevap>?) {
                    }

                    override fun onFailure(call: Call<CRUDCevap>?, t: Throwable?) {
                    }
                })

                startActivity(Intent(this@DetayActivity,MainActivity::class.java))
                finish()
                return true
            }

            else -> return false
        }
    }
}