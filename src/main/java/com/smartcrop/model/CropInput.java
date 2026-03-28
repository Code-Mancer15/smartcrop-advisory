package com.smartcrop.model;


public class CropInput {

    private double nitrogen;
    private double phosphorus;
    private double potassium;
    private double temperature;
    private double humidity;
    private double ph;
    private double rainfall;

    public CropInput() {}

    public CropInput(double nitrogen, double phosphorus, double potassium,
                     double temperature, double humidity, double ph, double rainfall) {
        this.nitrogen    = nitrogen;
        this.phosphorus  = phosphorus;
        this.potassium   = potassium;
        this.temperature = temperature;
        this.humidity    = humidity;
        this.ph          = ph;
        this.rainfall    = rainfall;
    }

    public double getNitrogen()              { return nitrogen; }
    public void setNitrogen(double v)        { this.nitrogen = v; }

    public double getPhosphorus()            { return phosphorus; }
    public void setPhosphorus(double v)      { this.phosphorus = v; }

    public double getPotassium()             { return potassium; }
    public void setPotassium(double v)       { this.potassium = v; }

    public double getTemperature()           { return temperature; }
    public void setTemperature(double v)     { this.temperature = v; }

    public double getHumidity()              { return humidity; }
    public void setHumidity(double v)        { this.humidity = v; }

    public double getPh()                    { return ph; }
    public void setPh(double v)              { this.ph = v; }

    public double getRainfall()              { return rainfall; }
    public void setRainfall(double v)        { this.rainfall = v; }
}
