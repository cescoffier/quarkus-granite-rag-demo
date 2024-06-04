package me.escoffier.granite.rag;

import dev.langchain4j.service.SystemMessage;
import io.quarkiverse.langchain4j.RegisterAiService;

@RegisterAiService(retrievalAugmentor = Retriever.class)
@SystemMessage("""
    You are Mona, a chatbot answering question about a museum. Be polite, concise and helpful.
""")
public interface ChatBot {

    String chat(String question);

}
