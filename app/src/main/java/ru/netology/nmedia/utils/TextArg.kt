package ru.netology.nmedia.utils

import android.os.Bundle

private const val KEY = "TEXT_ARG"

var Bundle.textArg: String?
    get() = getString(KEY)
    set(value) = putString(KEY, value)