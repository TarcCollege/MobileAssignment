package com.example.drugassignment.Login_Registration

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.drugassignment.Class.CurrentUser
import com.example.drugassignment.Class.DrugDetail
import com.example.drugassignment.Class.UserLog
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query


class LoginViewModel : ViewModel() {

    lateinit var user : UserLog

    private val _currentUser = MutableLiveData<CurrentUser>()
    val currentUser : LiveData<CurrentUser>
        get() = _currentUser

    var login : Boolean

    enum class AuthenticationState {
        AUTHENTICATED, UNAUTHENTICATED, INVALID_AUTHENTICATION
    }

    val authenticationState: LiveData<AuthenticationState>? = Transformations.map(FirebaseUserLiveData()) {
        if (it != null && it.isEmailVerified) {
           // user = UserLog(it)
            //getCurrentUser(it)
            login = true
            AuthenticationState.AUTHENTICATED
        } else {
            login = false
            AuthenticationState.UNAUTHENTICATED
        }

    }

//    private fun getCurrentUser(user : FirebaseUser) {
//        var mFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
//        //var user2 : CurrentUser
//        val email : String = user.email?:""
//        val docRef = mFirestore.collection("User")
//            .document(email)
//
//        Log.i("user", docRef.get().isSuccessful.toString())
//
//        docRef
//            .get().addOnSuccessListener { documentSnapshot ->
//                Log.i("user22", user.email)
//                _currentUser.value = documentSnapshot.toObject(CurrentUser::class.java)
//            }
//    }

    fun setCurrentUser() {
        var user = FirebaseAuth.getInstance().currentUser

        var mFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
        //var user2 : CurrentUser
        val email : String = user?.email?:""
        val docRef = mFirestore.collection("User")
            .document(email)

        Log.i("user", docRef.get().isSuccessful.toString())

        docRef
            .get().addOnSuccessListener { documentSnapshot ->
                Log.i("user22", user?.email)
                _currentUser.value = documentSnapshot.toObject(CurrentUser::class.java)
            }
    }





    init {
        login = false
        _currentUser.value = CurrentUser()
    }



}
