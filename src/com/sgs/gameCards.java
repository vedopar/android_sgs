package com.sgs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.DialogInterface.OnClickListener;

public class gameCards{
	gameCards(Activity ac){
		
		usedCardNum=0;
		chosenCard=0;
		humanAttackRange=1;
	    aiAttackRange=1;
	    humanHorseRange=0;
	    aiHorseRange=0;
	    isSelect=false;
		isRemove=false;
		isAiHeroOn=false;
		isHumanHeroOn=false;
		isWeaponOn=false;
		humanEquipCards=new int[4];
		aiEquipCards=new int[4];
		for(int i=0;i<4;i++){
			humanEquipCards[i]=-1;
			aiEquipCards[i]=-1;
		}
		validCards = new ArrayList<Integer>();
		usedCards = new ArrayList<Integer>();
		humanPlayerCards = new ArrayList<Integer>();
		aiPlayerCards = new ArrayList<Integer>();
		selectCards=new ArrayList<Integer>();
		removeCards = new ArrayList<Integer>();
		cardLayout = new LinearLayout[cards.length];
		gameAc=ac;
		cardLine=(LinearLayout)gameAc.findViewById(R.id.layout_card_line);
		humanShowCardField = (LinearLayout)gameAc.findViewById(R.id.layout_me_card);
		aiShowCardField = (LinearLayout)gameAc.findViewById(R.id.layout_enemy_card);
		layoutAnimation=(LinearLayout)gameAc.findViewById(R.id.layout_animation);
		judgeCard=(LinearLayout)gameAc.findViewById(R.id.judge_card);
		shuffle(true);
		
		//��ʼ����������ƥ
		aiPlayerEquip=new TextView[4];
		humanPlayerEquip=new TextView[4];
		aiPlayerEquip[0] = (TextView)ac.findViewById(R.id.TextView_enemy_weapon_1);
		aiPlayerEquip[1] = (TextView)ac.findViewById(R.id.TextView_enemy_weapon_2);
		aiPlayerEquip[2] = (TextView)ac.findViewById(R.id.TextView_enemy_weapon_3);
		aiPlayerEquip[3] = (TextView)ac.findViewById(R.id.TextView_enemy_weapon_4);
		humanPlayerEquip[0] = (TextView)ac.findViewById(R.id.TextView_me_weapon_1);
		humanPlayerEquip[1] = (TextView)ac.findViewById(R.id.TextView_me_weapon_2);
		humanPlayerEquip[2] = (TextView)ac.findViewById(R.id.TextView_me_weapon_3);
		humanPlayerEquip[3] = (TextView)ac.findViewById(R.id.TextView_me_weapon_4);
	}
	
    int chosenCard;//��¼���ѡ����ʲô��
    int usedCardNum;//��¼ȡ����
    private boolean isRemove;//�Ƿ������ƽ׶�
    boolean isWeaponOn;//�Ƿ�����������
    boolean isAiHeroOn;//�жϵ����Ƿ�ʹ��Ӣ�ۼ���
    boolean isHumanHeroOn;//�ж�����Ƿ�ʹ��Ӣ�ۼ���
    boolean isSelect;//�Ƿ���cardsForSelectionִ����
    List<Integer> selectCards;//select�Ľ���
    int humanAttackRange;//��ҹ�����Χ
    int aiAttackRange;//AI������Χ
    int humanHorseRange;//�����ķ�Χ
    int aiHorseRange;//AI��ķ�Χ
    int [] humanEquipCards;//������ҵ�װ����
    int [] aiEquipCards;//������Ե�װ����
    TextView [] aiPlayerEquip;  //����װ�������ϵ���Ϊ1��4
	TextView [] humanPlayerEquip;     //������������ϵ���Ϊ1��4 
    public List<Integer> validCards;//�ƶ�
    public List<Integer> usedCards;//�ù�����
    public List<Integer> humanPlayerCards;//������е���
    public List<Integer> aiPlayerCards;//�������е���
    public List<Integer> removeCards;//������������
    private Activity gameAc;//�����Ϸ�����Context����
    public LinearLayout cardLine;//��÷������ƵĽ���
    public LinearLayout [] cardLayout;//��÷ŵ����ƵĽ���
    public LinearLayout humanShowCardField;//������������ʾ�ƵĽ���
    public LinearLayout aiShowCardField;//������������ʾ�ƵĽ���
    LinearLayout judgeCard;		//�����ж�����
    LinearLayout layoutAnimation;//����
    AlphaAnimation myAlpha_1;
	AlphaAnimation myAlpha_1_end;
	ScaleAnimation myScale;
	AnimationSet am; 
    
