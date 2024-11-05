import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.loginapp.MainActivity
import com.example.loginapp.R


class VirtualAssistantActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.paginainicial)

        // Configura o botão "Minha conta" para navegar para a tela de perfil (exemplo)
        var tvLogin = null
        tvLogin.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java)) // Mude para a activity da sua tela de conta
        }

        // Configura o botão "Teste Agora" para realizar alguma ação
        btnTesteAgora.setOnClickListener {
            // Adicione a lógica para iniciar o assistente virtual ou realizar outra ação
        }
    }
}
