package org.example.e_commerce_api.DTO.Response.BasebodyResponse;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.e_commerce_api.Constant.Message.ResponseMessageConstant;
import org.springframework.http.HttpStatusCode;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StatusResponseUtils {
    public static StatusResponse createStatusResponse(HttpStatusCode statusCode, String message){
        return new StatusResponse(
                (short) statusCode.value(), message != null ? message : ResponseMessageConstant.FAILED
        );
    }
}
