# AI Crop Disease Scout (Android AgriTech App)

An AI-powered crop leaf disease diagnostic and agronomic advisory Android application built with **Kotlin**, **Jetpack Compose (Material Design 3)**, and **Gemini Multimodal Vision**. Designed specifically for smallholder farmers and agricultural extension officers to triage crop pathologies early, prevent yield loss, and practice responsible integrated pest management (IPM).

---

## 🌟 Key Features

### 1. 🔍 AI Multimodal Crop Pathology Scanner
- **Dual Engine Architecture**:
  - **Cloud Vision**: Integrates Google Gemini 2.5 Flash Multimodal Vision API to inspect leaf venation, chlorosis, and lesion borders.
  - **Offline Botanical Resilience**: On-device computer vision heuristic fallback when in remote fields without network connectivity or when API keys are unconfigured.
- **7 Core Supported Crops**: Cotton (کپاس / ڪپھ), Wheat (گندم / ڪڻڪ), Rice (چاول / چانور), Tomato (ٹماٹر / ٽماٽو), Chili (مرچ / مرچ), Mango (آم / انب), Sugarcane (کماد / ڪمند).
- **1-Tap Quick Sample Testing**: Includes sample test cases with simulated leaf pathology (Cotton CLCuV, Wheat Yellow Rust, Tomato Early Blight, Rice Blast, etc.) for instant testing on emulators and offline devices.
- **Zero-Permission Photo Selection**: Uses modern Android Photo Picker (`PickVisualMedia`) for high security and privacy.

### 2. 📊 Honest Diagnostic Appraisal & Safety
- **Clear Uncertainty Notices**: Clearly flags confidence levels (High, Moderate, Uncertain) and never fabricates claims of being a certified laboratory test.
- **Strict Chemical Safety Guardrails**: Never invents hazardous chemical pesticide dosages. Recommends biological, mechanical, and cultural remedies and directs farmers to their local Agricultural Extension Office.
- **Scientific Literature Citations**: Every documented condition is cross-referenced with reputable sources including **CABI PlantwisePlus**, **FAO Crop Protection Compendium**, and **PARC (Pakistan Agricultural Research Council)**.

### 3. 🎙️ Sindhi & Urdu Voice Agent (کسان وائس ایجنٹ • هاري وائس ايجنٽ)
- **Spoken Audio Diagnosis**: One-tap "Listen in Voice" (آواز میں سنیں / آواز ۾ ٻڌو) on the diagnostic screen speaks out the problem, cause, and practical remedies aloud in Urdu or Sindhi.
- **Voice Consultation by Microphone**: Farmers can tap the mic to verbally ask questions in Urdu or Sindhi, or choose from pre-curated rural problem chips.
- **Accessible & Easy-to-Understand**: Translates complex pathological terminology into friendly, simple, rural language so illiterate and smallholder farmers can protect their crops effortlessly.
- **Offline & Cloud Speech Integration**: Backed by Android's native `TextToSpeech` engine and conversational Gemini AI with offline botanical fallback.

### 4. 🌐 Native Multilingual Support with RTL Layout
- **English**, **Urdu (اردو)**, and **Sindhi (سنڌي)** translations across all interface labels, crop names, symptoms, and prevention advice.
- **Dynamic Right-to-Left (RTL)** layout mirroring when Urdu or Sindhi is selected.

### 5. 💾 Offline-First Durability (Room SQLite Database)
- **Zero-Setup Local Persistence**: Stores full history of crop scans, confidence metrics, visual symptoms, and recommended actions.
- **Printable/Shareable Agronomy Reports**: Generates formal advisory cards with unique Scan IDs and date stamps that can be shared via SMS, WhatsApp, or printed.
- **Search & Filter**: Search scans by crop name, symptoms, or disease pathology.

### 6. 🛡️ Agronomist Review & Second-Opinion Portal
- **Farmer Flagging**: Farmers can flag suspicious or uncertain predictions for review.
- **Agronomist Audit Desk**: Protected desk where agronomists and extension officers can audit flagged scans and append official corrective notes.

---

## 🏗️ Architecture & Tech Stack

```
com.example
├── CropScoutApplication.kt    # Singleton Application context & DB holder
├── MainActivity.kt            # Edge-to-edge Scaffold & RTL direction provider
├── data
│   ├── local                  # Room Database (Entities, DAO, Repository)
│   ├── model                  # Domain models, CropType, Language strings
│   ├── remote                 # Gemini Multimodal Vision API & Offline Engine
│   └── sample                 # Documented CABI/FAO knowledge base & sample leaves
└── ui
    ├── components             # TopBar, BottomNav, Report Dialog
    ├── screens                # Home, Scanner, Result, Dashboard, KnowledgeBase, Admin, Profile
    ├── theme                  # AgriTech Material 3 ColorScheme & Typography
    └── viewmodel              # CropScoutViewModel
```

---

## 🔑 Secret Management & API Configuration

1. Open the **Secrets panel** in the Google AI Studio UI.
2. Add your Gemini API key under the key name: `GEMINI_API_KEY`.
3. The platform automatically injects this key into `.env` at build time, securely consumed via Gradle's Secrets Plugin into `BuildConfig.GEMINI_API_KEY`.
4. **Offline / Missing Key Behavior**: If `GEMINI_API_KEY` is not configured, the application gracefully informs the user and activates the built-in **On-Device Agronomic Engine**, ensuring zero crashes and full utility.

---

## 📱 Google Play Policy & Best Practices Compliance
- **Zero Excessive Permissions**: Uses Android Photo Picker (`ActivityResultContracts.PickVisualMedia`), eliminating the need for broad storage read permissions.
- **Edge-to-Edge**: Fully compliant with `enableEdgeToEdge()` and WindowInsets.
- **Adaptive Icon**: Custom high-resolution vector emblem generated with agricultural motif and pinned `#1B4332` background across all mipmap densities.

---

## 🌐 Browser App

A responsive browser-based field notebook is available in [`web`](./web). From the project root, run:

```powershell
py -m http.server 8000
```

Then open `http://localhost:8000/web/` in a browser. The web app supports English, Urdu, and Sindhi, sample crop scenarios, a searchable crop guide, printable field notes, browser speech playback, farmer details, and on-device scan history. Notes and profile details are kept in the browser's local storage.

**Image diagnosis is not connected in the web app.** A selected photo is previewed locally and can be saved as a note for expert review; it is not uploaded or analyzed. Sample scenarios are educational examples, not predictions about a real crop. Do not use the app to choose or dose chemical treatments.