    public static int[][] cards =   
    {   
    	//�ƺţ���ɫ�����֣��Ƶ�����
//      ɱ 30   
//1
        {1, 1, 6, 1},   
        {2, 1, 7, 1},   
        {3, 1, 8, 1},   
        {4, 1, 9, 1},   
        {5, 1, 10, 1},   
        {6, 1, 13, 1},   
           
        {7, 4, 7, 1},   
        {8, 4, 8, 1},   
        {9, 4, 8, 1},   
        {10, 4, 9, 1},   
        {11, 4, 9, 1},   
        {12, 4, 10, 1},   
        {13, 4, 10, 1},   
           
        {14, 3, 10, 1},   
        {15, 3, 10, 1},   
        {16, 3, 11, 1},   
           
        {17, 2, 2, 1},   
        {18, 2, 3, 1},   
        {19, 2, 4, 1},   
        {20, 2, 5, 1},   
        {21, 2, 6, 1},   
        {22, 2, 7, 1},   
        {23, 2, 8, 1},   
        {24, 2, 8, 1},   
        {25, 2, 9, 1},   
        {26, 2, 9, 1},   
        {27, 2, 10, 1},   
        {28, 2, 10, 1},   
        {29, 2, 11, 1},   
        {30, 2, 11, 1},   
           
//      ��  15     
//  2 	            
        {31, 1, 2, 2},   
        {32, 1, 2, 2},   
        {33, 1, 3, 2},   
        {34, 1, 4, 2},   
        {35, 1, 5, 2},   
        {36, 1, 6, 2},   
        {37, 1, 7, 2},   
        {38, 1, 8, 2},   
        {39, 1, 9, 2},   
        {40, 1, 10, 2},   
        {41, 1, 11, 2},   
        {42, 1, 11, 2},   
           
        {43, 3, 2, 2},   
        {44, 3, 2, 2},   
        {45, 3, 13, 2},   
           
//      ��  8      
//   3	            	            
        {46, 1, 2, 3},   
        {47, 1, 12, 3},   
 
        {48, 3, 3, 3},   
        {49, 3, 4, 3},   
        {50, 3, 7, 3},   
        {51, 3, 8, 3},   
        {52, 3, 9, 3},   
        {53, 3, 12, 3},   
 
//������   
//      ����   
//      ����ʱ����ң�   
// 14    
        {54, 4, 1, 14},   
        {55, 3, 12, 14},   
           
//      �ֲ�˼��   
//      ����ʱ����ң�   
//  15         
        {56, 2, 6, 15},   
        {57, 3, 6, 15},   
        {58, 4, 6, 15},   
           
//      ��и�ɻ�   
//      ��4�ţ�     
//  8  
        {59, 1, 12, 8},   
        {60, 4, 11, 8},   
        {61, 2, 12, 8},   
        {62, 2, 13, 8},   
           
//      �赶ɱ��   
//      ��2�ţ�  
//13
        {63, 2, 12, 13},   
        {64, 2, 13, 13},   
 
//      ��ȷ��   
//      ��2�ţ�   
//	6         
        {65, 3, 3, 6},   
        {66, 3, 4, 6},   
 
//      ��������   
//      ��4�ţ�  
//   9          
        {67, 3, 7, 9},   
        {68, 3, 8, 9},   
        {69, 3, 9, 9},   
        {70, 3, 11, 9},   
           
//      ����   
//      ��3�ţ�   
//  12            
        {71, 1, 1, 12},   
        {72, 4, 1, 12},   
        {73, 2, 1, 12},   
           
//      ��԰����   
//      ��1�ţ�    
//  7           
        {74, 3, 1, 7},   
           
//      ��������   
//      ��3�ţ�   
//  5           
        {75, 4, 7, 5},   
        {76, 4, 13, 5},   
        {77, 2, 7, 5},   
           
//      ����뷢   
//      ��1�ţ� 
//4
        {78, 3, 1, 4},   
           
//      ˳��ǣ��   
//      ��5�ţ�
//  11
        {79, 1, 3, 11},   
        {80, 1, 4, 11},   
        {81, 4, 3, 11},   
        {82, 4, 4, 11},   
        {83, 4, 11, 11},   
           
//      ���Ӳ���   
//      ��6�ţ� 
//  10        
        {84, 4, 3, 10},   
        {85, 4, 4, 10},   
        {86, 4, 12, 10},   
        {87, 3, 11, 10},   
        {88, 2, 3, 10},   
        {89, 2, 4, 10},   
           
//װ����   
//��ƥ   
//      צ�Ʒɵ�   
//      ��+1��        
        {90, 3, 13, 32},   
           
//      ��¬   
//      ��+1��  ? 5         
        {91, 2, 5, 27},   
 
//      ��Ӱ   
//      ��+1��  ? 5   
        {92, 4, 5, 28},   
           
//      ����   
//      ��-1��  ? 5   
        {93, 3, 5, 29},   
           
//      ���U   
//      ��-1��  ? K         
        {94, 1, 13, 30},   
           
//      ����   
//      ��-1��  ? K   
        {95, 4, 13, 31},   
           
        //����   
//      �������   ? A ? A         
        {96, 1, 1, 23},   
        {97, 2, 1, 23},   
           
//      ������   
//      ��EX�ƣ�  ? 2         
        {98, 4, 2, 22},   
           
//      ���G��    ? 6         
        {99, 4, 6, 19},   
           
//      ����˫�ɽ�  ? 2         
        {100, 4, 2, 21},   
           
//      ��ʯ��    ? 5         
        {101, 1, 5, 16},   
           
//      �������µ�  ? 5         
        {102, 4, 5, 17},   
           
//      �ɰ���ì   ? Q         
        {103, 4, 12, 26},   
           
//      ���컭�   ? Q         
        {104, 1, 12, 20},   
           
//      ���빭    ? 5         
        {105, 3, 5,18},   
           
//      ������    ? 2   
//            
        {106, 2, 2, 24},   
        {107, 4, 2, 24},   
 
//      ������   
//      ��EX�ƣ�  ? 2         
        {108, 2, 2, 25},   
        
    };  

    
  //numΪ��������who=falseʱ�������ԣ�who=trueʱ�������
	public void getCards(int num,boolean who,boolean isBack)
    {
        	if(who==true){
        		for (int j=0;j<num;j++){
        			if(usedCardNum>validCards.size())
        				shuffle(false);
        			
        			humanPlayerCards.add(validCards.get(usedCardNum));
        			addCard(validCards.get(usedCardNum).intValue(),gameAc,cardLine,isBack);//�ڽ�������ʾ��
        			usedCardNum++;
        		}
        	}
        	else{
            		for (int j=0;j<num;j++){
            			if(usedCardNum>validCards.size())
            				shuffle(false);
            			
            			aiPlayerCards.add(validCards.get(usedCardNum));
            			usedCardNum++;
            		}         	
        	}
    }	
	//����Ƶ����֣�1-13��
	public int getCardNum(int num){
	       return cards[num][2];
	}
	//�����ǲ������ƽ׶�
	public void setIsRemove(boolean remove){
		isRemove=remove;
	}
	public void setIsWeaponOn(boolean weapon){
		isWeaponOn=weapon;
	}

