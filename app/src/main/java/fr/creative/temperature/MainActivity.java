package fr.creative.temperature;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

// import pour l'ActionBar
import androidx.appcompat.app.ActionBar;
import android.graphics.drawable.ColorDrawable;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private EditText editTextCelsius, editTextFahrenheit;
    private Button buttonSave, buttonClear;
    private ListView listViewTemperature;

    // gestion de la liste
    private List<String> stringList = new ArrayList<>(); // initialisation de la liste
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // affichage du layout / template
        setContentView(R.layout.activity_main);

        // Récupère une référence à l'ActionBar
        ActionBar actionBar = getSupportActionBar();

        // Vérifie si l'ActionBar est disponible
        if (actionBar != null) {
            // Définit la couleur de fond de l'ActionBar
            actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.teal_700, getTheme())));
        }

        // affectations
        editTextCelsius = findViewById(R.id.editTextCelsius); // équivalent de getElementById
        editTextFahrenheit = findViewById(R.id.editTextFahrenheit);
        buttonSave = findViewById(R.id.buttonSave);
        listViewTemperature = findViewById(R.id.listViewTemperature);

        //editTextCelsius.setText("10"); // appel du setter
        editTextCelsius.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String valeur = s.toString();

                if(editTextCelsius.hasFocus()) {
                    if (!valeur.isEmpty() && TemperatureConverter.isNumeric(valeur)) {
                        String resultat = TemperatureConverter.fahrenheitFromCelsius(Double.parseDouble(valeur));
                        editTextFahrenheit.setText(resultat);
                    } else {
                        editTextFahrenheit.setText(null);
                    }

                }
            }
        });

        editTextFahrenheit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String valeur = s.toString();

                if(editTextFahrenheit.hasFocus()) {
                    if (!valeur.isEmpty() && TemperatureConverter.isNumeric(valeur)) {
                        String resultat = TemperatureConverter.celsiusFromFahrenheit(Double.parseDouble(valeur));
                        editTextCelsius.setText(resultat);
                    } else {
                        editTextCelsius.setText(null);
                    }

                }
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Button b = (Button) v; // Conversion de type
                // Log.d(TAG, "cliquer sur le bouton sauvegarder");

                String celsius = editTextCelsius.getText().toString();
                String fahrenheit = editTextFahrenheit.getText().toString();

                // String valeur = celsius+"°C est égal à "+fahrenheit+"°F";

                String valeur = getString(R.string.main_text_convert, celsius, fahrenheit);

                // OU

                /*
                String valeur = String.format(
                    getString(R.string.main_text_convert),
                    celsius,
                    fahrenheit
                );
                */



                /*
                stringList.add(valeur);
                adapter.notifyDataSetChanged();
                listViewTemperature.setSelection(stringList.size()-1);
                */
                if (!celsius.isEmpty() && !fahrenheit.isEmpty()) {
                    adapter.insert(valeur, 0);
                    editTextCelsius.setText("");
                    editTextFahrenheit.setText("");
                } else {
                    Toast myToast = new Toast(MainActivity.this);
                    myToast.setText("Comment ça mon reuf ?");
                    myToast.setDuration(Toast.LENGTH_SHORT);
                    myToast.show();
                }
            }
        });

        adapter = new ArrayAdapter<String>(
                MainActivity.this,
                android.R.layout.simple_list_item_1,
                /* stringList */ //données
                new ArrayList<>()
        );

        listViewTemperature.setAdapter(adapter);

        listViewTemperature.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // String item = stringList.get(position);
                String item = adapter.getItem(position);

                adapter.remove(item);

                Toast myToast = new Toast(MainActivity.this);
                myToast.setText("Item effacé");
                myToast.setDuration(Toast.LENGTH_SHORT);
                myToast.show();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_default, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuDelete) {
            adapter.clear();
        }
        return super.onOptionsItemSelected(item);
    }
}