package vip.corejava.app.sample;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.image.CreateImageRequest;
import com.theokanning.openai.image.Image;
import com.theokanning.openai.image.ImageResult;
import com.theokanning.openai.service.OpenAiService;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;

public class OpenaiTest {

    OpenAiService service = new OpenAiService("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", Duration.ofSeconds(30));

    @Test
    void t1() {

        CompletionRequest completionRequest = CompletionRequest.builder()
                .prompt("什么是红黑树")
                .model("text-davinci-003")
                .temperature(0.6)
                .maxTokens(1000)
                .echo(true)
                .user("mock")
                .n(5)
                .build();
        service.createCompletion(completionRequest).getChoices().forEach(System.out::println);
    }


    @Test
    void t2() {

        CreateImageRequest request = CreateImageRequest.builder()
                .prompt("A cow breakdancing with a turtle")
                .build();

        ImageResult image = service.createImage(request);

        List<Image> data = image.getData();
        data.forEach(i -> {
            System.out.println(i.getUrl());
        });

    }
}