	public boolean getIsWeaponOn(){
		return isWeaponOn;
	}
	//����isRemove���������ж��Ƿ����ƽ׶�
	public boolean getIsRemove() {
		return isRemove;
	}
	//����ѡ�����
	public int getChosenCard() {
		return chosenCard;
	}
	//����װ�����ƣ�who=trueΪ��ҵ�װ��������Ϊ���Ե�װ��
	public int getEquip(int pos,boolean who){
		return (who?humanEquipCards[pos]:aiEquipCards[pos]);
	}
	//����װ�����ƣ�who=trueΪ��ҵ�װ��������Ϊ���Ե�װ��
	public void setEquip(int pos,int num,boolean who){
		int dis=getWeaponDistance(num);
		if(who){		
			switch(pos){
			case 0:
				humanAttackRange=humanAttackRange-getWeaponDistance(humanEquipCards[0])+dis;
				break;
			case 1:break;
			case 2:
				aiAttackRange=aiAttackRange+getWeaponDistance(aiEquipCards[2])-dis;
				aiHorseRange=aiHorseRange+getWeaponDistance(aiEquipCards[2])-dis;
				break;
			case 3:
				humanAttackRange=humanAttackRange+getWeaponDistance(humanEquipCards[3])-dis;
				humanHorseRange=humanHorseRange+getWeaponDistance(humanEquipCards[3])-dis;
				break;
			}
			humanEquipCards[pos]=num;
			humanPlayerEquip[pos].setText(getWeaponName(num));
		}
		else{
			switch(pos){
			case 0:
				aiAttackRange=aiAttackRange-getWeaponDistance(aiEquipCards[0])+dis;
				break;
			case 1:break;
			case 2:
				humanAttackRange=humanAttackRange+getWeaponDistance(humanEquipCards[2])-dis;
				humanHorseRange=humanHorseRange+getWeaponDistance(humanEquipCards[2])-dis;
				break;
			case 3:
				aiAttackRange=aiAttackRange+getWeaponDistance(aiEquipCards[3])-dis;
				aiHorseRange=humanHorseRange+getWeaponDistance(aiEquipCards[3])-dis;
				break;
			}
			aiEquipCards[pos]=num;
			aiPlayerEquip[pos].setText(getWeaponName(num));
		}
	}
	public int getHorseRange(boolean who) {
		return (who?humanHorseRange:aiHorseRange);
	}
	//���ع�������
    public int getAttackRange(boolean who){
    	return (who?humanAttackRange:aiAttackRange);
    }
	//����ƵĻ�ɫ
	public int getCardColor(int num){
	       return cards[num][1];
	}
	
