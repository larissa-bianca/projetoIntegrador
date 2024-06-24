package queiroz.larissa.projetointegrador.util;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import queiroz.larissa.projetointegrador.model.Compartimento;

public class Config {

    /**
     * Salva o endereço do ESP32 no espaço reservado da app
     * @param context contexto da app
     * @param login o login
     */
    public static void setESP32Address(Context context, String login) {
        SharedPreferences mPrefs = context.getSharedPreferences("configs", 0);
        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putString("esp32address", login).commit();
    }

    /**
     * Obtem o o endereço do ESP32 salvo no espaço reservado da app
     * @param context
     * @return
     */
    public static String getESP32Address(Context context) {
        SharedPreferences mPrefs = context.getSharedPreferences("configs", 0);
        return mPrefs.getString("esp32address", "");
    }

    /**
     * Salva o endereço do ESP32 no espaço reservado da app
     * @param context contexto da app
     * @param login o login
     */
    public static void salvarCompartimento(Context context, int caixa, Compartimento compartimento) throws JSONException {
        JSONObject obj = new JSONObject();

        obj.put("hora", compartimento.hora);
        obj.put("nome", compartimento.nome);
        obj.put("data", compartimento.data);
        obj.put("qtd", compartimento.qtd);
        obj.put("desc", compartimento.desc);


        SharedPreferences mPrefs = context.getSharedPreferences("configs", 0);
        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putString("caixa" + String.valueOf(caixa), obj.toString()).commit();
    }

    /**
     * Obtem o o endereço do ESP32 salvo no espaço reservado da app
     * @param context
     * @return
     */
    public static Compartimento pegarCompartimento(Context context, int caixa) throws JSONException {
        SharedPreferences mPrefs = context.getSharedPreferences("configs", 0);
        String caixaJson = mPrefs.getString("caixa" + String.valueOf(caixa), "");

        JSONObject obj = new JSONObject(caixaJson);
        Compartimento c = new Compartimento();
        c.hora = obj.getString("hora");
        c.nome = obj.getString("nome");
        c.hora = obj.getString("hora");
        c.qtd = obj.getString("qtd");
        c.desc = obj.getString("desc");
        return c;
    }
}
