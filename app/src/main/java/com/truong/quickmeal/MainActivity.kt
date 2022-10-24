package com.truong.quickmeal

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
import android.view.View
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
import androidx.appcompat.widget.SearchView
import com.truong.quickmeal.Adapters.IngredientsAdapter
import com.truong.quickmeal.Adapters.SimilarListAdapter
import com.truong.quickmeal.Adapters.InstructionsAdapter
import java.util.*

class MainActivity() : AppCompatActivity() {
    var manager: RequestManager? = null
    var adapter: RandomMealAdapter? = null

    //    ProgressDialog dialog;
    var recyclerView: RecyclerView? = null
    var tags: MutableList<String> = ArrayList()
    var spinner: Spinner? = null
    var searchView_home: SearchView? = null
    var progressBar: ProgressBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recycler_random)
        spinner = findViewById(R.id.spinner_tags)
        searchView_home = findViewById(R.id.searchView_home)
        progressBar = findViewById<View>(R.id.loader) as ProgressBar?
        val doubleBounce: Sprite = Wave()
        progressBar!!.setIndeterminateDrawable(doubleBounce)
        manager = RequestManager(this)
        val arrayAdapter: ArrayAdapter<*> = ArrayAdapter.createFromResource(
            this,
            R.array.tags,
            R.layout.spinner_text
        )
        arrayAdapter.setDropDownViewResource(R.layout.spinner_inner_text)
        spinner?.setAdapter(arrayAdapter)
        spinner?.setOnItemSelectedListener(spinnerSelectedListener)
        searchView_home?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            public override fun onQueryTextSubmit(query: String): Boolean {
                Toast.makeText(this@MainActivity, "Will be added soon!", Toast.LENGTH_SHORT).show()
                return true
            }

            public override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })


//        tags.add("dessert");
//        dialog = new ProgressDialog(this);
//        dialog.setTitle("Loading...");

//        manager.GetRandomRecipes(listener, tags);
//        dialog.show();
    }

    private val listener: RandomAPIResponseListener = object : RandomAPIResponseListener {
        public override fun didFetch(responses: List<RandomRecipe?>?, message: String?) {
            recyclerView!!.setHasFixedSize(true)
            recyclerView!!.setLayoutManager(
                StaggeredGridLayoutManager(
                    1,
                    LinearLayoutManager.VERTICAL
                )
            )
            adapter = RandomMealAdapter(this@MainActivity, responses, customOnClickListener)
            recyclerView!!.setAdapter(adapter)
            recyclerView!!.setVisibility(View.VISIBLE)
            progressBar!!.setVisibility(View.GONE)
        }

        public override fun didError(message: String?) {
            recyclerView!!.setVisibility(View.VISIBLE)
            progressBar!!.setVisibility(View.GONE)
            Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
        }
    }
    private val spinnerSelectedListener: AdapterView.OnItemSelectedListener =
        object : AdapterView.OnItemSelectedListener {
            public override fun onItemSelected(
                adapterView: AdapterView<*>,
                view: View,
                i: Int,
                l: Long
            ) {
                tags.clear()
                tags.add(adapterView.getSelectedItem().toString().lowercase(Locale.getDefault()))
                manager!!.GetRandomRecipes(listener, tags)
                recyclerView!!.setVisibility(View.GONE)
                progressBar!!.setVisibility(View.VISIBLE)
            }

            public override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }
    private val customOnClickListener: CustomOnClickListener = object : CustomOnClickListener {
        public override fun onClick(text: String?) {
            startActivity(
                Intent(this@MainActivity, RecipeDetailActivity::class.java)
                    .putExtra("id", text)
            )
        }
    }
}