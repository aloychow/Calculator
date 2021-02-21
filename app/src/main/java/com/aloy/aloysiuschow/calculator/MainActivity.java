package com.aloy.aloysiuschow.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.aloy.aloysiuschow.calculator.R;


public class MainActivity extends AppCompatActivity {

    private TextView result;
    private TextView newNumber;
    private TextView displayOperation;
    View view;

    //Variables to hold the operand and type of calculations
    //private Double operand1 = null;
    //private Double operand2 = null;
    //private String pendingOperation = "=";

    private static final String STATE_PENDING_OPERATION = "PendingOperation";
    private static final String STATE_OPERAND1 = "Operand1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = this.getWindow().getDecorView();
        view.setBackgroundResource(R.color.black);

        result = (TextView) findViewById(R.id.result);
        newNumber = (TextView) findViewById(R.id.newNumber);
        displayOperation = (TextView) findViewById(R.id.operation);

        Button button0 = (Button) findViewById(R.id.button0);
        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);
        Button button4 = (Button) findViewById(R.id.button4);
        Button button5 = (Button) findViewById(R.id.button5);
        Button button6 = (Button) findViewById(R.id.button6);
        Button button7 = (Button) findViewById(R.id.button7);
        Button button8 = (Button) findViewById(R.id.button8);
        Button button9 = (Button) findViewById(R.id.button9);
        Button buttonDot = (Button) findViewById(R.id.buttonDot);

        Button buttonEquals = (Button) findViewById(R.id.buttonEquals);
        Button buttonDivide = (Button) findViewById(R.id.buttonDivide);
        Button buttonMultiply = (Button) findViewById(R.id.buttonMultiply);
        Button buttonMinus = (Button) findViewById(R.id.buttonMinus);
        Button buttonPlus = (Button) findViewById(R.id.buttonPlus);

        Button buttonClear = (Button) findViewById(R.id.buttonClear);
        Button buttonFlip = (Button) findViewById(R.id.buttonFlip);
        Button buttonPercent = (Button) findViewById(R.id.buttonPercent);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button b = (Button) view;
                newNumber.append(b.getText().toString());
            }

        };
        button0.setOnClickListener(listener);
        button1.setOnClickListener(listener);
        button2.setOnClickListener(listener);
        button3.setOnClickListener(listener);
        button4.setOnClickListener(listener);
        button5.setOnClickListener(listener);
        button6.setOnClickListener(listener);
        button7.setOnClickListener(listener);
        button8.setOnClickListener(listener);
        button9.setOnClickListener(listener);
        buttonDot.setOnClickListener(listener);

        View.OnClickListener opListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button b = (Button) view;
                String previousOp = displayOperation.getText().toString();
                String currentOp = b.getText().toString();
                String operand1 = result.getText().toString();
                String operand2 = newNumber.getText().toString();
                try {
                    performOperation(currentOp, previousOp, operand1, Double.valueOf(operand2));
                } catch (NumberFormatException e) {
                    newNumber.setText("");
                }

                displayOperation.setText(currentOp);
            }
        };

        buttonEquals.setOnClickListener(opListener);
        buttonDivide.setOnClickListener(opListener);
        buttonMultiply.setOnClickListener(opListener);
        buttonMinus.setOnClickListener(opListener);
        buttonPlus.setOnClickListener(opListener);

        View.OnClickListener otherListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button b = (Button) view;

                String buttonText = b.getText().toString();
                System.out.println(buttonText);
                if (buttonText.equals("Clear")) {
                    result.setText("");
                    newNumber.setText("");
                    displayOperation.setText("");
                    return;
                }

                if (buttonText.equals("+/-")) {
                    if (!newNumber.getText().toString().equals("")) {
                        Double flip = Double.valueOf(newNumber.getText().toString());
                        flip = -flip;
                        newNumber.setText(flip.toString());
                    }
                    return;
                }

                if (buttonText.equals("%")) {
                    if (!result.getText().toString().equals("")) {
                        Double percent = Double.valueOf(result.getText().toString());
                        percent /= 100;
                        result.setText(percent.toString());
                    }
                    return;
                }
            }
        };

        buttonClear.setOnClickListener(otherListener);
        buttonFlip.setOnClickListener(otherListener);
        buttonPercent.setOnClickListener(otherListener);

    }

    private void performOperation(String current, String previous, String operand1, Double operand_2) {
        displayOperation.setText(current);

        String operand2 = operand_2.toString();

        if (operand1.equals("Infinity") || operand1.equals("-Infinity") || operand1.equals("Undefined")) {
            if (!operand2.equals("")) {
                if (previous.equals("-")) {
                    result.setText("-" + operand2);
                } else {
                    result.setText(operand2.toString());
                }
                newNumber.setText("");
            }
            return;
        }

        if (operand1.equals("") && !operand2.equals("")) {
            operand1 = operand2;
        }

        if (operand2.equals("")) {
            return;
        }

        if (!operand1.equals("") && !operand2.equals("")) {
            Double operand_1 = Double.valueOf(operand1);
            //Double operand_2 = Double.valueOf(operand2);

            switch (previous) {
                case "+":
                    operand_1 += operand_2;
                    break;
                case "-":
                    operand_1 -= operand_2;
                    break;
                case "x":
                    operand_1 *= operand_2;
                    break;
                case "\u00F7":
                    operand_1 /= operand_2;
                    break;
                case "=":
                    operand_1 = operand_2;
                    break;


            }
            operand1 = operand_1.toString();

        }

        if (operand1.equals("NaN")) {
            result.setText("Undefined");
            newNumber.setText("");
            return;
        }

        result.setText(operand1);
        newNumber.setText("");
    }


}
