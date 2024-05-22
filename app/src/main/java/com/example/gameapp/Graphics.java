package com.example.gameapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.DecimalFormat;
import java.util.ArrayList;
import android.widget.LinearLayout;
import java.util.List;

public class Graphics extends AppCompatActivity {
    private EditText gameNameEditText;
    private EditText gamePriceEditText;
    private EditText gameQuantityEditText;
    private EditText taxRateEditText;
    private EditText discountEditText;
    private TextView totalPriceTextView;
    private LinearLayout gameListLayout;
    private List<Game> gameList;
    private DecimalFormat decimalFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphics);

        gameNameEditText = findViewById(R.id.gameNameEditText);
        gamePriceEditText = findViewById(R.id.gamePriceEditText);
        gameQuantityEditText = findViewById(R.id.gameQuantityEditText);
        taxRateEditText = findViewById(R.id.taxRateEditText);
        discountEditText = findViewById(R.id.discountEditText);
        totalPriceTextView = findViewById(R.id.totalPriceTextView);
        gameListLayout = findViewById(R.id.gameListLayout); // Initialize gameListLayout

        gameList = new ArrayList<>();
        decimalFormat = new DecimalFormat("#.##");

        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addGame();
            }
        });

        Button calculateButton = findViewById(R.id.calculateButton);
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateTotalPrice();
            }
        });

        Button resetButton = findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetCalculator();
            }
        });
    }

    private void addGame() {
        String name = gameNameEditText.getText().toString();
        double price = Double.parseDouble(gamePriceEditText.getText().toString());
        int quantity = Integer.parseInt(gameQuantityEditText.getText().toString());
        Game game = new Game(name, price, quantity);
        gameList.add(game);

        // Add game to the layout
        TextView gameTextView = new TextView(Graphics.this);
        gameTextView.setText("Game: " + game.getName() + ", Price: ₹" + game.getPrice());
        gameListLayout.addView(gameTextView);
    }

    private void calculateTotalPrice() {
        double totalPrice = 0;

        for (Game game : gameList) {
            totalPrice += game.getPrice() * game.getQuantity();
        }

        double taxRate = Double.parseDouble(taxRateEditText.getText().toString());
        double discount = Double.parseDouble(discountEditText.getText().toString());

        totalPrice *= (1 + taxRate / 100);
        totalPrice *= (1 - discount / 100);

        totalPriceTextView.setText("Total Price: " + decimalFormat.format(totalPrice));
    }

    private void resetCalculator() {
        gameList.clear();
        gameNameEditText.setText("");
        gamePriceEditText.setText("");
        gameQuantityEditText.setText("");
        taxRateEditText.setText("");
        discountEditText.setText("");
        totalPriceTextView.setText("Total Price: ₹0.00");

        // Clear added games from the layout
        gameListLayout.removeAllViews();
    }
}
