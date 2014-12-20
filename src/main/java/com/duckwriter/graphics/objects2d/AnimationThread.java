package com.duckwriter.graphics.objects2d;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

final class AnimationThread extends Thread {

    /*
     * Instance Variables
     */

    private final Reference<AnimatedImageSource> imageSourceRef;
    private final Object animationMonitor;

    /*
     * Constructors
     */

    AnimationThread(AnimatedImageSource imageSource) {

        // initialize thread

        super();
        this.setPriority(Thread.MIN_PRIORITY);

        // initialize local variables

        this.imageSourceRef = new WeakReference<AnimatedImageSource>(imageSource);
        this.animationMonitor = new Object();

    }

    @Override
    public void run() {

        final Object monitor = this.animationMonitor;
        AnimatedImageSource imageSource;

        while ( (imageSource = this.imageSourceRef.get()) != null
                && !imageSource.animationFinished() ) {

            imageSource.render();
            imageSource = null;

            synchronized ( monitor ) {
                try {
                    monitor.wait(10L);
                } catch (InterruptedException e) {
                    break;
                }
            }

        }

    }

}

