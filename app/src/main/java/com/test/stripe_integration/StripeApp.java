package com.test.stripe_integration;

import android.app.Application;

import com.stripe.android.CustomerSession;
import com.stripe.android.PaymentConfiguration;

public class StripeApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PaymentConfiguration.init(
                getApplicationContext(),
                ""
        );

        CustomerSession.initCustomerSession(
                getApplicationContext(),
                new KeyProvider(getApplicationContext())
        );

    }
}
