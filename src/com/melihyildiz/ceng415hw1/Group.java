package com.melihyildiz.ceng415hw1;

import java.util.LinkedList;

/**
 * Created by Melih on 28.03.2018.
 * Every programmer is going to taste php one day.
 */
public class Group extends Object3D {
    public LinkedList<Object3D> objects = new LinkedList<>();

    @Override
    public boolean intersect(Ray ray, Hit hit, double tMin) {
        for(Object3D object3D : objects){
            object3D.intersect(ray, hit, tMin);
        }
        return true;
    }
}
