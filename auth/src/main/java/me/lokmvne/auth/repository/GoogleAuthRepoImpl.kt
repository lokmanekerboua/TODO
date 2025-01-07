package me.lokmvne.auth.repository

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import me.lokmvne.auth.R
import me.lokmvne.auth.data.GoogleAuthUiClient
import me.lokmvne.common.data.ToDoUser
import me.lokmvne.common.utils.Resource
import javax.inject.Inject

class GoogleAuthRepoImpl @Inject constructor(
    private val googleAuthUiClient: GoogleAuthUiClient,
    @ApplicationContext private val appContext: Context
) : GoogleAuthRepo {
    override suspend fun signIn(context: Context): Resource<ToDoUser> {
        googleAuthUiClient.signIn(context).let {
            when (it) {
                is Resource.Success -> {
                    it.data.let { data ->
                        googleAuthUiClient.handleSignIn(credentialResponse = data!!).let { res ->
                            when (res) {
                                is Resource.Success -> {
                                    return Resource.Success(res.data!!)
                                }

                                is Resource.Error -> {
                                    return Resource.Error(res.message ?: appContext.getString(R.string.error_occurred))
                                }

                                else -> {
                                    return Resource.Error(res.message ?: appContext.getString(R.string.error_occurred))
                                }
                            }
                        }
                    }
                }

                is Resource.Error -> {
                    return Resource.Error(it.message ?: appContext.getString(R.string.error_occurred))
                }

                else -> {
                    return Resource.Error(it.message ?: appContext.getString(R.string.error_occurred))
                }
            }
        }
    }

    override fun googleFireBaseSignOut() {
        googleAuthUiClient.googleFireBaseSignOut()
    }

    override fun checkIfGoogleFirebaseUserIsSignedIn(): Boolean {
        return googleAuthUiClient.checkIfGoogleFirebaseUserIsSignedIn()
    }
}