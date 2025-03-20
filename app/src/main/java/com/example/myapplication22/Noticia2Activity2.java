package com.example.myapplication22;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Noticia2Activity2 extends AppCompatActivity {

    private TextView textView;
    private TextView textView2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_noticia2);  // <-- ADICIONADO

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Encontrar os TextViews no layout
        textView = findViewById(R.id.textViewNoticia1);
        textView2 = findViewById(R.id.textViewNoticia2);

        // Verificar se os TextViews foram encontrados
        if (textView == null || textView2 == null) {
            throw new RuntimeException("Erro: TextViews não encontrados no layout.");
        }

        // Definir textos
        textView.setText("Conhecimento das Espécies");
        textView2.setText("Ignorar as espécies que habitam o seu país é escolher a cegueira diante de um ecossistema que sustenta a sua própria existência. Saber quais animais, plantas e microrganismos compartilham o território com você não é apenas um capricho de biólogos — é um ato de inteligência.\n" +
                "\n" +
                "A biodiversidade não é uma decoração da natureza, mas sim um mecanismo essencial para o equilíbrio ambiental, a segurança alimentar e até a economia. Cada espécie cumpre um papel: abelhas polinizam os alimentos que você come, árvores filtram o ar que você respira, predadores naturais mantêm pragas sob controle. Quando uma peça desse quebra-cabeça desaparece, os impactos chegam até você, seja na forma de desastres naturais, aumento de doenças ou colapsos agrícolas.\n" +
                "\n" +
                "E tem mais: conhecer a fauna e a flora do seu país te protege. Você sabe diferenciar uma planta medicinal de uma venenosa? Sabe identificar um animal inofensivo de um realmente perigoso? A ignorância, nesse caso, pode custar caro.\n" +
                "\n" +
                "Além disso, há uma questão de soberania. Se você não conhece sua própria biodiversidade, grandes corporações e interesses externos tomam a frente, patenteando e explorando riquezas naturais que pertencem ao seu território. Enquanto alguns dormem na ilusão de que a natureza é algo distante, outros fazem bilhões explorando sua falta de atenção.\n" +
                "\n" +
                "Saber quais espécies habitam seu país não é um detalhe — é uma questão de sobrevivência, de poder e de pertencimento. Se você não se importa, alguém se importa por você, e nem sempre a seu favor.");
    }
}
