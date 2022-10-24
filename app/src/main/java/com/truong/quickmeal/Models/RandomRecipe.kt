package com.truong.quickmeal.Models

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

class RandomRecipe {
    var isVegetarian = false
    var isVegan = false
    var isGlutenFree = false
    var isDairyFree = false
    var isVeryHealthy = false
    var isCheap = false
    var isVeryPopular = false
    var isSustainable = false
    var weightWatcherSmartPoints = 0
    var gaps: String? = null
    var isLowFodmap = false
    var aggregateLikes = 0
    var spoonacularScore = 0
    var healthScore = 0
    var creditsText: String? = null
    var license: String? = null
    var sourceName: String? = null
    var pricePerServing = 0.0
    var extendedIngredients: List<ExtendedIngredient>? = null
    var id = 0
    var title: String? = null
    var readyInMinutes = 0
    var servings = 0
    var sourceUrl: String? = null
    var image: String? = null
    var imageType: String? = null
    var summary: String? = null
    var cuisines: List<String>? = null
    var dishTypes: List<String>? = null
    var diets: List<String>? = null
    var occasions: List<String>? = null
    var instructions: String? = null
    var analyzedInstructions: List<AnalyzedInstruction>? = null
    var originalId: Any? = null
    var spoonacularSourceUrl: String? = null
}