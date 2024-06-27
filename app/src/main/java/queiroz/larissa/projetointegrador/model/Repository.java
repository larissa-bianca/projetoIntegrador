package queiroz.larissa.projetointegrador.model;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

import queiroz.larissa.projetointegrador.util.Util;
import queiroz.larissa.projetointegrador.util.Config;
import queiroz.larissa.projetointegrador.util.HttpRequest;

public class Repository {

    Context context;
    public Repository(Context context) {
        this.context = context;
    }

    /**
     * Método que cria uma requisição HTTP para desligar o LED.
     * @return true se o LED foi ligado com sucesso
     */
    public boolean fecharCaixa(int caixa) {

        // Cria uma requisição HTTP a ser enviada ao ESP32
        HttpRequest httpRequest = new HttpRequest("http://" + Config.getESP32Address(context) + "/close" + String.valueOf(caixa), "GET", "UTF-8");

        String result = "";
        try {
            // Executa a requisição HTTP. É neste momento que o ESP32 é contactado. Ao executar
            // a requisição é aberto um fluxo de dados entre o ESP32 e a app (InputStream is).
            InputStream is = httpRequest.execute();

            // Obtém a resposta fornecida pelo ESP32. O InputStream é convertido em uma String.
            //
            // Em caso de sucesso, 1. Falha, 0:
            result = Util.inputStream2String(is, "UTF-8");

            Log.i("HTTP REGISTER RESULT", result);

            // Fecha a conexão com o ESP32.
            httpRequest.finish();

            // Se result igual a 1, significa que o usuário foi registrado com sucesso.
            if(result.equals("1")) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean abrirCaixa(int caixa) {

        // Cria uma requisição HTTP a ser enviada ao ESP32
        HttpRequest httpRequest = new HttpRequest("http://" + Config.getESP32Address(context) + "/bot" + String.valueOf(caixa), "GET", "UTF-8");

        String result = "";
        try {
            // Executa a requisição HTTP. É neste momento que o ESP32 é contactado. Ao executar
            // a requisição é aberto um fluxo de dados entre o ESP32 e a app (InputStream is).
            InputStream is = httpRequest.execute();

            // Obtém a resposta fornecida pelo ESP32. O InputStream é convertido em uma String.
            //
            // Em caso de sucesso, 1. Falha, 0:
            result = Util.inputStream2String(is, "UTF-8");

            Log.i("HTTP REGISTER RESULT", result);

            // Fecha a conexão com o ESP32.
            httpRequest.finish();

            // Se result igual a 1, significa que o usuário foi registrado com sucesso.
            if(result.equals("1")) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean ligarLed() {

        // Cria uma requisição HTTP a ser enviada ao ESP32
        HttpRequest httpRequest = new HttpRequest("http://" + Config.getESP32Address(context) + "/ledH", "GET", "UTF-8");

        String result = "";
        try {
            // Executa a requisição HTTP. É neste momento que o ESP32 é contactado. Ao executar
            // a requisição é aberto um fluxo de dados entre o ESP32 e a app (InputStream is).
            InputStream is = httpRequest.execute();

            // Obtém a resposta fornecida pelo ESP32. O InputStream é convertido em uma String.
            //
            // Em caso de sucesso, 1. Falha, 0:
            result = Util.inputStream2String(is, "UTF-8");

            Log.i("HTTP REGISTER RESULT", result);

            // Fecha a conexão com o ESP32.
            httpRequest.finish();

            // Se result igual a 1, significa que o usuário foi registrado com sucesso.
            if(result.equals("1")) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean piscarBuzzer() {

        // Cria uma requisição HTTP a ser enviada ao ESP32
        HttpRequest httpRequest = new HttpRequest("http://" + Config.getESP32Address(context) + "/buzzerH", "GET", "UTF-8");

        String result = "";
        try {
            // Executa a requisição HTTP. É neste momento que o ESP32 é contactado. Ao executar
            // a requisição é aberto um fluxo de dados entre o ESP32 e a app (InputStream is).
            InputStream is = httpRequest.execute();

            // Obtém a resposta fornecida pelo ESP32. O InputStream é convertido em uma String.
            //
            // Em caso de sucesso, 1. Falha, 0:
            result = Util.inputStream2String(is, "UTF-8");

            Log.i("HTTP REGISTER RESULT", result);

            // Fecha a conexão com o ESP32.
            httpRequest.finish();

            // Se result igual a 1, significa que o usuário foi registrado com sucesso.
            if(result.equals("1")) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
