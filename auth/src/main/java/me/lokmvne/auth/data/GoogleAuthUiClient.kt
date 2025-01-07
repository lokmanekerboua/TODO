package me.lokmvne.auth.data

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.NoCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import me.lokmvne.auth.BuildConfig
import me.lokmvne.auth.R
import me.lokmvne.common.data.ToDoUser
import me.lokmvne.common.utils.Resource
import javax.inject.Inject

class GoogleAuthUiClient @Inject constructor(
    private val credentialManager: CredentialManager,
    @ApplicationContext private val appContext: Context
) {
    private val firebaseAuth = Firebase.auth

    suspend fun signIn(context: Context): Resource<GetCredentialResponse> {
        try {
            val credential = credentialManager.getCredential(
                request = buildGoogleSignInRequest(),
                context = context
            )
            return Resource.Success(credential)
        } catch (e: GetCredentialCancellationException) {
            return Resource.Error(appContext.getString(R.string.error_cancel_SignIn))
        } catch (e: NoCredentialException) {
            return Resource.Error(appContext.getString(R.string.error_NoAccount))
        } catch (e: Exception) {
            return Resource.Error(appContext.getString(R.string.error_SignIn_Default))
        }
    }

    suspend fun handleSignIn(credentialResponse: GetCredentialResponse): Resource<ToDoUser> {
        when (val credential = credentialResponse.credential) {
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        val googleIdTokenCredential =
                            GoogleIdTokenCredential.createFrom(credential.data)
                        val googleAuthCredential =
                            GoogleAuthProvider.getCredential(googleIdTokenCredential.idToken, null)

                        val sigInResult = firebaseAuth
                            .signInWithCredential(googleAuthCredential).await()

                        val resUser = sigInResult.user

                        if (resUser != null) {
                            val user = ToDoUser(
                                name = resUser.displayName ?: "user",
                                email = resUser.email ?: "email",
                                photoUrl = resUser.photoUrl.toString()
                            )
                            return Resource.Success(user)
                        } else {
                            return Resource.Error(appContext.getString(R.string.error_SignIn_Default))
                        }
                    } catch (e: Exception) {
                        return Resource.Error(appContext.getString(R.string.error_SignIn_Default))
                    }

                } else {
                    return Resource.Error(appContext.getString(R.string.error_credential_missMatched))
                }
            }

            else -> {
                return Resource.Error(appContext.getString(R.string.error_credential_missMatched))
            }
        }
    }

    fun googleFireBaseSignOut() {
        firebaseAuth.signOut()
    }

    fun checkIfGoogleFirebaseUserIsSignedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }

    private fun buildGoogleSignInRequest(): GetCredentialRequest {
        val googleIdOption = GetGoogleIdOption.Builder()
            .setServerClientId(BuildConfig.firebase_id)
            .setFilterByAuthorizedAccounts(false)
            .build()
        return GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()
    }
}