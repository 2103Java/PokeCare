package com.revature.pokecare.service;

import com.revature.pokecare.models.Pokemon;
import com.revature.pokecare.models.Trainer;
import com.revature.pokecare.repository.TrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
                if (PokemonService.pokes.isEmpty()) {
                    pokemonService.fillPokeMap();
                }

                for (Pokemon pk : train.getPokemon()) {
                    pk.setPokeName(PokemonService.pokes.get(pk.getNumber()));
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
}