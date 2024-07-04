package queiroz.larissa.projetointegrador.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import queiroz.larissa.projetointegrador.R;
import queiroz.larissa.projetointegrador.model.NewItemActivityAlarmViewModel;

public class NewItemActivityAlarm extends AppCompatActivity {
    NewItemActivityAlarmViewModel vm; // ViewModel para a Activity
    TextView tvNomeRemedio; // TextView para exibir o nome do remédio

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String nomeRemedio; // Variável para armazenar o nome do remédio

        vm = new ViewModelProvider(this).get(NewItemActivityAlarmViewModel.class); // Inicializar o ViewModel

        Intent i = getIntent();
        nomeRemedio = i.getStringExtra("nome"); // Obter o nome do remédio da Intent
        EdgeToEdge.enable(this); // Habilitar o modo Edge-to-Edge para a Activity
        setContentView(R.layout.activity_new_item_alarm); // Definir o layout da Activity
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.linearLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        tvNomeRemedio = findViewById(R.id.tvNomeRemedio); // Referência ao TextView do nome do remédio
        tvNomeRemedio.setText("Hora de tomar " + nomeRemedio + "!"); // Definir o texto do TextView

        vm.ligarLed(); // Ligar o LED
        try {
            Thread.sleep(300); // Pausar a execução por 300 milissegundos, para assim o led funcionar devidamente
        } catch (InterruptedException e) {
            throw new RuntimeException(e); // Tratar a exceção de interrupção
        }
        vm.acionarBuzzer(); // Acionar o buzzer

        Button btnDesligarAlarme = findViewById(R.id.btnDesligarAlarme); // Referência ao botão de desligar alarme
        btnDesligarAlarme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(NewItemActivityAlarm.this,
                        MainActivity.class); // Criar uma nova Intent para a MainActivity
                startActivity(j, null); // Iniciar a MainActivity
            }
        });
    }
}
