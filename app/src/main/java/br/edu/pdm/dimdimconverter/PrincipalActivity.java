package br.edu.pdm.dimdimconverter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.NumberFormat;

public class PrincipalActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtInforme;
    private EditText edtDolar;
    private EditText edtEuro;
    private EditText edtPeso;
    private ImageButton btnConverter;
    private ImageButton btnSobre;
    private RadioGroup rgMoeda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        edtInforme = (EditText) findViewById(R.id.edtInforme);
        edtDolar = (EditText) findViewById(R.id.edtDolar);
        edtEuro = (EditText) findViewById(R.id.edtEuro);
        edtPeso = (EditText) findViewById(R.id.edtPeso);
        btnConverter = (ImageButton) findViewById(R.id.btnConverter);
        btnSobre = (ImageButton) findViewById(R.id.btnSobre);
        rgMoeda = (RadioGroup) findViewById(R.id.rgMoeda);

        btnConverter.setOnClickListener(this);
        btnSobre.setOnClickListener(this);

        edtInforme.addTextChangedListener(setWatcher(edtInforme));
        edtDolar.addTextChangedListener(setWatcher(edtDolar));
        edtEuro.addTextChangedListener(setWatcher(edtEuro));
        edtPeso.addTextChangedListener(setWatcher(edtPeso));

    }

    @NonNull
    public TextWatcher setWatcher(final EditText curEdt) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            String current = "";

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.equals(current)) {
                    curEdt.removeTextChangedListener(this);
                    String cleanString = charSequence.toString().replaceAll("[R$,.]", "");
                    double parsed = Double.parseDouble(cleanString);
                    String formatted = NumberFormat.getCurrencyInstance().format((parsed / 100));
                    current = formatted;
                    curEdt.setText(formatted);
                    curEdt.setSelection(formatted.length());
                    curEdt.addTextChangedListener(this);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnConverter:
                if (edtInforme.getText().toString().trim().equals("") || edtDolar.getText().toString().trim().equals("") || edtEuro.getText().toString().trim().equals("") || edtPeso.getText().toString().trim().equals("")) {
                    Toast.makeText(this, R.string.msgVazio, Toast.LENGTH_LONG).show();
                } else if (rgMoeda.getCheckedRadioButtonId() != -1) {
                    Toast.makeText(this, R.string.msgSelecionar, Toast.LENGTH_LONG).show();
                } else {
                    switch (rgMoeda.getCheckedRadioButtonId()) {
                        case R.id.rdDolar:
                            break;
                        case R.id.rdEuro:
                            break;
                        case R.id.rdPeso:
                            break;
                    }
                }

                break;
            case R.id.btnSobre:
                break;
        }

    }

}
