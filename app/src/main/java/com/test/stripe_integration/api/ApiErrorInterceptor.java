package com.test.stripe_integration.api;

import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

public class ApiErrorInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        int responseCode = response.code();
        ResponseBody body = response.body();
        if (body != null && body.contentType() != null && body.contentType().subtype() != null && body.contentType().subtype().toLowerCase().equals("json")) {
            String errorMessage = "";
            int errorCode = 200; // Assume default OK
            try {
                BufferedSource source = body.source();
                source.request(Long.MAX_VALUE); // Buffer the entire body.
                Buffer buffer = source.buffer();
                Charset charset = body.contentType().charset(Charset.forName("UTF-8"));
                // Clone the existing buffer is they can only read once so we still want to pass the original one to the chain.
                String json = buffer.clone().readString(charset);
                JsonElement obj = new JsonParser().parse(json);
                // Capture error code an message.
                if (obj instanceof JsonObject && ((JsonObject) obj).has("code")) {
                    errorCode = ((JsonObject) obj).get("code").getAsInt();
                }
                if (obj instanceof JsonObject && ((JsonObject) obj).has("message")) {
                    errorMessage = ((JsonObject) obj).get("message").getAsString();
                }
            } catch (Exception e) {
                Log.e(this.getClass().getSimpleName(), "Error: " + e.getMessage());
            }

            if (responseCode >= 400) {
                throw new NetworkError.ApiResponseError("error", errorCode, errorMessage);
            } else {
                return response;
            }
        }
        return response;
    }
}
