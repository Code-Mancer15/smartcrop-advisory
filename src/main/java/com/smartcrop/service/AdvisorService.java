package com.smartcrop.service;

import com.smartcrop.model.FertilizerAdvice;
import com.smartcrop.model.WeatherTip;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AdvisorService {

    // Approximate N, P, K requirements per crop in kg/ha
    private static final Map<String, int[]> CROP_NUTRIENTS = new HashMap<>();
    static {
        CROP_NUTRIENTS.put("rice",        new int[]{80,  40,  40});
        CROP_NUTRIENTS.put("maize",       new int[]{120, 60,  60});
        CROP_NUTRIENTS.put("chickpea",    new int[]{20,  60,  40});
        CROP_NUTRIENTS.put("kidneybeans", new int[]{20,  60,  40});
        CROP_NUTRIENTS.put("pigeonpeas",  new int[]{20,  50,  20});
        CROP_NUTRIENTS.put("mothbeans",   new int[]{20,  40,  20});
        CROP_NUTRIENTS.put("mungbean",    new int[]{20,  40,  20});
        CROP_NUTRIENTS.put("blackgram",   new int[]{20,  40,  20});
        CROP_NUTRIENTS.put("lentil",      new int[]{20,  40,  20});
        CROP_NUTRIENTS.put("pomegranate", new int[]{50,  25,  50});
        CROP_NUTRIENTS.put("banana",      new int[]{200, 60,  300});
        CROP_NUTRIENTS.put("mango",       new int[]{100, 50,  100});
        CROP_NUTRIENTS.put("grapes",      new int[]{60,  30,  60});
        CROP_NUTRIENTS.put("watermelon",  new int[]{80,  40,  80});
        CROP_NUTRIENTS.put("muskmelon",   new int[]{80,  40,  80});
        CROP_NUTRIENTS.put("apple",       new int[]{70,  35,  70});
        CROP_NUTRIENTS.put("orange",      new int[]{100, 50,  100});
        CROP_NUTRIENTS.put("papaya",      new int[]{200, 100, 200});
        CROP_NUTRIENTS.put("coconut",     new int[]{100, 40,  140});
        CROP_NUTRIENTS.put("cotton",      new int[]{120, 60,  60});
        CROP_NUTRIENTS.put("jute",        new int[]{60,  30,  30});
        CROP_NUTRIENTS.put("coffee",      new int[]{100, 20,  150});
    }

    // Advice strings per nutrient per status
    private static final Map<String, Map<String, String>> MSGS = new HashMap<>();
    static {
        MSGS.put("N", Map.of(
            "low",  "Apply Urea (46% N) or Ammonium Sulphate (21% N). Urea is cost-effective for most crops.",
            "high", "Excess nitrogen causes leafy growth at the expense of yield. Skip N fertilizers this season.",
            "ok",   "Nitrogen levels are good. No additional nitrogen needed."
        ));
        MSGS.put("P", Map.of(
            "low",  "Apply Single Superphosphate (SSP) or DAP. SSP also adds sulphur.",
            "high", "Excess phosphorus can lock out zinc and iron. Avoid further P application this season.",
            "ok",   "Phosphorus levels are adequate. No additional phosphorus needed."
        ));
        MSGS.put("K", Map.of(
            "low",  "Apply Muriate of Potash (MOP) or Sulphate of Potash (SOP). SOP is better for sensitive crops.",
            "high", "Excess potassium can cause calcium and magnesium deficiency. Skip K fertilizers this season.",
            "ok",   "Potassium levels are fine. No additional potassium required."
        ));
    }

    public List<FertilizerAdvice> getFertilizerAdvice(String cropName, double N, double P, double K) {
        int[] req = CROP_NUTRIENTS.getOrDefault(cropName.toLowerCase(), new int[]{60, 40, 40});
        return List.of(
            buildAdvice("Nitrogen",   "N", N, req[0]),
            buildAdvice("Phosphorus", "P", P, req[1]),
            buildAdvice("Potassium",  "K", K, req[2])
        );
    }

    public List<WeatherTip> getWeatherAdvice(double temp, double humidity, double rainfall) {
        List<WeatherTip> tips = new ArrayList<>();

        if (temp < 10)
            tips.add(new WeatherTip("🌡️ Cold Alert", "Temperature is very low. Protect seedlings with mulching. Delay sowing until above 15°C."));
        else if (temp < 20)
            tips.add(new WeatherTip("🌡️ Cool Weather", "Suits wheat, peas, and mustard. Avoid planting heat-loving crops like rice or maize."));
        else if (temp <= 30)
            tips.add(new WeatherTip("🌡️ Ideal Temperature", "Ideal range for most crops. Conditions are favorable for growth and fruiting."));
        else if (temp <= 38)
            tips.add(new WeatherTip("🌡️ Warm Weather", "Ensure adequate water supply. Irrigate early morning or evening to reduce evaporation."));
        else
            tips.add(new WeatherTip("🌡️ Heat Stress Risk", "Very high temperatures can cause flower drop and yield loss. Use shade nets and irrigate more frequently."));

        if (humidity < 30)
            tips.add(new WeatherTip("💧 Low Humidity", "Dry air increases water demand. Increase irrigation frequency and mulch to conserve moisture."));
        else if (humidity <= 60)
            tips.add(new WeatherTip("💧 Good Humidity", "Humidity is in a healthy range. Fungal disease risk is low."));
        else if (humidity <= 80)
            tips.add(new WeatherTip("💧 Moderate Humidity", "Monitor crops for early signs of fungal issues like powdery mildew or blight."));
        else
            tips.add(new WeatherTip("💧 High Humidity Warning", "High disease risk. Ensure good drainage, avoid overhead irrigation, consider preventive fungicide."));

        if (rainfall < 20)
            tips.add(new WeatherTip("🌧️ Low Rainfall", "Irrigation is essential. Check soil moisture regularly."));
        else if (rainfall <= 100)
            tips.add(new WeatherTip("🌧️ Moderate Rainfall", "Suitable range. Supplement with irrigation if rainfall is irregular."));
        else if (rainfall <= 200)
            tips.add(new WeatherTip("🌧️ Good Rainfall", "Good conditions. Ensure proper field drainage to avoid waterlogging."));
        else
            tips.add(new WeatherTip("🌧️ Heavy Rainfall Alert", "Clear drainage channels. High waterlogging risk can cause root rot and nutrient leaching."));

        return tips;
    }

    // Compares user value against crop requirement and returns the appropriate advice
    private FertilizerAdvice buildAdvice(String fullName, String key, double userVal, int reqVal) {
        double ratio = reqVal > 0 ? userVal / reqVal : 1.0;
        String status, label, emoji;

        if (ratio < 0.7)       { status = "low";  label = "Deficient"; emoji = "🔴"; }
        else if (ratio > 1.5)  { status = "high"; label = "Excess";    emoji = "🟡"; }
        else                   { status = "ok";   label = "Adequate";  emoji = "🟢"; }

        return new FertilizerAdvice(fullName, userVal, reqVal, status, label, emoji, MSGS.get(key).get(status));
    }
}
