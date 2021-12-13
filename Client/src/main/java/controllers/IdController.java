package controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import models.Id;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class IdController {

    private final String BASE_URL = "http://zipcode.rocks:8085";
    private RestTemplate restTemplate = new RestTemplate();
    private HashMap <String, Id> allIds;
    Id myId;



    public ArrayList<Id> getIds() {
        Id [] arrayId = null;
        try{
            arrayId = restTemplate.getForObject(BASE_URL,Id [].class);

        } catch (RestClientResponseException e ) {
            e.getRawStatusCode();
        } catch ( ResourceAccessException e) {
            e.printStackTrace();
        }
        return  new ArrayList<Id>(Arrays.asList(arrayId));
    }

    public Id postId(Id id) {
        // create json from id
        // call server, get json result Or error
        // result json to Id obj

        try{
            HttpEntity <Id> entity = makeEntity(id);
            return restTemplate.postForObject(BASE_URL,entity, Id.class);
        } catch (RestClientResponseException e ) {
            e.getRawStatusCode();
        } catch ( ResourceAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Id putId(Id id) {

        try{
            HttpEntity <Id> entity = makeEntity(id);
            restTemplate.put(BASE_URL+id.getUid(),entity);
        } catch (RestClientResponseException e ) {
            e.getRawStatusCode();
        } catch ( ResourceAccessException e) {
            e.printStackTrace();
        }
        return null;

    }

    private HttpEntity <Id> makeEntity (Id id) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<Id>(id,headers);
    }


}