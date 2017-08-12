package com.hands.desafio.http.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.List;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Component
public class JsonHelper {

    private final ObjectMapper mapper;

    public <T> T toObject(final String pathJson, final Class<T> clazz) {
        try {
            return mapper.readValue(getJson(pathJson), clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private URL getJson(final String pathJson) {
        return this.getClass().getClassLoader().getResource(pathJson);
    }

    public String objectToJson(final Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> List<T> toListObect(final String pathJson, final Class<T> clazz){
        try {
            return mapper.readValue(getJson(pathJson), mapper.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}