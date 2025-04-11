package com.project.financ.Models.API;

import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpRequest {
    public static String Get() {
        // Criando uma nova Thread para evitar NetworkOnMainThreadException
        final String[] resultado = {""};

        Thread thread = new Thread(() -> {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("https://jsonplaceholder.typicode.com/todos/1")
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    resultado[0] = "Sucesso: " + response.body().string();
                } else {
                    resultado[0] = "Erro na chamada da API: Código de resposta " + response.code();
                }
            } catch (Exception e) {
                resultado[0] = "Exceção ao fazer a chamada da API: " + e.getMessage();
            }
        });

        thread.start();

        try {
            thread.join(); // Aguarda a conclusão da thread
        } catch (InterruptedException e) {
            return "Erro ao aguardar a execução da thread: " + e.getMessage();
        }

        return resultado[0];
    }
    public static String Post(Object objeto) {
        final String[] resultado = {""};

        Thread thread = new Thread(() -> {
            OkHttpClient client = new OkHttpClient();

            // Dados para enviar no corpo da requisição (exemplo em JSON)
            String json = objetoParaJson(objeto);

            RequestBody requestBody = RequestBody.create(
                    json, MediaType.get("application/json; charset=utf-8")
            );

            // Criação da requisição POST
            Request request = new Request.Builder()
                    .url("https://financ.requestcatcher.com/test")
                    .post(requestBody)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    resultado[0] = "Sucesso: " + response.body().string();
                } else {
                    resultado[0] = "Erro na chamada da API: Código de resposta " + response.code();
                }
            } catch (Exception e) {
                resultado[0] = "Exceção ao fazer a chamada da API: " + e.getMessage();
            }
        });

        thread.start();

        try {
            thread.join(); // Aguarda a conclusão da thread
        } catch (InterruptedException e) {
            return "Erro ao aguardar a execução da thread: " + e.getMessage();
        }

        return resultado[0];
    }

    public static String objetoParaJson(Object objeto) {
        Gson gson = new Gson();
        return gson.toJson(objeto);
    }
}
