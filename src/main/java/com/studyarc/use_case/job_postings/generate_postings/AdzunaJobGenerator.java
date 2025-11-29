package com.studyarc.use_case.job_postings.generate_postings;

import com.openai.core.JsonObject;
import com.studyarc.entity.job_postings.JobListing;
import com.studyarc.entity.job_postings.KeywordList;
import com.studyarc.use_case.job_postings.JobPostingsInputData;

import com.studyarc.use_case.job_postings.generate_keywords.KeywordGenerator;
import com.studyarc.use_case.job_postings.generate_keywords.LLMKeywordGenerator;
import io.github.cdimascio.dotenv.Dotenv;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

import java.util.List;

public class AdzunaJobGenerator implements JobRepository {

    private static final Dotenv DOTENV = Dotenv.load();
    private static final String API_KEY = DOTENV.get("ADZUNA_API_KEY");
    private static final String API_ID = DOTENV.get("ADZUNA_ID");
    private String focus;
    private String sort;
    private String countryCode;
    private int salaryMin;

    private static final OkHttpClient client = new OkHttpClient();

    @Override
    public List<JobListing> getJobListings(String focus, String countryCode, KeywordList keywords, String sort, String salaryMin) throws JobRepositoryException{
        // default arguments
        this.sort =  "date";
        this.countryCode = "ca";
        this.salaryMin = 40000;
        this.focus = URLEncoder.encode(focus, StandardCharsets.UTF_8); // format the focus to call the job listings api

        // strip the format of the salary selection
        if (!salaryMin.isEmpty() && !salaryMin.equals("Select Option")) {
            this.salaryMin = Integer.parseInt(salaryMin.replace("$", "").replace(",", ""));
        }

        // set the preferred country location if selected
        if (!countryCode.isEmpty() && !countryCode.equals("Select Country")) this.countryCode = countryCode;
        // set the preferred sort if selected
        if (!sort.isEmpty() && !sort.equals("Select Sort")) this.sort = sort;

        String url = "https://api.adzuna.com/v1/api/jobs/" + this.countryCode +"/search/1?app_id=" + API_ID + "&app_key=" + API_KEY + "&results_per_page=20&what_or=";
        String jobKeywords = keywords.getKeywords();

        // adds the keywords
        url += jobKeywords +"&title_only=" + this.focus + "&sort_by=" + this.sort + "&salary_min=" + this.salaryMin; // current issue, sort isnt working on the api? might need to sort myself?

        System.out.println(url);

        final Request request = new Request.Builder().url(url).build();

        try {
            final Response response = client.newCall(request).execute();
            String body = response.body().string();
            final JSONObject responseBody = new JSONObject(body);

            System.out.println("Getting jobs from api...");

            try {
                if (responseBody.get("results") != null) {

                    // gets the list of job listings from the api
                    JSONArray jobResults = responseBody.getJSONArray("results");

                    // creates the list that will be returned containing the JobListing entities
                    List<JobListing> listings = new ArrayList<>();

                    for (int i = 0; i < jobResults.length(); i++) {
                        JSONObject job = jobResults.getJSONObject(i);
                        JSONObject jobCompany = job.getJSONObject("company");
                        JSONObject jobLocation = job.getJSONObject("location");

                        JobListing newJob = new JobListing(job.get("title").toString(), Long.parseLong(job.get("id").toString()), jobCompany.get("display_name").toString(), Double.parseDouble(job.get("salary_min").toString()), Double.parseDouble(job.get("salary_max").toString()), job.get("description").toString(), jobLocation.get("display_name").toString(), job.get("redirect_url").toString());
                        listings.add(newJob);
                    }

                    System.out.println("Finished with api.");

                    return listings;
                }

            } catch (Exception e) {
                throw new JobRepositoryException(e.getMessage());
            }

        } catch (IOException e) {
            throw new JobRepositoryException(e.getMessage());
        }
        return List.of();
    }

    public int numberResults(List<JobListing> listings) {
        return listings.size();
    }

}
