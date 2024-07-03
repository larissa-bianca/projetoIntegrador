package queiroz.larissa.projetointegrador.activity;

import static androidx.core.content.ContextCompat.startActivity;

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
import queiroz.larissa.projetointegrador.model.MainActivityViewModel;
import queiroz.larissa.projetointegrador.model.NewItemActivityAlarmViewModel;

public class NewItemActivityAlarm extends AppCompatActivity {
    NewItemActivityAlarmViewModel vm;
    TextView tvNomeRemedio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String nomeRemedio;

        vm = new ViewModelProvider(this).get(NewItemActivityAlarmViewModel.class);

        Intent i = getIntent();
        nomeRemedio = i.getStringExtra("nome");
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_item_alarm);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.linearLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        tvNomeRemedio = findViewById(R.id.tvNomeRemedio);
        tvNomeRemedio.setText("Hora de tomar " + nomeRemedio + "!");

        vm.ligarLed();
        vm.piscarBuzzer();

        Button btnDesligarAlarme = findViewById(R.id.btnDesligarAlarme);
        btnDesligarAlarme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(NewItemActivityAlarm.this,
                        MainActivity.class);
                startActivity( j, null);
            }
        });
    }
}