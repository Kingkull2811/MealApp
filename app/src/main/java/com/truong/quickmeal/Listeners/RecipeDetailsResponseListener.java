package com.truong.quickmeal.Listeners;

import com.truong.quickmeal.Models.RecipeDetailsResponse;

public interface RecipeDetailsResponseListener {
    void didFetch(RecipeDetailsResponse response, String message);
    void didError(String message);
}
