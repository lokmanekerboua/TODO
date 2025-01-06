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
import kotlinx.coroutines.tasks.await
import me.lokmvne.auth.BuildConfig
import me.lokmvne.common.data.ToDoUser
import me.lokmvne.common.utils.Resource
import javax.inject.Inject

class GoogleAuthUiClient @Inject constructor(
    private val credentialManager: CredentialManager
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
            return Resource.Error("You Cancelled the sign in process")
        } catch (e: NoCredentialException) {
            return Resource.Error("There is no Google Account on this device!")
        } catch (e: Exception) {
            return Resource.Error("An error occurred while signing in")
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
                            return Resource.Error("An error occurred while signing in")
                        }
                    } catch (e: Exception) {
                        return Resource.Error("An error occurred while signing in")
                    }

                } else {
                    return Resource.Error("Credential type is miss matched")
                }
            }

            else -> {
                return Resource.Error("Credential type isn't supported")
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