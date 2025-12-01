

import com.studyarc.data_access.DatabaseAccess;
import com.studyarc.entity.ResearchPaper;
import com.studyarc.use_case.search_research_papers.SearchResearchPapersDataAccessInterface.SearchResult;

public class TestCoreAPI {
    public static void main(String[] args) {
        System.out.println("Testing CORE API integration...\n");

        try {
            DatabaseAccess db = DatabaseAccess.getInstance();

            // Search for papers
            SearchResult results = db.searchPapers("artificial intelligence", 5, 0);

            System.out.println("✅ Successfully found " + results.getTotalHits() + " papers!\n");

            System.out.println("Top 5 papers:");
            for (ResearchPaper paper : results.getPapers()) {
                System.out.println("\nTitle: " + paper.getTitle());
                System.out.println("Authors: " + paper.getAuthorsAsString());
                System.out.println("Year: " + paper.getYear());
                System.out.println("DOI: " + paper.getDoi());
            }

            System.out.println("\n✅ CORE API is fully integrated and working!");

        } catch (IllegalStateException e) {
            System.out.println("❌ API not configured: " + e.getMessage());
            System.out.println("\nMake sure you:");
            System.out.println("1. Created a .env file in project root");
            System.out.println("2. Added CORE_API_KEY=your_key to .env");
            System.out.println("3. Got your key from https://core.ac.uk/services/api");

        } catch (Exception e) {
            System.out.println("❌ API call failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}