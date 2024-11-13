package ru.practicum.android.diploma.util

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object Utils {
    private const val CLICK_DELAY = 2000L
    private var isClickAllowed: Boolean = true

    fun clickDebounce(viewLifecycleOwner: LifecycleOwner):Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            viewLifecycleOwner.lifecycleScope.launch {
                delay(CLICK_DELAY)
                isClickAllowed = true
            }
        }
        return current
    }
}
