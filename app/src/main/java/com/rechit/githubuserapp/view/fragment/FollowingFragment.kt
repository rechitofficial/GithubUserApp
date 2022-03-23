package com.rechit.githubuserapp.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rechit.githubuserapp.*
import com.rechit.githubuserapp.databinding.FragmentFollowingBinding

class FollowingFragment : Fragment() {

    companion object{
        val TAG = FollowingFragment::class.java.simpleName
        const val EXTRA_DETAIL = "extra_detail"
    }

    private val listData: ArrayList<Following> = ArrayList()
    private lateinit var listFollowingAdapter: ListFollowingAdapter

    private lateinit var followingViewModel: FollowingViewModel //by requireActivity().viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentFollowingBinding.bind(view)

        followingViewModel = ViewModelProvider(this)[FollowingViewModel::class.java]
        listFollowingAdapter = ListFollowingAdapter(listData)
        val dataUser = requireActivity().intent.getParcelableExtra<User>(EXTRA_DETAIL) as User

        binding.rvFollowing.layoutManager = LinearLayoutManager(activity)
        binding.rvFollowing.setHasFixedSize(true)
        binding.rvFollowing.adapter = listFollowingAdapter

        followingViewModel.getDataFromGithubApi(requireActivity().applicationContext, dataUser.user_name)
        binding.progressBar3.visibility = View.VISIBLE

        followingViewModel.getListFollowing().observe(requireActivity()) { listFollowing ->
            if (listFollowing != null) {
                listFollowingAdapter.setData(listFollowing)
                binding.progressBar3.visibility = View.INVISIBLE
            }
        }
    }
}