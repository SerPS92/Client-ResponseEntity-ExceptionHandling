package com.example.ClientResponseEntityExceptionHandling.Controller;
import com.example.ClientResponseEntityExceptionHandling.Model.Pet;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class PetController {

    private final RestTemplate restTemplate;
    private final String url = "http://localhost:8080";

    public PetController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping(value = "pets", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Pet>> getPets(){

        try {
            Pet[] pets = restTemplate.getForObject(url + "/pets", Pet[].class);
            return new ResponseEntity<List<Pet>>(Arrays.asList(pets), HttpStatus.OK);

        } catch (HttpStatusCodeException exception){
            HttpHeaders headers = new HttpHeaders();
            headers.add("error", exception.getResponseBodyAsString());
            return new ResponseEntity<List<Pet>>(new ArrayList<Pet>(), headers, exception.getStatusCode());
        }
    }

    @GetMapping(value = "pet/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Pet> getPet(@PathVariable(name = "name")String name){

        HttpHeaders headers = new HttpHeaders();

        try {
            Pet pet = restTemplate.getForObject(url + "/pets/" + name, Pet.class);
            headers.add("State", "OK");
            return new ResponseEntity<Pet>(pet, headers, HttpStatus.OK);

        } catch (HttpStatusCodeException exception){
            headers.add("error", exception.getResponseBodyAsString());
            return new ResponseEntity<>(headers, exception.getStatusCode());
        }
    }

    @GetMapping(value = "pets/add/{name}/{type}/{age}")
    public ResponseEntity<Void> addPet(@PathVariable(name = "name")String name,
                                            @PathVariable(name = "type")String type,
                                            @PathVariable(name = "age")int age){

        HttpHeaders headers = new HttpHeaders();
        try {
            Pet pet = new Pet(name, type, age);
            restTemplate.postForLocation(url + "/pets", pet);
            headers.add("Status", "OK");
            return new ResponseEntity<Void>(headers, HttpStatus.OK);

        } catch (HttpStatusCodeException exception){

            headers.add("error", exception.getResponseBodyAsString());
            return new ResponseEntity<Void>(headers, exception.getStatusCode());
        }

    }

    @GetMapping(value = "pets/{id}")
    public ResponseEntity<Void> deletePets(@PathVariable(name = "id") int id){

        HttpHeaders headers = new HttpHeaders();

        try {
            restTemplate.delete(url + "/pets/{id}", id);
            headers.add("State", "OK");
            return new ResponseEntity<>(headers, HttpStatus.OK);
        }catch (HttpStatusCodeException exception){
            headers.add("error", exception.getResponseBodyAsString());
            return new ResponseEntity<>(headers, exception.getStatusCode());
        }

    }
}
