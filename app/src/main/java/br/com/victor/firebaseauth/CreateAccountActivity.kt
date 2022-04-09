package br.com.victor.firebaseauth

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import br.com.victor.firebaseauth.databinding.ActivityCreateAccountBinding
import br.com.victor.firebaseauth.databinding.ActivityLoginBinding
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class CreateAccountActivity : AppCompatActivity() {

    private lateinit var etEmail : TextInputEditText
    private lateinit var etSenha : TextInputEditText
    private lateinit var btnCadastrar : MaterialButton
    private lateinit var btnintentLogin : MaterialButton
    private lateinit var binding: ActivityCreateAccountBinding

    private val auth = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        etEmail = findViewById(R.id.etEmail)
        etSenha = findViewById(R.id.etSenha)
        btnCadastrar = findViewById(R.id.btnCadastrar)
        btnCadastrar.setOnClickListener{
            cadastrar()
        }
        btnintentLogin = findViewById(R.id.btnintentLogin)
        btnintentLogin.setOnClickListener{
            val intentLogin = Intent(this, LoginActivity::class.java)
            startActivity(intentLogin)
        }


    }

    private fun cadastrar(){
        val email = etEmail.text.toString()
        val senha = etSenha.text.toString()

        if(email.isNullOrEmpty()||senha.isNullOrEmpty()){
            val snack = Snackbar.make(btnCadastrar, "Os campos usuário e senha nãopodem ser vazios", Snackbar.LENGTH_LONG)
            snack.setBackgroundTint(Color.RED)
            snack.show()
        }else{
            auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener{ Sucesso ->
                if(Sucesso.isSuccessful){
                    val snack = Snackbar.make(btnCadastrar, "Usuário cadastrado com sucesso", Snackbar.LENGTH_LONG)
                    snack.setBackgroundTint(Color.BLUE)
                    snack.show()

                    etEmail.text!!.clear()
                    etSenha.text!!.clear()

                }
            }.addOnFailureListener { Exception ->
                val errorMessage = when(Exception){
                    is FirebaseAuthWeakPasswordException -> "A senha deve conter no mínimo 6 caracteres"
                    is FirebaseAuthUserCollisionException -> "Ja existe um usuário cadastrado com este email"
                    is FirebaseAuthInvalidCredentialsException -> "Por favor digite um email valido"
                    is FirebaseNetworkException -> "Sem conexão com a internet"
                    else -> "Erro no cadastro do usuário"
                }

                val snack = Snackbar.make(btnCadastrar, errorMessage, Snackbar.LENGTH_LONG)
                snack.setBackgroundTint(Color.RED)
                snack.show()



            }
        }
    }



}