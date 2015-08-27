#Unlock Slider for Android

![2015-08-27_13-48-22](https://cloud.githubusercontent.com/assets/1117351/9518820/a8b07d4c-4cc2-11e5-977c-828bbd24ddac.png)

Simple example use:

	slider = new SlideListener.ToRight(submit_layout, submit, new Runnable() {
		@Override
		public void run() {
			startActivity(new Intent(MainActivity.this, BackActivity.class));
		}
	});
	
