package com.melihyildiz.ceng415hw1;

/**
 * Created by Melih on 28.03.2018.
 * Every programmer is going to taste php one day.
 */
public abstract class Object3D {
    public RGB color;
    public abstract boolean intersect(Ray ray, Hit hit, double tMin);
}
