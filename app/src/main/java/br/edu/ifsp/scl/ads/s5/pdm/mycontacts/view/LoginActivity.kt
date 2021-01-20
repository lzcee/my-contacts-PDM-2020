package br.edu.ifsp.scl.ads.s5.pdm.mycontacts.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import br.edu.ifsp.scl.ads.s5.pdm.mycontacts.R
import br.edu.ifsp.scl.ads.s5.pdm.mycontacts.databinding.ActivityLoginBinding
import br.edu.ifsp.scl.ads.s5.pdm.mycontacts.model.Auth

class LoginActivity : AppCompatActivity() {
    private lateinit var activityLoginBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(activityLoginBinding.root)
    }

    fun onClick(view: View) {
        when(view.id) {
            R.id.registerBt -> {
                startActivity(Intent(this, RegisterActivity::class.java))
            }
            R.id.loginBt -> {
                val email = activityLoginBinding.emailEt.text.toString()
                val senha = activityLoginBinding.passwordEt.text.toString()
                Auth.firebaseAuth.signInWithEmailAndPassword(email, senha).addOnSuccessListener {
                    Toast.makeText(this, "Usuário $email autenticado", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }.addOnFailureListener {
                    Toast.makeText(this, "Falha na autenticação do usuário", Toast.LENGTH_SHORT).show()
                }
            }
            R.id.recoverPasswordBt -> {
                startActivity(Intent(this, RecoverPasswordActivity::class.java))
            }
        }
    }
}