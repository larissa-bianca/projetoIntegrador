package queiroz.larissa.projetointegrador.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import queiroz.larissa.projetointegrador.R;
import queiroz.larissa.projetointegrador.model.Compartimento;
import queiroz.larissa.projetointegrador.model.MainActivityViewModel;
import queiroz.larissa.projetointegrador.util.Config;

public class MainActivity extends AppCompatActivity {
    boolean caixaAberta = false;
    static int NEW_ITEM_REQUEST =1;
    MainActivityViewModel vm;

    TextView tvName;
    TextView tvHrAlarme;
    TextView tvQtd;
    TextView tvData;
    TextView tvDesc;

    Compartimento c1, c2, c3;

    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvName = findViewById(R.id.tvName);
        tvHrAlarme = findViewById(R.id.tvHrAlarme);
        tvQtd = findViewById(R.id.tvQtd);
        tvData = findViewById(R.id.tvData);
        tvDesc = findViewById(R.id.tvDesc);

        c1 = Config.pegarCompartimento(MainActivity.this,1);
        if(c1 != null) {
            preencherCaixaUi(1, c1);
        }

        c2 = Config.pegarCompartimento(MainActivity.this,2);
        if(c2 != null) {
            preencherCaixaUi(2, c2);
        }
        c3 = Config.pegarCompartimento(MainActivity.this,3);

        Button btnEditar = findViewById(R.id.btnEditar);
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navegarEditarCaixa(1);
            }
        });

        Toolbar toolbar = findViewById(R.id.tbMain);
        setSupportActionBar(toolbar);

        vm = new ViewModelProvider(this).get(MainActivityViewModel.class);


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
                if(caixaAberta) {
                    resLD = vm.fecharCaixa(1);
                }
                else {
                    resLD = vm.abrirCaixa(1);
                }

                // depois de enviar a requisição ao ESP32, nós observamos a variável que resLD.
                // Assim que o ESP32 responder, o método onChanged abaixo é chamado e entrega a
                // resposta
                resLD.observe(MainActivity.this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {

                        // Independente se o ESP32 realizou a ação ou não, fazemos uma requisição
                        // ao ESP32 para saber o estado atual do LED
                        if(caixaAberta) {
                            if(aBoolean) {
                                caixaAberta = false;
                                btnAbreCaixa.setText("Fechar Caixa");

                            }
                        }
                        else {
                            if(aBoolean) {
                                caixaAberta = true;
                                btnAbreCaixa.setText("Abrir Caixa");

                            }
                        }

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
        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_ITEM_REQUEST){
            if(resultCode== Activity.RESULT_OK){


                Compartimento c = new Compartimento();
                c.data = data.getStringExtra("date");
                c.desc = desc;

                int caixa = data.getIntExtra("caixa", 0);

                Config.salvarCompartimento(MainActivity.this, , c );
                preencherCaixaUi(caixa, c);

                alarme(desc, hora);

            }
        }
    }

    protected void alarme(String desc, String h){
        String[] horaEMin = h.split(":");
        int hora = Integer.valueOf(horaEMin[0]);
        int min =  Integer.valueOf(horaEMin[1]);

        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM)
                .putExtra(AlarmClock.EXTRA_MESSAGE, desc)
                .putExtra(AlarmClock.EXTRA_HOUR, hora)
                .putExtra(AlarmClock.EXTRA_MINUTES, min);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    void navegarEditarCaixa( int caixa) {
        Intent i = new Intent(MainActivity.this,NewItemActivity.class);
        i.putExtra("caixa", caixa);
        startActivityForResult(i,NEW_ITEM_REQUEST);
    }

    void preencherCaixaUi( int caixa, Compartimento c) {
        String name = c.nome;
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