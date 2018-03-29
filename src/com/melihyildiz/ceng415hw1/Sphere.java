package com.melihyildiz.ceng415hw1;

import javafx.geometry.Point3D;

/**
 * Created by Melih on 28.03.2018.
 * Every programmer is going to taste php one day.
 */
public class Sphere extends Object3D {

    public double radius;
    public Point3D center;

    public Sphere(Point3D center, double radius, RGB color) {
        this.center = center;
        this.radius = radius;
        this.color = color;
    }

    @Override
    public boolean intersect(Ray ray, Hit hit, double tMin) {
        double t = 0; //TODO: detect intersect
        if (t > tMin) {
            return false;
        }
        double distance2ray = 0;
        // Projection Test
        Point3D testU = ray.origin.subtract(center);
        double cosOfAngle = Math.cos(testU.angle(ray.direction));
        if (cosOfAngle > 0) {
            Point3D projectionOfCenter = ray.direction.multiply(ray.direction.dotProduct(center.subtract(ray.origin)) / ray.direction.magnitude())
                    .add(ray.origin);
            distance2ray = projectionOfCenter.distance(center);
            if (distance2ray > radius) {
                return false;
            } else if (distance2ray == radius) {
                t = projectionOfCenter.distance(ray.origin);
            } else {
                t = 0;
            }
        } else {
            distance2ray = ray.origin.distance(center);
            if (distance2ray > radius) {
                return false;
            } else if (distance2ray == radius) {
                t = 0;
            }
        }
        if (t < hit.t) {
            hit.t = t;
            hit.color = this.color;
            return true;
        }
        return false;
    }
}
