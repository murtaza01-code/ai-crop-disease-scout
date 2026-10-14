package com.example.data.sample

import com.example.data.model.AppLanguage
import com.example.data.model.CropType
import com.example.data.model.KnowledgeBaseDisease

object KnowledgeBaseData {
    val diseases: List<KnowledgeBaseDisease> = listOf(
        // COTTON
        KnowledgeBaseDisease(
            id = "cotton_clcuv",
            cropType = CropType.COTTON,
            diseaseName = "Cotton Leaf Curl Virus (CLCuV)",
            urduName = "کپاس کا پتہ مروڑ وائرس",
            sindhiName = "ڪپھ جي پنن جو وڪوڙ وائرس",
            scientificName = "Begomovirus (transmitted by Bemisia tabaci)",
            severityLevel = "High",
            symptomsEnglish = listOf(
                "Upward or downward curling of leaf margins",
                "Thickening and dark-green discoloration of veins",
                "Enation (small cup-shaped leaf-like outgrowths) on the underside of leaves",
                "Stunted plant growth and reduced boll formation"
            ),
            symptomsUrdu = listOf(
                "پتوں کے کناروں کا اوپر یا نیچے کی طرف مڑ جانا",
                "پتے کی رگوں کا موٹا اور گہرا سبز ہو جانا",
                "پتے کی نچلی سطح پر پتے جیسی اضافی ساخت (اینیشن) بننا",
                "پودے کا قد چھوٹا رہ جانا اور ٹینڈے کم بننا"
            ),
            symptomsSindhi = listOf(
                "پنن جي ڪنارن جو مٿي يا هيٺ وڪوڙجي وڃڻ",
                "پن جي رڳن جو ٿلهو ۽ کهرو سائو ٿي وڃڻ",
                "پن جي هيٺئين پاسي ننڍڙا اضافي پن ٺهڻ",
                "ٻوٽي جي واڌ جو بيهي وڃڻ ۽ ڦٽين جو گهٽجڻ"
            ),
            preventionEnglish = listOf(
                "Plant certified CLCuV-tolerant hybrid seed varieties",
                "Manage whitefly vector populations using yellow sticky traps and neem extract",
                "Eradicate weed hosts (such as Abutilon and Parthenium) around field borders",
                "Avoid late planting to escape peak whitefly migrations"
            ),
            preventionUrdu = listOf(
                "تصدیق شدہ وائرس سے محفوظ اقسام کے بیج کاشت کریں",
                "سفید مکھی کے تدارک کے لیے پیلے اسٹکی ٹریپس اور نیم کا عرق استعمال کریں",
                "کھیت کی مینڈھوں سے بوٹیوں (خاص کر سوانک اور گاجر بوٹی) کا خاتمہ کریں",
                "سفید مکھی کے حملے سے بچنے کے لیے بروقت کاشت کو ترجیح دیں"
            ),
            preventionSindhi = listOf(
                "تصديق ٿيل بيماري-سٽيندڙ ٻج پوکيو",
                "اڇي مکي جي روڪٿام لاءِ پيلا اسٽڪي ٽريپ ۽ نم جو عرق ورتايو",
                "ٻنين جي دنگن تان واڌو گاهه ۽ ٻوٽا ختم ڪريو",
                "بروقت پوک ڪري اڇي مکي جي موثر روڪ ڪريو"
            ),
            sourceReference = "CABI PlantwisePlus Datasheet (Cotton Leaf Curl Disease); PARC Cotton Research Institute"
        ),
        KnowledgeBaseDisease(
            id = "cotton_bacterial_blight",
            cropType = CropType.COTTON,
            diseaseName = "Bacterial Blight (Angular Leaf Spot)",
            urduName = "کپاس کا کونیائی دھبہ / بیکٹیریل بلائیٹ",
            sindhiName = "ڪپھ جا ڪُنڊائتا داغ",
            scientificName = "Xanthomonas citri pv. malvacearum",
            severityLevel = "Moderate",
            symptomsEnglish = listOf(
                "Angular, water-soaked lesions bounded by leaf veinlets",
                "Lesions turn reddish-brown to dark black with age",
                "Premature defoliation and black-arm stem lesions",
                "Boll lesions leading to fiber staining"
            ),
            symptomsUrdu = listOf(
                "پتوں پر رگوں کے ساتھ کونیائی پانی بھرے داغ",
                "داغ وقت کے ساتھ بھورے اور سیاہ پڑ جاتے ہیں",
                "پتوں کا قبل از وقت گرنا اور تنے پر سیاہ پٹیاں",
                "ٹینڈوں پر سیاہ دھبے اور روئی کی خرابی"
            ),
            symptomsSindhi = listOf(
                "پنن جي رڳن وچ ۾ ڪنڊائتا پاڻي وارا نشان",
                "اهي داغ آهستي آهستي ناسي ۽ ڪارا ٿين ٿا",
                "پنن جو اڻوقت ڇڻڻ ۽ ٽارڙين جو سڙڻ",
                "ڦٽين تي داغ ۽ ڪپھ جو خراب ٿيڻ"
            ),
            preventionEnglish = listOf(
                "Use acid-delinted and certified pathogen-free seeds",
                "Practice 2-year crop rotation with non-host crops like wheat or maize",
                "Avoid excessive overhead flood irrigation to reduce leaf wetness duration",
                "Deep plow crop residue after harvest to destroy bacterial inoculum"
            ),
            preventionUrdu = listOf(
                "تیزاب سے صاف اور تصدیق شدہ بیج کا استعمال کریں",
                "گندم یا مکئی کے ساتھ دو سالہ فصلوں کی گردش اپنائیں",
                "کھیت میں ضرورت سے زیادہ پانی کھڑا نہ ہونے دیں",
                "کٹائی کے بعد گہرا ہل چلا کر باقیات کو تلف کریں"
            ),
            preventionSindhi = listOf(
                "تيزاب سان ڌوتل صاف ٻج پوکيو",
                "فصلن جي مٽاسٽا (ڪڻڪ يا مڪئي) ڪريو",
                "ٻني ۾ پاڻي گهڻو وقت بيٺل نه رهڻ ڏيو",
                "فصل جي لڻڻ بعد زمين جي اونهي کيڙ ڪريو"
            ),
            sourceReference = "FAO Crop Protection Compendium; Central Cotton Research Institute (CCRI)"
        ),

        // WHEAT
        KnowledgeBaseDisease(
            id = "wheat_yellow_rust",
            cropType = CropType.WHEAT,
            diseaseName = "Yellow (Stripe) Rust",
            urduName = "گندم کی پیلی کنگی",
            sindhiName = "ڪڻڪ جو پيلو رتُو",
            scientificName = "Puccinia striiformis f. sp. tritici",
            severityLevel = "High",
            symptomsEnglish = listOf(
                "Bright yellow to orange pustules arranged in linear stripes along leaf veins",
                "Powdery yellow spores rubbing off easily on fingertips",
                "Early leaf desiccation and chlorotic stripe necrosis",
                "Shriveled grain formation and significant yield loss"
            ),
            symptomsUrdu = listOf(
                "پتے کی رگوں کے ساتھ پیلے اور نارنجی باریک دانوں کی قطاریں",
                "انگلی لگانے پر پیلا سفوف یا پاؤڈر لگ جانا",
                "پتوں کا تیزی سے سوکھنا اور زرد پڑنا",
                "دانے سکڑ جانا اور پیداوار میں شدید کمی"
            ),
            symptomsSindhi = listOf(
                "پنن تي پيلا ۽ کهرا نارنگي داغ لائينن جي صورت ۾",
                "آڱر لڳائڻ سان پيلو اٽو هٿ تي لڳڻ",
                "پنن جو جلد سڪي زرد ٿيڻ",
                "ڪڻڪ جا داڻا سُڪڙجي ننڍا ٿيڻ"
            ),
            preventionEnglish = listOf(
                "Sow rust-resistant wheat cultivars certified by national seed councils",
                "Monitor cool, humid microclimates (especially riverine and irrigated plains)",
                "Apply balanced basal fertilizers; avoid excessive late nitrogen feeding",
                "Report sudden rust foci to regional wheat pathologists immediately"
            ),
            preventionUrdu = listOf(
                "کنگی کے خلاف مزاحمت رکھنے والی منظور شدہ اقسام کاشت کریں",
                "ٹھنڈے اور نمی والے موسم میں فصل کا مسلسل معائنہ کریں",
                "نائٹروجن (یوریا) کھاد کا متوازن استعمال کریں",
                "کھیت میں کنگی کا پھیلاؤ نظر آنے پر زرعی افسر کو مطلع کریں"
            ),
            preventionSindhi = listOf(
                "رتي بيماري جي خلاف قوت رکندڙ نوان ٻج پوکيو",
                "ٿڌي ۽ گهم واري موسم ۾ فصل جو روزانو جائزو وٺو",
                "يوريا ڀاڻ جو متوازن ۽ وقت سر استعمال ڪريو",
                "بيماري نظر اچڻ تي فوري مقامي زرعي آفيسر سان رابطو ڪريو"
            ),
            sourceReference = "CIMMYT Global Rust Reference Center; Wheat Research Institute"
        ),
        KnowledgeBaseDisease(
            id = "wheat_loose_smut",
            cropType = CropType.WHEAT,
            diseaseName = "Loose Smut",
            urduName = "گندم کا کانگیاری / سیاہ سٹہ",
            sindhiName = "ڪڻڪ جو ڪارو سٽو",
            scientificName = "Ustilago tritici",
            severityLevel = "Moderate",
            symptomsEnglish = listOf(
                "Entire wheat ear converted into a mass of olive-black powdery fungal spores",
                "Spikelets emerge earlier than healthy heads with membrane quickly rupturing",
                "Bare rachis remains after wind blows the spores away"
            ),
            symptomsUrdu = listOf(
                "گندم کا پورا سٹہ سیاہ پاؤڈر اور راکھ نما سفوف میں تبدیل ہو جانا",
                "بیمار سٹے عام سٹوں سے پہلے نکلتے ہیں اور جلدی جھڑ جاتے ہیں",
                "ہوا چلنے کے بعد صرف ننگی ڈنڈی باقی رہ جاتی ہے"
            ),
            symptomsSindhi = listOf(
                "ڪڻڪ جو سڄو سنگ ڪاري پائوڊر ۾ تبديل ٿي وڃڻ",
                "بيمار سنگ تڪڙو ٻاهر نڪرندا آهن ۽ ڪارا ٿيندا آهن",
                "واهه لڳڻ سان ڪارو مادو اڏري خالي تيلي بچي وڃڻ"
            ),
            preventionEnglish = listOf(
                "Use certified smut-free seed with systemic seed treatment",
                "Conduct hot water seed treatment (54°C for 10 min) for organic farms",
                "Rogue out and burn infected heads in cloth bags before spores disperse"
            ),
            preventionUrdu = listOf(
                "بیماری سے پاک تصدیق شدہ بیج استعمال کریں اور بیج کو زہر لگا کر بوئیں",
                "آرگینک کاشتکاری میں گرم پانی کے ذریعے بیج کا علاج کریں",
                "متاثرہ سٹوں کو بوری میں بند کر کے کھیت سے دور جلا دیں"
            ),
            preventionSindhi = listOf(
                "بيماري کان پاڪ تصديق ٿيل ٻج دوائن سان لڳائي پوکيو",
                "متاثر ٿيل ڪارا سنگ ٿيلهي ۾ وجهي ٻني کان پري ساڙي ڇڏيو"
            ),
            sourceReference = "FAO Plant Production and Protection Series; PARC Wheat Directorate"
        ),

        // RICE
        KnowledgeBaseDisease(
            id = "rice_bacterial_blight",
            cropType = CropType.RICE,
            diseaseName = "Bacterial Leaf Blight (BLB)",
            urduName = "دھان کا بیکٹیریل بلائیٹ / پتوں کا جھلساؤ",
            sindhiName = "چانورن جا سڙيل پن",
            scientificName = "Xanthomonas oryzae pv. oryzae",
            severityLevel = "High",
            symptomsEnglish = listOf(
                "Water-soaked to yellowish-white wavy stripes starting from leaf tips and margins",
                "Bacterial milky exudate (dew drops) visible on leaves during humid mornings",
                "Leaves dry up and turn grayish-white (kresek phase in seedlings)",
                "Poor grain filling and reduced panicle weight"
            ),
            symptomsUrdu = listOf(
                "پتوں کے سروں اور کناروں سے لہراتی زرد اور سفید پٹیاں شروع ہونا",
                "صبح کے وقت پتوں پر بیکٹیریا کے قطرے (شبنم جیسے) دکھائی دینا",
                "پتے سوکھ کر سفید یا سرمئی ہو جانا",
                "مونجروں میں دانے خالی یا کمزور رہ جانا"
            ),
            symptomsSindhi = listOf(
                "پنن جي نوڪن ۽ پاسن کان پيليون ۽ اڇيون پٽيون پيدا ٿيڻ",
                "صبح جي مهل پنن تي کير جهڙا ڦڙا ڏسڻ ۾ اچڻ",
                "پن سڪي اڇا ۽ نيسارا بي رنگ ٿي وڃڻ",
                "سارين جا داڻا ڦوٽ ٿيڻ يا خالي رهڻ"
            ),
            preventionEnglish = listOf(
                "Plant BLB-resistant basmati or coarse rice varieties",
                "Split nitrogen applications; avoid top-dressing nitrogen on infected fields",
                "Ensure proper field drainage and avoid keeping standing water continuously",
                "Maintain weed sanitation on bunds and avoid clipping seedling tips during transplanting"
            ),
            preventionUrdu = listOf(
                "بلائیٹ کے خلاف مزاحمت رکھنے والی باسمتی یا موٹی اقسام منتخب کریں",
                "نائٹروجن کھاد کی قسطیں بنائیں اور بیماری کی صورت میں اضافی یوریا بند کریں",
                "کھیت سے پانی نکال کر زمین کو کچھ دیر خشک ہونے دیں",
                "پنیری لگاتے وقت پتوں کے سرے کاٹنے سے گریز کریں"
            ),
            preventionSindhi = listOf(
                "بيماري جي خلاف قوت رکندڙ سارين جا ٻج چونڊيو",
                "يوريا ڀاڻ جا ٽڪرا ٽڪرا ڪري ڏيو، بيماري ۾ واڌو يوريا نه وجهو",
                "پاڻي ڪجهه وقت ڪڍي زمين کي ساهه کڻڻ ڏيو",
                "ٻوٽا لڳائڻ وقت پنن جا مٿا ڪٽڻ کان پاسو ڪريو"
            ),
            sourceReference = "International Rice Research Institute (IRRI) Diagnostic Key; Kala Shah Kaku Rice Institute"
        ),
        KnowledgeBaseDisease(
            id = "rice_blast",
            cropType = CropType.RICE,
            diseaseName = "Rice Blast",
            urduName = "دھان کا بلاسٹ / تلی روگ",
            sindhiName = "چانورن جو بلاسٽ / پنن جو داغ",
            scientificName = "Magnaporthe oryzae",
            severityLevel = "High",
            symptomsEnglish = listOf(
                "Spindle-shaped or eye-shaped lesions with gray or white centers and brown margins",
                "Lesions enlarge and coalesce, burning entire leaf blades",
                "Neck rot (blackening at the panicle node causing falling heads)",
                "Complete grain sterility in late season"
            ),
            symptomsUrdu = listOf(
                "پتوں پر آنکھ کی شکل کے تکونی داغ جن کے درمیان سرمئی اور کنارے بھورے ہوتے ہیں",
                "داغ آپس میں مل کر پورے پتے کو جھلسا دیتے ہیں",
                "سٹے کی گردن کالی ہو کر ٹوٹ جانا (گردن توڑ بلاسٹ)",
                "دانے بالکل خالی رہ جانا"
            ),
            symptomsSindhi = listOf(
                "پنن تي اکين وانگر وچ ۾ اڇا ۽ ڪنارن تي ناسي چٽا پيدا ٿيڻ",
                "سڄا پن سڙي باهه وانگر ڏسڻ",
                "نيساري جي ڳچي ڪاري ٿي ٽٽي پوڻ",
                "فصل جو داڻو بنهه خالي ٿي وڃڻ"
            ),
            preventionEnglish = listOf(
                "Treat seed with biological Trichoderma or certified seed dressing",
                "Avoid high seeding density and stagnant water flooding",
                "Apply potassium and silicon soil amendments to strengthen leaf cuticle",
                "Avoid late planting and prolonged high nitrogen application"
            ),
            preventionUrdu = listOf(
                "بیج کو ٹرائیکوڈرما یا منظور شدہ دوائی لگا کر کاشت کریں",
                "پنیری کو ضرورت سے زیادہ گنجان نہ لگائیں",
                "پوٹاش کھاد کا استعمال بڑھائیں تاکہ پودے کا قدرتی دفاع مضبوط ہو",
                "زیادہ نائٹروجن سے پرہیز کریں"
            ),
            preventionSindhi = listOf(
                "ٻج کي ڌوئي دوائن سان لڳائي پوکيو",
                "پنيءَ کي گهڻو ڳتيل نه پوکيو",
                "پوٽاش ڀاڻ استعمال ڪري ٻوٽي جي قوت وڌايو"
            ),
            sourceReference = "IRRI Blast Management Compendium; CABI PlantwisePlus"
        ),

        // TOMATO
        KnowledgeBaseDisease(
            id = "tomato_early_blight",
            cropType = CropType.TOMATO,
            diseaseName = "Early Blight",
            urduName = "ٹماٹر کا اگیتا جھلساؤ",
            sindhiName = "ٽماٽي جو اڳواٽ سڙاءُ",
            scientificName = "Alternaria solani",
            severityLevel = "Moderate",
            symptomsEnglish = listOf(
                "Concentric dark rings with a 'target-board' or bullseye appearance on older leaves",
                "Surrounding leaf tissue turns chlorotic yellow",
                "Lower foliage defoliates upward, exposing fruit to sunscald",
                "Dark, sunken, leathery lesions near the stem end of fruits"
            ),
            symptomsUrdu = listOf(
                "پرانے پتوں پر نشانہ بازی کے بورڈ (ہدف) جیسے گول گول گہرے حلقے",
                "داغ کے اردگرد پتا زرد پڑ جاتا ہے",
                "نیچے کے پتے پہلے جھڑتے ہیں اور ٹماٹر دھوپ سے خراب ہوتے ہیں",
                "ٹماٹر کے پھل پر تنے کے قریب دھنسے ہوئے کالے چمڑے نما داغ"
            ),
            symptomsSindhi = listOf(
                "هيٺين پنن تي گول دائري وانگر ناسي ۽ ڪارا داغ",
                "داغ جي چوڌاري پن پيلا ٿي وڃڻ",
                "هيٺيان پن سڪي ڇڻي وڃڻ جنهن سان ٽماٽا سج جي گرمي سان سڙن",
                "ٽماٽي جي ميوي تي ڪارا دٻيل داغ"
            ),
            preventionEnglish = listOf(
                "Mulch beds with straw to prevent fungal spores splashing from soil onto lower leaves",
                "Prune lower leaves that touch the ground to improve air circulation",
                "Drip irrigation preferred over sprinkler watering to keep leaves dry",
                "Rotate solanaceous crops (avoid planting after potatoes or chilies for 3 years)"
            ),
            preventionUrdu = listOf(
                "زمین پر بھوسے کی ملچنگ کریں تاکہ مٹی سے پھپھوندی اڑ کر پتوں پر نہ آئے",
                "زمین سے چھوتے ہوئے نیچے کے پتے توڑ دیں تاکہ ہوا کا گزر بہتر ہو",
                "پتوں کے اوپر پانی چھڑکنے کی بجائے جڑوں کو قطرہ قطرہ پانی دیں",
                "ٹماٹر، آلو اور مرچوں کے بعد کم از کم 3 سال تک یہ فصلیں دوبارہ نہ لگائیں"
            ),
            preventionSindhi = listOf(
                "زمين تي ڪک يا ٻوسو وڇايو ته جيئن ڇنڊا پنن تي نه لڳن",
                "زمين سان لڳندڙ هيٺيان پن ڇانٽي ڇڏيو ته هوا لڳي",
                "پنن مٿان پاڻي هڻڻ بدران پاڙن ۾ پاڻي ڏيو",
                "ٽماٽي جي پوک کانپوءِ مرچ يا پٽاٽا لڳاتار نه پوکيو"
            ),
            sourceReference = "FAO Vegetable Pest Manual; University of Agriculture Faisalabad (UAF) Extension"
        ),

        // CHILI
        KnowledgeBaseDisease(
            id = "chili_anthracnose",
            cropType = CropType.CHILI,
            diseaseName = "Anthracnose (Fruit Rot & Dieback)",
            urduName = "مرچ کا اینتھراکنوز / کوڑھ / پھل گلنا",
            sindhiName = "مرچ جو ڪوڙهه / ڦل جو سڙڻ",
            scientificName = "Colletotrichum capsici / C. truncatum",
            severityLevel = "High",
            symptomsEnglish = listOf(
                "Circular sunken lesions with black concentric spore rings on ripening fruits",
                "Straw-colored diseased fruit patches turning paper-thin",
                "Twig die-back starting from the plant tips moving downwards",
                "Severe drop of flowers and immature chili pods"
            ),
            symptomsUrdu = listOf(
                "پکتی ہوئی مرچوں پر اندر دھنسے ہوئے گول داغ جن پر سیاہ حلقے ہوتے ہیں",
                "پھل پر پیلے بھورے سوکھے کاغذی دھبے بن جانا",
                "اوپر کی شاخوں کا سوکھنا جو نیچے کی طرف پھیلتا ہے (ڈائی بیک)",
                "پھولوں اور کچی مرچوں کا کثرت سے گرنا"
            ),
            symptomsSindhi = listOf(
                "پڪل مرچن تي گول اندر دٻيل ناسي ۽ ڪارا داغ",
                "مرچ جي ڦر تي ڪاغذ وانگر سڪل چٽا پيدا ٿيڻ",
                "ٽارين جو مٿان کان هيٺ سڪي وڃڻ",
                "گلن ۽ ننڍين مرچن جو ڇڻي پوڻ"
            ),
            preventionEnglish = listOf(
                "Collect and burn diseased fruits and infected stems immediately",
                "Space plants generously (at least 45 cm) to facilitate quick leaf drying",
                "Use neem oil and bio-fungicides during pre-flowering stages",
                "Harvest red ripe chilies promptly to reduce field exposure to moisture"
            ),
            preventionUrdu = listOf(
                "بیمار مرچیں اور سوکھی شاخیں جمع کر کے فوری جلا دیں",
                "پودوں کے درمیان مناسب فاصلہ رکھیں تاکہ ہوا کا گزر رہے",
                "پھول آنے سے پہلے نیم کا تیل اور بائیو فنگسائیڈز استعمال کریں",
                "پکی ہوئی سرخ مرچیں بروقت توڑ لیں تاکہ نمی سے نقصان نہ ہو"
            ),
            preventionSindhi = listOf(
                "خراب مرچون ۽ سڪل ٽاريون فوري ميڙي ساڙي ڇڏيو",
                "ٻوٽن جي وچ ۾ کليل وڇوٽي رکو ته جيئن پن سڪل رهن",
                "نم جو تيل يا قدرتي دوائون گلن کان اڳ ڇٽڪاريو",
                "پڪل لال مرچون وقت تي چونڊي وٺو"
            ),
            sourceReference = "CABI PlantwisePlus Chili Pest Sheet; National Agriculture Research Centre (NARC)"
        ),

        // MANGO
        KnowledgeBaseDisease(
            id = "mango_anthracnose",
            cropType = CropType.MANGO,
            diseaseName = "Mango Anthracnose",
            urduName = "آم کا اینتھراکنوز / سیاہ داغ",
            sindhiName = "انب جو ڪارو داغ / اينٿراڪنوز",
            scientificName = "Colletotrichum gloeosporioides",
            severityLevel = "High",
            symptomsEnglish = listOf(
                "Dark brown to black angular necrotic spots on tender leaves and blossoms",
                "Blossom blight causing whole panicles to dry and wither",
                "Teardrop-shaped black staining on ripening mango fruits",
                "Latent post-harvest fruit rot ruining export quality"
            ),
            symptomsUrdu = listOf(
                "نئے نکلنے والے پتوں اور بور پر سیاہ اور بھورے داغ",
                "آم کے بور کا سوکھ کر کالا ہو جانا اور گر جانا",
                "پکتے ہوئے آموں پر آنسو کی شکل کے سیاہ داغ",
                "پھل اترنے کے بعد کالا پڑ جانا اور معیار خراب ہونا"
            ),
            symptomsSindhi = listOf(
                "نون نازڪ پنن ۽ انب جي ٻور تي ڪارا داغ",
                "ٻور جو ڪارو ٿي سڙي وڃڻ",
                "انب جي ميوي تي ڳوڙهن وانگر ڪاريون ليڪون ۽ چٽا",
                "انب لاهڻ بعد ڪارو ٿي خراب ٿيڻ"
            ),
            preventionEnglish = listOf(
                "Prune dead twigs, crisscrossing branches, and mummified fruits after harvest",
                "Ensure sun-penetration into inner tree canopy through sanitary pruning",
                "Apply post-harvest hot water treatment (52°C for 5 minutes) for market fruit",
                "Avoid overhead irrigation during flowering and fruit setting"
            ),
            preventionUrdu = listOf(
                "آم کی کٹائی کے بعد سوکھی لکڑیاں اور پرانا خشک پھل کاٹ کر ختم کریں",
                "درخت کے اندرونی حصے میں دھوپ پہنچانے کے لیے شاخ تراشی کریں",
                "پھل منڈی بھیجنے سے پہلے گرم پانی (52 ڈگری پر 5 منٹ) کا غسل دیں",
                "بور اور پھل بننے کے دوران درختوں پر اوپر سے پانی نہ چھڑکیں"
            ),
            preventionSindhi = listOf(
                "انب لاهڻ بعد وڻ جي سڪل ڪاٺين ۽ مئل ڦر کي ڇانگي صاف ڪريو",
                "وڻ جي وچ تائين اس پهچائڻ لاءِ ٽاريون هلڪيون ڪريو",
                "انب وڪري لاءِ موڪلڻ کان اڳ 52 درجا گرم پاڻي ۾ 5 منٽ ڌوئو",
                "ٻور نڪرڻ مهل مٿان کان وڻ کي پاڻي نه هڻو"
            ),
            sourceReference = "Mango Research Station Shujabad; FAO Tropical Fruit Advisory"
        ),

        // SUGARCANE
        KnowledgeBaseDisease(
            id = "sugarcane_red_rot",
            cropType = CropType.SUGARCANE,
            diseaseName = "Red Rot of Sugarcane",
            urduName = "کماد کا کینسر / رتُو روگ / ریڈ راٹ",
            sindhiName = "ڪمند جو رتُو روگ / ريڊ راٽ",
            scientificName = "Colletotrichum falcatum",
            severityLevel = "High",
            symptomsEnglish = listOf(
                "Midrib of top leaves shows blood-red lesions with dark margins",
                "Withering and yellowing of the crown leaves starting from outer edges",
                "Stalk splits reveal longitudinal red tissue with distinctive white cross-bands",
                "Alcoholic or acidic odor inside rotting cane stalks with hollow pith"
            ),
            symptomsUrdu = listOf(
                "اوپر کے پتوں کی درمیانی موٹی رگ پر خون جیسا سرخ داغ",
                "کماد کی گوب کے پتوں کا اوپر سے سوکھنا اور پیلا پڑنا",
                "گنا چیرنے پر اندر کا گودا لال اور اس میں سفید دھاریاں نظر آنا",
                "گنے کے اندر سے کھٹی یا الکوحل جیسی بو آنا اور گنا اندر سے کھوکھلا ہونا"
            ),
            symptomsSindhi = listOf(
                "مٿئين پن جي وچ واري ٿلهي رڳ تي رت جهڙو ڳاڙهو داغ",
                "ڪمند جي چوٽي سڪي پيلي ٿي وڃڻ",
                "ڪمند چيري ڏسڻ تي اندر لال رنگ ۽ اڇيون پٽيون ڏسڻ ۾ اچڻ",
                "ڪمند مان کٽي بوءِ اچڻ ۽ ڳنو اندران خالي ٿيڻ"
            ),
            preventionEnglish = listOf(
                "Plant healthy setts certified free of red rot from disease-free nurseries",
                "Dip seed cane setts in hot aerated water (50°C for 2 hours) or bio-fungicide",
                "Practice 3-year crop rotation; do NOT ratoon heavily infected fields",
                "Burn leftover cane trash and maintain field drainage during monsoons"
            ),
            preventionUrdu = listOf(
                "بیماری سے پاک تصدیق شدہ نرسریوں سے کماد کے تنے (سمے) حاصل کریں",
                "بیج کے گنوں کو بائیو فنگسائیڈ میں ڈبو کر کاشت کریں",
                "متاثرہ کھیت کی موڈھی (راٹون) فصل نہ رکھیں اور 3 سال فصل تبدیل کریں",
                "کماد کی چھلائی کے بعد خوری کو جلا دیں اور برسات میں پانی نہ کھڑا ہونے دیں"
            ),
            preventionSindhi = listOf(
                "بيماري کان پاڪ نرسرين مان ڪمند جا ڪٽيل سم پوکيو",
                "سم پوکڻ کان اڳ پاڻي ۽ دوائن سان ڌوئي وٺو",
                "بيمار ٻنيءَ مان ٻيو سال (موڍي) فصل نه کڻو، ٻني مٽايو",
                "ڪمند جا سڪل پن ساڙي ڇڏيو ۽ برسات ۾ پاڻي ڪڍو"
            ),
            sourceReference = "Sugarcane Research Institute Faisalabad; Pakistan Sugar Mills Association (PSMA) Ag Advisory"
        )
    )

    fun getByCrop(cropType: CropType): List<KnowledgeBaseDisease> {
        return if (cropType == CropType.AUTO_DETECT) {
            diseases
        } else {
            diseases.filter { it.cropType == cropType }
        }
    }

    fun search(query: String, language: AppLanguage): List<KnowledgeBaseDisease> {
        val q = query.trim().lowercase()
        if (q.isEmpty()) return diseases
        return diseases.filter { disease ->
            disease.diseaseName.lowercase().contains(q) ||
            disease.urduName.contains(q) ||
            disease.sindhiName.contains(q) ||
            disease.scientificName.lowercase().contains(q) ||
            disease.cropType.englishName.lowercase().contains(q) ||
            disease.symptomsEnglish.any { it.lowercase().contains(q) } ||
            disease.symptomsUrdu.any { it.contains(q) } ||
            disease.symptomsSindhi.any { it.contains(q) }
        }
    }
}
