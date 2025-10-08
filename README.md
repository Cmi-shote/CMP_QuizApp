# 🌍 Quiz App (Kotlin Multiplatform Project)

A cross-platform **Quiz App** built with **Kotlin Multiplatform (KMP)** — supporting **Android** and **iOS** from a single shared codebase.  
Originally built with XML layouts on Android([Github](https://github.com/Cmi-shote/Quiz_App)), this new version leverages **Compose Multiplatform** to deliver a modern, scalable, and unified experience.

---

## 📖 Overview

The Quiz App allows users to test their knowledge on world flags, featuring:

- Dynamic quiz questions fetched from a public REST API  
- Real-time feedback on answers  
- Clean, intuitive UI built with **Compose Multiplatform**  
- Shared business logic and networking layer powered by **Ktor**

---

## 🧱 Tech Stack

### Shared Module
- **Kotlin Multiplatform** – Shared logic between Android and iOS  
- **Ktor** – HTTP client for API requests  
- **Koin** – Dependency Injection across modules  
- **Coroutines** – Asynchronous programming  
- **Kotlinx Serialization** – JSON parsing  
- **Compose Multiplatform** – Declarative UI for Android & iOS  
- **Coil 3** – Image loading  
- **Voyager** – Navigation for Compose Multiplatform  

---

## Setup & Run

### 1️⃣ Clone the Repository
```bash
git clone https://github.com/<your-username>/quizapp.git
cd quizapp
```

### 2️⃣ Run on Android
Open the project in Android Studio
Select an emulator or a connected device
Click Run
### 3️⃣ Run on iOS
You have two options:
 - Option 1 – Using Xcode
Open the iosApp folder in Xcode
Select your target simulator or device
Click Run
 - Option 2 – Using Android Studio / IntelliJ IDEA
Install the Kotlin Multiplatform Mobile (KMM) plugin
Run the iOS app directly on the iOS Simulator from within the IDE
Select the iOS configuration and click Run

## API
This app consumes data from the REST Countries API using Ktor for network requests.
Endpoint used:
https://restcountries.com/v3.1/all?fields=name,flags
The response provides each country’s name and flag.
A helper function randomly selects 10 countries and generates single-choice options for questions like:
“Which country does this flag belong to?”


## Key Features
Cross-platform code sharing (Android + iOS)
Modern declarative UI with Compose
Clean architecture with DI (Koin)
Image loading with Coil 3
Smooth builds for both iOS and Android
