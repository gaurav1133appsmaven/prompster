package com.scriptively.app.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.SkuDetails;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.google.gson.Gson;
import com.scriptively.app.Api.ApiClient;
import com.scriptively.app.Api.ApiInterface;
import com.scriptively.app.Pojo.InAppProduct;
import com.scriptively.app.R;
import com.scriptively.app.Utils.Shared_PrefrencePrompster;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class In_app extends AppCompatActivity implements BillingProcessor.IBillingHandler {
TextView tv_getstarted;
    BillingProcessor bp;
    LinearLayout lowSub,mediumSub,highSub;
    ImageView lowImage,midImage,highImage,ivCross;
    TextView lowPrice,midPrice,highPrice,midDisc,highDisc,showMessage,textDesc;

    String product;

    ArrayList<String> productId = new ArrayList<String>();

    List<SkuDetails> sku;

    Shared_PrefrencePrompster shared_prefrencePrompster;

    String userid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_app);
        tv_getstarted=findViewById(R.id.tv_getstarted);
        lowSub = findViewById(R.id.lowSub);
        mediumSub = findViewById(R.id.mediumSub);
        highSub = findViewById(R.id.highSub);

        lowPrice = findViewById((R.id.low_price));
        midPrice = findViewById((R.id.mid_price));
        highPrice = findViewById((R.id.high_price));

        midDisc = findViewById(R.id.tv_mid_discount);
        highDisc = findViewById(R.id.high_discount);

        lowImage = findViewById(R.id.lowImage);
        midImage = findViewById(R.id.midImage);
        highImage = findViewById(R.id.highImage);

        showMessage = findViewById(R.id.showMessage);

        textDesc = findViewById(R.id.text_desc);

        ivCross = findViewById(R.id.cross);

        shared_prefrencePrompster = Shared_PrefrencePrompster.getInstance(getApplicationContext());

        userid = shared_prefrencePrompster.getUserid();

        getProducts();
        bp = new BillingProcessor(this, getResources().getString(R.string.google_license_key), this);
        bp.initialize();

        lowSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                productId = list.get(2).getProductId();

                product = sku.get(1).productId;

                showMessage.setText("7 day free trial,then "+ sku.get(1).priceText+" per month");
                textDesc.setText("Details\n\nSubscription automatically renew unless auto-renew turned off at least 24 hours before the end of the current period. You can turn off auto renew subscription from with in Android settings.\n" +
                        "\n\nPrice of Subscription: \nTry free for 7 days then pay "+sku.get(1).priceText+"/month. Payment will be changed to your Android account at confirmation of purchase. Any unused portion of a free trail will be forfeited when purchasing a subscription before it ends.");

                lowSub.setBackground(getResources().getDrawable(R.drawable.subscription_button));
                mediumSub.setBackground(null);
                highSub.setBackground(null);

                lowImage.setImageResource(R.drawable.radio_active);
                midImage.setImageResource(R.drawable.radio_inactive);
                highImage.setImageResource(R.drawable.radio_inactive);

            }
        });
        mediumSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                product = sku.get(0).productId;

                textDesc.setText("Details\n\nSubscription automatically renew unless auto-renew turned off at least 24 hours before the end of the current period. You can turn off auto renew subscription from with in Android settings.\n" +
                        "\n\nPrice of Subscription: \nTry free for 7 days then pay "+sku.get(0).priceText+"/six month. Payment will be changed to your Android account at confirmation of purchase. Any unused portion of a free trail will be forfeited when purchasing a subscription before it ends.");


                showMessage.setText("7 day free trial,then "+ sku.get(0).priceText+" for six month");
                lowSub.setBackground(null);
                mediumSub.setBackground(getResources().getDrawable(R.drawable.subscription_button));
                highSub.setBackground(null);
                lowImage.setImageResource(R.drawable.radio_inactive);
                midImage.setImageResource(R.drawable.radio_active);
                highImage.setImageResource(R.drawable.radio_inactive);
            }
        });

        highSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                product = sku.get(2).productId;

                textDesc.setText("Details\n\nSubscription automatically renew unless auto-renew turned off at least 24 hours before the end of the current period. You can turn off auto renew subscription from with in Android settings.\n" +
                        "\n\nPrice of Subscription: \nTry free for 7 days then pay "+sku.get(2).priceText+"/year. Payment will be changed to your Android account at confirmation of purchase. Any unused portion of a free trail will be forfeited when purchasing a subscription before it ends.");


                showMessage.setText("7 day free trial,then "+ sku.get(2).priceText+" per year");
                lowSub.setBackground(null);
                mediumSub.setBackground(null);
                highSub.setBackground(getResources().getDrawable(R.drawable.subscription_button));

                lowImage.setImageResource(R.drawable.radio_inactive);
                midImage.setImageResource(R.drawable.radio_inactive);
                highImage.setImageResource(R.drawable.radio_active);
            }
        });

        ivCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tv_getstarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bp.subscribe(In_app.this,product);

