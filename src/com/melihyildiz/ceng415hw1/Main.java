package com.melihyildiz.ceng415hw1;

import javafx.geometry.Point3D;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by Melih on 28.03.2018.
 * Every programmer is going to taste php one day.
 */
public class Main {
    public static void main(String[] args) throws IOException {
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
            orthographicCamera.backgroundColor = new RGB(backgroundColor.getInt(0), backgroundColor.getInt(1), backgroundColor.getInt(2));
        }
        Group objectGroup = new Group();
        if (jsonObject.has("group")) {
            JSONArray group = jsonObject.getJSONArray("group");
            List<Object> objects = group.toList();
            for (Object object : objects) { // GROUP OBJECTS
                JSONObject jsonObjectElement = (JSONObject) object;
                if (jsonObjectElement.has("sphere")) {
                    JSONObject sphere = jsonObjectElement.getJSONObject("sphere");
                    JSONArray centerArray = sphere.getJSONArray("center");
                    int radius = sphere.getInt("radius");
                    JSONArray color = sphere.getJSONArray("color");
                    Sphere sphere2add = new Sphere(
                            new Point3D(centerArray.getInt(0), centerArray.getInt(1), centerArray.getInt(2)),
                            radius,
                            new RGB(color.getInt(0), color.getInt(0), color.getInt(0))
                    );
                    objectGroup.objects.add(sphere2add);
                }
            }
        }
    }
}
