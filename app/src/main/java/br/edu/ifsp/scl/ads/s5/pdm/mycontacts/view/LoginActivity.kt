package br.edu.ifsp.scl.ads.s5.pdm.mycontacts.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import br.edu.ifsp.scl.ads.s5.pdm.mycontacts.R
import br.edu.ifsp.scl.ads.s5.pdm.mycontacts.databinding.ActivityLoginBinding
import br.edu.ifsp.scl.ads.s5.pdm.mycontacts.model.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : AppCompatActivity() {
    private lateinit var activityLoginBinding: ActivityLoginBinding

    private lateinit var googleSignInOptions: GoogleSignInOptions
    private val googleSignInRequestCode = 1


    private fun loginSuccess() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(activityLoginBinding.root)

        googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        Auth.googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)

        activityLoginBinding.loginGoogleBt.setOnClickListener {
            val googleSignInAccount: GoogleSignInAccount? =
                GoogleSignIn.getLastSignedInAccount(this)
            if (googleSignInAccount == null) {
                startActivityForResult(
                    Auth.googleSignInClient?.signInIntent,
                    googleSignInRequestCode
                )
            } else {
                loginSuccess()
            }
        }
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.registerBt -> {
                startActivity(Intent(this, RegisterActivity::class.java))
            }
            R.id.loginBt -> {
                val email = activityLoginBinding.emailEt.text.toString()
                val senha = activityLoginBinding.passwordEt.text.toString()
                Auth.firebaseAuth.signInWithEmailAndPassword(email, senha).addOnSuccessListener {
                    Toast.makeText(this, "Usuário $email autenticado", Toast.LENGTH_SHORT).show()
                    loginSuccess()
                }.addOnFailureListener {
                    Toast.makeText(this, "Falha na autenticação do usuário", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            R.id.recoverPasswordBt -> {
                startActivity(Intent(this, RecoverPasswordActivity::class.java))
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == googleSignInRequestCode) {

            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val googleAccount = task.getResult(ApiException::class.java)
                if (googleAccount != null) {
                    val credential = GoogleAuthProvider.getCredential(googleAccount.idToken, null)
                    Auth.firebaseAuth.signInWithCredential(credential)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(
                                    this,
                                    "Usuário ${googleAccount.email} autenticado com sucesso.",
                                    Toast.LENGTH_SHORT
                                ).show()
                                loginSuccess()
                            } else {
                                Toast.makeText(
                                    this,
                                    "Falha na autenticação com Google.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                } else {
                    Toast.makeText(this, "Falha na autenticação com Google.", Toast.LENGTH_SHORT)
                        .show()
                }
            } catch (e: ApiException) {
                Toast.makeText(this, "Falha na autenticação com Google.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}