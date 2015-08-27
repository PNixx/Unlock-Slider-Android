#Unlock Slider for Android

Simple example use:

	slider = new SlideListener.ToRight(submit_layout, submit, new Runnable() {
		@Override
		public void run() {
			startActivity(new Intent(MainActivity.this, BackActivity.class));
		}
	});
	
