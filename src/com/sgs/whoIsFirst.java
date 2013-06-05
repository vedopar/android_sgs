package com.sgs;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class whoIsFirst extends Activity { 
	
	ImageButton image1;
	ImageButton image2;
	
	Intent intent;
	
	hero aiPlayer;
	hero humanPlayer;
	private boolean humanFirst;
	
	@Override
	public void onCreate(Bundle icicle) {
	super.onCreate(icicle);
	setContentView(R.layout.who_is_first);
	setTitle("先手权");
	
	intent=this.getIntent();
	aiPlayer=intent.getParcelableExtra("aiPlayer");
	humanPlayer=intent.getParcelableExtra("humanPlayer");
	
	image1=(ImageButton)findViewById(R.id.image1);
	image1.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			randomOrderAndJump(image1,v);			
			
		}
	});
	image2=(ImageButton)findViewById(R.id.image2);
	image2.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			randomOrderAndJump(image2,v);	
		}
	});
	}
	
	
	void randomOrderAndJump(ImageButton image,View v)//跳转到下一界面  
	{
		java.util.Random random=new java.util.Random(); 
		if(Math.abs(random.nextInt())%2==0)
		{
			image.setImageResource(R.drawable.zhugong);
			humanFirst=true;
		}
		else
		{
			image.setImageResource(R.drawable.fanzei);
			humanFirst=false;
		}
				
				Bundle bundle=new Bundle(intent.getExtras());		
				bundle.putBoolean("order",humanFirst);
				intent=new Intent(whoIsFirst.this, game.class);
				intent.putExtras(bundle);
				startActivity(intent);
				whoIsFirst.this.finish();
		
	}
}