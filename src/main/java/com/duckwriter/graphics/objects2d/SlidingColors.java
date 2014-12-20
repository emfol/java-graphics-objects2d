package com.duckwriter.graphics.objects2d;

import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicBoolean;
import java.awt.image.ImageProducer;
import java.awt.image.ImageConsumer;
import java.awt.image.MemoryImageSource;

public final class SlidingColors extends AnimatedImageSource {

    private static final int WIDTH = 256;
    private static final int HEIGHT = 256;
    private static final int COLOR_BACKGROUND = 0xFFFFFFFF;
    private static final int[] COLORS;

    static {
        COLORS = new int[] {
            0xFFBADA55,
            0xFFC0FFEE,
            0xFFB000B5,
            0xFFFA113D
        };
    }

    /*
     * Instance Variables
     */

    private int colorIndex;
    private int lineIndex;

    /*
     * Constructors
     */

    public SlidingColors() {
        super(WIDTH, HEIGHT);
        this.colorIndex = 0;
        this.lineIndex = 0;
    }

    /*
     * AnimatedImageSource Implementation
     */

    @Override
    public void init() {
        // fill pixels with background color...
        final int[] pixels = this.getPixelArray();
        int i, length = pixels.length;
        for ( i = 0; i < length; ++i ) {
            pixels[i] = COLOR_BACKGROUND;
        }
    }

    @Override
    public void render() {

        final int[] colors = COLORS, pixels = this.getPixelArray();
        final int width = WIDTH, height = HEIGHT;
        int i, length, colorValue,
            line = this.lineIndex,
            color = this.colorIndex;

        // paint...
        colorValue = colors[color];
        length = pixels.length;
        i = line * width;
        while ( i < length ) {
            pixels[i++] = colorValue;
            if ( i % width == 0 ) {
                break;
            }
        }

        // notify consumers...
        this.newPixels(
            0,
            line,
            width,
            1
        );

        line = (line + 1) % height;

        // change color when last line is reached
        if ( line == 0 ) {
            this.colorIndex = (color + 1) % colors.length;
        }

        // update line index
        this.lineIndex = line;

    }

}

