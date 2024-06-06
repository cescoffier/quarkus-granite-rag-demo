package me.escoffier.granite.rag;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import io.quarkus.logging.Log;
import io.quarkus.runtime.Startup;
import jakarta.inject.Singleton;

import java.nio.file.Path;
import java.util.List;

import static dev.langchain4j.data.document.splitter.DocumentSplitters.recursive;

@Singleton
@Startup
public class Ingestion {

    public Ingestion(EmbeddingStore<TextSegment> store, EmbeddingModel embedding) {

        EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
                .embeddingStore(store)
                .embeddingModel(embedding)
                .documentSplitter(recursive(1024, 0))
                .build();

        Path dir = Path.of("documents");
        List<Document> documents = FileSystemDocumentLoader.loadDocuments(dir);
        Log.info("Ingesting " + documents.size() + " documents");

        ingestor.ingest(documents);

        Log.info("Document ingested");
    }

}
