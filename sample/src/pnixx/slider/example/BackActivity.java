package pnixx.slider.example;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import pnixx.slider.SlideListener;

/**
 * User: nixx
 * Date: 27.08.15
 * Time: 14:26
 * Contact: http://vk.com/djnixx
 */
public class BackActivity extends Activity {

	private SlideListener.ToLeft slider;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cancel);

		View submit_layout = findViewById(R.id.submit_layout);
		LinearLayout submit = (LinearLayout) findViewById(R.id.submit);

		slider = new SlideListener.ToLeft(submit_layout, submit, new Runnable() {
			@Override
			public void run() {
				finish();
			}
		});
	}
}