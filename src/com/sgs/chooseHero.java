package com.sgs;

import android.app.ListActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.Chronometer;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import com.sgs.hero;

public class chooseHero extends ListActivity{
    /** Called when the activity is first created. */

	AlertDialog choice;//选择英雄跳出的介绍框
	TextView deadTime;//显示剩余时间
	TextView aiChoice;//显示电脑的随机选择
	Chronometer counter;//计时器	
	final int timeLength=30;//倒计时秒数
	int lTime=timeLength;
	
	
	public final hero[] heros={ 
		new hero(1,"曹操",1,1,4,"1、奸雄：你可以立即获得对" +
				"你造成伤害的牌",R.drawable.caocao_icon,R.drawable.caocao_bust),
		new hero(2,"甄姬",0,1,3,"1、倾国：你可以将你的黑色手牌当" +
				"【闪】使用（或打出）\n2、洛神：回合开始阶段，你可以" +
				"进行判定：若为黑色，立即获得此生效后的判定牌，并可" +
				"以再次使用洛神――如此反复，直到出现红色或你不愿意判定" +
				"了为止。★使用倾国时，仅改变牌的类别（名称）和作用，" +
				"而牌的花色和点数不变。",R.drawable.zhenji_icon,R.drawable.zhenji_bust),
		new hero(3,"许褚",1,1,4,
				"裸衣：摸牌阶段，你可以少摸一张牌；" +
				"若如此做，该回合的出牌阶段，你使用【杀】" +
				"或【决斗】（你为伤害来源时）造成的伤害+1",R.drawable.xuchu_icon,R.drawable.xuchu_bust),
		
		new hero(4,"黄盖",1,2,4,"1、苦肉：出牌阶段，你可以失去一点体力，然后摸两张牌" +
				"每回合中，你可以多次使用苦肉。",R.drawable.huanggai_icon,R.drawable.huanggai_bust),
		new hero(5,"陆逊",1,2,3,"1、谦逊：你不能成为【顺手牵羊】和【乐不思蜀】的目标" +
				"/n2、连营：每当你失去最后一张牌时，可立即摸一张牌。" 
				,R.drawable.luxun_icon,R.drawable.luxun_bust),
		new hero(6,"甘宁",1,2,4,"奇袭：出牌阶段，你可以将仍和一张黑桃或梅花手牌当【过河拆桥】使用。",R.drawable.ganning_icon,R.drawable.ganning_bust),
		
		new hero(7,"赵云",1,3,4,"龙胆：你可以将你手牌的【杀】当【闪】、【闪】当" +
				"【杀】使用或打出。★使用龙胆时，仅改变牌的类别(名称)和作用，" +
				"而牌的花色和点数不变",R.drawable.zhaoyun_icon,R.drawable.zhaoyun_bust),
		new hero(8,"黄月英",0,3,3,"1、集智：每当你使用一张非延时类锦囊时，（在它结算之前）" +
				"你可以立即摸一张牌\n2、奇才：你使用任何锦囊牌无距离限制。",R.drawable.huangyueying_icon,R.drawable.huangyueying_bust),
		new hero(9,"关羽",1,3,4,"武圣：你可以将你的任意一张红色牌当【杀】使用或打出。" +
				"★若同时用当前装备的红色装备效果时，不可把这张装备牌当【杀】来使用或" +
				"打出。★使用武圣时，仅改变牌的类别(名称)和作用，而牌的花色和点数不变。",R.drawable.guanyu_icon,R.drawable.guanyu_bust),
		new hero(10,"华佗",1,0,3,"1、急救：你的回合外，你可以将你的任意红桃或方块牌当做【桃】来使用\n" +
				"2、青囊：出牌阶段，你可以主动弃掉一张手牌，令任一角色回复1点体力，每回合限用一次。",R.drawable.huatuo_icon,R.drawable.huatuo_bust),
	};
	
