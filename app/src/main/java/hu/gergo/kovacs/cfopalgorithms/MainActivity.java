package hu.gergo.kovacs.cfopalgorithms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import hu.gergo.kovacs.cfopalgorithms.util.adapter.MainMenuAdapter;


public class MainActivity extends AppCompatActivity {

    private ListView listView;
    MainMenuAdapter mainMenuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.main_listview);
        setupMainMenu();
    }


    private void setupMainMenu() {
        String[] title = getResources().getStringArray(R.array.main_list);
        String[] descriptions = getResources().getStringArray(R.array.main_list_description);

        mainMenuAdapter = new MainMenuAdapter(this, title, descriptions);
        listView.setAdapter(mainMenuAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position) {
                    case 0: {
                        Intent intent = new Intent(MainActivity.this, CFOPActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 1: {
                        Intent intent = new Intent(MainActivity.this, CrossActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 2: {
                        Intent intent = new Intent(MainActivity.this, F2LActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 3: {
                        Intent intent = new Intent(MainActivity.this, OLLActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 4: {
                        Intent intent = new Intent(MainActivity.this, PLLActivity.class);
                        startActivity(intent);
                        break;
                    }
                }
            }
        });
    }
}
