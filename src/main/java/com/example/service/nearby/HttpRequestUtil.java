package com.example.service.nearby;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@Component
public class HttpRequestUtil {
    private static RestTemplate restTemplate;

    @Autowired
    private ApplicationContext  applicationContext;

    private HttpRequestUtil     httpRequestUtil;

    private ObjectMapper mapper;
    private ObjectWriter        writer;

    private static Logger       logger = LoggerFactory.getLogger(HttpRequestUtil.class);

    private HttpRequestUtil getHttpRequestUtil() {
        if (httpRequestUtil == null) {
            httpRequestUtil = applicationContext.getBean(HttpRequestUtil.class);
        }
        return httpRequestUtil;
    }

    @PostConstruct
    private void initilize() {
        mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        writer = mapper.writer();
    }

    static {
        restTemplate = getRequestTemplate(null);
    }

    /**
     * returns a rest template based on whether readtimeout is required or not
     * 
     * @param timeOut
     * @return
     */
    private static RestTemplate getRequestTemplate(Integer timeOut) {
        RestTemplate restTemplate = new RestTemplate();
        if (timeOut != null) {
            ((SimpleClientHttpRequestFactory) restTemplate.getRequestFactory()).setReadTimeout(timeOut);
        }

        restTemplate.setErrorHandler(new ResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse response) throws IOException {
                /*
                 * Return false in all cases even if there was a error while
                 * handling a url. This is to include original error message
                 * returned from server in the composite api response
                 */
                return false;
            }

            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                // this method will never be used because we are returning false
                // in each case from hasError method
            }
        });
        return restTemplate;
    }

    public <T> PaginatedResponse<List<T>> getPaginatedResponse(URI uri, Class<T> type) {
        return getInternalApiResultAsPaginatedResponse(uri, type, false, null, null);
    }

    public <T> T postInternalRequest(URI uri, Class<T> type) {
        APIResponse apiResponse = restTemplate.postForObject(uri, null, APIResponse.class);
        Object object = parseAPIResponseObject(uri, apiResponse);

        return convertObjectToClass(object, type);
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> postAndReturnInternalJsonRequest(URI uri, List<T> data, Class<T> type) {
        Object object = postInternalJsonRequest(uri, data, type);
        return convertObjectToClass((List<Object>) object, type);
    }

    public <T> T postAndReturnInternalJsonRequest(URI uri, Object data, Class<T> type) {
        Object object = postInternalJsonRequest(uri, data, type);
        return convertObjectToClass(object, type);
    }

    /**
     * 
     * @param uri
     * @param type
     * @return List of specified type
     */
    public <T> List<T> getInternalApiResultAsTypeList(URI uri, Class<T> type) {
        return getInternalApiResultAsTypeList(uri, type, false, null, null);
    }

    /**
     * returns value from cache if exists
     * 
     * @param uri
     * @param type
     * @return List of specified type
     */
    public <T> List<T> getInternalApiResultAsTypeListFromCache(URI uri, Class<T> type) {
        return getInternalApiResultAsTypeList(uri, type, true, null, null);
    }

    /**
     * 
     * @param uri
     * @param type
     * @return List of specified type
     */
    public <T> List<T> getInternalApiResultAsTypeList(URI uri, HttpHeaders httpHeaders, Class<T> type) {
        return getInternalApiResultAsTypeList(uri, type, false, httpHeaders, null);
    }

    /**
     * returns value from cache if exists
     * 
     * @param uri
     * @param type
     * @return List of specified type
     */
    public <T> List<T> getInternalApiResultAsTypeListFromCache(URI uri, HttpHeaders httpHeaders, Class<T> type) {
        return getInternalApiResultAsTypeList(uri, type, true, httpHeaders, null);
    }

    /**
     * returns value from cache if exists throws read timeout exception if
     * requests takes long
     * 
     * @param uri
     * @param type
     * @return List of specified type
     */
    public <T> List<T> getInternalApiResultAsTypeListFromCache(URI uri, int timeOut, Class<T> type)
            throws ResourceAccessException {
        return getInternalApiResultAsTypeList(uri, type, true, null, timeOut);
    }

    /**
     * returns value from cache if exists
     * 
     * @param uri
     * @param httpHeaders
     * @param type
     * @return Object of specified type
     */
    public <T> T getInternalApiResultAsTypeFromCache(URI uri, HttpHeaders httpHeaders, Class<T> type) {
        return convertObjectToClass(getHttpRequestUtil().getApiResponse(uri, httpHeaders, null).getData(), type);
    }

    /**
     * returns value from cache if exists
     * 
     * @param uri
     * @param type
     * @return Object of specified type
     */
    public <T> T getInternalApiResultAsTypeFromCache(URI uri, Class<T> type) {
        return convertObjectToClass(getHttpRequestUtil().getApiResponse(uri, null, null).getData(), type);
    }

    /**
     * returns value from cache if exists
     * 
     * @param uri
     * @param httpHeaders
     * @param type
     * @return Object of specified type
     */
    public <T> T getInternalApiResultAsType(URI uri, HttpHeaders httpHeaders, Class<T> type) {
        return convertObjectToClass(getApiResponse(uri, httpHeaders, null).getData(), type);
    }

    @SuppressWarnings("unchecked")
    private <T> PaginatedResponse<List<T>> getInternalApiResultAsPaginatedResponse(
            URI uri,
            Class<T> clazz,
            boolean useCache,
            HttpHeaders httpHeaders,
            Integer timeOut) {
        APIResponse apiResponse;
        PaginatedResponse<List<T>> paginatedResponse = new PaginatedResponse<>();
        if (useCache) {
            if (httpHeaders == null) {
                apiResponse = getHttpRequestUtil().getApiResponse(uri, null, timeOut);
            }
            else {
                apiResponse = getHttpRequestUtil().getApiResponse(uri, httpHeaders, timeOut);
            }

        }
        else {
            if (httpHeaders == null) {
                apiResponse = getApiResponse(uri, null, timeOut);
            }
            else {
                apiResponse = getApiResponse(uri, httpHeaders, timeOut);
            }

        }
        List<Object> objectList = null;
        if (apiResponse.getData() instanceof LinkedHashMap) {
            LinkedHashMap l = (LinkedHashMap) apiResponse.getData();
            objectList = (List<Object>) l.get("items");
        }
        else {
            objectList = (List<Object>) apiResponse.getData();
        }

        paginatedResponse.setResults(convertObjectToClass(objectList, clazz));
        if (apiResponse.getTotalCount() != null) {
            paginatedResponse.setTotalCount(apiResponse.getTotalCount());
        }
        return paginatedResponse;
    }

    @SuppressWarnings("unchecked")
    private <T> List<T> getInternalApiResultAsTypeList(
            URI uri,
            Class<T> clazz,
            boolean useCache,
            HttpHeaders httpHeaders,
            Integer timeOut) {
        return getInternalApiResultAsPaginatedResponse(uri, clazz, useCache, httpHeaders, timeOut).getResults();
    }

    private <T> List<T> convertObjectToClass(List<Object> objects, Class<T> clazz) {
        List<T> finalList = new ArrayList<>();
        if (objects != null) {
            objects.forEach(o -> finalList.add(convertObjectToClass(o, clazz)));
        }
        return finalList;
    }

    private <T> T convertObjectToClass(Object object, Class<T> clazz) {
        try {
            if (object instanceof LinkedHashMap) {
                Object uri = ((LinkedHashMap) object).get("URL");
                ((LinkedHashMap) object).put("url", uri);
            }
            return mapper.readValue(writer.writeValueAsString(object), clazz);
        }
        catch (IOException e) {
            throw new IllegalArgumentException("Error Mapping APIResponse to :" + clazz, e);
        }
    }

    private APIResponse getApiResponse(URI uri, HttpHeaders httpHeaders, Integer timeOut) {
        Date date1 = new Date();
        RestTemplate template = restTemplate;
        if (timeOut != null) {
            template = getRequestTemplate(timeOut);
        }

        ResponseEntity<APIResponse> response;
        try {
            HttpEntity<APIResponse> requestEntity = new HttpEntity<APIResponse>(httpHeaders);
            response = template.exchange(uri, HttpMethod.GET, requestEntity, APIResponse.class);
        }
        catch (Exception e) {
            logger.error("Exception calling api: " + uri);
            logger.error("Stacktrace: ", e);
            throw new IllegalArgumentException(e);
        }
        APIResponse responseBody = response.getBody();
        Date date2 = new Date();
        long diff = date2.getTime() - date1.getTime();
        logger.info("TIME TAKEN IN INTERNAL API(" + uri + ") CALL:" + diff);
        return responseBody;
    }

    public APIResponse getApiResponseFromCache(URI uri) {
        return getApiResponse(uri, null, null);
    }

    public APIResponse getApiResponseFromCache(URI uri, HttpHeaders httpHeaders) {
        return getApiResponse(uri, httpHeaders, null);
    }

    private Object postInternalJsonRequest(URI uri, Object data, Class<?> type) {
        String json = null;
        try {
            json = mapper.writeValueAsString(data);
        }
        catch (Exception e) {
            logger.error("error while converting data to json format");
            throw new IllegalArgumentException(e);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        @SuppressWarnings({ "rawtypes", "unchecked" })
        HttpEntity request = new HttpEntity(json, headers);

        logger.info(" API URL " + uri + " Post Data" + json);
        APIResponse apiResponse = restTemplate.postForObject(uri, request, APIResponse.class);

        return parseAPIResponseObject(uri, apiResponse);
    }

    public void putInternalJsonRequest(URI uri, Object data, Class<?> type) {
        String json = null;
        try {
            json = mapper.writeValueAsString(data);
        }
        catch (Exception e) {
            logger.error("error while converting data to json format");
            throw new IllegalArgumentException(e);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        @SuppressWarnings({ "rawtypes", "unchecked" })
        HttpEntity request = new HttpEntity(json, headers);

        logger.info(" API URL " + uri + " PUT Data" + json);
        try {
            restTemplate.put(uri, request);
        }
        catch (Exception e) {
            logger.error("Unable to PUT data in database for url " + uri);
            throw new IllegalArgumentException("Unable to PUT data in database for url " + uri);
        }

    }

    private Object postInternalJsonRequest(URI uri, List<?> data, Class<?> type) {
        String json = null;
        try {
            json = mapper.writeValueAsString(data);
        }
        catch (JsonProcessingException e) {
            logger.error("Exception in internal post", e);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        @SuppressWarnings({ "rawtypes", "unchecked" })
        HttpEntity request = new HttpEntity(json, headers);

        logger.info(" API URL " + uri + " Post Data" + json);
        APIResponse apiResponse = restTemplate.postForObject(uri, request, APIResponse.class);
        try {
            logger.info(" API Response " + mapper.writeValueAsString(apiResponse));
        }
        catch (JsonProcessingException e) {
            logger.error("Exception in internal post", e);
        }

        return parseAPIResponseObject(uri, apiResponse);
    }

    private Object parseAPIResponseObject(URI uri, APIResponse apiResponse) {
        if (apiResponse == null || !apiResponse.getStatusCode().equals(ResponseCodes.SUCCESS)) {
            logger.info(" API Failed " + uri.toString() + " URI " + uri + " Error Occurred : " + apiResponse.toString());
        }

        return apiResponse.getData();
    }

}
