package com.sentura.countries.service;

import com.sentura.countries.model.Country;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class CountryService {

    private List<Country> cachedCountries = new ArrayList<>();
    private long lastFetchTime = 0;

    public List<Country> getCountries() {

        long currentTime = System.currentTimeMillis();

        // 10 minutes cache
        if (cachedCountries.isEmpty() || currentTime - lastFetchTime > 600000) {

            String url = "https://restcountries.com/v3.1/all";

            RestTemplate restTemplate = new RestTemplate();

            List<Map<String, Object>> response =
                    restTemplate.getForObject(url, List.class);

            List<Country> countries = new ArrayList<>();

            for (Map<String, Object> item : response) {

                Map nameObj = (Map) item.get("name");
                List capitalList = (List) item.get("capital");
                Map flags = (Map) item.get("flags");

                Country c = new Country();

                c.setName(nameObj.get("common").toString());

                if (capitalList != null && !capitalList.isEmpty()) {
                    c.setCapital(capitalList.get(0).toString());
                }

                c.setRegion(item.get("region").toString());
                c.setPopulation(Long.parseLong(item.get("population").toString()));
                c.setFlag(flags.get("png").toString());

                countries.add(c);
            }

            cachedCountries = countries;
            lastFetchTime = currentTime;
        }

        return cachedCountries;
    }
    public List<Country> searchCountries(String keyword){

        List<Country> countries = getCountries();

        List<Country> result = new ArrayList<>();

        for(Country c : countries){
            if(c.getName().toLowerCase().contains(keyword.toLowerCase())){
                result.add(c);
            }
        }

        return result;
    }

}