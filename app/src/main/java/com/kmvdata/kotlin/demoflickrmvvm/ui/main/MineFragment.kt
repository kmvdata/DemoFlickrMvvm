package com.kmvdata.kotlin.demoflickrmvvm.ui.main

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.kmvdata.kotlin.demoflickrmvvm.R

private const val TAG = "TAG-MineFragment"

private const val VIEW_TYPE_TOP = 0
private const val VIEW_TYPE_NORMAL = 1

class MineFragment : Fragment() {


    companion object {
        fun newInstance() = MineFragment()
    }

    private lateinit var viewModel: MineViewModel
    private lateinit var swipeContainer: SwipeRefreshLayout
    private lateinit var mineRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView")
        return inflater.inflate(R.layout.mine_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated")
        viewModel = ViewModelProvider(this).get(MineViewModel::class.java)

        swipeContainer = view.findViewById(R.id.swipeContainer)
        mineRecyclerView = view.findViewById(R.id.mineRecyclerView)

        mineRecyclerView.layoutManager = GridLayoutManager(context, 1)
        mineRecyclerView.addItemDecoration(SpacesItemDecoration(50))
        mineRecyclerView.adapter = MineListAdapter()
    }

    private inner class MineListAdapter() : RecyclerView.Adapter<MineListHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MineListHolder {
            Log.d(TAG, "onCreateViewHolder viewType:$viewType")
            return when (viewType) {
                VIEW_TYPE_TOP -> {
                    MineListHolder(
                        layoutInflater.inflate(
                            R.layout.mine_list_item_top,
                            parent,
                            false
                        )
                    )
                }
                else -> {
                    MineListHolder(
                        layoutInflater.inflate(
                            R.layout.mine_list_item_normal,
                            parent,
                            false
                        )
                    )
                }
            }
        }

        override fun getItemViewType(position: Int): Int {
            return when (position) {
                0 -> {
                    VIEW_TYPE_TOP
                }
                else -> {
                    VIEW_TYPE_NORMAL
                }
            }
        }

        override fun getItemCount(): Int = 30

        override fun onBindViewHolder(holder: MineListHolder, position: Int) {
            Log.d(TAG, "onBindViewHolder: ${position}")
        }
    }

    private inner class MineListHolder(val itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                Log.d(TAG, " itemView OnClick!")
            }
        }
    }

    private inner class SpacesItemDecoration(private val space: Int) :
        RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)
            // Add top margin only for the first item to avoid double space between items
            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.bottom = space
            } else if (parent.getChildAdapterPosition(view) == 29) {
                outRect.bottom = 200
            } else if (parent.getChildAdapterPosition(view) % 3 == 0) {
                outRect.top = space
            }
            Log.d(TAG, "Space in ${parent.getChildAdapterPosition(view)} is $space")
        }
    }
}
