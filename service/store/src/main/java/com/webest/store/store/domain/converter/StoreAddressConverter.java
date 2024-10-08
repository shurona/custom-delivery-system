package com.webest.store.store.domain.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webest.web.exception.ApplicationException;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;

import static com.webest.web.exception.ErrorCode.SERVER_ERROR;

import java.util.List;

@RequiredArgsConstructor
@Converter
public class StoreAddressConverter implements AttributeConverter<List<Long>, String> {

    private final ObjectMapper objectMapper;

    /**
     * List to String
     */
    @Override
    public String convertToDatabaseColumn(List<Long> attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new ApplicationException(SERVER_ERROR.getStatus(), SERVER_ERROR.getMessage());
        }
    }

    /**
     * String to List<Long>
     */
    @Override
    public List<Long> convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, new TypeReference<List<Long>>() {});
        } catch (JsonProcessingException e) {
            throw new ApplicationException(SERVER_ERROR.getStatus(), SERVER_ERROR.getMessage());
        }
    }
}
