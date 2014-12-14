package com.duckwriter.graphics.shapes2d;

import java.awt.geom.Path2D;

public final class XGlyph extends Path2D.Float {

    public XGlyph() {
        super(Path2D.WIND_EVEN_ODD, 12);
        this.init();
    }

    private void init() {

        this.moveTo(0.0f, 1.0f);
        this.lineTo(1.0f, 0.0f);
        this.lineTo(2.0f, 1.0f);
        this.lineTo(3.0f, 0.0f);
        this.lineTo(4.0f, 1.0f);
        this.lineTo(3.0f, 2.0f);
        this.lineTo(4.0f, 3.0f);
        this.lineTo(3.0f, 4.0f);
        this.lineTo(2.0f, 3.0f);
        this.lineTo(1.0f, 4.0f);
        this.lineTo(0.0f, 3.0f);
        this.lineTo(1.0f, 2.0f);
        this.closePath();

    }

}
