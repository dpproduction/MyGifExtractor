package com.example.mygifextractor.ui.mygifextractor

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
        println("viewModel will be instantiated with section $section")
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

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"
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
                        1 -> putInt(ARG_SECTION_NUMBER, sectionNumber)
                        2 -> {
                            putInt(ARG_SECTION_NUMBER, sectionNumber)
                            putString(ARG_SECTION_LABEL, "latest")
                        }
                        3 -> {
                            putInt(ARG_SECTION_NUMBER, sectionNumber)
                            putString(ARG_SECTION_LABEL, "top")
                        }
                        4 -> {
                            putInt(ARG_SECTION_NUMBER, sectionNumber)
                            putString(ARG_SECTION_LABEL, "hot")
                        }
                    }
                }
            }
        }
    }
}