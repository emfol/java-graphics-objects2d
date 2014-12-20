package com.duckwriter.graphics.objects2d;

import java.util.concurrent.atomic.AtomicBoolean;
import java.awt.image.ColorModel;
import java.awt.image.ImageProducer;
import java.awt.image.ImageConsumer;
import java.awt.image.MemoryImageSource;

public abstract class AnimatedImageSource extends Object
    implements ImageProducer {

    /*
     * Instance Variables
     */

    private final int[] pixelArray;
    private final MemoryImageSource imageSource;
    private final AtomicBoolean initialized;
    private volatile boolean finished;

    /*
     * Constructors
     */

    public AnimatedImageSource(final int width, final int height) {

        // initialize parent class

        super();

        // check parameters

        if ( width < 1 || height < 1 ) {
            throw new IllegalArgumentException("Invalid Dimension for Image Source");
        }

        // initialize instance variables

        this.pixelArray = new int[width * height];
        this.imageSource = new MemoryImageSource(
            width, height,
            ColorModel.getRGBdefault(),
            this.pixelArray,
            0,
            width
        );
        this.initialized = new AtomicBoolean(false);
        this.finished = false;

        // set animation flag for image source

        this.imageSource.setAnimated(true);

    }

    /*
     * Private Methods
     */

    private void initCheck() {
        if ( this.initialized.compareAndSet(false, true) ) {
            // let instance initialize itself
            this.init();
            // start animation thread
            (new AnimationThread(this)).start();
        }
    }

    /*
     * Final Methods
     */

    public final int[] getPixelArray() {
        return this.pixelArray;
    }

    public final void newPixels(int x, int y, int w, int h) {
        this.imageSource.newPixels(x, y, w, h);
    }

    public final void newPixels() {
        this.imageSource.newPixels();
    }

    public final void stop() {
        this.finished = true;
    }

    public final boolean animationFinished() {
        return this.finished;
    }

    /*
     * Abstract Methods
     */

    public abstract void init();
    public abstract void render();

    /*
     * ImageProducer Implementation
     */

    @Override
    public final void addConsumer(ImageConsumer imageConsumer) {
        this.initCheck();
        this.imageSource.addConsumer(imageConsumer);
    }

    @Override
    public final boolean isConsumer(ImageConsumer imageConsumer) {
        return this.imageSource.isConsumer(imageConsumer);
    }

    @Override
    public final void removeConsumer(ImageConsumer imageConsumer) {
        this.imageSource.removeConsumer(imageConsumer);
    }

    @Override
    public final void requestTopDownLeftRightResend(ImageConsumer imageConsumer) {
        this.imageSource.requestTopDownLeftRightResend(imageConsumer);
    }

    @Override
    public final void startProduction(ImageConsumer imageConsumer) {
        this.initCheck();
        this.imageSource.startProduction(imageConsumer);
    }

}

