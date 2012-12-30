package com.tlulybluemonochrome.minimarucalc;

import java.math.BigDecimal;

import android.os.Bundle;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	BigDecimal buf = BigDecimal.valueOf(0); // バッファ
	BigDecimal result = BigDecimal.valueOf(0); // 計算結果
	char calc = 0; // 四則演算の符号用
	int dig = 0; // 小数点以下の桁数保持用
	boolean i = false; // 演算ボタン用フラグ
	boolean error = false; // エラーフラグ
	TextView text; // 表示出力
	private static final int MENU_ID_MENU1 = (Menu.FIRST + 1); // メニュー1
	private static final int MENU_ID_MENU2 = (Menu.FIRST + 2); // メニュー2

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/* Intentでthemeを変更 */
		Intent intent = getIntent();
		int theme = intent.getIntExtra("com.tlulybluemonochrome.minimarucalc",
				android.R.style.Theme_Holo_Light);
		setTheme(theme);
		setContentView(R.layout.activity_main);
		text = (TextView) findViewById(R.id.editText1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.activity_main, menu);
		menu.add(Menu.NONE, MENU_ID_MENU1, Menu.NONE, "Light theme");
		menu.add(Menu.NONE, MENU_ID_MENU2, Menu.NONE, "Dark theme");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		/* 自分にIntentを投げてActivity再起動 */
		boolean ret = true;
		Intent intent = new Intent(this, this.getClass());
		switch (item.getItemId()) {
		default:
			ret = super.onOptionsItemSelected(item);
			break;
		case MENU_ID_MENU1:
			Toast.makeText(this, "Setting Lightness", Toast.LENGTH_SHORT)
					.show();
			ret = true;
			intent.putExtra("com.tlulybluemonochrome.minimarucalc",
					android.R.style.Theme_Holo_Light);
			startActivity(intent);
			finish();
			break;
		case MENU_ID_MENU2:
			Toast.makeText(this, "Setting Darkness", Toast.LENGTH_SHORT).show();
			ret = true;
			startActivity(new Intent(this, this.getClass()));
			intent.putExtra("com.tlulybluemonochrome.minimarucalc",
					android.R.style.Theme_Holo);
			startActivity(intent);
			finish();
			break;
		}
		return ret;

	}

	/* クリア定義 */
	public void clickButton_AC(View v) {
		result = BigDecimal.valueOf(0);
		buf = BigDecimal.valueOf(0);
		dig = 0;
		calc = 0;
		i = false;
		error = false;
		text.setText(buf.toPlainString());
	}

	public void clickButton_C(View v) {
		buf = BigDecimal.valueOf(0);
		dig = 0;
		calc = 0;
		text.setText(buf.toPlainString());
	}

	/* 数字ボタン定義 */
	public void clickButton_figure(View v) {
		BigDecimal figure = BigDecimal.valueOf(0);
		i = true;
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

		if (error == true) {
			text.setText("error");
			Toast.makeText(this, "エラー", Toast.LENGTH_SHORT).show();
		}

		/* 桁数制限 */
		else if (buf == buf.max(BigDecimal.valueOf(1E9))
				|| buf == buf.min(BigDecimal.valueOf(-1E9)))
			text.setText(buf.toPlainString());

		/* 整数計算 */
		else if (dig == 0) {
			buf = (buf.movePointRight(1)).add(figure);
			text.setText(buf.toPlainString());
		}

		/* 小数計算 */
		else {
			buf = buf.add(figure.movePointLeft(dig));
			dig++;
			text.setText(buf.toPlainString());
		}

	}

	/* 小数点ボタン定義 */
	public void clickButton_dot(View v) {
		if (dig == 0)
			dig = 1;
		text.setText(buf.toPlainString() + ".");
	}

	/* 演算ボタン定義 */
	public void clickButton_calc(View v) {
		// クリック時の処理
		// 型変換

		// 保持してる演算符号に従って演算
		if (i == true) {
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
				if (buf == BigDecimal.valueOf(0))
					error = true;
				else
					result = result.divide(buf, 11, BigDecimal.ROUND_HALF_UP);
				break;
			}
			/* 桁数制限 */
			if (result == result.max(BigDecimal.valueOf(1E10))
					|| result == result.min(BigDecimal.valueOf(-1E10))) {
				error = true;
			}
			i = false;
		}

		if (error == true) {
			text.setText("error");
			Toast.makeText(this, "エラー", Toast.LENGTH_SHORT).show();
		} else {
			/* 桁数制限 */
			result = result.setScale(10, BigDecimal.ROUND_HALF_EVEN);

			/* 末尾0を消す */
			result = result.stripTrailingZeros();

			buf = BigDecimal.valueOf(0);// 入力リセット
			dig = 0;// 小数点リセット
			text.setText(result.toPlainString());// 結果表示
		}

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
			calc = 0;
			break;
		}

	}

	/* コピーボタン */
	public void clickButton_copy(View v) {
		// クリップボードに格納するItemを作成
		ClipData.Item item;
		if (i == true) {
			item = new ClipData.Item(buf.toPlainString());
		} else {
			item = new ClipData.Item(result.toPlainString());
		}

		// MIMETYPEの作成
		String[] mimeType = new String[1];
		mimeType[0] = ClipDescription.MIMETYPE_TEXT_URILIST;

		// クリップボードに格納するClipDataオブジェクトの作成
		ClipData cd = new ClipData(new ClipDescription("text_data", mimeType),
				item);

		// クリップボードにデータを格納
		ClipboardManager cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
		cm.setPrimaryClip(cd);

		Toast.makeText(this, "コピーしました", Toast.LENGTH_SHORT).show();

	}

	/* 貼り付けボタン */
	public void clickButton_paste(View v) {
		// システムのクリップボードを取得
		ClipboardManager cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

		// クリップボードからClipDataを取得
		ClipData cd = cm.getPrimaryClip();

		// クリップデータからItemを取得
		if (cd != null) {
			ClipData.Item item = cd.getItemAt(0);
			buf = new BigDecimal((String) item.getText());
			text.setText(buf.toPlainString());
			i = true;
		}

		Toast.makeText(this, "ペーストしました", Toast.LENGTH_SHORT).show();

	}

}
