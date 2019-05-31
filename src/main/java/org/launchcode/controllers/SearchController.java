package org.launchcode.controllers;

import org.launchcode.models.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("search")
public class SearchController {

    @RequestMapping(value = "")
    public String search(Model model) {
        model.addAttribute("columns", ListController.columnChoices);
        return "search";
    }

    // TODO #1 - Create handler to process search request and display results
    @RequestMapping(value = "results", method = RequestMethod.POST)
    private String displaySearchResults(@RequestParam String searchType, @RequestParam String searchTerm, Model model){

        ArrayList<HashMap<String, String>> results = JobData.findByColumnAndValue(searchType, searchTerm);
        HashMap<String, String> job = new HashMap<>();
        job = results.get(0);
        ArrayList<String> fields = new ArrayList<>(job.keySet());

        model.addAttribute("results", results);
        model.addAttribute("title", "Search Results");
        model.addAttribute("searchType", searchType);
        model.addAttribute("searchTerm", searchTerm);
        model.addAttribute("fields", fields);

        return "results";
    }
}