	//����ƵĻ�ɫͼƬ
	public Drawable getCardColPic(int num,Context con){
		switch(cards[num][1]){
		case 1:
			return con.getResources().getDrawable(R.drawable.diamond);
		case 2:
			return con.getResources().getDrawable(R.drawable.club);
		case 3:
			return con.getResources().getDrawable(R.drawable.heart);
		case 4:
			return con.getResources().getDrawable(R.drawable.spade);
			default:
				return con.getResources().getDrawable(R.drawable.icon);
		}
	}
	
	//����Ƶ����ࣨ����ɱ���ҵȵȣ�
	public int getCardSort(int num){
		if(num==-1)
			return -1;
		return cards[num][3];
	}
	//��û�ɫ��ͼƬ
	int getColorPic(int color)
    {
    	switch(color)
    	{
    	case 1:return R.drawable.diamond;
    	case 2:return R.drawable.club;
    	case 3:return R.drawable.heart;
    	case 4:return R.drawable.spade;
    	default:return 0;
    	}
    }
	//������ϵ�����
    public String getNumText(int position)
    {
    	switch(position)
    	{
    	case 1:return "A";
    	case 2:return "2";
    	case 3:return "3";
    	case 4:return "4";
    	case 5:return "5";
    	case 6:return "6";
    	case 7:return "7";
    	case 8:return "8";
    	case 9:return "9";
    	case 10:return "10";
    	case 11:return "J";
    	case 12:return "Q";
    	case 13:return "K";
    	default:return "0";
    	}
    }
    public String getWeaponName(int weapon){
    	switch(getCardSort(weapon)){
    	case 16:return "��ʯ��";
    	case 17:return "���µ�";
    	case 18:return "���빭";
    	case 19:return "��콣";
    	case 20:return "�����";
    	case 21:return "����˫��";
    	case 22:return "������";
    	case 23:return "�������";
    	case 24:return "������";
    	case 25:return "������";
    	case 26:return "�ɰ˳�ì";
    	case 27:return "��¬";
    	case 28:return "��Ӱ";
    	case 29:return "����";
    	case 30:return "���U";
    	case 31:return "����";
    	case 32:return "צ�Ʒɵ�";
    	default:return " ";
    	}
    }
    public int getWeaponDistance(int weapon){
    	switch(getCardSort(weapon)){
    	case 16:return 2;
    	case 17:return 2;
    	case 18:return 4;
    	case 19:return 1;
    	case 20:return 3;
    	case 21:return 1;
    	case 22:return 1;
    	case 23:return 0;
    	case 24:return 0;
    	case 25:return 0;
    	case 26:return 2;
    	case 27:return 1;
    	case 28:return 1;
    	case 29:return -1;
    	case 30:return -1;
    	case 31:return -1;
    	case 32:return 1;
    	default:return 0;
    	}
    }
	//����Ƶ�ͼƬ
	public Drawable getCardPic(int sort,Context con){
		switch(sort){
		case 1:
			return con.getResources().getDrawable(R.drawable.card_1);
		case 2:
			return con.getResources().getDrawable(R.drawable.card_2);
		case 3:
			return con.getResources().getDrawable(R.drawable.card_3);
		case 4:
			return con.getResources().getDrawable(R.drawable.card_4);
		case 5:
			return con.getResources().getDrawable(R.drawable.card_5);
		case 6:
			return con.getResources().getDrawable(R.drawable.card_6);
		case 7:
			return con.getResources().getDrawable(R.drawable.card_7);
		case 8:
			return con.getResources().getDrawable(R.drawable.card_8);
		case 9:
			return con.getResources().getDrawable(R.drawable.card_9);
		case 10:
			return con.getResources().getDrawable(R.drawable.card_10);
		case 11:
			return con.getResources().getDrawable(R.drawable.card_11);
		case 12:
			return con.getResources().getDrawable(R.drawable.card_12);
		case 13:
			return con.getResources().getDrawable(R.drawable.card_13);
		case 14:
			return con.getResources().getDrawable(R.drawable.card_14);
		case 15:
			return con.getResources().getDrawable(R.drawable.card_15);
		case 16:
			return con.getResources().getDrawable(R.drawable.card_16);
		case 17:
			return con.getResources().getDrawable(R.drawable.card_17);
		case 18:
			return con.getResources().getDrawable(R.drawable.card_18);
		case 19:
			return con.getResources().getDrawable(R.drawable.card_19);
		case 20:
			return con.getResources().getDrawable(R.drawable.card_20);
		case 21:
			return con.getResources().getDrawable(R.drawable.card_21);
		case 22:
			return con.getResources().getDrawable(R.drawable.card_22);
		case 23:
			return con.getResources().getDrawable(R.drawable.card_23);
		case 24:
			return con.getResources().getDrawable(R.drawable.card_24);
		case 25:
			return con.getResources().getDrawable(R.drawable.card_25);
		case 26:
			return con.getResources().getDrawable(R.drawable.card_26);
		case 27:
			return con.getResources().getDrawable(R.drawable.card_27);
		case 28:
			return con.getResources().getDrawable(R.drawable.card_28);
		case 29:
			return con.getResources().getDrawable(R.drawable.card_29);
		case 30:
			return con.getResources().getDrawable(R.drawable.card_30);
		case 31:
			return con.getResources().getDrawable(R.drawable.card_31);
		case 32:
			return con.getResources().getDrawable(R.drawable.card_32);
		case 33:
			return con.getResources().getDrawable(R.drawable.round_begin);
			default:
				return null;
		}
	}
	
