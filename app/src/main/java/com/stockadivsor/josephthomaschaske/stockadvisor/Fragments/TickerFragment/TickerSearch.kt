package com.stockadivsor.josephthomaschaske.stockadvisor.Fragments.TickerFragment
import android.os.Bundle
import android.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.android.volley.Request
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.stockadivsor.josephthomaschaske.stockadvisor.Constants
import com.stockadivsor.josephthomaschaske.stockadvisor.IEXDataModels.IEXSymbols
import com.stockadivsor.josephthomaschaske.stockadvisor.R
import java.lang.reflect.Type


class TickerSearch : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragment: View = inflater.inflate(R.layout.fragment_ticker_search, container, false)
        requestAndAssembleSymbolsAdapter(fragment)

        return fragment
    }

    private fun requestAndAssembleSymbolsAdapter(fragment: View) {
        val queue = Volley.newRequestQueue(fragment.context)
        val stringRequest = StringRequest(
                Request.Method.GET,
                Constants.IEX_SYMBOLS_ENDPOINT,
                Response.Listener<String> { response ->
                    var type: Type = object : TypeToken<MutableList<IEXSymbols>>() {}.type
                    var symbols: MutableList<IEXSymbols> = Gson().fromJson(response, type)
                    var symbolsAdapter: ArrayAdapter<String> = ArrayAdapter(
                            fragment.context,
                            android.R.layout.simple_dropdown_item_1line,
                            symbols.map {
                                it.symbol + ": " + it.name
                            }.toTypedArray()
                    )
                    var typeAhead: AutoCompleteTextView = fragment.findViewById(R.id.symbolAutoComplete)
                    typeAhead.setOnItemClickListener({
                        parent: AdapterView<*>,
                        view: View,
                        position: Int,
                        id: Long ->
                        var value: String = (parent.getItemAtPosition(position) as String).split(":")[0]
                        typeAhead.setText(value)
                    })
                    typeAhead.setAdapter(symbolsAdapter)
                },
                Response.ErrorListener { error -> println(error.message) }
        )
        queue.add(stringRequest)
    }
}
