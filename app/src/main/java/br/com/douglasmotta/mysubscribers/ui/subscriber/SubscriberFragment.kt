package br.com.douglasmotta.mysubscribers.ui.subscriber

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.douglasmotta.mysubscribers.R
import br.com.douglasmotta.mysubscribers.data.db.AppDataBase
import br.com.douglasmotta.mysubscribers.data.db.SubscriberDao
import br.com.douglasmotta.mysubscribers.extension.hideKeyboard
import br.com.douglasmotta.mysubscribers.repository.DatabaseDataSource
import br.com.douglasmotta.mysubscribers.repository.SubscriberRepository
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.subscriber_fragment.*

class SubscriberFragment : Fragment(R.layout.subscriber_fragment) {

    private val viewModel: SubscriberViewModel by viewModels {
        object : ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val subscriberDao: SubscriberDao =
                    AppDataBase.getInstance(requireContext()).subscriberDao

                val repository: SubscriberRepository = DatabaseDataSource(subscriberDao)
                return SubscriberViewModel(repository) as T
            }
        }
    }

    private val args: SubscriberFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args.subscriber?.let {
            button_subscriber.text = getString(R.string.subscriber_button_update)
            input_name.setText(it.name)
            input_email.setText(it.email)

            button_delete.visibility = View.VISIBLE
        }

        observeEvents()
        setListeners()
    }

    private fun observeEvents() {
        viewModel.subscriberStateEventData.observe(viewLifecycleOwner) {
            when(it){
                is SubscriberViewModel.SubscriberState.Inserted,
                    is SubscriberViewModel.SubscriberState.Updated,
                    is SubscriberViewModel.SubscriberState.Deleted -> {
                    clearFields()
                    hideKeyboard()
                    requireView().requestFocus()
                    findNavController().popBackStack()
                    }
            }
        }

        viewModel.messageEventData.observe(viewLifecycleOwner) {
            Snackbar.make(requireView(), it, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun clearFields() {
        input_name.text?.clear()
        input_email.text?.clear()
    }

    private fun hideKeyboard() {
        val parentActivity = requireActivity()
        if (parentActivity is AppCompatActivity){
            parentActivity.hideKeyboard()
        }
    }

    private fun setListeners() {
        button_subscriber.setOnClickListener {
            val name = input_name.text.toString()
            val email = input_email.text.toString()

            viewModel.addOrUpdateSubscriber(name, email, args.subscriber?.id ?: 0)
        }

        button_delete.setOnClickListener {
            viewModel.removeSubscriber(args.subscriber?.id ?: 0)
        }
    }
}