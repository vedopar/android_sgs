package com.sgs;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;
import android.widget.LinearLayout;
import android.content.Intent;

public class gameResult extends Activity 
implements View.OnClickListener{

	Intent intent;
	boolean result;
	LinearLayout outline;
	TextView message;
	@Override
	public void onCreate(Bundle icicle){
		super.onCreate(icicle);
		setContentView(R.layout.game_result);
		
		intent=this.getIntent();
		result=intent.getBooleanExtra("isVictory",true);//�õ����ݹ����ı�����Ϣ
		
		message=(TextView)findViewById(R.id.result_messsage);
		if(!result)
			message.setText("�ܱ�");
		else 
			message.setText("ʤ��");
		
		outline=(LinearLayout)findViewById(R.id.result_layout);
		outline.setOnClickListener(this);
	}
	
	public void onClick(View v){

		intent=new Intent(gameResult.this,welcome.class);
		startActivity(intent);
		gameResult.this.finish();
	}

}