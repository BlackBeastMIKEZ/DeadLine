package mah.farmer.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import mah.farmer.fragament.guide.GuideFragamentSix;
import mah.farmer.fragament.guide.GuideFragamentTwo;
import mah.farmer.fragament.guide.GuideFragamentone;
import mah.farmer.fragament.guide.GuideFragmentFive;
import mah.farmer.fragament.guide.GuideFragmentFour;
import mah.farmer.fragament.guide.GuideFragmentThrid;


/**引导页滑动时的适配器
 * Created by 黑色野兽迈特祖 on 2016/4/18.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    private Context ctx;

    //FragmentManager fragment管理器 ,上下文
    public ViewPagerAdapter(FragmentManager fm,Context ctx) {
        super(fm);
        this.ctx = ctx;
    }
    //返回一个fragment
    //arg0 滑动到第几页
    @Override
    public Fragment getItem(int arg0) {
        Fragment mFragment = null;
        if(arg0 == 0){
            mFragment = new GuideFragamentone(ctx);
        }else if(arg0 == 1){
            mFragment = new GuideFragamentTwo(ctx);
        }else if(arg0 == 2){
            mFragment = new GuideFragmentThrid(ctx);
        }
        else if(arg0 == 3){
            mFragment = new GuideFragmentFour(ctx);
        }else if(arg0 == 4){
            mFragment = new GuideFragmentFive(ctx);
        }
        else if(arg0 == 5){
            mFragment = new GuideFragamentSix(ctx);
        }
        return mFragment;
    }

    //返回适配数量
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return 6;
    }
}
