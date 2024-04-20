package ibf.paf3.day23workshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ibf.paf3.day23workshop.model.Game;
import ibf.paf3.day23workshop.repository.GameRepository;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    public List<Game> findGamebyName (String name){
        return gameRepository.findGamebyName(name);
    }
}
