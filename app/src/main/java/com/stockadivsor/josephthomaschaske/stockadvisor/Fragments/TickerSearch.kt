package com.stockadivsor.josephthomaschaske.stockadvisor.Fragments

import android.os.Bundle
import android.app.Fragment
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.stockadivsor.josephthomaschaske.stockadvisor.R

class TickerSearch : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        var fragment: View  = inflater.inflate(R.layout.fragment_ticker_search, container, false)
        var tickerSearch: EditText = fragment.findViewById(R.id.tickerSearch)
        return fragment
    }
}
