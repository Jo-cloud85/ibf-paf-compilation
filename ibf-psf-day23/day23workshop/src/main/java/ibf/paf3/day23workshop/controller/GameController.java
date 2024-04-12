package ibf.paf3.day23workshop.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import ibf.paf3.day23workshop.model.Game;
import ibf.paf3.day23workshop.service.GameService;

@RestController
@RequestMapping
public class GameController {

    @Autowired
    private GameService gameService;
    
    @GetMapping(value="/search")
    public ModelAndView getRSVPByName(@RequestParam("search") String q){

        ModelAndView mav = new ModelAndView("listing");
        List<Game> gameList = new LinkedList<Game>();
        gameList = gameService.findGamebyName(q);
        mav.addObject("gameList", gameList);

        return mav;
    }
}
