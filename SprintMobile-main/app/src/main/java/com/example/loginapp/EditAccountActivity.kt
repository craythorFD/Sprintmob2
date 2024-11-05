package com.example.loginapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class EditAccountActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var emailInput: EditText
    private lateinit var senhaInput: EditText
    private lateinit var usernameInput: EditText
    private lateinit var birthDateInput: EditText
    private lateinit var updateBtn: Button
    private lateinit var deleteBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.minhaconta) // Use o layout XML modificado

        // Inicializa o FirebaseAuth
        auth = FirebaseAuth.getInstance()

        // Inicializa os campos de entrada e os botões
        emailInput = findViewById(R.id.etEmail) // ID do EditText de e-mail
        senhaInput = findViewById(R.id.etPassword) // ID do EditText de senha
        usernameInput = findViewById(R.id.etUsername) // ID do EditText de nome de usuário
        birthDateInput = findViewById(R.id.etBirthDate) // ID do EditText de data de nascimento
        updateBtn = findViewById(R.id.update_btn) // ID do botão de atualização
        deleteBtn = findViewById(R.id.delete_btn) // ID do botão de exclusão

        // Define o comportamento do botão de atualização
        updateBtn.setOnClickListener {
            val newEmail = emailInput.text.toString()
            val newPassword = senhaInput.text.toString()
            val newUsername = usernameInput.text.toString()
            val newBirthDate = birthDateInput.text.toString()

            // Validação de campos
            if (newEmail.isBlank() || newPassword.isBlank() || newUsername.isBlank() || newBirthDate.isBlank()) {
                Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
            } else {
                // Atualiza o email do usuário
                auth.currentUser?.updateEmail(newEmail)?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.i("Update", "Email atualizado com sucesso")
                        Toast.makeText(this, "Email atualizado com sucesso", Toast.LENGTH_SHORT).show()
                    } else {
                        Log.e("Update", "Erro ao atualizar email", task.exception)
                        Toast.makeText(this, "Erro ao atualizar email", Toast.LENGTH_SHORT).show()
                    }
                }

                // Atualiza a senha do usuário
                auth.currentUser?.updatePassword(newPassword)?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.i("Update", "Senha atualizada com sucesso")
                        Toast.makeText(this, "Senha atualizada com sucesso", Toast.LENGTH_SHORT).show()
                    } else {
                        Log.e("Update", "Erro ao atualizar senha", task.exception)
                        Toast.makeText(this, "Erro ao atualizar senha", Toast.LENGTH_SHORT).show()
                    }
                }

                // Aqui você pode adicionar lógica para atualizar o nome de usuário e a data de nascimento,
                // dependendo de como você está armazenando essas informações.
            }
        }

        // Define o comportamento do botão de exclusão da conta
        deleteBtn.setOnClickListener {
            auth.currentUser?.delete()?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.i("Delete", "Conta excluída com sucesso")
                    Toast.makeText(this, "Conta excluída com sucesso", Toast.LENGTH_SHORT).show()
                    finish() // Finaliza a atividade e volta para a tela de login
                } else {
                    Log.e("Delete", "Erro ao excluir conta", task.exception)
                    Toast.makeText(this, "Erro ao excluir conta", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // Função para registrar o usuário no Firebase
    private fun registerUser() {
        val fullName = usernameInput.text.toString()
        val email = emailInput.text.toString()
        val password = senhaInput.text.toString()

        if (fullName.isNotBlank() && email.isNotBlank() && password.isNotBlank()) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Registro bem-sucedido
                        Log.i("Register", "createUserWithEmail:success")
                        Toast.makeText(this, "Conta criada com sucesso!", Toast.LENGTH_SHORT).show()
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
