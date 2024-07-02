package queiroz.larissa.projetointegrador.activity;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import queiroz.larissa.projetointegrador.model.MainActivityViewModel;
import queiroz.larissa.projetointegrador.model.Repository;

import queiroz.larissa.projetointegrador.R;

public class AlarmReceiver extends BroadcastReceiver {

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    MainActivityViewModel vm;
    // implement onReceive() method
    public void onReceive(Context context, Intent intent) {
        String nomeRemedio;
        // we will use vibrator first
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(4000);

        Toast.makeText(context, "Hora de tomar o remédio!", Toast.LENGTH_LONG).show();

        //Intent i = new Intent(context, NewItemActivityAlarm.class);
        //startActivity(context, i, null);


    }
}
