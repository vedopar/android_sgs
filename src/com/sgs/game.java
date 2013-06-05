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
	int aiCurrentBlood=0;//�������ڵ�Ѫ��
	hero humanPlayer;
	int humanCurrentBlood=0;//������ڵ�Ѫ��
	gameCards allCards;
	
	boolean order;//�ж���˭�Ļغϣ�trueΪ��һغϣ�����Ϊ���Իغ�
	boolean response;//Ϊtrueʱ���ٷǱ��غ�ʱ���Գ���������Ӧ
	boolean acceptEffect;//�������ȡ������ѡ���ѪʱΪtrue
	boolean inBattle;//�ж��Ƿ��ھ���״̬
	boolean isLightning;//�ж��Ƿ�����
	boolean isContinue;//�ж��Ƿ���Լ�����ɱ
	boolean isLe;//�ж��Ƿ����ֲ�˼��
	boolean isEnd;//�ж��Ƿ�غϽ���
	boolean isWuXie;//�ж��Ƿ������и�ɻ�
	boolean isCiXiong;//�ж��Ƿ�ʹ�ô���˫��
	int humanShownCard;//��¼��Ҹոճ�����
	int aiShownCard;//��¼���Ըոճ�����
	Button confirm;         //ȷ����ť
	Button cancel;          //ȡ����ť
	Button endTurn;         //���ư�ť
	Button menu;
	AlertDialog dialogOption;
	
	ImageView [] aiPlayerHp;//����Ѫ�����ϵ���Ϊ0��4
	ImageView [] humanPlayerHp;//���Ѫ�����ϵ���Ϊ0��4  
	ImageView aiPlayerLe;      //���Ե��ֲ�˼��
	ImageView aiPlayerDian;    //���Ե�����
	ImageView humanPlayerLe;         //��ҵ��ֲ�˼��
	ImageView humanPlayerDian;       //��ҵ��ֲ�˼��
	LinearLayout aiPlayerBust;//���Ե�ͷ��
	LinearLayout humanPlayerBust;   //��ҵ�ͷ��
	LinearLayout layoutMain;	//������
	Button weaponSkill;         //�������ܰ�ť
	Button humanPlayerSkill;    //���������

	
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
       getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main);	
        
        
        intent=this.getIntent();//����chooseHero��ѡ��Ӣ����Ϣ
        humanPlayer=(hero) intent.getParcelableExtra("humanPlayer"); 
        humanCurrentBlood=humanPlayer.getBloodLength();
        aiPlayer=(hero) intent.getParcelableExtra("aiPlayer"); 
          aiCurrentBlood=aiPlayer.getBloodLength();
      //����whoIsFirst������Ȩ��Ϣ,orderΪtrueʱ��һغϣ�ΪfalseʱAI�غ�
       order=intent.getExtras().getBoolean("order");
        
        //�Ҿ���ֱ�ӳ�ʼ��Ӣ�ۣ�Ϊ�˲��Է���
        
      //��ʼ��ͷ��   
    	humanPlayerBust = (LinearLayout)this.findViewById(R.id.layout_me_head);
		aiPlayerBust = (LinearLayout)this.findViewById(R.id.layout_enemy_head);
		layoutMain = (LinearLayout)this.findViewById(R.id.layout_main);
		
		
		//��ʼ���ֲ�˼���׵�ͼ��
		aiPlayerLe = (ImageView)this.findViewById(R.id.imageview_enemy_le);
		aiPlayerDian = (ImageView)this.findViewById(R.id.imageview_enemy_dian);
		humanPlayerLe = (ImageView)this.findViewById(R.id.imageview_me_le);
		humanPlayerDian = (ImageView)this.findViewById(R.id.imageview_me_dian);
		
		//��ʼ���Ƽ������ж�ֵ
		response=false;
		acceptEffect=false;
		inBattle=false;		
		isLightning=false;
		isLe=false;
		isContinue=true;
		isWuXie=false;
		humanShownCard=-1;
		aiShownCard=-1;
		allCards=new gameCards(this);	//ϴ�Ʋ���һ�η���
		
		//��ʼ���غ�
		isEnd=true;
		Begin(order);
		isEnd=false;
		if(order==false)
		{
			aiAction();
		}
		
		//��ʼ��˫��Ѫ��
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
        
		//��ʼ��"��ͣ"��ť
		menu = (Button)this.findViewById(R.id.button_menu);  
        menu.setOnClickListener(new View.OnClickListener(){
        	
        	public void onClick(View v){
        dialogOption=new AlertDialog.Builder(layoutMain.getContext()).create();
        
		dialogOption.setButton("����", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				dialogOption.cancel();
			}
		});
		dialogOption.setButton2("���˵�", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				intent = new Intent(game.this, welcome.class);
				game.this.finish();
	            startActivity(intent);
			}
		});
		dialogOption.setButton3("���¿�ʼ", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				intent = new Intent(game.this , chooseHero.class);
				game.this.finish();
				startActivity(intent);
			}
			
		}); 	
		dialogOption.show();
        	}
        });
    
        //��ʼ����ť
    weaponSkill = (Button)this.findViewById(R.id.button_weapon_skill);
	humanPlayerSkill = (Button)this.findViewById(R.id.button_skill);
    confirm = (Button)this.findViewById(R.id.button_comfirm);
	cancel = (Button)this.findViewById(R.id.button_cancel);
	endTurn = (Button)this.findViewById(R.id.button_endturn);
	
	weaponSkill.setOnClickListener(new View.OnClickListener() {
		
		
		public void onClick(View v) {
			if(order==true && response==false && allCards.getWeaponName(allCards.getEquip(0,true))=="�ɰ���ì")//�ж��Ƿ�Ϊ�ɰ���ì
				allCards.setIsWeaponOn(true);
			else if(order==true && response==false && allCards.getWeaponName(allCards.getEquip(0,true))=="��ʯ��"//�ж��Ƿ�Ϊ��ʯ��
					&& inBattle==false)
				{allCards.setIsWeaponOn(true);				
				allCards.setIsRemove(true);
				}
		}
	});
	humanPlayerSkill.setOnClickListener(new View.OnClickListener() {
		
		
		public void onClick(View v) {
		 if(order!=response)//��ҳ��ƽ׶�
				allCards.isHumanHeroOn=true;
		}
	});
	confirm.setOnClickListener(new View.OnClickListener() {
		
		
		public void onClick(View v) {
		//����Ƿ�ѡ����ʹ����������	
		if(allCards.getIsWeaponOn() && (allCards.getEquip(0,true)==26 || allCards.getEquip(0,true)==16)){
			if(allCards.removeCards.size()!= 2){
				   new AlertDialog.Builder(layoutMain.getContext())
			        .setMessage("����������������ѡ��")
			        .setNeutralButton("ȷ��", new DialogInterface.OnClickListener() {
						
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
					decreaseBlood(1,false);//ǿ�ƿ�Ѫ
				}
				//allCards.setIsWeaponOn(false);
			}
		}
		//����
		else if(allCards.getIsRemove()){	
				if(allCards.removeCards.size()!= allCards.humanPlayerCards.size()-humanCurrentBlood)
				{	 
					//��ʾ�Ի���
					allCards.cancelCard();
					new AlertDialog.Builder(layoutMain.getContext())
					.setMessage("��������������ѡ��")
					.setNeutralButton("ȷ��", new DialogInterface.OnClickListener() {
						
						
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
		else if(order==response){}//�������������TRUE���Ի�Ӧ�׶Σ�FALSE���Գ��ƽ׶�
			
		else if(order!=response){//�������������ҳ���	
				if(allCards.isHumanHeroOn)//�ж��Ƿ�ʹ��Ӣ�ۼ���
				{		
					getHeroInitSkill(true);
				}
				else{
				cardEffect(allCards.getChosenCard(),true,-1);
				}
				if(humanPlayer.getHeroNumber()==5//½ѷ����Ӫ����
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
				acceptEffect=true;//�����ȡ�����������ƣ����ܶԷ��Ƶ�Ч��
				cardEffect(aiShownCard,false,-1);			
				aiAction();
			}
		}
	});
	endTurn.setOnClickListener(new View.OnClickListener() {
		
		
		public void onClick(View v) {
			//���Ƶ��ж�
			if(response==false && order==true && allCards.getIsRemove()==false){
				//����
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
			//�������ö�ʱ��
		}
	});
    }
    public void getHeroInitSkill(boolean who){
    	switch(getPlayerPart(who).getHeroNumber()){
    	//�缧
    	case 2:{
    		if(allCards.getCardColor(allCards.getChosenCard())==2 ||
    				allCards.getCardColor(allCards.getChosenCard())==4)
    				{
    					cardEffect(2,who,allCards.getChosenCard());
    					allCards.isHumanHeroOn=false;
    				}
    		break;
    	}
    	//�ܲ٣����ң�½ѷ������Ӣ������
    	case 1:
    	case 3:
    	case 5:
    	case 8:{
    		cardEffect(allCards.getChosenCard(),who,-1);
    		break;
    	}
    	//�Ƹ�
    	case 4:{
    		if(who==true){
    		if(humanCurrentBlood==1){
    			new AlertDialog.Builder(this)
    			.setMessage("����ʹ�ü��ܣ���1��Ѫ��")
    			.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
					
					
					public void onClick(DialogInterface dialog, int which) {
						decreaseBlood(1,true);
			    		allCards.getCards(2,true,true);
			    		allCards.isHumanHeroOn=false;
					}
				})
				.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
					
					
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
    	//����
    	case 6:{
    		if(allCards.getCardColor(allCards.getChosenCard())==2 ||
    				allCards.getCardColor(allCards.getChosenCard())==4)
    				{
    					cardEffect(10,who,allCards.getChosenCard());
    					allCards.isHumanHeroOn=false;
    				}
    		break;
    	}
    	//����
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
    	//����
    	case 9:{
    		if(allCards.getCardColor(allCards.getChosenCard())==1 ||
    				allCards.getCardColor(allCards.getChosenCard())==3)
    				{
    					cardEffect(1,who,allCards.getChosenCard());
    				}
    		break;
    	}
    	//��٢
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
    
  //�Ƶ�Ч��,initializerΪtrueʱ����ҳ��ƣ������ǵ��Գ���
  //ÿ���Ƶ��߼���������ͬ�Ĳ��֣����׷���response==false����
    //�ƻ�Ӧ��response==true�����Ʋ���Ч����acceptEffect==true��
    
    //isInsteadΪ-1ʱ�����ݸ�������chooseӦΪ����cards��������������
    
    //��isInstead��Ϊ-1��ʱ��˵��֮ǰʹ�ü��ܵ����Ƶ�Ч�����choose��Ч������ʱchooseΪ
    //�Ƶ������ţ�����1Ϊɱ��,isInstead��¼��������cards���������е����
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
		//ɱ
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
				else if(weaponName=="������" || weaponName=="���빭"){
		    		if(!initializer)
		    		{
		    			if(weaponName=="������")		    				
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
		    			//��Ҫ��ʾ����Ƿ�ʹ�ø���������ʹ�������Է�2���ƣ�����ʹ����Է���Ѫ��
		    	        new AlertDialog.Builder(layoutMain.getContext())
		    	        .setMessage(weaponName+":�Ƿ����Է���")
		    	        .setPositiveButton("��", new DialogInterface.OnClickListener() {
		    				
		    				public void onClick(DialogInterface dialog, int which) {
		    					if(weaponName=="������"){
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
		    							//�ж��Ƿ�������
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
		    	        .setNegativeButton("��", new DialogInterface.OnClickListener() {
		    				
		    				public void onClick(DialogInterface dialog, int which) {
		    					if(weaponName=="������"){
		    					//�ж��Ƿ�������
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
				//�ж��Ƿ�������
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
			else if(response==true && counterCardSort==5)//ǰһ��������������
			{
				allCards.showChosenCard(chosenCard,initializer);
				setPlayerShownCard(initializer,chosenCard);
				response=!response;
				break;
			}
			else if(inBattle==true)//�ھ����׶�
				{
					allCards.showChosenCard(chosenCard,initializer);
					setPlayerShownCard(initializer,chosenCard);
					response=!response;
				}
			else if(response==false && order==false && allCards.getCardSort(allCards.getEquip(0, initializer))==21
					&& humanPlayer.getHeroSex()!=aiPlayer.getHeroSex() && !initializer)//����˫��
			{
				new AlertDialog.Builder(this)
				.setMessage("����˫��")
				.setNegativeButton("�ҷ�����",new DialogInterface.OnClickListener() {				
					
					public void onClick(DialogInterface dialog, int which) {
						response=true;
						isCiXiong=true;
					}
				})
			.setPositiveButton("�Է�����",new DialogInterface.OnClickListener() {
				
				
				public void onClick(DialogInterface dialog, int which) {
					allCards.getCards(1,false,true);
				}
			}).show();
		}
		else if(response==false && isContinue==true && inBattle==false){		
				//�жϾ����Ƿ������Լ��Ƿ���Լ�����ɱ
				if(allCards.getAttackRange(initializer)>0)//���Գ���
				{
					int temp;//�ڰ��Ե��ж���ʹ��
					
					//�������ж�
					if(allCards.getCardSort(allCards.getEquip(1, !initializer))==25 && allCards.getCardSort(allCards.getEquip(0, initializer))!=19 &&
							(allCards.getCardColor(chosenCard)==2 || allCards.getCardColor(chosenCard)==4))
					{
						allCards.showChosenCard(chosenCard,initializer);
						setPlayerShownCard(initializer,chosenCard);
						if(allCards.getWeaponName(allCards.getEquip(0,initializer))!="�������")
							isContinue=false;
						if(initializer==false)
							aiAction();
					}
					else{
						allCards.showChosenCard(chosenCard,initializer);
						setPlayerShownCard(initializer,chosenCard);
					if(allCards.getWeaponName(allCards.getEquip(0,initializer))!="�������")
						isContinue=false;
					response=true;
					//�����ж�
					if(allCards.getEquip(1, !initializer)==24 && allCards.getEquip(0, initializer)!=19)
					{
							//�ж�
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
		//��
		case 2:
		{
			
			//�ж϶Է�������Ϊɱ��������뷢
			if(response==true && inBattle==false &&
			(counterCardSort==1 || counterCardSort==4))
			{
				allCards.showChosenCard(chosenCard,initializer);
				setPlayerShownCard(initializer,chosenCard);
				response=!response;
				if(getPlayerPart(initializer).getHeroNumber()==9)//����
				{
					if(initializer && allCards.isHumanHeroOn) 
						allCards.isHumanHeroOn=false;
					else if(!initializer && allCards.isAiHeroOn)
						allCards.isAiHeroOn=false;
				}
			}
			break;
		}
		//��
		case 3:
		{
			//ֻ����Ѫ��С��Ĭ��Ѫ��ʱ���ſɼ�Ѫ
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
		//����뷢����������
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
			//�ж϶Է���û����и�ɻ����Ƿ�ʹ��
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
		    
		    if(getPlayerPart(initializer).getHeroNumber()==8)//����Ӣ�ļ���
		    	allCards.getCards(1,initializer,true);
		    
			}
			break;
		}
		//��ȷ��
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
				if(getPlayerPart(initializer).getHeroNumber()==8)//����Ӣ�ļ���
			    	allCards.getCards(1,initializer,true);
			}
			break;
		}
		//���Ӳ���
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
			if(getPlayerPart(!initializer).getHeroNumber()==5)//½ѷ
		    {
		    	if((initializer && allCards.humanPlayerCards.size()==0) || 
		    			(!initializer && allCards.aiPlayerCards.size()==0))
		    		allCards.getCards(1,initializer,true);
		    }
			if(getPlayerPart(initializer).getHeroNumber()==8)//����Ӣ�ļ���
		    	allCards.getCards(1,initializer,true);
			
			}	
			break;
		}
		//��԰����
		case 7:
		{
			if(response==false){
				allCards.showChosenCard(chosenCard,initializer);
				setPlayerShownCard(initializer,chosenCard);
				increaseBlood(1,initializer);			
				increaseBlood(1,!initializer);
				
				if(getPlayerPart(initializer).getHeroNumber()==8)//����Ӣ�ļ���
			    	allCards.getCards(1,initializer,true);
			}
			break;
		}
		//��������
		case 9:
		{
			if(response==false){
				allCards.showChosenCard(chosenCard,initializer);
			    setPlayerShownCard(initializer,chosenCard);
			    //�ж϶Է���û����и�ɻ�
			    isToShowWuXieKeJi(!initializer);
			    if(isWuXie==false){
			    	allCards.getCards(2,initializer,true);
			    }
			    else{
			    	isWuXie=false;
			    if(initializer==false)
					aiAction();
			    }
			    if(getPlayerPart(initializer).getHeroNumber()==8)//����Ӣ�ļ���
			    	allCards.getCards(1,initializer,true);
			}
			break;
		}
		//˳��ǣ��
		case 11:
		{
			if(response==false && getPlayerPart(!initializer).getHeroNumber()!=5 &&//½ѷ 
			(allCards.getHorseRange(initializer)>=0 || getPlayerPart(initializer).getHeroNumber()==8))//����Ӣ
			{
				allCards.showChosenCard(chosenCard,initializer);
					setPlayerShownCard(initializer,chosenCard);
					//�ж϶Է���û����и�ɻ�
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
				    if(getPlayerPart(initializer).getHeroNumber()==8)//����Ӣ�ļ���
				    	allCards.getCards(1,initializer,true);
				   
				}
			break;
		}
		//����
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

				    if(getPlayerPart(initializer).getHeroNumber()==8)//����Ӣ�ļ���
				    	allCards.getCards(1,initializer,true);
				   
				}
				break;
			}
		//�赶ɱ��
		case 13:
		{
			if(response==false && allCards.getEquip(0,!initializer)!=-1
					&& allCards.getAttackRange(!initializer)>0){
				allCards.showChosenCard(chosenCard,initializer);
				setPlayerShownCard(initializer,chosenCard);
				//�ж϶Է���û����и�ɻ�
			    isToShowWuXieKeJi(!initializer);
			    if(isWuXie==false){
			    	allCards.setEquip(0,allCards.getEquip(0,!initializer),initializer);
					allCards.setEquip(0,-1,!initializer);//-1��ʾû������				
			    }
			    else{
			    	setPlayerShownCard(initializer,-1);
			    	isWuXie=false;
			    	if(initializer==false)
						aiAction();}
			    
			    if(getPlayerPart(initializer).getHeroName()=="����Ӣ")
			    	allCards.getCards(1,initializer,true);
			    
			}
			break;
		}
		//����
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
		//�ֲ�˼��
		case 15:
		{
			if(response==false && !isLe(!initializer) 
					&& getPlayerPart(!initializer).getHeroNumber()!=5){//½ѷ
				allCards.showChosenCard(chosenCard,initializer);
				setPlayerShownCard(initializer,chosenCard);
				setLe(true,!initializer);
			}
			break;
		}
		//����
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
		//�������
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
		//����
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
		//+1��
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
		//-1��
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
		//ͳһ��acceptEffect��Ϊfalse
		if(acceptEffect==true)
			acceptEffect=false;
	}

	//��һ��boolean�ж��Ƿ����֣��ڶ����ж�����һ��
	public void setLe(boolean isLe, boolean who) {
		int temp=(isLe?View.VISIBLE:View.INVISIBLE);//��booleanת����int
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
		//whoΪtrueΪ��Ҽ�Ѫ������Ϊ���Լ�Ѫ	
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
	//whoΪtrueΪ��Ҽ�Ѫ������Ϊ���Լ�Ѫ	
    private void decreaseBlood(int i, boolean who) {
    	if(who){
    		if((humanCurrentBlood=humanCurrentBlood-i)<=0)
    		isGameOver(who);
    		    setBloodLeft(who);
    		if(humanPlayer.getHeroNumber()==1)//�ж��Ƿ�Ϊ�ܲ�
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
    		if(aiPlayer.getHeroNumber()==1)//�ж��Ƿ�Ϊ�ܲ�
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
		 if(getPlayerPart(who).getHeroNumber()==10 && order!=who)//��٢
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
	//����������Ƿ񳬹�Ѫ����û�г����򷵻�true��,����Ĳ��������Ϊtrue��AIΪfalse��
    private boolean checkCards(boolean checkWhom) {
		
		return (checkWhom ? (humanCurrentBlood>=allCards.humanPlayerCards.size())
				:(aiCurrentBlood>=allCards.aiPlayerCards.size()));
	}
    //AI�߼�
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
    		else if(humanPlayer.getHeroNumber()==5//½ѷ����Ӫ����
				&& allCards.humanPlayerCards.size()==0)
				allCards.getCards(1,true,true);
    		else
    			allCards.setIsRemove(true);
    	}
    	
    	else if(response==true && isWuXie==false)
    	{
    		if(humanPlayer.getHeroNumber()==9 && allCards.isHumanHeroOn)//�������ʥ
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
    		else if(humanPlayer.getHeroNumber()==7 && allCards.isHumanHeroOn)//��������ɱ
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
    		case 1://��ҳ�ɱ
    		{
    			if(!inBattle)
    			{
    				if((temp=allCards.searchCards(2, false))!=-1)
    					{
    					cardEffect(temp,false,-1);
    					}
    				else
    				{
    					acceptEffect=true;//�����ȡ�����������ƣ����ܶԷ��Ƶ�Ч��	 
    					cardEffect(humanShownCard,true,-1);	
    				}
    			} 
    			
    			else//�������
    			{
    				if((temp=allCards.searchCards(1, false))!=-1)
    					cardEffect(temp,false,-1);
    				else
    				{
    					acceptEffect=true;//�����ȡ�����������ƣ����ܶԷ��Ƶ�Ч��
    				    cardEffect(humanShownCard,true,-1);	
    				}
    			}
    			break;
    		}
    		case 4://���
    		{
    			if((temp=allCards.searchCards(2, false))!=-1)
					{cardEffect(temp,false,-1);}
				else
				{
					acceptEffect=true;//�����ȡ�����������ƣ����ܶԷ��Ƶ�Ч��
				    cardEffect(humanShownCard,true,-1);	
				}
    			break;
    		}
    		case 5://����
    		{
    			if((temp=allCards.searchCards(1, false))!=-1)
					{cardEffect(temp,false,-1);}
				else
				{
					acceptEffect=true;//�����ȡ�����������ƣ����ܶԷ��Ƶ�Ч��
				    cardEffect(humanShownCard,true,-1);    
    		    }
    			break;
    		}
    		case 12://����
    		{
    			if((temp=allCards.searchCards(1, false))!=-1)
    				{cardEffect(temp,false,-1);}
				else
				{
					acceptEffect=true;//�����ȡ�����������ƣ����ܶԷ��Ƶ�Ч��
			        cardEffect(humanShownCard,true,-1);	
				}
    			break;
    		}
    		case 13://�赶ɱ��
    		{
    			 if((temp=allCards.searchCards(1, false))!=-1)
    				{cardEffect(temp,false,-1);}
				else
				{
					acceptEffect=true;//�����ȡ�����������ƣ����ܶԷ��Ƶ�Ч��
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
    
    //�ж�ai���ƣ���������������
    public void aiHandCardAction(int sort)
    {
    	int temp=-1;
    	if((temp=allCards.searchCards(sort, false))!=-1)
			{cardEffect(temp,false,-1);aiShownCard=temp;}
    }
    
    //whoΪtrue������ҵ�hero���������򷵻ص��Ե�hero����
    public hero getPlayerPart(boolean who)
    {
    	return(who?humanPlayer:aiPlayer);
    }
  
    //���¼�¼�ոճ�����
    public void setPlayerShownCard(boolean who,int num){
    	if(who)
    		humanShownCard=num;
    	else
    		aiShownCard=num;
    }
    //���ظոճ�������
    public int getPlayerShownCard(boolean who){
    	return(who?humanShownCard:aiShownCard);
    }
    //������ľ���
    
    public void weaponAction1(){
    	String humanAttackWeapon=allCards.getWeaponName(allCards.getEquip(0,true));
    	if(humanAttackWeapon=="����˫�ɽ�"){
    		if(humanPlayer.getHeroSex()!=aiPlayer.getHeroSex()){
    		new AlertDialog.Builder(layoutMain.getContext())
	        .setMessage("�Ƿ�ʹ������")
	        .setPositiveButton("��", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					allCards.getCards(1, true, true);
				}
    		})
	        .setNegativeButton("��", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					dialogOption.cancel();
				}
			}).show();
    		}
    	}
    }
    
    public void weaponAction2(){
    	String humanAttackWeapon=allCards.getWeaponName(allCards.getEquip(0,true));

    	if(humanAttackWeapon=="���µ�" ){
    		if(allCards.getIsWeaponOn() && allCards.getCardSort(getPlayerShownCard(false))==2 && allCards.getCardSort(getPlayerShownCard(true))==1)
            {
    			//���Լ�����ɱ������һ���ж��Ƿ����������ɱ�ı���isContinue
    			isContinue=true;
    			if(allCards.searchCards(1, true)!=-1){
    			 new AlertDialog.Builder(layoutMain.getContext())
 		        .setMessage("�Ƿ�ʹ���������µ�")
 		        .setPositiveButton("��", new DialogInterface.OnClickListener() {
 					
 					public void onClick(DialogInterface dialog, int which) {
 						cardEffect(allCards.searchCards(1, true),true,-1);
 						aiAction();
 						weaponAction2();
 					}
 				})
 		        .setNegativeButton("��", new DialogInterface.OnClickListener() {
 					
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
    
	//�ж��Ƿ���Ҫ����и�ɻ�,����ֵΪtrue����Ҫ��
	public void isToShowWuXieKeJi(boolean who){
		final int temp=allCards.searchCards(8, who);
			if(temp==-1){
				return;
			}
			else {//�����
				isWuXie=!isWuXie;
				if(who==false){
					allCards.showChosenCard(temp,false);
					setPlayerShownCard(false,temp);	
					if(getPlayerPart(false).getHeroNumber()==8)//����Ӣ�ļ���
				    	allCards.getCards(1,who,false);
					isToShowWuXieKeJi(true);
					return;
				}
				else if(who==true)//���Ϊ�������
					{
					new AlertDialog.Builder(this)
					.setMessage("�Ƿ�ʹ����и�ɻ���")
					.setPositiveButton("��", new DialogInterface.OnClickListener() {
						
						public void onClick(DialogInterface dialog, int which) {
							allCards.showChosenCard(temp,true);
							setPlayerShownCard(true,temp);
							if(getPlayerPart(true).getHeroNumber()==8)//����Ӣ�ļ���
						    	allCards.getCards(1,true,true);
							isToShowWuXieKeJi(false);
							return;
						}
					})
					.setNegativeButton("��", new DialogInterface.OnClickListener() {						
						
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
							isWuXie=!isWuXie;
						}
					}).show();
					}
			}
	}
	//���ý�������ʾ��Ѫ��
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
    
  //ÿ�غ��׽����ж�������
    public void Begin(boolean who){
    	
    	if(isEnd==true){
    		allCards.humanShowCardField.removeAllViews();
        	allCards.aiShowCardField.removeAllViews();
        	isContinue=true;
        	allCards.setIsRemove(false);
        	response=false;
        	//�غϿ�ʼ
    	if(who)
    	{
    		allCards.getAnimation(33);
    		order=true;
    		//�ж�
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
            			//����;
            			isLightning=false;
            			setLightning(true);
            			decreaseBlood(3,true);	    
            		}
            		else
            			setLightning(false); 
            	}
            	else{//��и�ɻ�Ч��
            		isLightning=false;
            		setLightning(false); 
            		isWuXie=false;
            	}
            }
            if(humanPlayer.getHeroNumber()==2)//�缧
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
            if(humanPlayer.getHeroNumber()==3)//����
            {
                new AlertDialog.Builder(this)
            .setMessage("�Ƿ�ʹ�����¼���").setPositiveButton("��", new DialogInterface.OnClickListener() {
                
                public void onClick(DialogInterface dialog, int which) {
                allCards.isHumanHeroOn=true;
                allCards.getCards(1,true,true);
            }
            }).setNegativeButton("��",  new DialogInterface.OnClickListener() {
                
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
    	
    	//����ǵ��ԵĻغϿ�ʼ
    	else
    	{
    		order=false;
    		//�ж�
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
            			//����;
            			decreaseBlood(3,false);
            			isLightning=false;
            			setLightning(false);    
            		}
            		else
                        setLightning(true);
                }
            	else{//��и�ɻ�Ч��
            		isLightning=false;
            		setLightning(true); 
            		isWuXie=false;
            	}
                
            }
            if(aiPlayer.getHeroNumber()==2)//�缧
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
            else if(aiPlayer.getHeroNumber()==2)//����
            {
                 if(allCards.searchCards(1, false)!=-1)
                 {
                	 allCards.isAiHeroOn=true;
                     allCards.getCards(1,false,true);
                 }
             }
            else if(!allCards.isAiHeroOn || aiPlayer.getHeroNumber()==2)//����
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


public void onBackPressed() //��д���̵�back��ť
{
	
	AlertDialog whereToGo=new AlertDialog.Builder(this).create();
	whereToGo.setButton("������Ϸ",new DialogInterface.OnClickListener() {
		
		
		public void onClick(DialogInterface dialog, int which) {
		}
	});
	whereToGo.setButton2("���˵�",new DialogInterface.OnClickListener() {
		
		
		public void onClick(DialogInterface dialog, int which) {
			Intent intent=new Intent(game.this,welcome.class);
			startActivity(intent);
			game.this.finish();
		}
	});
	whereToGo.setButton3("�˳���Ϸ",new DialogInterface.OnClickListener() {
		
		
		public void onClick(DialogInterface dialog, int which) {
			game.this.finish();	
		}
	});
	whereToGo.show();
	
	return;
}
}