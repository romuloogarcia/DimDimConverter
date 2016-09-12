package br.edu.pdm.dimdimconverter;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class PrincipalActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtInforme;
    private EditText edtDolar;
    private EditText edtEuro;
    private EditText edtPeso;
    private ImageButton btnConverter;
    private ImageButton btnSobre;
    private ImageButton btnLimpar;
    private ImageButton btnSair;
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
        btnLimpar = (ImageButton) findViewById(R.id.btnLimpar);
        btnSair = (ImageButton) findViewById(R.id.btnSair);
        rgMoeda = (RadioGroup) findViewById(R.id.rgMoeda);

        btnConverter.setOnClickListener(this);
        btnSobre.setOnClickListener(this);
        btnLimpar.setOnClickListener(this);
        btnSair.setOnClickListener(this);

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
                    String cleanString = charSequence.toString().replaceAll("[^0-9]", "");
                    if (!cleanString.equals("")) {
                        BigDecimal price;
                        try {
                            price = new BigDecimal(cleanString).setScale(2,BigDecimal.ROUND_FLOOR).divide(new BigDecimal(100),BigDecimal.ROUND_FLOOR);
                        } catch (NumberFormatException e) {
                            price = new BigDecimal(0.0);
                        }

                        String formatted = NumberFormat.getCurrencyInstance().format(price);
                        current = formatted;
                        curEdt.setText(formatted);
                        curEdt.setSelection(formatted.length());
                    }
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
                } else {
                    if (rgMoeda.getCheckedRadioButtonId() == -1) {
                        Toast.makeText(this, R.string.msgSelecionar, Toast.LENGTH_LONG).show();
                    } else {
                        double vlReal = (Double.parseDouble(edtInforme.getText().toString().replaceAll("[^0-9]", ""))) / 100;
                        DecimalFormat df = new DecimalFormat("0.00");
                        switch (rgMoeda.getCheckedRadioButtonId()) {
                            case R.id.rdDolar:
                                double vlDolar = (Double.parseDouble(edtDolar.getText().toString().replaceAll("[^0-9]", ""))) / 100;
                                Toast.makeText(this, "R$ " + df.format(vlReal) + " = US$ " + df.format(vlReal / vlDolar), Toast.LENGTH_LONG).show();
                                break;
                            case R.id.rdEuro:
                                double vlEuro = (Double.parseDouble(edtEuro.getText().toString().replaceAll("[^0-9]", ""))) / 100;
                                Toast.makeText(this, "R$ " + df.format(vlReal) + " = € " + df.format(vlReal / vlEuro), Toast.LENGTH_LONG).show();
                                break;
                            case R.id.rdPeso:
                                double vlPeso = (Double.parseDouble(edtPeso.getText().toString().replaceAll("[^0-9]", ""))) / 100;
                                Toast.makeText(this, "R$ " + df.format(vlReal) + " = $ " + df.format(vlReal / vlPeso), Toast.LENGTH_LONG).show();
                                break;
                        }
                        rgMoeda.clearCheck();
                        edtInforme.setText("");
                        edtInforme.requestFocus();
                    }
                }
                break;
            case R.id.btnSobre:
                Intent it = new Intent(this, AboutActivity.class);
                startActivity(it);
                finish();
                break;
            case R.id.btnLimpar:
                rgMoeda.clearCheck();
                edtInforme.setText("");
                edtDolar.setText("");
                edtEuro.setText("");
                edtPeso.setText("");
                edtInforme.requestFocus();
                break;
            case R.id.btnSair:
                sair();
                break;
        }

    }

    private void sair() {
        AlertDialog.Builder dialogoSair = new AlertDialog.Builder(this);
        dialogoSair.setTitle(R.string.dlgSair);
        dialogoSair.setMessage(R.string.msgSair);
        dialogoSair.setNegativeButton(R.string.opNao, null);
        dialogoSair.setPositiveButton(R.string.opSim, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
                System.exit(0);
            }
        });
        dialogoSair.show();
    }

}
