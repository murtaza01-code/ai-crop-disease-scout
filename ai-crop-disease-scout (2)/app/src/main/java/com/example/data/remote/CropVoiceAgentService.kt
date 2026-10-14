package com.example.data.remote

import com.example.BuildConfig
import com.example.data.model.AppLanguage
import com.example.data.model.DiseaseAnalysisResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class CropVoiceAgentService {

    private val httpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    fun createSpokenDiagnosisSummary(result: DiseaseAnalysisResult, language: AppLanguage): String {
        return when (language) {
            AppLanguage.URDU -> {
                if (result.isHealthy) {
                    "السلام علیکم کسان بھائی۔ مبارک ہو، آپ کی ${result.cropType.urduName} کا پتا بالکل صحت مند اور تروتازہ ہے۔ اس پر بیماری کے کوئی داغ نہیں۔ معمول کے مطابق مناسب پانی اور دیکھ بھال جاری رکھیں۔"
                } else {
                    val actions = result.immediateActions.take(2).joinToString("۔ اور ")
                    "السلام علیکم کسان بھائی۔ آپ کی ${result.cropType.urduName} میں ${result.conditionLocalName} کے اثرات دیکھے گئے ہیں۔ گھبرانے کی ضرورت نہیں ہے۔ فوری طور پر یہ کریں: $actions۔ یاد رکھیں، زہریلی ادویات کے استعمال سے پہلے اپنے مقامی زرعی افسر سے مشورہ ضرور کریں۔"
                }
            }
            AppLanguage.SINDHI -> {
                if (result.isHealthy) {
                    "سلام هاري ڀاءُ. مبارڪون هجن، توهان جي ${result.cropType.sindhiName} جو پن بلڪل چڱو ڀلو ۽ صحت مند آهي. ڪنهن به بيماري جا داغ ناهن. پنهنجي وقت سر سنڀال ۽ پاڻي جاري رکو."
                } else {
                    val actions = result.immediateActions.take(2).joinToString("، ۽ ")
                    "سلام هاري ڀاءُ. توهان جي ${result.cropType.sindhiName} ۾ ${result.conditionLocalName} جو اثر نظر آيو آهي. ڳڻتي نه ڪريو. فوري طور تي هي اپاءَ وٺو: $actions. ڪنهن به زهريلي دوا هڻڻ کان اڳ پنهنجي مقامي زرعي آفيسر سان صلاح ضرور ڪريو."
                }
            }
            AppLanguage.ENGLISH -> {
                if (result.isHealthy) {
                    "Hello respected grower. Great news, your ${result.cropType.englishName} leaf appears completely healthy with strong chlorophyll and no visible lesions. Continue routine irrigation and scouting."
                } else {
                    val actions = result.immediateActions.take(2).joinToString(". Also ")
                    "Hello respected grower. In your ${result.cropType.englishName}, symptoms indicate ${result.conditionName}. Recommended immediate steps: $actions. Please consult your local extension officer before applying chemicals."
                }
            }
        }
    }

    suspend fun answerFarmerQuery(
        query: String,
        language: AppLanguage,
        cropContext: String = "General Crop"
    ): String = withContext(Dispatchers.IO) {
        val openAiKey = BuildConfig.OPENAI_API_KEY.trim()
        val hasOpenAiKey = openAiKey.isNotEmpty() && openAiKey != "MY_OPENAI_API_KEY"

        if (hasOpenAiKey) {
            try {
                val openAiResponse = callOpenAiForVoiceResponse(openAiKey, query, language, cropContext)
                if (!openAiResponse.isNullOrBlank()) {
                    return@withContext openAiResponse
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        val geminiKey = BuildConfig.GEMINI_API_KEY.trim()
        val hasGeminiKey = geminiKey.isNotEmpty() && geminiKey != "MY_GEMINI_API_KEY"

        if (hasGeminiKey) {
            try {
                val geminiResponse = callGeminiForVoiceResponse(geminiKey, query, language, cropContext)
                if (!geminiResponse.isNullOrBlank()) {
                    return@withContext geminiResponse
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        // Offline conversational agricultural responses in Urdu and Sindhi
        return@withContext generateOfflineVoiceResponse(query, language)
    }

    private fun callOpenAiForVoiceResponse(
        apiKey: String,
        query: String,
        language: AppLanguage,
        cropContext: String
    ): String? {
        val targetLangName = when (language) {
            AppLanguage.URDU -> "Urdu (اردو)"
            AppLanguage.SINDHI -> "Sindhi (سنڌي)"
            AppLanguage.ENGLISH -> "English"
        }

        val systemPrompt = """
            You are 'Kisan Dost Voice Agent' (کسان دوست وائس ایجنٹ / هاري دوست وائس ايجنٽ), a warm, encouraging, expert agricultural extension companion for rural farmers in Pakistan.
            The farmer asks you a question in $targetLangName about crop health or farm management.
            Crop context: $cropContext.
            
            Provide an oral spoken response strictly in $targetLangName.
            Rules:
            1. Speak directly, warmly, and respectfully using customary rural honorifics (e.g. 'کسان بھائی' in Urdu or 'هاري ڀاءُ' in Sindhi).
            2. Explain practical, immediately actionable solutions in 3 to 4 short, crystal-clear sentences.
            3. Prioritize natural, cultural, biological, and Integrated Pest Management (IPM) measures (e.g., neem extract, yellow sticky traps, rogueing diseased plants, sanitation, water drainage).
            4. Do not invent exact hazardous chemical dosages; encourage consulting their local agricultural extension department.
            5. Return STRICTLY the spoken text without asterisks, markdown, emojis, or bullet points, as this will be read aloud by Text-To-Speech.
        """.trimIndent()

        val jsonPayload = JSONObject().apply {
            put("model", "gpt-4o-mini")
            val messagesArray = JSONArray().apply {
                put(JSONObject().apply {
                    put("role", "system")
                    put("content", systemPrompt)
                })
                put(JSONObject().apply {
                    put("role", "user")
                    put("content", "Farmer question: $query")
                })
            }
            put("messages", messagesArray)
            put("temperature", 0.3)
            put("max_tokens", 400)
        }

        val mediaType = "application/json; charset=utf-8".toMediaType()
        val requestBody = jsonPayload.toString().toRequestBody(mediaType)
        val request = Request.Builder()
            .url("https://api.openai.com/v1/chat/completions")
            .header("Authorization", "Bearer $apiKey")
            .post(requestBody)
            .build()

        val response = httpClient.newCall(request).execute()
        if (!response.isSuccessful) return null

        val responseBody = response.body?.string() ?: return null
        val root = JSONObject(responseBody)
        val choices = root.optJSONArray("choices") ?: return null
        if (choices.length() == 0) return null
        val message = choices.getJSONObject(0).optJSONObject("message") ?: return null
        return message.optString("content", "").trim()
    }

    private fun callGeminiForVoiceResponse(
        apiKey: String,
        query: String,
        language: AppLanguage,
        cropContext: String
    ): String? {
        val targetLangName = when (language) {
            AppLanguage.URDU -> "Urdu (اردو)"
            AppLanguage.SINDHI -> "Sindhi (سنڌي)"
            AppLanguage.ENGLISH -> "English"
        }

        val prompt = """
            You are 'Kisan Dost Voice Agent' (کسان دوست وائس ایجنٹ / هاري دوست وائس ايجنٽ), a warm, encouraging, expert agricultural extension companion for rural farmers.
            The farmer asks in $targetLangName: "$query" (Crop context: $cropContext).
            
            Provide an spoken oral response strictly in $targetLangName.
            Rules:
            1. Speak directly, warmly, and respectfully using rural honorifics (e.g. 'کسان بھائی' or 'هاري ڀاءُ').
            2. Explain the core solution in simple, practical, actionable terms in 3 to 4 short sentences.
            3. Emphasize organic, cultural, and preventive remedies (neem water, sanitation, drainage, seed choice).
            4. Do not invent dangerous chemical doses; advise checking with district agriculture office.
            5. Return ONLY the plain text response with no markdown, stars, or bullet symbols, as this will be read aloud by Text-To-Speech.
        """.trimIndent()

        val jsonPayload = JSONObject().apply {
            val contentsArray = JSONArray()
            val contentObj = JSONObject()
            val partsArray = JSONArray()
            partsArray.put(JSONObject().apply { put("text", prompt) })
            contentObj.put("parts", partsArray)
            contentsArray.put(contentObj)
            put("contents", contentsArray)
            put("generationConfig", JSONObject().apply {
                put("temperature", 0.3)
            })
        }

        val mediaType = "application/json; charset=utf-8".toMediaType()
        val requestBody = jsonPayload.toString().toRequestBody(mediaType)
        val url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=$apiKey"

        val request = Request.Builder().url(url).post(requestBody).build()
        val response = httpClient.newCall(request).execute()
        if (!response.isSuccessful) return null

        val responseBody = response.body?.string() ?: return null
        val root = JSONObject(responseBody)
        val candidate = root.optJSONArray("candidates")?.optJSONObject(0) ?: return null
        val part = candidate.optJSONObject("content")?.optJSONArray("parts")?.optJSONObject(0) ?: return null
        return part.optString("text", "").trim()
    }

    private fun generateOfflineVoiceResponse(query: String, language: AppLanguage): String {
        val q = query.lowercase()
        return when (language) {
            AppLanguage.URDU -> {
                when {
                    q.contains("کپاس") || q.contains("مروڑ") || q.contains("سفید مکھی") -> {
                        "کسان بھائی، کپاس کے پتوں کا مڑنا سفید مکھی کے حملے کی علامت ہو سکتا ہے۔ فوری طور پر کھیت میں پیلے اسٹکی کارڈز لگائیں اور نیم کے عرق کا اسپرے کریں۔ متاثرہ پودوں کو کھیت سے نکال کر محفوظ فاصلے پر تلف کریں۔"
                    }
                    q.contains("گندم") || q.contains("کنگی") || q.contains("پیلا") -> {
                        "کسان بھائی، گندم پر پیلا سفوف کنگی بیماری کی علامت ہے۔ ٹھنڈے اور مرطوب موسم میں یوریا کھاد کا غیر ضروری استعمال روکیں اور پوٹاش کی مناسب خوراک دیں۔ مزید رہنمائی کے لیے مقامی زرعی مرکز سے رابطہ کریں۔"
                    }
                    q.contains("دھان") || q.contains("چاول") || q.contains("بلاسٹ") -> {
                        "کسان بھائی، دھان کے پتوں پر آنکھ نما داغ بلاسٹ بیماری ہیں۔ کھیت میں مسلسل کھڑا پانی نکال کر زمین کو ہوادار بنائیں، اور نائٹروجن کی اضافی مقدار فوری بند کریں۔"
                    }
                    q.contains("ٹماٹر") || q.contains("مرچ") -> {
                        "کسان بھائی، ٹماٹر اور مرچوں کے نچلے پتے زمین سے لگنے پر داغدار ہوتے ہیں۔ پودوں کے نچلے پتے توڑ دیں، قطرہ قطرہ پانی دیں اور زمین پر بھوسا بچھائیں تاکہ پھپھوندی پتوں تک نہ پہنچے۔"
                    }
                    else -> {
                        "محترم کسان بھائی، پودے کے متاثرہ پتے کی تصویر اسکینر میں اپلوڈ کریں تاکہ درست بیماری کی فوری نشاندہی ہو سکے۔ پودے کی صفائی کا خیال رکھیں اور اضافی کیمیائی زہروں سے پرہیز کریں۔"
                    }
                }
            }
            AppLanguage.SINDHI -> {
                when {
                    q.contains("ڪپھ") || q.contains("وڪوڙ") || q.contains("اڇي مکي") -> {
                        "هاري ڀاءُ، ڪپھ جا پن مڙڻ اڇي مکي جي وائرس جي نشاني ٿي سگهي ٿي. فوري طور تي پيلا ڪارڊ ٻنيءَ ۾ لڳايو ۽ نم جي پاڻي جو ڇٽڪار ڪريو. بيمار ٻوٽا ٻنيءَ مان ڪڍي تلف ڪريو."
                    }
                    q.contains("ڪڻڪ") || q.contains("رتو") || q.contains("پيلو") -> {
                        "هاري ڀاءُ، ڪڻڪ تي پيلو پائوڊر رتي بيماري جو اشارو آهي. واڌو يوريا ڀاڻ جو استعمال بند ڪريو ۽ پوٽاش ورتايو. وڌيڪ تصديق لاءِ زرعي مرڪز سان رابطو رکو."
                    }
                    q.contains("چانور") || q.contains("ساريون") -> {
                        "هاري ڀاءُ، چانورن جي پنن جا سڙيل چٽا بلاسٹ بيماري آهن. ٻنيءَ مان بيٺل پاڻي ڪڍي هوا لڳڻ ڏيو ۽ کٽين تي گند گاهه صاف رکو."
                    }
                    else -> {
                        "محترم هاري ڀاءُ، پنهنجي بيمار پن جي تصوير اسڪينر ۾ وجهو ته جيئن صحيح بيماري سڃاڻي سگهجي. قدرتي ۽ صاف طريقن کي ترجيح ڏيو."
                    }
                }
            }
            AppLanguage.ENGLISH -> {
                "Hello respected farmer. For best results, capture a clear close-up leaf photo using the scanner. Practice crop sanitation, avoid excess nitrogen, and consult your regional agricultural officer."
            }
        }
    }
}
