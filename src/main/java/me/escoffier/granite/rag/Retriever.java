package me.escoffier.granite.rag;

import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.rag.DefaultRetrievalAugmentor;
import dev.langchain4j.rag.RetrievalAugmentor;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import io.quarkiverse.langchain4j.redis.RedisEmbeddingStore;
import jakarta.inject.Singleton;

import java.util.function.Supplier;

@Singleton
public class Retriever implements Supplier<RetrievalAugmentor> {

    private final DefaultRetrievalAugmentor augmentor;

    Retriever(EmbeddingStore store, EmbeddingModel model) {
        EmbeddingStoreContentRetriever contentRetriever = EmbeddingStoreContentRetriever.builder()
                .embeddingModel(model)
                .embeddingStore(store)
                .maxResults(3)
                .build();
        augmentor = DefaultRetrievalAugmentor
                .builder()
                .contentRetriever(contentRetriever)
                .build();
    }

    @Override
    public RetrievalAugmentor get() {
        return augmentor;
    }

}
