package queiroz.larissa.projetointegrador.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import queiroz.larissa.projetointegrador.R;
import queiroz.larissa.projetointegrador.model.MainActivityViewModel;
import queiroz.larissa.projetointegrador.util.Config;

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

        Toolbar toolbar = findViewById(R.id.tbMain);
        setSupportActionBar(toolbar);

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
    // preenche o menu da toolbar com os itens de ações definidos no xml main_activity_tb
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_tb, menu);
        return true;
    }

    // método que é chamado toda vez que um item da toolbar é chamado
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        // caso o usuário clique na ação de configurar, exibimos uma caixa de diálogo que
        // permite que o usuário configure o endereço do ESP32
        if (item.getItemId() == R.id.opConfig) {

            // Constrói o layout da caixa de diálogo
            LayoutInflater inflater = getLayoutInflater();
            View configDlgView = inflater.inflate(R.layout.config_dlg, null);

            // obtem a caixa de texto dentro da caixa de diálogo usada pelo usuário
            EditText etESP32Address = configDlgView.findViewById(R.id.etESP32Address);

            // setamos na caixa de texto o valor atual de endereço do ESP32
            etESP32Address.setText(Config.getESP32Address(this));

            // Construção da caixa de diálogo
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            // definimos o layout que a caixa de diálogo vai ter
            builder.setView(configDlgView);

            // definimos o que acontece quando clicamos no botão OK da caixa de diálogo
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    // obtemos o endereço do ESP32 definido pelo usuário
                    String esp32Address = etESP32Address.getText().toString();

                    // salvamos esse novo endereço no arquivo de configuração da app
                    Config.setESP32Address(MainActivity.this, esp32Address);
                }
            });

            // se clicar em cancelar, não fazemos nada
            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                }
            });

            // depois de configurada a caixa, a criamos e exibimos
            builder.create().show();

            return true;
        }

        // caso o usuário clique na ação de atualizar, realizamos requisições ao ESP32 para
        // obter o estado atual do LED e motor
        if (item.getItemId() == R.id.opUpdate) {
            updateCaixaStatus();
            return true;
        }
        return super.onOptionsItemSelected(item);
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