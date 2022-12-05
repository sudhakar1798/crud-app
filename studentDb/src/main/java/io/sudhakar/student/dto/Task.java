package io.sudhakar.student.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    private int id;
    private String name;
    private String description;
    private String status;

    @JsonProperty("user_id")
    private long userId;

}
