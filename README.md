# 🌱 AI Crop Disease Scout

**An Android-Based Crop Health Assessment and Agricultural Assistance Application**

AI Crop Disease Scout is an Android application project designed to help farmers explore crop disease information, assess crop health through image analysis, and access useful agricultural support tools.

The project includes Android application screens, crop-related data models, local database components, analyzer-related modules, and a web interface.

> **Project Status:** Under Development  
> Build compatibility, AI integration, and individual feature functionality must be verified before production use.

---

## 📑 Table of Contents

- [About the Project](#-about-the-project)
- [Project Objectives](#-project-objectives)
- [Features](#-features)
- [Technologies Used](#-technologies-used)
- [How the App Works](#-how-the-app-works)
- [How to Use the App](#-how-to-use-the-app)
- [Project Workflow](#-project-workflow)
- [System Requirements](#-system-requirements)
- [Download and Installation](#-download-and-installation)
- [Run Using Command Prompt](#-run-using-command-prompt)
- [Generate an APK](#-generate-an-apk)
- [Web Interface](#-web-interface)
- [Project Structure](#-project-structure)
- [Project Roadmap](#-project-roadmap)
- [Testing and Troubleshooting](#-testing-and-troubleshooting)
- [Security and Privacy](#-security-and-privacy)
- [Contributing](#-contributing)
- [Author](#-author)
- [License](#-license)

---

## 📌 About the Project

Crop diseases can reduce agricultural productivity and affect crop quality. Identifying symptoms early can help farmers investigate crop health problems and seek appropriate advice.

AI Crop Disease Scout aims to provide a mobile platform for:

- Exploring crop disease information.
- Submitting crop images to an analysis module.
- Viewing available crop health assessments.
- Accessing agricultural knowledge and support tools.
- Organizing crop scan records when local storage is implemented.
- Exploring additional crop care features.

The application is intended to support farmers, agriculture students, and people interested in crop health.

**Important:** An analyzer component does not automatically mean that a trained AI model is available or that predictions are accurate. The actual image-analysis implementation must be configured and tested.

## 🎯 Project Objectives

1. Make crop health information easier to access.
2. Provide a convenient interface for submitting crop images.
3. Support the development of image-based crop disease assessment.
4. Organize agricultural information in a mobile application.
5. Provide tools for crop care calculations and recovery tracking.
6. Build a foundation for future AI-powered agricultural assistance.

---

## ✨ Features

### 📸 1. Crop Scanner

A dedicated interface for selecting or capturing crop and leaf images.

Planned capabilities:
- Select images from the gallery.
- Capture images using the camera.
- Check image quality.
- Submit images for analysis.

Camera capture, gallery selection, and image validation must be verified in the running application.

### 🤖 2. Crop Disease Analysis

An analyzer module intended to assess submitted crop images.

Planned capabilities:
- Process crop images.
- Identify possible disease symptoms using a configured model or API.
- Display available assessment results.
- Provide relevant crop health information.

A working model or API, appropriate configuration, and accuracy testing are required for genuine AI-based predictions.

### 📚 3. Disease Knowledge Base

A place for crop and disease information.

Potential information includes:
- Crop names.
- Disease names.
- Visible symptoms.
- Prevention guidance.
- General crop care recommendations.

The available content depends on the implemented knowledge base.

### 🌦️ 4. Outbreak Alerts

An interface intended to display crop disease outbreak information.

Current and location-specific alerts require a reliable, configured data source.

### 🧪 5. Safe Spray Calculator

A tool for spray-related calculations.

The calculator's supported inputs, formulas, and outputs should be checked before practical use. Always follow the pesticide product label and qualified agricultural guidance.

### 📈 6. Recovery Tracker

A feature intended to help users organize crop recovery information over time.

Its tracking and data-persistence capabilities must be tested.

### 🎙️ 7. Voice Assistant

Voice-related agricultural assistance through the project's voice modules.

Microphone access, speech recognition, responses, and any external service integrations must be configured and tested.

### 🗂️ 8. Scan History and Local Database

The project contains Room-related database components.

These are intended to support structured local data storage, including scan records. Actual saving, retrieval, and deletion behavior must be verified.

### 🖨️ 9. Printable Reports

A report dialog component is included in the source files.

The final report generation, print functionality, and supported output formats need testing.

### 🛡️ 10. Admin Review

An interface for reviewing information.

Authentication, authorization, and actual administrative operations must be implemented and tested before using this feature for real users.

### 👤 11. Dashboard and Profile

Screens for accessing application features and user-related information.

### 🌐 12. Web Interface

The repository also contains a separate web interface built with HTML, CSS, and JavaScript.

---

## 🛠️ Technologies Used

The following technologies are identified from the available project files. Actual dependencies should be confirmed against the Gradle configuration.

| Technology | Purpose |
|---|---|
| Kotlin | Android application programming language |
| Android SDK | Android development platform |
| Jetpack Compose | Android user interface components |
| Gradle Kotlin DSL | Build configuration and dependency management |
| Room | Android local database abstraction |
| SQLite | Database engine underlying Room |
| Android XML Resources | App resources, themes, strings, and icons |
| HTML | Structure of the web interface |
| CSS | Web interface styling |
| JavaScript | Web interface behavior |
| Git | Source code version control |
| GitHub | Repository hosting and collaboration |
| Android Studio | Recommended development environment |

**Note:** The precise versions of Kotlin, Gradle, Android SDK, and third-party libraries depend on the project's configuration files.

---

## ⚙️ How the App Works

The intended user journey follows these steps:

1. **Open the app:** The user enters the application and accesses the dashboard.
2. **Select a crop image:** The user opens the Crop Scanner and selects or captures an image.
3. **Validate the image:** If image-quality checking is implemented, the app checks whether the image is suitable.
4. **Analyze the image:** The selected image is sent to the configured analyzer.
5. **Display the result:** The Result screen presents the assessment returned by the analysis module.
6. **Explore guidance:** The user can browse relevant disease information and crop care resources.
7. **Use additional tools:** The user can explore the calculator, alerts, voice features, recovery tracking, and reports.

The actual result depends on the implementation of the analyzer, any required AI model or API, and the quality of the submitted image.

---

## 📱 How to Use the App

### 🏠 Step 1: Open the Application

1. Install and launch the app on a compatible Android device or emulator.
2. Open the Home Screen or Dashboard.
3. Review the available features.

### 📸 Step 2: Scan a Crop

1. Open **Crop Scanner**.
2. Select an image from the gallery or capture one using the camera, if supported.
3. Choose a clear image with the affected leaf area visible.
4. Follow the instructions displayed on the screen.

### 🔍 Step 3: Analyze the Image

1. Submit the image to the configured analysis module.
2. Wait for the analysis process to finish.
3. Check whether the application displays a result or an error.

A valid model or API must be connected for AI-powered analysis.

### 📊 Step 4: Review Results

1. Open the Result screen.
2. Read the disease assessment returned by the analyzer.
3. Review any available crop care guidance.
4. If analysis fails, check the displayed error and try a suitable image.

Do not treat an unverified prediction as a confirmed diagnosis.

### 📚 Step 5: Explore the Knowledge Base

1. Open **Knowledge Base**.
2. Browse available crop and disease information.
3. Read the listed symptoms and guidance.

### 🌦️ Step 6: Check Outbreak Alerts

1. Open **Outbreak Alerts**.
2. Review any available information.
3. Confirm the source and date of alerts before acting on them.

### 🧪 Step 7: Use the Spray Calculator

1. Open **Spray Calculator**.
2. Enter the values requested by the interface.
3. Review the calculation.
4. Confirm the result against the product label and appropriate agricultural guidance.

### 🎙️ Step 8: Use Voice Assistance

1. Open **Voice Agent**.
2. Follow the instructions on the screen.
3. Grant microphone permission if required.
4. Configure any required voice service if prompted.

### 📈 Step 9: Track Crop Recovery

1. Open the recovery tracking feature if available.
2. Add or review supported crop recovery information.
3. Check whether the information persists after restarting the application.

### 🗂️ Step 10: Review Scan History and Reports

1. Open the relevant history or dashboard screen.
2. Review previously saved scans, if supported.
3. Open the report dialog.
4. Test the available report or print options.

### 👤 Step 11: Explore Other Screens

Explore the Profile, Dashboard, Admin Review, and other available screens. Some features may be incomplete or may require additional configuration.

---

## 🔄 Project Workflow

The following diagram illustrates the intended crop assessment process:

```text
          ┌──────────────────┐
          │    Open App      │
          └────────┬─────────┘
                   │
                   ▼
          ┌──────────────────┐
          │ Dashboard / Home │
          └────────┬─────────┘
                   │
                   ▼
          ┌──────────────────┐
          │   Crop Scanner   │
          └────────┬─────────┘
                   │
                   ▼
          ┌──────────────────┐
          │ Select Leaf Image│
          └────────┬─────────┘
                   │
                   ▼
          ┌──────────────────┐
          │ Configured AI    │
          │ Analysis Module  │
          └────────┬─────────┘
                   │
                   ▼
          ┌──────────────────┐
          │  Result Screen   │
          └────────┬─────────┘
                   │
                   ▼
          ┌──────────────────┐
          │ Knowledge Base & │
          │ Farmer Tools     │
          └──────────────────┘
```

This diagram describes the intended workflow, not a guarantee that every connection is currently functional.

---

## 💻 System Requirements

Before running the Android application, prepare:

- A Windows, macOS, or Linux computer.
- Android Studio.
- A compatible Java Development Kit (JDK).
- The Android SDK required by the project.
- An internet connection for downloading dependencies during setup.
- An Android phone with USB debugging enabled, or an Android emulator.

The required JDK and SDK versions must be confirmed from the project's Gradle configuration.

---

## 🚀 Download and Installation

### Method 1: Download ZIP from GitHub

**Step 1:** Open the repository:

https://github.com/murtaza01-code/ai-crop-disease-scout

**Step 2:** Click the **Code** button and select **Download ZIP**.

**Step 3:** Extract the ZIP file using Windows File Explorer.

**Step 4:** Open Android Studio.

**Step 5:** Click **Open** and select the actual Android project root folder.

The correct folder should contain `settings.gradle.kts`.

**Step 6:** Wait for Gradle Sync to finish.

**Step 7:** Install any required SDK components if Android Studio prompts you.

**Step 8:** Select an emulator or connect your Android phone.

**Step 9:** Click the green **Run ▶** button.

If the project builds successfully, Android Studio should install and launch the app on the selected device.

### Method 2: Clone the Repository Using Git

First install Git, then open Command Prompt or a terminal.

Run:

```bash
git clone https://github.com/murtaza01-code/ai-crop-disease-scout.git
cd ai-crop-disease-scout
```

Open the cloned folder in Android Studio and wait for Gradle Sync.

If the repository contains the required Gradle Wrapper script, you can try building the application using the instructions below.

---

## 🖥️ Run Using Command Prompt

### Step 1: Verify Git and Java

```bat
git --version
java -version
```

If either command fails, install the relevant software and configure it before continuing.

### Step 2: Enter the Project Folder

If you cloned the repository into Downloads:

```bat
cd %USERPROFILE%\Downloads\ai-crop-disease-scout
```

If your folder is in a different location, change the path accordingly.

### Step 3: Check the Project Files

```bat
dir
dir gradle
dir gradlew*
```

Check that the project contains `settings.gradle.kts`, `app`, and the required Gradle configuration files.

### Step 4: Build the Debug Version

On Windows, if `gradlew.bat` exists, run:

```bat
gradlew.bat assembleDebug
```

The first build may take time because Gradle needs to download dependencies.

If the build fails, read the error message and verify the JDK, SDK, dependency configuration, and required project files.

If the Gradle Wrapper script is missing, open the project in Android Studio and repair or restore the wrapper configuration before building.

---

## 📦 Generate an APK

If the debug build succeeds, the APK is normally generated at:

```text
app/build/outputs/apk/debug/app-debug.apk
```

To locate the APK from the project root on Windows:

```bat
dir app\build\outputs\apk\debug
```

Transfer the APK to a compatible Android phone and install it only if you trust the source.

For distribution, generate and test a properly signed release APK or Android App Bundle (AAB). A debug APK is intended primarily for development and testing.

**Important:** A successful APK build does not prove that AI predictions, voice services, alerts, or every other feature works correctly.

---

## 🌐 Web Interface

The repository contains a `web` directory with:

```text
web/
├── index.html
├── styles.css
└── app.js
```

To preview the web interface:

1. Open the project folder.
2. Open the `web` directory.
3. Open `index.html` in a modern web browser.

Some features may require a server, backend, or external API and may not work from a local HTML file alone.

The web interface and Android application are separate components unless an integration is explicitly implemented.

---

## 📂 Project Structure

The expected project structure is approximately:

```text
ai-crop-disease-scout/
├── app/
│   ├── build.gradle.kts
│   ├── proguard-rules.pro
│   └── src/
│       ├── main/
│       │   ├── AndroidManifest.xml
│       │   ├── java/com/example/
│       │   │   ├── MainActivity.kt
│       │   │   ├── CropScoutApplication.kt
│       │   │   ├── data/
│       │   │   ├── ui/
│       │   │   └── ...
│       │   └── res/
│       ├── test/
│       └── androidTest/
├── gradle/
│   ├── libs.versions.toml
│   └── wrapper/
├── web/
│   ├── index.html
│   ├── styles.css
│   └── app.js
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
├── metadata.json
└── README.md
```

The exact directory structure depends on how the files are organized in the repository. The Gradle Wrapper scripts may also need to be included separately if they are missing.

---

## 🗺️ Project Roadmap

The following roadmap outlines the planned development phases.

### Phase 1: Project Foundation

- [x] Create the Android application project.
- [x] Add initial screens and navigation components.
- [x] Add crop-related data models and sample knowledge-base data.
- [x] Add local database components.
- [x] Create the initial web interface.
- [ ] Verify the repository structure.
- [ ] Build the application successfully in Android Studio.

### Phase 2: Crop Scanning

- [ ] Verify camera capture and gallery selection.
- [ ] Implement and test image-quality validation.
- [ ] Connect the scanner to a working analysis model.
- [ ] Display meaningful analysis results.
- [ ] Test with real crop images.

### Phase 3: AI Integration

- [ ] Select a suitable disease detection model or API.
- [ ] Configure any required credentials securely.
- [ ] Define the crops and diseases supported by the selected model.
- [ ] Display appropriate assessment results and guidance.
- [ ] Handle network failures and unsupported images.
- [ ] Evaluate model accuracy using representative test data.

### Phase 4: Farmer Assistance

- [ ] Expand the disease knowledge base.
- [ ] Connect outbreak alerts to a verified data source.
- [ ] Validate spray calculations against reliable agricultural guidance.
- [ ] Test recovery tracking.
- [ ] Complete and test voice assistance.
- [ ] Verify printable report functionality.

### Phase 5: Data and User Experience

- [ ] Test scan history persistence.
- [ ] Improve dashboard navigation.
- [ ] Add clear loading and error states.
- [ ] Test different Android screen sizes.
- [ ] Improve accessibility and multilingual support.

### Phase 6: Testing and Reliability

- [ ] Run unit tests.
- [ ] Run instrumentation tests.
- [ ] Fix build errors and runtime crashes.
- [ ] Test offline and poor-network scenarios.
- [ ] Review permissions and data security.
- [ ] Test the release build on Android devices.

### Phase 7: Release

- [ ] Generate a signed release APK or AAB.
- [ ] Complete final device testing.
- [ ] Prepare screenshots and release documentation.
- [ ] Publish the app when it is ready.
- [ ] Maintain the project and address reported issues.

**Current priority:** Verify the project structure, build the Android app, and make the crop image analysis workflow functional end to end.

Roadmap checkboxes indicate planned or previously added project components, not independently verified production readiness. Update them as tasks are completed and tested.

---

## 🧪 Testing and Troubleshooting

### Gradle build fails

- Check the Java version.
- Check Android Studio's configured JDK.
- Install the required Android SDK.
- Review the Gradle error message.
- Confirm that the project includes its required build files and wrapper.

### App does not install

- Check Android version compatibility.
- Confirm that the APK was generated successfully.
- Check available device storage.
- Review the installation error message.

### AI analysis does not work

- Confirm that a working model or API is configured.
- Check network access if the service requires the internet.
- Verify image format and size requirements.
- Check application logs for errors.
- Test using images supported by the selected model.

### Web interface does not work fully

- Check the browser console for errors.
- Verify any required API configuration.
- Confirm whether the feature needs a backend server.

Bug reports should include the steps to reproduce the problem and relevant error messages. Never include passwords or API secrets in public issue reports.

---

## 🔐 Security and Privacy

- Never commit API keys, passwords, access tokens, or private credentials.
- Store secrets securely outside the public repository.
- Review configuration files before publishing code.
- Request only the Android permissions required by the app.
- Protect any personal or agricultural data collected by the application.
- Verify authorization before enabling administrative features.
- Use reliable agricultural sources for recommendations.
- Do not treat unverified AI predictions as definitive diagnoses.

---

## 🤝 Contributing

Contributions and suggestions are welcome.

1. Fork the repository.
2. Create a separate branch for your changes.
3. Make and test your changes.
4. Commit your changes with a descriptive message.
5. Open a Pull Request describing what you changed.

Please avoid committing secrets, generated build files, or unrelated personal information.

---

## 👨‍💻 Author

**GitHub:** [@murtaza01-code](https://github.com/murtaza01-code)

**Repository:** [AI Crop Disease Scout](https://github.com/murtaza01-code/ai-crop-disease-scout)

---

## 📄 License

No open-source license has been selected yet. Until a license is added, reuse and redistribution remain subject to applicable copyright law.

