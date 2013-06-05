package com.sgs;

import java.lang.String;

import android.os.Parcel;
import android.os.Parcelable;
import android.content.Context;
import android.graphics.drawable.Drawable;



public class hero implements Parcelable{
	
	private int heroNumber=0;//英雄的编号
	private String heroName=null;//英雄的名字
	private int heroSort=0;//英雄所属的势力
	private int bloodLength=0;//英雄初始血量
	private String heroIntro=null;//英雄介绍
	private int heroIcon=0;//英雄的小图标
	private int heroBust=0;//英雄的头像
	private int heroSex=1;//1为男,0为女

	
	hero(int num,String hN,int sex,int hS,int bL,String hI,int icon,int bust){
		heroNumber=num;
		heroName=hN;
		bloodLength=bL;
		heroIntro=hI;
		heroSort=hS;
		heroIcon=icon;
		heroBust=bust;
		heroSex=sex;
		
	}
	hero(hero h)
	{
		heroNumber=h.getHeroNumber();
		heroName=h.getHeroName();
		bloodLength=h.getBloodLength();
		heroIntro=h.getHeroIntro();
		heroSort=h.getHeroSort();
		heroIcon=h.getHeroIcon();
		heroBust=h.getHeroBust();
		heroSex=h.getHeroSex();
	}
	
	hero()
	{
		
	}
	
	public hero getThis()
	{
		return this;
	}
	public int getHeroNumber() 
	{
		return heroNumber;
	}
	public String getHeroName()
	{
		return heroName;
	}

	public int getBloodLength()
	{
		return bloodLength;
	}
	public String getHeroIntro()
	{
		return heroIntro;
	}
	public char getHeroSort()
	{
		switch(heroSort){
		case 1:
			return '魏';
		case 2:
			return '吴';
		case 3:
			return '蜀';
		case 0:
			return '群';
			default:
				return 'e';
		}
	}
	public int getHeroIcon()
	{
		return heroIcon;
	}
	public int getHeroBust()
	{
		return heroBust;
	}
	
	public int getHeroSex() {
		return heroSex;
	}
	
	public Drawable getHeroSortIcon(Context con)
	{
		switch(heroSort){
		case 1://魏
			return con.getResources().getDrawable(R.drawable.wei);
		case 2://吴
			return con.getResources().getDrawable(R.drawable.wu);
		case 3://蜀
			return con.getResources().getDrawable(R.drawable.shu);
		case 0://群
			return con.getResources().getDrawable(R.drawable.qun);
		}
		return con.getResources().getDrawable(R.drawable.icon);
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel p, int arg1) {
		p.writeInt(heroNumber);
		p.writeString(heroName);
		p.writeInt(heroSort);
		p.writeInt(heroSex);
		p.writeInt(bloodLength);
		p.writeString(heroIntro);
		p.writeInt(heroIcon);
		p.writeInt(heroBust);
	}
		public static final Parcelable.Creator<hero> CREATOR = new Creator<hero>()
		{
			public hero createFromParcel(Parcel in)
			{
				hero h=new hero();
				h.heroNumber=in.readInt();
				h.heroName=in.readString();
				h.heroSort=in.readInt();
				h.heroSex=in.readInt();
				h.bloodLength=in.readInt();
				h.heroIntro=in.readString();
				h.heroIcon=in.readInt();
				h.heroBust=in.readInt();
				return h;
			}

			@Override
			public hero[] newArray(int size) {
				
				return new hero[size];
			}
		};
	
}