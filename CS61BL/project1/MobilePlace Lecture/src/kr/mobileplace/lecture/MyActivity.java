package kr.mobileplace.lecture;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MyActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myactivity);
		Button button = (Button) findViewById(R.id.button);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				TextView t = (TextView) findViewById(R.id.text);
				t.setText("Å¬¸¯µÊ~");
				t.setBackgroundColor(0xFFFF0000);
				t.setGravity(Gravity.LEFT);
			}
		});
	}
}
