package com.tab1_deadline_list.deadline;

import android.widget.TextView;

public class DeadlineListData {

	private String tab_deadline_list_item1;//�¼�����
	private String tab_deadline_list_item2;//Ŀ����
	private String deadline_day;           //��������
	public DeadlineListData(String tab_deadline_list_item1,
		String tab_deadline_list_item2, String deadline_day) {
		super();
		this.tab_deadline_list_item1 = tab_deadline_list_item1;
		this.tab_deadline_list_item2 = tab_deadline_list_item2;
		this.deadline_day = deadline_day;
	}
	public String getTab_deadline_list_item1() {
		return tab_deadline_list_item1;
	}
	public void setTab_deadline_list_item1(String tab_deadline_list_item1) {
		this.tab_deadline_list_item1 = tab_deadline_list_item1;
	}
	public String getTab_deadline_list_item2() {
		return tab_deadline_list_item2;
	}
	public void setTab_deadline_list_item2(String tab_deadline_list_item2) {
		this.tab_deadline_list_item2 = tab_deadline_list_item2;
	}
	public String getDeadline_day() {
		return deadline_day;
	}
	public void setDeadline_day(String deadline_day) {
		this.deadline_day = deadline_day;
	}
	
}
