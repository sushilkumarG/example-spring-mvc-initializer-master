package com.example.service.nearby;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize.Inclusion;

/**
 * This class represents a response that will handle error and success scenario
 * 
 * @author Rajeev Pandey
 * 
 */
@JsonInclude(Include.NON_EMPTY)
public class APIResponse implements Serializable {

    private static final long serialVersionUID = -7809000164180146201L;
    private Long              totalCount;
    private String            statusCode;

    @JsonInclude(Include.ALWAYS)
    private Object            data;

    // @JsonInclude(Include.NON_EMPTY)
    @JsonSerialize(include = Inclusion.NON_EMPTY, using = APIErrorSerialize.class)
    private APIError          error;

    public APIResponse() {
        super();
        this.statusCode = ResponseCodes.SUCCESS;
    }

    public APIResponse(Object data) {
        super();
        this.statusCode = ResponseCodes.SUCCESS;
        this.data = data;
    }

    public APIResponse(Object data, String errorMessage) {
        super();
        this.statusCode = ResponseCodes.SUCCESS;
        this.data = data;
        this.error = new APIError(errorMessage);
    }

    public APIResponse(Object data, Long totalCount) {
        super();
        this.statusCode = ResponseCodes.SUCCESS;
        this.totalCount = totalCount;
        this.data = data;
    }

    public APIResponse(Object data, int totalCount) {
        super();
        this.statusCode = ResponseCodes.SUCCESS;
        this.totalCount = new Long(totalCount);
        this.data = data;
    }

    public APIResponse(String statusCode, String errorMessage) {
        super();
        this.statusCode = statusCode;
        this.error = new APIError(errorMessage);
    }

    public APIResponse(String statusCode, String errorMessage, List<String> stackTrace) {
        super();
        this.statusCode = statusCode;
        this.error = new APIError(errorMessage);
        this.error.setStackTrace(stackTrace);
    }

    public APIResponse(PaginatedResponse<?> paginatedResponse) {
        super();
        this.statusCode = ResponseCodes.SUCCESS;
        this.data = paginatedResponse.getResults();
        this.totalCount = paginatedResponse.getTotalCount();
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public APIResponse setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
        return this;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
        // return this;
    }

    public Object getData() {
        return data;
    }

    public APIResponse setData(Object data) {
        this.data = data;
        return this;
    }

    public APIError getError() {
        return error;
    }

    public APIResponse setError(APIError error) {
        this.error = error;
        return this;
    }

    public APIResponse setErrorMessage(String msg) {
        this.error = new APIError(msg);
        return this;
    }

    @JsonInclude(Include.NON_EMPTY)
    public static class APIError {
        @JsonInclude(Include.NON_EMPTY)
        private String msg = "SOME_ERROR_OCCURED";
        
        @JsonInclude(Include.NON_NULL)
        private List<String> stackTrace;

        public APIError() {
        }

        public APIError(String msg) {
            super();
            this.msg = msg;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public List<String> getStackTrace() {
            return stackTrace;
        }

        public void setStackTrace(List<String> stackTrace) {
            this.stackTrace = stackTrace;
        }

        @Override
        public String toString() {
            return "{msg=" + msg + "}";
        }
    }

    public static class APIErrorSerialize extends JsonSerializer<APIError> {

        @Override
        public void serialize(APIError value, JsonGenerator jgen, SerializerProvider provider)
                throws IOException, JsonProcessingException {
            jgen.writeObject(value);
            /*
             * if(value.getMsg() != null && !value.getMsg().isEmpty()){
             * jgen.writeStringField("msg", value.getMsg()); }
             * if(!CollectionUtils.isEmpty(value.getStackTrace())){
             * jgen.writeObjectField("stackTrace", value.getStackTrace()); }
             */
        }


    }
}