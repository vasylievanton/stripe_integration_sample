package com.test.stripe_integration;

import android.content.Context;

import com.stripe.android.EphemeralKeyProvider;
import com.stripe.android.EphemeralKeyUpdateListener;
import com.test.stripe_integration.api.RetrofitClient;
import com.test.stripe_integration.api.StripeApi;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class KeyProvider implements EphemeralKeyProvider {

    private StripeApi backendApi;
    private CompositeDisposable compositeDisposable;


    public KeyProvider(Context context) {
        backendApi = RetrofitClient.getInstance().getRestClient(context).create(StripeApi.class);
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void createEphemeralKey(@NotNull String s, @NotNull final EphemeralKeyUpdateListener ephemeralKeyUpdateListener) {

        final Map<String, String> apiParamMap = new HashMap<>();
        apiParamMap.put("api_version", s);

        // Using RxJava2 for handling asynchronous responses
        compositeDisposable.add(backendApi.createEphemeralKey(apiParamMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            try {
                                String rawKey = response.string();
                                ephemeralKeyUpdateListener.onKeyUpdate(rawKey);
                            } catch (IOException ignored) {
                                ignored.printStackTrace();
                            }
                        }));
    }
}


