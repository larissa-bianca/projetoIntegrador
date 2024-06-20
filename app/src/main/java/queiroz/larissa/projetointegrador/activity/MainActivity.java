package queiroz.larissa.projetointegrador.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import queiroz.larissa.projetointegrador.R;
import queiroz.larissa.projetointegrador.model.MainActivityViewModel;

public class MainActivity extends AppCompatActivity {
    boolean CaixaStatus = false;
    static int NEW_ITEM_REQUEST =1;
    MainActivityViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnEditar = findViewById(R.id.btnEditar);
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,NewItemActivity.class);
                startActivityForResult(i,NEW_ITEM_REQUEST);
            }
        });

        vm = new ViewModelProvider(this).get(MainActivityViewModel.class);
        // realiza consulta ao ESP32 para verificar se o LED está ligado ou desligado
        updateCaixaStatus();

        Button btnAbreCaixa = findViewById(R.id.btnAbreCaixa);
        btnAbreCaixa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Quando o botão é clicado, ele é desabilitado. Isso garante que o usuário não
                // será capaz de clicar várias vezes no botão
                v.setEnabled(false);

                // guarda uma promessa de resposta por parte do ESP32. Assim que o ESP32 responder,
                // a variável abaixo guarda o resultado da ação: um booleano, onde true indica que
                // o ESP32 conseguiu realizar a ação e false caso contrário
                LiveData<Boolean> resLD;

                // Se o estado atual do botão está ligado, enviamos uma requisição ao ESP32 para
                // desligar. Caso contrário, enviamos uma requisição ao ESP32 para ligar
                if(CaixaStatus) {
                    resLD = vm.turnLedOff();
                }
                else {
                    resLD = vm.turnLedOn();
                }

                // depois de enviar a requisição ao ESP32, nós observamos a variável que resLD.
                // Assim que o ESP32 responder, o método onChanged abaixo é chamado e entrega a
                // resposta
                resLD.observe(MainActivity.this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {

                        // Independente se o ESP32 realizou a ação ou não, fazemos uma requisição
                        // ao ESP32 para saber o estado atual do LED
                        updateCaixaStatus();

                        // reabilitamos novamente o botão que permite ligar/desligar o LED
                        v.setEnabled(true);
                    }
                });
            }
        });
    }
    void updateCaixaStatus() {

        // obtém o textview que exibe o status do LED
        //TextView tvLedStatusRes = findViewById(R.id.tvLedStatusRes);

        // obtém o button que permite ligar/desligar o LED
        Button btnAbreCaixa = findViewById(R.id.btnAbreCaixa);

        // envia uma requisição ao ESP32 para saber se o LED está ligado ou desligado
        LiveData<Boolean> caixaStatusLD = vm.getLedStatus();

        // observa ledStatusLD. Assim que o ESP32 responder, o resultado vai aparecer em ledStatusLD
        caixaStatusLD.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                // guarda o estado atual do LED
                CaixaStatus = aBoolean;

                // se o LED está ligado, mudamos o valor de tvLedStatusRes para ligado e mudamos
                // também o texto que aparece no botão para desligar
                if(aBoolean) {
                    btnAbreCaixa.setText("Fechar Caixa");
                }
                // se o LED está desligado, mudamos o valor de tvLedStatusRes para desligado e mudamos
                // também o texto que aparece no botão para ligar
                else {
                    btnAbreCaixa.setText("Abrir Caixa");
                }
            }
        });
    }

    TextView tvName = findViewById(R.id.tvName);
    TextView tvHrAlarme = findViewById(R.id.tvHrAlarme);
    TextView tvQtd = findViewById(R.id.tvQtd);
    TextView tvData = findViewById(R.id.tvData);
    TextView tvDesc = findViewById(R.id.tvDesc);

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_ITEM_REQUEST){
            if(resultCode== Activity.RESULT_OK){
                String name = data.getStringExtra("name");
                tvName.setText(name);
                String qtd = data.getStringExtra("qtd");
                tvQtd.setText(qtd);
                String desc = data.getStringExtra("desc");
                tvDesc.setText(desc);

                String date = data.getStringExtra("date");
                tvData.setText(date);
                String hora = data.getStringExtra("hora");
                tvHrAlarme.setText(hora);
            }
        }
    }
}