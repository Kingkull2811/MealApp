package com.truong.quickmeal.Adapters

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
import android.os.Bundle
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.Wave
import com.truong.quickmeal.Listeners.RandomAPIResponseListener
import android.content.Intent
import android.view.View
import android.widget.*
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
import com.truong.quickmeal.Adapters.IngredientsAdapter
import com.truong.quickmeal.Adapters.SimilarListAdapter
import com.truong.quickmeal.Adapters.InstructionsAdapter

class IngredientsAdapter constructor(var context: Context, var list: List<ExtendedIngredient?>?) :
    RecyclerView.Adapter<IngredientsViewHolder>() {
    public override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): IngredientsViewHolder {
        return IngredientsViewHolder(
            LayoutInflater.from(context).inflate(R.layout.list_ingredients, parent, false)
        )
    }

    public override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {
        holder.textView_ingred_name.setText(list!!.get(position)!!.name)
        holder.textView_ingred_name.setSelected(true)
        holder.textView_ingred_quantity.setText(list!!.get(position)!!.original)
        holder.textView_ingred_quantity.setSelected(true)
        Picasso.get()
            .load("https://spoonacular.com/cdn/ingredients_100x100/" + list!!.get(position)!!.image)
            .into(holder.imageView_ingred)
    }

    public override fun getItemCount(): Int {
        return list!!.size
    }
}

class IngredientsViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var textView_ingred_quantity: TextView
    var textView_ingred_name: TextView
    var imageView_ingred: ImageView

    init {
        textView_ingred_quantity = itemView.findViewById(R.id.textView_ingred_quantity)
        textView_ingred_name = itemView.findViewById(R.id.textView_ingred_name)
        imageView_ingred = itemView.findViewById(R.id.imageView_ingred)
    }
}