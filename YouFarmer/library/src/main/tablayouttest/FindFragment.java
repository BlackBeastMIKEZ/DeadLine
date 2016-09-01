package com.example.tablayouttest;

import android.os.Bundle;  
import android.support.design.widget.TabLayout;  
import android.support.v4.app.Fragment;  
import android.support.v4.app.FragmentPagerAdapter;  
import android.support.v4.view.ViewPager;  
import android.view.LayoutInflater;  
import android.view.View;  
import android.view.ViewGroup;  
  
import com.example.tablayouttest.Find_tab_Adapter;  
  
import java.util.ArrayList;  
import java.util.List;  
  
  
/** 
 * ����ҳ�� 
 */  
public class FindFragment extends Fragment {  
  
    private TabLayout tab_FindFragment_title;                            //����TabLayout  
    private ViewPager vp_FindFragment_pager;                             //����viewPager  
    private FragmentPagerAdapter fAdapter;                               //����adapter  
  
    private List<Fragment> list_fragment;                                //����Ҫװfragment���б�  
    private List<String> list_title;                                     //tab�����б�  
  
    private Find_hotRecommendFragment hotRecommendFragment;              //�����Ƽ�fragment  
    private Find_hotCollectionFragment hotCollectionFragment;            //�����ղ�fragment  
    private Find_hotMonthFragment hotMonthFragment;                      //�����Ȱ�fragment  
    private Find_hotToday hotToday;                                      //�����Ȱ�fragment  
  
    @Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
                             Bundle savedInstanceState) {  
        View view = inflater.inflate(R.layout.fragment_find, container, false);  
  
        initControls(view);  
  
        return view;  
    }  
  
    /** 
     * ��ʼ�����ؼ� 
     * @param view 
     */  
    private void initControls(View view) {  
  
        tab_FindFragment_title = (TabLayout)view.findViewById(R.id.tab_FindFragment_title);  
        vp_FindFragment_pager = (ViewPager)view.findViewById(R.id.vp_FindFragment_pager);  
  
        //��ʼ����fragment  
        hotRecommendFragment = new Find_hotRecommendFragment();  
        hotCollectionFragment = new Find_hotCollectionFragment();  
        hotMonthFragment = new Find_hotMonthFragment();  
        hotToday = new Find_hotToday();  
  
        //��fragmentװ���б���  
        list_fragment = new ArrayList<>();  
        list_fragment.add(hotRecommendFragment);  
        list_fragment.add(hotCollectionFragment);  
        list_fragment.add(hotMonthFragment);  
        list_fragment.add(hotToday);  
  
        //�����Ƽ���tab�����б���������£�����Ӧ����values/arrays.xml�н��ж���Ȼ�����  
        list_title = new ArrayList<>();  
        list_title.add("�����Ƽ�");  
        list_title.add("�����ղ�");  
        list_title.add("�����Ȱ�");  
        list_title.add("�����Ȱ�");  
  
        //����TabLayout��ģʽ  
        tab_FindFragment_title.setTabMode(TabLayout.MODE_FIXED);  
        //ΪTabLayout���tab����  
        tab_FindFragment_title.addTab(tab_FindFragment_title.newTab().setText(list_title.get(0)));  
        tab_FindFragment_title.addTab(tab_FindFragment_title.newTab().setText(list_title.get(1)));  
        tab_FindFragment_title.addTab(tab_FindFragment_title.newTab().setText(list_title.get(2)));  
        tab_FindFragment_title.addTab(tab_FindFragment_title.newTab().setText(list_title.get(3)));  
  
        fAdapter = new Find_tab_Adapter(getActivity().getSupportFragmentManager(),list_fragment,list_title);  
  
        //viewpager����adapter  
        vp_FindFragment_pager.setAdapter(fAdapter);  
        //tab_FindFragment_title.setViewPager(vp_FindFragment_pager);  
        //TabLayout����viewpager  
        tab_FindFragment_title.setupWithViewPager(vp_FindFragment_pager);  
        //tab_FindFragment_title.set  
    }  
  
  
}  