package com.example.currencyconverter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button bnt1, btn2, btn3, btn4, bnt5, btn6, btn7, btn8, bnt9, btn0, btndel, btncheck, btnpoint;
    EditText editText;
    Spinner fromCurrency, toCurrency;
    public static BreakIterator data;
    List<String> keysList;
    String valof = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bnt1 = findViewById(R.id.btn_one);
        btn2 = findViewById(R.id.btn_two);
        btn3 = findViewById(R.id.btn_three);
        btn4 = findViewById(R.id.btn_four);
        bnt5 = findViewById(R.id.btn_five);
        btn6 = findViewById(R.id.btn_six);
        btn7 = findViewById(R.id.btn_seven);
        btn8 = findViewById(R.id.btn_eight);
        bnt9 = findViewById(R.id.btn_nine);
        btn0 = findViewById(R.id.btn_zero);
        btnpoint = findViewById(R.id.btn_point);
        btndel = findViewById(R.id.btn_delete);
        btncheck = findViewById(R.id.btn_sumbit);
        editText = findViewById(R.id.entervalue);

        toCurrency = (Spinner) findViewById(R.id.spinerfrom);
        fromCurrency = (Spinner) findViewById(R.id.spinerto);

        editText.setText("");
        loadConvTypes();

        bnt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().equals("")) {
                    editText.setText("1");
                } else {
                    editText.append("1");
                }
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().equals("")) {
                    editText.setText("2");
                } else {
                    editText.append("2");
                }
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().equals("")) {
                    editText.setText("3");
                } else {
                    editText.append("3");
                }
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().equals("")) {
                    editText.setText("4");
                } else {
                    editText.append("4");
                }
            }
        });
        bnt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().equals("")) {
                    editText.setText("5");
                } else {
                    editText.append("5");
                }
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().equals("")) {
                    editText.setText("6");
                } else {
                    editText.append("6");
                }
            }
        });
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().equals("")) {
                    editText.setText("7");
                } else {
                    editText.append("7");
                }
            }
        });

        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().equals("")) {
                    editText.setText("8");
                } else {
                    editText.append("8");
                }
            }
        });
        bnt9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().equals("")) {
                    editText.setText("9");
                } else {
                    editText.append("9");
                }
            }
        });
        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().equals("")) {
                    editText.setText("0");
                } else {
                    editText.append("0");
                }
            }
        });
        btnpoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().equals("")) {
                    editText.setText(".");
                } else {
                    editText.append(".");
                }
            }
        });

        btndel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().equals("")) {
                } else {
                    editText.setText("");
                }
            }
        });


        btncheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editText.getText().toString().isEmpty()) {

                    String fromCurr = fromCurrency.getSelectedItem().toString();
                    double euroVlaue = Double.valueOf(editText.getText().toString());
                    try {
                        convertCurrency(fromCurr, euroVlaue);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Please Enter a Value to Convert..", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getValue(final String toCurr, final double euroVlaue, final double vlaue) {
        String url = "https://api.exchangeratesapi.io/latest";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).header("Content-Type", "application/json").build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                String mMessage = e.getMessage().toString();
                Log.w("failure Response", mMessage);
                Toast.makeText(MainActivity.this, mMessage, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String mMessage = response.body().string();
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject obj = new JSONObject(mMessage);
                            JSONObject b = obj.getJSONObject("rates");
                            valof = b.getString(toCurr);
                            double output = euroVlaue / Double.valueOf(valof);
                            Intent intent = new Intent(MainActivity.this, ActivityResult.class);
                            intent.putExtra("toc", String.valueOf(output));
                            intent.putExtra("fromc", String.valueOf(vlaue));
                            intent.putExtra("fromcn", toCurr);
                            String fromCurr = fromCurrency.getSelectedItem().toString();
                            intent.putExtra("tomcn", fromCurr);
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }


    public void convertCurrency(final String toCurr, final double euroVlaue) throws IOException {
        String url = "https://api.exchangeratesapi.io/latest";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).header("Content-Type", "application/json").build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                String mMessage = e.getMessage().toString();
                Log.w("failure Response", mMessage);
                Toast.makeText(MainActivity.this, mMessage, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String mMessage = response.body().string();
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject obj = new JSONObject(mMessage);
                            JSONObject b = obj.getJSONObject("rates");
                            String val = b.getString(toCurr);
                            double output = euroVlaue * Double.valueOf(val);
                            String toCurr = toCurrency.getSelectedItem().toString();
                            getValue(toCurr, output, euroVlaue);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    private void loadConvTypes() {
        String url = "https://api.exchangeratesapi.io/latest?symbols=USD,GBP";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).header("Content-Type", "application/json").build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                String mMessage = e.getMessage().toString();
                Log.w("failure Response", mMessage);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String mMessage = response.body().string();
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject obj = new JSONObject(mMessage);
                            JSONObject b = obj.getJSONObject("rates");
                            Iterator keysToCopyIterator = b.keys();
                            keysList = new ArrayList<String>();
                            while (keysToCopyIterator.hasNext()) {
                                String key = (String) keysToCopyIterator.next();
                                keysList.add(key);
                            }
                            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, keysList);
                            toCurrency.setAdapter(spinnerArrayAdapter);
                            fromCurrency.setAdapter(spinnerArrayAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

    }
}
