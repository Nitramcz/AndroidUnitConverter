package com.nitramtul.unitconverter;

import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity {

    private EditText etVal;
    private EditText etRes;
    private Spinner spinner;

    private ArrayList<String> historyArray;
    private ArrayAdapter<String> historyAdapter;
    private ListView lvHistory;

    private int typ_prevodu = 0;
    private String typ_prevodu_text = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etVal = (EditText) findViewById(R.id.etValue);
        etRes = (EditText) findViewById(R.id.etResult);
        spinner = (Spinner) findViewById(R.id.spinner_typ);

        // spinner + arrayAdapter
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.typy_prevodu, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //napojeni listenera na spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                ((TextView) parent.getChildAt(0)).setTextSize(18);

                etRes.setText("");
                typ_prevodu = position;
                typ_prevodu_text = parent.getItemAtPosition(position).toString();

                String[] splitted_prevod_text = typ_prevodu_text.split(" to ");
                etVal.setHint(splitted_prevod_text[0]+"");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //do nothing
            }
        });

        //listview history
        lvHistory = (ListView) findViewById(R.id.lvHistory);
        historyArray = new ArrayList<String>();
        historyAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1, historyArray);
        lvHistory.setAdapter(historyAdapter);
    }

    public void swap(View v) {
        if ((typ_prevodu % 2 ) == 0) {
            spinner.setSelection(typ_prevodu + 1);
        } else {
            spinner.setSelection(typ_prevodu - 1);
        }
    }

    public void clear_value(View v) {
        etVal.setText("");
        etRes.setText("");
    }

    public void conversion(View v) {
        float value = 0;
        float result = 0;

        if (TextUtils.isEmpty(etVal.getText().toString())) {
            return;
        }
        value = Float.parseFloat(etVal.getText().toString());

        switch(typ_prevodu) {
            case 0: // km/h > m/s
                result = value / (float) 3.6;
                break;
            case 1: // m/s > km/h
                result = value * (float) 3.6;
                break;
            case 2: // mile > km
                result = value / (float) 0.62137;
                break;
            case 3: // km > mile
                result = value * (float) 0.62137;
                break;
            case 4: // cm > inch
                result = value / (float) 2.54;
                break;
            case 5: // inch > cm
                result = value * (float) 2.54;
                break;
            case 6: // hp > kW
                result = value / (float) 1.3410;
                break;
            case 7: // kW > hp
                result = value * (float) 1.3410;
                break;
            case 8: // C > F
                result = (value * ((float) 9 / (float) 5)) + 32;
                break;
            case 9: // F > C
                result = (value - 32) * ( (float) 5 / (float) 9);
                break;
            case 10: // kg > pound
                result = value * (float) 2.204623;
                break;
            case 11: // pound > kg
                result = value / (float) 2.204623;
                break;
            case 12: // liter > gallon (US)
                result = value / (float) 3.785412;
                break;
            case 13: // gallon (US) > liter
                result = value * (float) 3.785412;
                break;

        }
        String[] splitted_prevod_text = typ_prevodu_text.split(" to ");
        etRes.setText(result + " " + splitted_prevod_text[splitted_prevod_text.length - 1], TextView.BufferType.NORMAL);

        //zobrazeni vysledku v historii
        historyAdapter.insert(value + " " + splitted_prevod_text[0] + " = " + String.format("%.4f", result) + " " + splitted_prevod_text[splitted_prevod_text.length - 1], 0);
    }
}