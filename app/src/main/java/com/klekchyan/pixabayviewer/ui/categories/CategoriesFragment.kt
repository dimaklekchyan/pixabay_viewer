package com.klekchyan.pixabayviewer.ui.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.klekchyan.pixabayviewer.databinding.FragmentCategoriesBinding

class CategoriesFragment : Fragment() {

    private var _binding: FragmentCategoriesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CategoriesViewModel by viewModels()
    private val adapter by lazy {
        CategoriesAdapter(CategoryClickListener { category ->
            viewModel.onCategoryClicked(category)
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCategoriesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = GridLayoutManager(context, 2,  RecyclerView.VERTICAL, false)

        binding.categoryRecyclerView.adapter = adapter
        binding.categoryRecyclerView.layoutManager = layoutManager

        viewModel.categories.observe(viewLifecycleOwner, { categories ->
            adapter.submitList(categories)
        })

        viewModel.navigateToPhotoListFragment.observe(viewLifecycleOwner, { category ->
            category?.let {
                findNavController().navigate(
                    CategoriesFragmentDirections
                        .actionCategoriesFragmentToPhotoListFragment(category.name.lowercase())
                )
                viewModel.navigateToPhotoListFragmentDone()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}