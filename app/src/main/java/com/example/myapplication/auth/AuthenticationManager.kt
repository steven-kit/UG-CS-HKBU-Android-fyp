package com.example.myapplication.auth

import android.content.Context
import android.util.Log
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.example.myapplication.R
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.security.MessageDigest
import java.util.UUID

class AuthenticationManager(private val context: Context) {

    private val credentialManager = CredentialManager.create(context)

    private val auth = FirebaseAuth.getInstance()

    private fun createNonce(): String {
        val rawNonce = UUID.randomUUID().toString()
        val bytes = rawNonce.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)

        return digest.fold("") { str, it ->
            str + "%02x".format(it)
        }
    }

    fun createAccountWithEmail(email: String, password: String): Flow<AuthResponse> = callbackFlow {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    trySend(AuthResponse.Success)
                } else {
                    trySend(AuthResponse.Error(message = it.exception?.message ?: "Unknown error"))
                }
            }

        awaitClose()
    }

    fun signInWithEmail(email: String, password: String): Flow<AuthResponse> = callbackFlow {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    trySend(AuthResponse.Success)
                } else {
                    trySend(AuthResponse.Error(message = it.exception?.message ?: "Unknown error"))
                }
            }

        awaitClose()
    }

    fun signInWithGoogle(): Flow<AuthResponse> = callbackFlow {

        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(context.getString(R.string.web_client_id))
            .setAutoSelectEnabled(false)
            .setNonce(createNonce())
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        try {
            println("AuthenticationManager: credentialManager created")
            val result = credentialManager.getCredential(
                context = context, request = request
            )
            val credential = result.credential
            println("AuthenticationManager Credential received: $credential")

            if (credential is CustomCredential) {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        val googleIdTokenCredential = GoogleIdTokenCredential
                            .createFrom(credential.data)

                        val firebaseCredential = GoogleAuthProvider
                            .getCredential(
                                googleIdTokenCredential.idToken, null
                            )

                        auth.signInWithCredential(firebaseCredential)
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    Log.d("AuthenticationManager", "Sign in successful")
                                    trySend(AuthResponse.Success)
                                } else {
                                    Log.e("AuthenticationManager", "Sign in failed: ${it.exception?.message}")
                                    trySend(AuthResponse.Error(message = it.exception?.message ?: "Unknown error"))
                                }
                            }
                    } catch (e: GoogleIdTokenParsingException) {
                        Log.e("AuthenticationManager", "GoogleIdTokenParsingException: ${e.message}")
                        trySend(AuthResponse.Error(message = e.message ?: "Unknown error"))
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("AuthenticationManager", "Exception: ${e.message}")
            trySend(AuthResponse.Error(message = e.message ?: "Unknown error"))
        }

        awaitClose()
    }

    suspend fun signOut() {
        credentialManager.clearCredentialState(ClearCredentialStateRequest())
        auth.signOut()
    }

}

interface AuthResponse {
    data object Success : AuthResponse
    data class Error(val message: String) : AuthResponse
}