package com.tanjiajun.androidgenericframework.ui.recyclerView

/**
 * Created by TanJiaJun on 2019-08-31.
 */
abstract class NoDataViewType<D> : BaseViewType<D>() {

    abstract fun bind(holder: BaseViewHolder,
                      position: Int)

    abstract fun isMatchViewType(position: Int,
                                 headerCount: Int,
                                 itemCount: Int,
                                 footerCount: Int): Boolean

    override fun bind(holder: BaseViewHolder, data: D, position: Int) {
        // no implementation
    }

    override fun bind(holder: BaseViewHolder, data: D,
                      position: Int,
                      payLoads: List<Any>) {
        // no implementation
    }

    override fun isMatchViewType(any: Any): Boolean = false

    fun bind(holder: BaseViewHolder,
             position: Int,
             payLoads: List<Any>) =
            holder.run {
                bind(this, position)
                binding.executePendingBindings()
            }

}