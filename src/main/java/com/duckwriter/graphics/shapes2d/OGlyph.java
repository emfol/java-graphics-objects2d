package com.duckwriter.graphics.shapes2d;

import java.awt.geom.Path2D;
import java.awt.geom.Ellipse2D;

public final class OGlyph extends Path2D.Float {

    private static final float SQRT2 = (float)Math.sqrt(2.0);

    public OGlyph() {
        super(Path2D.WIND_EVEN_ODD);
        this.init();
    }

    private void init() {

        Ellipse2D.Float ellipse = new Ellipse2D.Float();
        float a, b;

        // outer circle
        a = 0.0f;
        b = 4.0f;
        ellipse.setFrame(a, a, b, b);
        this.append(ellipse, false);

        // inner circle
        a = SQRT2;
        b = 4.0f - 2.0f * SQRT2;
        ellipse.setFrame(a, a, b, b);
        this.append(ellipse, false);

    }

}
