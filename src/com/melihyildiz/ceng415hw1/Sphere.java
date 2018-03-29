package com.melihyildiz.ceng415hw1;

import javafx.geometry.Point3D;

import java.awt.*;

/**
 * Created by Melih on 28.03.2018.
 * Every programmer is going to taste php one day.
 */
public class Sphere extends Object3D {

    private double radius;
    private Point3D center;

    public Sphere(Point3D center, double radius, Color color) {
        this.center = center;
        this.radius = radius;
        this.color = color;
    }

    @Override
    public boolean intersect(Ray ray, Hit hit, double tMin) {
        double t; // intersection distance

        Point3D vpc = center.subtract(ray.origin); // the vector from rayorigin to center
        if (vpc.dotProduct(ray.direction) < 0) { // when the sphere is behind the origin rayorigin
            double vpcMagnitude = vpc.magnitude();
            if (vpcMagnitude >= radius) {
                return false; // no intersection, even if the intersection point is the rayorigin
            } else { // occurs when rayorigin is inside the sphere
                Point3D projectionOfCenter = ray.direction.multiply(vpc.dotProduct(ray.direction)/Math.pow(ray.direction.magnitude(), 2))
                        .add(ray.origin);
                double dist = Math.sqrt(Math.pow(radius, 2) - Math.pow(projectionOfCenter.subtract(center).magnitude(), 2));
                t = dist - projectionOfCenter.subtract(ray.origin).magnitude();
            }
        } else { // center of sphere projects on the ray
            Point3D projectionOfCenter = ray.direction.multiply(vpc.dotProduct(ray.direction)/Math.pow(ray.direction.magnitude(), 2))
                    .add(ray.origin);
            if (center.subtract(projectionOfCenter).magnitude() > radius) {
                return false; // no intersection
            } else {
                double dist2intersection = Math.sqrt(Math.pow(radius, 2) - Math.pow(projectionOfCenter.subtract(center).magnitude(), 2));
                t = projectionOfCenter.subtract(ray.origin).magnitude();
                if (vpc.magnitude() > radius) { // origin is outside sphere
                    t -= dist2intersection;
                } else { // origin is inside sphere
                    t += dist2intersection;
                }
            }
        }

        if (t > tMin && t < hit.t) {
            hit.t = t;
            hit.color = this.color;
            return true;
        }

        return false; // t <= tMin
    }
}
