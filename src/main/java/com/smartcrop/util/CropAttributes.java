package com.smartcrop.util;

import weka.core.Attribute;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Defines the Weka dataset structure shared between training and prediction
public class CropAttributes {

    // Must match the 'label' values in the CSV exactly
    public static final List<String> CROP_NAMES = Arrays.asList(
            "apple", "banana", "blackgram", "chickpea", "coconut", "coffee",
            "cotton", "grapes", "jute", "kidneybeans", "lentil", "maize",
            "mango", "mothbeans", "mungbean", "muskmelon", "orange", "papaya",
            "pigeonpeas", "pomegranate", "rice", "watermelon"
    );

    public static ArrayList<Attribute> getAttributes() {
        ArrayList<Attribute> attrs = new ArrayList<>();
        attrs.add(new Attribute("N"));
        attrs.add(new Attribute("P"));
        attrs.add(new Attribute("K"));
        attrs.add(new Attribute("temperature"));
        attrs.add(new Attribute("humidity"));
        attrs.add(new Attribute("ph"));
        attrs.add(new Attribute("rainfall"));
        attrs.add(new Attribute("label", CROP_NAMES)); // class attribute
        return attrs;
    }

    // Returns an empty dataset with the correct structure for building prediction instances
    public static Instances createEmptyDataset() {
        Instances dataset = new Instances("CropData", getAttributes(), 0);
        dataset.setClassIndex(dataset.numAttributes() - 1);
        return dataset;
    }
}
