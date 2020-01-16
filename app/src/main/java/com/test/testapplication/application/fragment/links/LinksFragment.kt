package com.test.testapplication.application.fragment.links

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.test.testapplication.application.adapter.LinksAdapter
import com.test.testapplication.common.viewModel
import com.test.testapplication.databinding.LinksFragmentBinding
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein


class LinksFragment : Fragment(), KodeinAware {

    override val kodein: Kodein by closestKodein()
    private val viewModel: LinksFragmentViewModel by viewModel()
    private lateinit var linksAdapter: LinksAdapter

    companion object {
        fun newInstance() = LinksFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LinksFragmentBinding.inflate(inflater, container, false).apply {
            viewModel = this@LinksFragment.viewModel
            linksAdapter = LinksAdapter { itemClick: String -> openLink(itemClick) }
            adapter = this@LinksFragment.linksAdapter
            lifecycleOwner = this@LinksFragment
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
    }

    private fun openLink(itemClick: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(itemClick)))
    }

    private fun getData() {
        viewModel.getLinksList().observe(viewLifecycleOwner, Observer {
            linksAdapter.updateData(it)
        })
    }
}
