package com.webest.rider.domain.model.converter;

import static com.webest.web.exception.ErrorCode.SERVER_ERROR;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webest.web.exception.ApplicationException;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Converter
public class RiderAddressConverter implements AttributeConverter<List<Long>, String> {

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
            return objectMapper.readValue(dbData, new TypeReference<List<Long>>() {
            });
        } catch (JsonProcessingException e) {
            throw new ApplicationException(SERVER_ERROR.getStatus(), SERVER_ERROR.getMessage());
        }
    }
}
