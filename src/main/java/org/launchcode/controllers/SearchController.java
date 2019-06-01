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
public class SearchController extends TechJobsController {

    @RequestMapping(value = "")
    public String search(Model model) {
        return "search";
    }

    // TODO #1 - Create handler to process search request and display results
    @RequestMapping(value = "results", method = RequestMethod.GET)
    private String displaySearchResults(@RequestParam("searchType") String searchType, @RequestParam("searchTerm")
            String searchTerm, Model model){

        int numResults;

        //if search type is all
        if (searchType.equals("all")){
            if (JobData.findByValue(searchTerm).size() > 0){
                ArrayList<HashMap<String, String>> results = JobData.findByValue(searchTerm);
                HashMap<String, String> job = new HashMap<>(results.get(0));
                ArrayList<String> fields = new ArrayList<>(job.keySet());
                numResults = results.size();

                model.addAttribute("results", results);
                model.addAttribute("fields", fields);
            } else {
                numResults = 0;
                String errorMessage = "No results found. Please try another search.";
                model.addAttribute("errorMessage", errorMessage);
            }
        } else{
            if (JobData.findByColumnAndValue(searchType, searchTerm).size() > 0) {
                ArrayList<HashMap<String, String>> results = JobData.findByColumnAndValue(searchType, searchTerm);
                HashMap<String, String> job = new HashMap<>(results.get(0));
                ArrayList<String> fields = new ArrayList<>(job.keySet());
                numResults = results.size();

                model.addAttribute("results", results);
                model.addAttribute("fields", fields);
            } else {
                numResults = 0;
                String errorMessage = "No results found. Please try another search.";
                model.addAttribute("errorMessage", errorMessage);
            }
        }
        model.addAttribute("title", "Search Results");
        model.addAttribute("searchType", searchType);
        model.addAttribute("searchTerm", searchTerm);
        model.addAttribute("numResults", numResults);

        return "search";
    }
}
