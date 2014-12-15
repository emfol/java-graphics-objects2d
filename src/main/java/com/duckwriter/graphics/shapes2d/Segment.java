package com.duckwriter.graphics.shapes2d;

import java.awt.geom.Path2D;

public final class Segment extends Path2D.Float {

    public Segment() {
        super(Path2D.WIND_EVEN_ODD, 7);
        this.init();
    }

    private void init() {
        this.moveTo(0.0f, 0.5f);
        this.lineTo(0.5f, 0.0f);
        this.lineTo(2.5f, 0.0f);
        this.lineTo(3.0f, 0.5f);
        this.lineTo(2.5f, 1.0f);
        this.lineTo(0.5f, 1.0f);
        this.closePath();
    }

}

