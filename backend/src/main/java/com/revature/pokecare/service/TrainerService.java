package com.revature.pokecare.service;

import com.revature.pokecare.models.Pokemon;
import com.revature.pokecare.models.Trainer;
import com.revature.pokecare.repository.TrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class TrainerService {
    private final TrainerRepository trainerRepo;
    private final PokemonService pokemonService;
    private final MailService mailService;

    @Autowired
    public TrainerService(TrainerRepository trainerRepo, PokemonService pokemonService, MailService mailService) {
        this.trainerRepo = trainerRepo;
        this.pokemonService = pokemonService;
        this.mailService = mailService;
    }

    public Trainer login(String username, String password) {
        //Making sure these aren't empty strings. We don't want empty strings!
        if (username != null && password != null) {
            Trainer train = trainerRepo.findTrainerByUsername(username);
            //And just in case the DAO passes us an empty trainer object.
            if (train != null && train.correctPassword(password)) {
                for (Pokemon pk : train.getPokemon()) {
                    pk.setData(pokemonService.getPokemonData(pk.getNumber()));
                }
                return train;
            }
        }

        return null;
    }

    public boolean register(Trainer newTrainer) {
        if (trainerRepo.insertTrainer(newTrainer)) {
            mailService.sendRegistration(newTrainer);
            return true;
        }
        return false;
    }

    public boolean deleteTrainer(Trainer trainer) {
        //Probably need to check if the users is an admin before allowing this, but that's going to be a session thing and probably handled in the controller.
        if (trainer != null) {
            trainerRepo.deleteTrainer(trainer.getId());
        }
        return true;
    }
    public boolean addFriend(Trainer pokeTrainer, String fUsername) {
        Trainer friendee = trainerRepo.findTrainerByUsername(fUsername);
        if (friendee != null) {
            return trainerRepo.sendFriendRequest(pokeTrainer, friendee);
        } else return false;
    }


    public List<Trainer> myFriends(Trainer trainer) {
        return trainerRepo.findMyFriends(trainer);
    }

    public List<Trainer> myFriendReq(Trainer trainer){
        return trainerRepo.myFriendRequests(trainer);
    }

    public void processFriendRequest(Trainer friendee, int friendID, String process){
        Trainer friender = trainerRepo.findTrainerById(friendID);
        trainerRepo.processFriendRequest(friender,friendee,process);
    }
}