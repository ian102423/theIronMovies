package com.ironmovies.theIronMovies.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


    @RestController
    public class MainController {

        static final String API_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=be2a38521a7859c95e2d73c48786e4bb";
        private ObjectMapper objectMapper = new ObjectMapper();


        @RequestMapping(path = "/medium-popular-long-name", method = RequestMethod.GET)
        public String medPop(Model model){
            model.addAttribute("movies",getMovies(API_URL)
                    .stream()
                    .filter(e->e.getTitle().length()>9)
                    .filter(e->e.getPopularity()>29 && e.getPopularity()<81)
                    .collect(Collectors.toList()));
            return "medium-popular-long-name";
        }
        @RequestMapping(path = "/now-playing", method = RequestMethod.GET)
        public String nowPlaying (Model model){
            model.addAttribute("movies",getMovies(API_URL));
            return "now-playing";
        }

        public List<Movie> getMovies(String route){
            RestTemplate restTemplate = new RestTemplate();
            ResultsPage results  = restTemplate.getForObject(route, ResultsPage.class);


            return results.getResults();
        }

}
