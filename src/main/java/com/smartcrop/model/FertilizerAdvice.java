package com.smartcrop.model;

// Holds fertilizer recommendation for one nutrient (N, P, or K)
public class FertilizerAdvice {

    private String nutrientName;
    private double userValue;
    private double requiredValue;
    private String status;   // "ok" | "low" | "high"
    private String label;    // "Adequate" | "Deficient" | "Excess"
    private String emoji;
    private String message;

    public FertilizerAdvice() {}

    public FertilizerAdvice(String nutrientName, double userValue, double requiredValue,
                             String status, String label, String emoji, String message) {
        this.nutrientName  = nutrientName;
        this.userValue     = userValue;
        this.requiredValue = requiredValue;
        this.status        = status;
        this.label         = label;
        this.emoji         = emoji;
        this.message       = message;
    }

    public String getNutrientName()               { return nutrientName; }
    public void setNutrientName(String v)         { this.nutrientName = v; }

    public double getUserValue()                  { return userValue; }
    public void setUserValue(double v)            { this.userValue = v; }

    public double getRequiredValue()              { return requiredValue; }
    public void setRequiredValue(double v)        { this.requiredValue = v; }

    public String getStatus()                     { return status; }
    public void setStatus(String v)               { this.status = v; }

    public String getLabel()                      { return label; }
    public void setLabel(String v)                { this.label = v; }

    public String getEmoji()                      { return emoji; }
    public void setEmoji(String v)                { this.emoji = v; }

    public String getMessage()                    { return message; }
    public void setMessage(String v)              { this.message = v; }
}
