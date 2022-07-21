package com.example.android_graphql.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.apollographql.apollo3.exception.ApolloException
import com.example.android_graphql.*
import com.example.android_graphql.network.GraphQl
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
    }

    private fun initViews() {
        getUserList()
    }

    private fun getUserList() {
        lifecycleScope.launch launchWhenResumed@{
            val response = try {
                GraphQl.get().query(UsersListQuery(10)).execute()
            } catch (e:ApolloException){
                Log.d("MainActivity", e.toString())
                return@launchWhenResumed
            }

            val users = response.data?.users
            Log.d("MainActivity", users!!.size.toString())
        }
    }

    private fun insertUser(name: String, rocket: String, twitter: String) {
        lifecycleScope.launch launchWhenResumed@{
            val result = try {
                GraphQl.get().mutation(
                    InsertUserMutation(name, rocket, twitter)
                ).execute()
            } catch (e: ApolloException) {
                Log.d("MainActivity", e.toString())
                return@launchWhenResumed
            }
            Log.d("MainActivity", result.toString())
        }
    }

    private fun updateUser(id: String, name: String, rocket: String, twitter: String) {
        lifecycleScope.launch launchWhenResumed@{
            val result = try {
                GraphQl.get().mutation(
                    UpdateUserMutation(id, name, rocket, twitter)
                ).execute()
            } catch (e: ApolloException) {
                Log.d("MainActivity", e.toString())
                return@launchWhenResumed
            }
            Log.d("MainActivity", result.toString())
        }
    }

    private fun deleteUser(id: String) {
        lifecycleScope.launch launchWhenResumed@{
            val result = try {
                GraphQl.get().mutation(DeleteUserMutation(id)).execute()
            } catch (e: ApolloException) {
                Log.d("MainActivity", e.toString())
                return@launchWhenResumed
            }
            Log.d("MainActivity", result.toString())
        }
    }
}