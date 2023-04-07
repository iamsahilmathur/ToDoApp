package com.example.todo.presentation.event_management

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.todo.BR
import com.example.todo.R
import com.example.todo.core.utilities.Constants
import com.example.todo.core.utilities.Resource
import com.example.todo.core.utilities.Utils
import com.example.todo.core.utilities.isVisible
import com.example.todo.databinding.FragmentEventBinding
import com.example.todo.presentation.baseviews.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EventFragment : BaseFragment<FragmentEventBinding, EventViewModel>() {

    private val eventViewModel: EventViewModel by viewModels()
    var isUpdate: String? = null
    var eId: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {

        arguments?.let {
            isUpdate = requireArguments().getString(Constants.KEY)
            eId = requireArguments().getInt(Constants.ID)
        }
        if (isUpdate != null) {
            getBinding().addEventBtn.text = Constants.UPDATE_CONST
            eventViewModel.getStatus(Constants.UPDATE_CONST, eId)
        } else {
            eventViewModel.getStatus("")
        }


        eventViewModel.addLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> getBinding().progressBar.isVisible(true)
                is Resource.Success -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    getBinding().progressBar.isVisible(false)
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    getBinding().progressBar.isVisible(false)
                }
            }
        }

        eventViewModel.showSnackbar.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { msg ->
                Utils.showSnackbar(getBinding().pCL, msg)
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_event
    }

    override fun getViewModel(): EventViewModel {
        return eventViewModel
    }

    override fun getBindingVariable(): Int {
        return BR.eventVM
    }

}