package com.truong.quickmeal.Adapters

import android.content.Context
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
import com.truong.quickmeal.Adapters.SimilarViewHolder
import com.truong.quickmeal.Adapters.InstructionViewHolder
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.truong.quickmeal.Adapters.InstructionStepsAdapter
import com.truong.quickmeal.Adapters.StepsViewHolder
import com.truong.quickmeal.Adapters.InstructionStepItemsAdapter
import com.truong.quickmeal.Adapters.InstructionStepIngredientsAdapter
import com.truong.quickmeal.Adapters.InstructionStepIngredientsViewHolder
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
import android.view.View
import com.truong.quickmeal.RecipeDetailActivity
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.truong.quickmeal.RequestManager.CallRandomRecipe
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
import com.truong.quickmeal.Models.*

class InstructionStepsAdapter constructor(var context: Context, var list: List<Step?>?) :
    RecyclerView.Adapter<StepsViewHolder>() {
    public override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepsViewHolder {
        return StepsViewHolder(
            LayoutInflater.from(context).inflate(R.layout.list_instructions_steps, parent, false)
        )
    }

    public override fun onBindViewHolder(holder: StepsViewHolder, position: Int) {
        holder.textView_instruction_step_number.setText(
            list!!.get(position)?.number.toString() + ". "
        )
        holder.textView_instruction_step_title.setText(list!!.get(position)?.step)
        holder.recycler_instructions_equipments.setHasFixedSize(true)
        holder.recycler_instructions_equipments.setLayoutManager(
            StaggeredGridLayoutManager(
                1,
                LinearLayoutManager.HORIZONTAL
            )
        )
        val equipmentAdapter: InstructionStepItemsAdapter =
            InstructionStepItemsAdapter(context, list!!.get(position)?.equipment)
        holder.recycler_instructions_equipments.setAdapter(equipmentAdapter)
        holder.recycler_instructions_ingredients.setHasFixedSize(true)
        holder.recycler_instructions_ingredients.setLayoutManager(
            StaggeredGridLayoutManager(
                1,
                LinearLayoutManager.HORIZONTAL
            )
        )
        val ingredientsAdapter: InstructionStepIngredientsAdapter =
            InstructionStepIngredientsAdapter(
                context, list!!.get(position)?.ingredients
            )
        holder.recycler_instructions_ingredients.setAdapter(ingredientsAdapter)
    }

    public override fun getItemCount(): Int {
        return list!!.size
    }
}

class StepsViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var textView_instruction_step_title: TextView
    var textView_instruction_step_number: TextView
    var recycler_instructions_equipments: RecyclerView
    var recycler_instructions_ingredients: RecyclerView

    init {
        recycler_instructions_ingredients =
            itemView.findViewById(R.id.recycler_instructions_ingredients)
        recycler_instructions_equipments =
            itemView.findViewById(R.id.recycler_instructions_equipments)
        textView_instruction_step_title =
            itemView.findViewById(R.id.textView_instruction_step_title)
        textView_instruction_step_number =
            itemView.findViewById(R.id.textView_instruction_step_number)
    }
}