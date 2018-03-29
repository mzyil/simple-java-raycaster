package com.melihyildiz.ceng415hw1;

import java.awt.*;

/**
 * Created by Melih on 28.03.2018.
 * Every programmer is going to taste php one day.
 */
abstract class Object3D {
    Color color;
    protected abstract boolean intersect(Ray ray, Hit hit, double tMin);
}
