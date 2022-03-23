package com.rechit.githubuserapp.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rechit.githubuserapp.*
import com.rechit.githubuserapp.databinding.FragmentFollowerBinding

class FollowerFragment : Fragment() {

    companion object{
        val TAG = FollowerFragment::class.java.simpleName
        const val EXTRA_DETAIL = "extra_detail"
    }

    private val listData: ArrayList<Follower> = ArrayList()
    private lateinit var listFollowerAdapter: ListFollowerAdapter

    private lateinit var followerViewModel: FollowerViewModel //by requireActivity().viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_follower, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentFollowerBinding.bind(view)

        followerViewModel = ViewModelProvider(this)[FollowerViewModel::class.java]

        listFollowerAdapter = ListFollowerAdapter(listData)

        val dataUser = requireActivity().intent.getParcelableExtra<User>(EXTRA_DETAIL) as User

        binding.rvFollower.layoutManager = LinearLayoutManager(activity)
        binding.rvFollower.setHasFixedSize(true)
        binding.rvFollower.adapter = listFollowerAdapter

        followerViewModel.getDataFromGithubApi(requireActivity().applicationContext, dataUser.user_name)
        binding.progressBar2.visibility = View.VISIBLE

        followerViewModel.getListFollower().observe(requireActivity()) { listFollower ->
            if (listFollower != null) {
                listFollowerAdapter.setData(listFollower)
                binding.progressBar2.visibility = View.INVISIBLE
            }
        }


    }



}