	//ϴ��
	public void shuffle(boolean first)
	{
		if(first == true)
		{
		    for(int i=0;i<108;i++)
		    	validCards.add(new Integer(i));
	        Collections.shuffle(validCards);
	        getCards(4,true,false);//���ȷ�������
	       getCards(4,false,false);     
	    }
		else
		{
			Collections.shuffle(usedCards);
			validCards=usedCards;
			usedCards=null;
			usedCardNum=0;
		}		    
	}
	
	//��������ƽ���������ƣ�position��Ӧcards[][]�����Ԫ��λ��
	public void addCard(int position,Context con,LinearLayout layout,boolean isBack){
		final int pos=position;
		final boolean back=isBack;
		cardLayout[position]=null;
		cardLayout[position]=new LinearLayout(con);
		cardLayout[position].setLayoutParams(new LayoutParams(120, 170));
		cardLayout[position].setGravity(Gravity.CENTER);
		
		LinearLayout cardPic = new LinearLayout(con);
		cardPic.setLayoutParams(new LayoutParams(110,160));
		cardPic.setOrientation(LinearLayout.VERTICAL);
		cardPic.setGravity(Gravity.TOP);
		
		if(isBack==false){
			cardPic.setBackgroundDrawable(getCardPic(getCardSort(position),cardPic.getContext()));
			ImageView cardColor = new ImageView(con);
			cardColor.setLayoutParams(new LayoutParams(20,20));
			cardColor.setPadding(8, 8, 0, 0);
			cardColor.setImageResource(getColorPic(cards[position][1]));
			TextView cardNum = new TextView(con);
			cardNum.setLayoutParams(new LayoutParams(30,30));
			cardNum.setPadding(8, 0, 0, 3);
			cardNum.setText(getNumText(cards[position][2]));	
			cardNum.setTextColor(Color.BLACK);
			cardNum.setTextSize(11);
			cardPic.addView(cardColor);
			cardPic.addView(cardNum);
		}
		else
			cardPic.setBackgroundResource(R.drawable.card_back);	
		
		cardLayout[position].addView(cardPic);
		
        layout.addView(cardLayout[position]); 
		
		cardLayout[position].setOnClickListener(new View.OnClickListener() {
			
			//�����õ�isBack��ֵ
			boolean b=back;
			
			public void onClick(View v) {
				if(b && isSelect==false)
				{
					LinearLayout cardPic = new LinearLayout(v.getContext());
					cardPic.setLayoutParams(new LayoutParams(110,160));
					cardPic.setOrientation(LinearLayout.VERTICAL);
					cardPic.setGravity(Gravity.TOP);
					ImageView cardColor = new ImageView(v.getContext());
			        cardColor.setLayoutParams(new LayoutParams(20,20));
					cardColor.setPadding(8, 8, 0, 0);
					cardColor.setImageResource(getColorPic(cards[pos][1]));
					TextView cardNum = new TextView(v.getContext());
					cardNum.setLayoutParams(new LayoutParams(30,30));
					cardNum.setPadding(8, 0, 0, 3);
					cardNum.setText(getNumText(cards[pos][2]));	
					cardNum.setTextColor(Color.BLACK);
			        cardNum.setTextSize(11);
			        cardPic.setBackgroundDrawable(getCardPic(getCardSort(pos),cardPic.getContext()));
			        cardPic.addView(cardColor);
			        cardPic.addView(cardNum);
			        cardLayout[pos].removeAllViews();
			        cardLayout[pos].addView(cardPic);
			        chosenCard=pos;
			        b=!b;
				}
				
				else if(!isRemove && !isWeaponOn)
					chosenCard=pos;
				else{
					removeCards.add(new Integer(pos));
				}			
				setChosenCardBack(pos);
			}
		});
	}
	
