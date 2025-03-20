package com.example.myapplication22;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
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

        // Encontrar os TextViews no layout
        textView = findViewById(R.id.textView4);
        textView3 = findViewById(R.id.textView6);

        // Verificar se os IDs foram encontrados corretamente
        if (textView == null) {
            Log.e("MainActivity", "Erro: textView4 não encontrado!");
        }

        if (textView3 == null) {
            Log.e("MainActivity", "Erro: textView6 não encontrado!");
        }

        // Definir cliques para abrir novas atividades
        if (textView != null) {
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, Noticia2Activity2.class);
                    startActivity(intent);
                }
            });
        }

        if (textView3 != null) {
            textView3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, NoticiaActivity3.class);
                    startActivity(intent);
                }
            });
        }
    }
}
