package com.example.mytreningwork.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.animation.AnimationUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mytreningwork.Func.currentUser
import com.example.mytreningwork.R
import com.example.mytreningwork.ViewModel.ViewModelHomeFragment
import com.example.mytreningwork.model.changePhotoCropper
import kotlinx.android.synthetic.main.fragment_home.*

class FragmentHome : Fragment(R.layout.fragment_home)
{
    private lateinit var mProvider : ViewModelHomeFragment

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        mProvider = ViewModelProvider(this).get(ViewModelHomeFragment::class.java)
    }

    override fun onStart()
    {
        super.onStart()
        initFields()
        mAnim()
        mProvider.loadBoardPushUps.observe(this , Observer {//наблюдатель для доски отжиманий
            //home_day_count.text = it.size.toString()
            val resPushUps = it.map { map->map.loop.toInt()}
            home_board_push_one_day.text = resPushUps.max().toString()//самое большое кол-во отжиманий за день
            home_board_push_all_time.text = resPushUps.sum().toString()//сумма всех отжиманий
            home_board_push_middle_res.text = resPushUps.average().toInt().toString()//среднее значение
        })

        mProvider.loadBoardPullUps.observe(this , Observer {//подтягивания
            val resPullUps = it.map { map -> map.loop.toInt()}
            home_board_pull_ups_all_time.text = resPullUps.sum().toString()
            home_board_pull_ups_one_day.text = resPullUps.max().toString()
            home_board_pull_ups_middle_res.text = resPullUps.average().toInt().toString()
        })

        mProvider.loadBoard.observe(this, Observer {
            home_score_user.text = it.USER_SCORE
        })

        home_btn_start_board.setOnClickListener {//открытия списка board
            parentFragmentManager.beginTransaction().addToBackStack(null)
                .replace(R.id.MainContainer, FragmentBoard()).commit()
        }
    }

    //TODO ПЕРЕДЕЛАТЬ UI GRIDLAYOUT

    private fun initFields()
    {
        home_nick.text = currentUser.USER_NICK
        home_user_photo.changePhotoCropper(currentUser.USER_PHOTO.toString())
    }

    private fun mAnim()
    {
        home_card_day.animation = AnimationUtils.loadAnimation(this.context, R.anim.anim_card_res)
        home_card_score.animation = AnimationUtils.loadAnimation(this.context , R.anim.anim_card_score)
        home_card_dash_board.animation = AnimationUtils.loadAnimation(this.context , R.anim.anim_card_dash_board)
        home_card_push_ups.animation = AnimationUtils.loadAnimation(this.context , R.anim.anim_card_home)
        home_card_pull_ups.animation = AnimationUtils.loadAnimation(this.context , R.anim.anim_card_home_pull_ups)
    }
}