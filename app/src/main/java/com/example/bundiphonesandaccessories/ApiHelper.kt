package com.example.bundiphonesandaccessories

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import com.loopj.android.http.RequestParams

import cz.msebera.android.httpclient.Header
import cz.msebera.android.httpclient.entity.StringEntity
import org.json.JSONArray
import org.json.JSONObject

class ApiHelper(var context: Context) {
    //POST
    fun post(api: String, params: RequestParams) {
        Toast.makeText(context, "Please wait for response", Toast.LENGTH_LONG).show()
        val client = AsyncHttpClient()

        client.post(api, params, object : JsonHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                response: JSONObject?
            ) {
                val message = response?.optString("message") ?: response.toString()
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseString: String?,
                throwable: Throwable?
            ) {
                val error = responseString ?: throwable?.message ?: "Unknown Error"
                Toast.makeText(context, "Error: $error (Status: $statusCode)", Toast.LENGTH_LONG).show()
            }
        })
    }

    //Requires Access Token
    fun post_login(api: String, params: RequestParams) {
        Toast.makeText(context, "Please wait for response", Toast.LENGTH_LONG).show()
        val client = AsyncHttpClient()

        client.post(api, params, object : JsonHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                response: JSONObject?
            ) {
                val message = response?.optString("message")
                if (response?.has("user") == true) {
                    val user = response.optJSONObject("user")
                    val username = user?.optString("username") ?: ""
                    val email = user?.optString("email") ?: ""

                    // 🔐 Save to SharedPreferences
                    val prefs = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
                    val editor = prefs.edit()
                    editor.putString("username", username)
                    editor.putString("email", email)
                    editor.apply()

                    Toast.makeText(context, "Welcome $username", Toast.LENGTH_LONG).show()

                    // Redirect to Dashboard
                    val intent = Intent(context, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    context.startActivity(intent)
                } else {
                    Toast.makeText(context, "$message", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseString: String?,
                throwable: Throwable?
            ) {
                val error = responseString ?: throwable?.message ?: "Unknown Error"
                Toast.makeText(context, "Login Failed: $error", Toast.LENGTH_LONG).show()
            }
        })
    }

    fun loadProducts(url: String, recyclerView: RecyclerView, progressBar: ProgressBar? = null) {
        progressBar?.visibility = View.VISIBLE
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        val client = AsyncHttpClient()

        client.get(url, object : JsonHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                response: JSONArray
            ) {
                progressBar?.visibility = View.GONE
                val productList = ProductAdapter.fromJsonArray(response)
                val adapter = ProductAdapter(productList)
                recyclerView.adapter = adapter
            }

            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                response: JSONObject
            ) {
                progressBar?.visibility = View.GONE
                val array = response.optJSONArray("products") ?: response.optJSONArray("data")
                if (array != null) {
                    val productList = ProductAdapter.fromJsonArray(array)
                    val adapter = ProductAdapter(productList)
                    recyclerView.adapter = adapter
                } else {
                    val message = response.optString("message", "No products found")
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseString: String?,
                throwable: Throwable?
            ) {
                progressBar?.visibility = View.GONE
                val error = responseString ?: throwable?.message ?: "Unknown Error"
                Toast.makeText(context, "Failed to load products: $error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    //GET
    fun get(api: String, callBack: CallBack) {
        val client = AsyncHttpClient()
        //GET to API
        client.get(api, object : JsonHttpResponseHandler() {
                //When a JSON array is Returned
                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    response: JSONArray
                ) {
                    //Push the response to Callback Interface
                    callBack.onSuccess(response)
                }

                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    response: JSONObject?
                ) {
                    //Push the response to Callback Interface
                    callBack.onSuccess(response)
                }

                override fun onFailure(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    responseString: String?,
                    throwable: Throwable?
                ) {
                    callBack.onFailure(responseString ?: throwable?.message)
                }
            })

    }//END GET


    //PUT
    fun put(api: String, jsonData: JSONObject) {
        Toast.makeText(context, "Please Wait for response", Toast.LENGTH_LONG).show()
        val client = AsyncHttpClient()
        val con_body = StringEntity(jsonData.toString())
        //PUT to API
        client.put(context, api, con_body, "application/json",
            object : JsonHttpResponseHandler() {
                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    response: JSONObject?
                ) {
                    Toast.makeText(context, "Response $response ", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    throwable: Throwable?,
                    errorResponse: JSONObject?
                ) {
                    Toast.makeText(
                        context,
                        "Error Occurred: " + (throwable?.message ?: "Unknown"),
                        Toast.LENGTH_LONG
                    ).show()

                }
            })
    }//END PUT

    //DELETE
    fun delete(api: String, jsonData: JSONObject) {
        Toast.makeText(context, "Please Wait for response", Toast.LENGTH_LONG).show()
        val client = AsyncHttpClient()
        val con_body = StringEntity(jsonData.toString())
        //DELETE to API
        client.delete(context, api, con_body, "application/json",
            object : JsonHttpResponseHandler() {
                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    response: JSONObject?
                ) {
                    Toast.makeText(context, "Response $response ", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    throwable: Throwable?,
                    errorResponse: JSONObject?
                ) {
                    Toast.makeText(
                        context,
                        "Error Occurred: " + (throwable?.message ?: "Unknown"),
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }//END DELETE

    //Interface to used by the GET function above.
    //All APis responses either JSON array [], JSON Object {}, String ""
    //Are brought here
    interface CallBack {
        fun onSuccess(result: JSONArray?)
        fun onSuccess(result: JSONObject?)
        fun onFailure(result: String?)
    }

}
