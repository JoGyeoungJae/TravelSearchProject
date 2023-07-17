package com.trInfo.Controller;

import com.trInfo.service.CityService;
import com.trInfo.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.transaction.Transactional;

@Controller
@Transactional
public class MainController {

    @Autowired
    private CityService citySertvice;
    @Autowired
    private CountryService countryService;

    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("countryList", countryService.getcountrylist());
        return "main";
    }

}
