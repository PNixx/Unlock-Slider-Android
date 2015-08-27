package pnixx.slider;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.drawable.AnimationDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * User: nixx
 * Date: 14.08.15
 * Time: 16:28
 * Contact: http://vk.com/djnixx
 */
public abstract class SlideListener {

	protected final View layout;
	protected final LinearLayout submit;
	protected final Runnable runnable;
	protected float downX;
	protected int submit_width;
	protected AnimationDrawable animation;
	protected boolean enable = true;

	//Конструктор
	public SlideListener(View layout, LinearLayout submit, Runnable runnable) {
		this.layout = layout;
		this.submit = submit;
		this.runnable = runnable;

		//Загружаем анимацию
		ImageView image = (ImageView) layout.findViewById(R.id.animation);
		animation = (AnimationDrawable) image.getDrawable();
		startAnimation();
	}

	public void stopAnimation() {
		animation.stop();
	}

	public void startAnimation() {
		animation.start();
	}

	//Отключение
	public void disable(int drawable) {
		enable = false;
		setSubmitBackground(drawable);
		stopAnimation();
	}

	//Включение
	public void enable(int drawable) {
		enable = true;
		setSubmitBackground(drawable);
		startAnimation();
	}

	//Указываем цвет кнопки
	private void setSubmitBackground(int drawable) {
		submit.setBackgroundResource(drawable);
	}

	//Устанавливаем листенер
	protected void setOnTouchListener(View.OnTouchListener listener) {
		//Биндим тач по layout
		layout.setOnTouchListener(listener);
	}

	//Возвращает кнопку обратно на место
	public abstract void buttonMoveBack();

	/**
	 * Возвращает кнопку обратно на место
	 * @param from     Число откуда считаем
	 * @param to       Число куда вернуть
	 * @param listener Колбек на обновление
	 */
	protected void buttonMoveBack(int from, int to, final AnimationListener listener) {
		submit.setLayerType(View.LAYER_TYPE_HARDWARE, null);

		//Инициализируем анимацию
		ValueAnimator a = ValueAnimator.ofInt(from, to);

		//Добавляем прослушку на обновление
		a.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator valueAnimator) {
				listener.update((int) valueAnimator.getAnimatedValue());
			}
		});

		//Добавляем прослушку на завершение
		a.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				submit.setLayerType(View.LAYER_TYPE_NONE, null);
			}
		});
		a.setDuration(300);
		a.start();
	}

	//Колбек обновления
	private interface AnimationListener {
		void update(int i);
	}

	/**
	 * Слайдер слева на право
	 */
	public static final class ToRight extends SlideListener implements View.OnTouchListener {

		//Constructor
		public ToRight(View layout, LinearLayout submit, Runnable runnable) {
			super(layout, submit, runnable);

			//Устанавливаем листенер
			setOnTouchListener(this);
		}

		//Возвращает кнопку обратно на место
		public void buttonMoveBack() {

			//Получаем параметры
			final RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) submit.getLayoutParams();
			buttonMoveBack(params.leftMargin, 0, new AnimationListener() {
				@Override
				public void update(int i) {
					params.leftMargin = i;
					submit.requestLayout();
				}
			});
		}

		@Override
		public boolean onTouch(View view, MotionEvent e) {
			if( !enable ) {
				return true;
			}

			//Получаем параметры
			final RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) submit.getLayoutParams();

			//Изменение положения кнопки
			final AnimationListener listener = new AnimationListener() {
				@Override
				public void update(int i) {
					params.leftMargin = i;
					submit.requestLayout();
				}
			};

			//Действия
			switch( e.getAction() ) {

				case MotionEvent.ACTION_DOWN:
					if( params.leftMargin == 0 ) {
						downX = e.getX();
						submit_width = submit.getWidth();
					} else {
						downX = -1;
					}
					return true;

				case MotionEvent.ACTION_MOVE:
					if( downX != -1 ) {
						float dX = e.getX() - downX;
						if( dX > 0 && dX < (float) view.getWidth() * .98f - (float) submit_width ) {
							listener.update((int) dX);
						}

						//Если дотянули до конца, создаем заказ
						if( dX > (float) view.getWidth() * .95f - (float) submit_width ) {
							downX = -1;
							listener.update((int) ((float) view.getWidth() * .97f - (float) submit_width));
							runnable.run();
							return true;
						}
					}
					break;

				case MotionEvent.ACTION_UP:
					if( downX != -1 ) {
						buttonMoveBack(params.leftMargin, 0, listener);
					}
					break;
			}

			return false;
		}
	}

	public static final class ToLeft extends SlideListener implements View.OnTouchListener {

		//Constructor
		public ToLeft(View layout, LinearLayout submit, Runnable runnable) {
			super(layout, submit, runnable);

			//Устанавливаем листенер
			setOnTouchListener(this);
		}

		//Возвращает кнопку обратно на место
		public void buttonMoveBack() {

			//Получаем параметры
			final RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) submit.getLayoutParams();
			buttonMoveBack(params.rightMargin, 0, new AnimationListener() {
				@Override
				public void update(int i) {
					params.rightMargin = i;
					submit.requestLayout();
				}
			});
		}

		@Override
		public boolean onTouch(View view, MotionEvent e) {
			if( !enable ) {
				return true;
			}

			//Получаем параметры
			final RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) submit.getLayoutParams();

			//Изменение положения кнопки
			final AnimationListener listener = new AnimationListener() {
				@Override
				public void update(int i) {
					params.rightMargin = i;
					submit.requestLayout();
				}
			};

			//Действия
			switch( e.getAction() ) {

				case MotionEvent.ACTION_DOWN:
					if( params.rightMargin == 0 ) {
						downX = e.getX();
						submit_width = submit.getWidth();
					} else {
						downX = -1;
					}
					return true;

				case MotionEvent.ACTION_MOVE:
					if( downX != -1 ) {
						float dX = e.getX() - downX;
						if( -dX > 0 && -dX < (float) view.getWidth() * .98f - (float) submit_width ) {
							listener.update(-(int) dX);
						}

						//Если дотянули до конца, создаем заказ
						if( -dX > (float) view.getWidth() * .95f - (float) submit_width ) {
							downX = -1;
							listener.update((int) ((float) view.getWidth() * .98f - (float) submit_width));
							runnable.run();
							return true;
						}
					}
					break;

				case MotionEvent.ACTION_UP:
					if( downX != -1 ) {
						//Создаем анимацию
						buttonMoveBack(params.rightMargin, 0, listener);
					}
					break;
			}

			return false;
		}
	}
}
