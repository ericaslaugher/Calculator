package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textResult;
    private TextView textExpression;

    private String currentNumber = "";
    private String pendingOperator = null;
    private Double operand1 = null;
    private DecimalFormat decimalFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textResult = findViewById(R.id.textResult);
        textExpression = findViewById(R.id.textExpression);
        decimalFormat = new DecimalFormat("#.##########");


        findViewById(R.id.btn0).setOnClickListener(this);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
        findViewById(R.id.btn5).setOnClickListener(this);
        findViewById(R.id.btn6).setOnClickListener(this);
        findViewById(R.id.btn7).setOnClickListener(this);
        findViewById(R.id.btn8).setOnClickListener(this);
        findViewById(R.id.btn9).setOnClickListener(this);
        findViewById(R.id.btnDot).setOnClickListener(this);

        findViewById(R.id.btnAdd).setOnClickListener(this);
        findViewById(R.id.btnSubtract).setOnClickListener(this);
        findViewById(R.id.btnMultiply).setOnClickListener(this);
        findViewById(R.id.btnDivide).setOnClickListener(this);

        findViewById(R.id.btnClear).setOnClickListener(this);
        findViewById(R.id.btnBackspace).setOnClickListener(this);
        findViewById(R.id.btnEquals).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();


        if (id == R.id.btn0 || id == R.id.btn1 || id == R.id.btn2 || id == R.id.btn3 ||
                id == R.id.btn4 || id == R.id.btn5 || id == R.id.btn6 || id == R.id.btn7 ||
                id == R.id.btn8 || id == R.id.btn9) {
            onNumberClick(((Button) v).getText().toString());
        }

        else if (id == R.id.btnDot) {
            onDotClick();
        }

        else if (id == R.id.btnAdd || id == R.id.btnSubtract || id == R.id.btnMultiply || id == R.id.btnDivide) {
            onOperatorClick(((Button) v).getText().toString());
        }

        else if (id == R.id.btnEquals) {
            onEqualsClick();
        }

        else if (id == R.id.btnClear) {
            onClearClick();
        }

        else if (id == R.id.btnBackspace) {
            onBackspaceClick();
        }
    }


    private void onNumberClick(String number) {
        if (currentNumber.length() > 10) return;
        currentNumber += number;
        updateDisplay(currentNumber);
    }


    private void onDotClick() {
        if (!currentNumber.contains(".")) {
            if (currentNumber.isEmpty()) {
                currentNumber = "0.";
            } else {
                currentNumber += ".";
            }
            updateDisplay(currentNumber);
        }
    }


    private void onClearClick() {
        currentNumber = "";
        pendingOperator = null;
        operand1 = null;
        updateDisplay("0");
        updateExpression("");
    }

    private void onBackspaceClick() {
        if (!currentNumber.isEmpty()) {
            currentNumber = currentNumber.substring(0, currentNumber.length() - 1);
            if (currentNumber.isEmpty()) {
                updateDisplay("0");
            } else {
                updateDisplay(currentNumber);
            }
        }
    }


    private void onOperatorClick(String operator) {

        if (currentNumber.isEmpty() && operand1 == null) {
            return;
        }


        if (operand1 != null && pendingOperator != null && !currentNumber.isEmpty()) {
            onEqualsClick();
        }


        if (!currentNumber.isEmpty()) {
            try {
                operand1 = Double.parseDouble(currentNumber);
            } catch (NumberFormatException e) {
                operand1 = null;
            }
        }

        pendingOperator = operator;
        currentNumber = "";


        updateExpression(decimalFormat.format(operand1) + " " + pendingOperator);
        updateDisplay("0");
    }


    private void onEqualsClick() {

        if (operand1 == null || pendingOperator == null || currentNumber.isEmpty()) {
            return;
        }

        double operand2;
        try {
            operand2 = Double.parseDouble(currentNumber);
        } catch (NumberFormatException e) {
            return;
        }

        double result = 0;


        String expression = decimalFormat.format(operand1) + " " + pendingOperator + " " + decimalFormat.format(operand2) + " =";

        switch (pendingOperator) {
            case "+":
                result = operand1 + operand2;
                break;
            case "-":
                result = operand1 - operand2;
                break;
            case "×":
                result = operand1 * operand2;
                break;
            case "÷":

                if (operand2 == 0) {
                    Toast.makeText(this, "Không thể chia cho 0", Toast.LENGTH_SHORT).show();
                    onClearClick();
                    return;
                }
                result = operand1 / operand2;
                break;
        }


        String resultString = decimalFormat.format(result);
        updateDisplay(resultString);
        updateExpression(expression);

        operand1 = result;
        currentNumber = "";
        pendingOperator = null;
    }

    private void updateDisplay(String text) {
        if (text.isEmpty()) {
            textResult.setText("0");
        } else {
            textResult.setText(text);
        }
    }


    private void updateExpression(String text) {
        textExpression.setText(text);
    }
}