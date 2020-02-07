package com.tanjiajun.androidgenericframework.utils

import androidx.annotation.DrawableRes
import com.tanjiajun.androidgenericframework.R

/**
 * Created by TanJiaJun on 2020-02-07.
 */
enum class Language(val languageName: String, @DrawableRes val iconRes: Int) {

    KOTLIN("Kotlin", R.drawable.ic_circle_kotlin),

    JAVA("Java", R.drawable.ic_circle_java),

    SWIFT("Swift", R.drawable.ic_circle_kotlin),

    JAVA_SCRIPT("JavaScript", R.drawable.ic_circle_java_script),

    PYTHON("Python", R.drawable.ic_circle_python),

    GO("Go", R.drawable.ic_circle_go),

    CSS("CSS", R.drawable.ic_circle_css),

    PHP("PHP", R.drawable.ic_circle_php),

    RUBY("Ruby", R.drawable.ic_circle_ruby),

    C_PLUS_PLUS("C++", R.drawable.ic_circle_c_plus_plus),

    C("C", R.drawable.ic_circle_c),

    OTHER("Other", R.drawable.ic_circle_other);

    companion object {
        val LANGUAGES = listOf(KOTLIN, JAVA, SWIFT, JAVA_SCRIPT, PYTHON, GO, CSS, PHP, RUBY, C_PLUS_PLUS, C, OTHER)

        fun of(language: String): Language =
                when (language) {
                    KOTLIN.languageName -> KOTLIN
                    JAVA.languageName -> JAVA
                    SWIFT.languageName -> SWIFT
                    JAVA_SCRIPT.languageName -> JAVA_SCRIPT
                    PYTHON.languageName -> PYTHON
                    GO.languageName -> GO
                    CSS.languageName -> CSS
                    PHP.languageName -> PHP
                    RUBY.languageName -> RUBY
                    C_PLUS_PLUS.languageName -> C_PLUS_PLUS
                    C.languageName -> C
                    OTHER.languageName -> OTHER
                    else -> OTHER
                }
    }

}