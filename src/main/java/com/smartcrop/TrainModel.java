package com.smartcrop;

import com.smartcrop.util.CropAttributes;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.CSVLoader;
import weka.filters.unsupervised.attribute.Normalize;

import java.io.File;
import java.util.Random;

// Run once before starting the app: mvn exec:java
// Trains the model on SmartCrop-Dataset.csv and saves it as model.dat
public class TrainModel {

    public static void main(String[] args) throws Exception {

        // Load CSV
        File csvFile = new File("SmartCrop-Dataset.csv");
        if (!csvFile.exists()) {
            System.err.println("SmartCrop-Dataset.csv not found. Download it from the original repo.");
            System.exit(1);
        }

        CSVLoader loader = new CSVLoader();
        loader.setSource(csvFile);
        Instances data = loader.getDataSet();
        data.setClassIndex(data.numAttributes() - 1); // last column is the crop label

        System.out.println("Loaded: " + data.numInstances() + " rows, " + data.numClasses() + " crops");

        // Build pipeline: Normalize → NaiveBayes
        // FilteredClassifier bundles the scaler with the model so both apply at prediction time
        NaiveBayes nb = new NaiveBayes();
        nb.setUseSupervisedDiscretization(false); // use Gaussian kernel

        FilteredClassifier pipeline = new FilteredClassifier();
        pipeline.setFilter(new Normalize());
        pipeline.setClassifier(nb);

        // Train
        pipeline.buildClassifier(data);

        // Evaluate with 10-fold cross-validation
        Evaluation eval = new Evaluation(data);
        eval.crossValidateModel(pipeline, data, 10, new Random(42));
        System.out.printf("Accuracy: %.2f%%\n", eval.pctCorrect());

        // Save
        SerializationHelper.write("model.dat", pipeline);
        System.out.println("Model saved as model.dat");
        System.out.println("Now run: mvn spring-boot:run");
    }
}
