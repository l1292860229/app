package com.example.administrator.util;

import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.widget.ImageView;

import com.example.administrator.R;

public class MediaManager {

	private static MediaPlayer mMediaPlayer; 
	private static boolean isPause;
	private static String mFilePath;
	private static AnimationDrawable mAnimation;
	private static ImageView imageView;
	/**
	 * 播放音乐
	 * @param filePath
	 * @param onCompletionListener
	 */
	public static void playSound(String filePath,OnCompletionListener onCompletionListener,ImageView animView) {
		if (mMediaPlayer == null) {
			mFilePath = filePath;
			mAnimation = (AnimationDrawable) animView.getDrawable();
			imageView = animView;
			mMediaPlayer = new MediaPlayer();
			//设置一个error监听器
			mMediaPlayer.setOnErrorListener(new OnErrorListener() {

				public boolean onError(MediaPlayer arg0, int arg1, int arg2) {
					mMediaPlayer.reset();
					return false;
				}
			});
		}else{
			if(mFilePath.equals(filePath)){
				if(mMediaPlayer.isPlaying()){
					mMediaPlayer.stop();
					mAnimation.stop();
					imageView.setImageResource(R.mipmap.lvoice3);
					mMediaPlayer = null;
					return;
				}
			}else{
				mAnimation.stop();
				imageView.setImageResource(R.mipmap.lvoice3);
				mAnimation = (AnimationDrawable) animView.getDrawable();
				imageView = animView;
				mFilePath = filePath;
			}
			mMediaPlayer.reset();
		}

		try {
			mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mMediaPlayer.setOnCompletionListener(onCompletionListener);
			mMediaPlayer.setDataSource(mFilePath);
			mMediaPlayer.prepare();
			mMediaPlayer.start();
			mAnimation.start();
		} catch (Exception e) {

		}
	}

	/**
	 * 暂停播放
	 */
	public static void pause() {
		if (mMediaPlayer != null && mMediaPlayer.isPlaying()) { //正在播放的时候
			mMediaPlayer.pause();
			isPause = true;
		}
	}

	/**
	 * 当前是isPause状态
	 */
	public static void stop() {
		if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
			mMediaPlayer.stop();
			mAnimation.stop();
			imageView.setImageResource(R.mipmap.lvoice3);
		}
	}
	/**
	 * 当前是isPause状态
	 */
	public static void resume() {
		if (mMediaPlayer != null && isPause) {
			mMediaPlayer.start();
			isPause = false;
		}
	}
	/**
	 * 释放资源
	 */
	public static void release() {
		if (mMediaPlayer != null) {
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
	}
}
