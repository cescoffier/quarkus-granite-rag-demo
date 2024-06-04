# Quarkus LLM Demo - Granite RAG

This is a demo of a Retrieval Augmented Generation (RAG) pattern using Quarkus and Langchain4J.
It uses the Granite LLM model to generate text based on a given prompt.

## Prerequisites

- Java 21 or later
- Apache Maven 3.9.6 or later
- Podman or Docker (Podman recommended)
- Granite available using Podman Desktop (AI studio) or InstructLab

*IMPORTANT:* Update the LLM URL in the `application.properties` to match your Granite LLM URL.

## Running the Demo

To run the demo, navigate to the `4-rag` directory and run `mvn quarkus:dev`.

```
> mvn quarkus:dev
```

The UI is available at `http://localhost:8080`.