package k25.datle.gymania.Utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.widget.Toast;

public abstract class CustomCountDownTimer {


	/**
	 * Millis since epoch when alarm should stop.
	 */
	private long mMillisInFuture;

	/**
	 * The interval in millis that the user receives callbacks
	 */
	private long mCountdownInterval;

	private long mStopTimeInFuture;

	private long mMilisLeft;


	/**
	 * @param millisInFuture
	 *            The number of millis in the future from the call to
	 *            {@link #start()} until the countdown is done and
	 *            {@link #onFinish()} is called.
	 * @param countDownInterval
	 *            The interval along the way to receive {@link #onTick(long)}
	 *            callbacks.
	 */

	private boolean isPaused = false;
	private boolean isInit = false;
	private boolean isFinished = false;

	public CustomCountDownTimer(long millisInFuture, long countDownInterval) {
		mMillisInFuture = millisInFuture;
		mCountdownInterval = countDownInterval;
		//mCtx = ctx;
	}

	/**
	 * Cancel the countdown.
	 */
	public final void cancel() {
		if (isInit == true) {
			mHandler.removeMessages(MSG);
		}
	}

	/**
	 * Start the countdown.
	 */
	public final synchronized CustomCountDownTimer start() {
		if (mMillisInFuture <= 0) {
			onFinish();
			return this;
		}
		if (isInit == false) {
			mStopTimeInFuture = SystemClock.elapsedRealtime() + mMillisInFuture;
			mHandler.sendMessage(mHandler.obtainMessage(MSG));
			isInit = true;
		} else if (isPaused == true){
			resume();
		}
		return this;
	}

	/**
	 * Callback fired on regular interval.
	 * 
	 * @param millisUntilFinished
	 *            The amount of time until finished.
	 */
	public void onTick(long millisUntilFinished) {
		
	}

	/**
	 * Callback fired when the time is up.
	 */
	public void onFinish() {		
		cancel();
		isFinished = true;
	}
	
	public boolean isFinished() {
		return isFinished;
	}

	public void pause() {
		mMilisLeft = mStopTimeInFuture - SystemClock.elapsedRealtime();
		isPaused = true;
	}

	public void resume() {
		isPaused = false;
		mStopTimeInFuture = SystemClock.elapsedRealtime() + mMilisLeft;
		mHandler.sendMessage(mHandler.obtainMessage(MSG));
	}

	public void renewTimer(long millisInFuture,long countDownInterval) {
		mMillisInFuture = millisInFuture;
		mCountdownInterval = countDownInterval;
		isInit = false;
		isFinished = false;
		isPaused = false;
		mHandler.obtainMessage(MSG);
	}
	
	private static final int MSG = 1;

	public String getTime() {
		return Long.toString(mMillisInFuture/1000);
	}
	// handles counting down
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			synchronized (CustomCountDownTimer.this) {
				if (isPaused == false) {
					final long millisLeft = mStopTimeInFuture
							- SystemClock.elapsedRealtime();

					if (millisLeft <= 0) {
						onFinish();
					} else if (millisLeft < mCountdownInterval) {
						// no tick, just delay until done
						sendMessageDelayed(obtainMessage(MSG), millisLeft);
					} else {
						long lastTickStart = SystemClock.elapsedRealtime();
						onTick(millisLeft);

						// take into account user's onTick taking time to
						// execute
						long delay = lastTickStart + mCountdownInterval
								- SystemClock.elapsedRealtime();

						// special case: user's onTick took more than interval
						// to
						// complete, skip to next interval
						while (delay < 0)
							delay += mCountdownInterval;

						sendMessageDelayed(obtainMessage(MSG), delay);
					}
				}
			}
		}
	};
}
