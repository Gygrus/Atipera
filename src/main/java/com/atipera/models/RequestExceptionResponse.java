package com.atipera.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RequestExceptionResponse {
    private String Message;
    private int status;
}
