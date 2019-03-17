package com.example.testchart1;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class MainActivity extends Activity {

	MYChart chart;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		chart = (MYChart) findViewById(R.id.chart);

		List<Smartband_sleepCalitem> data= new ArrayList<Smartband_sleepCalitem>();
		data.add(new Smartband_sleepCalitem("2015-06-05 22:00:00", "2015-06-05 23:30:00", Smartband_sleepCalitem.quality_light));
		data.add(new Smartband_sleepCalitem("2015-06-05 23:31:00", "2015-06-06 01:30:00", Smartband_sleepCalitem.quality_deep));
		data.add(new Smartband_sleepCalitem("2015-06-06 01:31:00", "2015-06-06 03:30:00", Smartband_sleepCalitem.quality_light));
		data.add(new Smartband_sleepCalitem("2015-06-06 03:31:00", "2015-06-06 04:30:00", Smartband_sleepCalitem.quality_deep));
		data.add(new Smartband_sleepCalitem("2015-06-06 04:31:00", "2015-06-06 04:50:00", Smartband_sleepCalitem.quality_light));
		data.add(new Smartband_sleepCalitem("2015-06-06 04:51:00", "2015-06-06 05:30:00", Smartband_sleepCalitem.quality_deep));
		data.add(new Smartband_sleepCalitem("2015-06-06 05:31:00", "2015-06-06 06:30:00", Smartband_sleepCalitem.quality_light));
		chart.setData(data);

	}
}
