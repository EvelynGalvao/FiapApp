package com.example.myapplication22;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private TextView textView2;
    private TextView textView3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Encontrar o TextView no layout para exibir os dados da API
        textView = findViewById(R.id.textView4);
        textView2 = findViewById(R.id.textView5);
        textView3 = findViewById(R.id.textView6);
        // Chamar a API assim que o app abrir
        new GetData().execute("https://www2.inpe.br/climaespacial/portal/o-programa-de-clima-espacial-do-instituto-national-de-pesquisas-espaciais-inpe-divulga-o-novo-aplicativo-de-compartilhamento-de-dados-e-api-para-divulgacao-de-dados-cientificos/");
//        new GetData().execute("https://jsonplaceholder.typicode.com/posts/1");

        ImageView recycleCircle = findViewById(R.id.reciclagem_circulo);
//        recycleCircle.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, RecycleActivity.class)));

        ImageView calendarCircle = findViewById(R.id.calendario_circulo);
        calendarCircle.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CalendarActivity.class)));

        ImageView earthCircle = findViewById(R.id.terra_circulo);
        earthCircle.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, EarthActivity.class)));


        textView.setOnClickListener(v -> {

            Intent intent = new Intent(MainActivity.this, Noticia1Activity.class);
            startActivity(intent);
        });

        textView2.setOnClickListener(v -> {

            Intent intent = new Intent(MainActivity.this, Noticia2Activity2.class);
            startActivity(intent);
        });

        textView3.setOnClickListener(v -> {

            Intent intent = new Intent(MainActivity.this, NoticiaActivity3.class);
            startActivity(intent);
        });
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
            textView.setText(result);
            System.out.print(result);
            // Pegando o título completo
            String title = result.replaceAll(".*<title>(.*?)</title>.*", "$1");

            // Usando regex para pegar a primeira frase (tudo antes do primeiro ponto)
            String firstSentence = title.replaceAll("^(.*?\\.).*", "$1");

            // Exibindo apenas a primeira frase no TextView
            textView.setText( firstSentence);

            // Log para depuração
            Log.d("HTML_Response", result);
            Log.d("First_Sentence", firstSentence);
        }
    }
}
