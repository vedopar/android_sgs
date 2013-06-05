package com.sgs;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.view.View;
import android.widget.LinearLayout;
import android.content.Intent;

public class finalDecision extends Activity 
implements View.OnClickListener{
	
	ImageView aiHero;
	ImageView humanHero;
	LinearLayout outline;
	Intent intent;
	
	hero humanPlayer;
	hero aiPlayer;
	
	
	public void onCreate(Bundle icicle){
		super.onCreate(icicle);
		setContentView(R.layout.final_decision);
		
		aiHero=(ImageView)findViewById(R.id.aiHero);
		humanHero=(ImageView)findViewById(R.id.humanHero);
		
		intent=this.getIntent();
		humanPlayer=(hero) intent.getParcelableExtra("humanPlayer");//得到传递过来的双方英雄信息
		aiPlayer=(hero) intent.getParcelableExtra("aiPlayer");
		
		aiHero.setImageDrawable(aiHero.getResources().getDrawable(aiPlayer.getHeroBust()));//设置英雄头像
		humanHero.setImageDrawable(humanHero.getResources().getDrawable(humanPlayer.getHeroBust()));
		
		outline=(LinearLayout)findViewById(R.id.outline);
		outline.setOnClickListener(this);
	}
	
	public void onClick(View v){

		Bundle bundle=new Bundle(intent.getExtras());
		intent=new Intent(finalDecision.this,whoIsFirst.class);
		intent.putExtras(bundle);
		startActivity(intent);
		finalDecision.this.finish();
	}

}