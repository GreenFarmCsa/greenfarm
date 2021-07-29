package com.callforcode.greenfarm.fabric;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@Slf4j
public class FabricUtil {

    public static String getFile(String rPath) {
        BufferedReader reader = null;
        String content = "";
        try {
            ClassPathResource resource = new ClassPathResource(rPath);
            reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
            content = reader.lines().collect(Collectors.joining("\n"));
            reader.close();
        } catch (Exception e) {
            log.error("Fabric getFile error", e);
        } finally {
            try {
                if (null != reader) {
                    reader.close();
                }
            } catch (IOException e) {
                //ignore
            }
        }
        return content;
    }

}
