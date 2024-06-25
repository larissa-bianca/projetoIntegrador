package queiroz.larissa.projetointegrador.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
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
import android.Manifest;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import queiroz.larissa.projetointegrador.R;
import queiroz.larissa.projetointegrador.model.Compartimento;
import queiroz.larissa.projetointegrador.model.MainActivityViewModel;
import queiroz.larissa.projetointegrador.util.Config;

public class MainActivity extends AppCompatActivity {

    static int RESULT_REQUEST_PERMISSION = 2;

    boolean caixaAberta = false;
    static int NEW_ITEM_REQUEST =1;
    MainActivityViewModel vm;

    TextView tvName;
    TextView tvHoraAlarme;
    TextView tvQtd;
    TextView tvData;
    TextView tvDesc;
    TextView tvFreq;

    Compartimento c1, c2, c3;

    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<String> permissions = new ArrayList<>();
        permissions.add(Manifest.permission.SET_ALARM);

        checkForPermissions(permissions);

        tvName = findViewById(R.id.tvNomRem1);
        tvHoraAlarme = findViewById(R.id.tvHoraAlarme);
        tvQtd = findViewById(R.id.tvQtd);
        tvData = findViewById(R.id.tvData);
        tvDesc = findViewById(R.id.tvDesc);


        try {
            c1 = Config.pegarCompartimento(MainActivity.this,1);
            if(c1 != null) {
                preencherCaixaUi(1, c1);
            }

            c2 = Config.pegarCompartimento(MainActivity.this,2);
            if(c2 != null) {
                preencherCaixaUi(2, c2);
            }

            c3 = Config.pegarCompartimento(MainActivity.this,3);
            if(c3 != null) {
                preencherCaixaUi(3, c3);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Button btnEditar = findViewById(R.id.btnEditar);
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navegarEditarCaixa(1);
            }
        });

        Button btnEditar2 = findViewById(R.id.btnEditar2);
        btnEditar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navegarEditarCaixa(2);
            }
        });

        Button btnEditar3 = findViewById(R.id.btnEditar3);
        btnEditar3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navegarEditarCaixa(3);
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
                        // Verifica se a caixa está aberta
                        if (caixaAberta) {
                            // Se a caixa estava aberta e a ação do ESP32 foi bem-sucedida
                            if (aBoolean) {
                                // Atualiza o estado da caixa para fechada
                                caixaAberta = false;
                                // Atualiza o texto do botão para "Fechar Caixa"
                                btnAbreCaixa.setText("Fechar Caixa");
                                c1.qtd = Integer.toString(Integer.parseInt(c1.qtd) - 1);

                            }
                        } else {
                            // Se a caixa estava fechada e a ação do ESP32 foi bem-sucedida
                            if (aBoolean) {
                                // Atualiza o estado da caixa para aberta
                                caixaAberta = true;
                                // Atualiza o texto do botão para "Abrir Caixa"
                                btnAbreCaixa.setText("Abrir Caixa");
                            }
                        }

                        // Reabilita o botão para permitir novas interações do usuário
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
                c.desc = data.getStringExtra("desc");
                c.nome = data.getStringExtra("nome");
                c.qtd = data.getStringExtra("qtd");
                c.hora = data.getStringExtra("hora");
                c.dias = data.getStringArrayExtra("days");
                c.diasPT = data.getStringExtra("diasPT");

                int caixa = data.getIntExtra("caixa", 0);

                try {
                    Config.salvarCompartimento(MainActivity.this, caixa, c);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                preencherCaixaUi(caixa, c);

                alarme(c);

            }
        }
    }

    protected void alarme(Compartimento c){
        int hora = c.getHoras();
        int min =  c.getMinutos();

        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM)
                .putExtra(AlarmClock.EXTRA_MESSAGE, c.nome)
                .putExtra(AlarmClock.EXTRA_HOUR, hora)
                .putExtra(AlarmClock.EXTRA_MINUTES, min)
                .putExtra(AlarmClock.EXTRA_DAYS, c.dias);
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
        if(caixa == 1){
            tvName = findViewById(R.id.tvNomRem1);
            tvHoraAlarme = findViewById(R.id.tvHoraAlarme);
            tvQtd = findViewById(R.id.tvQtd);
            tvData = findViewById(R.id.tvData);
            tvDesc = findViewById(R.id.tvDesc);
            tvFreq = findViewById(R.id.tvFreq1);
        }
        else if (caixa==2){
            tvName = findViewById(R.id.tvNomRem2);
            tvHoraAlarme = findViewById(R.id.tvHrAlarm2);
            tvQtd = findViewById(R.id.tvQtd2);
            tvData = findViewById(R.id.tvData2);
            tvDesc = findViewById(R.id.tvDesc2);
            tvFreq = findViewById(R.id.tvFreq2);
        }
        else if (caixa == 3){
            tvName = findViewById(R.id.tvNomRem3);
            tvHoraAlarme = findViewById(R.id.tvHrAlarme3);
            tvQtd = findViewById(R.id.tvQtd3);
            tvData = findViewById(R.id.tvData3);
            tvDesc = findViewById(R.id.tvDesc3);
            tvFreq = findViewById(R.id.tvFreq3);
        }

        String name = c.nome;
        tvName.setText(name);
        String qtd = c.qtd;
        tvQtd.setText(qtd);
        String desc = c.desc;
        tvDesc.setText(desc);

        String date = c.data;
        tvData.setText(date);
        String hora = c.hora;
        tvHoraAlarme.setText(hora);
        String diasSelecionados = c.diasPT;
        tvFreq.setText(diasSelecionados);

    }

    private void checkForPermissions(List<String> permissions) {
        List<String> permissionsNotGranted = new ArrayList<>();

        for(String permission : permissions) {
            if( !hasPermission(permission)) {
                permissionsNotGranted.add(permission);
            }
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(permissionsNotGranted.size() > 0) {
                requestPermissions(permissionsNotGranted.toArray(new String[permissionsNotGranted.size()]), RESULT_REQUEST_PERMISSION);
            }
        }
    }

    private boolean hasPermission(String permission) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return ActivityCompat.checkSelfPermission(MainActivity.this, permission) == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        final List<String> permissionsRejected = new ArrayList<>();
        if(requestCode == RESULT_REQUEST_PERMISSION) {
            for(String permission : permissions) {
                if(!hasPermission(permission)) {
                    permissionsRejected.add(permission);
                }
            }
        }

        if(permissionsRejected.size() > 0) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if(shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                    new AlertDialog.Builder(MainActivity.this)
                            .setMessage("Para usar essa app é preciso conceder essas permissões")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]), RESULT_REQUEST_PERMISSION);
                                }
                            }).create().show();
                }
            }
        }
    }

}