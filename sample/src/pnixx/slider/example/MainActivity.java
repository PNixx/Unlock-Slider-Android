package pnixx.slider.example;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import pnixx.slider.SlideListener;

public class MainActivity extends Activity implements View.OnClickListener {

	private SlideListener.ToRight slider;

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		View submit_layout = findViewById(R.id.submit_layout);
		LinearLayout submit = (LinearLayout) findViewById(R.id.submit);

		slider = new SlideListener.ToRight(submit_layout, submit, new Runnable() {
			@Override
			public void run() {
				startActivityForResult(new Intent(MainActivity.this, BackActivity.class), 1);
			}
		});

		findViewById(R.id.disable).setOnClickListener(this);
		findViewById(R.id.enable).setOnClickListener(this);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		slider.buttonMoveBack();
	}

	@Override
	public void onClick(View view) {
		switch( view.getId() ) {
			case R.id.enable:
				slider.enable(R.drawable.button);
				break;

			case R.id.disable:
				slider.disable(R.drawable.button_disable);
				break;
		}
	}
}
