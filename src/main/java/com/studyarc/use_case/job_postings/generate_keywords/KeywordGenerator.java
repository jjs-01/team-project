package com.studyarc.use_case.job_postings.generate_keywords;

import com.studyarc.entity.job_postings.KeywordList;

public interface KeywordGenerator {

    /**
     * Returns the keywords for user's selected focus
     * @return
     */
    KeywordList generate(String focus) throws KeywordGeneratorException;

    class KeywordGeneratorException extends Exception {
        public KeywordGeneratorException(String message) {
            super("Error with Cohere," + message);
        }
    }
}
