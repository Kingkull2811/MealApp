package com.truong.quickmeal

import android.content.Context
import com.truong.quickmeal.Models.Ingredient
import com.truong.quickmeal.Models.Equipment
import com.truong.quickmeal.Models.Us
import com.truong.quickmeal.Models.Metric
import com.truong.quickmeal.Models.Temperature
import com.truong.quickmeal.Models.ProductMatch
import com.truong.quickmeal.Models.ExtendedIngredient
import com.truong.quickmeal.Models.AnalyzedInstruction
import com.truong.quickmeal.Models.Measures
import com.truong.quickmeal.Models.RandomRecipe
import com.truong.quickmeal.Models.WinePairing
import com.truong.quickmeal.Listeners.CustomOnClickListener
import androidx.recyclerview.widget.RecyclerView
import com.truong.quickmeal.Adapters.RandomMealViewHolder
import android.view.ViewGroup
import android.view.LayoutInflater
import com.truong.quickmeal.R
import com.squareup.picasso.Picasso
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.truong.quickmeal.Adapters.IngredientsViewHolder
import com.truong.quickmeal.Models.SimilarRecipeResponse
import com.truong.quickmeal.Adapters.SimilarViewHolder
import com.truong.quickmeal.Models.InstructionsResponse
import com.truong.quickmeal.Adapters.InstructionViewHolder
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.truong.quickmeal.Adapters.InstructionStepsAdapter
import com.truong.quickmeal.Adapters.StepsViewHolder
import com.truong.quickmeal.Adapters.InstructionStepItemsAdapter
import com.truong.quickmeal.Adapters.InstructionStepIngredientsAdapter
import com.truong.quickmeal.Adapters.InstructionStepIngredientsViewHolder
import com.truong.quickmeal.Models.RecipeDetailsResponse
import androidx.appcompat.app.AppCompatActivity
import com.truong.quickmeal.RequestManager
import com.truong.quickmeal.Adapters.RandomMealAdapter
import android.widget.Spinner
import android.widget.ProgressBar
import android.os.Bundle
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.Wave
import android.widget.ArrayAdapter
import android.widget.Toast
import com.truong.quickmeal.Listeners.RandomAPIResponseListener
import android.widget.AdapterView
import android.content.Intent
import com.truong.quickmeal.RecipeDetailActivity
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.truong.quickmeal.RequestManager.CallRandomRecipe
import com.truong.quickmeal.Models.RandomRecipeResponse
import com.truong.quickmeal.Listeners.RecipeDetailsResponseListener
import com.truong.quickmeal.RequestManager.CallRecipeDetails
import com.truong.quickmeal.Listeners.SimilarRecipeListener
import com.truong.quickmeal.RequestManager.CallSimilarRecipes
import com.truong.quickmeal.Listeners.InstructionsListener
import com.truong.quickmeal.RequestManager.CallInstructions
import retrofit2.http.GET
import android.widget.ScrollView
import com.truong.quickmeal.Adapters.IngredientsAdapter
import com.truong.quickmeal.Adapters.SimilarListAdapter
import com.truong.quickmeal.Adapters.InstructionsAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Path
import retrofit2.http.Query

class RequestManager constructor(var context: Context) {
    var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.spoonacular.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun GetRandomRecipes(listener: RandomAPIResponseListener, tags: List<String>?) {
        val callRandomRecipe: CallRandomRecipe = retrofit.create(
            CallRandomRecipe::class.java
        )
        val call: Call<RandomRecipeResponse> = callRandomRecipe.callRandomRecipe(
            context.getString(R.string.api_key), "80", tags
        )
        call.enqueue(object : Callback<RandomRecipeResponse> {
            public override fun onResponse(
                call: Call<RandomRecipeResponse>,
                response: Response<RandomRecipeResponse>
            ) {
                if (!response.isSuccessful()) {
                    listener.didError(response.message())
                    return
                }
                listener.didFetch(response.body()?.recipes, response.message())
            }

            public override fun onFailure(call: Call<RandomRecipeResponse>, t: Throwable) {
                listener.didError(t.message)
            }
        })
    }

    fun GetRecipeDetails(listener: RecipeDetailsResponseListener, id: Int) {
        val callRecipeDetails: CallRecipeDetails = retrofit.create(
            CallRecipeDetails::class.java
        )
        val call: Call<RecipeDetailsResponse> =
            callRecipeDetails.callRecipeDetails(id, context.getString(R.string.api_key))
        call.enqueue(object : Callback<RecipeDetailsResponse?> {
            public override fun onResponse(
                call: Call<RecipeDetailsResponse?>,
                response: Response<RecipeDetailsResponse?>
            ) {
                if (!response.isSuccessful()) {
                    listener.didError(response.message())
                    return
                }
                listener.didFetch(response.body(), response.message())
            }

            public override fun onFailure(call: Call<RecipeDetailsResponse?>, t: Throwable) {
                listener.didError(t.message)
            }
        })
    }

    fun GetSimilarRecipe(listener: SimilarRecipeListener, id: Int) {
        val callSimilarRecipes: CallSimilarRecipes = retrofit.create(
            CallSimilarRecipes::class.java
        )
        val call: Call<List<SimilarRecipeResponse>> =
            callSimilarRecipes.callSimilarRecipe(id, context.getString(R.string.api_key))
        call.enqueue(object : Callback<List<SimilarRecipeResponse>> {
            public override fun onResponse(
                call: Call<List<SimilarRecipeResponse>>,
                response: Response<List<SimilarRecipeResponse>>
            ) {
                if (!response.isSuccessful()) {
                    listener.didError(response.message())
                    return
                }
                listener.didFetch((response.body())!!, response.message())
            }

            public override fun onFailure(call: Call<List<SimilarRecipeResponse>>, t: Throwable) {
                listener.didError(t.message)
            }
        })
    }

    fun GetInstructions(listener: InstructionsListener, id: Int) {
        val callInstructions: CallInstructions = retrofit.create(
            CallInstructions::class.java
        )
        val call: Call<List<InstructionsResponse>> =
            callInstructions.callInstructions(id, context.getString(R.string.api_key))
        call.enqueue(object : Callback<List<InstructionsResponse>> {
            public override fun onResponse(
                call: Call<List<InstructionsResponse>>,
                response: Response<List<InstructionsResponse>>
            ) {
                if (!response.isSuccessful()) {
                    listener.didError(response.message())
                    return
                }
                listener.didFetch((response.body())!!, response.message())
            }

            public override fun onFailure(call: Call<List<InstructionsResponse>>, t: Throwable) {
                listener.didError(t.message)
            }
        })
    }

     open interface CallRandomRecipe {
        @GET("recipes/random")
        fun callRandomRecipe(
            @Query("apiKey") apiKey: String?,
            @Query("number") number: String?,
            @Query("tags") tags: List<String>?
        ): Call<RandomRecipeResponse>
    }

     open interface CallRecipeDetails {
        @GET("recipes/{id}/information")
        fun callRecipeDetails(
            @Path("id") id: Int,
            @Query("apiKey") apiKey: String?
        ): Call<RecipeDetailsResponse>
    }

     open interface CallSimilarRecipes {
        @GET("recipes/{id}/similar")
        fun callSimilarRecipe(
            @Path("id") id: Int,
            @Query("apiKey") apiKey: String?
        ): Call<List<SimilarRecipeResponse>>
    }

     open interface CallInstructions {
        @GET("recipes/{id}/analyzedInstructions")
        fun callInstructions(
            @Path("id") id: Int,
            @Query("apiKey") apiKey: String?
        ): Call<List<InstructionsResponse>>
    }
}