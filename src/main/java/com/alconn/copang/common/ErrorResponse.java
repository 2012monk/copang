package com.alconn.copang.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse extends ResponseMessage<String> {

    private int status;


}
