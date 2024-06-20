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
     * Método que cria uma requisição HTTP para ligar o LED.
     * @return true se o LED foi ligado com sucesso
     */
    public boolean turnLedOn() {

        // Cria uma requisição HTTP a ser enviada ao ESP32
        HttpRequest httpRequest = new HttpRequest("http://" + Config.getESP32Address(context) + "/ligar_led", "GET", "UTF-8");

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

    /**
     * Método que cria uma requisição HTTP e obtem o estado do LED.
     * @return true se o LED esta ligado
     */
    public boolean getLedStatus() {

        // Cria uma requisição HTTP a ser enviada ao ESP32
        HttpRequest httpRequest = new HttpRequest("http://" + Config.getESP32Address(context) + "/pegar_status_led", "GET", "UTF-8");

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

    /*
    public boolean abrirCaixa1() {

        // Cria uma requisição HTTP a ser enviada ao ESP32
        HttpRequest httpRequest = new HttpRequest("http://" + Config.getESP32Address(context) + "/bot1", "GET", "UTF-8");

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

    public boolean abrirCaixa2() {

        // Cria uma requisição HTTP a ser enviada ao ESP32
        HttpRequest httpRequest = new HttpRequest("http://" + Config.getESP32Address(context) + "/bot2", "GET", "UTF-8");

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

    public boolean abrirCaixa3() {

        // Cria uma requisição HTTP a ser enviada ao ESP32
        HttpRequest httpRequest = new HttpRequest("http://" + Config.getESP32Address(context) + "/bot3", "GET", "UTF-8");

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

    //fecha compartimentos
    public boolean fechar1() {

        // Cria uma requisição HTTP a ser enviada ao ESP32
        HttpRequest httpRequest = new HttpRequest("http://" + Config.getESP32Address(context) + "/close1", "GET", "UTF-8");

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

    public boolean fechar2() {

        // Cria uma requisição HTTP a ser enviada ao ESP32
        HttpRequest httpRequest = new HttpRequest("http://" + Config.getESP32Address(context) + "/close2", "GET", "UTF-8");

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

    public boolean fechar3() {

        // Cria uma requisição HTTP a ser enviada ao ESP32
        HttpRequest httpRequest = new HttpRequest("http://" + Config.getESP32Address(context) + "/close3", "GET", "UTF-8");

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

    //liga led rgb
    public boolean turnLedOn() {

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

    //obtem status compartimentos

    public boolean getCaixa1Status() {

        // Cria uma requisição HTTP a ser enviada ao ESP32
        HttpRequest httpRequest = new HttpRequest("http://" + Config.getESP32Address(context) + "/status_caixa1", "GET", "UTF-8");

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

    */
    /**
     * Método que cria uma requisição HTTP para ligar o motor.
     * @return true se o LED foi ligado com sucesso
     */
    public boolean turnMotorOn() {

        // Cria uma requisição HTTP a ser enviada ao ESP32
        HttpRequest httpRequest = new HttpRequest("http://" + Config.getESP32Address(context) + "/ligar_motor", "GET", "UTF-8");

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

    /**
     * Método que cria uma requisição HTTP para desligar o motor.
     * @return true se o LED foi ligado com sucesso
     */
    public boolean turnMotorOff() {

        // Cria uma requisição HTTP a ser enviada ao ESP32
        HttpRequest httpRequest = new HttpRequest("http://" + Config.getESP32Address(context) + "/desligar_motor", "GET", "UTF-8");

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

    /**
     * Método que cria uma requisição HTTP e obtem o estado do motor.
     * @return true se o motor esta ligado
     */
    public boolean getMotorStatus() {

        // Cria uma requisição HTTP a ser enviada ao ESP32
        HttpRequest httpRequest = new HttpRequest("http://" + Config.getESP32Address(context) + "/pegar_status_motor", "GET", "UTF-8");

        String result = "";
        try {
            // Executa a requisição HTTP. É neste momento que o ESP32 é contactado. Ao executar
            // a requisição é aberto um fluxo de dados entre o ESP32 e a app (InputStream is).
            InputStream is = httpRequest.execute();

            // Obtém a resposta fornecida pelo ESP32. O InputStream é convertido em uma String.
            //
            // Em caso de motor ligado, 1. Desligado, 0:
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

    /**
     * Método que cria uma requisição HTTP para ajustar a velocidade do Motor.
     * @return true se o motor esta ligado
     */
    public boolean setMotorVel(int vel) {

        // Cria uma requisição HTTP a ser enviada ao ESP32
        HttpRequest httpRequest = new HttpRequest("http://" + Config.getESP32Address(context) + "/ajustar_vel_motor/" + String.valueOf(vel), "GET", "UTF-8");

        String result = "";
        try {
            // Executa a requisição HTTP. É neste momento que o ESP32 é contactado. Ao executar
            // a requisição é aberto um fluxo de dados entre o ESP32 e a app (InputStream is).
            InputStream is = httpRequest.execute();

            // Obtém a resposta fornecida pelo ESP32. O InputStream é convertido em uma String.
            //
            // Em caso de motor ligado, 1. Desligado, 0:
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
