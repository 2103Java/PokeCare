package com.revature.pokecare.controllers;

import com.revature.pokecare.models.Trainer;
import com.revature.pokecare.service.FileService;
import com.revature.pokecare.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("/trainer")
public class TrainerController {
    private final TrainerService ts;
    private final FileService fileService;

    @Autowired
    public TrainerController(TrainerService ts, FileService fileService) {
        this.ts = ts;
        this.fileService = fileService;
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Trainer> postTrainer(@RequestBody MultiValueMap<String, String> form, HttpSession session) {
        Trainer trainer = ts.login(form.getFirst("username"), form.getFirst("password"));

        if (trainer == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        session.setAttribute("PokeTrainer", trainer);
        return new ResponseEntity<>(trainer, HttpStatus.OK);
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<?> register(@RequestBody MultiValueMap<String, String> form) {
        System.out.println(form);
        return ts.register(new Trainer(form.getFirst("username"), form.getFirst("email"), form.getFirst("password"))) ?
                new ResponseEntity<>(HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    //Delete a trainer based on a passed in JSON Trainer
    @DeleteMapping(value = "/delete")
    public ResponseEntity<String> deleteTrainer(@RequestBody Trainer dTrainer, HttpSession session) {

        if (session.getAttribute("PokeTrainer").equals(dTrainer)) {
            boolean trDeleted = ts.deleteTrainer(dTrainer);
            if (trDeleted) {
                session.invalidate();
                return new ResponseEntity<String>(HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity<String>(HttpStatus.NOT_ACCEPTABLE);
            }
        }
        return null;
    }

    @PutMapping(value = "/profile", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public ResponseEntity<?> putProfilePicture(@RequestParam("pic") MultipartFile file, HttpServletRequest request, HttpSession session) {
        Trainer trainer = (Trainer) session.getAttribute("PokeTrainer");

        if (trainer == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        try {
            fileService.putFile(String.valueOf(trainer.getId()), file.getInputStream());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //lougout ping closes the session
    @PostMapping(value = "/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return new ResponseEntity<String>("Logout Successful.", HttpStatus.OK);
    }
}