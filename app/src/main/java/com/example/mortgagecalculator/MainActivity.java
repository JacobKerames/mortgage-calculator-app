package com.example.mortgagecalculator;

import android.os.Bundle; // for saving state information
import androidx.appcompat.app.AppCompatActivity; // base class
import android.text.Editable; // for EditText event handling
import android.text.TextWatcher; // EditText listener
import android.widget.EditText; // for bill amount input
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar; // for changing the tip percentage
import android.widget.SeekBar.OnSeekBarChangeListener; // SeekBar listener
import android.widget.TextView; // for displaying text

import java.text.NumberFormat; // for currency formatting

public class MainActivity extends AppCompatActivity {

    // currency and percent formatter objects
    private static final NumberFormat currencyFormat =
            NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat =
            NumberFormat.getPercentInstance();

    private double purchasePrice; // purchase price entered by the user
    private double downPaymentAmount; // down-payment amount entered by the user
    private double interestRate; // interest rate entered by the user
    private int loanDuration; // loan duration entered by the user

    private TextView purchasePriceTextView; // shows formatted purchase price amount
    private TextView downPaymentAmountTextView; // shows formatted down payment amount
    private TextView interestRateTextView; // shows formatted interest rate
    private TextView loanDurationTextView; // shows formatted loan duration
    private TextView monthlyPaymentTextView; // shows formatted monthly payment amount
    private SeekBar loanDurationSeekBar; // for changing loan duration
    private RadioGroup loanDurationRadioGroup; // for changing loan duration

    // called when the activity is first created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // call superclass onCreate
        setContentView(R.layout.activity_main); // inflate the GUI

        // get references to programmatically manipulated TextViews
        purchasePriceTextView = findViewById(R.id.purchasePriceTextView);
        downPaymentAmountTextView = findViewById(R.id.downPaymentAmountTextView);
        interestRateTextView = findViewById(R.id.interestRateTextView);
        loanDurationTextView = findViewById(R.id.loanDurationTextView);
        loanDurationSeekBar = findViewById(R.id.loanDurationSeekBar);
        loanDurationRadioGroup = findViewById(R.id.loanDurationRadioGroup);
        monthlyPaymentTextView = findViewById(R.id.monthlyPaymentTextView);

        // set TextWatchers
        EditText purchasePriceEditText =
                findViewById(R.id.purchasePriceEditText);
        purchasePriceEditText.addTextChangedListener(purchasePriceEditTextWatcher);

        EditText downPaymentAmountEditText =
                findViewById(R.id.downPaymentAmountEditText);
        downPaymentAmountEditText.addTextChangedListener(downPaymentAmountEditTextWatcher);

        EditText interestRateEditText =
                findViewById(R.id.interestRateEditText);
        interestRateEditText.addTextChangedListener(interestRateEditTextWatcher);

        // set radio buttons and custom radio button
        RadioGroup loanDurationRadioGroup = findViewById(R.id.loanDurationRadioGroup);
        loanDurationRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton checkedRadioButton = group.findViewById(checkedId);
            boolean isChecked = checkedRadioButton.isChecked();
            if (isChecked) {
                if (checkedId == R.id.tenYearsRadioButton) {
                    loanDuration = 10;
                    loanDurationSeekBar.setEnabled(false);
                    calculate();
                } else if (checkedId == R.id.twentyYearsRadioButton) {
                    loanDuration = 20;
                    loanDurationSeekBar.setEnabled(false);
                    calculate();
                } else if (checkedId == R.id.thirtyYearsRadioButton) {
                    loanDuration = 30;
                    loanDurationSeekBar.setEnabled(false);
                    calculate();
                } else {
                    loanDurationSeekBar.setEnabled(true);
                }
            }
        });

        // set SeekBar's OnSeekBarChangeListener
        SeekBar loanDurationSeekBar =
                findViewById(R.id.loanDurationSeekBar);
        loanDurationSeekBar.setOnSeekBarChangeListener(seekBarListener);
    }

    // calculate and display monthly payment
    private void calculate() {
        // format inputs and display in TextViews
        loanDurationTextView.setText(Integer.toString(loanDuration));

        // calculate the monthly payment
        double loanAmount = purchasePrice - downPaymentAmount;
        double monthlyInterestRate = interestRate / 12;
        double monthsLeftOnMortgage = loanDuration * 12;

        double monthlyPayment = (loanAmount * (monthlyInterestRate * Math.pow((1 + monthlyInterestRate), monthsLeftOnMortgage))) / (Math.pow((1 + monthlyInterestRate), monthsLeftOnMortgage) - 1);

        // display monthly payment formatted as currency
        monthlyPaymentTextView.setText(currencyFormat.format(monthlyPayment));
    }

    // listener object for the SeekBar's progress changed events
    private final OnSeekBarChangeListener seekBarListener =
            new OnSeekBarChangeListener() {
                // update percent, then call calculate
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress,
                                              boolean fromUser) {
                    loanDuration = progress; // set loan duration based on progress
                    calculate(); // calculate and display tip and total
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) { }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) { }
            };

    // listener objects for the EditTexts' text-changed events
    private final TextWatcher purchasePriceEditTextWatcher = new TextWatcher() {
        // called when the user modifies the purchase price
        @Override
        public void onTextChanged(CharSequence s, int start,
                                  int before, int count) {

            try { // get purchase price and display currency formatted value
                purchasePrice = Double.parseDouble(s.toString()) / 100.0;
                purchasePriceTextView.setText(currencyFormat.format(purchasePrice));
            }
            catch (NumberFormatException e) { // if s is empty or non-numeric
                purchasePriceTextView.setText("Enter Purchase Price");
            }

            calculate(); // update the purchase price and monthly payment TextViews
        }

        @Override
        public void afterTextChanged(Editable s) { }

        @Override
        public void beforeTextChanged(
                CharSequence s, int start, int count, int after) { }
    };

    private final TextWatcher downPaymentAmountEditTextWatcher = new TextWatcher() {
        // called when the user modifies the down payment amount
        @Override
        public void onTextChanged(CharSequence s, int start,
                                  int before, int count) {

            try { // get down payment amount and display currency formatted value
                downPaymentAmount = Double.parseDouble(s.toString()) / 100.0;
                downPaymentAmountTextView.setText(currencyFormat.format(downPaymentAmount));
            }
            catch (NumberFormatException e) { // if s is empty or non-numeric
                downPaymentAmountTextView.setText("");
                downPaymentAmount = 0.0;
            }

            calculate(); // update the down payment amount and monthly payment TextViews
        }

        @Override
        public void afterTextChanged(Editable s) { }

        @Override
        public void beforeTextChanged(
                CharSequence s, int start, int count, int after) { }
    };

    private final TextWatcher interestRateEditTextWatcher = new TextWatcher() {
        // called when the user modifies the interest rate
        @Override
        public void onTextChanged(CharSequence s, int start,
                                  int before, int count) {

            try { // get interest rate and display currency formatted value
                interestRate = Double.parseDouble(s.toString()) / 100.0;
                interestRateTextView.setText(percentFormat.format(interestRate));
            }
            catch (NumberFormatException e) { // if s is empty or non-numeric
                interestRateTextView.setText("");
                interestRate = 0.0;
            }

            calculate(); // update the interest rate and monthly payment TextViews
        }

        @Override
        public void afterTextChanged(Editable s) { }

        @Override
        public void beforeTextChanged(
                CharSequence s, int start, int count, int after) { }
    };
}