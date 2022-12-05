package io.sudhakar.student.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    private int id;
    private String area;
    private int pincode;
    private String district;

    @JsonProperty("user_id")
    private int studentId;
}

