package com.smartcrop.model;

import java.util.List;


public class AdvisoryResult {

    private String cropName;
    private List<FertilizerAdvice> fertilizerList;
    private List<WeatherTip> weatherTips;
    private CropInput input;

    public AdvisoryResult() {}

    public AdvisoryResult(String cropName, List<FertilizerAdvice> fertilizerList,
                          List<WeatherTip> weatherTips, CropInput input) {
        this.cropName       = cropName;
        this.fertilizerList = fertilizerList;
        this.weatherTips    = weatherTips;
        this.input          = input;
    }

    public String getCropName()                             { return cropName; }
    public void setCropName(String v)                       { this.cropName = v; }

    public List<FertilizerAdvice> getFertilizerList()       { return fertilizerList; }
    public void setFertilizerList(List<FertilizerAdvice> v) { this.fertilizerList = v; }

    public List<WeatherTip> getWeatherTips()                { return weatherTips; }
    public void setWeatherTips(List<WeatherTip> v)          { this.weatherTips = v; }

    public CropInput getInput()                             { return input; }
    public void setInput(CropInput v)                       { this.input = v; }
}
