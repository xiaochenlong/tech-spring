package vip.corejava.app.sample;

import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;

/**
 * @author xcl
 * @date 2023/2/25
 */


//@SpringBootTest
@Slf4j
public class OpenAiTest {

    @Test
    void t1() {
        OpenAiService service = new OpenAiService("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",
                Duration.ofSeconds(60));
        CompletionRequest completionRequest = CompletionRequest.builder()
                .prompt("每天一个知识点")
                .model("text-davinci-003")
                .temperature(0d)
                .maxTokens(3000)
                .build();

        List<CompletionChoice> choices = service.createCompletion(completionRequest)
                .getChoices();

        for (CompletionChoice choice : choices) {
            log.info("choice:{}", choice.getText());
        }
    }


}
