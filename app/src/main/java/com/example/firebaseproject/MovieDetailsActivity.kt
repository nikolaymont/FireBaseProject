package com.example.firebaseproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var title: TextView
    private lateinit var releaseDate: TextView
    private lateinit var score: TextView
    private lateinit var overview: TextView
    private lateinit var banner: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        val id = intent.getIntExtra("id",0)
        Log.d("myLog","id   -  $id")

        title = findViewById(R.id.movies_details_title)
        releaseDate =findViewById(R.id.movies_details_date)
        score =findViewById(R.id.movies_details_score)
        overview =findViewById(R.id.movies_details_body_overview)
        banner =findViewById(R.id.movies_details_image_banner)

        val apiInterface = id?.let { ApiInterface.create().getMovieDetails(it,"e294a9388bae1aab03e4de9b3e4ca0a0") }
        apiInterface?.enqueue( object : Callback<MovieDetails> {
            override fun onResponse(call: Call<MovieDetails>, response: Response<MovieDetails>) {
                title.text = response?.body()?.title
                releaseDate.text = response?.body()?.release_date.toString()
                score.text = response?.body()?.vote_average.toString()
                overview.text = response?.body()?.overview

                Picasso.get().load("https://image.tmdb.org/t/p/w500" + response?.body()?.backdrop_path).into(banner)
            }
            override fun onFailure(call: Call<MovieDetails>?, t: Throwable?) {
                Log.d("myLog", "OnFailure ${t?.message}")
            }

        })

}}