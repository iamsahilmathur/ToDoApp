package com.example.todo.presentation.baseviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.example.todo.core.utilities.autoCleaned


abstract class BaseFragment<V : ViewDataBinding, VM : ViewModel> : Fragment() {

    private var mBinding: V by autoCleaned()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = DataBindingUtil.inflate<V>(layoutInflater, getLayoutId(), container, false).also {
        it.setVariable(getBindingVariable(), getViewModel())
        mBinding = it
    }.root


    fun getBinding(): V {
        return mBinding
    }

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun getViewModel(): VM

    abstract fun getBindingVariable(): Int

    fun navigateToDestination(
        view: View,
        destinationId: Int,
        sourceId: Int=0,
        isPopUpTo: Boolean=false,
        isPopInclusive: Boolean=false,
        bundle: Bundle? = null
    ) {
        val navOptions= NavOptions.Builder()

        if(isPopUpTo){
            navOptions.setPopUpTo(sourceId,isPopInclusive)
        }
//        navOptions.setEnterAnim(R.anim.right_to_left)
//        // navOptions.setExitAnim(R.anim.right_to_left)
//        // navOptions.setPopExitAnim(R.anim.fade_in)
//        navOptions.setPopEnterAnim(R.anim.left_to_right_anim)
        view.findNavController().navigate(destinationId,bundle,navOptions.build())
    }

}