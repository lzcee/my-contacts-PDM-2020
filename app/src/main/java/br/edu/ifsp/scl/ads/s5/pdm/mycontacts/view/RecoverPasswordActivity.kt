package br.edu.ifsp.scl.ads.s5.pdm.mycontacts.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import br.edu.ifsp.scl.ads.s5.pdm.mycontacts.databinding.ActivityRecoverPasswordBinding
import br.edu.ifsp.scl.ads.s5.pdm.mycontacts.model.Auth

class RecoverPasswordActivity : AppCompatActivity() {
    private lateinit var activityRecoverPasswordBinding: ActivityRecoverPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityRecoverPasswordBinding = ActivityRecoverPasswordBinding.inflate(layoutInflater)
        setContentView(activityRecoverPasswordBinding.root)
    }

    fun onClick(view: View) {
        if (view == activityRecoverPasswordBinding.enviarEmailBt) {
            val email = activityRecoverPasswordBinding.emailRecoverPasswordEt.text.toString()

            if (email.isNotBlank() && email.isNotEmpty()) {
                Auth.firebaseAuth.sendPasswordResetEmail(email)
                Toast.makeText(this, "E-mail de recuperação de senha enviado para $email", Toast.LENGTH_SHORT).show()
            }

            finish()
        }
    }
}