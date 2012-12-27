package com.tlulybluemonochrome.minimarucalc;

import java.math.BigDecimal;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

	BigDecimal buf = BigDecimal.valueOf(0); // バッファ
	BigDecimal result = BigDecimal.valueOf(0); // 計算結果
	char calc = 0; // 四則演算の符号用
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
		result = BigDecimal.valueOf(0);
		buf = BigDecimal.valueOf(0);
		dig = 0;
		calc = 1;
		setFigure(buf);
	}

	public void clickButton_C(View v) {
		buf = BigDecimal.valueOf(0);
		;
		dig = 0;
		setFigure(buf);
	}

	/* 数字ボタン定義 */
	public void clickButton_figure(View v) {
		BigDecimal figure = BigDecimal.valueOf(0);
		// クリック時の処理
		switch (v.getId()) {
		case R.id.button1:
			figure = BigDecimal.valueOf(1);
			break;
		case R.id.button2:
			figure = BigDecimal.valueOf(2);
			break;
		case R.id.button3:
			figure = BigDecimal.valueOf(3);
			break;
		case R.id.button4:
			figure = BigDecimal.valueOf(4);
			break;
		case R.id.button5:
			figure = BigDecimal.valueOf(5);
			break;
		case R.id.button6:
			figure = BigDecimal.valueOf(6);
			break;
		case R.id.button7:
			figure = BigDecimal.valueOf(7);
			break;
		case R.id.button8:
			figure = BigDecimal.valueOf(8);
			break;
		case R.id.button9:
			figure = BigDecimal.valueOf(9);
			break;
		case R.id.button0:
			figure = BigDecimal.valueOf(0);
			break;
		}


		/* 整数計算 */
		if (dig == 0)
			buf = (buf.movePointRight(1)).add(figure);

		/* 小数計算 */
		else {
			buf = buf.add(figure.movePointLeft(dig));
			dig++;
		}

		setFigure(buf);
	}

	/* 小数点ボタン定義 */
	public void clickButton_dot(View v) {
		if (dig == 0)
			dig = 1;
		setFigure(buf);
	}

	/* 演算ボタン定義 */
	public void clickButton_calc(View v) {
		// クリック時の処理
		// 型変換

		// 保持してる演算符号に従って演算
		switch (calc) {
		case 0:
			// なにもしない
			result = buf;
			break;
		case 1:
			// 加算
			result = result.add(buf);
			break;
		case 2:
			// 減算
			result = result.subtract(buf);
			break;
		case 3:
			// 掛け算
			result = result.multiply(buf);
			break;
		case 4:
			// 割り算
			result = result.divide(buf, 20, BigDecimal.ROUND_HALF_UP);
			break;
		}

		/* 末尾0を消す */
		int i = result.scale();
		while (result == result.max(result.setScale(i, BigDecimal.ROUND_UP))) {
			result = result.setScale(i);
			i--;
		}

		buf = BigDecimal.valueOf(0);// 入力リセット
		dig = 0;// 小数点リセット
		setFigure(result);// 結果表示

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
			calc = 4;
			break;
		case R.id.button_equal:
			// イコール
			buf = result;
			result = BigDecimal.valueOf(0);
			calc = 1;// とりあえずリセット
			break;
		}

	}

	public void setFigure(BigDecimal buf) {
		String temp;
		temp = String.valueOf(buf);

		text.setText(temp);
	}

}
