package br.com.victor.firebaseauth

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.victor.firebaseauth.databinding.ActivityLoginBinding
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var etEmailLogin : TextInputEditText
    private lateinit var etSenhaLogin : TextInputEditText
    private lateinit var btnLogin : MaterialButton
    private lateinit var btnintentCadastro : MaterialButton
    private lateinit var binding: ActivityLoginBinding

    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        etEmailLogin = findViewById(R.id.etEmailLogin)
        etSenhaLogin = findViewById(R.id.etSenhaLogin)
        btnLogin = findViewById(R.id.btnLogin)
        btnLogin.setOnClickListener{
            logInSystem()
        }
        btnintentCadastro = findViewById(R.id.btnintentCadastro)
        btnintentCadastro.setOnClickListener{
            intentCadastro()
        }
    }

    private fun logInSystem(){
        val emailLogin = etEmailLogin.text.toString()
        val senhaLogin = etSenhaLogin.text.toString()

        if(emailLogin.isNullOrEmpty()||senhaLogin.isNullOrEmpty()){
            val snack = Snackbar.make(btnLogin, "Os campos de usuário e senha não podem estar vazios", Snackbar.LENGTH_LONG)
            snack.setBackgroundTint(Color.RED)
            snack.show()
        }else{
            auth.signInWithEmailAndPassword(emailLogin, senhaLogin).addOnCompleteListener{ Sucesso ->
                if(Sucesso.isSuccessful){
                    intentMain()
                }

            }.addOnFailureListener { Exception ->

                val snack = Snackbar.make(btnLogin, "Usuário ou senha incorretos", Snackbar.LENGTH_LONG)
                snack.setBackgroundTint(Color.RED)
                snack.show()
            }
        }
    }

    private fun intentMain(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun intentCadastro(){
        val intent = Intent(this, CreateAccountActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onStart() {
        super.onStart()

        val userAuth = FirebaseAuth.getInstance().currentUser

        if(userAuth != null){
            intentMain()
        }
    }
}