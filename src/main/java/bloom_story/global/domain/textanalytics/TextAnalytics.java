package bloom_story.global.domain.textanalytics;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.azure.ai.textanalytics.TextAnalyticsClient;
import com.azure.ai.textanalytics.TextAnalyticsClientBuilder;
import com.azure.ai.textanalytics.models.AnalyzeSentimentOptions;
import com.azure.ai.textanalytics.models.AssessmentSentiment;
import com.azure.ai.textanalytics.models.DocumentSentiment;
import com.azure.ai.textanalytics.models.SentimentConfidenceScores;
import com.azure.ai.textanalytics.models.TargetSentiment;
import com.azure.core.credential.AzureKeyCredential;

@Configuration
public class TextAnalytics {

    @Value("${azure.key}")
    private String apiKey;

    @Value("${azure.endpoint}")
    private String endPoint;

    public String analyzeTextEmotion(String content) {
        TextAnalyticsClient client = authenticateClient(apiKey, endPoint);
        return sentimentAnalysisWithOpinionMiningExample(client, content);
    }

    private TextAnalyticsClient authenticateClient(String key, String endpoint) {
        return new TextAnalyticsClientBuilder()
            .credential(new AzureKeyCredential(key))
            .endpoint(endpoint)
            .buildClient();
    }

    // Example method for detecting sentiment and opinions in text.
    public String sentimentAnalysisWithOpinionMiningExample(TextAnalyticsClient client, String content) {
        // 분석할 문서

        System.out.printf("분석할 문서: %s%n", content);

        AnalyzeSentimentOptions options = new AnalyzeSentimentOptions().setIncludeOpinionMining(true);
        final DocumentSentiment documentSentiment = client.analyzeSentiment(content, "en", options);
        SentimentConfidenceScores scores = documentSentiment.getConfidenceScores();
        System.out.printf(
            "문서의 전체 감정 분석 결과: %s, 긍정 점수: %f, 중립 점수: %f, 부정 점수: %f.%n",
            documentSentiment.getSentiment(), scores.getPositive(), scores.getNeutral(), scores.getNegative());

        documentSentiment.getSentences().forEach(sentenceSentiment -> {
            SentimentConfidenceScores sentenceScores = sentenceSentiment.getConfidenceScores();
            System.out.printf("\t문장 감정 분석 결과: %s, 긍정 점수: %f, 중립 점수: %f, 부정 점수: %f.%n",
                sentenceSentiment.getSentiment(), sentenceScores.getPositive(), sentenceScores.getNeutral(),
                sentenceScores.getNegative());

            sentenceSentiment.getOpinions().forEach(opinion -> {
                TargetSentiment targetSentiment = opinion.getTarget();
                System.out.printf("\t\t대상 감정 분석 결과: %s, 대상 텍스트: %s%n",
                    targetSentiment.getSentiment(), targetSentiment.getText());

                for (AssessmentSentiment assessmentSentiment : opinion.getAssessments()) {
                    System.out.printf(
                        "\t\t\t'%s' 평가 감정 (\"%s\" 때문). 평가가 부정적 의미로 사용되었는가: %s.%n",
                        assessmentSentiment.getSentiment(), assessmentSentiment.getText(),
                        assessmentSentiment.isNegated());
                }
            });
        });

        return documentSentiment.getSentiment().toString();
    }
}
