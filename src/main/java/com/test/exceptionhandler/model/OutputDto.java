package com.test.exceptionhandler.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonDeserialize(builder = OutputDto.OutputDtoBuilder.class)
@JsonPOJOBuilder(withPrefix = "")
@JsonIgnoreProperties(ignoreUnknown = true)
public class OutputDto {
    // TODO: finalize output model
}
