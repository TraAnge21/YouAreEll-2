package controllers;

import models.Id;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TransactionController {
    private String rootURL = "http://zipcode.rocks:8085";
    private MessageController msgCtrl;
    private IdController idCtrl;
    private RestTemplate restTemplate = new RestTemplate();

    public TransactionController(MessageController m, IdController j) {}

    public List<Id> getIds() {

        Id [] arrayId = null;
        try{
            arrayId = restTemplate.getForObject(rootURL,Id [].class);

        } catch (RestClientResponseException e ) {
            e.getRawStatusCode();
        } catch ( ResourceAccessException e) {
            e.printStackTrace();
        }
        return  new ArrayList<Id>(Arrays.asList(arrayId));



    }


    public String postId(String idtoRegister, String githubName) {
        Id tid = new Id(idtoRegister, githubName);
        tid = idCtrl.postId(tid);
        return ("Id registered.");

    }



}
