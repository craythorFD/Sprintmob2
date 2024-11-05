package com.example.loginapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.auth.FirebaseAuth


class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var toolbar: MaterialToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registrar)

        // Inicializa o FirebaseAuth
        auth = FirebaseAuth.getInstance()

        // Configura o botão "Entrar" para ir para a tela de login
        tvLogin.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        // Configura o botão de criação de conta para enviar dados para o Firebase
        btnCreateAccount.setOnClickListener {

            // Inicializando os campos de entrada
            var etFullName: EditText = findViewById(R.id.etFullName)
            var etUsername: EditText = findViewById(R.id.etUsername)
            val etBirthDate: EditText = findViewById(R.id.etBirthDate)
            var etEmail: EditText = findViewById(R.id.etEmail)
            val etPassword: EditText = findViewById(R.id.etPassword)

// Obtendo o texto dos campos
            var fullName = etFullName.text.toString()
            var username = etUsername.text.toString()
            val birthDate = etBirthDate.text.toString()
            var email = etEmail.text.toString()
            val password = etPassword.text.toString()


            if (fullName.isNotBlank() && username.isNotBlank() && email.isNotBlank() && password.isNotBlank()) {
                // Registra o usuário no Firebase
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Registro bem-sucedido
                            Log.i("Register", "createUserWithEmail:success")
                            Toast.makeText(this, "Conta criada com sucesso!", Toast.LENGTH_SHORT).show()

                            // Aqui você pode salvar mais informações do usuário no Firestore, se desejar

                            // Redireciona para a tela de login após o cadastro
                            startActivity(Intent(this, MainActivity::class.java))
                            finish() // Finaliza a atividade atual
                        } else {
                            // Falha no registro
                            Log.e("Register", "createUserWithEmail:failure", task.exception)
                            Toast.makeText(this, "Erro ao criar conta: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
