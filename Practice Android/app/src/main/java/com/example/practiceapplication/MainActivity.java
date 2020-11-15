package com.example.practiceapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    int numberOfCoffees = 1;

    public void decrement (View view){
        if (numberOfCoffees == 0){
            Toast.makeText(this , "you can't have less then 1 cup of coffees", Toast.LENGTH_SHORT);
            /*Toast toast = Toast.makeText(getApplicationContext(),"You can't have less than one cup of coffee" , Toast.LENGTH_SHORT);
            toast.setMargin(20,50);
            toast.show();*/
            return ;
        }
        numberOfCoffees -= 1;
        displayNoOfCoffees(numberOfCoffees);
    }

    public void increment(View view) {
        if (numberOfCoffees == 100){
            Toast.makeText(this,"you can't have more then 100 cup of coffees",Toast.LENGTH_SHORT);
            /*Toast toast = Toast.makeText(getApplicationContext(),"You can't have more than 100 cup of coffee" , Toast.LENGTH_SHORT);
            toast.setMargin(20,50);
            toast.show();*/
            return ;
        }
        numberOfCoffees += 1;
        displayNoOfCoffees(numberOfCoffees);
    }

    public void submitOrder(View view) {

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();
        EditText nameField = (EditText)findViewById(R.id.name_field);
        String name = nameField.getText().toString();
        int price = calculatePrice(hasWhippedCream , hasChocolate);
        String priceMessage = createOrderSummery(price,hasWhippedCream,hasChocolate,name);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT,"just java order for coffee" + name);
        intent.putExtra(Intent.EXTRA_TEXT,priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
//        displayPriceMessage(priceMessage);
    }

    private void displayNoOfCoffees(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /*private void displayPrice(int number){
        TextView priceTextView = (TextView)findViewById(R.id.price_text_display);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }*/


//    private void displayPriceMessage(String message){
//        TextView priceTextView = (TextView)findViewById(R.id.price_text_display);
//        priceTextView.setText(message);
//    }

    private int calculatePrice(boolean addWhippedCream , boolean addChocolate){
        int basePrice = 5;
        if (addChocolate){
            basePrice += 2;
        }
        if(addWhippedCream){
            basePrice += 1;
        }
        return numberOfCoffees * basePrice;

    }
    private String createOrderSummery(int price , boolean hasWhippedCream , boolean hasChocolate, String name){
        String priceMessage = getString(R.string.order_summary_name,name);
        priceMessage += "\nAdd whipped cream? " + hasWhippedCream;
        priceMessage += "\nAdd Chocolate? " + hasChocolate;
        priceMessage += "\nQuantity : " + numberOfCoffees;
        priceMessage += "\nTotal : $" + price ;
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;
    }
}