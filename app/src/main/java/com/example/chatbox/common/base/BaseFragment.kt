package com.example.chatbox.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.chatbox.common.inflater

abstract class BaseFragment<VB : ViewBinding>(
    private val inflater: inflater<VB>
) : Fragment() {

    private var _binding: VB? = null
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = this.inflater.invoke(inflater, container, false)
        return binding.root
    }

    abstract fun started()
    abstract fun observers()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        started()
        observers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}