package br.edu.ifsp.scl.ads.s5.pdm.mycontacts.model

import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth

object Auth {
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    var googleSignInClient: GoogleSignInClient? = null
}