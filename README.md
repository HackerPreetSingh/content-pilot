# ContentPilot 🚀

An automated LinkedIn content generation system built using Java Spring Boot and LLM integration.

## 🔥 Overview

ContentPilot generates high-quality, engaging LinkedIn posts automatically using a structured rotation engine and AI models.

Instead of random or repetitive content, it ensures:
- Smart topic rotation
- Variation in content format
- Diverse engagement angles
- Consistent, non-repetitive output

---

## ⚙️ How It Works

The system combines three core dimensions:

- Topics → (Java, Spring Boot, Kafka, System Design, etc.)
- Formats → (Deep Dive, Quick Tip, Comparison, etc.)
- Angles → (Hook styles like “Most developers do this wrong”)

A deterministic rotation engine ensures:
- No repetition
- Balanced coverage
- Predictable sequencing

Each request:
1. Reads current indexes
2. Selects topic + format + angle
3. Generates prompt
4. Calls AI model
5. Updates indexes

---

## 🧠 Key Features

- Deterministic content rotation (no randomness)
- Multi-model fallback support (Gemini models)
- Externalized prompt template
- Lightweight state persistence (/tmp JSON)
- Plug-and-play REST API
- Fully deployable using Docker

---

## 🏗️ Tech Stack

- Java 21
- Spring Boot
- Spring AI (Gemini integration)
- Gradle
- Docker
- Render (deployment)

---

## 🚀 API Usage

Trigger content generation:

GET /generate

Response:
- Returns a ready-to-post LinkedIn content string

---

## 🐳 Deployment

The project is containerized using Docker.

### Build & Run (Local)

./gradlew clean build -x test  
java -jar build/libs/ContentPilot-0.0.1-SNAPSHOT.jar

### Docker

docker build -t content-pilot .  
docker run -p 8080:8080 content-pilot

---

## 🔐 Environment Variables

GEMINI_API_KEY=your_api_key_here

---

## ⚠️ Notes

- /tmp/counter.json is used for lightweight state persistence
- Data resets on container restart (acceptable for current use case)
- Designed for automation pipelines (cron / GitHub Actions / manual trigger)

---

## 🎯 Use Case

- Automate LinkedIn posting workflow
- Maintain consistent personal branding
- Improve visibility to recruiters
- Practice technical articulation for interviews

---

## 🚀 Future Improvements

- Persistent DB storage (replace /tmp)
- Content scheduling system
- Multi-platform posting (Twitter, Medium)
- UI dashboard
- Analytics tracking

---

## 👨‍💻 Author

Built as a backend automation project combining:
- System design
- Backend engineering
- AI integration

---

## ⭐ Final Note

This is not just a content generator.

It’s a structured system that combines deterministic logic with AI to produce consistent, high-quality output — making it both a practical tool and a strong engineering project.