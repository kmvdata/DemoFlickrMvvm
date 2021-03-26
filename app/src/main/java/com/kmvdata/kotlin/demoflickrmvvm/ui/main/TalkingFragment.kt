package com.kmvdata.kotlin.demoflickrmvvm.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kmvdata.kotlin.demoflickrmvvm.R

class TalkingFragment : Fragment() {

    companion object {
        fun newInstance() = TalkingFragment()
    }

    private lateinit var viewModel: TalkingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.talking_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TalkingViewModel::class.java)
        // TODO: Use the ViewModel
    }

}