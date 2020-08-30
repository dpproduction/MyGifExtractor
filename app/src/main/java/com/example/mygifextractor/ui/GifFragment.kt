package com.example.mygifextractor.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mygifextractor.databinding.FragmentGifBinding
import com.example.mygifextractor.viewModels.GifExtractViewModelFactory
import com.example.mygifextractor.viewModels.GifViewModel

class GifFragment : Fragment() {

    private lateinit var viewModel: GifViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val section = arguments?.getString(ARG_SECTION_LABEL, null)
        val viewModelFactory = GifExtractViewModelFactory(section)
        viewModel = ViewModelProvider(
            this, viewModelFactory
        ).get(GifViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentGifBinding.inflate(inflater)

        binding.viewModel = viewModel

        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            viewModel.fetchData()
        }
    }

    companion object {

        private const val ARG_SECTION_LABEL = "section_label"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): GifFragment {
            return GifFragment().apply {
                arguments = Bundle().apply {
                    when (sectionNumber) {
                        0 -> putString(ARG_SECTION_LABEL, "")
                        1 -> putString(ARG_SECTION_LABEL, "latest")
                        2 -> putString(ARG_SECTION_LABEL, "top")
                        3 -> putString(ARG_SECTION_LABEL, "hot")
                    }
                }
            }
        }
    }
}