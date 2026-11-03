package org.example.e_commerce_api.DTO.Response.BasebodyResponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;
import org.example.e_commerce_api.Constant.Message.ResponseMessageConstant;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import static org.example.e_commerce_api.Config.TraceFilter.PATH;
import static org.example.e_commerce_api.Config.TraceFilter.TRACE_ID;
import static org.example.e_commerce_api.Constant.app.AppConstant.REQUEST_ID;

@Getter
@Setter
public class BaseBodyResponse<T> implements Serializable {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private boolean success;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private PageResponse meta;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private StatusResponse status;

    private String timestamp;

    private String traceId;

    private String requestId;

    private String path;



    private static void populateTraceAndPath(BaseBodyResponse<?> response){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null){
            HttpServletRequest request = requestAttributes.getRequest();
            response.setTraceId((String) request.getAttribute(TRACE_ID));
            response.setPath((String) request.getAttribute(PATH));
            response.setTimestamp(String.valueOf(Instant.now()));
            response.setRequestId((String) request.getAttribute(REQUEST_ID));
        }
    }
    private static <T> BaseBodyResponse<T> buildSuccess(String message){
        BaseBodyResponse<T> response = new BaseBodyResponse<>();
        response.setSuccess(true);
        response.setStatus(new StatusResponse(HttpStatus.OK.value(),
                message != null ? message : ResponseMessageConstant.SUCCESS));
        populateTraceAndPath(response);
        return response;
    }

    private static <T> BaseBodyResponse<T> buildFailure(HttpStatusCode statusCode, String message){
        BaseBodyResponse<T> response = new BaseBodyResponse<>();
        response.setSuccess(false);
        response.setStatus(StatusResponseUtils.createStatusResponse(statusCode, message));
        populateTraceAndPath(response);
        return response;
    }

    public static <T> ResponseEntity<BaseBodyResponse<T>> success(T data, String message){
        BaseBodyResponse<T> response = buildSuccess(message);
        response.setData(data);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
    }
    public static <T> ResponseEntity<BaseBodyResponse<List<T>>> success(List<T> data, String message){
        short statusCode = 200;
        BaseBodyResponse<List<T>> response = new BaseBodyResponse<>();
        StatusResponse statusResponse =
                new StatusResponse(statusCode,message != null ? message : ResponseMessageConstant.SUCCESS);
        response.setSuccess(true);
        response.setStatus(statusResponse);
        response.setData(data);
        populateTraceAndPath(response);
        return ResponseEntity.status(statusCode).body(response);
    }
    public static ResponseEntity<BaseBodyResponse<Void>> success(String message){
        BaseBodyResponse<Void> response = new BaseBodyResponse<>();
        return ResponseEntity.ok(response);
    }
    public static <T> ResponseEntity<BaseBodyResponse<List<T>>> pageSuccess(Page<T> page, String message){
        BaseBodyResponse<List<T>> response = new BaseBodyResponse<>();
        response.setData(page.getContent());
        response.setMeta(page.getPageable().isUnpaged() ? null : new PageResponse(page));
        return ResponseEntity.ok(response);
    }

    public static ResponseEntity<BaseBodyResponse<Void>> failed(HttpStatusCode statusCode, String message){
        BaseBodyResponse<Void> response = buildFailure(statusCode, message);
        return ResponseEntity.status(statusCode).body(response);
    }
    //using when throw exception in controller advice
    public static <T> BaseBodyResponse<T> bodyFailed(HttpStatusCode statusCode, String message){
        return buildFailure(statusCode, message);
    }
}
