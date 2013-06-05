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

	AlertDialog choice;//ѡ��Ӣ�������Ľ��ܿ�
	TextView deadTime;//��ʾʣ��ʱ��
	TextView aiChoice;//��ʾ���Ե����ѡ��
	Chronometer counter;//��ʱ��	
	final int timeLength=30;//����ʱ����
	int lTime=timeLength;
	
	
	public final hero[] heros={ 
		new hero(1,"�ܲ�",1,1,4,"1�����ۣ������������ö�" +
				"������˺�����",R.drawable.caocao_icon,R.drawable.caocao_bust),
		new hero(2,"�缧",0,1,3,"1�����������Խ���ĺ�ɫ���Ƶ�" +
				"������ʹ�ã�������\n2�����񣺻غϿ�ʼ�׶Σ������" +
				"�����ж�����Ϊ��ɫ��������ô���Ч����ж��ƣ�����" +
				"���ٴ�ʹ������D�D��˷�����ֱ�����ֺ�ɫ���㲻Ը���ж�" +
				"��Ϊֹ����ʹ�����ʱ�����ı��Ƶ�������ƣ������ã�" +
				"���ƵĻ�ɫ�͵������䡣",R.drawable.zhenji_icon,R.drawable.zhenji_bust),
		new hero(3,"����",1,1,4,
				"���£����ƽ׶Σ����������һ���ƣ�" +
				"����������ûغϵĳ��ƽ׶Σ���ʹ�á�ɱ��" +
				"�򡾾���������Ϊ�˺���Դʱ����ɵ��˺�+1",R.drawable.xuchu_icon,R.drawable.xuchu_bust),
		
		new hero(4,"�Ƹ�",1,2,4,"1�����⣺���ƽ׶Σ������ʧȥһ��������Ȼ����������" +
				"ÿ�غ��У�����Զ��ʹ�ÿ��⡣",R.drawable.huanggai_icon,R.drawable.huanggai_bust),
		new hero(5,"½ѷ",1,2,3,"1��ǫѷ���㲻�ܳ�Ϊ��˳��ǣ�򡿺͡��ֲ�˼�񡿵�Ŀ��" +
				"/n2����Ӫ��ÿ����ʧȥ���һ����ʱ����������һ���ơ�" 
				,R.drawable.luxun_icon,R.drawable.luxun_bust),
		new hero(6,"����",1,2,4,"��Ϯ�����ƽ׶Σ�����Խ��Ժ�һ�ź��һ�÷�����Ƶ������Ӳ��š�ʹ�á�",R.drawable.ganning_icon,R.drawable.ganning_bust),
		
		new hero(7,"����",1,3,4,"����������Խ������Ƶġ�ɱ��������������������" +
				"��ɱ��ʹ�û�������ʹ������ʱ�����ı��Ƶ����(����)�����ã�" +
				"���ƵĻ�ɫ�͵�������",R.drawable.zhaoyun_icon,R.drawable.zhaoyun_bust),
		new hero(8,"����Ӣ",0,3,3,"1�����ǣ�ÿ����ʹ��һ�ŷ���ʱ�����ʱ������������֮ǰ��" +
				"�����������һ����\n2����ţ���ʹ���κν������޾������ơ�",R.drawable.huangyueying_icon,R.drawable.huangyueying_bust),
		new hero(9,"����",1,3,4,"��ʥ������Խ��������һ�ź�ɫ�Ƶ���ɱ��ʹ�û�����" +
				"����ͬʱ�õ�ǰװ���ĺ�ɫװ��Ч��ʱ�����ɰ�����װ���Ƶ���ɱ����ʹ�û�" +
				"�������ʹ����ʥʱ�����ı��Ƶ����(����)�����ã����ƵĻ�ɫ�͵������䡣",R.drawable.guanyu_icon,R.drawable.guanyu_bust),
		new hero(10,"��٢",1,0,3,"1�����ȣ���Ļغ��⣬����Խ����������һ򷽿��Ƶ������ҡ���ʹ��\n" +
				"2�����ң����ƽ׶Σ��������������һ�����ƣ�����һ��ɫ�ظ�1��������ÿ�غ�����һ�Ρ�",R.drawable.huatuo_icon,R.drawable.huatuo_bust),
	};
	
	private hero humanPlayer=null;//��ҽ�ɫ
	private hero aiPlayer=null;//AI��ɫ
	
	
	public void onCreate(Bundle icicle) {
	super.onCreate(icicle);
	setContentView(R.layout.choose_hero);
	setTitle("ѡӢ��");

	aiPlayer=new hero(heros[(int)(Math.random()*10)%10].getThis());
	
	setListAdapter(new IconicAdapter());//ʹ��Class IconicAdapter���廯ÿһ��Ӣ�۽���
	
	
	//��ʱ��
	deadTime = (TextView) findViewById(R.id.deadTime);
	aiChoice = (TextView) findViewById(R.id.aiChoice);
	counter= (Chronometer) findViewById(R.id.chronometer);	
	counter.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener(){
		
		
        public void onChronometerTick(Chronometer count) {
			
			deadTime.setText("ʣ��ʱ�䣺"+String.valueOf(lTime));
			lTime=lTime-1;
			if(lTime==timeLength/2)
			{
				aiChoice.setText("AIӢ�ۣ�"+aiPlayer.getHeroName());
			}
           
            if(lTime<=0) {
            	
            	counter.stop();//ֹͣ��ʱ

            	            	  	
            	humanPlayer=new hero(heros[(int)(Math.random()*10)%10].getThis());//��ұ������ѡ��Ӣ��
            	
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
	
	//ÿһ�е���Ӧ
	public void onListItemClick(ListView parent, View v, int position,
			long id) {
		
		final int pos=position;//���ѡ���Ӣ�۵���ţ������ȷ��Ӣ����
		
		//����Ӣ�۽���
		choice=new AlertDialog.Builder(this).create();
		choice.setTitle(heros[position].getHeroName());
		choice.setMessage(""+heros[position].getHeroName()+"\n������"+heros[position].getHeroSort()
				+"\nѪ����"+heros[position].getBloodLength()+"\n"+heros[position].getHeroIntro());
		choice.setIcon(this.getResources().getDrawable(heros[position].getHeroIcon()));
		choice.setButton("ȡ��",new DialogInterface.OnClickListener() {
			
			
			
			public void onClick(DialogInterface dialog, int which) {
				// ȡ��������
				
			}
		});
		choice.setButton2("ѡ��",new DialogInterface.OnClickListener() {
			
						
			
			public void onClick(DialogInterface dialog, int which) {
				
				humanPlayer=new hero(heros[pos]);//ȷ����ҵ�Ӣ��
				
				Intent intent = new Intent(chooseHero.this, finalDecision.class);//��ת����һ����
				Bundle bundle=new Bundle();		
            	bundle.putParcelable("aiPlayer", aiPlayer);
            	bundle.putParcelable("humanPlayer",humanPlayer);   	
            	intent.putExtras(bundle);
				startActivity(intent);
				chooseHero.this.finish();//�رձ�����
			}
		});
		choice.show();
		
			}
	
	
	//���廯ÿһ��Ӣ�۽���
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
	
	
	public void onBackPressed() //��д���̵�back��ť
	{
		
		AlertDialog whereToGo=new AlertDialog.Builder(deadTime.getContext()).create();
		whereToGo.setButton("������Ϸ",new DialogInterface.OnClickListener() {
			
			
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		whereToGo.setButton2("���˵�",new DialogInterface.OnClickListener() {
			
			
			public void onClick(DialogInterface dialog, int which) {
				Intent intent=new Intent(chooseHero.this,welcome.class);
				startActivity(intent);
				chooseHero.this.finish();
			}
		});
		whereToGo.setButton3("�˳���Ϸ",new DialogInterface.OnClickListener() {
			
			
			public void onClick(DialogInterface dialog, int which) {
				chooseHero.this.finish();	
			}
		});
		whereToGo.show();
		
		return;
	}
	
	

}