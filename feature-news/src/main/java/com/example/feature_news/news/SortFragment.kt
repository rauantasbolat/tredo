package com.example.feature_news.news

import android.content.Context
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.example.feature_news.R
import com.example.feature_news.databinding.FragmentSortListDialogItemBinding
import com.example.feature_news.databinding.FragmentSortListDialogBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SortFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentSortListDialogBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NewsViewModel by viewModels()

    interface SortSelectionListener {
        fun onSortSelected(sortCategory: String)
    }
    private lateinit var listener: SortSelectionListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (parentFragment is SortSelectionListener) {
            listener = parentFragment as SortSelectionListener
        } else {
            throw RuntimeException("$context ")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSortListDialogBinding.inflate(inflater, container, false)
        binding.sortByDate.setOnClickListener {
            listener.onSortSelected("publishedAt")
            dismiss()
        }
        binding.sortByPopularity.setOnClickListener {
            listener.onSortSelected("popularity")
            dismiss()
        }
        binding.sortByRelevance.setOnClickListener {
            listener.onSortSelected("relevancy")
            dismiss()
        }
        return binding.root
    }

    companion object {

        val TAG = "SortFragment"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}