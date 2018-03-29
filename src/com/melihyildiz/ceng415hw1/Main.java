package com.melihyildiz.ceng415hw1;

import javafx.geometry.Point3D;
import org.json.JSONArray;
import org.json.JSONObject;


import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by Melih on 28.03.2018.
 * Every programmer is going to taste php one day.
 */
class Main {
    public static void main(String[] args) throws IOException {
        int imageWidth = 1280;
        int imageHeight = 720;

        String jsonFilePath = "scene1.json";
        List<String> jsonFileLines = Files.readAllLines(Paths.get(jsonFilePath));
        StringBuilder jsonContents = new StringBuilder();
        for (String line : jsonFileLines) {
            jsonContents.append(line);
        }
        JSONObject jsonObject = new JSONObject(jsonContents.toString());

        OrthographicCamera orthographicCamera = new OrthographicCamera();
        if (jsonObject.has("orthocamera")) {
            JSONObject orthocamera = jsonObject.getJSONObject("orthocamera");
            JSONArray center = orthocamera.getJSONArray("center");
            JSONArray direction = orthocamera.getJSONArray("direction");
            JSONArray up = orthocamera.getJSONArray("up");
            int size = orthocamera.getInt("size");
            orthographicCamera.center = new Point3D(center.getDouble(0), center.getDouble(1), center.getDouble(2));
            orthographicCamera.direction = new Point3D(direction.getDouble(0), direction.getDouble(1), direction.getDouble(2));
            orthographicCamera.up = new Point3D(up.getDouble(0), up.getDouble(1), up.getDouble(2));
            orthographicCamera.size = size;
        }
        if (jsonObject.has("background")) {
            JSONArray backgroundColor = jsonObject.getJSONObject("background").getJSONArray("color");
            orthographicCamera.backgroundColor = new Color(backgroundColor.getInt(0), backgroundColor.getInt(1), backgroundColor.getInt(2));
        }
        Group objectGroup = new Group();
        if (jsonObject.has("group")) {
            JSONArray group = jsonObject.getJSONArray("group");
            for (int i = 0; i < group.length(); i++) {
                JSONObject jsonObjectElement = group.getJSONObject(i);
                if (jsonObjectElement.has("sphere")) {
                    JSONObject sphere = jsonObjectElement.getJSONObject("sphere");
                    JSONArray centerArray = sphere.getJSONArray("center");
                    double radius = sphere.getDouble("radius");
                    JSONArray color = sphere.getJSONArray("color");
                    Sphere sphere2add = new Sphere(
                            new Point3D(centerArray.getInt(0), centerArray.getInt(1), centerArray.getInt(2)),
                            radius,
                            new Color(color.getInt(0), color.getInt(1), color.getInt(2))
                    );
                    objectGroup.objects.add(sphere2add);
                }
            }
        }

        BufferedImage bufferedImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < imageWidth; i++) {
            for (int j = 0; j < imageHeight; j++) {
                Ray ray = orthographicCamera.generateRayFromPixel(i, j, imageWidth, imageHeight);
                Hit hit = new Hit(Double.MAX_VALUE, orthographicCamera.backgroundColor);
                objectGroup.intersect(ray, hit, 0);
                bufferedImage.setRGB(i, j, hit.color.getRGB());
            }
        }
        JFrame frame = new JFrame();
        frame.getContentPane().setLayout(new FlowLayout());
        frame.getContentPane().add(new JLabel(new ImageIcon(bufferedImage)));
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
