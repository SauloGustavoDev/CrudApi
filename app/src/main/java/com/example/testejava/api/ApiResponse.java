package com.example.testejava.api;

public abstract class ApiResponse<T> {
    private T data;
    private String message;

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public ApiResponse(T data, String message) {
        this.data = data;
        this.message = message;
    }

    public static class Success<T> extends ApiResponse<T> {

        public Success(T data) {
            super(data, null);
        }
    }

    public static class Failure<T> extends ApiResponse<T> {

        public Failure(String message) {
            super(null, message);
        }
    }
}
