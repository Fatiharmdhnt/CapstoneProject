package com.capstone.herbalease.view.fitur.diskusi

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.herbalease.R
import com.capstone.herbalease.data.model.ForumDiscussion
import com.capstone.herbalease.databinding.FragmentDiscussionBinding
import com.capstone.herbalease.databinding.KeywordItemBinding
import com.capstone.herbalease.view.adapter.DiscussionAdapter
import com.capstone.herbalease.view.fitur.diskusi.detail.DetailDiscussionActivity
import com.capstone.herbalease.view.fitur.diskusi.post.AddDicussionActivity

class DiscussionFragment : Fragment() {
    private lateinit var binding: FragmentDiscussionBinding
    private lateinit var viewModel: DiscussionViewModel
    private lateinit var adapter: DiscussionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DiscussionViewModel::class.java)
        adapter = DiscussionAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDiscussionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Set List Discussion
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
        setListDiscussion()
        //End Set List Discussion

        binding.addDiscussion.setOnClickListener {
            startActivity(Intent(requireContext(), AddDicussionActivity::class.java))
        }
    }

    private fun setListDiscussion(){
        viewModel.setDiscussion()

        adapter.setOnItemClickCallback(object : DiscussionAdapter.OnItemClickListener{
            override fun onItemClick(data: ForumDiscussion) {
                val intent = Intent(requireContext(), DetailDiscussionActivity::class.java)
                intent.putExtra(DetailDiscussionActivity.EXTRA_DISCUSSION, data)
                startActivity(intent)
            }
        })
        viewModel.listDiscussion.observe(viewLifecycleOwner, Observer{
            adapter.setListDiscussion(it)
        })
    }
}