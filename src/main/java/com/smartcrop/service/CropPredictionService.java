package com.smartcrop.service;

import com.smartcrop.model.CropInput;
import com.smartcrop.util.CropAttributes;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;

import java.io.File;

@Service
public class CropPredictionService {

    private FilteredClassifier model;
    private Instances datasetStructure;

    // Loads model.dat once when the app starts
    @PostConstruct
    public void loadModel() {
        try {
            File modelFile = new File("model.dat");
            if (!modelFile.exists())
                throw new RuntimeException("model.dat not found. Run 'mvn exec:java' first.");

            model = (FilteredClassifier) SerializationHelper.read(modelFile.getAbsolutePath());
            datasetStructure = CropAttributes.createEmptyDataset();
            System.out.println("Model loaded from: " + modelFile.getAbsolutePath());
        } catch (Exception e) {
            throw new RuntimeException("Failed to load model: " + e.getMessage(), e);
        }
    }

    public String predict(CropInput input) {
        try {
            // Build a single Weka instance from the user's values
            Instance instance = new DenseInstance(datasetStructure.numAttributes());
            instance.setDataset(datasetStructure);
            instance.setValue(datasetStructure.attribute("N"),           input.getNitrogen());
            instance.setValue(datasetStructure.attribute("P"),           input.getPhosphorus());
            instance.setValue(datasetStructure.attribute("K"),           input.getPotassium());
            instance.setValue(datasetStructure.attribute("temperature"), input.getTemperature());
            instance.setValue(datasetStructure.attribute("humidity"),    input.getHumidity());
            instance.setValue(datasetStructure.attribute("ph"),          input.getPh());
            instance.setValue(datasetStructure.attribute("rainfall"),    input.getRainfall());

            // classifyInstance returns a numeric index; convert it back to the crop name
            double classIndex = model.classifyInstance(instance);
            String crop = datasetStructure.classAttribute().value((int) classIndex);
            return crop.substring(0, 1).toUpperCase() + crop.substring(1);

        } catch (Exception e) {
            throw new RuntimeException("Prediction failed: " + e.getMessage(), e);
        }
    }
}
