package com.lambdaschool.schools.controllers;

import com.lambdaschool.schools.models.AdviceData;
import com.lambdaschool.schools.models.Instructor;
import com.lambdaschool.schools.services.InstructorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@RestController
@RequestMapping(value ="/instructors")
public class InstructorController
{
    @Autowired
    InstructorServiceImpl instructorService;
    private RestTemplate restTemplate = new RestTemplate();


   @PatchMapping(value = "/instructor/{instid}/advice",consumes = "application/json",produces = "application/json")
    public ResponseEntity<?> getAdviceById(@PathVariable long instid)
   {
       MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
       converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
       restTemplate.getMessageConverters().add(converter);
       String requestURL = "https://api.adviceslip.com/advice";
      ParameterizedTypeReference<AdviceData> responseType = new ParameterizedTypeReference<>()
      {
      };
      ResponseEntity<AdviceData> responseEntity = restTemplate.exchange(requestURL,HttpMethod.GET,null,responseType);

       System.out.println(responseEntity.getBody());
       instructorService.ourAdviceData = responseEntity.getBody();
       instructorService.addAdvice(instid);
//       instructorService.ourAdvice =responseEntity.getBody();
//       Instructor instructor= instructorService.addAdvice(instid,instructor);
//       return new ResponseEntity<>(HttpStatus.CREATED);
       return new ResponseEntity<>(HttpStatus.OK);
   }

   @GetMapping(value = "/instructor/{instid}",produces = "application/json")
    public ResponseEntity<?> findInstructorById(@PathVariable long instid)
   {
       Instructor instructor = instructorService.findInstructorByid(instid);
       return new ResponseEntity<>(instructor,HttpStatus.OK);
   }
}
