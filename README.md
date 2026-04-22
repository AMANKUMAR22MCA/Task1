<div align="center">

<!-- Hero Banner -->
<img width="100%" src="https://capsule-render.vercel.app/api?type=waving&color=0:0f0c29,50:302b63,100:24243e&height=200&section=header&text=DocMind%20AI&fontSize=80&fontColor=ffffff&animation=fadeIn&fontAlignY=35&desc=Document%20Q%26A%20System&descAlignY=58&descColor=a78bfa"/>

<br/>

<h3>
  <samp>🧠 Upload. Ask. Understand.</samp>
</h3>

<p>
  <em>Transform any PDF into an intelligent conversational knowledge base — powered by RAG, Groq LLM, and a blazing-fast async pipeline.</em>
</p>

<br/>

<!-- Badges -->
[![FastAPI](https://img.shields.io/badge/FastAPI-009688?style=for-the-badge&logo=fastapi&logoColor=white)](https://fastapi.tiangolo.com/)
[![Python](https://img.shields.io/badge/Python_3.11+-3776AB?style=for-the-badge&logo=python&logoColor=white)](https://python.org)
[![LangChain](https://img.shields.io/badge/LangChain-1C3C3C?style=for-the-badge&logo=langchain&logoColor=white)](https://langchain.com)
[![Groq](https://img.shields.io/badge/Groq_LLM-F55036?style=for-the-badge&logo=groq&logoColor=white)](https://groq.com)
[![Redis](https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=redis&logoColor=white)](https://redis.io)
[![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)](https://docker.com)
[![Celery](https://img.shields.io/badge/Celery-37814A?style=for-the-badge&logo=celery&logoColor=white)](https://docs.celeryq.dev)
[![ChromaDB](https://img.shields.io/badge/ChromaDB-FF6B35?style=for-the-badge&logo=databricks&logoColor=white)](https://www.trychroma.com/)

<br/><br/>

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg?style=flat-square)](LICENSE)
[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg?style=flat-square)](CONTRIBUTING.md)
[![Stars](https://img.shields.io/github/stars/yourusername/docmind-ai?style=flat-square&color=gold)](https://github.com/yourusername/docmind-ai/stargazers)
[![Issues](https://img.shields.io/github/issues/yourusername/docmind-ai?style=flat-square&color=red)](https://github.com/yourusername/docmind-ai/issues)

<br/>

[**🚀 Quick Start**](#-quick-start) · [**📖 API Docs**](#-api-reference) · [**🏗 Architecture**](#-architecture) · [**🤝 Contributing**](#-contributing)

</div>

---

## 📸 Screenshots

<div align="center">

| Chat Interface | Upload & Process | API Documentation |
|:-:|:-:|:-:|
| ![Chat UI](https://via.placeholder.com/280x180/0f0f1a/a78bfa?text=Chat+Interface) | ![Upload](https://via.placeholder.com/280x180/0f0f1a/38bdf8?text=PDF+Upload) | ![API Docs](https://via.placeholder.com/280x180/0f0f1a/34d399?text=Swagger+UI) |
| *Multi-turn dark theme chat* | *Async processing status* | *Auto-generated FastAPI docs* |

</div>

---

## ✨ Features

- 📄 **PDF Intelligence** — Upload any PDF and instantly build a searchable knowledge base
- 🤖 **RAG Pipeline** — Retrieval-Augmented Generation for accurate, context-grounded answers
- ⚡ **Async Processing** — Celery + Redis worker queue handles heavy PDF ingestion non-blocking
- 🧵 **Multi-Turn Conversations** — Maintains conversation history for contextual follow-up questions
- 🗃️ **Multi-PDF Isolation** — Each document lives in its own ChromaDB namespace — no cross-contamination
- ⚡ **Redis Caching** — Blazing fast repeated query responses with intelligent cache invalidation
- 📇 **Contact Extraction** — Automatically extracts names, emails, and phone numbers from documents
- 🛡️ **Rate Limiting** — Built-in API rate limiting to prevent abuse
- 🌗 **Dark Theme UI** — Sleek HTML/JS chat interface built for power users
- 📊 **Task Monitoring** — Real-time Celery task status tracking via REST endpoint
- 🐳 **Fully Dockerized** — One command to spin up the entire stack

---

## 🏗 Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                        DocMind AI System                        │
│                                                                 │
│   ┌──────────────┐     ┌─────────────────────────────────────┐  │
│   │   Browser    │     │           FastAPI Backend           │  │
│   │  Dark Theme  │────▶│                                     │  │
│   │  Chat UI     │◀────│  POST /upload   POST /ask           │  │
│   └──────────────┘     │  GET  /status   GET  /docs          │  │
│                        └────────┬──────────────┬────────────┘  │
│                                 │              │               │
│              ┌──────────────────▼──┐    ┌──────▼────────────┐  │
│              │    Celery Worker    │    │    Redis Cache    │  │
│              │                    │    │                   │  │
│              │  1. Parse PDF       │    │  • Query cache    │  │
│              │  2. Chunk text      │    │  • Session store  │  │
│              │  3. Embed chunks    │    │  • Rate limiting  │  │
│              │  4. Store vectors   │    └───────────────────┘  │
│              └──────────┬──────────┘                           │
│                         │                                      │
│              ┌──────────▼──────────┐    ┌───────────────────┐  │
│              │     ChromaDB        │    │     Groq LLM      │  │
│              │  Vector Store       │───▶│   (Inference)     │  │
│              │                     │    │                   │  │
│              │  • Per-PDF namespaces│   │  • llama-3.1-70b  │  │
│              │  • Semantic search  │    │  • Ultra-low      │  │
│              │  • Top-K retrieval  │    │    latency        │  │
│              └─────────────────────┘    └───────────────────┘  │
└─────────────────────────────────────────────────────────────────┘
```

### 🔄 RAG Pipeline — How It Works

```
 PDF Upload                 Ingestion                    Query Time
─────────────          ─────────────────────          ──────────────────────
                                                        
 ┌─────────┐            ┌──────────────────┐            ┌──────────────────┐
 │  PDF    │──chunk──▶  │  Text Chunks     │  embed ──▶ │  Vector Search   │
 │  File   │            │  (512 tokens)    │            │  Top-K Results   │
 └─────────┘            └──────────────────┘            └────────┬─────────┘
                                                                 │
                        ┌──────────────────┐                     │ context
                        │   ChromaDB       │◀────────────────────┘
                        │   Vector Store   │
                        └──────────────────┘      ┌──────────────────────┐
                                                   │  Prompt Assembly     │
  User Question ──────────────────────────────────▶│                      │
                                                   │  System + History +  │
                                                   │  Context + Question  │
                                                   └──────────┬───────────┘
                                                              │
                                                   ┌──────────▼───────────┐
                                                   │     Groq LLM         │
                                                   │   Generates Answer   │
                                                   └──────────────────────┘
```

> **In plain English:** Your PDF is sliced into overlapping chunks → each chunk is converted to a vector embedding → stored in ChromaDB. When you ask a question, it's also embedded → semantically similar chunks are retrieved → fed to the LLM as context → you get a precise, grounded answer. No hallucinations from thin air.

---

## 🚀 Quick Start

### Prerequisites

- 🐳 Docker & Docker Compose
- 🔑 [Groq API Key](https://console.groq.com) (free tier available)
- 🐍 Python 3.11+ *(for local dev only)*

### ⚡ Run with Docker (Recommended)

```bash
# 1. Clone the repository
git clone https://github.com/yourusername/docmind-ai.git
cd docmind-ai

# 2. Configure environment
cp .env.example .env
# Edit .env and add your GROQ_API_KEY

# 3. Launch the full stack 🚀
docker compose up --build

# Services will be available at:
# 🌐 Chat UI    → http://localhost:8000
# 📚 API Docs   → http://localhost:8000/docs
# 🌸 Flower     → http://localhost:5555  (Celery monitor)
```

### 🛠 Local Development Setup

```bash
# Create virtual environment
python -m venv .venv
source .venv/bin/activate        # Windows: .venv\Scripts\activate

# Install dependencies
pip install -r requirements.txt

# Start Redis (required)
docker run -d -p 6379:6379 redis:alpine

# Start Celery worker
celery -A app.celery_app worker --loglevel=info &

# Launch FastAPI server
uvicorn app.main:app --reload --port 8000
```

---

## 📡 API Reference

### Endpoints

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| `POST` | `/upload` | Upload a PDF for async processing | — |
| `POST` | `/ask` | Ask a question about a document | — |
| `GET` | `/status/{task_id}` | Poll Celery task processing status | — |
| `POST` | `/cache/clear` | Flush Redis response cache | — |
| `GET` | `/docs` | Interactive Swagger UI | — |
| `GET` | `/redoc` | ReDoc API documentation | — |

### 📤 `POST /upload`

```bash
curl -X POST "http://localhost:8000/upload" \
  -H "Content-Type: multipart/form-data" \
  -F "file=@your_document.pdf"
```

```json
{
  "task_id": "3f2e1d0c-b9a8-7654-3210-fedcba987654",
  "status": "processing",
  "message": "PDF uploaded. Processing started asynchronously."
}
```

### 💬 `POST /ask`

```bash
curl -X POST "http://localhost:8000/ask" \
  -H "Content-Type: application/json" \
  -d '{
    "document_id": "your-doc-uuid",
    "question": "What are the key findings in chapter 3?",
    "conversation_history": [
      {"role": "user", "content": "Summarize this document"},
      {"role": "assistant", "content": "This document covers..."}
    ]
  }'
```

```json
{
  "answer": "Chapter 3 highlights three key findings: ...",
  "sources": ["page 12", "page 14"],
  "cached": false,
  "contacts_found": {
    "names": ["Dr. Jane Smith"],
    "emails": ["j.smith@example.com"],
    "phones": ["+1-555-0123"]
  }
}
```

### 🔍 `GET /status/{task_id}`

```json
{
  "task_id": "3f2e1d0c-b9a8-7654-3210-fedcba987654",
  "status": "SUCCESS",
  "result": {
    "document_id": "doc-uuid",
    "chunks_processed": 142,
    "processing_time_ms": 3821
  }
}
```

---

## ⚙️ Environment Variables

Create a `.env` file in the project root:

```env
# ─── LLM Configuration ────────────────────────────────────────────
GROQ_API_KEY=gsk_xxxxxxxxxxxxxxxxxxxxxxxxxxxx
GROQ_MODEL=llama-3.1-70b-versatile

# ─── Redis Configuration ──────────────────────────────────────────
REDIS_URL=redis://localhost:6379/0
CACHE_TTL_SECONDS=3600

# ─── ChromaDB Configuration ───────────────────────────────────────
CHROMA_PERSIST_DIR=./chroma_db
CHROMA_COLLECTION_PREFIX=docmind

# ─── Celery Configuration ─────────────────────────────────────────
CELERY_BROKER_URL=redis://localhost:6379/1
CELERY_RESULT_BACKEND=redis://localhost:6379/2

# ─── App Configuration ────────────────────────────────────────────
MAX_UPLOAD_SIZE_MB=50
CHUNK_SIZE=512
CHUNK_OVERLAP=64
TOP_K_RESULTS=5
RATE_LIMIT_PER_MINUTE=30

# ─── FastAPI Configuration ────────────────────────────────────────
APP_HOST=0.0.0.0
APP_PORT=8000
DEBUG=false
```

| Variable | Default | Description |
|----------|---------|-------------|
| `GROQ_API_KEY` | **required** | Your Groq Cloud API key |
| `GROQ_MODEL` | `llama-3.1-70b-versatile` | Groq model to use for inference |
| `REDIS_URL` | `redis://localhost:6379/0` | Redis connection string |
| `CACHE_TTL_SECONDS` | `3600` | Cache expiration time in seconds |
| `CHROMA_PERSIST_DIR` | `./chroma_db` | ChromaDB persistence directory |
| `MAX_UPLOAD_SIZE_MB` | `50` | Maximum PDF upload size |
| `CHUNK_SIZE` | `512` | Token chunk size for text splitting |
| `CHUNK_OVERLAP` | `64` | Overlap between consecutive chunks |
| `TOP_K_RESULTS` | `5` | Number of context chunks to retrieve |
| `RATE_LIMIT_PER_MINUTE` | `30` | API rate limit per IP per minute |

---

## 📁 Project Structure

```
docmind-ai/
│
├── 📂 app/
│   ├── 📄 main.py                 # FastAPI app entry point & route definitions
│   ├── 📄 celery_app.py           # Celery app configuration & task registry
│   ├── 📄 config.py               # Pydantic settings & env variable loading
│   │
│   ├── 📂 api/
│   │   ├── 📄 upload.py           # PDF upload endpoint & validation
│   │   ├── 📄 ask.py              # Q&A endpoint with history support
│   │   ├── 📄 status.py           # Celery task status polling
│   │   └── 📄 cache.py            # Cache management endpoints
│   │
│   ├── 📂 core/
│   │   ├── 📄 rag_pipeline.py     # RAG orchestration (retrieve → augment → generate)
│   │   ├── 📄 embeddings.py       # Text embedding model wrapper
│   │   ├── 📄 vector_store.py     # ChromaDB client & collection management
│   │   ├── 📄 llm_client.py       # Groq LLM client with retry logic
│   │   └── 📄 extractor.py        # Contact info extraction (name/email/phone)
│   │
│   ├── 📂 services/
│   │   ├── 📄 pdf_processor.py    # PDF parsing, chunking, ingestion service
│   │   ├── 📄 cache_service.py    # Redis caching layer abstraction
│   │   └── 📄 rate_limiter.py     # Sliding window rate limiting
│   │
│   └── 📂 tasks/
│       └── 📄 process_pdf.py      # Celery async task for PDF ingestion
│
├── 📂 frontend/
│   ├── 📄 index.html              # Dark theme chat interface
│   ├── 📄 style.css               # Custom CSS + dark theme variables
│   └── 📄 app.js                  # Fetch API calls, chat rendering, state
│
├── 📂 tests/
│   ├── 📄 test_upload.py
│   ├── 📄 test_ask.py
│   └── 📄 test_rag_pipeline.py
│
├── 📄 docker-compose.yml          # Full stack orchestration
├── 📄 Dockerfile                  # FastAPI app container
├── 📄 Dockerfile.worker           # Celery worker container
├── 📄 requirements.txt
├── 📄 .env.example
└── 📄 README.md
```

---

## 🛠 Tech Stack

| Layer | Technology | Purpose |
|-------|-----------|---------|
| **API Framework** | [FastAPI](https://fastapi.tiangolo.com/) | High-performance async REST API |
| **LLM Orchestration** | [LangChain](https://langchain.com/) | RAG chain, text splitters, prompt management |
| **LLM Inference** | [Groq](https://groq.com/) | Ultra-fast LLM inference (LPU hardware) |
| **Vector Database** | [ChromaDB](https://www.trychroma.com/) | Local vector store with persistence |
| **Task Queue** | [Celery](https://docs.celeryq.dev/) | Async PDF processing workers |
| **Cache / Broker** | [Redis](https://redis.io/) | Response cache, Celery broker & backend |
| **PDF Parsing** | PyMuPDF / pdfplumber | Robust text + metadata extraction |
| **Containerization** | [Docker](https://docker.com/) | Reproducible full-stack deployment |
| **Frontend** | Vanilla HTML/JS | Zero-dependency dark chat UI |

---

## 🔬 How RAG Works (Simple Explanation)

> Think of RAG as giving the AI a textbook to look things up in — rather than relying only on what it memorized during training.

**Step 1 — 📄 Ingest**
Your PDF is parsed and split into small, overlapping text chunks (~512 tokens each). Each chunk is converted into a *vector embedding* — a list of numbers that captures the chunk's semantic meaning.

**Step 2 — 🗄️ Store**
All embeddings are stored in ChromaDB, indexed under your document's unique ID. This is your searchable knowledge base.

**Step 3 — 🔍 Retrieve**
When you ask a question, it's also converted to an embedding. ChromaDB finds the `TOP_K` chunks with the most similar embeddings — these are the most *semantically relevant* passages.

**Step 4 — 🤖 Generate**
The retrieved chunks + your conversation history + your question are assembled into a structured prompt. Groq's LLM reads all of this and generates a precise, grounded answer.

**Why this matters:** The AI only answers based on *your document's actual content*, not hallucinated knowledge. If the answer isn't in the PDF, it tells you so.

---

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

```bash
# Fork the repo, then:
git checkout -b feature/your-amazing-feature
git commit -m "feat: add your amazing feature"
git push origin feature/your-amazing-feature
# Open a Pull Request 🎉
```

Please read [CONTRIBUTING.md](CONTRIBUTING.md) for our code of conduct and submission guidelines.

---

## 🗺 Roadmap

- [ ] 🌐 Multi-language document support
- [ ] 📊 Document comparison mode (ask across multiple PDFs)
- [ ] 🔐 JWT authentication & user sessions
- [ ] 📈 Analytics dashboard (query volume, cache hit rate)
- [ ] 🧩 LangSmith tracing integration
- [ ] 🌍 Deployable to AWS / GCP / Railway with one click

---

## 📄 License

Distributed under the **MIT License**. See [`LICENSE`](LICENSE) for full details.

---

<div align="center">

### Built with ❤️ by [Your Name](https://github.com/yourusername)

<a href="https://github.com/yourusername">
  <img src="https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white" />
</a>
<a href="https://linkedin.com/in/yourusername">
  <img src="https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white" />
</a>
<a href="mailto:you@example.com">
  <img src="https://img.shields.io/badge/Email-D14836?style=for-the-badge&logo=gmail&logoColor=white" />
</a>

<br/><br/>

*If DocMind AI saved you time, consider giving it a ⭐ — it helps a lot!*

<img width="100%" src="https://capsule-render.vercel.app/api?type=waving&color=0:24243e,50:302b63,100:0f0c29&height=120&section=footer"/>

</div>
