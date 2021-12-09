package br.com.douglasmotta.mysubscribers.ui.subscriber

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.douglasmotta.mysubscribers.R

class SubscriberFragment : Fragment() {

    private lateinit var viewModel: SubscriberViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.subscriber_fragment, container, false)
    }

}