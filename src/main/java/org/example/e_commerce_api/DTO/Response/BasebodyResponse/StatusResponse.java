package org.example.e_commerce_api.DTO.Response.BasebodyResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import org.example.e_commerce_api.Constant.Message.ResponseMessageConstant;

import java.io.Serializable;

public class StatusResponse implements Serializable {
    @Schema(example = "200")
    public int code;

    @Schema(example = "Success")
    public String message;

    public StatusResponse(int code, String message) {
        this.code = code;
        if (message != null){
            this.message = message;
        }else {
                // Default based on code
                switch (code) {
                    case 200: // OK
                        this.message = ResponseMessageConstant.SUCCESS;
                        break;
                    case 400: // Bad Request
                        this.message = ResponseMessageConstant.OPERATION_BAD_REQUEST;
                        break;
                    case 401: // Unauthorized
                        this.message = ResponseMessageConstant.OPERATION_UNAUTHORIZED;
                        break;
                    case 404: // Not Found
                        this.message = ResponseMessageConstant.OPERATION_NOT_FOUND;
                        break;
                    case 500: // Internal Server Error
                        this.message = ResponseMessageConstant.FAILED;
                        break;
                    default:
                        this.message = ResponseMessageConstant.OPERATION_UNKNOWN;
                }
        }
    }
}
