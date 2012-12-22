package com.tlulybluemonochrome.minimarucalc;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

	double buf = 0;		//バッファ
	double result = 0;	//計算結果
	char calc = 1;		//四則演算の符号用
	int dig = 0;		//小数点以下の桁数保持用
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
	
	/*	数字ボタン定義 */
	public void clickButton_AC(View v){
		result = 0;
		buf = 0;
		dig = 0;
		calc = 1;
		text.setText(String.valueOf(buf));
	}
	
	public void clickButton_C(View v){
		buf = 0;
		dig = 0;
		text.setText(String.valueOf(buf));
	}
	
	public void clickButton0(View v){
		if(dig!=0){						//小数点計算
			dig = dig * 10;
			buf = buf + (0/(double)dig);
		}
		else buf = buf * 10 + 0;		//実数計算
		text.setText(String.valueOf(buf));
	}
	
	public void clickButton1(View v){
		if(dig!=0){
			dig = dig * 10;
			buf = buf + (1/(double)dig);
		}
		else buf = buf * 10 + 1;
		text.setText(String.valueOf(buf));
	}
	
	public void clickButton2(View v){
		if(dig!=0){
			dig = dig * 10;
			buf = buf + (2/(double)dig);
		}
		else buf = buf * 10 + 2;
		text.setText(String.valueOf(buf));
	}
	
	public void clickButton3(View v){
		if(dig!=0){
			dig = dig * 10;
			buf = buf + (3/(double)dig);
		}
		else buf = buf * 10 + 3;
		text.setText(String.valueOf(buf));
	}
	
	public void clickButton4(View v){
		if(dig!=0){
			dig = dig * 10;
			buf = buf + (4/(double)dig);
		}
		else buf = buf * 10 + 4;
		text.setText(String.valueOf(buf));
	}
	
	public void clickButton5(View v){
		if(dig!=0){
			dig = dig * 10;
			buf = buf + (5/(double)dig);
		}
		else buf = buf * 10 + 5;
		text.setText(String.valueOf(buf));
	}
	
	public void clickButton6(View v){
		if(dig!=0){
			dig = dig * 10;
			buf = buf + (6/(double)dig);
		}
		else buf = buf * 10 + 6;
		text.setText(String.valueOf(buf));
	}
	
	public void clickButton7(View v){
		if(dig!=0){
			dig = dig * 10;
			buf = buf + (7/(double)dig);
		}
		else buf = buf * 10 + 7;
		text.setText(String.valueOf(buf));
	}
	
	public void clickButton8(View v){
		if(dig!=0){
			dig = dig * 10;
			buf = buf + (8/(double)dig);
		}
		else buf = buf * 10 + 8;
		text.setText(String.valueOf(buf));
	}
	
	public void clickButton9(View v){
		if(dig!=0){
			dig = dig * 10;
			buf = buf + (9/(double)dig);
		}
		else buf = buf * 10 + 9;
		text.setText(String.valueOf(buf));
	}
	
	public void clickButton_dot(View v){
		if(dig==0)dig = 1;
		text.setText(String.valueOf(buf));
	}
	
	/* 演算ボタン定義 */
	public void clickButton_add(View v){
		result = calc(calc,buf,result);
		calc = 1;
		buf = 0;
		dig = 0;
		text.setText(String.valueOf(result));
	}
	

	public void clickButton_sub(View v){
		result = calc(calc,buf,result);
		calc = 2;
		buf = 0;
		dig = 0;
		text.setText(String.valueOf(result));
	}
	
	public void clickButton_mul(View v){
		result = calc(calc,buf,result);
		calc = 3;
		buf = 0;
		dig = 0;
		text.setText(String.valueOf(result));
	}

	public void clickButton_div(View v){
		result = calc(calc,buf,result);
		calc = 4;
		buf = 0;
		dig = 0;
		text.setText(String.valueOf(result));
	}
	
	public void clickButton_equal(View v){
		result = calc(calc,buf,result);
		buf = 0;
		dig = 0;
		text.setText(String.valueOf(result));
	}
	
	/* 四則演算 */
	private double calc(char a,double b,double c) {
		double d = 0;

		if(a==1)d = c + b;
		else if(a==2)d = c - b;
		else if(a==3)d = c * b;
		else if(a==4)d = c / b;

		return d;
	}
}
