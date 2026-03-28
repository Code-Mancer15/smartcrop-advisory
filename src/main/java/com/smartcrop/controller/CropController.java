package com.smartcrop.controller;
import jakarta.servlet.http.HttpSession;
import com.smartcrop.model.AdvisoryResult;
import com.smartcrop.model.CropInput;
import com.smartcrop.service.AdvisorService;
import com.smartcrop.service.CropPredictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CropController {

    @Autowired private CropPredictionService predictionService;
    @Autowired private AdvisorService advisorService;

    // Show the input form with default values pre-filled

    @GetMapping("/")
public String showHome(HttpSession session, Model model) {

    if (session.getAttribute("user") == null) {
        return "redirect:/signup"; // 🔒 block access
    }

    model.addAttribute("cropInput", new CropInput());
    return "index";
}


        @PostMapping("/predict")
public String predict(@ModelAttribute CropInput input,
                      Model model,
                      HttpSession session) {

    if (session.getAttribute("user") == null) {
        return "redirect:/signup"; // 🔒 block access
    }

    String crop = predictionService.predict(input);

    model.addAttribute("result",
            new AdvisoryResult(
                    crop,
                    advisorService.getFertilizerAdvice(crop,
                            input.getNitrogen(),
                            input.getPhosphorus(),
                            input.getPotassium()),
                    advisorService.getWeatherAdvice(
                            input.getTemperature(),
                            input.getHumidity(),
                            input.getRainfall()),
                    input
            )
    );

    return "index";
}

}
