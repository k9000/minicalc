package com.tlulybluemonochrome.minimarucalc;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

	double buf = 0; // バッファ
	double result = 0; // 計算結果
	char calc = 1; // 四則演算の符号用
	int dig = 0; // 小数点以下の桁数保持用
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

	/* クリア定義 */
	public void clickButton_AC(View v) {
		result = 0;
		buf = 0;
		dig = 0;
		calc = 1;
		setFigure(buf,text);
	}

	public void clickButton_C(View v) {
		buf = 0;
		dig = 0;
		setFigure(buf,text);
	}

	/* 数字ボタン定義 */
	public void clickButton_figure(View v) {
		int figure = 0;
		// クリック時の処理
		switch (v.getId()) {
		case R.id.button1:
			figure = 1;
			break;
		case R.id.button2:
			figure = 2;
			break;
		case R.id.button3:
			figure = 3;
			break;
		case R.id.button4:
			figure = 4;
			break;
		case R.id.button5:
			figure = 5;
			break;
		case R.id.button6:
			figure = 6;
			break;
		case R.id.button7:
			figure = 7;
			break;
		case R.id.button8:
			figure = 8;
			break;
		case R.id.button9:
			figure = 9;
			break;
		case R.id.button0:
			figure = 10;
			break;
		}

		if (dig != 0) { // 小数点計算
			dig = dig * 10;
			buf = buf + ((double) figure / (double) dig);
		} else
			buf = buf * 10 + (double) figure; // 整数計算
		setFigure(buf,text);
	}

	/* 小数点ボタン定義 */
	public void clickButton_dot(View v) {
		if (dig == 0)
			dig = 1;
		setFigure(buf,text);
	}

	/* 演算ボタン定義 */
	public void clickButton_calc(View v) {
		// クリック時の処理

		// 保持してる演算符号に従って演算
		switch (calc) {
		case 1:
			// 加算
			result = result + buf;
			break;
		case 2:
			// 減算
			result = result - buf;
			break;
		case 3:
			// 掛け算
			result = result * buf;
			break;
		case 4:
			// 割り算
			result = result / buf;
			break;
		}

		buf = 0;// 入力リセット
		dig = 0;// 小数点リセット
		setFigure(result,text);// 結果表示

		// 演算符号の保持
		switch (v.getId()) {
		case R.id.button_add:
			// 加算
			calc = 1;
			break;
		case R.id.button_sub:
			// 減算
			calc = 2;
			break;
		case R.id.button_mul:
			// 掛け算
			calc = 3;
			break;
		case R.id.button_div:
			// 割り算
			calc = 1;
			break;
		case R.id.button_equal:
			// イコール
			result = 0;// とりあえずリセット
			break;
		}

	}

	public void setFigure(double buf,TextView text) {
		if (buf == (int) buf)
			text.setText(String.valueOf((int) buf));
		else
			text.setText(String.valueOf(buf));
	}

}
