package com.example.dipon.tabviewdemo.main.UI_utility;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Patterns;
import android.widget.ImageView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.signature.StringSignature;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 *@author Dipon
 *  on 17/5/2017.
 */
@SuppressWarnings("unused")
public class ImageUtil {

    public enum Quality {
        HIGH, LOW, VERY_LOW, AS_IS, THUMB
    }

    public static void setImageButTextImageOnException(Activity activity, final String imagePath, ImageView imageView, final String name) {
        if (imagePath == null || imagePath.trim().equals("")) {
            TextImage.setTextImage(name, imageView);
            return;
        }
        File file = new File(imagePath);
        final WeakReference<ImageView> imageViewWeakReference = new WeakReference<ImageView>(imageView);
        Glide.with(activity)
                .load(imagePath)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .signature(new StringSignature(String.valueOf(file.lastModified())))
                .into(new BitmapImageViewTarget(imageViewWeakReference.get()) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        imageViewWeakReference.get().setImageBitmap(resource);
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        TextImage.setTextImage(name, imageViewWeakReference.get());
                    }
                });


    }

    public static void setImageButTextImageOnException(Fragment fragment, final String imagePath, ImageView imageView, final String name) {
        if (imagePath == null || imagePath.trim().equals("")) {
            TextImage.setTextImage(name, imageView);
            return;
        }
        File file = new File(imagePath);
        final WeakReference<ImageView> imageViewWeakReference = new WeakReference<ImageView>(imageView);
        Glide.with(fragment)
                .load(imagePath)
                .asBitmap()
                .override(160,120)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .signature(new StringSignature(String.valueOf(file.lastModified())))
                .into(new BitmapImageViewTarget(imageViewWeakReference.get()) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        imageViewWeakReference.get().setImageBitmap(resource);
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        TextImage.setTextImage(name, imageViewWeakReference.get());
                    }
                });


    }


    public static void setImageButDefaultImageOnException(Fragment fragment, final String imagePath, ImageView imageView, final String name, final int defaultId) {
        if (imagePath == null || imagePath.trim().equals("")) {
            if (name == null) {
                imageView.setImageResource(defaultId);
                return;
            }

            TextImage.ColorGenerator colorGenerator = TextImage.ColorGenerator.MATERIAL;
            String textToSet = ImageUtil.TextImage.getSingleAlphaTextForImage(name);
            imageView.setBackgroundColor(colorGenerator.getColor(name == null ? textToSet : name));
            imageView.setImageResource(defaultId);
            imageView.setImageAlpha(50);
            return;
        }
        File file = new File(imagePath);
        final WeakReference<ImageView> imageViewWeakReference = new WeakReference<ImageView>(imageView);
        Glide.with(fragment)
                .load(imagePath)
                .asBitmap()
                .override(350,120)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .signature(new StringSignature(String.valueOf(file.lastModified())))
                .into(new BitmapImageViewTarget(imageViewWeakReference.get()) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        imageViewWeakReference.get().setImageBitmap(resource);
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        TextImage.setTextImage(name, imageViewWeakReference.get());
                    }
                });


    }

    public static void setImage(Fragment fragment, final String imagePath, ImageView imageView) {
        if (imagePath != null) {
            File file = new File(imagePath);
            Glide.with(fragment).load(imagePath).asBitmap().diskCacheStrategy(DiskCacheStrategy.NONE).signature(new StringSignature(String.valueOf(file.lastModified()))).into(imageView);
        }
    }

    public static void setImage(Fragment fragment, int drawableId, ImageView imageView) {
        Glide.with(fragment).load(drawableId).asBitmap().into(imageView);
    }

    public static void setImage(Activity activity, final String imagePath, ImageView imageView) {
        if (imagePath != null) {
            File file = new File(imagePath);
            Glide.with(activity).load(imagePath).asBitmap().diskCacheStrategy(DiskCacheStrategy.NONE).signature(new StringSignature(String.valueOf(file.lastModified()))).into(imageView);
        }
    }

    public static void setImage(Activity activity, int drawableId, ImageView imageView) {
        Glide.with(activity).load(drawableId).asBitmap().into(imageView);
    }

    public static void setImageButDefaultOnException(Fragment fragment, final String imagePath, ImageView imageView, final int defaultImageId) {
        if (imagePath == null || imagePath.trim().equals("")) {
            imageView.setImageResource(defaultImageId);
        }
        final WeakReference<ImageView> imageViewWeakReference = new WeakReference<ImageView>(imageView);
        if (imagePath != null) {
            File file = new File(imagePath);
            Glide.with(fragment).load(imagePath).asBitmap().diskCacheStrategy(DiskCacheStrategy.NONE).signature(new StringSignature(String.valueOf(file.lastModified()))).into(new BitmapImageViewTarget(imageViewWeakReference.get()) {
                @Override
                protected void setResource(Bitmap resource) {
                    imageViewWeakReference.get().setImageBitmap(resource);
                }

                @Override
                public void onLoadFailed(Exception e, Drawable errorDrawable) {
                    imageViewWeakReference.get().setImageResource(defaultImageId);
                }
            });
        }
    }

    public static void setImageButDefaultOnException(Activity activity, final String imagePath, ImageView imageView, final int defaultImageId) {
        if (imagePath == null || imagePath.trim().equals("")) {
            imageView.setImageResource(defaultImageId);
            return;
        }
        final WeakReference<ImageView> imageViewWeakReference = new WeakReference<ImageView>(imageView);
        if (imagePath != null) {
            File file = new File(imagePath);
            Glide.with(activity).load(imagePath).asBitmap().diskCacheStrategy(DiskCacheStrategy.NONE).signature(new StringSignature(String.valueOf(file.lastModified()))).into(new BitmapImageViewTarget(imageViewWeakReference.get()) {
                @Override
                protected void setResource(Bitmap resource) {
                    imageViewWeakReference.get().setImageBitmap(resource);
                }

                @Override
                public void onLoadFailed(Exception e, Drawable errorDrawable) {
                    imageViewWeakReference.get().setImageResource(defaultImageId);
                }
            });
        }
    }

    public static void setBlurredImageButTextImageOnException(Activity activity, int drawableId, ImageView imageView, final Quality quality) {
        final WeakReference<ImageView> imageViewWeakReference = new WeakReference<ImageView>(imageView);
        Glide.with(activity).load(drawableId).asBitmap().into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                resource = changeQuality(quality, resource);
                imageViewWeakReference.get().setImageBitmap(ImageUtil.fastBlur(resource, 7));
            }
        });
    }

    public static void setBlurredImageButTextImageOnException(Activity activity, String photoPath, String name, ImageView imageView, final Quality quality) {

        if (photoPath == null || photoPath.equals("")) {
            TextImage.setTextImage(name, imageView, 512);
            return;
        }
        File file = new File(photoPath);
        final WeakReference<ImageView> imageViewWeakReference = new WeakReference<ImageView>(imageView);
        final WeakReference<String> nameWeakReference = new WeakReference<String>(name);
        Glide.with(activity).load(photoPath).asBitmap().diskCacheStrategy(DiskCacheStrategy.NONE).signature(new StringSignature(String.valueOf(file.lastModified()))).into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                resource = changeQuality(quality, resource);
                imageViewWeakReference.get().setImageBitmap(ImageUtil.fastBlur(resource, 12));
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                Bitmap clearBitmap = TextImage.getTextImageBitmap(nameWeakReference.get(), 512);
                Bitmap blurredBitmap = ImageUtil.fastBlur(clearBitmap, 12);
                imageViewWeakReference.get().setImageBitmap(blurredBitmap);
            }
        });
    }

    private static Bitmap changeQuality(Quality quality, Bitmap bitmap) {
        int width = bitmap.getWidth();
        switch (quality) {
            case AS_IS:
                break;
            case HIGH:
                if (width > 720) {
                    return Bitmap.createScaledBitmap(bitmap, 720, 720, false);
                }
            case LOW:
                if (width > 512) {
                    return Bitmap.createScaledBitmap(bitmap, 512, 512, false);
                }

            case VERY_LOW:
                if (width > 256) {
                    return Bitmap.createScaledBitmap(bitmap, 256, 256, false);
                }
            case THUMB:
                if (width > 96) {
                    return Bitmap.createScaledBitmap(bitmap, 96, 96, false);
                }
            default:
                return bitmap;
        }
        return bitmap;
    }

    public static Bitmap getCircularImage(Context context, Bitmap input) {
        Bitmap output = Bitmap.createBitmap(input.getWidth(),
                input.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, input.getWidth(), input.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        canvas.drawCircle(input.getWidth() / 2, input.getHeight() / 2,
                input.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(input, rect, rect, paint);
        //Bitmap _bmp = Bitmap.createScaledBitmap(output, 60, 60, false);
        //return _bmp;
        return output;
    }

    /**
     * Stack Blur v1.0 from
     * http://www.quasimondo.com/StackBlurForCanvas/StackBlurDemo.html
     * Java Author: Mario Klingemann <mario at quasimondo.com>
     * http://incubator.quasimondo.com
     * <p>
     * created Feburary 29, 2004
     * Android port : Yahel Bouaziz <yahel at kayenko.com>
     * http://www.kayenko.com
     * ported april 5th, 2012
     * <p>
     * This is a compromise between Gaussian Blur and Box blur
     * It creates much better looking blurs than Box Blur, but is
     * 7x faster than my Gaussian Blur implementation.
     * <p>
     * I called it Stack Blur because this describes best how this
     * filter works internally: it creates a kind of moving stack
     * of colors whilst scanning through the image. Thereby it
     * just has to add one new block of color to the right side
     * of the stack and remove the leftmost color. The remaining
     * colors on the topmost layer of the stack are either added on
     * or reduced by one, depending on if they are on the right or
     * on the left side of the stack.
     * <p>
     * If you are using this algorithm in your code please add
     * the following line:
     * Stack Blur Algorithm by Mario Klingemann <mario@quasimondo.com>
     */

    private static Bitmap fastBlur(Bitmap sentBitmap, int radius) {

//        int width = Math.round(sentBitmap.getWidth() * scale);
//        int height = Math.round(sentBitmap.getHeight() * scale);
//        sentBitmap = Bitmap.createScaledBitmap(sentBitmap, width, height, false);

        Bitmap bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);

        if (radius < 1) {
            return (null);
        }

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int[] pix = new int[w * h];
        Log.e("pix", w + " " + h + " " + pix.length);
        bitmap.getPixels(pix, 0, w, 0, 0, w, h);

        int wm = w - 1;
        int hm = h - 1;
        int wh = w * h;
        int div = radius + radius + 1;

        int r[] = new int[wh];
        int g[] = new int[wh];
        int b[] = new int[wh];
        int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
        int vmin[] = new int[Math.max(w, h)];

        int divsum = (div + 1) >> 1;
        divsum *= divsum;
        int dv[] = new int[256 * divsum];
        for (i = 0; i < 256 * divsum; i++) {
            dv[i] = (i / divsum);
        }

        yw = yi = 0;

        int[][] stack = new int[div][3];
        int stackpointer;
        int stackstart;
        int[] sir;
        int rbs;
        int r1 = radius + 1;
        int routsum, goutsum, boutsum;
        int rinsum, ginsum, binsum;

        for (y = 0; y < h; y++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            for (i = -radius; i <= radius; i++) {
                p = pix[yi + Math.min(wm, Math.max(i, 0))];
                sir = stack[i + radius];
                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);
                rbs = r1 - Math.abs(i);
                rsum += sir[0] * rbs;
                gsum += sir[1] * rbs;
                bsum += sir[2] * rbs;
                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }
            }
            stackpointer = radius;

            for (x = 0; x < w; x++) {

                r[yi] = dv[rsum];
                g[yi] = dv[gsum];
                b[yi] = dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (y == 0) {
                    vmin[x] = Math.min(x + radius + 1, wm);
                }
                p = pix[yw + vmin[x]];

                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[(stackpointer) % div];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi++;
            }
            yw += w;
        }
        for (x = 0; x < w; x++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            yp = -radius * w;
            for (i = -radius; i <= radius; i++) {
                yi = Math.max(0, yp) + x;

                sir = stack[i + radius];

                sir[0] = r[yi];
                sir[1] = g[yi];
                sir[2] = b[yi];

                rbs = r1 - Math.abs(i);

                rsum += r[yi] * rbs;
                gsum += g[yi] * rbs;
                bsum += b[yi] * rbs;

                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }

                if (i < hm) {
                    yp += w;
                }
            }
            yi = x;
            stackpointer = radius;
            for (y = 0; y < h; y++) {
                // Preserve alpha channel: ( 0xff000000 & pix[yi] )
                pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16) | (dv[gsum] << 8) | dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (x == 0) {
                    vmin[y] = Math.min(y + r1, hm) * w;
                }
                p = x + vmin[y];

                sir[0] = r[p];
                sir[1] = g[p];
                sir[2] = b[p];

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[stackpointer];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi += w;
            }
        }

