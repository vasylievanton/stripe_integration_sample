package com.test.stripe_integration.api;

public class NetworkError extends RuntimeException {

    public final static class ApiResponseError extends NetworkError{
        private String status;
        private int code;
        private String message;

        public ApiResponseError(String status, int code, String message) {
            this.status = status;
            this.code = code;
            this.message = message;
        }

        public String getStatus() {
            return status;
        }

        public int getCode() {
            return code;
        }

        @Override
        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
