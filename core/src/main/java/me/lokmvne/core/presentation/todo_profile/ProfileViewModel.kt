package me.lokmvne.core.presentation.todo_profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import me.lokmvne.auth.repository.GoogleAuthRepoImpl
import me.lokmvne.common.data.ToDoUser
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val googleAuthRepoImpl: GoogleAuthRepoImpl
) : ViewModel() {
    val firebaseAuth = Firebase.auth
    var profileState by mutableStateOf(ProfileState())
        private set

    var showSignOutDialog by mutableStateOf(false)

    init {
        profileState = profileState.copy(
            toDoUser = ToDoUser(
                name = firebaseAuth.currentUser?.displayName ?: "user",
                email = firebaseAuth.currentUser?.email ?: "email",
                photoUrl = firebaseAuth.currentUser?.photoUrl.toString()
            )
        )
    }

    fun showSignOutDialog() {
        showSignOutDialog = true
    }

    fun hideSignOutDialog() {
        showSignOutDialog = false
    }

    fun signOut() {
        googleAuthRepoImpl.googleFireBaseSignOut()
    }
}