package com.tanjiajun.androidgenericframework.ui.repository.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.tanjiajun.androidgenericframework.EXTRA_LANGUAGE
import com.tanjiajun.androidgenericframework.FRAGMENT_TAG_REPOSITORY
import com.tanjiajun.androidgenericframework.R
import com.tanjiajun.androidgenericframework.databinding.FragmentRepositoryBinding
import com.tanjiajun.androidgenericframework.ui.BaseFragment
import com.tanjiajun.androidgenericframework.ui.repository.adapter.RepositoryAdapter
import com.tanjiajun.androidgenericframework.ui.repository.viewmodel.RepositoryViewModel
import com.tanjiajun.androidgenericframework.utils.getViewModelFactory

/**
 * Created by TanJiaJun on 2020-02-07.
 */
class RepositoryFragment : BaseFragment<FragmentRepositoryBinding>() {

    private val viewModel by viewModels<RepositoryViewModel> { getViewModelFactory() }
    private lateinit var language: String
    private val adapter = RepositoryAdapter()

    override val layoutRes: Int = R.layout.fragment_repository

    override fun getTransactionTag(): String = FRAGMENT_TAG_REPOSITORY

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.run { language = getString(EXTRA_LANGUAGE, "") }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initUI()
        initData()
    }

    private fun initUI() =
            with(binding.rvRepository) {
                layoutManager = LinearLayoutManager(context)
                adapter = this@RepositoryFragment.adapter
            }

    private fun initData() =
            with(viewModel) {
                getRepositories(language)
                repositories.observe(this@RepositoryFragment, Observer {
                    adapter.setItems(it)
                })
            }

    companion object {
        fun newInstance(language: String): RepositoryFragment =
                RepositoryFragment().apply {
                    arguments = Bundle().apply {
                        putString(EXTRA_LANGUAGE, language)
                    }
                }
    }

}