	//���õ���Ƶ�Ч��
	private void setChosenCardBack(int chosenCard){
		if(isSelect)
		{
			for(int i=0;i<selectCards.size();i++)
				cardLayout[selectCards.get(i).intValue()].setBackgroundColor(0);
			cardLayout[chosenCard].setBackgroundColor(Color.RED);
			return;
		}
		else if(isRemove==false && isWeaponOn==false ){
				for(int i=0;i<humanPlayerCards.size();i++)
					cardLayout[humanPlayerCards.get(i).intValue()].setBackgroundColor(0);
				cardLayout[chosenCard].setBackgroundColor(Color.RED);
				return;
			}
		cardLayout[chosenCard].setBackgroundColor(Color.RED);
	}

	//ȡ��ѡ�����
	public void cancelCard() {
		if(isRemove==true)
			removeCards.clear();
		else if(isWeaponOn==true && removeCards.size()==0)
			isWeaponOn=false;
			
		chosenCard = 0;
		for(int i=0;i<humanPlayerCards.size();i++)
			cardLayout[humanPlayerCards.get(i).intValue()].setBackgroundColor(0);
	}
	

	//���ƴӽ���������
	public void removeChosenCard() {
		for(int i=0;i<removeCards.size();i++){
			cardLine.removeView(cardLayout[removeCards.get(i).intValue()]);
			humanPlayerCards.remove(getListPos(removeCards.get(i).intValue(),true));
		}
		removeCards.clear();
		isWeaponOn=false;
		isRemove=false;
	}
	
