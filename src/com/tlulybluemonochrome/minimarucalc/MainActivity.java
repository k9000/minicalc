package com.tlulybluemonochrome.minimarucalc;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

	double buf = 0;
	double result = 0;
	char calc = 1;
	boolean dot = false;
	TextView text;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		text = (TextView) findViewById(R.id.editText1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	public void clickButton_AC(View v){
		result = 0;
		buf = 0;
		calc = 1;
		text.setText(String.valueOf(buf));
	}
	
	public void clickButton_C(View v){
		buf = 0;
		text.setText(String.valueOf(buf));
	}
	
	public void clickButton0(View v){
		buf = buf * 10 + 0;
		text.setText(String.valueOf(buf));
	}
	
	public void clickButton1(View v){
		buf = buf * 10 + 1;
		text.setText(String.valueOf(buf));
	}
	
	public void clickButton2(View v){
		buf = buf * 10 + 2;
		text.setText(String.valueOf(buf));
	}
	
	public void clickButton3(View v){
		buf = buf * 10 + 3;
		text.setText(String.valueOf(buf));
	}
	
	public void clickButton4(View v){
		buf = buf * 10 + 4;
		text.setText(String.valueOf(buf));
	}
	
	public void clickButton5(View v){
		buf = buf * 10 + 5;
		text.setText(String.valueOf(buf));
	}
	
	public void clickButton6(View v){
		buf = buf * 10 + 6;
		text.setText(String.valueOf(buf));
	}
	
	public void clickButton7(View v){
		buf = buf * 10 + 7;
		text.setText(String.valueOf(buf));
	}
	
	public void clickButton8(View v){
		buf = buf * 10 + 8;
		text.setText(String.valueOf(buf));
	}
	
	public void clickButton9(View v){
		buf = buf * 10 + 9;
		text.setText(String.valueOf(buf));
	}
	
	public void clickButton_dot(View v){
		dot = true;
		text.setText(String.valueOf(buf));
	}
	
	public void clickButton_add(View v){
		result = calc(calc,buf,result);
		calc = 1;
		buf = 0;
		text.setText(String.valueOf(result));
	}
	

	public void clickButton_sub(View v){
		result = calc(calc,buf,result);
		calc = 2;
		buf = 0;
		text.setText(String.valueOf(result));
	}
	
	public void clickButton_mul(View v){
		result = calc(calc,buf,result);
		calc = 3;
		buf = 0;
		text.setText(String.valueOf(result));
	}

	public void clickButton_div(View v){
		result = calc(calc,buf,result);
		calc = 4;
		buf = 0;
		text.setText(String.valueOf(result));
	}
	
	public void clickButton_equal(View v){
		result = calc(calc,buf,result);
		buf = 0;
		text.setText(String.valueOf(result));
	}
	
	private double calc(char a,double b,double c) {
		double d = 0;

		if(a==1)d = c + b;
		else if(a==2)d = c - b;
		else if(a==3)d = c * b;
		else if(a==4)d = c / b;

		return d;
	}
}
