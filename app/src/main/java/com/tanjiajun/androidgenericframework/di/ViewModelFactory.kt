package com.tanjiajun.androidgenericframework.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.MapKey
import dagger.Module
import javax.inject.Inject
import javax.inject.Provider
import kotlin.reflect.KClass

/**
 * Created by TanJiaJun on 2019-08-07.
 */
class AndroidGenericFrameworkViewModelFactory @Inject constructor(
        private val creators: @JvmSuppressWildcards Map<Class<out ViewModel>, Provider<ViewModel>>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            creators[modelClass]
                    ?.let {
                        creators[modelClass]
                                ?.let { getViewModel<T>(it) }
                                ?: throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
                    }
                    ?: creators.entries
                            .find { modelClass.isAssignableFrom(it.key) }
                            ?.value
                            ?.let { getViewModel<T>(it) }
                    ?: throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")

    private fun <T : ViewModel?> getViewModel(provider: Provider<ViewModel>): T =
            try {
                @Suppress("UNCHECKED_CAST")
                provider.get() as T
            } catch (e: Exception) {
                throw RuntimeException(e)
            }

}

@Module
internal abstract class ViewModelBuilder {

    @Binds
    internal abstract fun bindViewModelFactory(
            factory: AndroidGenericFrameworkViewModelFactory
    ): ViewModelProvider.Factory

}

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)