package bitsindri.hncc.collegeapp.Adapters;

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import bitsindri.hncc.collegeapp.R
import bitsindri.hncc.collegeapp.fragments.*
import com.anjali.myapplication.fragments.LoginTabFrament
import com.anjali.myapplication.fragments.SignupTabFrament
import kotlinx.android.synthetic.main.dialog.view.*

class LoginAdapter(val fm:FragmentManager, var context: Context, val totalTabs:Int):FragmentPagerAdapter(fm) {

  lateinit var tab: Fragment

    override fun getCount(): Int {
      return totalTabs
    }

    override fun getItem(position: Int): Fragment {
        when{
            position==0->{
                 tab = LoginTabFrament()
                return tab
            }
            position==1->{
                tab=SignupTabFrament()
                return tab
            }
        }
        return tab
    }
}