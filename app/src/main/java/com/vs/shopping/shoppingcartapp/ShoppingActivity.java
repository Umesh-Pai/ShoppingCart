package com.vs.shopping.shoppingcartapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ShoppingActivity extends AppCompatActivity {

    Button btnScan;
    TextView tv_qr_readTxt;
    TextView textTotal;
    TextView textSeperator;
    int total = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);

        btnScan = (Button)findViewById(R.id.btnScan);
        tv_qr_readTxt = (TextView) findViewById(R.id.tv_qr_readTxt);

        textTotal = (TextView) findViewById(R.id.textTotal);
        textSeperator = (TextView) findViewById(R.id.textSeperator);
		
		btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                IntentIntegrator integrator = new IntentIntegrator(ShoppingActivity.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });

    }
	
	 @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        String productDetails = null;
        String endpoint = "https://umeshpai16-eval-test.apigee.net/shopping/inventory/";
        String method = "GET";
        String scannedProducts = null;
        String seperator = null;

        if(result != null) {
            if(result.getContents() == null) {
                Log.e("Scan*******", "Cancelled scan");

            } else {
                Log.e("Scan", "Scanned");
                //endpoint = endpoint + result.getContents();
                //productDetails = new APIClient().invokeAPI(endpoint, method);

                scannedProducts = tv_qr_readTxt.getText().toString();
                Log.i("scannedProducts::", scannedProducts);

                productDetails = scannedProducts + "\n" + "Company \t Pura | Product \t Milk | Weight \t 2l | Price \t 4.00";
                Log.i("productDetails::", productDetails);
                tv_qr_readTxt.setText(productDetails);

                seperator = "\n" + "_________________________________________________";

                textSeperator.setText(seperator);

                total = total + 4;
                textTotal.setText("\n Total \t \t \t \t \t \t \t " + total + ".00" +  "$");
                Log.i("test::", total + "");

                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
