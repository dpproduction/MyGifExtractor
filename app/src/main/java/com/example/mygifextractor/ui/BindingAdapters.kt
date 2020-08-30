package com.example.mygifextractor.ui

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.mygifextractor.R
import com.example.mygifextractor.viewModels.base.DevLifeApiStatus


@BindingAdapter("gifUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.ic_loading_animation)
                    .error(R.drawable.ic_broken_image)
            )
            .into(imgView)
    }
}

@BindingAdapter("devLifeApiStatusImg")
fun bindStatus(statusImageView: ImageView, status: DevLifeApiStatus?) {
    when (status) {
        DevLifeApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_loading_animation)
        }
        DevLifeApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        DevLifeApiStatus.DONE ->
            statusImageView.visibility = View.GONE
        DevLifeApiStatus.EMPTY -> {
            statusImageView.setImageResource(R.drawable.ic_empty)
            statusImageView.visibility = View.VISIBLE
        }
    }
}

@BindingAdapter("devLifeApiStatusTxt")
fun bindStatus(statusTextView: TextView, status: DevLifeApiStatus?) {
    when (status) {
        DevLifeApiStatus.LOADING ->
            statusTextView.visibility = View.GONE
        DevLifeApiStatus.ERROR -> {
            statusTextView.text = statusTextView.context.getString(R.string.connection_error)
            statusTextView.visibility = View.VISIBLE
        }
        DevLifeApiStatus.DONE ->
            statusTextView.visibility = View.GONE
        DevLifeApiStatus.EMPTY -> {
            statusTextView.text = statusTextView.context.getString(R.string.empty_data)
            statusTextView.visibility = View.VISIBLE
        }
    }
}

@BindingAdapter("devLifeGifStatus")
fun bindGifStatus(imageView: ImageView, status: DevLifeApiStatus?) {
    when (status) {
        DevLifeApiStatus.LOADING ->
            imageView.visibility = View.VISIBLE
        DevLifeApiStatus.ERROR ->
            imageView.visibility = View.INVISIBLE
        DevLifeApiStatus.DONE ->
            imageView.visibility = View.VISIBLE
        DevLifeApiStatus.EMPTY ->
            imageView.visibility = View.INVISIBLE
    }
}