package com.kmvdata.kotlin.demoflickrmvvm.ui.main

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kmvdata.kotlin.demoflickrmvvm.R

private const val TAG = "TAG-MainFragment"

class MainFragment : Fragment() {

    private lateinit var viewPager: ViewPager2

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate")
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView")
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewCreated")
        super.onViewCreated(view, savedInstanceState)

        viewPager = view.findViewById(R.id.viewPager)
        val navigation = view.findViewById<BottomNavigationView>(R.id.mainNavigation)

        viewPager.adapter = MainViewPagerAdapter(this)

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                navigation.menu.getItem(position).isChecked = true
            }
        })
        activity?.setTitle(R.string.title_flickr_photo)
        navigation.setOnNavigationItemSelectedListener {
            Log.d(TAG, "Begin to onOptionsItemSelected(${it.itemId})")
            when (it.itemId) {
                R.id.talk -> {
                    Log.d(TAG, "setOnNavigationItemSelectedListener(title_talk)")
                    viewPager.setCurrentItem(0, true)
                    activity?.setTitle(R.string.title_talk)
                }
                R.id.flickr_photo -> {
                    Log.d(TAG, "setOnNavigationItemSelectedListener(title_flickr_photo)")
                    viewPager.setCurrentItem(1, true)
                    activity?.setTitle(R.string.title_flickr_photo)
                }
                else -> {
                    Log.d(TAG, "setOnNavigationItemSelectedListener(title_mine)")
                    viewPager.setCurrentItem(2, true)
                    activity?.setTitle(R.string.title_mine)
                }
            }
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        Log.d(TAG, "onCreateOptionsMenu")
        inflater.inflate(R.menu.main_navigation_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private class MainViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = 3

        override fun createFragment(position: Int): Fragment {
            // Return a NEW fragment instance in createFragment(int)
            return when (position) {
                0 -> {
                    TalkListFragment()
                }
                1 -> {
                    FlickrPhotoFragment()
                }
                else -> {
                    MineFragment()
                }
            }
        }
    }
}
