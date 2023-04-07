package com.example.todo.presentation.dashboard

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.todo.BR
import com.example.todo.R
import com.example.todo.core.utilities.Constants
import com.example.todo.core.utilities.Resource
import com.example.todo.core.utilities.ISendAction
import com.example.todo.core.utilities.isVisible
import com.example.todo.databinding.FragmentDashboardBinding
import com.example.todo.domain.models.EventModel
import com.example.todo.presentation.baseviews.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : BaseFragment<FragmentDashboardBinding, DashboardViewModel>() {

    private val dashboardViewModel: DashboardViewModel by viewModels()
    private lateinit var eventAdapter: EventAdapter
    private var eventList: List<EventModel> = emptyList()
    var position=-1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRv()
        getAllEvents()
    }


    private fun initRv() {
        eventAdapter = EventAdapter(object : ISendAction {
            override fun data(action: String, pos: Int) {
                when (action) {
                    Constants.UPDATE_CONST -> {
                        val bundle = Bundle()
                        bundle.putInt(Constants.ID, eventList[pos].id)
                        bundle.putString(Constants.KEY, Constants.UPDATE_CONST)
                        navigateToDestination(
                            view!!,
                            R.id.action_dashboardFragment_to_eventFragment,
                            bundle = bundle
                        )
                    }
                    Constants.DELETE_CONST -> {
                        position= pos
                        dashboardViewModel.deleteEvent(eventList[pos])
                    }
                }
            }

        })
        getBinding().rvEvent.adapter = eventAdapter
    }

    private fun getAllEvents() {
        dashboardViewModel.state.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loading -> getBinding().progressBar.isVisible(true)
                is Resource.Success -> {
                    it.data?.let { list ->
                        eventList= list
                        eventAdapter.differ.submitList(list)
                    }
                    getBinding().rvEvent.isVisible(true)
                    getBinding().statusTV.isVisible(false)
                    getBinding().progressBar.isVisible(false)
                }
                is Resource.Error -> {
                    getBinding().rvEvent.isVisible(false)
                    getBinding().statusTV.isVisible(true)
                    getBinding().progressBar.isVisible(false)
                    getBinding().statusTV.text = it.message
                }
            }
        })

        dashboardViewModel.dELiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loading -> getBinding().progressBar.isVisible(true)
                is Resource.Success -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    eventAdapter.removeEvent(position)
                    getBinding().rvEvent.isVisible(true)
                    getBinding().statusTV.isVisible(false)
                    getBinding().progressBar.isVisible(false)
                }
                is Resource.Error -> {
                    getBinding().rvEvent.isVisible(false)
                    getBinding().statusTV.isVisible(true)
                    getBinding().progressBar.isVisible(false)
                    getBinding().statusTV.text = it.message
                }
            }
        })

    }


    override fun getLayoutId(): Int {
        return R.layout.fragment_dashboard
    }

    override fun getViewModel(): DashboardViewModel {
        return dashboardViewModel
    }

    override fun getBindingVariable(): Int {
        return BR.dashboardVM
    }


}