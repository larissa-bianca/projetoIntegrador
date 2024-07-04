package queiroz.larissa.projetointegrador.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONException;

import queiroz.larissa.projetointegrador.R;
import queiroz.larissa.projetointegrador.model.Compartimento;
import queiroz.larissa.projetointegrador.util.Config;

public class NewItemActivity extends AppCompatActivity {
    private int itemSelecionado; // Variável para armazenar o item selecionado na lista de frequência

    private int hour; // Variável para armazenar a hora selecionada
    private int minute; // Variável para armazenar o minuto selecionado

    private String freqNum; // Variável para armazenar o valor numérico da frequência

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Habilitar o modo Edge-to-Edge para a Activity
        setContentView(R.layout.activity_new_item); // Definir o layout da Activity
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent i = getIntent();
        int caixa = i.getIntExtra("caixa", 0); // Obter o valor da caixa passada pela Intent

        try {
            Compartimento c = Config.pegarCompartimento(NewItemActivity.this,caixa); // Obter o compartimento da caixa

            if(c != null){
                // Se o compartimento não for nulo, preencher os campos da tela com os dados do compartimento
                TextView tvHora = findViewById(R.id.tvHora);
                tvHora.setText(c.hora);

                TextView tvFreq = findViewById(R.id.tvFreq);
                tvFreq.setText(c.freq);

                EditText etNome = findViewById(R.id.etNome);
                etNome.setText(c.nome);

                EditText etQtd = findViewById(R.id.etQtd);
                etQtd.setText(c.qtd);

                TextView tvDate = findViewById(R.id.tvDate);
                tvDate.setText(c.data);

                EditText etDesc = findViewById(R.id.etDesc);
                etDesc.setText(c.desc);
            }

        } catch (JSONException e) {
            e.printStackTrace(); // Tratar a exceção JSON
        }

        TextView tvHora = findViewById(R.id.tvHora); // Referência ao TextView da hora

        ImageButton imgButtonTime = findViewById(R.id.imgButtonTime); // Referência ao ImageButton para selecionar a hora
        imgButtonTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance(); // Obter a instância do calendário

                hour = c.get(Calendar.HOUR_OF_DAY); // Obter a hora atual
                minute = c.get(Calendar.MINUTE); // Obter o minuto atual

