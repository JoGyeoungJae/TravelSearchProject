package com.trInfo.Controller;

import com.trInfo.entity.City;
import com.trInfo.service.CityService;
import com.trInfo.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.transaction.Transactional;
import java.util.List;

@Controller
@Transactional
@RequestMapping("/city")
public class CityController {
    @Autowired
    private CityService citySertvice;
    @Autowired
    private CountryService countryService;

    @PostMapping("citylist")
    @ResponseBody
    public List<City> citylist(String countryid) {
        Long id = Long.parseLong(countryid);
        List<City> list = citySertvice.findAllByCountry_Countryid(id);

        return list;
    }
}
