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
         * 重构代码
         * 设置一个map映射：NavHostFragment中fragment不同页面分别对应底部导航栏不同按钮的map映射:
         */
        val destinationMap = mapOf(
            R.id.messageFragment to messageMotionLayout,
            R.id.contactFragment to contactMotionLayout,
            R.id.exploreFragment to exploreMotionLayout,
            R.id.accountFragment to accountMotionLayout
        )

        /**
         * 将底部导航栏按键和Navigation的NavHostFragment导航页面关联起来
         */
        //R.id.fragment就是activity_main.xml文件中添加的NavHostfragment的id
        navController = findNavController(R.id.fragment)
        //修改顶部导航栏，主要是它显示的标题
        setupActionBarWithNavController(
            navController,
            //设置导航条配置
            AppBarConfiguration(destinationMap.keys)
        )
        //messageMotionLayout就是图标layout文件中，MotionLayout的id
        destinationMap.forEach{map->
            map.value.setOnClickListener{ navController.navigate(map.key)}
        }

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
            destinationMap.values.forEach{it.progress = 0f}

            /*
                再根据不同导航页面设置的过渡动画
             */
            destinationMap[destination.id]?.transitionToEnd()
        }

    }
}