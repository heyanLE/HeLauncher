package cn.heyan.helauncher

import android.app.Activity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.content.ContentValues.TAG
import android.content.Context
import android.content.pm.ResolveInfo
import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import kotlinx.android.synthetic.main.item.*
import java.util.zip.Inflater


/**
 * MainActivity
 * Created by HeYan on 2018/2/4 0004.
 */

class MainActivity:Activity(){

    val apps:MutableList<App> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WALLPAPER)


        setContentView(R.layout.activity_main)

        val mainIntent = Intent(Intent.ACTION_MAIN, null)
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER)

        //val listN:MutableList<String> = mutableListOf()
        //val listI:MutableList<Int> = mutableListOf()


        val appss = packageManager.queryIntentActivities(mainIntent, 0)

        if (appss != null){
            for(info in appss){
                val app = App()
                app.name = info.loadLabel(packageManager).toString()
                app.iconD = info.loadIcon(packageManager)
                app.packN = info.activityInfo.packageName
                apps.add(app)
            }
        }


        val ada = Adapter(apps,this)
        lv.adapter = ada



        lv.setOnItemClickListener {_, _, i, _ ->

            val int = packageManager.getLaunchIntentForPackage(apps[i].packN)
            startActivity(int)

        }

    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if(intent.action == Intent.ACTION_MAIN){
            closeSystemDialogs()
        }
    }


    fun closeSystemDialogs(){
        window.closeAllPanels()
        val close = Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)
        sendBroadcast(close)
    }



}

class App{

    var name = ""
    var iconD:Drawable? = null
    var packN = ""

}

class Adapter(apps:MutableList<App>,context: Context):BaseAdapter(){

    val inflater = LayoutInflater.from(context)

    val apps = apps

    override fun getCount(): Int {
        return apps.size
    }

    override fun getItem(p0: Int): Any {
        return p0
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {


            val view = inflater.inflate(R.layout.item,null)
            val tv = view.findViewById<TextView>(R.id.tv)
            val im = view.findViewById<ImageView>(R.id.img)
            im.setImageDrawable(apps[p0].iconD)
            tv.text = apps[p0].name
            return view


    }

}

class ViewHolder(tv:TextView,im:ImageView){

    var tv:TextView = tv
    var im:ImageView = im

}
