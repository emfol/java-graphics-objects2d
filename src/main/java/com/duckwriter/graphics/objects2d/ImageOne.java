package com.duckwriter.graphics.objects2d;

import java.util.concurrent.atomic.AtomicBoolean;
import java.awt.image.ImageProducer;
import java.awt.image.ImageConsumer;
import java.awt.image.MemoryImageSource;

public final class ImageOne extends Object
    implements ImageProducer, Runnable {

    private static final int WIDTH = 256;
    private static final int HEIGHT = 256;
    private static final int COLOR_BACKGROUND = 0xFFFFFFFF;
    private static final int COLOR_FOREGROUND = 0xFF000000;

    private final AtomicBoolean isRunning;
    private final int[] pixels;
    private final MemoryImageSource source;

    private volatile Thread animationThread;

    /*
     * Constructors
     */

    public ImageOne() {

        super();

        // initialize instance variables
        this.isRunning = new AtomicBoolean(false);
        this.pixels = new int[WIDTH * HEIGHT];
        this.source = new MemoryImageSource(
            WIDTH, HEIGHT,
            this.pixels,
            0,
            WIDTH
        );
        this.source.setAnimated(true);
        this.animationThread = null;

        // initialize pixel values...
        this.init();

    }

    /*
     * Private Methods
     */

    private void init() {
        // fill pixels with background color...
        int i, length = this.pixels.length;
        for ( i = 0; i < length; ++i ) {
            this.pixels[i] = COLOR_BACKGROUND;
        }
    }

    /*
     * Runnable Implementation
     */

    @Override
    public void run() {

        int i = 0, j = 0, color = COLOR_FOREGROUND;
        final MemoryImageSource image = this.source;
        final int[] matrix = this.pixels, colors = new int[] {
            0xFFBADA55,
            0xFFC0FFEE,
            0xFFB000B5,
            0xFFFA113D
        };

        while (this.animationThread != null) {

            // change color when first pixel is reached...
            if (i == 0) {
                color = colors[j];
                j = (j + 1) % colors.length;
            }

            // paint...
            while ( i < matrix.length ) {
                matrix[i++] = color;
                if ( i % WIDTH == 0 ) {
                    break;
                }
            }

            // notify consumers...
            image.newPixels(
                0,
                i / WIDTH - 1,
                WIDTH,
                1,
                true
            );

            // reset i if bound has been reached...
            if ( i >= matrix.length ) {
                i = 0;
            }

            // lets give it a time...
            try {
                Thread.sleep(45);
            } catch (InterruptedException e) {
                // silence is golden...
            }

        }

    }

    /*
     * ImageProducer Implementation
     */

    @Override
    public void addConsumer(ImageConsumer consumer) {
        this.source.addConsumer(consumer);
    }

    @Override
    public boolean isConsumer(ImageConsumer consumer) {
        return this.source.isConsumer(consumer);
    }

    @Override
    public void removeConsumer(ImageConsumer consumer) {
        this.source.removeConsumer(consumer);
    }

    @Override
    public void requestTopDownLeftRightResend(ImageConsumer consumer) {
        this.source.requestTopDownLeftRightResend(consumer);
    }

    @Override
    public void startProduction(ImageConsumer consumer) {
        if (this.isRunning.compareAndSet(false, true)) {
            this.animationThread = new Thread(this);
            this.animationThread.start();
        }
        this.source.startProduction(consumer);
    }

}

