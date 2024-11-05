package com.example.loginapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.FirebaseApp

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var nomeInput: EditText
    private lateinit var senhaInput: EditText
    private lateinit var loginBtn: Button

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    FirebaseApp.initializeApp(this) // Inicializa o Firebase
    setContentView(R.layout.activity_main)

        // Inicializa o FirebaseAuth
        auth = FirebaseAuth.getInstance()

        // Inicializa os campos de entrada e o botão
        nomeInput = findViewById(R.id.Nome_input)
        senhaInput = findViewById(R.id.Senha_input)
        loginBtn = findViewById(R.id.login_btn)

        // Define o comportamento do botão de login
        loginBtn.setOnClickListener {
            val email = nomeInput.text.toString()
            val password = senhaInput.text.toString()

            // Validação de campos
            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
            } else {
                // Autenticação com Firebase
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Login bem-sucedido
                            Log.i("Login", "signInWithEmail:success")
                            Toast.makeText(this, "Login bem-sucedido", Toast.LENGTH_SHORT).show()

                            // Navega para a tela de edição de conta
                            val intent = Intent(this, EditAccountActivity::class.java)
                            startActivity(intent)
                            finish() // Finaliza a atividade atual
                        } else {
                            // Falha no login
                            Log.e("Login", "signInWithEmail:failure", task.exception)
                            Toast.makeText(this, "Usuário ou senha inválidos.", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }
}
