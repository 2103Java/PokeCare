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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/api/trainer")
public class TrainerController {
    private final TrainerService trainerService;
    private final FileService fileService;

    @Autowired
    public TrainerController(TrainerService trainerService, FileService fileService) {
        this.trainerService = trainerService;
        this.fileService = fileService;
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Trainer> postTrainer(@RequestBody MultiValueMap<String, String> form, HttpSession session) {
        Trainer trainer = trainerService.login(form.getFirst("username"), form.getFirst("password"));

        if (trainer == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        session.setAttribute("PokeTrainer", trainer);
        return new ResponseEntity<>(trainer, HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<?> register(@RequestBody MultiValueMap<String, String> form) {
        return trainerService.register(new Trainer(form.getFirst("username"), form.getFirst("email"), form.getFirst("password"))) ?
                new ResponseEntity<>(HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @PostMapping(value = "/friends/new/{username}")
    public ResponseEntity<Integer> addFriend(@PathVariable String username, HttpSession session) {
        if (session.getAttribute("PokeTrainer") == null) return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

        if (trainerService.addFriend((Trainer) session.getAttribute("PokeTrainer"), username))
            return new ResponseEntity<Integer>(1, HttpStatus.OK);

        return new ResponseEntity<Integer>(0, HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/friends/requests")
    public ResponseEntity<List<Trainer>> myRequests(HttpSession session){
        if (session.getAttribute("PokeTrainer") == null) return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

        Trainer trainer = (Trainer) session.getAttribute(("PokeTrainer"));
        List<Trainer> rCheck = trainerService.myFriendReq(trainer);
        session.setAttribute("PokeTrainer", trainer);
        if (rCheck.isEmpty()) return new ResponseEntity<List<Trainer>>(HttpStatus.OK);

        return new ResponseEntity<List<Trainer>>(rCheck, HttpStatus.OK);
    }

    @PutMapping(value = "/friends/update/{id}")
    public ResponseEntity<String> acceptRequest(@PathVariable int id, HttpSession session){
        if (session.getAttribute("PokeTrainer") == null) return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

        Trainer friendee = (Trainer) session.getAttribute("PokeTrainer");
        trainerService.processFriendRequest(friendee, id, "ACCEPTED");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/friends/update/{id}")
    public ResponseEntity<String> rejectRequest(@PathVariable int id, HttpSession session){
        if (session.getAttribute("PokeTrainer") == null) return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

        Trainer friendee = (Trainer) session.getAttribute("PokeTrainer");
        trainerService.processFriendRequest(friendee, id, "REJECTED");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/friends")
    public ResponseEntity<List<Trainer>> myFriends(HttpSession session) {
        if (session.getAttribute("PokeTrainer") == null) return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

        Trainer trainer = (Trainer) session.getAttribute(("PokeTrainer"));
        List<Trainer> fCheck = trainerService.myFriends(trainer);
        session.setAttribute("PokeTrainer", trainer);
        if (fCheck.isEmpty()) return new ResponseEntity<List<Trainer>>(HttpStatus.OK);

        return new ResponseEntity<List<Trainer>>(fCheck, HttpStatus.OK);
    }

    //Delete a trainer based on a passed in JSON Trainer
    @DeleteMapping(value = "/delete")
    public ResponseEntity<String> deleteTrainer(@RequestBody Trainer dTrainer, HttpSession session) {

        if (session.getAttribute("PokeTrainer").equals(dTrainer)) {
            boolean trDeleted = trainerService.deleteTrainer(dTrainer);
            if (trDeleted) {
                session.invalidate();
                return new ResponseEntity<>(HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
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

    @DeleteMapping(value = "/logout")
    @ResponseStatus(HttpStatus.OK)
    public void logout(HttpSession session) {
        session.invalidate();
    }

    @GetMapping
    public ResponseEntity<Trainer> getTrainer(HttpSession session) {
        Trainer trainer = (Trainer) session.getAttribute("PokeTrainer");

        return trainer == null ? new ResponseEntity<>(HttpStatus.FORBIDDEN) : new ResponseEntity<>(trainer, HttpStatus.OK);
    }
}