                TimePickerDialog timePickerDialog = new TimePickerDialog(NewItemActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                tvHora.setText(hourOfDay + ":" + minute); // Definir a hora selecionada no TextView
                            }
                        }, hour, minute, false);
                timePickerDialog.show(); // Mostrar o TimePickerDialog
            }
        });

        TextView tvDate = findViewById(R.id.tvDate); // Referência ao TextView da data

        ImageButton imgBtnData = findViewById(R.id.imgBtnData); // Referência ao ImageButton para selecionar a data
        imgBtnData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance(); // Obter a instância do calendário
                int Year = c.get(Calendar.YEAR); // Obter o ano atual
                int Month = c.get(Calendar.MONTH); // Obter o mês atual
                int Day = c.get(Calendar.DAY_OF_MONTH); // Obter o dia atual

                DatePickerDialog datePickerDialog = new DatePickerDialog(NewItemActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                tvDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year); // Definir a data selecionada no TextView
                            }
                        }, Year, Month, Day);
                datePickerDialog.show(); // Mostrar o DatePickerDialog
            }
        });

        TextView tvFreq = findViewById(R.id.tvFreq); // Referência ao TextView da frequência
        ImageButton imgBtnFreq = findViewById(R.id.imgBtnFreq); // Referência ao ImageButton para selecionar a frequência

        final String[] selecaoFreq = {
                "TESTE", "A cada 1 hora", "A cada 3 horas", "A cada 6 horas", "A cada 12 horas", "A cada 18 horas", "A cada 24 horas", "A cada 36 horas", "A cada 48 horas", "A cada 72 horas"
        }; // Array de opções de frequência (TESTE = a cada 10 segundos)

        final String[] selecaoFreqInt = {
                "0.00277", "1", "3", "6", "12", "18", "24", "36", "48", "72"
        }; // Array de valores numéricos correspondentes às opções de frequência em horas (0.00277 = 10 segundos em horas)

        imgBtnFreq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvFreq.setText(null); // Limpar o TextView da frequência

                AlertDialog.Builder builder = new AlertDialog.Builder(NewItemActivity.this); // Criar o AlertDialog para seleção de frequência
                builder.setTitle("Escolha a frequência"); // Definir o título do AlertDialog

                builder.setPositiveButton("FEITO", (dialog, which) -> {
                });

                builder.setNegativeButton("CANCELAR", (dialog, which) -> {
                });

                builder.setSingleChoiceItems(selecaoFreq, 0, (dialog, which) -> {
                    itemSelecionado = which; // Armazenar o item selecionado
                    freqNum = selecaoFreqInt[itemSelecionado]; // Armazenar o valor numérico correspondente
                    tvFreq.setText(selecaoFreq[itemSelecionado]); // Definir o item selecionado no TextView
                });

                builder.create();
                AlertDialog alertDialog = builder.create(); // Criar o AlertDialog
                alertDialog.show(); // Mostrar o AlertDialog

            }

        });

        Button btnAddItem = findViewById(R.id.btnAddItem); // Referência ao botão de adicionar item
        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etName = findViewById(R.id.etNome); // Referência ao EditText do nome
                String name = etName.getText().toString();
                if (name.isEmpty()){
                    Toast.makeText(NewItemActivity.this,"É necessário inserir o nome do remédio", Toast.LENGTH_LONG).show();
                    return;
                }
                EditText etQtd = findViewById(R.id.etQtd); // Referência ao EditText da quantidade
                String qtd = etQtd.getText().toString();
                if (qtd.isEmpty()){
                    Toast.makeText(NewItemActivity.this,"É necessário inserir a quantidade de remédios", Toast.LENGTH_LONG).show();
                    return;
                }
                EditText etDesc = findViewById(R.id.etDesc); // Referência ao EditText da descrição
                String desc = etDesc.getText().toString();
                if (desc.isEmpty()){
                    Toast.makeText(NewItemActivity.this,"É necessário inserir uma descrição", Toast.LENGTH_LONG).show();
                    return;
                }
                String date = tvDate.getText().toString(); // Obter a data selecionada
                if (date.isEmpty()){
                    Toast.makeText(NewItemActivity.this,"É necessário selecionar uma Data",Toast.LENGTH_LONG).show();
                    return;
                }
                String hora = tvHora.getText().toString(); // Obter a hora selecionada
                if (hora.isEmpty()){
                    Toast.makeText(NewItemActivity.this,"É necessário selecionar uma Hora",Toast.LENGTH_LONG).show();
                    return;
                }
                String frequencia = tvFreq.getText().toString(); // Obter a frequência selecionada
                if (frequencia.isEmpty()){
                    Toast.makeText(NewItemActivity.this,"É necessário selecionar uma Frequência",Toast.LENGTH_LONG).show();
                    return;
                }

                Intent i = new Intent(); // Criar uma nova Intent para retornar os dados
                i.putExtra("nome", name); // Adicionar o nome à Intent
                i.putExtra("qtd",qtd); // Adicionar a quantidade à Intent
                i.putExtra("desc",desc); // Adicionar a descrição à Intent
                i.putExtra("date", date); // Adicionar a data à Intent
                i.putExtra("hora", hora); // Adicionar a hora à Intent
                i.putExtra("caixa", caixa); // Adicionar a caixa à Intent
                i.putExtra("freq", frequencia); // Adicionar a frequência à Intent
                i.putExtra("freqNum", freqNum); // Adicionar o valor numérico da frequência à Intent

                setResult(Activity.RESULT_OK, i); // Definir o resultado da Activity
                finish(); // Finalizar a Activity
            }
        });
    }
}
