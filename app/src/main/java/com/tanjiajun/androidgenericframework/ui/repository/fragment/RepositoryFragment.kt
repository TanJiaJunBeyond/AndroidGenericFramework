package com.tanjiajun.androidgenericframework.ui.repository.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.tanjiajun.androidgenericframework.EXTRA_LANGUAGE
import com.tanjiajun.androidgenericframework.FRAGMENT_TAG_REPOSITORY
import com.tanjiajun.androidgenericframework.R
import com.tanjiajun.androidgenericframework.databinding.FragmentRepositoryBinding
import com.tanjiajun.androidgenericframework.databinding.LayoutErrorBinding
import com.tanjiajun.androidgenericframework.ui.BaseFragment
import com.tanjiajun.androidgenericframework.ui.repository.adapter.RepositoryAdapter
import com.tanjiajun.androidgenericframework.ui.repository.viewmodel.RepositoryViewModel
import com.tanjiajun.androidgenericframework.utils.getViewModelFactory
import com.tanjiajun.androidgenericframework.utils.otherwise
import com.tanjiajun.androidgenericframework.utils.yes

/**
 * Created by TanJiaJun on 2020-02-07.
 */
class RepositoryFragment private constructor()
    : BaseFragment<FragmentRepositoryBinding, RepositoryViewModel>() {

    override val layoutRes: Int = R.layout.fragment_repository
    override val viewModel by viewModels<RepositoryViewModel> { getViewModelFactory() }
    override val transactionTag: String = FRAGMENT_TAG_REPOSITORY

    private lateinit var language: String
    private val adapter = RepositoryAdapter()
    private var errorView: View? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.run { language = getString(EXTRA_LANGUAGE, "") }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initUI()
        initData()
    }

    private fun initUI() =
            with(binding) {
                lifecycleOwner = this@RepositoryFragment
                viewModel = this@RepositoryFragment.viewModel

                rvRepository.layoutManager = LinearLayoutManager(context)
                rvRepository.adapter = this@RepositoryFragment.adapter

                this@RepositoryFragment.viewModel.isShowErrorView.observe(this@RepositoryFragment, Observer { isShowErrorView ->
                    isShowErrorView
                            .yes {
                                if (!vsError.isInflated) {
                                    vsError.viewStub?.inflate()?.also { errorView = it }
                                    vsError.setOnInflateListener { _, inflated ->
                                        DataBindingUtil.bind<LayoutErrorBinding>(inflated)?.viewModel = viewModel
                                    }
                                } else {
                                    errorView?.visibility = View.VISIBLE
                                }
                            }
                            .otherwise { errorView?.visibility = View.GONE }
                })
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