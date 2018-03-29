package com.melihyildiz.ceng415hw1;

import javafx.geometry.Point3D;

import java.awt.*;

/**
 * Created by Melih on 28.03.2018.
 * Every programmer is going to taste php one day.
 */
public class OrthographicCamera extends Camera {
    public Point3D center;
    public Point3D direction;
    public Point3D up;
    public double size;
    public Color backgroundColor;

    public static double scaleInterval(
            double value,
            double minOrig, double maxOrig,
            double minTarget, double maxTarget) {

        return ((value - minOrig) / (maxOrig - minOrig))
                * (maxTarget - minTarget) + minTarget;

    }

    @Override
    public Ray generateRay(double x, double y) {
        if (x > 1 || y > 1) {
            return null;
        }
        Point3D horizontal = up.crossProduct(direction);
        Ray ray2return = new Ray();
        ray2return.direction = direction;
        double originX = center.getX() + ((x - 0.5) * size * horizontal.getX()) + ((y - 0.5) * size * up.getX());
        double originY = center.getY() + ((x - 0.5) * size * horizontal.getY()) + ((y - 0.5) * size * up.getY());
        double originZ = center.getZ() + ((x - 0.5) * size * horizontal.getZ()) + ((y - 0.5) * size * up.getZ());
        ray2return.origin = new Point3D(originX, originY, originZ);
        return ray2return;
    }

    public Ray generateRayFromPixel(int imageX, int imageY, int imageWidth, int imageHeight) {
        double rayX = scaleInterval(imageX, 0, imageWidth, 0, 1);
        double rayY = scaleInterval(imageY, 0, imageHeight, 0, 1);
        return generateRay(rayX, rayY);
    }
}
