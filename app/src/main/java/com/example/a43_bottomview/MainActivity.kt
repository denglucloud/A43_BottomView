package com.example.a43_bottomview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import kotlinx.android.synthetic.main.account_icon_layout.*
import kotlinx.android.synthetic.main.contact_icon_layout.*
import kotlinx.android.synthetic.main.explore_icon_layout.*
import kotlinx.android.synthetic.main.message_icon_layout.*

class MainActivity : AppCompatActivity() {

    private lateinit var  navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        /**
         * 将底部导航栏按键和Navigation的NavHostFragment导航页面关联起来
         */
        //R.id.fragment就是activity_main.xml文件中添加的NavHostfragment的id
        navController = findNavController(R.id.fragment)
        //修改顶部导航栏，主要是它显示的标题
        setupActionBarWithNavController(
            navController,
            //设置导航条配置
            AppBarConfiguration(setOf(R.id.messageFragment,R.id.contactFragment,R.id.exploreFragment,R.id.accountFragment))
        )
        //messageMotionLayout就是图标layout文件中，MotionLayout的id
        messageMotionLayout.setOnClickListener{navController.navigate(R.id.messageFragment)}
        contactMotionLayout.setOnClickListener{navController.navigate(R.id.contactFragment)}
        exploreMotionLayout.setOnClickListener{navController.navigate(R.id.exploreFragment)}
        accountMotionLayout.setOnClickListener{navController.navigate(R.id.accountFragment)}

        /**
         * 让底部导航栏添加显示过渡动画
         * 根据不同的导航页面触发对应不同的底部导航栏按键的过渡动画
         */
        navController.addOnDestinationChangedListener{controller,destination,argument ->
            /*
                取消系统自带的回弹设置
                使系统默认的按下三大金刚的back键回弹原来的操作，就是按下back键不退APP，而是像一步步撤销
             */
            controller.popBackStack()


            /*
                先使过渡动画默认归0
             */
            messageMotionLayout.progress = 0f
            contactMotionLayout.progress = 0f
            exploreMotionLayout.progress = 0f
            accountMotionLayout.progress = 0f

            /*
                再根据不同导航页面设置的过渡动画
             */
            when(destination.id){
                R.id.messageFragment -> messageMotionLayout.transitionToEnd()
                R.id.contactFragment -> contactMotionLayout.transitionToEnd()
                R.id.exploreFragment -> exploreMotionLayout.transitionToEnd()
                R.id.accountFragment -> accountMotionLayout.transitionToEnd()
            }
        }

    }
}