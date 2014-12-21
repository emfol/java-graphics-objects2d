package com.duckwriter.graphics.objects2d;

import java.lang.ref.WeakReference;

final class AnimationThread extends Thread {

    /*
     * Constants
     */

    private static final long INTERVAL = 10L;
    private static final String NAME = "Duckwriter Animation Thread";

    /*
     * Instance Variables
     */

    private final WeakReference<AnimatedImageSource> imageSourceRef;
    private final Object animationMonitor;

    /*
     * Constructors
     */

    AnimationThread(AnimatedImageSource imageSource) {

        // initialize thread

        super(NAME);
        this.setPriority(Thread.MIN_PRIORITY);

        // initialize local variables

        this.imageSourceRef = new WeakReference<AnimatedImageSource>(imageSource);
        this.animationMonitor = new Object();

    }

    /*
     * Private Methods
     */

    private boolean invokeRenderer() {

        boolean result = false;
        AnimatedImageSource imageSource = this.imageSourceRef.get();

        if ( imageSource != null
                && !imageSource.animationFinished() ) {
            imageSource.render();
            result = true;
        }

        return result;

    }

    /*
     * Runnable Implementation
     */

    @Override
    public void run() {

        final Object monitor = this.animationMonitor;

        while ( this.invokeRenderer() ) {
            synchronized (monitor) {
                try {
                    monitor.wait(INTERVAL);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }

    }

}

