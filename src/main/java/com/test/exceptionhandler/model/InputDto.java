package com.test.exceptionhandler.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonDeserialize(builder = InputDto.InputDtoBuilder.class)
@JsonPOJOBuilder(withPrefix = "")
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
public class InputDto {
  @JsonProperty("id")
  private int id;
  @JsonProperty("name")
  private String name;
  @JsonProperty("value")
  private String value;
}