	private hero humanPlayer=null;//玩家角色
	private hero aiPlayer=null;//AI角色
	
	
	public void onCreate(Bundle icicle) {
	super.onCreate(icicle);
	setContentView(R.layout.choose_hero);
	setTitle("选英雄");

	aiPlayer=new hero(heros[(int)(Math.random()*10)%10].getThis());
	
	setListAdapter(new IconicAdapter());//使用Class IconicAdapter具体化每一行英雄界面
	
	
	//计时器
	deadTime = (TextView) findViewById(R.id.deadTime);
	aiChoice = (TextView) findViewById(R.id.aiChoice);
	counter= (Chronometer) findViewById(R.id.chronometer);	
	counter.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener(){
		
		
        public void onChronometerTick(Chronometer count) {
			
			deadTime.setText("剩余时间："+String.valueOf(lTime));
			lTime=lTime-1;
			if(lTime==timeLength/2)
			{
				aiChoice.setText("AI英雄："+aiPlayer.getHeroName());
			}
           
            if(lTime<=0) {
            	
            	counter.stop();//停止计时

            	            	  	
            	humanPlayer=new hero(heros[(int)(Math.random()*10)%10].getThis());//玩家被迫随机选择英雄
            	
            	Intent intent=new Intent(chooseHero.this,finalDecision.class);
            	Bundle bundle=new Bundle();
            	bundle.putParcelable("aiPlayer", aiPlayer);
            	bundle.putParcelable("humanPlayer",humanPlayer);
            	intent.putExtras(bundle);
            	startActivity(intent);
            	chooseHero.this.finish();
            	
            }
        }
    });
	counter.setBase(SystemClock.elapsedRealtime());
    counter.start();
	}
	
	//每一行的响应
	public void onListItemClick(ListView parent, View v, int position,
			long id) {
		
		final int pos=position;//获得选择的英雄的序号，供最后确定英雄用
		
		//进入英雄介绍
		choice=new AlertDialog.Builder(this).create();
		choice.setTitle(heros[position].getHeroName());
		choice.setMessage(""+heros[position].getHeroName()+"\n势力："+heros[position].getHeroSort()
				+"\n血量："+heros[position].getBloodLength()+"\n"+heros[position].getHeroIntro());
		choice.setIcon(this.getResources().getDrawable(heros[position].getHeroIcon()));
		choice.setButton("取消",new DialogInterface.OnClickListener() {
			
			
			
			public void onClick(DialogInterface dialog, int which) {
				// 取消即返回
				
			}
		});
		choice.setButton2("选择",new DialogInterface.OnClickListener() {
			
						
			
			public void onClick(DialogInterface dialog, int which) {
				
				humanPlayer=new hero(heros[pos]);//确定玩家的英雄
				
				Intent intent = new Intent(chooseHero.this, finalDecision.class);//跳转到下一界面
				Bundle bundle=new Bundle();		
            	bundle.putParcelable("aiPlayer", aiPlayer);
            	bundle.putParcelable("humanPlayer",humanPlayer);   	
            	intent.putExtras(bundle);
				startActivity(intent);
				chooseHero.this.finish();//关闭本界面
			}
		});
		choice.show();
		
			}
	
	
	//具体化每一行英雄界面
	class IconicAdapter extends ArrayAdapter{
		IconicAdapter(){
			super(chooseHero.this,R.layout.choose_hero_row,heros);
		}
		public View getView(int position, View convertView,
				ViewGroup parent) {
				LayoutInflater inflater=getLayoutInflater();
				View row=inflater.inflate(R.layout.choose_hero_row, parent, false);
				TextView name=(TextView)row.findViewById(R.id.name);
				name.setText(heros[position].getHeroName());
				ImageView icon=(ImageView)row.findViewById(R.id.icon);
				icon.setImageDrawable(heros[position].getHeroSortIcon(row .getContext()));
				return(row);
				}
	}
	
	
	public void onBackPressed() //重写键盘的back按钮
	{
		
		AlertDialog whereToGo=new AlertDialog.Builder(deadTime.getContext()).create();
		whereToGo.setButton("返回游戏",new DialogInterface.OnClickListener() {
			
			
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		whereToGo.setButton2("主菜单",new DialogInterface.OnClickListener() {
			
			
			public void onClick(DialogInterface dialog, int which) {
				Intent intent=new Intent(chooseHero.this,welcome.class);
				startActivity(intent);
				chooseHero.this.finish();
			}
		});
		whereToGo.setButton3("退出游戏",new DialogInterface.OnClickListener() {
			
			
			public void onClick(DialogInterface dialog, int which) {
				chooseHero.this.finish();	
			}
		});
		whereToGo.show();
		
		return;
	}
	
	

}