//        Log.e("pix", w + " " + h + " " + pix.length);
        bitmap.setPixels(pix, 0, w, 0, 0, w, h);

        return (bitmap);
    }

    /**
     * This method resolves image file path where the image is selected from gallery.
     *
     * @param activity calling activity
     * @param uri      uri that you got from selection from image gallery
     * @return selected image path
     */
    public static String getSelectedPicturePathFromUri(Activity activity, Uri uri) {
        String picturePath = null;
        String[] projection = {MediaStore.Images.Media.DATA};

        Cursor cursor = activity.getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(projection[0]);
            picturePath = cursor.getString(columnIndex);
            cursor.close();
        }
        return picturePath;
    }

    public static class TextImage {
        private static class ColorGenerator{


            private static ColorGenerator DEFAULT;

            private static ColorGenerator MATERIAL;

            static {
                DEFAULT = create(Arrays.asList(
                        0xfff16364,
                        0xfff58559,
                        0xfff9a43e,
                        0xffe4c62e,
                        0xff67bf74,
                        0xff59a2be,
                        0xff2093cd,
                        0xffad62a7,
                        0xff805781
                ));
                MATERIAL = create(Arrays.asList(
                        0xffe57373,
                        0xfff06292,
                        0xffba68c8,
                        0xff9575cd,
                        0xff7986cb,
                        0xff64b5f6,
                        0xff4fc3f7,
                        0xff4dd0e1,
                        0xff4db6ac,
                        0xff81c784,
                        0xffaed581,
                        0xffff8a65,
                        0xffd4e157,
                        0xffffd54f,
                        0xffffb74d,
                        0xffa1887f,
                        0xff90a4ae
                ));
            }

            private final List<Integer> mColors;
            private final Random mRandom;

            public static ColorGenerator create(List<Integer> colorList) {
                return new ColorGenerator(colorList);
            }

            private ColorGenerator(List<Integer> colorList) {
                mColors = colorList;
                mRandom = new Random(System.currentTimeMillis());
            }

            public int getRandomColor() {
                return mColors.get(mRandom.nextInt(mColors.size()));
            }

            public int getColor(Object key) {
                return mColors.get(Math.abs(key.hashCode()) % mColors.size());
            }
        }
        public static boolean isPhoneNumber(String name) {
            return Patterns.PHONE.matcher(name).matches();
        }

        private static String getDoubleAlphaTextForImage(String name) {
            if (name == null || name.equals("")) {
                return "?";
            } else if (name.startsWith("+") && name.length() > 1) {
                return getDoubleAlphaTextForImage(name.substring(1));
            } else {
                String[] items = name.split(" ");
                if (items.length > 1) {
                    return items[0].charAt(0) + "" + items[1].charAt(0);
                } else {
                    return name.charAt(0) + "";
                }
            }
        }

        private static String getSingleAlphaTextForImage(String name) {
            if (name == null || name.equals("")) {
                return "?";
            } else if (name.startsWith("+") && name.length() > 1) {
                return getSingleAlphaTextForImage(name.substring(1));
            } else {
                return name.charAt(0) + "";
            }
        }


        public static void setTextImage(String name, ImageView iv) {
            if (name == null)
                return;
            ColorGenerator generator = ColorGenerator.MATERIAL;
            String textToSet = ImageUtil.TextImage.getSingleAlphaTextForImage(name);
            TextDrawable drawable = TextDrawable
                    .builder()
                    .beginConfig()
                    .width(96)
                    .height(96)
                    .endConfig()
                    .buildRect(textToSet, generator.getColor(name == null ? textToSet : name));
            iv.setImageDrawable(drawable);
        }

        public static void setTextImage(String name, ImageView iv, int size) {
            if (name == null)
                return;
            ColorGenerator generator = ColorGenerator.MATERIAL;
            String textToSet = ImageUtil.TextImage.getSingleAlphaTextForImage(name);
            TextDrawable drawable = TextDrawable
                    .builder()
                    .beginConfig()
                    .width(size)
                    .height(size)
                    .endConfig()
                    .buildRect(textToSet, generator.getColor(name == null ? textToSet : name));
            iv.setImageDrawable(drawable);
        }

        public static Bitmap getTextImageBitmap(String name, int size) {
            ColorGenerator generator = ColorGenerator.MATERIAL;
            String textToSet = ImageUtil.TextImage.getSingleAlphaTextForImage(name);
            TextDrawable drawable = TextDrawable
                    .builder()
                    .beginConfig()
                    .width(size)
                    .height(size)
                    .endConfig()
                    .buildRect(textToSet, generator.getColor(name));
            Bitmap bmp = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bmp);
            drawable.draw(canvas);
            return bmp;
        }
    }


}
