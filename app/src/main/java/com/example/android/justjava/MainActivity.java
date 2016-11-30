package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends ActionBarActivity {
    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();
        EditText clientName = (EditText) findViewById(R.id.name_edit_text);
        String typedName = clientName.getText().toString();
        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, typedName);

        //displayMessage(priceMessage);

        //SEND A EMAIL ORDER
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        //  intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name) + " " + getString(R.string.order_for) + " " + typedName);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }


        //Log.v("MainActivity", "Name ?: "+ typedName);
    }

    /**
     * Calculates the price of the order.
     *
     * @param hasWCream Whipped Cream
     * @param hasChoco  Chocolate
     * @return total price
     */
    private int calculatePrice(Boolean hasWCream, Boolean hasChoco) {

        int coffePrice = 5;

        if (hasWCream) {
            coffePrice += 1;
        }

        if (hasChoco) {
            coffePrice += 2;
        }

        return quantity * coffePrice;
    }

    /**
     * Create summary of the order
     *
     * @param price of order
     * @return text summary
     */
    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, String addName) {

        String messageOrder = getString(R.string.name) + ": " + addName;
        messageOrder += "\n"+ getString(R.string.whipped_cream)+" ? " + addWhippedCream;
        //messageOrder += "\nHas Whipped Cream? " + addWhippedCream;
        messageOrder += "\n" + getString(R.string.chocolate) + " ? " + addChocolate;
        //messageOrder += "\nHas Chocolate? " + addChocolate;
        messageOrder += "\n" + getString(R.string.quantity) + ": " + quantity;
        //messageOrder += "\nQuantity: " + quantity;
        messageOrder += "\n" + getString(R.string.total)+ " " + price;
        //messageOrder += "\nTotal: $ " + price;
        messageOrder += "\n" + getString(R.string.thanks);
        //messageOrder += "\nThank You :)";
        return messageOrder;
    }

    /**
     * This method displays the given text on the screen.
     */

//    private String displayMessage(String message) {
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(message);
//        return message;
//    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    public void increment(View view) {
        if (quantity == 100) {
            Toast.makeText(this, getString(R.string.messageIncrement), Toast.LENGTH_SHORT).show();
            return;
        }
        quantity++;
        displayQuantity(quantity);
    }

    public void decrement(View view) {

        if (quantity == 1) {
            Toast.makeText(this, getString(R.string.messageDecrement), Toast.LENGTH_SHORT).show();
            return;
        }
        quantity--;
        displayQuantity(quantity);
    }


}