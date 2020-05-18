package com.example.tacheliste_volley

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject
import java.net.URISyntaxException

class MainActivity : AppCompatActivity() {
    val urltodos : String = "https://jsonplaceholder.typicode.com/users/1/todos"
    lateinit var mRequestQueue : RequestQueue
    var dataTodos  : MutableList<ToDo> = mutableListOf<ToDo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        try {

            mRequestQueue = Volley.newRequestQueue(applicationContext)
            Log.d(ContentValues.TAG,"9bal jsonParseeeeee")

            jsonParse(object : VolleyCallBack {
                override fun onSuccess() {
                    Log.d(ContentValues.TAG,"bbbbbbbbbbbbbbbbbbbbbbb")
                    initData()
                }
            })

        } catch ( uriSyntaxException : URISyntaxException) {
            uriSyntaxException.printStackTrace()
        }
    }

    private fun jsonParse(callback:VolleyCallBack)  {
        val mJsonObjectRequest = object : StringRequest(
            Method.GET,urltodos,
            Response.Listener { response ->

                var jsonArray= JSONArray(response)
                var gson= Gson()
                var jsonObject= JSONObject()
                Log.d(ContentValues.TAG,"9ballllll onSuccessssssssssssssssssssssss")


                for (i in 0 until jsonArray.length()) {
                    jsonObject = jsonArray.getJSONObject(i)
                    Log.d(ContentValues.TAG,"lalaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                    dataTodos.add(gson.fromJson(jsonObject.toString(), ToDo::class.java))
                }

                callback.onSuccess()

                //it : JSON Object
            }, Response.ErrorListener {
                it.printStackTrace()
            }) {}

        mRequestQueue.add(mJsonObjectRequest)
    }

    interface VolleyCallBack {
        fun onSuccess()
    }

    fun initData(){
        val adapterTodo = TacheAdapter(dataTodos)
        val lineaireLayout= LinearLayoutManager(applicationContext)

        myRecyclerView.apply {
            adapter = adapterTodo
            layoutManager = lineaireLayout
        }
    }
}
