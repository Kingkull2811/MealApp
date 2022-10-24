package com.truong.quickmeal

import android.app.Dialog
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
import android.view.Window
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

class RecipeDetailActivity constructor() : AppCompatActivity() {
    var textView_meal_name: TextView? = null
    var textView_meal_source: TextView? = null
    var textView_meal_servings: TextView? = null
    var textView_meal_ready: TextView? = null
    var textView_meal_price: TextView? = null
    var textView_meal_summary: TextView? = null
    var imageView_meal_image: ImageView? = null
    var recycler_meal_ingredients: RecyclerView? = null
    var recycler_meal_similar: RecyclerView? = null
    var recycler_meal_instructions: RecyclerView? = null
    var button_nutrition: Button? = null
    var scrollView: ScrollView? = null
    var manager: RequestManager? = null
    var adapter: IngredientsAdapter? = null
    var similarListAdapter: SimilarListAdapter? = null
    var instructionsAdapter: InstructionsAdapter? = null

    //    ProgressDialog dialog;
    var progressBar: ProgressBar? = null
    var ingredientList: List<ExtendedIngredient>? = null
    var id: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)
        findViews()

//        dialog = new ProgressDialog(this);
//        dialog.setTitle("Please wait...");
        progressBar = findViewById<View>(R.id.loader) as ProgressBar?
        val doubleBounce: Sprite = Wave()
        progressBar!!.setIndeterminateDrawable(doubleBounce)
        id = Integer.valueOf(getIntent().getStringExtra("id"))
        manager = RequestManager(this)
        manager!!.GetRecipeDetails(listener, id)
        //        dialog.show();
        manager!!.GetSimilarRecipe(similarRecipeListener, id)
        manager!!.GetInstructions(instructionsListener, id)
        button_nutrition!!.setOnClickListener(object : View.OnClickListener {
            public override fun onClick(view: View) {
                //https://api.spoonacular.com/recipes/1082038/nutritionWidget.png
//                https://api.spoonacular.com/recipes/641166/nutritionLabel.png
                val nutritionDialog: Dialog =
                    Dialog(this@RecipeDetailActivity, android.R.style.Theme_Light)
                nutritionDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                nutritionDialog.getWindow()!!
                    .setBackgroundDrawableResource(android.R.color.transparent)
                nutritionDialog.setContentView(R.layout.nutrition_dialog)
                val imageView: ImageView =
                    nutritionDialog.findViewById<View>(R.id.imageView_nutrition) as ImageView
                Picasso.get().load(
                    "https://api.spoonacular.com/recipes/" + id + "/nutritionLabel.png?apiKey=" + getString(
                        R.string.api_key
                    )
                ).into(imageView)
                nutritionDialog.show()
            }
        })
    }

    private fun findViews() {
        textView_meal_name = findViewById(R.id.textView_meal_name)
        textView_meal_source = findViewById(R.id.textView_meal_source)
        imageView_meal_image = findViewById(R.id.imageView_meal_image)
        recycler_meal_ingredients = findViewById(R.id.recycler_meal_ingredients)
        textView_meal_servings = findViewById(R.id.textView_meal_servings)
        textView_meal_ready = findViewById(R.id.textView_meal_ready)
        textView_meal_price = findViewById(R.id.textView_meal_price)
        textView_meal_summary = findViewById(R.id.textView_meal_summary)
        recycler_meal_similar = findViewById(R.id.recycler_meal_similar)
        recycler_meal_instructions = findViewById(R.id.recycler_meal_instructions)
        button_nutrition = findViewById(R.id.button_nutrition)
        scrollView = findViewById(R.id.scrollView)
    }

    private val listener: RecipeDetailsResponseListener = object : RecipeDetailsResponseListener {
        public override fun didFetch(response: RecipeDetailsResponse?, message: String?) {
            showData(response)
        }

        public override fun didError(message: String?) {
            Toast.makeText(this@RecipeDetailActivity, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun showData(response: RecipeDetailsResponse?) {
        textView_meal_name!!.setText(response!!.title)
        textView_meal_source!!.setText(response.sourceName)
        Picasso.get().load(response.image).into(imageView_meal_image)
        textView_meal_servings!!.setText(response.servings.toString() + " Servings")
        textView_meal_ready!!.setText(response.readyInMinutes.toString() + " Minutes")
        textView_meal_price!!.setText(response.pricePerServing.toString() + " $ per serving")
        textView_meal_summary!!.setText(response.summary)
        //        dialog.dismiss();
        progressBar!!.setVisibility(View.GONE)
        scrollView!!.setVisibility(View.VISIBLE)
        recycler_meal_ingredients!!.setHasFixedSize(true)
        recycler_meal_ingredients!!.setLayoutManager(
            StaggeredGridLayoutManager(
                1,
                LinearLayoutManager.HORIZONTAL
            )
        )
        adapter = IngredientsAdapter(this@RecipeDetailActivity, response.extendedIngredients)
        recycler_meal_ingredients!!.setAdapter(adapter)
    }

    private val similarRecipeListener: SimilarRecipeListener = object : SimilarRecipeListener {
        public override fun didFetch(response: List<SimilarRecipeResponse>, message: String?) {
            recycler_meal_similar!!.setHasFixedSize(true)
            recycler_meal_similar!!.setLayoutManager(
                StaggeredGridLayoutManager(
                    1,
                    LinearLayoutManager.HORIZONTAL
                )
            )
            similarListAdapter =
                SimilarListAdapter(this@RecipeDetailActivity, response, similarOnClickListener)
            recycler_meal_similar!!.setAdapter(similarListAdapter)
            progressBar!!.setVisibility(View.GONE)
        }

        public override fun didError(message: String?) {}
    }
    private val similarOnClickListener: CustomOnClickListener = object : CustomOnClickListener {
        public override fun onClick(text: String?) {
//            Toast.makeText(RecipeDetailActivity.this, text, Toast.LENGTH_SHORT).show();
            startActivity(
                Intent(this@RecipeDetailActivity, RecipeDetailActivity::class.java)
                    .putExtra("id", text)
            )
        }
    }
    private val instructionsListener: InstructionsListener = object : InstructionsListener {
        public override fun didFetch(responses: List<InstructionsResponse>, message: String?) {
            recycler_meal_instructions!!.setHasFixedSize(true)
            recycler_meal_instructions!!.setLayoutManager(
                StaggeredGridLayoutManager(
                    1,
                    LinearLayoutManager.VERTICAL
                )
            )
            instructionsAdapter = InstructionsAdapter(this@RecipeDetailActivity, responses)
            recycler_meal_instructions!!.setAdapter(instructionsAdapter)
        }

        public override fun didError(message: String?) {
            Toast.makeText(this@RecipeDetailActivity, message, Toast.LENGTH_SHORT).show()
        }
    }
}