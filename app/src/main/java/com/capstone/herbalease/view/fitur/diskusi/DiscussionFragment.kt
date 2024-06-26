package com.capstone.herbalease.view.fitur.diskusi

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.herbalease.data.pref.ForumDiscussion
import com.capstone.herbalease.databinding.FragmentDiscussionBinding
import com.capstone.herbalease.view.ViewModelFactory
import com.capstone.herbalease.view.adapter.DiscussionAdapter
import com.capstone.herbalease.view.fitur.diskusi.detail.DetailDiscussionActivity
import com.capstone.herbalease.view.fitur.diskusi.post.AddDicussionActivity

class DiscussionFragment : Fragment() {
    private lateinit var binding: FragmentDiscussionBinding
    private  val viewModel by viewModels<DiscussionViewModel> {
        ViewModelFactory(requireContext())
    }
    private lateinit var adapter: DiscussionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
            startActivityForResult(
                Intent(requireContext(), AddDicussionActivity::class.java),
                ADD_DISCUSSION_REQUEST_CODE
            )
        }

        binding.filteredSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(!query.isNullOrBlank()){
                    viewModel.searchDiscussion(query)
                    viewModel.listDiscussion.observe(viewLifecycleOwner, Observer {
                        if (it != null) {
                            adapter.setListDiscussion(it)
                        }
                    })
                } else if (query == ""){
                    viewModel.setDiscussion()
                    viewModel.listDiscussion.observe(viewLifecycleOwner, Observer {
                        if (it != null) {
                            adapter.setListDiscussion(it)
                        }
                    })
                }
                return true
            }


            override fun onQueryTextChange(newText: String?): Boolean {
                if(!newText.isNullOrBlank()){
                    viewModel.searchDiscussion(newText)
                    viewModel.listDiscussion.observe(viewLifecycleOwner, Observer {
                        if (it != null) {
                            adapter.setListDiscussion(it)
                        }
                    })
                } else if (newText == ""){
                    viewModel.setDiscussion()
                    viewModel.listDiscussion.observe(viewLifecycleOwner, Observer {
                        if (it != null) {
                            adapter.setListDiscussion(it)
                        }
                    })
                }
                return true
            }


        })
    }



    private fun setListDiscussion(){
        viewModel.getSession()
        viewModel.userSession.observe(requireActivity(), Observer {user ->
            if (user != null){
                viewModel.setDiscussion()
            }
        })

        adapter.setOnItemClickCallback(object : DiscussionAdapter.OnItemClickListener{
            override fun onItemClick(data: ForumDiscussion) {
                val intent = Intent(requireContext(), DetailDiscussionActivity::class.java)
                intent.putExtra(DetailDiscussionActivity.EXTRA_DISCUSSION, data)
                startActivity(intent)
            }
        })
        viewModel.listDiscussion.observe(viewLifecycleOwner, Observer{
            if (it != null) {
                adapter.setListDiscussion(it)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_DISCUSSION_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            viewModel.setDiscussion()
        }
    }

    companion object {
        private const val ADD_DISCUSSION_REQUEST_CODE = 1
    }
}