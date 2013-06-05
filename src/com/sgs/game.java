package com.sgs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;



public class game extends Activity {
    /** Called when the activity is first created. */
	
	Intent intent;
	hero aiPlayer;
	int aiCurrentBlood=0;//电脑现在的血量
	hero humanPlayer;
	int humanCurrentBlood=0;//玩家现在的血量
	gameCards allCards;
	
	boolean order;//判断是谁的回合，true为玩家回合，否则为电脑回合
	boolean response;//为true时，再非本回合时可以出牌做出回应
	boolean acceptEffect;//当点击“取消”，选择扣血时为true
	boolean inBattle;//判断是否在决斗状态
	boolean isLightning;//判断是否闪电
	boolean isContinue;//判断是否可以继续出杀
	boolean isLe;//判断是否中乐不思蜀
	boolean isEnd;//判断是否回合结束
	boolean isWuXie;//判断是否出了无懈可击
	boolean isCiXiong;//判断是否使用雌雄双剑
	int humanShownCard;//记录玩家刚刚出的牌
	int aiShownCard;//记录电脑刚刚出的牌
	Button confirm;         //确定按钮
	Button cancel;          //取消按钮
	Button endTurn;         //弃牌按钮
	Button menu;
	AlertDialog dialogOption;
	
	ImageView [] aiPlayerHp;//电脑血：由上到下为0到4
	ImageView [] humanPlayerHp;//玩家血：由上到下为0到4  
	ImageView aiPlayerLe;      //电脑的乐不思蜀
	ImageView aiPlayerDian;    //电脑的闪电
	ImageView humanPlayerLe;         //玩家的乐不思蜀
	ImageView humanPlayerDian;       //玩家的乐不思蜀
	LinearLayout aiPlayerBust;//电脑的头像
	LinearLayout humanPlayerBust;   //玩家的头像
	LinearLayout layoutMain;	//主界面
	Button weaponSkill;         //武器技能按钮
	Button humanPlayerSkill;    //玩家主技能

	
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
       getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main);	
        
        
        intent=this.getIntent();//传递chooseHero的选择英雄信息
        humanPlayer=(hero) intent.getParcelableExtra("humanPlayer"); 
        humanCurrentBlood=humanPlayer.getBloodLength();
        aiPlayer=(hero) intent.getParcelableExtra("aiPlayer"); 
          aiCurrentBlood=aiPlayer.getBloodLength();
      //传递whoIsFirst的优先权信息,order为true时玩家回合，为false时AI回合
       order=intent.getExtras().getBoolean("order");
        
        //我就先直接初始化英雄，为了测试方便
        
      //初始化头像   
    	humanPlayerBust = (LinearLayout)this.findViewById(R.id.layout_me_head);
		aiPlayerBust = (LinearLayout)this.findViewById(R.id.layout_enemy_head);
		layoutMain = (LinearLayout)this.findViewById(R.id.layout_main);
		
		
		//初始化乐不思蜀、雷电图标
		aiPlayerLe = (ImageView)this.findViewById(R.id.imageview_enemy_le);
		aiPlayerDian = (ImageView)this.findViewById(R.id.imageview_enemy_dian);
		humanPlayerLe = (ImageView)this.findViewById(R.id.imageview_me_le);
		humanPlayerDian = (ImageView)this.findViewById(R.id.imageview_me_dian);
		
		//初始化牌及各种判断值
		response=false;
		acceptEffect=false;
		inBattle=false;		
		isLightning=false;
		isLe=false;
		isContinue=true;
		isWuXie=false;
		humanShownCard=-1;
		aiShownCard=-1;
		allCards=new gameCards(this);	//洗牌并第一次发牌
		
		//初始化回合
		isEnd=true;
		Begin(order);
		isEnd=false;
		if(order==false)
		{
			aiAction();
		}
		
		//初始化双方血量
		aiPlayerHp=new ImageView[5];
		humanPlayerHp=new ImageView[5];
		aiPlayerHp[0] = (ImageView)this.findViewById(R.id.imageview_enemy_hp_0);
		aiPlayerHp[1] = (ImageView)this.findViewById(R.id.imageview_enemy_hp_1);
		aiPlayerHp[2] = (ImageView)this.findViewById(R.id.imageview_enemy_hp_2);
		aiPlayerHp[3] = (ImageView)this.findViewById(R.id.imageview_enemy_hp_3);
		aiPlayerHp[4] = (ImageView)this.findViewById(R.id.imageview_enemy_hp_4);	
		humanPlayerHp[0] = (ImageView)this.findViewById(R.id.imageview_me_hp_0);
		humanPlayerHp[1] = (ImageView)this.findViewById(R.id.imageview_me_hp_1);
		humanPlayerHp[2] = (ImageView)this.findViewById(R.id.imageview_me_hp_2);
		humanPlayerHp[3] = (ImageView)this.findViewById(R.id.imageview_me_hp_3);
		humanPlayerHp[4] = (ImageView)this.findViewById(R.id.imageview_me_hp_4);
		setBloodLeft(true);
		setBloodLeft(false);
		
		humanPlayerBust.setBackgroundDrawable
		(humanPlayerBust.getContext().getResources().getDrawable(humanPlayer.getHeroBust()));
		aiPlayerBust.setBackgroundDrawable
		(aiPlayerBust.getContext().getResources().getDrawable(aiPlayer.getHeroBust()));
        
		//初始化"暂停"按钮
		menu = (Button)this.findViewById(R.id.button_menu);  
        menu.setOnClickListener(new View.OnClickListener(){
        	
        	public void onClick(View v){
        dialogOption=new AlertDialog.Builder(layoutMain.getContext()).create();
        
		dialogOption.setButton("继续", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				dialogOption.cancel();
			}
		});
		dialogOption.setButton2("主菜单", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				intent = new Intent(game.this, welcome.class);
				game.this.finish();
	            startActivity(intent);
			}
		});
		dialogOption.setButton3("重新开始", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				intent = new Intent(game.this , chooseHero.class);
				game.this.finish();
				startActivity(intent);
			}
			
		}); 	
		dialogOption.show();
        	}
        });
    
        //初始化按钮
    weaponSkill = (Button)this.findViewById(R.id.button_weapon_skill);
	humanPlayerSkill = (Button)this.findViewById(R.id.button_skill);
    confirm = (Button)this.findViewById(R.id.button_comfirm);
	cancel = (Button)this.findViewById(R.id.button_cancel);
	endTurn = (Button)this.findViewById(R.id.button_endturn);
	
	weaponSkill.setOnClickListener(new View.OnClickListener() {
		
		
		public void onClick(View v) {
			if(order==true && response==false && allCards.getWeaponName(allCards.getEquip(0,true))=="丈八蛇矛")//判断是否为丈八蛇矛
				allCards.setIsWeaponOn(true);
			else if(order==true && response==false && allCards.getWeaponName(allCards.getEquip(0,true))=="贯石斧"//判断是否为贯石斧
					&& inBattle==false)
				{allCards.setIsWeaponOn(true);				
				allCards.setIsRemove(true);
				}
		}
	});
	humanPlayerSkill.setOnClickListener(new View.OnClickListener() {
		
		
		public void onClick(View v) {
		 if(order!=response)//玩家出牌阶段
				allCards.isHumanHeroOn=true;
		}
	});
	confirm.setOnClickListener(new View.OnClickListener() {
		
		
		public void onClick(View v) {
		//检测是否选择了使用武器技能	
		if(allCards.getIsWeaponOn() && (allCards.getEquip(0,true)==26 || allCards.getEquip(0,true)==16)){
			if(allCards.removeCards.size()!= 2){
				   new AlertDialog.Builder(layoutMain.getContext())
			        .setMessage("弃牌数有误，请重新选择")
			        .setNeutralButton("确定", new DialogInterface.OnClickListener() {
						
						public void onClick(DialogInterface dialog, int which) {
							allCards.cancelCard();
							dialog.cancel();							
						}
					}).show();
				}
			else{
				allCards.removeChosenCard();
				if(allCards.getEquip(0,true)==26){
					cardEffect(1,true,1);
					aiAction();
				}
				else if(allCards.getEquip(0,true)==16){
					decreaseBlood(1,false);//强制扣血
				}
				//allCards.setIsWeaponOn(false);
			}
		}
		//弃牌
		else if(allCards.getIsRemove()){	
				if(allCards.removeCards.size()!= allCards.humanPlayerCards.size()-humanCurrentBlood)
				{	 
					//提示对话框
					allCards.cancelCard();
					new AlertDialog.Builder(layoutMain.getContext())
					.setMessage("弃牌有误，请重新选择")
					.setNeutralButton("确定", new DialogInterface.OnClickListener() {
						
						
						public void onClick(DialogInterface dialog, int which) {
							allCards.cancelCard();
							dialog.cancel();
						}
					}).show();
				}
				else{
					allCards.removeChosenCard(); 
					allCards.setIsRemove(false);
					isEnd=true;
					response=false;
					aiAction();
				}
			}
		else if(response==true && isCiXiong==true){
			allCards.showChosenCard(allCards.getChosenCard(), true);
			isCiXiong=false;
		}
		else if(order==response){}//包含两种情况，TRUE电脑回应阶段，FALSE电脑出牌阶段
			
		else if(order!=response){//另两种情况，玩家出牌	
				if(allCards.isHumanHeroOn)//判断是否使用英雄技能
				{		
					getHeroInitSkill(true);
				}
				else{
				cardEffect(allCards.getChosenCard(),true,-1);
				}
				if(humanPlayer.getHeroNumber()==5//陆逊的连营技能
					&& allCards.humanPlayerCards.size()==0)
					allCards.getCards(1,true,true);
				else if(aiPlayer.getHeroNumber()==5
						&& allCards.aiPlayerCards.size()==0)
						allCards.getCards(1,false,true);
				weaponAction1();
				aiAction();
				weaponAction2();
			}	
		}
	});
	cancel.setOnClickListener(new View.OnClickListener() {
		
		
		public void onClick(View v) {
				allCards.cancelCard();
				allCards.isHumanHeroOn=false;
			if(response==true && (order==false || inBattle==true)){
				acceptEffect=true;//点击“取消”，不出牌，接受对方牌的效果
				cardEffect(aiShownCard,false,-1);			
				aiAction();
			}
		}
	});
	endTurn.setOnClickListener(new View.OnClickListener() {
		
		
		public void onClick(View v) {
			//弃牌的判定
			if(response==false && order==true && allCards.getIsRemove()==false){
				//弃牌
			    allCards.cancelCard();
			if(!checkCards(true)){
				allCards.setIsRemove(true);
			}
			else{
					response=false;
					isEnd=true;
					aiAction();					
			}
			}			
			//可以设置定时；
		}
	});
    }
    public void getHeroInitSkill(boolean who){
    	switch(getPlayerPart(who).getHeroNumber()){
    	//甄姬
    	case 2:{
    		if(allCards.getCardColor(allCards.getChosenCard())==2 ||
    				allCards.getCardColor(allCards.getChosenCard())==4)
    				{
    					cardEffect(2,who,allCards.getChosenCard());
    					allCards.isHumanHeroOn=false;
    				}
    		break;
    	}
    	//曹操，许褚，陆逊，黄月英，吕布
    	case 1:
    	case 3:
    	case 5:
    	case 8:{
    		cardEffect(allCards.getChosenCard(),who,-1);
    		break;
    	}
    	//黄盖
    	case 4:{
    		if(who==true){
    		if(humanCurrentBlood==1){
    			new AlertDialog.Builder(this)
    			.setMessage("继续使用技能？（1点血）")
    			.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					
					public void onClick(DialogInterface dialog, int which) {
						decreaseBlood(1,true);
			    		allCards.getCards(2,true,true);
			    		allCards.isHumanHeroOn=false;
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					
					
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
						allCards.isHumanHeroOn=false;
					}
				}).show();
    		}
    		if(true){
    				decreaseBlood(1,true);
    				allCards.getCards(2,true,true);
    				allCards.isHumanHeroOn=false;
    			}
    		}
    		//who==false
    		else{
    			decreaseBlood(1,false);
				allCards.getCards(2,false,true);
				allCards.isAiHeroOn=false;
    		}
    		break;
    	}
    	//甘宁
    	case 6:{
    		if(allCards.getCardColor(allCards.getChosenCard())==2 ||
    				allCards.getCardColor(allCards.getChosenCard())==4)
    				{
    					cardEffect(10,who,allCards.getChosenCard());
    					allCards.isHumanHeroOn=false;
    				}
    		break;
    	}
    	//赵云
    	case 7:{
    		if(allCards.getCardSort(allCards.getChosenCard())==1)
    		{
    			cardEffect(2,who,allCards.getChosenCard());
    			allCards.isHumanHeroOn=false;
    		}
    		else if(allCards.getCardSort(allCards.getChosenCard())==2)
    			cardEffect(1,who,allCards.getChosenCard());
    		break;
    	}
    	//关羽
    	case 9:{
    		if(allCards.getCardColor(allCards.getChosenCard())==1 ||
    				allCards.getCardColor(allCards.getChosenCard())==3)
    				{
    					cardEffect(1,who,allCards.getChosenCard());
    				}
    		break;
    	}
    	//华佗
    	case 10:{
    		if(allCards.getChosenCard()!=-1)
    				{
    					cardEffect(3,who,allCards.getChosenCard());
    					allCards.isHumanHeroOn=false;
    				}
    		break;
    	}
    	}
	}
    
  //牌的效果,initializer为true时是玩家出牌，否则是电脑出牌
  //每张牌的逻辑分三个不同的部分：牌首发（response==false），
    //牌回应（response==true），牌产生效果（acceptEffect==true）
    
    //isInstead为-1时，传递给函数的choose应为牌在cards【】【】里的序号
    
    //当isInstead不为-1的时候，说明之前使用技能导致牌的效果变成choose的效果，此时choose为
    //牌的类的序号（比如1为杀）,isInstead记录出的牌在cards【】【】中的序号
	public void cardEffect(int choose,boolean initializer,int isInstead) {
		int cardSort;
		int chosenCard;
		if(isInstead==-1){
			cardSort=allCards.getCardSort(choose);
			chosenCard=choose;
		}
		else{
			cardSort=choose;
			chosenCard=isInstead;
		}
		int counterCardSort=allCards.getCardSort(getPlayerShownCard(!initializer));
		switch(cardSort){
		//杀
		case 1:
		{
			if(acceptEffect==true)
			{
				final String weaponName=allCards.getWeaponName(allCards.getEquip(0,initializer));
				if(inBattle==true)
					{
					inBattle=false;
					decreaseBlood(1,!initializer);
					response=!response;
					break;
					}
				else if(weaponName=="寒冰剑" || weaponName=="麒麟弓"){
		    		if(!initializer)
		    		{
		    			if(weaponName=="寒冰剑")		    				
		    			{
		    				if(allCards.humanPlayerCards.size()>=1)
		    					allCards.showChosenCard(allCards.getListPos(0, true),true);
		    				if(allCards.humanPlayerCards.size()>=1)
		    					allCards.showChosenCard(allCards.getListPos(0, true),true);}
		    			else{
		    				if(allCards.humanEquipCards[2]!=-1)
		    					allCards.setEquip(2, -1, true);
		    				else
		    					allCards.setEquip(3, -1, true);
		    			}
		    		}
		    		else{
		    			//需要提示玩家是否使用该武器，若使用则弃对方2张牌，若不使用则对方扣血；
		    	        new AlertDialog.Builder(layoutMain.getContext())
		    	        .setMessage(weaponName+":是否弃对方牌")
		    	        .setPositiveButton("是", new DialogInterface.OnClickListener() {
		    				
		    				public void onClick(DialogInterface dialog, int which) {
		    					if(weaponName=="寒冰剑"){
		    						if(allCards.aiPlayerCards.size()>=1)
		    							allCards.showChosenCard(allCards.getListPos(0, false),false);
		    						if(allCards.aiPlayerCards.size()>=1)
		    							allCards.showChosenCard(allCards.getListPos(0, false),false);
		    					}
		    					else{
		    						if(allCards.aiEquipCards[2]!=-1)
				    					allCards.setEquip(2, -1, false);
				    				else
				    					allCards.setEquip(3, -1, false);
		    						
		    						if(getPlayerPart(true).getHeroNumber()==3 && allCards.isHumanHeroOn==true)
		    							//判断是否是许褚
		    						{
			    						allCards.isHumanHeroOn=false;					
			    						decreaseBlood(2,false);
			    					}
		    						else
		    							decreaseBlood(1,false);
			    					
		    					}
		    					isContinue=false;
		    				}
		    			})
		    	        .setNegativeButton("否", new DialogInterface.OnClickListener() {
		    				
		    				public void onClick(DialogInterface dialog, int which) {
		    					if(weaponName=="寒冰剑"){
		    					//判断是否是许褚
		    					if(getPlayerPart(true).getHeroNumber()==3 && allCards.isHumanHeroOn==true)
		    					{
		    						allCards.isHumanHeroOn=false;					
		    						decreaseBlood(2,false);
		    					}
		    					else
		    						decreaseBlood(1,false);
		    					}
		    					isContinue=false;
		    					dialog.cancel();
		    				}
		    			}).show();
		    	        break;
		            }
				}
				//判断是否是许褚
				if(getPlayerPart(initializer).getHeroNumber()==3){
					if(!initializer && allCards.isAiHeroOn==true)
						{allCards.isAiHeroOn=false;decreaseBlood(2,!initializer);}
					else if(initializer && allCards.isHumanHeroOn==true)
						{allCards.isHumanHeroOn=false;					
					decreaseBlood(2,!initializer);}
				}
				else
					decreaseBlood(1,!initializer);
				isContinue=false;
				response=!response;
			}
			else if(response==true && counterCardSort==5)//前一张牌是南蛮入侵
			{
				allCards.showChosenCard(chosenCard,initializer);
				setPlayerShownCard(initializer,chosenCard);
				response=!response;
				break;
			}
			else if(inBattle==true)//在决斗阶段
				{
					allCards.showChosenCard(chosenCard,initializer);
					setPlayerShownCard(initializer,chosenCard);
					response=!response;
				}
			else if(response==false && order==false && allCards.getCardSort(allCards.getEquip(0, initializer))==21
					&& humanPlayer.getHeroSex()!=aiPlayer.getHeroSex() && !initializer)//雌雄双剑
			{
				new AlertDialog.Builder(this)
				.setMessage("雌雄双剑")
				.setNegativeButton("我方弃牌",new DialogInterface.OnClickListener() {				
					
					public void onClick(DialogInterface dialog, int which) {
						response=true;
						isCiXiong=true;
					}
				})
			.setPositiveButton("对方摸牌",new DialogInterface.OnClickListener() {
				
				
				public void onClick(DialogInterface dialog, int which) {
					allCards.getCards(1,false,true);
				}
			}).show();
		}
		else if(response==false && isContinue==true && inBattle==false){		
				//判断距离是否允许以及是否可以继续出杀
				if(allCards.getAttackRange(initializer)>0)//可以出牌
				{
					int temp;//在八卦的判定中使用
					
					//仁王盾判定
					if(allCards.getCardSort(allCards.getEquip(1, !initializer))==25 && allCards.getCardSort(allCards.getEquip(0, initializer))!=19 &&
							(allCards.getCardColor(chosenCard)==2 || allCards.getCardColor(chosenCard)==4))
					{
						allCards.showChosenCard(chosenCard,initializer);
						setPlayerShownCard(initializer,chosenCard);
						if(allCards.getWeaponName(allCards.getEquip(0,initializer))!="诸葛连弩")
							isContinue=false;
						if(initializer==false)
							aiAction();
					}
					else{
						allCards.showChosenCard(chosenCard,initializer);
						setPlayerShownCard(initializer,chosenCard);
					if(allCards.getWeaponName(allCards.getEquip(0,initializer))!="诸葛连弩")
						isContinue=false;
					response=true;
					//八卦判定
					if(allCards.getEquip(1, !initializer)==24 && allCards.getEquip(0, initializer)!=19)
					{
							//判定
						temp=allCards.checkTopOfCard();
						if(allCards.getCardColor(temp)==1 || allCards.getCardColor(temp)==3)
								response=false;
						allCards.judgeCard.removeAllViews();
						allCards.addCard(temp,game.this,allCards.judgeCard,false);
						if(!initializer)
							aiAction();
					}		
					}
				}
			}
			
			break;
		}
		//闪
		case 2:
		{
			
			//判断对方出的牌为杀或是万箭齐发
			if(response==true && inBattle==false &&
			(counterCardSort==1 || counterCardSort==4))
			{
				allCards.showChosenCard(chosenCard,initializer);
				setPlayerShownCard(initializer,chosenCard);
				response=!response;
				if(getPlayerPart(initializer).getHeroNumber()==9)//关羽
				{
					if(initializer && allCards.isHumanHeroOn) 
						allCards.isHumanHeroOn=false;
					else if(!initializer && allCards.isAiHeroOn)
						allCards.isAiHeroOn=false;
				}
			}
			break;
		}
		//桃
		case 3:
		{
			//只有在血量小于默认血量时，才可加血
			if(response==false &&
			((initializer && humanCurrentBlood<humanPlayer.getBloodLength()) ||
			(!initializer && aiCurrentBlood<aiPlayer.getBloodLength())))
			{
				allCards.showChosenCard(chosenCard,initializer);
			    increaseBlood(1,initializer);
			    setPlayerShownCard(initializer,chosenCard);
			}
			else if((initializer && humanCurrentBlood<0) ||
					(!initializer && aiCurrentBlood<0))
			{
				allCards.showChosenCard(chosenCard,initializer);
			    increaseBlood(1,initializer);
			    setPlayerShownCard(initializer,chosenCard);
			}
			break;
		}
		//万箭齐发，南蛮入侵
		case 4:
		case 5:
		{
			if(acceptEffect){
				decreaseBlood(1,!initializer);
				response=!response;
				break;
				}
			else if(response==false){
				allCards.showChosenCard(chosenCard,initializer);		
			//判断对方有没有无懈可击及是否使用
		    isToShowWuXieKeJi(!initializer);
		    if(isWuXie==false){
		    	setPlayerShownCard(initializer,chosenCard);
		    	response=!response;
		    }
		    else{
		    	setPlayerShownCard(initializer,-1);
		    	isWuXie=false;
		    	if(initializer==false)
		    		aiAction();
		    }
		    
		    if(getPlayerPart(initializer).getHeroNumber()==8)//黄月英的集智
		    	allCards.getCards(1,initializer,true);
		    
			}
			break;
		}
		//五谷丰登
		case 6:
		{
			if(response==false){
				allCards.showChosenCard(chosenCard,initializer);
				setPlayerShownCard(initializer,chosenCard);
				isToShowWuXieKeJi(!initializer);
				if(isWuXie==false){
					allCards.cardsForSelection(initializer,cardSort);
				}
				else
			    	{
					isWuXie=false;
					if(initializer==false)
						aiAction();
			    	}
				if(getPlayerPart(initializer).getHeroNumber()==8)//黄月英的集智
			    	allCards.getCards(1,initializer,true);
			}
			break;
		}
		//过河拆桥
		case 10:
		{
			if(response==false){
				allCards.showChosenCard(chosenCard,initializer);
				setPlayerShownCard(initializer,chosenCard);
				isToShowWuXieKeJi(!initializer);
				if(isWuXie==false){
					allCards.cardsForSelection(initializer,cardSort);
				}
				else{
			    	isWuXie=false;
			    	if(initializer==false)
						aiAction();
				}
			if(getPlayerPart(!initializer).getHeroNumber()==5)//陆逊
		    {
		    	if((initializer && allCards.humanPlayerCards.size()==0) || 
		    			(!initializer && allCards.aiPlayerCards.size()==0))
		    		allCards.getCards(1,initializer,true);
		    }
			if(getPlayerPart(initializer).getHeroNumber()==8)//黄月英的集智
		    	allCards.getCards(1,initializer,true);
			
			}	
			break;
		}
		//桃园结义
		case 7:
		{
			if(response==false){
				allCards.showChosenCard(chosenCard,initializer);
				setPlayerShownCard(initializer,chosenCard);
				increaseBlood(1,initializer);			
				increaseBlood(1,!initializer);
				
				if(getPlayerPart(initializer).getHeroNumber()==8)//黄月英的集智
			    	allCards.getCards(1,initializer,true);
			}
			break;
		}
		//无中生有
		case 9:
		{
			if(response==false){
				allCards.showChosenCard(chosenCard,initializer);
			    setPlayerShownCard(initializer,chosenCard);
			    //判断对方有没有无懈可击
			    isToShowWuXieKeJi(!initializer);
			    if(isWuXie==false){
			    	allCards.getCards(2,initializer,true);
			    }
			    else{
			    	isWuXie=false;
			    if(initializer==false)
					aiAction();
			    }
			    if(getPlayerPart(initializer).getHeroNumber()==8)//黄月英的集智
			    	allCards.getCards(1,initializer,true);
			}
			break;
		}
		//顺手牵羊
		case 11:
		{
			if(response==false && getPlayerPart(!initializer).getHeroNumber()!=5 &&//陆逊 
			(allCards.getHorseRange(initializer)>=0 || getPlayerPart(initializer).getHeroNumber()==8))//黄月英
			{
				allCards.showChosenCard(chosenCard,initializer);
					setPlayerShownCard(initializer,chosenCard);
					//判断对方有没有无懈可击
				    isToShowWuXieKeJi(!initializer);
				    if(isWuXie==false){
				    allCards.cardsForSelection(initializer,cardSort);
				    }
				    else{
				    	setPlayerShownCard(initializer,-1);
				    	isWuXie=false;
				    	if(initializer==false)
							aiAction();
				    }
				    if(getPlayerPart(initializer).getHeroNumber()==8)//黄月英的集智
				    	allCards.getCards(1,initializer,true);
				   
				}
			break;
		}
		//决斗
		case 12:
			{
				if(acceptEffect){
					inBattle=false;
					decreaseBlood(1,!initializer);
					acceptEffect=false;
					response=!response;
					break;
				}
				else if(response==false){
					allCards.showChosenCard(chosenCard,initializer);
					setPlayerShownCard(initializer,chosenCard);
					isToShowWuXieKeJi(!initializer);
				    if(isWuXie==false){
					inBattle=true;
					response=!response;
				    }
				    else{
				    	isWuXie=false;
				    	if(initializer==false)
							aiAction();
				    }

				    if(getPlayerPart(initializer).getHeroNumber()==8)//黄月英的集智
				    	allCards.getCards(1,initializer,true);
				   
				}
				break;
			}
		//借刀杀人
		case 13:
		{
			if(response==false && allCards.getEquip(0,!initializer)!=-1
					&& allCards.getAttackRange(!initializer)>0){
				allCards.showChosenCard(chosenCard,initializer);
				setPlayerShownCard(initializer,chosenCard);
				//判断对方有没有无懈可击
			    isToShowWuXieKeJi(!initializer);
			    if(isWuXie==false){
			    	allCards.setEquip(0,allCards.getEquip(0,!initializer),initializer);
					allCards.setEquip(0,-1,!initializer);//-1表示没有武器				
			    }
			    else{
			    	setPlayerShownCard(initializer,-1);
			    	isWuXie=false;
			    	if(initializer==false)
						aiAction();}
			    
			    if(getPlayerPart(initializer).getHeroName()=="黄月英")
			    	allCards.getCards(1,initializer,true);
			    
			}
			break;
		}
		//闪电
		case 14:
		{
			if(response==false && isLightning==false){
				allCards.showChosenCard(chosenCard,initializer);
				setPlayerShownCard(initializer,chosenCard);
				isLightning=true;
				setLightning(initializer);
			}
			break;
		}
		//乐不思蜀
		case 15:
		{
			if(response==false && !isLe(!initializer) 
					&& getPlayerPart(!initializer).getHeroNumber()!=5){//陆逊
				allCards.showChosenCard(chosenCard,initializer);
				setPlayerShownCard(initializer,chosenCard);
				setLe(true,!initializer);
			}
			break;
		}
		//攻具
		case 16:
		case 17:
		case 18:
		case 19:
		case 20:
		case 21:
		case 22:
		case 26: 
		{
			if(response==false){
				allCards.showChosenCard(chosenCard,initializer);
				setPlayerShownCard(initializer,chosenCard);
				allCards.setEquip(0,chosenCard,initializer);
			}
			break;
		}
		//诸葛连弩
		case 23:
		{
			if(response==false){
				allCards.showChosenCard(chosenCard,initializer);
				setPlayerShownCard(initializer,chosenCard);
				allCards.setEquip(0,chosenCard,initializer);
				isContinue=true;
			}
			break;
		}
		//防具
		case 24:
		case 25:
		{
			if(response==false){
				allCards.showChosenCard(chosenCard,initializer);
				setPlayerShownCard(initializer,chosenCard);
				allCards.setEquip(1,chosenCard,initializer);
				break;
			}
			break;
		}
		//+1马
		case 27:
		case 28:
		case 32:
		{
			if(response==false){
				allCards.showChosenCard(chosenCard,initializer);
				setPlayerShownCard(initializer,chosenCard);
				allCards.setEquip(2,chosenCard,initializer);
				break;
			}
			break;
		}
		//-1马
		case 29:
		case 30:
		case 31:
		{
			if(response==false){
				allCards.showChosenCard(chosenCard,initializer);
				setPlayerShownCard(initializer,chosenCard);
				allCards.setEquip(3,chosenCard,initializer);
				break;
			}
			break;
		}
		default:
			break;
		}
		//统一将acceptEffect设为false
		if(acceptEffect==true)
			acceptEffect=false;
	}

	//第一个boolean判断是否中乐，第二个判断是哪一方
	public void setLe(boolean isLe, boolean who) {
		int temp=(isLe?View.VISIBLE:View.INVISIBLE);//将boolean转换成int
			if(who)
				humanPlayerLe.setVisibility(temp);
			else
				aiPlayerLe.setVisibility(temp);
	}
	public boolean isLe(boolean who){
		return (who?humanPlayerLe.isShown():aiPlayerLe.isShown());
	}
	public void setLightning(boolean who){
			if(isLightning==false){
				humanPlayerDian.setVisibility(View.INVISIBLE);
				aiPlayerDian.setVisibility(View.INVISIBLE);
			}
			else if(who){
				humanPlayerDian.setVisibility(View.VISIBLE);
				aiPlayerDian.setVisibility(View.INVISIBLE);
			}
			else{
				humanPlayerDian.setVisibility(View.INVISIBLE);
				aiPlayerDian.setVisibility(View.VISIBLE);
			}
		}
	public boolean isLightning(boolean who){
		return (who?humanPlayerDian.isShown():aiPlayerDian.isShown());
	}
		//who为true为玩家加血，否则为电脑加血	
    public void increaseBlood(int i, boolean who) {
    	if(who){
    		if(humanCurrentBlood+i>=humanPlayer.getBloodLength())
    			humanCurrentBlood=humanPlayer.getBloodLength();
    		else
    			humanCurrentBlood=humanCurrentBlood+i;
    		
    		setBloodLeft(who);
    	}
    	else{
    		if(aiCurrentBlood+i>=aiPlayer.getBloodLength())
    			aiCurrentBlood=aiPlayer.getBloodLength();
    		else
    			aiCurrentBlood=aiCurrentBlood+i;
    		
    		setBloodLeft(who);
    	}
    }
	//who为true为玩家加血，否则为电脑加血	
    private void decreaseBlood(int i, boolean who) {
    	if(who){
    		if((humanCurrentBlood=humanCurrentBlood-i)<=0)
    		isGameOver(who);
    		    setBloodLeft(who);
    		if(humanPlayer.getHeroNumber()==1)//判断是否为曹操
    		{
    			allCards.cardLayout[aiShownCard].setClickable(true);
    			allCards.addCard(aiShownCard,game.this,allCards.cardLine,false);
    			allCards.humanPlayerCards.add(aiShownCard);
    			
    		}
    			
    	}
    	else{
    		if((aiCurrentBlood=aiCurrentBlood-i)<=0)
    			isGameOver(who);
    			setBloodLeft(who);
    		if(aiPlayer.getHeroNumber()==1)//判断是否为曹操
    		{
    			allCards.cardLayout[aiShownCard].setClickable(true);
    			allCards.aiPlayerCards.add(new Integer(humanShownCard));
    			allCards.aiPlayerCards.add(humanShownCard);
    			
    		}
    	}
    }
	private void isGameOver(boolean who) {
		int temp=0;
		boolean isSave=false;
		int blood=(who?humanCurrentBlood:aiCurrentBlood);
		while(isSave || (temp=allCards.searchCards(3, who))!=-1){
			cardEffect(3,who,temp);
			blood--;
			if(blood>0)
			{
				isSave=true;
			}
		}
		 if(getPlayerPart(who).getHeroNumber()==10 && order!=who)//华佗
		{
			
			if(who)
			{
				for(int i=0;i<allCards.humanPlayerCards.size() || isSave;i++){
					temp=allCards.humanPlayerCards.get(i).intValue();
					if(allCards.getCardColor(temp)==1
							|| allCards.getCardColor(temp)==3)
					{
						cardEffect(3,who,temp);
						blood--;
						if(blood>0)
							isSave=true;
					}
				}
			}
			else
			{
				for(int i=0;i<allCards.aiPlayerCards.size() || isSave;i++){
					temp=allCards.aiPlayerCards.get(i).intValue();
					if(allCards.getCardColor(temp)==1
							|| allCards.getCardColor(temp)==3)
					{
						cardEffect(3,who,temp);
						blood--;
						if(blood>0)
							isSave=true;
					}
				}
			}
		}
		if(isSave==false){
			Intent intent=new Intent(game.this,gameResult.class);
			Bundle bundle=new Bundle();
			bundle.putBoolean("isVictory", !who);
			intent.putExtras(bundle);
			startActivity(intent);
			game.this.finish();	
		}
	}
	//检查手牌数是否超过血量（没有超过则返回true）,读入的布尔量玩家为true，AI为false；
    private boolean checkCards(boolean checkWhom) {
		
		return (checkWhom ? (humanCurrentBlood>=allCards.humanPlayerCards.size())
				:(aiCurrentBlood>=allCards.aiPlayerCards.size()));
	}
    //AI逻辑
    public void aiAction() {
    	Begin(false);
		int temp=0;
    	if(!order && !response && isWuXie==false)
    	{
    		if((temp=allCards.searchCards(7, false))!=-1 && aiCurrentBlood <= humanCurrentBlood)
    		{
    			cardEffect(temp,false,-1);
    		}
    		aiHandCardAction(27);
    		aiHandCardAction(28);
    		aiHandCardAction(29);
    		aiHandCardAction(30);
    		aiHandCardAction(31);
    		aiHandCardAction(32);
    		aiHandCardAction(20);
    		aiHandCardAction(19);
    		aiHandCardAction(22);
    		aiHandCardAction(21);
    		aiHandCardAction(16);
    		aiHandCardAction(17);
    		aiHandCardAction(26);
    		aiHandCardAction(23);
    		aiHandCardAction(18);
    		aiHandCardAction(24);
    		aiHandCardAction(25);
    		aiHandCardAction(3);
    		aiHandCardAction(10);
    		aiHandCardAction(11);
    		aiHandCardAction(15);
    		aiHandCardAction(14);
    		aiHandCardAction(9);
    		if((temp=allCards.searchCards(4, false))!=-1)
    		{
    			cardEffect(temp,false,-1);
    		}
    		else if((temp=allCards.searchCards(5, false))!=-1)
    		{
    			cardEffect(temp,false,-1);	
    		} 		
    		else if((temp=allCards.searchCards(12, false))!=-1)
    		{
    			cardEffect(temp,false,-1);
    		}
    		else if((temp=allCards.searchCards(1, false))!=-1 && isContinue)
    		{
    			cardEffect(temp,false,-1);
    			isContinue=false;
    		}
    		else if(humanPlayer.getHeroNumber()==5//陆逊的连营技能
				&& allCards.humanPlayerCards.size()==0)
				allCards.getCards(1,true,true);
    		else
    			allCards.setIsRemove(true);
    	}
    	
    	else if(response==true && isWuXie==false)
    	{
    		if(humanPlayer.getHeroNumber()==9 && allCards.isHumanHeroOn)//关羽的武圣
    		{
    			if((temp=allCards.searchCards(2, false))!=-1)
				{
				cardEffect(temp,false,-1);
				allCards.isHumanHeroOn=false;
				}
    			else{
    				decreaseBlood(1,false);
    				allCards.isHumanHeroOn=false;
    				response=!response;
    			}
    		}
    		else if(humanPlayer.getHeroNumber()==7 && allCards.isHumanHeroOn)//赵云闪当杀
    		{
    			if(allCards.getCardSort(humanShownCard)==2 && (temp=allCards.searchCards(2, false))!=-1)
    			{
    				cardEffect(temp,false,-1);
    				allCards.isHumanHeroOn=false;
    			}
    			else{
    				decreaseBlood(1,false);
    				allCards.isHumanHeroOn=false;
    				response=!response;
    			}
    		}
    	else {
    		switch(allCards.getCardSort(humanShownCard)){
    		case 1://玩家出杀
    		{
    			if(!inBattle)
    			{
    				if((temp=allCards.searchCards(2, false))!=-1)
    					{
    					cardEffect(temp,false,-1);
    					}
    				else
    				{
    					acceptEffect=true;//点击“取消”，不出牌，接受对方牌的效果	 
    					cardEffect(humanShownCard,true,-1);	
    				}
    			} 
    			
    			else//决斗情况
    			{
    				if((temp=allCards.searchCards(1, false))!=-1)
    					cardEffect(temp,false,-1);
    				else
    				{
    					acceptEffect=true;//点击“取消”，不出牌，接受对方牌的效果
    				    cardEffect(humanShownCard,true,-1);	
    				}
    			}
    			break;
    		}
    		case 4://万箭
    		{
    			if((temp=allCards.searchCards(2, false))!=-1)
					{cardEffect(temp,false,-1);}
				else
				{
					acceptEffect=true;//点击“取消”，不出牌，接受对方牌的效果
				    cardEffect(humanShownCard,true,-1);	
				}
    			break;
    		}
    		case 5://南蛮
    		{
    			if((temp=allCards.searchCards(1, false))!=-1)
					{cardEffect(temp,false,-1);}
				else
				{
					acceptEffect=true;//点击“取消”，不出牌，接受对方牌的效果
				    cardEffect(humanShownCard,true,-1);    
    		    }
    			break;
    		}
    		case 12://决斗
    		{
    			if((temp=allCards.searchCards(1, false))!=-1)
    				{cardEffect(temp,false,-1);}
				else
				{
					acceptEffect=true;//点击“取消”，不出牌，接受对方牌的效果
			        cardEffect(humanShownCard,true,-1);	
				}
    			break;
    		}
    		case 13://借刀杀人
    		{
    			 if((temp=allCards.searchCards(1, false))!=-1)
    				{cardEffect(temp,false,-1);}
				else
				{
					acceptEffect=true;//点击“取消”，不出牌，接受对方牌的效果
				    cardEffect(humanShownCard,true,-1);		
				}
    			break;
    		}
    		default:break;
    		} 
    	}
    	}
    	if(!order && allCards.getIsRemove())
    	{
    		while(allCards.aiPlayerCards.size()>aiCurrentBlood)
				allCards.aiPlayerCards.remove(0);
    		allCards.setIsRemove(false);
    		isEnd=true;
    		isContinue=true;
    		Begin(true); 
    	}    
	}
    
    //判断ai手牌，若有这张牌则打出
    public void aiHandCardAction(int sort)
    {
    	int temp=-1;
    	if((temp=allCards.searchCards(sort, false))!=-1)
			{cardEffect(temp,false,-1);aiShownCard=temp;}
    }
    
    //who为true返回玩家的hero变量，否则返回电脑的hero变量
    public hero getPlayerPart(boolean who)
    {
    	return(who?humanPlayer:aiPlayer);
    }
  
    //更新记录刚刚出的牌
    public void setPlayerShownCard(boolean who,int num){
    	if(who)
    		humanShownCard=num;
    	else
    		aiShownCard=num;
    }
    //返回刚刚出过的牌
    public int getPlayerShownCard(boolean who){
    	return(who?humanShownCard:aiShownCard);
    }
    //返回马的距离
    
    public void weaponAction1(){
    	String humanAttackWeapon=allCards.getWeaponName(allCards.getEquip(0,true));
    	if(humanAttackWeapon=="雌雄双股剑"){
    		if(humanPlayer.getHeroSex()!=aiPlayer.getHeroSex()){
    		new AlertDialog.Builder(layoutMain.getContext())
	        .setMessage("是否使用武器")
	        .setPositiveButton("是", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					allCards.getCards(1, true, true);
				}
    		})
	        .setNegativeButton("否", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					dialogOption.cancel();
				}
			}).show();
    		}
    	}
    }
    
    public void weaponAction2(){
    	String humanAttackWeapon=allCards.getWeaponName(allCards.getEquip(0,true));

    	if(humanAttackWeapon=="偃月刀" ){
    		if(allCards.getIsWeaponOn() && allCards.getCardSort(getPlayerShownCard(false))==2 && allCards.getCardSort(getPlayerShownCard(true))==1)
            {
    			//可以继续出杀，设置一个判断是否可以连续出杀的变量isContinue
    			isContinue=true;
    			if(allCards.searchCards(1, true)!=-1){
    			 new AlertDialog.Builder(layoutMain.getContext())
 		        .setMessage("是否使用青龙偃月刀")
 		        .setPositiveButton("是", new DialogInterface.OnClickListener() {
 					
 					public void onClick(DialogInterface dialog, int which) {
 						cardEffect(allCards.searchCards(1, true),true,-1);
 						aiAction();
 						weaponAction2();
 					}
 				})
 		        .setNegativeButton("否", new DialogInterface.OnClickListener() {
 					
 					public void onClick(DialogInterface dialog, int which) {
 						allCards.setIsWeaponOn(false);
 						isContinue=false;
 						dialog.cancel();
 					}
 				}).show();
    			}
            }
    	}  
    }
    
	//判断是否需要出无懈可击,返回值为true则需要出
	public void isToShowWuXieKeJi(boolean who){
		final int temp=allCards.searchCards(8, who);
			if(temp==-1){
				return;
			}
			else {//如果有
				isWuXie=!isWuXie;
				if(who==false){
					allCards.showChosenCard(temp,false);
					setPlayerShownCard(false,temp);	
					if(getPlayerPart(false).getHeroNumber()==8)//黄月英的集智
				    	allCards.getCards(1,who,false);
					isToShowWuXieKeJi(true);
					return;
				}
				else if(who==true)//如果为人类玩家
					{
					new AlertDialog.Builder(this)
					.setMessage("是否使用无懈可击？")
					.setPositiveButton("是", new DialogInterface.OnClickListener() {
						
						public void onClick(DialogInterface dialog, int which) {
							allCards.showChosenCard(temp,true);
							setPlayerShownCard(true,temp);
							if(getPlayerPart(true).getHeroNumber()==8)//黄月英的集智
						    	allCards.getCards(1,true,true);
							isToShowWuXieKeJi(false);
							return;
						}
					})
					.setNegativeButton("否", new DialogInterface.OnClickListener() {						
						
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
							isWuXie=!isWuXie;
						}
					}).show();
					}
			}
	}
	//设置界面上显示的血量
    public void setBloodLeft(boolean toWhom){   	
    	if(toWhom==false){
    	for(int i=0;i<5;i++)
		{
			if(aiCurrentBlood>i)
			{
				aiPlayerHp[i].setImageResource(R.drawable.hp_5);
			}
			else{
				aiPlayerHp[i].setImageResource(R.drawable.hp_0);
			}
			}
    	}
    	else{
    		for(int i=0;i<5;i++){
			if(humanCurrentBlood>i)
			{
				humanPlayerHp[i].setImageResource(R.drawable.hp_5);
			}
			else{
				humanPlayerHp[i].setImageResource(R.drawable.hp_0);
			}
    		}
    	}
    }
    
  //每回合首进行判定，发牌
    public void Begin(boolean who){
    	
    	if(isEnd==true){
    		allCards.humanShowCardField.removeAllViews();
        	allCards.aiShowCardField.removeAllViews();
        	isContinue=true;
        	allCards.setIsRemove(false);
        	response=false;
        	//回合开始
    	if(who)
    	{
    		allCards.getAnimation(33);
    		order=true;
    		//判定
            if(humanPlayerLe.isShown())
            {
            	isToShowWuXieKeJi(true);
            	if(isWuXie==false){
            	int check=allCards.checkTopOfCard();
            	allCards.judgeCard.removeAllViews();
            	allCards.addCard(check,game.this,allCards.judgeCard,false);
                if(allCards.getCardColor(check)!= 3)
                    isLe=true;
            	}
            	isWuXie=false;
                humanPlayerLe.setVisibility(View.INVISIBLE);
         }
            if(humanPlayerDian.isShown())
            {
            	isToShowWuXieKeJi(true);
            	if(isWuXie==false){
            		int check=allCards.checkTopOfCard();
            		int num=allCards.getCardNum(check);
            		allCards.judgeCard.removeAllViews();
            		allCards.addCard(check,game.this,allCards.judgeCard,false);
            		if(num>1 && num<10 && allCards.getCardColor(check) == 4)
            		{
            			//命中;
            			isLightning=false;
            			setLightning(true);
            			decreaseBlood(3,true);	    
            		}
            		else
            			setLightning(false); 
            	}
            	else{//无懈可击效果
            		isLightning=false;
            		setLightning(false); 
            		isWuXie=false;
            	}
            }
            if(humanPlayer.getHeroNumber()==2)//甄姬
            {
                int temp=allCards.getCardColor(allCards.checkTopOfCard());
                while(temp==2 || temp==4)
                {
                    allCards.usedCardNum--;
                    allCards.getCards(1,true,false);
                    temp=allCards.getCardColor(allCards.checkTopOfCard());
                }
                allCards.judgeCard.removeAllViews();
                allCards.addCard(allCards.usedCardNum-1,game.this,allCards.judgeCard,false);
            }
            if(humanPlayer.getHeroNumber()==3)//许褚
            {
                new AlertDialog.Builder(this)
            .setMessage("是否使用裸衣技能").setPositiveButton("是", new DialogInterface.OnClickListener() {
                
                public void onClick(DialogInterface dialog, int which) {
                allCards.isHumanHeroOn=true;
                allCards.getCards(1,true,true);
            }
            }).setNegativeButton("否",  new DialogInterface.OnClickListener() {
                
                public void onClick(DialogInterface dialog, int which) {
                	allCards.getCards(2,true,true);
                    dialog.cancel();
                      }
                  }).show();
            }
            else{
            	allCards.getCards(2,true,true);
            }
            if(isLe==true){
            if(!checkCards(true))
    			allCards.setIsRemove(true);
    		else{
    				response=false;
    				isLe=false;
    				allCards.isHumanHeroOn=false;
    				isEnd=true;
    				aiAction();					
    			}
            }
        }
    	
    	//如果是电脑的回合开始
    	else
    	{
    		order=false;
    		//判定
    		if(aiPlayerLe.isShown())
            {
    			isToShowWuXieKeJi(false);
            	if(isWuXie==false){
    			int check=allCards.checkTopOfCard();
    			allCards.judgeCard.removeAllViews();
    			allCards.addCard(check,game.this,allCards.judgeCard,false);
                if(allCards.getCardColor(check)!= 3)
                	isLe=true;
            	}
            	isWuXie=false;
                aiPlayerLe.setVisibility(View.INVISIBLE);
            }
            if(aiPlayerDian.isShown())
            {
            	isToShowWuXieKeJi(false);
            	if(isWuXie==false){
            		int check=allCards.checkTopOfCard();
            		int num=allCards.getCardNum(check);
            		allCards.judgeCard.removeAllViews();
            		allCards.addCard(check,game.this,allCards.judgeCard,false);
            		if(num>1 && num<10 && allCards.getCardColor(check) == 4)
            		{
            			//命中;
            			decreaseBlood(3,false);
            			isLightning=false;
            			setLightning(false);    
            		}
            		else
                        setLightning(true);
                }
            	else{//无懈可击效果
            		isLightning=false;
            		setLightning(true); 
            		isWuXie=false;
            	}
                
            }
            if(aiPlayer.getHeroNumber()==2)//甄姬
            {
                int temp=allCards.getCardColor(allCards.checkTopOfCard());
                while(temp==2 || temp==4)
                {
                    allCards.usedCardNum--;
                    allCards.getCards(1, false,true);
                    temp=allCards.getCardColor(allCards.checkTopOfCard());
                }
                allCards.judgeCard.removeAllViews();
                allCards.addCard(allCards.usedCardNum-1,game.this,allCards.judgeCard,false);
            }
            else if(aiPlayer.getHeroNumber()==2)//许褚
            {
                 if(allCards.searchCards(1, false)!=-1)
                 {
                	 allCards.isAiHeroOn=true;
                     allCards.getCards(1,false,true);
                 }
             }
            else if(!allCards.isAiHeroOn || aiPlayer.getHeroNumber()==2)//许褚
                allCards.getCards(2,false,true);  
            if(isLe==true){
            if(!checkCards(false))
            {
            	while(allCards.aiPlayerCards.size()>aiCurrentBlood)
        			allCards.aiPlayerCards.remove(0);
            }
    		else{
    				response=false;
    				isLe=false;
    				isEnd=true;
                    Begin(true);				
    			}		
    	    }
    	}
    	isEnd=false;
    }
    }


public void onBackPressed() //重写键盘的back按钮
{
	
	AlertDialog whereToGo=new AlertDialog.Builder(this).create();
	whereToGo.setButton("返回游戏",new DialogInterface.OnClickListener() {
		
		
		public void onClick(DialogInterface dialog, int which) {
		}
	});
	whereToGo.setButton2("主菜单",new DialogInterface.OnClickListener() {
		
		
		public void onClick(DialogInterface dialog, int which) {
			Intent intent=new Intent(game.this,welcome.class);
			startActivity(intent);
			game.this.finish();
		}
	});
	whereToGo.setButton3("退出游戏",new DialogInterface.OnClickListener() {
		
		
		public void onClick(DialogInterface dialog, int which) {
			game.this.finish();	
		}
	});
	whereToGo.show();
	
	return;
}
}