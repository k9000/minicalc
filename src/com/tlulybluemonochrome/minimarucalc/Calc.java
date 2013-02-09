package com.tlulybluemonochrome.minimarucalc;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import android.widget.TextView;

public class Calc {
	
	BigDecimal buf = BigDecimal.valueOf(0); // バッファ
	BigDecimal result = BigDecimal.valueOf(0); // 計算結果
	int calc = 0; // 四則演算の符号用
	int dig = 0; // 小数点以下の桁数保持用
	int i = 0; // 演算ボタン用フラグ
	TextView text; // 表示出力
	DecimalFormat df = new DecimalFormat(".0000000000E0");
	int sigdig;
	
	/* クリア定義 */
	public String AC() {
		result = BigDecimal.valueOf(0);
		buf = BigDecimal.valueOf(0);
		dig = 0;
		calc = 0;
		i = 0;
		return buf.toPlainString();
	}

	public String C() {
		buf = BigDecimal.valueOf(0);
		dig = 0;
		calc = 0;
		return buf.toPlainString();
	}

	/* 数字ボタン定義 */
	public String figure(int id) {
		BigDecimal figure = BigDecimal.valueOf(id);
		if (i == -1) {
			return "error";
		} else if (i != 1) {
			buf = BigDecimal.valueOf(0);
			dig = 0;// 小数点リセット
			if (i == -2)
				calc = 0;
		}
		i = 1;

		// クリック時の処理
		/*
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
		*/

		/* 桁数制限 */
		buf = new BigDecimal(df.format(buf));
		if (buf.scale() <= 0 || buf.scale() - dig < 0)
			return buf.toPlainString();

		/* 整数計算 */
		else if (dig == 0) {
			buf = (buf.movePointRight(1)).add(figure);
		}

		/* 小数計算 */
		else {
			buf = buf.add(figure.movePointLeft(dig));
			dig++;
		}

		/* 末尾0を消す */
		buf = buf.stripTrailingZeros();
		if (buf.doubleValue() == 0)
			buf = BigDecimal.ZERO;

		return buf.toPlainString();
	}

	/* 小数点ボタン定義 */
	public String dot() {
		if (dig == 0)
			dig = 1;
		return buf.toPlainString() + ".";
	}

	/* 演算ボタン定義 */
	public String calc(int mcalc) {
		
		// クリック時の処理
		if (i == -1) {
			return "error";
		}

		// 保持してる演算符号に従って演算
		else if (i >= 1 || mcalc == 0) {
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
				if (buf.doubleValue() == 0) {
					i = -1;
					return "error";
				} else
					result = result.divide(buf, 20, BigDecimal.ROUND_HALF_UP);
				break;
			}
		}
		i = 0;
		/* 桁数制限 */
		result = new BigDecimal(df.format(result));
		if (result.scale() <= -1) {
			i = -1;
			return "error";
		}

		/* 末尾0を消す */
		result = result.stripTrailingZeros();
		if (result.doubleValue() == 0)
			result = BigDecimal.ZERO;

		

		// 演算符号の保持
		switch (mcalc) {
		case 1:
			// 加算
			calc = 1;
			break;
		case 2:
			// 減算
			calc = 2;
			break;
		case 3:
			// 掛け算
			calc = 3;
			break;
		case 4:
			// 割り算
			calc = 4;
			break;
		case 0:
			// イコール
			i = -2;
			break;
		}
		
		return result.toPlainString();// 結果表示

	}
	
	

}
