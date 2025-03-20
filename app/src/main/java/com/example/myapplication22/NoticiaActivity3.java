package com.example.myapplication22;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class NoticiaActivity3 extends AppCompatActivity {
    private TextView textView;
    private TextView textView2;
    private Spinner spinnerCountry;
    private Button buttonSearch;
    private ImageView imageView;  // Imagem da espécie

    private ArrayList<String> countryList = new ArrayList<>();
    private ArrayAdapter<String> countryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticia3);

        // Encontrar os elementos no layout
        textView = findViewById(R.id.textViewNoticia5);
        textView2 = findViewById(R.id.textViewNoticia6);
        spinnerCountry = findViewById(R.id.spinnerCountry);
        buttonSearch = findViewById(R.id.buttonSearch);
          // Referência para a ImageView

        // Configurar o Spinner
        countryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, countryList);
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCountry.setAdapter(countryAdapter);

        // Carregar a lista de países
        new GetCountries().execute("https://api.gbif.org/v1/enumeration/country");

        // Configurar o clique do botão
        buttonSearch.setOnClickListener(v -> {
            String selectedCountry = spinnerCountry.getSelectedItem().toString();

            // Extrair o código ISO do país selecionado
            String countryIso = selectedCountry.split("\\(")[1].split("\\)")[0];

            if (!selectedCountry.isEmpty()) {
                // Chamar a API com o código ISO do país
                new GetData().execute("https://api.gbif.org/v1/occurrence/search?country=" + countryIso + "&limit=50");
            } else {
                // Mensagem caso o país não tenha sido selecionado
                textView.setText("Por favor, selecione um país.");
                textView2.setText("");
            }
        });
    }

    // Task para obter a lista de países
    private class GetCountries extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                return response.toString();
            } catch (Exception e) {
                return "Erro: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d("API_Response", result);

            if (result != null && !result.startsWith("Erro")) {
                try {
                    JSONArray countriesArray = new JSONArray(result);

                    for (int i = 0; i < countriesArray.length(); i++) {
                        JSONObject countryObject = countriesArray.getJSONObject(i);
                        String countryName = countryObject.getString("title");
                        String countryIso = countryObject.getString("iso2");
                        countryList.add(countryName + " (" + countryIso + ")");
                    }

                    // Atualizar o adapter para refletir as mudanças no Spinner
                    countryAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    Log.e("Erro JSON", "Erro ao processar JSON: " + e.getMessage());
                }
            } else {
                textView.setText("Erro ao carregar a lista de países.");
                textView2.setText("");
            }
        }
    }

    // Task para obter dados da API com base no país selecionado
    private class GetData extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                return response.toString();
            } catch (Exception e) {
                return "Erro: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d("API_Response", result);

            if (result != null && !result.startsWith("Erro")) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray resultsArray = jsonObject.getJSONArray("results");

                    if (resultsArray.length() > 0) {
                        JSONObject firstResult = resultsArray.getJSONObject(0);
                        String species = firstResult.optString("scientificName", "Nome não encontrado");
                        String country = firstResult.optString("country", "País não encontrado");

                        // Exibir as informações no layout
                        textView.setText("Espécie encontrada:");
                        textView2.setText("Nome: " + species + "\nPaís: " + country);

                    } else {
                        textView.setText("Nenhum dado encontrado.");
                        textView2.setText("");
                    }
                } catch (JSONException e) {
                    Log.e("Erro JSON", "Erro ao processar JSON: " + e.getMessage());
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
