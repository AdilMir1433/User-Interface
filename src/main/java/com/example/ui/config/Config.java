package com.example.ui.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class Config {

    @Bean
    public Cloudinary getCloudinary(){

        Map config = new HashMap<>();
        config.put("cloud_name", "dn9ugl96j");
        config.put("api_key", "618395338318682");
        config.put("api_secret", "1gohwcpUwsExaRv1Xyz7g7Gz8Bo");
        config.put("secure", true);

        return new Cloudinary(config);
    }
}
