package com.tlulybluemonochrome.minimarucalc;

import java.math.BigDecimal;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import android.widget.Toast;

public class MainActivity extends Activity {

	BigDecimal buf = BigDecimal.valueOf(0); // バッファ
	BigDecimal result = BigDecimal.valueOf(0); // 計算結果
	int calc = 0; // 四則演算の符号用
	int dig = 0; // 小数点以下の桁数保持用
	int i = 0; // 演算ボタン用フラグ
	TextView text; // 表示出力

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// android.os.Debug.waitForDebugger();
		/* Preferencesからテーマ設定 */
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		String thme_preference = sharedPreferences.getString("theme_list",
				"Light");
		int theme = android.R.style.Theme_Holo_Light;
		if (thme_preference.equals("Light"))
			theme = android.R.style.Theme_Holo_Light;
		else if (thme_preference.equals("Dark"))
			theme = android.R.style.Theme_Holo;
		setTheme(theme);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		text = (TextView) findViewById(R.id.editText1);

		boolean save;
		if (save = sharedPreferences.getBoolean("save_checkbox", true)) {
			/* セーブデータ取得 */
			SharedPreferences sp = PreferenceManager
					.getDefaultSharedPreferences(this);

			try {
				buf = new BigDecimal(sp.getString("SaveBuf", "0"));
			} catch (NumberFormatException e) {
				buf = BigDecimal.valueOf(0);
			}
			try {
				result = new BigDecimal(sp.getString("SaveResult", "0"));
			} catch (NumberFormatException e) {
				result = BigDecimal.valueOf(0);
			}
			i = sp.getInt("SaveI", 0);
			calc = sp.getInt("SaveCalc", 0);
			dig = sp.getInt("SaveDig", 0);

			/* 末尾0を消す */
			buf = buf.stripTrailingZeros();
			if (buf.doubleValue() == 0)
				buf = BigDecimal.ZERO;
			result = result.stripTrailingZeros();
			if (result.doubleValue() == 0)
				result = BigDecimal.ZERO;

			if (i >= 1)
				text.setText(buf.toPlainString());
			else
				text.setText(result.toPlainString());
		}

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (i == -1) {
			result = BigDecimal.valueOf(0);
			buf = BigDecimal.valueOf(0);
			dig = 0;
			calc = 0;
			i = 0;
		}
		/* データセーブ */
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(this);
		sp.edit().putString("SaveBuf", buf.toPlainString()).commit();
		sp.edit().putString("SaveResult", result.toPlainString()).commit();
		sp.edit().putInt("SaveI", i).commit();
		sp.edit().putInt("SaveCalc", calc).commit();
		sp.edit().putInt("SaveDig", dig).commit();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		boolean ret = true;
		switch (item.getItemId()) {
		default:
			ret = super.onOptionsItemSelected(item);
			break;
		case R.id.menu_settings:
			/* 設定画面呼び出し */
			ret = true;
			Intent intent = new Intent(this, (Class<?>) SettingsActivity.class);
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
		i = 0;
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
		if (i == -1) {
			text.setText("error");
			Toast.makeText(this, "エラー", Toast.LENGTH_SHORT).show();
			return;
		} else if (i != 1) {
			buf = BigDecimal.valueOf(0);
			dig = 0;// 小数点リセット
			if (i == -2)
				calc = 0;
		}
		i = 1;

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

		/* 桁数制限 */
		if (buf == buf.max(BigDecimal.valueOf(1E9))
				|| buf == buf.min(BigDecimal.valueOf(-1E9)) || buf.scale() > 8)
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
		if (i == -1) {
			text.setText("error");
			Toast.makeText(this, "エラー", Toast.LENGTH_SHORT).show();
			return;
		}

		// 保持してる演算符号に従って演算
		else if (i >= 1 || v.getId() == R.id.button_equal) {
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
					text.setText("error");
					Toast.makeText(this, "エラー", Toast.LENGTH_SHORT).show();
					return;
				} else
					result = result.divide(buf, 11, BigDecimal.ROUND_HALF_UP);
				break;
			}
			/* 桁数制限 */
			if (result == result.max(BigDecimal.valueOf(1E10))
					|| result == result.min(BigDecimal.valueOf(-1E10))) {
				i = -1;
				text.setText("error");
				Toast.makeText(this, "エラー", Toast.LENGTH_SHORT).show();
				return;
			}
		}
		i = 0;
		/* 桁数制限 */
		result = result.setScale(10, BigDecimal.ROUND_HALF_EVEN);

		/* 末尾0を消す */
		result = result.stripTrailingZeros();
		if (result.doubleValue() == 0)
			result = BigDecimal.ZERO;

		text.setText(result.toPlainString());// 結果表示

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
			i = -2;
			break;
		}

	}

	/* コピーボタン */
	public void clickButton_copy(View v) {
		// クリップボードに格納するItemを作成
		ClipData.Item item;
		if (i >= 1) {
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
			try {
				buf = new BigDecimal((String) item.getText());
			} catch (NumberFormatException e) {
				Toast.makeText(this, "ペーストできませんでした", Toast.LENGTH_SHORT).show();
				return;
			}

			/* 桁数制限 */
			buf = buf.setScale(10, BigDecimal.ROUND_HALF_EVEN);

			/* 末尾0を消す */
			buf = buf.stripTrailingZeros();
			if (buf.doubleValue() == 0)
				buf = BigDecimal.ZERO;

			text.setText(buf.toPlainString());
			i = 2;
		}

		Toast.makeText(this, "ペーストしました", Toast.LENGTH_SHORT).show();

	}

}
