package com.kmvdata.kotlin.demoflickrmvvm.ui.main

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.kmvdata.kotlin.demoflickrmvvm.R
import com.kmvdata.kotlin.demoflickrmvvm.api.GalleryItem

private const val TAG = "TAG-FlickrPhotoFragment"

class FlickrPhotoFragment : Fragment() {

    private lateinit var photoRecyclerView: RecyclerView

    private lateinit var swipeContainer: SwipeRefreshLayout

    companion object {
        fun newInstance() = FlickrPhotoFragment()
    }

    private lateinit var viewModel: FlickrPhotoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView")
        return inflater.inflate(R.layout.flickr_photo_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(FlickrPhotoViewModel::class.java)

        photoRecyclerView = view.findViewById(R.id.mineRecyclerView)
        swipeContainer = view.findViewById(R.id.swipeContainer)
        swipeContainer.setOnRefreshListener {
            viewModel.fetchPhotos()
            Handler(Looper.getMainLooper()).postDelayed({
                swipeContainer.isRefreshing = false
                Toast.makeText(context?.applicationContext, "Refreshed", Toast.LENGTH_SHORT)
                    .show()
            }, 2000)
        }

        photoRecyclerView.layoutManager = GridLayoutManager(context, 3)
        photoRecyclerView.adapter = PhotoAdapter()
        viewModel.galleryItems.observe(viewLifecycleOwner, {
            Log.d(TAG, "Have gallery items from view model ${it.size}")
            photoRecyclerView.adapter = PhotoAdapter()
        })
    }

    private inner class PhotoAdapter() : RecyclerView.Adapter<PhotoHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder {
            val galleryItemView =
                layoutInflater.inflate(R.layout.flickr_photo_list_item, parent, false) as ImageView
            return PhotoHolder(galleryItemView)
        }

        override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
            val galleryItem = viewModel.getGalleryItem(position) ?: return
            holder.bind(galleryItem)
            Log.d(TAG, "Fetch image by Glide: ${galleryItem.url}")
            Glide.with(this@FlickrPhotoFragment)
                .load(galleryItem.url)
                .into(holder.galleryItemView)
        }

        override fun getItemCount(): Int {
            return viewModel.getGalleryItemCount()
        }
    }

    private inner class PhotoHolder(val galleryItemView: ImageView) :
        RecyclerView.ViewHolder(galleryItemView) {

        private lateinit var galleryItem: GalleryItem

        init {
            galleryItemView.setOnClickListener {
                Log.d(TAG, " galleryItemView OnClick!")
            }
        }

        fun bind(galleryItem: GalleryItem) {
            this.galleryItem = galleryItem
        }
    }
}
