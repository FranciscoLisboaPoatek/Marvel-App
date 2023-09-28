package com.example.marvel_app.feature_character.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding


abstract class BaseFragment<B : ViewBinding> : Fragment() {

    private var _binding: B? = null

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