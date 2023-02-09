# Mortgage Calculator App

| Example 1 | Example 2 |
| --- | --- |
| <img src="https://user-images.githubusercontent.com/108101472/217965082-1def038b-5b09-432a-b840-4da6b275e9c0.png" width="200"> | <img src="https://user-images.githubusercontent.com/108101472/217965095-6cb4e8fa-e8e6-42be-8c93-4b6a9fa1de06.png" width="200"> |

This Android app is designed to help users calculate the monthly mortgage payment on a property they wish to purchase. The app takes into account the purchase price, down payment amount, interest rate, and loan duration to determine the monthly payment amount.

### Features

  - Calculates monthly mortgage payment based on user inputs
  - Allows users to input purchase price, down payment amount, interest rate, and loan duration
  - Includes options for loan duration in 10, 20, and 30 years, as well as a custom option that allows users to input any desired loan duration
  - Displays results in a clear and easy-to-read format

### Technical Details

This app was developed in Java using the Android operating system. It makes use of several libraries, including:

  - Android OS framework
  - java.text.NumberFormat for formatting currency and percentage values
  - android.widget for user interface elements such as EditText, SeekBar, and TextView
  - android.text for handling input from EditText fields

The app uses a custom TextWatcher class to listen for changes in the input fields and update the results accordingly. The loan duration can be adjusted either by selecting a predefined option from the radio buttons or by using the SeekBar widget. The results are displayed in a TextView and updated in real-time as the user changes the inputs.
