package com.kmvdata.kotlin.demoflickrmvvm.ui.main

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.kmvdata.kotlin.demoflickrmvvm.R
import com.kmvdata.kotlin.demoflickrmvvm.pojo.TalkInfoVO
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "TAG-TalkFragment"

class TalkFragment : Fragment() {

    private lateinit var talkRecyclerView: RecyclerView
    private lateinit var swipeContainer: SwipeRefreshLayout

    companion object {
        fun newInstance() = TalkFragment()
    }

    private lateinit var viewModel: TalkViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.talk_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(TalkViewModel::class.java)

        swipeContainer = view.findViewById(R.id.swipeContainer)
        talkRecyclerView = view.findViewById(R.id.talkRecyclerView)

        swipeContainer.setOnRefreshListener {
            viewModel.mockTalkInfoList()
            Handler(Looper.getMainLooper()).postDelayed({
                swipeContainer.isRefreshing = false
                Toast.makeText(context?.applicationContext, "Refreshed", Toast.LENGTH_SHORT)
                    .show()
            }, 2000)
        }

        talkRecyclerView.layoutManager = GridLayoutManager(context, 1)
        talkRecyclerView.adapter = TalkListAdapter()
        viewModel.talkInfoes.observe(viewLifecycleOwner, {
            Log.d(TAG, "Have gallery items from view model ${it.size}")
            talkRecyclerView.adapter = TalkListAdapter()
        })
    }

    private inner class TalkListHolder(val talkItemView: View) :
        RecyclerView.ViewHolder(talkItemView) {
        private lateinit var talkInfoVO: TalkInfoVO

        init {
            talkItemView.setOnClickListener {
                Log.d(TAG, " TalkListHolder::talkItemView OnClick!")
            }
        }

        fun bind(talkInfoVO: TalkInfoVO) {
            Log.d(TAG, "bind talkInfoVO: ${Gson().toJson(talkInfoVO)}")
            this.talkInfoVO = talkInfoVO
            val iconImage: ImageView = talkItemView.findViewById(R.id.iconImageView)
            Glide.with(this@TalkFragment)
                .load(talkInfoVO.iconUrl)
                .into(iconImage)

            val talkNameTextView: TextView = talkItemView.findViewById(R.id.talkNameTextView)
            talkNameTextView.text = talkInfoVO.talkName

            val talkTimeTextView: TextView = talkItemView.findViewById(R.id.talkTimeTextView)
            val pattern = "hh:mm"
            val simpleDateFormat = SimpleDateFormat(pattern)
            talkTimeTextView.text = simpleDateFormat.format(talkInfoVO.lastTime)

            val talkContentTextView: TextView = talkItemView.findViewById(R.id.talkContentTextView)
            talkContentTextView.text = talkInfoVO.iconUrl

            Log.d(TAG, "Fetch image by Glide: ${talkInfoVO.iconUrl}")
        }
    }

    private inner class TalkListAdapter() : RecyclerView.Adapter<TalkListHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TalkListHolder {
            val talkItemView =
                layoutInflater.inflate(R.layout.talk_list_item, parent, false)
            return TalkListHolder(talkItemView)
        }

        override fun onBindViewHolder(holder: TalkListHolder, position: Int) {
            val talkInfoVO = viewModel.getTalkInfoItem(position) ?: return
            holder.bind(talkInfoVO)
        }

        override fun getItemCount(): Int = viewModel.getTalkInfoCount()

    }
}