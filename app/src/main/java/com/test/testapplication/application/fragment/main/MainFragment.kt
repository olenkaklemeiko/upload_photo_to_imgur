package com.test.testapplication.application.fragment.main

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.test.testapplication.R
import com.test.testapplication.application.adapter.PagingAdapter
import com.test.testapplication.application.fragment.links.LinksFragment
import com.test.testapplication.common.viewModel
import com.test.testapplication.data.dto.Image
import com.test.testapplication.databinding.MainFragmentBinding
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.RequestBody
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import java.io.File

class MainFragment : Fragment(), KodeinAware {
    override val kodein: Kodein by closestKodein()
    private val viewModel: MainFragmentViewModel by viewModel()
    private lateinit var pagingAdapter: PagingAdapter
    private var imagesList: ArrayList<Image>? = null
    private var menuItem: Menu? = null

    companion object {
        fun newInstance() =
            MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return MainFragmentBinding.inflate(inflater, container, false).apply {
            viewModel = this@MainFragment.viewModel
            lifecycleOwner = this@MainFragment
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        setHasOptionsMenu(true)

        val diffCallback = object : DiffUtil.ItemCallback<Image>() {
            override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
                return TextUtils.equals(oldItem.image, newItem.image)
            }

            override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
                return TextUtils.equals(oldItem.image, newItem.image)
            }
        }

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        pagingAdapter = PagingAdapter(diffCallback) { clickItem: Image, loadCallback: () -> Unit ->
            sendData(
                clickItem,
                loadCallback
            )
        }
        rv_images.layoutManager = GridLayoutManager(context, 3)
        rv_images.adapter = pagingAdapter
        getAllImagesPath()
    }

    @SuppressLint("CheckResult")
    private fun sendData(item: Image, loadCallback: () -> Unit) {
        val uriImage = Uri.parse(Uri.decode(item.image))
        val pathImage = uriImage?.path
        val file = File(pathImage!!)
        val fileBody = RequestBody.create(MediaType.parse("image/*"), file)
        val nameBody = RequestBody.create(MediaType.parse("text/plain"), file.name)
        viewModel.uploadImages(fileBody, nameBody).subscribe(
            {
                viewModel.saveLink(it)
                CoroutineScope(Dispatchers.Main).launch { loadCallback.invoke() }
                Log.d("image", "Upload image finished")
            },
            {
                val builder = AlertDialog.Builder(activity)
                builder.setTitle("Upload image failed").create().show()
                CoroutineScope(Dispatchers.Main).launch { loadCallback.invoke() }
                Log.d("image", "Upload failed")
            }
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menuItem = menu
        inflater.inflate(R.menu.menu_links, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_link -> {
                activity!!.supportFragmentManager.beginTransaction()
                    .replace(R.id.main_container, LinksFragment.newInstance()).addToBackStack(null)
                    .commit()
            }
        }
        return true
    }

    private fun initToolbar() {
        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity).setSupportActionBar(toolbar)
        }
    }

    private fun getAllImagesPath() {
        viewModel.getAllImages().observe(viewLifecycleOwner, Observer { result ->
            pagingAdapter.submitList(result)
            imagesList?.addAll(result)
        })
    }
}
