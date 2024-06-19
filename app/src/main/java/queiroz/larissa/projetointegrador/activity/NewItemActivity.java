package queiroz.larissa.projetointegrador.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.sql.Time;

import queiroz.larissa.projetointegrador.R;

public class NewItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*EdgeToEdge.enable(this);*/
        setContentView(R.layout.activity_new_item);
        /*
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
         */

        TextView tvHora = findViewById(R.id.tvHora);

        ImageButton imgButtonTime = findViewById(R.id.imgButtonTime);
        imgButtonTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are getting the
                // instance of our calendar.
                final Calendar c = Calendar.getInstance();

                // on below line we are getting our hour, minute.
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

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

                                tvDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, Year, Month, Day);
                datePickerDialog.show();
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
                if (tvDate == null){
                    Toast.makeText(NewItemActivity.this,"É necessário selecionar uma Data",Toast.LENGTH_LONG).show();
                    return;
                }
                if (tvHora == null){
                    Toast.makeText(NewItemActivity.this,"É necessário selecionar uma Hora",Toast.LENGTH_LONG).show();
                    return;
                }

                Intent i = new Intent();
                i.putExtra("nome", name);
                i.putExtra("qtd",qtd);
                i.putExtra("desc",desc);
            }
        });
    }
}