//                Intent intent=new Intent(In_app.this,Script_Toolbar.class);
//                startActivity(intent);
//                finish();
            }
        });
    }

    @Override
    public void onProductPurchased(String productId, TransactionDetails details) {

        Log.e("ProductId",productId);
        Log.e("ProductId",details.purchaseInfo.responseData);

        if (!details.purchaseInfo.responseData.isEmpty()){
            shared_prefrencePrompster.setSub(true);
            saveSub();
        }
        else {
            shared_prefrencePrompster.setSub(false);
        }

    }

    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int errorCode, Throwable error) {

        try {
            Log.e("Error","abc"+error.getMessage());
        }catch (Exception e)
        {

        }

    }

    @Override
    public void onBillingInitialized() {
        sku = bp.getSubscriptionListingDetails(productId);
        String price = sku.get(1).priceText;

        Log.e("ProductDetail",price+" "+sku.get(2).priceValue);

        lowPrice.setText(sku.get(1).priceValue+" "+sku.get(1).currency);
        midPrice.setText(sku.get(0).priceValue+" "+sku.get(0).currency);
        highPrice.setText(sku.get(2).priceValue+" "+sku.get(2).currency);

        showMessage.setText("7 day free trial,then "+ sku.get(1).priceText+" per month");

        textDesc.setText("Details\n\nSubscription automatically renew unless auto-renew turned off at least 24 hours before the end of the current period. You can turn off auto renew subscription from with in Android settings.\n" +
                "\n\nPrice of Subscription: \nTry free for 7 days then pay "+sku.get(1).priceText+"/month. Payment will be changed to your Android account at confirmation of purchase. Any unused portion of a free trail will be forfeited when purchasing a subscription before it ends.");


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!bp.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);

            Log.e("PurchageData",data.getData().toString());

        }
    }

    private void getProducts()
    {


        productId.add("sciptively.six.month.subscription");
        productId.add("scriptively.monthly.subscription");
        productId.add("scriptively.yearly.subscription");

//        {,"sciptively.six.month.subscription",""};
//        PurchasesUpdatedListener purchasesUpdatedListener = new PurchasesUpdatedListener() {
//            @Override
//            public void onPurchasesUpdated(BillingResult billingResult, List<Purchase> purchases) {
//                // To be implemented in a later section.
//            }
//        };
//
//        BillingClient billingClient = BillingClient.newBuilder(this)
//                .setListener(purchasesUpdatedListener)
//                .enablePendingPurchases()
//                .build();
//
//        billingClient.startConnection(new BillingClientStateListener() {
//            @Override
//            public void onBillingSetupFinished(BillingResult billingResult) {
//                if (billingResult.getResponseCode() ==  BillingClient.BillingResponseCode.OK) {
//                    // The BillingClient is ready. You can query purchases here.
//                }
//            }
//            @Override
//            public void onBillingServiceDisconnected() {
//                // Try to restart the connection on the next request to
//                // Google Play by calling the startConnection() method.
//            }
//        });
//
//        List<String> skuList = new ArrayList<> ();
//        skuList.add("premium_upgrade");
//        skuList.add("gas");
//        SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
//        params.setSkusList(skuList).setType(BillingClient.SkuType.SUBS);
//        billingClient.querySkuDetailsAsync(params.build(),
//                new SkuDetailsResponseListener() {
//                    @Override
//                    public void onSkuDetailsResponse(BillingResult billingResult,
//                                                     List<SkuDetails> skuDetailsList) {
//
//                        Log.e("ProductList",skuDetailsList.toString());
//
//                    }
//                });

//        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
//        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
//
//        JSONObject mySelection = new JSONObject();
//        try {
//            mySelection.put("device_type","android");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        RequestBody body = RequestBody.create(mediaType, String.valueOf(mySelection));
//        retrofit2.Call<InAppProduct> call = apiService.getProducts(body);
//        call.enqueue(new retrofit2.Callback<InAppProduct>() {
//
//            @Override
//            public void onResponse(Call<InAppProduct> call, Response<InAppProduct> response) {
//
//                Log.e("Request",call.request().body().toString());
//                Log.e("Response",new Gson().toJson(response.body()));
//
//
//
//                list.addAll(response.body().getData());
//
//                lowPrice.setText(list.get(2).getPrice()+" "+list.get(0).getCurrency());
//                midPrice.setText(list.get(0).getPrice()+" "+list.get(1).getCurrency());
//                highPrice.setText(list.get(1).getPrice()+" "+list.get(2).getCurrency());
//
//            }
//
//            @Override
//            public void onFailure(Call<InAppProduct> call, Throwable t) {
//
//            }
//        });
    }

    @Override
    protected void onDestroy() {
        if (bp != null) {
            bp.release();
        }
        super.onDestroy();
    }

    private void saveSub()
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        retrofit2.Call<InAppProduct> call = apiService.saveProduct(userid, "android",product);

        call.enqueue(new Callback<InAppProduct>() {
            @Override
            public void onResponse(Call<InAppProduct> call, Response<InAppProduct> response) {

                if (response.isSuccessful())
                {
                    Log.e("Response",new Gson().toJson(response.body()));
                }

            }

            @Override
            public void onFailure(Call<InAppProduct> call, Throwable t) {

            }
        });
    }

}
