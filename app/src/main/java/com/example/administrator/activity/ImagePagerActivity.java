/*******************************************************************************
 * Copyright 2011-2013 Sergey Tarasevich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.example.administrator.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.administrator.R;
import com.example.administrator.entity.Picture;
import com.example.administrator.util.ImageUitl;
import com.example.administrator.util.UIUtil;
import com.smartandroid.sa.zUImageLoader.core.DisplayImageOptions;
import com.smartandroid.sa.zUImageLoader.core.ImageLoader;
import com.smartandroid.sa.zUImageLoader.core.assist.FailReason;
import com.smartandroid.sa.zUImageLoader.core.assist.ImageScaleType;
import com.smartandroid.sa.zUImageLoader.core.display.FadeInBitmapDisplayer;
import com.smartandroid.sa.zUImageLoader.core.listener.SimpleImageLoadingListener;
import com.smartandroid.sa.zmImageview.ZoomImageView;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * 这个是加载大图的类
 */
public class ImagePagerActivity extends AppCompatActivity {

	private static final String STATE_POSITION = "STATE_POSITION";
	public static final  String IMAGES = "images";
	public static final  String IMAGE_POSITION = "image_position";
	public static final String IS_DELETE = "is_delete";
	DisplayImageOptions options;

	ViewPager pager;
	ImageLoader imageLoader;
	private boolean isdelete=false;
	private ImagePagerAdapter imagePagerAdapter;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//改变状态栏的颜色
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			Window window = this.getWindow();
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.setStatusBarColor(this.getResources().getColor(R.color.black));
		}
		setContentView(R.layout.ac_image_pager);
		ImageUitl.init(ImagePagerActivity.this);
		imageLoader = ImageLoader.getInstance();
		Bundle bundle = getIntent().getExtras();
		assert bundle != null;
		Picture[] imageUrls = (Picture[]) bundle.getSerializable(IMAGES);
		int pagerPosition = bundle.getInt(IMAGE_POSITION, 0);
		if (savedInstanceState != null) {
			pagerPosition = savedInstanceState.getInt(STATE_POSITION);
		}
		isdelete = bundle.getBoolean(IS_DELETE,false);
		options = new DisplayImageOptions.Builder()
			.showImageForEmptyUri(R.mipmap.ic_empty)
			.showImageOnFail(R.mipmap.ic_error)
			.resetViewBeforeLoading(true)
			.cacheOnDisk(true)
			.imageScaleType(ImageScaleType.EXACTLY)
			.bitmapConfig(Bitmap.Config.RGB_565)
			.considerExifParams(true)
			.displayer(new FadeInBitmapDisplayer(300))
			.build();
		pager = (ViewPager) findViewById(R.id.pager);
		imagePagerAdapter = new ImagePagerAdapter(imageUrls);
		pager.setAdapter(imagePagerAdapter);
		pager.setCurrentItem(pagerPosition);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putInt(STATE_POSITION, pager.getCurrentItem());
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if(event.getAction()==KeyEvent.ACTION_UP&&
				event.getKeyCode()==KeyEvent.KEYCODE_BACK){
			Intent intent = new Intent();
			intent.putExtra(SendFriensLoopActivity.IMAGE_PICTURE_LIST,imagePagerAdapter.getData());
			setResult(RESULT_OK,intent);
			ImagePagerActivity.this.finish();
		}
		return super.dispatchKeyEvent(event);
	}

	private class ImagePagerAdapter extends PagerAdapter {
		private Picture[] images;
		private LayoutInflater inflater;
		ImagePagerAdapter(Picture[] images) {
			this.images = images;
			inflater = getLayoutInflater();
		}
		public Picture[] getData(){
			return images;
		}
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
		@Override
		public int getCount() {
			return images.length;
		}

		@Override
		public Object instantiateItem(final ViewGroup view,final int position) {
			final View imageLayout = inflater.inflate(R.layout.item_pager_image, view, false);
			assert imageLayout != null;
			ZoomImageView imageView = (ZoomImageView) imageLayout.findViewById(R.id.image);
			if (isdelete) {
				imageLayout.findViewById(R.id.del_layout).setVisibility(View.VISIBLE);
				imageLayout.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent();
						intent.putExtra(SendFriensLoopActivity.IMAGE_PICTURE_LIST,images);
						setResult(RESULT_OK,intent);
						ImagePagerActivity.this.finish();
					}
				});
				imageLayout.findViewById(R.id.del_btn).setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						//移除掉删除的界面
						ArrayList<Picture> pictureArrayList = new ArrayList<>(Arrays.asList(images));
						pictureArrayList.remove(pictureArrayList.size()-1-position);
						//如果图片都被移除光了，就退出
						if(pictureArrayList.size()==0){
							Intent intent = new Intent();
							intent.putExtra(SendFriensLoopActivity.IMAGE_PICTURE_LIST,new Picture[]{});
							setResult(RESULT_OK,intent);
							ImagePagerActivity.this.finish();
							return;
						}
						images = new Picture[pictureArrayList.size()];
						pictureArrayList.toArray(images);
						imagePagerAdapter.notifyDataSetChanged();
						pager.setAdapter(imagePagerAdapter);
					}
				});
			}
			switch (images[images.length-1-position].getType()){
				case URL_TYPE://如果是网络图片
					showUrlImage(imageView,position,imageLayout);
					break;
				case LOCAL_TYPE://如果是本地图片
					showLocalImage(imageView,images[images.length-1-position].getSmallUrl());
					break;
				case MIPMAP_TYPE://如果是mipmap
					showMipmapImage(imageView,images[images.length-1-position].getSmallUrl());
					break;
			}
			view.addView(imageLayout, 0);
			return imageLayout;
		}

		/**
		 * 显示网络上的图片
		 * @param imageView
		 * @param position
         * @param imageLayout
         */
		private void showUrlImage(ZoomImageView imageView,int position,View imageLayout){
			final ProgressBar spinner = (ProgressBar) imageLayout.findViewById(R.id.loading);
			imageView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					ImagePagerActivity.this.finish();
				}
			});
			//异步加载图片
			imageLoader.displayImage(images[images.length-1-position].getOriginUrl(), imageView, options, new SimpleImageLoadingListener() {
				@Override
				public void onLoadingStarted(String imageUri, View view) {
					spinner.setVisibility(View.VISIBLE);
				}
				@Override
				public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
					String message = null;
					switch (failReason.getType()) {
						case IO_ERROR:
							message = "读取异常";
							break;
						case DECODING_ERROR:
							message = "无法解码图像";
							break;
						case NETWORK_DENIED:
							message = "下载被拒绝";
							break;
						case OUT_OF_MEMORY:
							message = "内存不足，无法查看";
							break;
						case UNKNOWN:
							message = "未知错误";
							break;
					}
					Toast.makeText(ImagePagerActivity.this, message, Toast.LENGTH_SHORT).show();
					spinner.setVisibility(View.GONE);
				}
				@Override
				public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
					spinner.setVisibility(View.GONE);
				}
			});

		}
		/**
		 * 显示本地上的图片
		 */
		public void showLocalImage(ZoomImageView imageView, String s){
			UIUtil.showLocalImage(imageView,s,null);
		}
		/**
		 * 显示本地上的图片
		 */
		public void showMipmapImage(ZoomImageView imageView, String s){
			UIUtil.showMipmapImage(imageView,s,null);
			try {
				//如果刚好是登录页面的图片就跳到登录页面
				if(R.mipmap.class.getDeclaredField(s).getInt(null)==R.mipmap.denglv){
					Intent intent = new Intent(ImagePagerActivity.this,LoginMainActivity.class);
					startActivity(intent);
					ImagePagerActivity.this.finish();
                }
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			}
		}
		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view.equals(object);
		}

		@Override
		public void restoreState(Parcelable state, ClassLoader loader) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}
	}
}