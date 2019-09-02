package de.spiritcroc.test;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.service.wallpaper.WallpaperService;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class MainActivity extends Activity {

    private int currentPercentage = 100;
    private boolean reverse = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("RadioAccessFamily", "" + getString(R.string.config_test1));

        Log.d("Color -6", Color.alpha(-6) + "." + Color.red(-6) + "." + Color.green(-6) + "." + Color.blue(-6));
        int test = Integer.MAX_VALUE;
        Log.d("Color " + test, Color.alpha(test) + "." + Color.red(test) + "." + Color.green(test) + "." + Color.blue(test));

        final float[] empty = new float[3];
        final float[] full =   new float[3];

        Color.colorToHSV(Color.parseColor("#FFFF0000"), empty);
        Color.colorToHSV(Color.parseColor("#FF00FF00"), full);

        final Button b1 = (Button) findViewById(R.id.button);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPercentage = currentPercentage + 5;
                if (currentPercentage > 100) {
                    currentPercentage = 0;
                }
                b1.setText((reverse ? "R" : "") + currentPercentage);
                float[] newColor = new float[3];
                for (int i = 0; i < newColor.length; i++) {
                    newColor[i] = empty[i] + ((full[i]-empty[i])*(currentPercentage/100f));
                }
                if (reverse) {
                    if (empty[0] < full[0]) {
                        empty[0] += 360f;
                    }
                    newColor[0] = empty[0] - (empty[0]-full[0])*(currentPercentage/100f);
                } else {
                    if (empty[0] > full[0]) {
                        full[0] += 360f;
                    }
                    newColor[0] = empty[0] + (full[0]-empty[0])*(currentPercentage/100f);
                }
                if (newColor[0] > 360f) {
                    newColor[0] -= 360f;
                } else if (newColor[0] < 0) {
                    newColor[0] += 360f;
                }
                newColor[1] = empty[1] + ((full[1]-empty[1])*(currentPercentage/100f));
                newColor[2] = empty[2] + ((full[2]-empty[2])*(currentPercentage/100f));
                findViewById(R.id.base_layout)
                        .setBackgroundColor(Color.HSVToColor(newColor));
                Log.d("Percentage", "" + currentPercentage);
                Log.d("Color", Arrays.toString(newColor));
            }
        });
        b1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                reverse = !reverse;
                return true;
            }
        });

        findViewById(R.id.button_1).setOnClickListener(new View.OnClickListener() {
            int state = -1;
            @Override
            public void onClick(View v) {
                int resolvedColor = Color.rgb(127, 127, 127);
                int actionBg = Color.rgb(127, 127, 127);
                int notiBg = Color.rgb(127, 127, 127);

                switch (state) {
                    case -1:
                        state++;
                        break;
                    case 0:
                        resolvedColor = Color.BLACK;
                        actionBg = Color.rgb(48, 48, 48);
                        notiBg = Color.rgb(33, 33, 33);
                        state++;
                        break;
                    case 1:
                        resolvedColor = Color.GREEN;
                        actionBg = Color.WHITE;
                        notiBg = Color.WHITE;
                        state++;
                        break;
                    case 2:
                        resolvedColor = Color.BLUE;
                        actionBg = Color.WHITE;
                        notiBg = Color.WHITE;
                        state++;
                        break;
                    case 3:
                        resolvedColor = Color.BLUE;
                        actionBg = Color.BLACK;
                        notiBg = Color.BLACK;
                        state++;
                        break;
                    case 4:
                        resolvedColor = Color.BLUE;
                        actionBg = Color.rgb(33, 33, 33);
                        notiBg = Color.rgb(33, 33, 33);
                        state++;
                        break;
                    case 5:
                        resolvedColor = Color.rgb(183, 28, 28);
                        actionBg = Color.rgb(33, 33, 33);
                        notiBg = Color.rgb(33, 33, 33);
                        state++;
                        break;
                    case 6:
                        resolvedColor = Color.rgb(0, 77, 64);
                        actionBg = Color.rgb(33, 33, 33);
                        notiBg = Color.rgb(33, 33, 33);
                        state++;
                        break;
                    case 7:
                        resolvedColor = Color.WHITE;
                        actionBg = Color.WHITE;
                        notiBg = Color.WHITE;
                        state++;
                        break;
                    case 8:
                        resolvedColor = Color.BLACK;
                        actionBg = Color.BLACK;
                        notiBg = Color.BLACK;
                        state++;
                        break;
                    default:
                        state = 0;
                        break;
                }
                int color = resolvedColor;
                color = ensureLargeTextContrast(color, actionBg, false);
                color = ensureTextContrast(color, notiBg, false);
                int darkColor = resolvedColor;
                darkColor = ensureLargeTextContrast(darkColor, actionBg, true);
                darkColor = ensureTextContrast(darkColor, notiBg, true);
                TextView t1 = (TextView) findViewById(R.id.text_view_1);
                t1.setTextColor(resolvedColor);
                t1.setBackgroundColor(notiBg);
                t1.setText("No algorithm: " + colorToString(resolvedColor));
                TextView t2 = (TextView) findViewById(R.id.text_view_2);
                t2.setTextColor(color);
                t2.setBackgroundColor(notiBg);
                t2.setText("Old light algorithm: " + colorToString(color));
                TextView t3 = (TextView) findViewById(R.id.text_view_3);
                t3.setTextColor(darkColor);
                t3.setBackgroundColor(notiBg);
                t3.setText("New dark algorithm: " + colorToString(darkColor));
            }
        });

        int mScaledIconSize = 48;
        Rect mScaledIconBounds = new Rect(0, 0, mScaledIconSize, mScaledIconSize);

        Drawable icon = getDrawable(R.drawable.teeest);
        Bitmap b = Bitmap.createBitmap(mScaledIconBounds.width(), mScaledIconBounds.height(),
                Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        icon.setBounds(0, 0, c.getWidth(), c.getHeight());
        icon.draw(c);
        icon = new BitmapDrawable(getResources(), b);
        icon.setBounds(mScaledIconBounds);


        ImageView img = new ImageView(this);
        img.setImageDrawable(icon);
        ((ViewGroup) findViewById(R.id.container)).addView(img);

        img = new ImageView(this);
        img.setImageDrawable(getDrawable(R.drawable.teeest));
        ((ViewGroup) findViewById(R.id.container)).addView(img);

        img = new ImageView(this);
        img.setImageDrawable(getDrawable(R.mipmap.ic_launcher));
        ((ViewGroup) findViewById(R.id.container)).addView(img);

        TextView tv = new TextView(this);
        tv.setText("bla");
        Drawable d = getDrawable(R.drawable.teeest);
        d.setBounds(0, 0, c.getWidth(), c.getHeight());
        Log.v("icon", ""+d);
        tv.setCompoundDrawables(null, d, null, null);
        ((ViewGroup) findViewById(R.id.container)).addView(tv);

        tv = new TextView(this);
        tv.setText("bla");
        d = getDrawable(R.mipmap.ic_launcher);
        d.setBounds(0, 0, c.getWidth(), c.getHeight());
        Log.v("icon", ""+d);
        tv.setCompoundDrawables(null, d, null, null);
        ((ViewGroup) findViewById(R.id.container)).addView(tv);

        tv = new TextView(this);
        tv.setText("bla");
        tv.setCompoundDrawables(null, icon, null, null);
        Log.v("icon", ""+icon);
        ((ViewGroup) findViewById(R.id.container)).addView(tv);




        addColorShowIntView("#ff303030");
        addColorShowIntView("#ff212121");
        addColorShowIntView("#ff424242");
        addColorShowIntView("#ff616161");
        addColorShowIntView("#b3ffffff");
        addColorShowIntView("#80ffffff");
        addColorShowIntView("#ff202831");
        addColorShowIntView("#ff1e1e1e");
        addColorShowIntView("#ff191919");
        addColorShowIntView("#ff696969");
        addColorShowIntView("#ff636363");
        addColorShowIntView("#ff707070");
        addColorShowIntView("#fffffffa");


        findViewById(R.id.button_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Notification n = new NotificationCompat.Builder(MainActivity.this).setContentTitle(getString(R.string.app_name)).setSmallIcon(R.drawable.teeest).setPriority(5).setTicker(getString(R.string.app_name)).build();
                        //NotificationManager nMgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        //nMgr.notify(0, n);
                        NotificationManager nMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                        String id = "test";
                        CharSequence name = "Test";
                        String description = "test";
                        Spannable text = new SpannableString("tata test");
                        text.setSpan(new ForegroundColorSpan(Color.BLACK), 0, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        int importance = NotificationManager.IMPORTANCE_HIGH;
                        NotificationChannel mChannel = new NotificationChannel(id, name, importance);
                        mChannel.setDescription(description);
                        mChannel.enableLights(true);
                        mChannel.setLightColor(Color.GREEN);
                        mChannel.enableVibration(true);
                        nMgr.createNotificationChannel(mChannel);
                        NotificationCompat.Builder mBuilder =
                                new NotificationCompat.Builder(MainActivity.this, id)
                                        //.setColor(0xff1B5E20)
                                        .setSmallIcon(R.drawable.testest)
                                        .setContentTitle("My notification")
                                        .setContentText(text)
                                        //.setPriority(NotificationManager.IMPORTANCE_MIN)
                                        .setTicker("AAAAAAAA");
                        mBuilder.addAction(R.drawable.testest, "Nothing", null);
                        nMgr.notify(1, mBuilder.build());

                    }
                }, 3000);
            }
        });

        findViewById(R.id.button_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                final float before = lp.screenBrightness;
                lp.screenBrightness = 0;
                getWindow().setAttributes(lp);
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            /*
                            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
                            PowerManager.WakeLock wakeLock = pm.newWakeLock(PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK, "tag");
                            wakeLock.acquire();

                            Thread.sleep(5000);
                            PowerManager.WakeLock screenLock = ((PowerManager)getSystemService(POWER_SERVICE)).newWakeLock(
                                    PowerManager.SCREEN_DIM_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG");
                            screenLock.acquire();
                            Thread.sleep(5000);

                            screenLock.release();
                            */

                            Thread.sleep(5000);
                            Log.i("Test", "over");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    WindowManager.LayoutParams lp = getWindow().getAttributes();
                                    lp.screenBrightness = before;
                                    getWindow().setAttributes(lp);
                                }
                            });
                            /*
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
                                }
                                */

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });

        findViewById(R.id.button_4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TestActivity.class));
            }
        });


        //startActivity(new Intent(this, TestActivity.class));
        tv.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);

        ((TextView)findViewById(R.id.config)).setText("Config: " + getString(R.string.folder));

        WallpaperManager wm = (WallpaperManager) getSystemService(WALLPAPER_SERVICE);
        try {
            ((TextView) findViewById(R.id.config)).setText("Wallpaper: " + wm.getWallpaperInfo().getComponent());
            Log.d("WALLDEBUG", "Current " + wm.getWallpaperInfo().getComponent());
            Log.d("WALLDEBUG", "Default " + getString(R.string.default_wall));
            Log.d("WALLDEBUG", "Equals " + getString(R.string.default_wall).equals(wm.getWallpaperInfo().getComponent().flattenToShortString()));
        } catch (Exception e) {}
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "BLABLABLA", Toast.LENGTH_LONG).show();
    }

    private String colorToString(int color) {
        return Color.alpha(color) + ":" + Color.red(color) + ":" + Color.green(color) + ":" + Color.blue(color);
    }

    /**
     * Finds a text color with sufficient contrast over bg that has the same hue as the original
     * color, assuming it is for large text.
     */
    public static int ensureLargeTextContrast(int color, int bg, boolean dark) {
        return dark ? findDarkContrastColor(color, bg, true, 3) : findContrastColor(color, bg, true, 3);
    }

    /**
     * Finds a text color with sufficient contrast over bg that has the same hue as the original
     * color.
     */
    private static int ensureTextContrast(int color, int bg, boolean dark) {
        return dark ? findDarkContrastColor(color, bg, true, 4.5) : findContrastColor(color, bg, true, 4.5);
    }

    private static int findContrastColor(int color, int other, boolean findFg, double minRatio) {
        int fg = findFg ? color : other;
        int bg = findFg ? other : color;
        if (ColorUtilsFromCompat.calculateContrast(fg, bg) >= minRatio) {
            return color;
        }

        double[] lab = new double[3];
        ColorUtilsFromCompat.colorToLAB(findFg ? fg : bg, lab);

        double low = 0, high = lab[0];
        final double a = lab[1], b = lab[2];
        for (int i = 0; i < 15 && high - low > 0.00001; i++) {
            final double l = (low + high) / 2;
            Log.w("findContrast: ", "low " + low + " high " + high + " l " + l);
            if (findFg) {
                fg = ColorUtilsFromCompat.LABToColor(l, a, b);
            } else {
                bg = ColorUtilsFromCompat.LABToColor(l, a, b);
            }
            if (ColorUtilsFromCompat.calculateContrast(fg, bg) > minRatio) {
                low = l;
            } else {
                high = l;
            }
        }
        return ColorUtilsFromCompat.LABToColor(low, a, b);
    }

    private static int findDarkContrastColor(int color, int other, boolean findFg, double minRatio) {
        int fg = findFg ? color : other;
        int bg = findFg ? other : color;
        if (ColorUtilsFromCompat.calculateContrast(fg, bg) >= minRatio) {
            return color;
        }

        double[] lab = new double[3];
        ColorUtilsFromCompat.colorToLAB(findFg ? fg : bg, lab);

        double low = lab[0], high = 100d;
        final double a = lab[1], b = lab[2];
        for (int i = 0; i < 15 && high - low > 0.00001; i++) {
            final double l = (low + high) / 2;
            Log.e("findDarkContrast: ", "low " + low + " high " + high + " l " + l);
            if (findFg) {
                fg = ColorUtilsFromCompat.LABToColor(l, a, b);
            } else {
                bg = ColorUtilsFromCompat.LABToColor(l, a, b);
            }
            if (ColorUtilsFromCompat.calculateContrast(fg, bg) > minRatio) {
                high = l;
            } else {
                low = l;
            }
        }
        return ColorUtilsFromCompat.LABToColor(high, a, b);
    }

    private static class ColorUtilsFromCompat {
        private static final double XYZ_WHITE_REFERENCE_X = 95.047;
        private static final double XYZ_WHITE_REFERENCE_Y = 100;
        private static final double XYZ_WHITE_REFERENCE_Z = 108.883;
        private static final double XYZ_EPSILON = 0.008856;
        private static final double XYZ_KAPPA = 903.3;

        private static final int MIN_ALPHA_SEARCH_MAX_ITERATIONS = 10;
        private static final int MIN_ALPHA_SEARCH_PRECISION = 1;

        private static final ThreadLocal<double[]> TEMP_ARRAY = new ThreadLocal<>();

        private ColorUtilsFromCompat() {}

        /**
         * Composite two potentially translucent colors over each other and returns the result.
         */
        public static int compositeColors(@ColorInt int foreground, @ColorInt int background) {
            int bgAlpha = Color.alpha(background);
            int fgAlpha = Color.alpha(foreground);
            int a = compositeAlpha(fgAlpha, bgAlpha);

            int r = compositeComponent(Color.red(foreground), fgAlpha,
                    Color.red(background), bgAlpha, a);
            int g = compositeComponent(Color.green(foreground), fgAlpha,
                    Color.green(background), bgAlpha, a);
            int b = compositeComponent(Color.blue(foreground), fgAlpha,
                    Color.blue(background), bgAlpha, a);

            return Color.argb(a, r, g, b);
        }

        private static int compositeAlpha(int foregroundAlpha, int backgroundAlpha) {
            return 0xFF - (((0xFF - backgroundAlpha) * (0xFF - foregroundAlpha)) / 0xFF);
        }

        private static int compositeComponent(int fgC, int fgA, int bgC, int bgA, int a) {
            if (a == 0) return 0;
            return ((0xFF * fgC * fgA) + (bgC * bgA * (0xFF - fgA))) / (a * 0xFF);
        }

        /**
         * Returns the luminance of a color as a float between {@code 0.0} and {@code 1.0}.
         * <p>Defined as the Y component in the XYZ representation of {@code color}.</p>
         */
        @FloatRange(from = 0.0, to = 1.0)
        public static double calculateLuminance(@ColorInt int color) {
            final double[] result = getTempDouble3Array();
            colorToXYZ(color, result);
            // Luminance is the Y component
            return result[1] / 100;
        }

        /**
         * Returns the contrast ratio between {@code foreground} and {@code background}.
         * {@code background} must be opaque.
         * <p>
         * Formula defined
         * <a href="http://www.w3.org/TR/2008/REC-WCAG20-20081211/#contrast-ratiodef">here</a>.
         */
        public static double calculateContrast(@ColorInt int foreground, @ColorInt int background) {
            if (Color.alpha(background) != 255) {
                throw new IllegalArgumentException("background can not be translucent: #"
                        + Integer.toHexString(background));
            }
            if (Color.alpha(foreground) < 255) {
                // If the foreground is translucent, composite the foreground over the background
                foreground = compositeColors(foreground, background);
            }

            final double luminance1 = calculateLuminance(foreground) + 0.05;
            final double luminance2 = calculateLuminance(background) + 0.05;

            // Now return the lighter luminance divided by the darker luminance
            return Math.max(luminance1, luminance2) / Math.min(luminance1, luminance2);
        }

        /**
         * Convert the ARGB color to its CIE Lab representative components.
         *
         * @param color  the ARGB color to convert. The alpha component is ignored
         * @param outLab 3-element array which holds the resulting LAB components
         */
        public static void colorToLAB(@ColorInt int color, @NonNull double[] outLab) {
            RGBToLAB(Color.red(color), Color.green(color), Color.blue(color), outLab);
        }

        /**
         * Convert RGB components to its CIE Lab representative components.
         *
         * <ul>
         * <li>outLab[0] is L [0 ...100)</li>
         * <li>outLab[1] is a [-128...127)</li>
         * <li>outLab[2] is b [-128...127)</li>
         * </ul>
         *
         * @param r      red component value [0..255]
         * @param g      green component value [0..255]
         * @param b      blue component value [0..255]
         * @param outLab 3-element array which holds the resulting LAB components
         */
        public static void RGBToLAB(@IntRange(from = 0x0, to = 0xFF) int r,
                                    @IntRange(from = 0x0, to = 0xFF) int g, @IntRange(from = 0x0, to = 0xFF) int b,
                                    @NonNull double[] outLab) {
            // First we convert RGB to XYZ
            RGBToXYZ(r, g, b, outLab);
            // outLab now contains XYZ
            XYZToLAB(outLab[0], outLab[1], outLab[2], outLab);
            // outLab now contains LAB representation
        }

        /**
         * Convert the ARGB color to it's CIE XYZ representative components.
         *
         * <p>The resulting XYZ representation will use the D65 illuminant and the CIE
         * 2° Standard Observer (1931).</p>
         *
         * <ul>
         * <li>outXyz[0] is X [0 ...95.047)</li>
         * <li>outXyz[1] is Y [0...100)</li>
         * <li>outXyz[2] is Z [0...108.883)</li>
         * </ul>
         *
         * @param color  the ARGB color to convert. The alpha component is ignored
         * @param outXyz 3-element array which holds the resulting LAB components
         */
        public static void colorToXYZ(@ColorInt int color, @NonNull double[] outXyz) {
            RGBToXYZ(Color.red(color), Color.green(color), Color.blue(color), outXyz);
        }

        /**
         * Convert RGB components to it's CIE XYZ representative components.
         *
         * <p>The resulting XYZ representation will use the D65 illuminant and the CIE
         * 2° Standard Observer (1931).</p>
         *
         * <ul>
         * <li>outXyz[0] is X [0 ...95.047)</li>
         * <li>outXyz[1] is Y [0...100)</li>
         * <li>outXyz[2] is Z [0...108.883)</li>
         * </ul>
         *
         * @param r      red component value [0..255]
         * @param g      green component value [0..255]
         * @param b      blue component value [0..255]
         * @param outXyz 3-element array which holds the resulting XYZ components
         */
        public static void RGBToXYZ(@IntRange(from = 0x0, to = 0xFF) int r,
                                    @IntRange(from = 0x0, to = 0xFF) int g, @IntRange(from = 0x0, to = 0xFF) int b,
                                    @NonNull double[] outXyz) {
            if (outXyz.length != 3) {
                throw new IllegalArgumentException("outXyz must have a length of 3.");
            }

            double sr = r / 255.0;
            sr = sr < 0.04045 ? sr / 12.92 : Math.pow((sr + 0.055) / 1.055, 2.4);
            double sg = g / 255.0;
            sg = sg < 0.04045 ? sg / 12.92 : Math.pow((sg + 0.055) / 1.055, 2.4);
            double sb = b / 255.0;
            sb = sb < 0.04045 ? sb / 12.92 : Math.pow((sb + 0.055) / 1.055, 2.4);

            outXyz[0] = 100 * (sr * 0.4124 + sg * 0.3576 + sb * 0.1805);
            outXyz[1] = 100 * (sr * 0.2126 + sg * 0.7152 + sb * 0.0722);
            outXyz[2] = 100 * (sr * 0.0193 + sg * 0.1192 + sb * 0.9505);
        }

        /**
         * Converts a color from CIE XYZ to CIE Lab representation.
         *
         * <p>This method expects the XYZ representation to use the D65 illuminant and the CIE
         * 2° Standard Observer (1931).</p>
         *
         * <ul>
         * <li>outLab[0] is L [0 ...100)</li>
         * <li>outLab[1] is a [-128...127)</li>
         * <li>outLab[2] is b [-128...127)</li>
         * </ul>
         *
         * @param x      X component value [0...95.047)
         * @param y      Y component value [0...100)
         * @param z      Z component value [0...108.883)
         * @param outLab 3-element array which holds the resulting Lab components
         */
        public static void XYZToLAB(@FloatRange(from = 0f, to = XYZ_WHITE_REFERENCE_X) double x,
                                    @FloatRange(from = 0f, to = XYZ_WHITE_REFERENCE_Y) double y,
                                    @FloatRange(from = 0f, to = XYZ_WHITE_REFERENCE_Z) double z,
                                    @NonNull double[] outLab) {
            if (outLab.length != 3) {
                throw new IllegalArgumentException("outLab must have a length of 3.");
            }
            x = pivotXyzComponent(x / XYZ_WHITE_REFERENCE_X);
            y = pivotXyzComponent(y / XYZ_WHITE_REFERENCE_Y);
            z = pivotXyzComponent(z / XYZ_WHITE_REFERENCE_Z);
            outLab[0] = Math.max(0, 116 * y - 16);
            outLab[1] = 500 * (x - y);
            outLab[2] = 200 * (y - z);
        }

        /**
         * Converts a color from CIE Lab to CIE XYZ representation.
         *
         * <p>The resulting XYZ representation will use the D65 illuminant and the CIE
         * 2° Standard Observer (1931).</p>
         *
         * <ul>
         * <li>outXyz[0] is X [0 ...95.047)</li>
         * <li>outXyz[1] is Y [0...100)</li>
         * <li>outXyz[2] is Z [0...108.883)</li>
         * </ul>
         *
         * @param l      L component value [0...100)
         * @param a      A component value [-128...127)
         * @param b      B component value [-128...127)
         * @param outXyz 3-element array which holds the resulting XYZ components
         */
        public static void LABToXYZ(@FloatRange(from = 0f, to = 100) final double l,
                                    @FloatRange(from = -128, to = 127) final double a,
                                    @FloatRange(from = -128, to = 127) final double b,
                                    @NonNull double[] outXyz) {
            final double fy = (l + 16) / 116;
            final double fx = a / 500 + fy;
            final double fz = fy - b / 200;

            double tmp = Math.pow(fx, 3);
            final double xr = tmp > XYZ_EPSILON ? tmp : (116 * fx - 16) / XYZ_KAPPA;
            final double yr = l > XYZ_KAPPA * XYZ_EPSILON ? Math.pow(fy, 3) : l / XYZ_KAPPA;

            tmp = Math.pow(fz, 3);
            final double zr = tmp > XYZ_EPSILON ? tmp : (116 * fz - 16) / XYZ_KAPPA;

            outXyz[0] = xr * XYZ_WHITE_REFERENCE_X;
            outXyz[1] = yr * XYZ_WHITE_REFERENCE_Y;
            outXyz[2] = zr * XYZ_WHITE_REFERENCE_Z;
        }

        /**
         * Converts a color from CIE XYZ to its RGB representation.
         *
         * <p>This method expects the XYZ representation to use the D65 illuminant and the CIE
         * 2° Standard Observer (1931).</p>
         *
         * @param x X component value [0...95.047)
         * @param y Y component value [0...100)
         * @param z Z component value [0...108.883)
         * @return int containing the RGB representation
         */
        @ColorInt
        public static int XYZToColor(@FloatRange(from = 0f, to = XYZ_WHITE_REFERENCE_X) double x,
                                     @FloatRange(from = 0f, to = XYZ_WHITE_REFERENCE_Y) double y,
                                     @FloatRange(from = 0f, to = XYZ_WHITE_REFERENCE_Z) double z) {
            double r = (x * 3.2406 + y * -1.5372 + z * -0.4986) / 100;
            double g = (x * -0.9689 + y * 1.8758 + z * 0.0415) / 100;
            double b = (x * 0.0557 + y * -0.2040 + z * 1.0570) / 100;

            r = r > 0.0031308 ? 1.055 * Math.pow(r, 1 / 2.4) - 0.055 : 12.92 * r;
            g = g > 0.0031308 ? 1.055 * Math.pow(g, 1 / 2.4) - 0.055 : 12.92 * g;
            b = b > 0.0031308 ? 1.055 * Math.pow(b, 1 / 2.4) - 0.055 : 12.92 * b;

            return Color.rgb(
                    constrain((int) Math.round(r * 255), 0, 255),
                    constrain((int) Math.round(g * 255), 0, 255),
                    constrain((int) Math.round(b * 255), 0, 255));
        }

        /**
         * Converts a color from CIE Lab to its RGB representation.
         *
         * @param l L component value [0...100]
         * @param a A component value [-128...127]
         * @param b B component value [-128...127]
         * @return int containing the RGB representation
         */
        @ColorInt
        public static int LABToColor(@FloatRange(from = 0f, to = 100) final double l,
                                     @FloatRange(from = -128, to = 127) final double a,
                                     @FloatRange(from = -128, to = 127) final double b) {
            final double[] result = getTempDouble3Array();
            LABToXYZ(l, a, b, result);
            return XYZToColor(result[0], result[1], result[2]);
        }

        private static int constrain(int amount, int low, int high) {
            return amount < low ? low : (amount > high ? high : amount);
        }

        private static double pivotXyzComponent(double component) {
            return component > XYZ_EPSILON
                    ? Math.pow(component, 1 / 3.0)
                    : (XYZ_KAPPA * component + 16) / 116;
        }

        public static double[] getTempDouble3Array() {
            double[] result = TEMP_ARRAY.get();
            if (result == null) {
                result = new double[3];
                TEMP_ARRAY.set(result);
            }
            return result;
        }

    }

    private void addColorShowIntView(String color) {
        int c = Color.parseColor(color);
        addColorShowIntView(Color.alpha(c), Color.red(c), Color.green(c), Color.blue(c));
    }

    private void addColorShowIntView(int a, int r, int g, int b) {
        TextView add = new TextView(this);
        String text = Integer.toHexString(a) + Integer.toHexString(r) + Integer.toHexString(g) + Integer.toHexString(b) + " " + Color.argb(a, r, g, b);
        add.setText(text);
        add.setTextColor(Color.argb(a, r, g, b));
        add.setBackgroundColor(Color.GRAY);
        ((ViewGroup) findViewById(R.id.container)).addView(add);
        Log.v("COLOR", text);
    }
}
