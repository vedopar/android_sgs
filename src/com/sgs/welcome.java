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
import android.widget.LinearLayout;


public class welcome extends Activity {
    /** Called when the activity is first created. */
	LinearLayout layout_login;
	Button login;
	Button rules;
	Button exit;
	AlertDialog dialog_exit;
	AlertDialog dialog_rules;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.welcome);
        
        layout_login = (LinearLayout)this.findViewById(R.id.layout_login);
        layout_login.setBackgroundResource(R.drawable.background_1); 
        login = (Button)this.findViewById(R.id.login);
        rules =( Button)this.findViewById(R.id.rules);
        exit =( Button)this.findViewById(R.id.exit);       
		dialog_exit = new AlertDialog.Builder(this).create();
		dialog_rules = new AlertDialog.Builder(this).create();
		
	    login.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(welcome.this, chooseHero.class);
	            startActivity(intent); 
	            welcome.this.finish();
			}
		});
		
        rules.setOnClickListener(new View.OnClickListener(){
        	@Override
        	public void onClick(View v){
        		dialog_rules.show();
        	}
        });
		
        exit.setOnClickListener(new View.OnClickListener(){
        	@Override
        	public void onClick(View v){
        		dialog_exit.show();
        	}
        });
		
		dialog_exit.setMessage("确认退出？");
		dialog_exit.setButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				System.exit(0);
			}
		});
		dialog_exit.setButton2("取消",new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog_exit.cancel();
			}
		});
		
		dialog_rules.setMessage(getResources().getString(R.string.rules));//读取strings.xml文件里面的规则
		
		dialog_rules.setButton("返回", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog_rules.cancel();
			}
		});
		
    }
}