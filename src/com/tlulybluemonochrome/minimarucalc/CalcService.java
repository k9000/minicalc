package com.tlulybluemonochrome.minimarucalc;

import java.math.BigDecimal;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

public class CalcService extends Service {
	final String FIGURE = "com.tlulybluemonochrome.minimarucalc.BUTTON_CLICK_FIGURE";
	final String DOT = "com.tlulybluemonochrome.minimarucalc.BUTTON_CLICK_DOT";
	final String CALC = "com.tlulybluemonochrome.minimarucalc.BUTTON_CLICK_CALC";
	final String CLEAR = "com.tlulybluemonochrome.minimarucalc.BUTTON_CLICK_CLEAR";

	BigDecimal buf = BigDecimal.valueOf(0); // バッファ
	BigDecimal result = BigDecimal.valueOf(0); // 計算結果
	char calc = 0; // 四則演算の符号用
	int dig = 0; // 小数点以下の桁数保持用
	boolean i = false; // 演算ボタン用フラグ
	boolean error = false; // エラーフラグ

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);

		BigDecimal figure = BigDecimal.valueOf(0);

		/* ボタンのidを配列に */
		int[] fig = { R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
				R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9 };
		int[] cal = { R.id.btnEqual, R.id.btnAdd, R.id.btnSub, R.id.btnMul,
				R.id.btnDiv, };

		/* リモートビューインスタンスを取得 */
		RemoteViews remoteViews = new RemoteViews(getPackageName(),
				R.layout.widget_main);

		// ボタンが押された時に発行されるインテントを準備する
		Intent[] buttonIntentF = new Intent[fig.length];
		Intent[] buttonIntentC = new Intent[cal.length];
		PendingIntent[] pendingIntentF = new PendingIntent[fig.length];
		PendingIntent[] pendingIntentC = new PendingIntent[cal.length];

		Intent buttonIntentD = new Intent(DOT);
		PendingIntent pendingIntentD = PendingIntent.getService(this, 0,
				buttonIntentD, 0);
		remoteViews.setOnClickPendingIntent(R.id.btnDot, pendingIntentD);

		Intent buttonIntentAC = new Intent(CLEAR);
		PendingIntent pendingIntentAC = PendingIntent.getService(this, 0,
				buttonIntentAC, 0);
		remoteViews.setOnClickPendingIntent(R.id.btnCLR, pendingIntentAC);

		for (int j = 0; j < buttonIntentF.length; j++) {
			buttonIntentF[j] = new Intent(FIGURE);
			buttonIntentF[j].putExtra(FIGURE, j);
			pendingIntentF[j] = PendingIntent.getService(this, j,
					buttonIntentF[j], PendingIntent.FLAG_UPDATE_CURRENT);
			remoteViews.setOnClickPendingIntent(fig[j], pendingIntentF[j]);
		}
		for (int k = 0; k < buttonIntentC.length; k++) {
			buttonIntentC[k] = new Intent(CALC);
			buttonIntentC[k].putExtra(CALC, k);
			pendingIntentC[k] = PendingIntent.getService(this, k,
					buttonIntentC[k], PendingIntent.FLAG_UPDATE_CURRENT);
			remoteViews.setOnClickPendingIntent(cal[k], pendingIntentC[k]);
		}

		// ボタンが押された時に発行されたインテントで処理
		/* クリア定義 */
		if (CLEAR.equals(intent.getAction())) {
			result = BigDecimal.valueOf(0);
			buf = BigDecimal.valueOf(0);
			dig = 0;
			calc = 0;
			i = false;
			error = false;
			remoteViews.setTextViewText(R.id.textView1, buf.toPlainString());

			/* 数字ボタン定義 */
		} else if (FIGURE.equals(intent.getAction())) {
			int test = intent.getIntExtra(FIGURE, 0);

			figure = BigDecimal.valueOf(test);
			i = true;

			if (error == true) {
				remoteViews.setTextViewText(R.id.textView1, "error");
			}

			/* 桁数制限 */
			else if (buf == buf.max(BigDecimal.valueOf(1E9))
					|| buf == buf.min(BigDecimal.valueOf(-1E9))
					|| buf.scale() > 8)
				remoteViews
						.setTextViewText(R.id.textView1, buf.toPlainString());

			/* 整数計算 */
			else if (dig == 0) {
				buf = (buf.movePointRight(1)).add(figure);
				remoteViews
						.setTextViewText(R.id.textView1, buf.toPlainString());
			}

			/* 小数計算 */
			else {
				buf = buf.add(figure.movePointLeft(dig));
				dig++;
				remoteViews
						.setTextViewText(R.id.textView1, buf.toPlainString());
			}

			/* 小数点ボタン定義 */
		} else if (DOT.equals(intent.getAction())) {
			if (dig == 0)
				dig = 1;
			remoteViews.setTextViewText(R.id.textView1, buf.toPlainString()
					+ ".");

			/* 演算ボタン定義 */
		} else if (CALC.equals(intent.getAction())) {
			int calcbuf = intent.getIntExtra(CALC, 0);

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
					if (buf.doubleValue() == 0)
						error = true;
					else
						result = result.divide(buf, 11,
								BigDecimal.ROUND_HALF_UP);
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
				remoteViews.setTextViewText(R.id.textView1, "error");
			} else {
				/* 桁数制限 */
				result = result.setScale(10, BigDecimal.ROUND_HALF_EVEN);

				/* 末尾0を消す */
				result = result.stripTrailingZeros();
				if (result.doubleValue() == 0)
					result = BigDecimal.ZERO;

				buf = BigDecimal.valueOf(0);// 入力リセット
				dig = 0;// 小数点リセット
				remoteViews.setTextViewText(R.id.textView1,
						result.toPlainString());// 結果表示
			}

			// 演算符号の保持
			calc = (char) calcbuf;

		}

		// AppWidgetの画面更新
		ComponentName thisWidget = new ComponentName(this, CalcWidget.class);
		AppWidgetManager manager = AppWidgetManager.getInstance(this);
		manager.updateAppWidget(thisWidget, remoteViews);

		return START_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO: Return the communication channel to the service.
		// throw new UnsupportedOperationException("Not yet implemented");
		return null;
	}

}
