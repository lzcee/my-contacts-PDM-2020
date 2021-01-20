package br.edu.ifsp.scl.ads.s5.pdm.mycontacts.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import br.edu.ifsp.scl.ads.s5.pdm.mycontacts.databinding.ActivityRegisterBinding
import br.edu.ifsp.scl.ads.s5.pdm.mycontacts.model.Auth

class RegisterActivity : AppCompatActivity() {
    private lateinit var activityRegisterBinding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityRegisterBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(activityRegisterBinding.root)
    }

    fun onClick(view: View) {
        if (view == activityRegisterBinding.registerBt) {
            val email = activityRegisterBinding.emailEt.text.toString()
            val password = activityRegisterBinding.passwordEt.text.toString()
            val repeatPassword = activityRegisterBinding.repeatPasswordEt.text.toString()

            if (validateFields(email, password, repeatPassword)) {
                Auth.firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                this,
                                "Usuário $email criado com sucesso",
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        } else {
                            Toast.makeText(this, "Erro na criação do usuário", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
            } else {
                Toast.makeText(this, "Campos preenchidos incorretamente", Toast.LENGTH_SHORT).show()
                activityRegisterBinding.emailEt.requestFocus()
            }
        }
    }

    fun validateFields(email: String, password: String, repeatPassword: String): Boolean {
        return if (email.isBlank() || email.isEmpty())
            false
        else if (password.isBlank() || password.isEmpty())
            false
        else if (repeatPassword.isBlank() || repeatPassword.isEmpty())
            false
        else password.equals(repeatPassword)
    }
}