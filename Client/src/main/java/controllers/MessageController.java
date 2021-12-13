package controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import models.Id;
import models.Message;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class MessageController {

    private final String BASE_URL = "http://zipcode.rocks:8085";
    private RestTemplate restTemplate = new RestTemplate();
    private HashSet<Message> messagesSeen;
    // why a HashSet??

    public ArrayList<Message> getMessages() {

        Message [] arrayMessage = null;

        try{
            arrayMessage = restTemplate.getForObject(BASE_URL,Message [].class);

        } catch (RestClientResponseException e ) {
            e.getRawStatusCode();
        } catch ( ResourceAccessException e) {
            e.printStackTrace();
        }
        return  new ArrayList<Message>(Arrays.asList(arrayMessage));


    }


    public ArrayList<Message> getMessagesForId(Id id) {

        Message [] arrayMessage = null;

        try{
            arrayMessage = restTemplate.getForObject(BASE_URL + id.getUid(),Message [].class, Id[].class);

        } catch (RestClientResponseException e ) {
            e.getRawStatusCode();
        } catch ( ResourceAccessException e) {
            e.printStackTrace();
        }
        return  new ArrayList<Message>(Arrays.asList(arrayMessage));

    }


    public Message getMessageForSequence(String seq) {
        Message arrayMessageSequence = null;

        try{
            arrayMessageSequence = restTemplate.getForObject(BASE_URL +seq,Message.class, String.class);

        } catch (RestClientResponseException e ) {
            e.getRawStatusCode();
        } catch ( ResourceAccessException e) {
            e.printStackTrace();
        }
        return  arrayMessageSequence;

    }


    public ArrayList<Message> getMessagesFromFriend(Id myId, Id friendId) {

        Message [] arrayMessage = null;

        try{
            arrayMessage = restTemplate.getForObject(BASE_URL + myId.getUid() + friendId.getUid(),Message [].class, Id[].class);

        } catch (RestClientResponseException e ) {
            e.getRawStatusCode();
        } catch ( ResourceAccessException e) {
            e.printStackTrace();
        }
        return  new ArrayList<Message>(Arrays.asList(arrayMessage));


    }



    public Message postMessage(Id myId, Id toId, Message msg) {

        try{
            HttpEntity <Message> entity = makeEntity(msg);
            return restTemplate.postForObject(BASE_URL +myId.getUid()+ toId.getUid()+msg.getMessage(), Id.class, Message.class);
        } catch (RestClientResponseException e ) {
            e.getRawStatusCode();
        } catch ( ResourceAccessException e) {
            e.printStackTrace();
        }
        return null;

    }



    private HttpEntity<Message> makeEntity (Message message) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<Message>(message,headers);
    }


}