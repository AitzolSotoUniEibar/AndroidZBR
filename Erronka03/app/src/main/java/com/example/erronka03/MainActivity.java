package com.example.erronka03;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class MainActivity extends AppCompatActivity {

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
        ListView produktuakList = findViewById(R.id.produktuakList);
        DatabaseHelper dbHelper = new DatabaseHelper(this);

        List<Produktua> produktuak = dbHelper.getAllProduktuak();//Produktuak jaso
        ProduktuaAdapter produktuaAdapter = new ProduktuaAdapter(this,produktuak);//Adapter sortu eta produktuak pasatu
        produktuakList.setAdapter(produktuaAdapter);//Produktuak erakutsi
    }


    public void SaioaItxi(View view){
        SharedPreferences sharedPref = getSharedPreferences("NireDatuak",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear().apply();
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }
}