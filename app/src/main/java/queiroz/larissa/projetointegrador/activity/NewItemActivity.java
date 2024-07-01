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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import queiroz.larissa.projetointegrador.R;
import queiroz.larissa.projetointegrador.model.Compartimento;
import queiroz.larissa.projetointegrador.util.Config;

public class NewItemActivity extends AppCompatActivity {
    private int itemSelecionado;

    private int hour;
    private int minute;

    private String freqNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_item);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent i = getIntent();
        int caixa = i.getIntExtra("caixa", 0);

        try {
            Compartimento c = Config.pegarCompartimento(NewItemActivity.this,caixa);

            if(c != null){
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
            e.printStackTrace();
        }


        TextView tvHora = findViewById(R.id.tvHora);

        ImageButton imgButtonTime = findViewById(R.id.imgButtonTime);
        imgButtonTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are getting the
                // instance of our calendar.
                final Calendar c = Calendar.getInstance();

                // on below line we are getting our hour, minute.
                hour = c.get(Calendar.HOUR_OF_DAY);
                minute = c.get(Calendar.MINUTE);

                // on below line we are initializing our Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(NewItemActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                // on below line we are setting selected time
                                // in our text view.
                                tvHora.setText(hourOfDay + ":" + minute);
                            }
                        }, hour, minute, false);
                // at last we are calling show to
                // display our time picker dialog.
                timePickerDialog.show();
            }
        });


        TextView tvDate = findViewById(R.id.tvDate);

        ImageButton imgBtnData = findViewById(R.id.imgBtnData);
        imgBtnData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                int Year = c.get(Calendar.YEAR);
                int Month = c.get(Calendar.MONTH);
                int Day = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(NewItemActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                tvDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                            }
                        }, Year, Month, Day);
                datePickerDialog.show();
            }
        });


        TextView tvFreq = findViewById(R.id.tvFreq);
        ImageButton imgBtnFreq = findViewById(R.id.imgBtnFreq);

        final String[] selecaoFreq = {
                "TESTE", "A cada 1 hora", "A cada 3 horas", "A cada 6 horas", "A cada 12 horas", "A cada 18 horas", "A cada 24 horas", "A cada 36 horas", "A cada 48 horas", "A cada 72 horas"
        };

        final String[] selecaoFreqInt = {
                "0.03", "1", "3", "6", "12", "18", "24", "36", "48", "72"
        };

        final boolean[] checados = new boolean[selecaoFreq.length];

        imgBtnFreq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvFreq.setText(null);

                AlertDialog.Builder builder = new AlertDialog.Builder(NewItemActivity.this);
                builder.setTitle("Escolha a frequência");

                builder.setCancelable(false);

                builder.setPositiveButton("FEITO", (dialog, which) -> {
                    //diasSelecionados.clear();
                    //List<String> diasSelecionados = new ArrayList<>();
                    /*for (int i = 0; i < checados.length; i++) {
                        if (checados[i]) {
                            diasSelecionados.add(selecaoFreq[i]);
                            days.add(daysWeek[i]);
                        }
                    }
                    tvFreq.setText(TextUtils.join(", ", diasSelecionados));*/
                });

                builder.setNegativeButton("CANCELAR", (dialog, which) -> {});
                builder.setSingleChoiceItems(selecaoFreq, 0, (dialog, which) -> {
                    itemSelecionado = which;
                    freqNum = selecaoFreqInt[itemSelecionado];
                    tvFreq.setText(selecaoFreq[itemSelecionado]);
                });


                builder.create();
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }

        });


        Button btnAddItem = findViewById(R.id.btnAddItem);
        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etName = findViewById(R.id.etNome);
                String name = etName.getText().toString();
                if (name.isEmpty()){
                    Toast.makeText(NewItemActivity.this,"É necessário inserir o nome do remédio", Toast.LENGTH_LONG).show();
                    return;
                }
                EditText etQtd = findViewById(R.id.etQtd);
                String qtd = etQtd.getText().toString();
                if (qtd.isEmpty()){
                    Toast.makeText(NewItemActivity.this,"É necessário inserir a quantidade de remédios", Toast.LENGTH_LONG).show();
                    return;
                }
                EditText etDesc = findViewById(R.id.etDesc);
                String desc = etDesc.getText().toString();
                if (desc.isEmpty()){
                    Toast.makeText(NewItemActivity.this,"É necessário inserir uma descrição", Toast.LENGTH_LONG).show();
                    return;
                }
                String date = tvDate.getText().toString();
                if (date.isEmpty()){
                    Toast.makeText(NewItemActivity.this,"É necessário selecionar uma Data",Toast.LENGTH_LONG).show();
                    return;
                }
                String hora = tvHora.getText().toString();
                if (hora.isEmpty()){
                    Toast.makeText(NewItemActivity.this,"É necessário selecionar uma Hora",Toast.LENGTH_LONG).show();
                    return;
                }
                String frequencia = tvFreq.getText().toString();
                if (frequencia.isEmpty()){
                    Toast.makeText(NewItemActivity.this,"É necessário selecionar uma Frequência",Toast.LENGTH_LONG).show();
                    return;
                }

                Intent i = new Intent();
                i.putExtra("nome", name);
                i.putExtra("qtd",qtd);
                i.putExtra("desc",desc);

                i.putExtra("date", date);
                i.putExtra("hora", hora);
                i.putExtra("caixa", caixa);
                i.putExtra("freq", frequencia);
                i.putExtra("freqNum", freqNum);


                setResult(Activity.RESULT_OK, i);
                finish();
            }
        });
    }

}