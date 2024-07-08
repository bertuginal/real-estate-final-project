package com.example.patika.utils.results;

public class SuccessResult extends Result{

    public SuccessResult() {
        super(true);
    }

    public SuccessResult(String message) {
        super(true,message);

    }
}
