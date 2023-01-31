package com.example.retrofitgradebook

class ApiUtils {

    companion object{

        val BASE_URL = "http://kasimadalan.pe.hu/"

        fun getNotlarDaoInterface(): NotlarDaoInterface {
            return RetrofitClient.getClient(BASE_URL).create(NotlarDaoInterface::class.java)
        }
    }
}