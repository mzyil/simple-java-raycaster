package com.melihyildiz.ceng415hw1;

import java.awt.*;

/**
 * Created by Melih on 28.03.2018.
 * Every programmer is going to taste php one day.
 */
class Hit {
    double t; // distance of the closest intersection
    Color color; // color of the object with the closest object

    public Hit(double t, Color color) {
        this.t = t;
        this.color = color;
    }
}
