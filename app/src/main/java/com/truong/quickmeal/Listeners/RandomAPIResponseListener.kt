package com.truong.quickmeal.Listeners;

import com.truong.quickmeal.Models.RandomRecipe;

import java.util.List;

public interface RandomAPIResponseListener {
    void didFetch(List<RandomRecipe> responses, String message);
    void didError(String message);
}
