package me.lokmvne.auth.repository

import android.content.Context
import me.lokmvne.common.utils.Resource
import me.lokmvne.common.data.ToDoUser

interface GoogleAuthRepo {
    suspend fun signIn(context: Context): Resource<ToDoUser>

    fun googleFireBaseSignOut()

    fun checkIfGoogleFirebaseUserIsSignedIn(): Boolean


}