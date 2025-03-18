package com.example.myapplication22;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NoticiaActivity3 extends AppCompatActivity {
    private TextView textView;
    private TextView textView2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticia3);

        // Encontrar os TextViews no layout
        textView = findViewById(R.id.textViewNoticia5);
        textView2 = findViewById(R.id.textViewNoticia6);

        // Chamar a API assim que o app abrir
        new GetData().execute("https://appsnis.mdr.gov.br/indicadores-hmg/web/residuos_solidos/mapa-indicadores");
    }

    private class GetData extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();
                    return response.toString();
                } else {
                    return "Erro: " + responseCode;
                }
            } catch (Exception e) {
                return "Erro: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            // Log para depuração
            Log.d("API_Response", result);

            if (result != null && !result.isEmpty()) {
                try {
                    // Usando o jsoup para fazer o parsing do HTML
                    Document document = Jsoup.parse(result);

                    // Extraindo o valor da Massa coletada total
                    Element massaColetadaTotal = document.select("a[title=IN028 - Massa de resíduos domiciliares e públicos (rdo+rpu) coletada per capita em relação à população total atendida pelo serviço de coleta]").first();

                    // Verifique se a massa coletada total foi encontrada
                    String massaColetadaText = "";
                    if (massaColetadaTotal != null) {
                        massaColetadaText = massaColetadaTotal.text();
                    } else {
                        massaColetadaText = "Indicador não encontrado";
                    }

                    // Exibir os dados no TextView
                    textView.setText("Indicadores operacionais");
                    textView2.setText("Massa coletada total: " + massaColetadaText);
                } catch (Exception e) {
                    Log.e("Error", "Erro ao processar o HTML: " + e.getMessage());
                    textView.setText("Erro ao processar os dados.");
                    textView2.setText("");
                }
            } else {
                textView.setText("Erro ao carregar os dados.");
                textView2.setText("");
            }
        }
    }
}
