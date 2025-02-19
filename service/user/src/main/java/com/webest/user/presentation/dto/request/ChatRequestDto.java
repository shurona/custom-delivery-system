package com.webest.user.presentation.dto.request;

public record ChatRequestDto(
    Long userId,
    String chatData
) {

//    @JsonCreator
//    public static ChatRequestDto fromJson(String json) {
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            return objectMapper.readValue(json, ChatRequestDto.class);
//        } catch (Exception e) {
//            throw new RuntimeException("JSON 변환 오류: " + json, e);
//        }
//    }

}
