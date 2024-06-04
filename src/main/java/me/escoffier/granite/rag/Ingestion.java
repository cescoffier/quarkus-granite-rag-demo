package me.escoffier.granite.rag;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import io.quarkus.logging.Log;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Singleton;

import java.io.File;
import java.util.List;

import static dev.langchain4j.data.document.splitter.DocumentSplitters.recursive;

@Singleton
@Startup
public class Ingestion {

    public Ingestion(RedisDataSource redis, EmbeddingStore<TextSegment> store, EmbeddingModel embedding) {
        redis.flushall(); // Demo purposes only

        EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
                .embeddingStore(store)
                .embeddingModel(embedding)
                .documentSplitter(recursive(500, 50))
                .build();

        File file = new File("documents");
        List<Document> documents = FileSystemDocumentLoader.loadDocuments(file.toPath());
        Log.info("Ingesting " + documents.size() + " documents");

        ingestor.ingest(documents);

        Log.info("Document ingested");
    }

}
