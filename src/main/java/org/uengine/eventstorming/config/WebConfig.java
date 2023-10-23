package org.uengine.eventstorming.config;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    FileInputStream fis = null;

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        try {
            registry.addMapping("/**")
                    .allowedOrigins(getOrigin())
                    .allowedMethods("*");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] getOrigin() throws IOException {
        fis = new FileInputStream("/etc/config/cors.properties");
        String data = IOUtils.toString(fis, "UTF-8");
//        String[] corsList = data.split(",");
        List<String> rawOrigins = Arrays.asList(data.split(","));
        int size = rawOrigins.size();
        String[] originArray = new String[size];
        for (int i = 0; i < rawOrigins.size(); i++)  {
            if(rawOrigins.get(i).contains("\n")) {
                rawOrigins.set(i,rawOrigins.get(i).replaceAll("\n","").trim());
            } else {
                rawOrigins.set(i,rawOrigins.get(i).trim());
            }
        }
        return rawOrigins.toArray(originArray);
    }
}
