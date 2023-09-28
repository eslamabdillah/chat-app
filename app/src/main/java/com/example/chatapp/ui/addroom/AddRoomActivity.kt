package com.example.chatapp.ui.addroom

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.chatapp.R
import com.example.chatapp.databinding.ActivityAddRoomBinding
import com.example.chatapp.ui.home.HomeActivity
import com.example.chatapp.ui.showMessage

class AddRoomActivity : AppCompatActivity() {
    lateinit var viewBinding: ActivityAddRoomBinding
    val viewModel: AddRoomViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_room)
        initView()
        initObserves()
    }

    private fun initObserves() {
        viewModel.messageLiveData.observe(this, {
            showMessage(
                message = it.message ?: "",
                posActionName = it.posActionName,
                postAction = it.onPosActionClick,
                negActionName = it.negActionName,
                negAction = it.onNegActionClick,
                isCancelable = it.isCancelable
            )

        })
        viewModel.events.observe(this, {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        })
    }


    lateinit var categoryAdapter: RoomCategoriesAdapter
    private fun initView() {
        categoryAdapter = RoomCategoriesAdapter(viewModel.categories)
        viewBinding.vm = viewModel
        viewBinding.lifecycleOwner = this
        viewBinding.contentAddroom.spinner.adapter = categoryAdapter
        viewBinding.contentAddroom.spinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    itemView: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.onCategorySelect(position)
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

            }
    }
}