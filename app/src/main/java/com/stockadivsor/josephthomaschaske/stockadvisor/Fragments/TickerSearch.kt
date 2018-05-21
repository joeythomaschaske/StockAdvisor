package com.stockadivsor.josephthomaschaske.stockadvisor.Fragments


import android.os.Bundle
import android.app.Fragment
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import com.android.volley.Request
import com.android.volley.RequestQueue;
import com.android.volley.Request.Method.GET;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.stockadivsor.josephthomaschaske.stockadvisor.R
import com.stockadivsor.josephthomaschaske.stockadvisor.Constants
import com.stockadivsor.josephthomaschaske.stockadvisor.IEXDataModels.IEXSymbols
import java.lang.reflect.Type


class TickerSearch : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        var fragment: View = inflater.inflate(R.layout.fragment_ticker_search, container, false)
        val queue = Volley.newRequestQueue(fragment.context)
        val stringRequest = StringRequest(Request.Method.GET, Constants.IEX_SYMBOLS_ENDPOINT,
                Response.Listener<String> { response ->
                    var type: Type = object : TypeToken<MutableList<IEXSymbols>>() {}.type
                    var symbols: MutableList<IEXSymbols> = Gson().fromJson(response, type)
                    var symbolsAdapter: ArrayAdapter<String> = ArrayAdapter(fragment.context, android.R.layout.simple_dropdown_item_1line, assembleSymbolsAdapter(symbols))
                    var typeAhead: AutoCompleteTextView = fragment.findViewById(R.id.symbolAutoComplete)
                    typeAhead.setAdapter(symbolsAdapter)

                },
                Response.ErrorListener { error -> println(error.message) }
        )
        queue.add(stringRequest)
        return fragment
    }

    fun assembleSymbolsAdapter(symbols: MutableList<IEXSymbols>): Array<String> {
        return symbols.map {
            it.symbol
        }.filterNotNull().toTypedArray()
    }
}
