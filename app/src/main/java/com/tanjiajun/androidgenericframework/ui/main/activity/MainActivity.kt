package com.tanjiajun.androidgenericframework.ui.main.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.tanjiajun.androidgenericframework.EXTRA_LOGOUT
import com.tanjiajun.androidgenericframework.R
import com.tanjiajun.androidgenericframework.databinding.ActivityMainBinding
import com.tanjiajun.androidgenericframework.ui.BaseActivity
import com.tanjiajun.androidgenericframework.ui.main.viewModel.MainViewModel
import com.tanjiajun.androidgenericframework.ui.repository.fragment.RepositoryFragment
import com.tanjiajun.androidgenericframework.ui.user.activity.PersonalCenterActivity
import com.tanjiajun.androidgenericframework.ui.user.activity.RegisterAndLoginActivity
import com.tanjiajun.androidgenericframework.utils.getViewModelFactory
import com.tanjiajun.androidgenericframework.utils.registerOnTabSelectedListener
import com.tanjiajun.androidgenericframework.utils.startActivity

/**
 * Created by TanJiaJun on 2019-07-22.
 */
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(), MainViewModel.Handlers {

    override val layoutRes: Int = R.layout.activity_main
    override val viewModel by viewModels<MainViewModel> { getViewModelFactory() }

    private val tlRepository: TabLayout
        get() = binding.tlRepository

    private val vpRepository: ViewPager2
        get() = binding.vpRepository

    private lateinit var repositoryFragments: MutableList<RepositoryFragment>

    private lateinit var adapter: OrderFragmentStateAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            lifecycleOwner = this@MainActivity
            viewModel = this@MainActivity.viewModel
            handlers = this@MainActivity
        }

        initUI()
        initData()
    }

    private fun initUI() {
        repositoryFragments = mutableListOf<RepositoryFragment>().apply {
            viewModel.getDefaultLanguageNames().forEach { add(RepositoryFragment.newInstance(it)) }
        }
        adapter = OrderFragmentStateAdapter(this, repositoryFragments)
        viewModel.getDefaultLanguageNames().forEach {
            tlRepository.addTab(tlRepository.newTab().setText(it))
        }

        tlRepository.addOnTabSelectedListener(registerOnTabSelectedListener {
            onTabSelected { vpRepository.currentItem = it?.position ?: 0 }
        })

        vpRepository.adapter = adapter
        vpRepository.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) =
                    tlRepository.setScrollPosition(position, 0f, true)
        })
    }

    private fun initData() =
            with(viewModel) {
                languageNames.observe(this@MainActivity, Observer {
                    if (it.size > getDefaultLanguageNamesCount()) {
                        tlRepository.addTab(tlRepository.newTab().setText(getLastLanguageName()))
                        repositoryFragments.add(RepositoryFragment.newInstance(getLastLanguageName()))
                        adapter.notifyItemInserted(getLastLanguageNameIndex())
                        vpRepository.currentItem = getLastLanguageNameIndex()
                    }
                })
            }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent
                ?.getBooleanExtra(EXTRA_LOGOUT, false)
                ?.takeIf { it }
                ?.let {
                    startActivity<RegisterAndLoginActivity>()
                    finish()
                }
    }

    override fun onPersonalCenterClick(view: View) =
            startActivity<PersonalCenterActivity>()

    override fun onAddClick(view: View) {
        viewModel.addLanguageName()
    }

}

class OrderFragmentStateAdapter(fragmentActivity: FragmentActivity,
                                private val repositoryFragments: List<RepositoryFragment>)
    : FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment =
            repositoryFragments[position]

    override fun getItemCount(): Int = repositoryFragments.size

}