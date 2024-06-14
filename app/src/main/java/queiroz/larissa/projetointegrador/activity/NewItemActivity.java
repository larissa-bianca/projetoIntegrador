package queiroz.larissa.projetointegrador.activity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_item);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
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
            }
        });
    }
}