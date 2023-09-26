package com.example.marvel_app.feature_character.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.annotation.CallSuper
import androidx.core.view.doOnLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding


/**
 * All fragments will inherit from this base class which handles view binding [_binding] and bottom
 * nav visibility [shouldHideBottomNavigation]. In addition, we've implemented counting idling
 * resources [idlingResource] that'll increment on the creation of the fragment and decrement when
 * all views are laid out.
 */
abstract class BaseFragment<B : ViewBinding> : Fragment() {

    // Inner mutable binding
    private var _binding: B? = null

    // Binding used by subclasses, only valid between onCreateView and onDestroyView.
    val binding: B get() = _binding!!
    abstract fun onCreateBinding(inflater: LayoutInflater): B

    protected abstract fun setupUI(view: View, savedInstanceState: Bundle?)

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindView(inflater, container)
        return binding.root
    }

    @CallSuper
    protected open fun bindView(inflater: LayoutInflater, container: ViewGroup?): B {
        return onCreateBinding(inflater)
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI(view, savedInstanceState)
    }

    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}