package com.codeup;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class IndexWalkthroughWorkbenchController {
    @GetMapping("")
    public String landingPage(){

//  TODO: Implement fetching of most recent puzzle(s?) to feature somewhere as most recently submitted
        return "index";
    }

    @GetMapping("/walkthrough")
    public String walkthroughIndex(){

//  TODO: Implement fetch of names and schemes of all walkthrough pages, order by difficulty, and then send through links and names into model
        return "walkthrough";
    }

    @GetMapping("/walkthrough/{scheme}")
    public String walkthroughPage(@PathVariable String scheme){

//  TODO: Get the walkthrough entry with the passed scheme and add it to the model
        return "individualWalkthrough";
    }

    @GetMapping("/challenge")
    public String challenge(){
        return "challenge";
    }
}