	//��ѡ�е��Ʒŵ�������ȥ
	public void showChosenCard(final int card, final boolean initializer){
		if(isSelect!=true)
			getAnimation(getCardSort(card));
			if(initializer){		
				cardLine.removeView(cardLayout[card]);
				humanShowCardField.removeAllViews();
				cardLayout[card].setBackgroundColor(0);
				cardLayout[card].setClickable(false);
				humanShowCardField.addView(cardLayout[card]);
				humanPlayerCards.remove(getListPos(card,true));
				usedCards.add(new Integer(chosenCard));
				chosenCard=-1;			
			}
		else{
				aiShowCardField.removeAllViews();
				addCard(card,gameAc,aiShowCardField,false);
				cardLayout[card].setClickable(false);
				aiPlayerCards.remove(getListPos(card,false));
				usedCards.add(new Integer(chosenCard));
			}
	}
	//����Ƿ����ĳ����,���ص�������cards�������������е�λ��
	public int searchCards(int sort,boolean toWho){
		if(sort==-1)
			return -1;
		if(toWho){
		    for(int i=0;i<humanPlayerCards.size();i++){
		        if(getCardSort(humanPlayerCards.get(i).intValue())==sort)
		            return humanPlayerCards.get(i).intValue();
		    }
		}
		else
		{
			for(int i=0;i<aiPlayerCards.size();i++){
		        if(getCardSort(aiPlayerCards.get(i).intValue())==sort)
		            return aiPlayerCards.get(i).intValue();
		    }
		}
		return -1;
	}
	//������ʾ����
	public void getAnimation(final int aniSort){
		if(getCardPic(aniSort,layoutAnimation.getContext())==null)
			return;
		
		myAlpha_1 = new AlphaAnimation(0.1f, 1.0f);
		myAlpha_1.setDuration(1000);
		
		myAlpha_1_end = new AlphaAnimation(1.0f, 0.0f);
		myAlpha_1_end.setDuration(250);
		myScale = new ScaleAnimation(0.3f,1.0f,0.3f,1.0f,Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		myScale.setDuration(1000);
		myAlpha_1_end.setAnimationListener(new Animation.AnimationListener() {
			
			
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			
			public void onAnimationEnd(Animation animation) {
				layoutAnimation.setBackgroundResource(0);
			}
		});
		
		myAlpha_1.setAnimationListener(new Animation.AnimationListener() {
			
			
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				layoutAnimation.setBackgroundDrawable(getCardPic(aniSort,layoutAnimation.getContext()));
			}
			
			
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				layoutAnimation.setAnimation(myAlpha_1_end);
			}
		});
		am = new AnimationSet ( false );
		am.addAnimation(myAlpha_1);
		am.addAnimation(myScale);
		layoutAnimation.setAnimation(am);
	}
	//�õ��ƶѵ�һ���ƣ������ж�
	public int checkTopOfCard(){
		int temp=validCards.get(usedCardNum).intValue();
		usedCardNum++;
		return temp;
		}

	//�õ�chosenCard��Ӧ����List�����λ��
	public int getListPos(int chosenCard,boolean who){
		List<Integer> temp=(who?humanPlayerCards:aiPlayerCards);
		for(int i=0;i<temp.size();i++)
		{
			if(chosenCard==temp.get(i).intValue())
			    return i;
		}
		return -1;
	}

	//�����Ի�������ʾ��ѡ�����,sort�������ŵ��ô˷������Ƶ����࣬����sort=6��Ϊ��ȷ��
	public void cardsForSelection(boolean who,int sort){
		if(who){
		cardDialog d=new cardDialog(gameAc,sort);  
         d.show();		
		}
		else{
			int temp=0;
			switch(sort){
			case 6:{
				if((temp=(int)(Math.random()*10)%2)==0){
					getCards(1,true,true);
					getCards(1,false,true);
				}
				else{
					getCards(1,false,true);
					getCards(1,true,true);
				}
				break;
			}
			case 10:{
				if(humanPlayerCards.size()==0)
					{
						for(int i=0;i<4;i++)
							if(humanEquipCards[i]!=-1)
							{
								judgeCard.removeAllViews();
								addCard(chosenCard,gameAc,judgeCard,false);
								setEquip(i,-1,true);
								return;
							}
					}
				else
					temp=(int)(Math.random()*100)%humanPlayerCards.size();
				cardLine.removeView(cardLayout[humanPlayerCards.get(temp).intValue()]);
				judgeCard.removeAllViews();
				addCard(chosenCard,gameAc,judgeCard,false);
				humanPlayerCards.remove(getListPos(chosenCard,true));

				break;
			}
			case 11:
			{
				if(humanPlayerCards.size()==0)
				{
					for(int i=0;i<4;i++)
						if(humanEquipCards[i]!=-1)
						{
							aiPlayerCards.add(new Integer(humanEquipCards[i]));
							setEquip(i,-1,true);
							return;
						}
				}
				else
					temp=(int)(Math.random()*100)%humanPlayerCards.size();
				cardLine.removeView(cardLayout[humanPlayerCards.get(temp).intValue()]);
				aiPlayerCards.add(humanPlayerCards.get(temp));
				humanPlayerCards.remove(temp);

			}
			}
		}
	} 
	class cardDialog extends Dialog implements OnClickListener{
		public cardDialog(Context context,int s) {
			super(context);
			sort=s;
		}
		
		Button confirmSelect;
		final int sort;
		protected void onCreate(Bundle savedInstanceState){
			super.onCreate(savedInstanceState);
			setContentView(R.layout.card_for_select);
			
			LinearLayout selectLayout;
			isSelect=true;
			int cardSum=0;
			boolean isBack=false;
			selectLayout=(LinearLayout)findViewById(R.id.card_select_list);
			confirmSelect=(Button)findViewById(R.id.confirm_select);
			chosenCard=-1;
			
			switch(sort){
			case 6:{
				if(usedCardNum+2>validCards.size())
					shuffle(false);
				selectCards=null;
				selectCards=new ArrayList<Integer>();
				selectCards.add(validCards.get(usedCardNum));
				selectCards.add(validCards.get(usedCardNum+1));
				cardSum=2;
				break;
			}
			case 10:
			case 11:
				{
					cardSum=aiPlayerCards.size();
					selectCards=aiPlayerCards;
					isBack=true;
					//���װ���Ƶ����沢������ʾ
					for(int i=0;i<4;i++){
						if(aiEquipCards[i]!=-1){
							addCard(aiEquipCards[i],this.getContext(),selectLayout,false);
							selectCards.add(new Integer(aiEquipCards[i]));
						}
					}
					break;
				}				
			}
			
			//����Ƶ�����
			for(int i=0;i<cardSum;i++){
				addCard(selectCards.get(i).intValue(),this.getContext(),selectLayout,isBack);
			}
			
			confirmSelect.setOnClickListener(new View.OnClickListener() {
				
				
				public void onClick(View v) {
					if(chosenCard!=-1)
					{
					switch(sort){
					case 6:{
						if(validCards.get(usedCardNum).intValue()==chosenCard){
							getCards(1,true,false);
							getCards(1,false,false);
							
						}
						else{
							getCards(1,false,false);
							getCards(1,true,false);
						}
						cancel();
						cardDialog.this.cancel();
						isSelect=false;
						break;
					}
					case 10:{
						for(int i=0;i<4;i++){
							if(aiEquipCards[i]==chosenCard)
								{
									judgeCard.removeAllViews();
									addCard(chosenCard,gameAc,judgeCard,false);
									setEquip(i,-1,false);
									break;
								}
							}
						judgeCard.removeAllViews();
						addCard(chosenCard,gameAc,judgeCard,false);
						aiPlayerCards.remove(getListPos(chosenCard,false));
						cancel();
						cardDialog.this.cancel();
						isSelect=false;
						break;
					}
					case 11:{
						for(int i=0;i<4;i++){
							if(aiEquipCards[i]==chosenCard)
								{
									setEquip(i,-1,false);
									humanPlayerCards.add(new Integer(chosenCard));
									break;
								}
							}
						aiPlayerCards.remove(getListPos(chosenCard,false));
						addCard(chosenCard,gameAc,cardLine,false);
						cardLayout[chosenCard].setClickable(true);
						humanPlayerCards.add(new Integer(chosenCard));
						cancel();
						cardDialog.this.cancel();
						isSelect=false;
						break;
					}
					default:break;
					}
				}
				}
			});
		}
		
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
		}
	}
}
