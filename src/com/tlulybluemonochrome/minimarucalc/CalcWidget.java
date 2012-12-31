package com.tlulybluemonochrome.minimarucalc;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

public class CalcWidget extends AppWidgetProvider {

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);

		// サービスの起動
		Intent intent = new Intent(context, CalcService.class);
		context.startService(intent);

	}
}
