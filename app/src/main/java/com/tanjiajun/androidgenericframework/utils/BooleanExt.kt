package com.tanjiajun.androidgenericframework.utils

/**
 * Created by TanJiaJun on 2020-01-28.
 */
sealed class BooleanExt<out T>

class TransferData<out T>(val data: T) : BooleanExt<T>()
object Otherwise : BooleanExt<Nothing>()

inline fun <T> Boolean.yes(block: () -> T): BooleanExt<T> =
        when {
            this -> TransferData(block.invoke())
            else -> Otherwise
        }

inline fun <T> BooleanExt<T>.otherwise(block: () -> T): T =
        when (this) {
            is Otherwise -> block()
            is TransferData -> data
        }