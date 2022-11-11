package com.example.firebaseproject.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaseproject.*
import com.example.firebaseproject.viewmodel.MoviesViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ActivityMovies : AppCompatActivity() {
    private lateinit var mViewModel: MoviesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)

        Log.d("myLog", " ActivityMovies start")

        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = GridLayoutManager(this, 2)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<ItemsViewModel>()

        mViewModel = MoviesViewModel()
        val result = mViewModel.getMovies()

        // This loop will create 20 Views containing
        // the image with the count of view
        for (i in 1..20) {
            data.add(ItemsViewModel(com.google.android.gms.base.R.drawable.common_full_open_on_phone, "Item " + i))
        }



        val apiInterface = ApiInterface.create().getMovies("e294a9388bae1aab03e4de9b3e4ca0a0")

        //apiInterface.enqueue( Callback<List<Movie>>())
        apiInterface.enqueue( object : Callback<Movies>, CustomAdapter.ItemClickListener {
            override fun onResponse(call: Call<Movies>?, response: Response<Movies>?) {

                Log.d("myLog", "OnResponse Success ${response?.body()?.results}")

                // This will pass the ArrayList to our Adapter
                val adapter = CustomAdapter(response?.body()?.results, this)

                // Setting the Adapter with the recyclerview
                recyclerview.adapter = adapter


               //if(response?.body() != null)
                 //   recyclerAdapter.setMovieListItems(response.body()!!)
            }

            override fun onFailure(call: Call<Movies>?, t: Throwable?) {
                Log.d("myLog", "OnFailure ${t?.message}")
            }

            override fun onItemClick(id: Int) {
              val intent = Intent (this@ActivityMovies, MovieDetailsActivity::class.java)
                intent.putExtra("id",id)
                startActivity(intent)
            }
        })
    }


    override fun onBackPressed() {
        super.onBackPressed()
        this.finishAffinity()
        Log.d("myLog", " ActivityMovies been closed")
    }
}