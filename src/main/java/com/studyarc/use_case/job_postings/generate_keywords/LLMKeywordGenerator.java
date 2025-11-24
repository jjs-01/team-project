package com.studyarc.use_case.job_postings.generate_keywords;

import io.github.cdimascio.dotenv.Dotenv;

import com.studyarc.entity.job_postings.KeywordList;

import com.cohere.api.Cohere;
import com.cohere.api.requests.ChatRequest;
import com.cohere.api.types.ChatMessage;
import com.cohere.api.types.Message;
import com.cohere.api.types.NonStreamedChatResponse;
import org.json.JSONObject;

import java.util.List;

/***
 * Generates the keywords via an LLM prompt
 */
public class LLMKeywordGenerator implements KeywordGenerator {
    private static final Dotenv DOTENV = Dotenv.load();
    private static final String API_KEY = DOTENV.get("COHERE_API_KEY");

    @Override
    public KeywordList generate(String focus) throws KeywordGeneratorException {
        Cohere cohere = Cohere.builder().token(API_KEY).clientName("snippet").build();

        try {
            System.out.println("Promting Cohere...");
            // call the llm to generate the keywords
            NonStreamedChatResponse response = cohere.chat(
                    ChatRequest.builder()
                            .message("Given this computer science focus: " + focus + ". Generate a list of keywords that may be used to find jobs in relation to the focus. Return me a string of all of the keywords and instead of spaces, input %20 for ALL SPACES. Multiple terms may be space separated. No commas. Do not write me a confirmation message understanding my prompt, just return the answer.")
                            .build());

            final JSONObject responseBody = new JSONObject(response);

            // creates the KeywordList entity
            KeywordList keywordList = new KeywordList(responseBody.get("text").toString());

            System.out.println(responseBody.get("text"));
            System.out.println(responseBody);

            System.out.println("Cohere finished.");

            return keywordList;

        }  catch (Exception e) {
            throw new KeywordGeneratorException(e.getMessage());
        }

    }

    public static void main(String[] args) {
        Cohere cohere = Cohere.builder().token(API_KEY).clientName("snippet").build();

        try {
            System.out.println("Promting Cohere...");
            // call the llm to generate the keywords
            NonStreamedChatResponse response = cohere.chat(
                    ChatRequest.builder()
                            .message("Given this computer science forcus: Machine Learning Generate a list of keywords that may be used to find jobs in relation to the focus. Return me a string of all of the keywords and instead of spaces, input %20 for ALL SPACES. Multiple terms may be space separated. No commas. Do not write me a confirmation message understanding my prompt, just return the answer.")
                            .build());

            final JSONObject responseBody = new JSONObject(response);

            // creates the KeywordList entity
            KeywordList keywordList = new KeywordList(responseBody.get("text").toString());

            System.out.println(responseBody.get("text"));
            System.out.println(responseBody);
        }  catch (Exception e) {
            System.out.println("An error occured with Cohere");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
