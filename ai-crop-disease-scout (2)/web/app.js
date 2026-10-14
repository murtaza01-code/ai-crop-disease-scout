(() => {
  "use strict";

  const STORAGE = {
    scans: "cropscout.web.scans.v1",
    language: "cropscout.web.language.v1",
    profile: "cropscout.web.profile.v1",
  };

  const crops = {
    cotton: { en: "Cotton", ur: "کپاس", sd: "ڪپھ", icon: "🌱" },
    wheat: { en: "Wheat", ur: "گندم", sd: "ڪڻڪ", icon: "🌾" },
    rice: { en: "Rice", ur: "چاول", sd: "چانور", icon: "🌾" },
    tomato: { en: "Tomato", ur: "ٹماٹر", sd: "ٽماٽو", icon: "🍅" },
    chili: { en: "Chili pepper", ur: "مرچ", sd: "مرچ", icon: "🌶️" },
    mango: { en: "Mango", ur: "آم", sd: "انب", icon: "🥭" },
    sugarcane: { en: "Sugarcane", ur: "کماد", sd: "ڪمند", icon: "🎋" },
  };

  const labels = {
    en: {
      home: "Overview", scanner: "New field note", history: "Field history", knowledge: "Crop library", voice: "Voice guide", profile: "Farmer profile",
      menu: "FIELD NOTEBOOK", offline: "Offline-ready", offlineText: "Your notes stay on this device.", profileRole: "Farmer · Field notebook",
      welcome: "FIELD NOTEBOOK · PAKISTAN", title: "A closer look at your crops.", tagline: "Keep field observations in one place. Explore educational examples and practical, safety-first crop guidance.",
      scan: "Start a field note", library: "Explore crop library", recorded: "Notes recorded", examples: "Sample examples", needsReview: "Needs review",
      latest: "Recent field notes", seeHistory: "See all notes", common: "In the field guide", guideSub: "Educational references to help you know what to look for.",
      newNote: "New field note", noteSub: "Choose an example to explore, or attach a photo to keep a local field note.", crop: "Crop", chooseCrop: "Choose a crop", upload: "Add a field photo", uploadHint: "Choose a photo to preview it privately in this browser. It is not uploaded or diagnosed.", choosePhoto: "Choose photo",
      sampleTitle: "Explore a sample scenario", sampleSub: "Sample results are illustrative learning examples, not an analysis of a photo.", analyze: "Show sample guidance", clear: "Clear selection",
      demoWarning: "Important: this web app does not have an image-recognition service connected. Uploaded photos are not diagnosed. Sample scenarios are labeled demonstrations only.",
      reportTitle: "Field note", example: "EDUCATIONAL SAMPLE", photoReview: "PHOTO SAVED · REVIEW NEEDED", confidence: "Example scenario · not a diagnosis",
      savePhotoReview: "Save review note", photoNotStored: "Photo not stored",
      symptoms: "What to look for", actions: "Practical next steps", prevention: "Prevention notes", safety: "Safety first",
      safetyText: "This is general early-warning guidance, not a laboratory result or a treatment prescription. Do not choose or dose chemical sprays based on this app. Ask your local agricultural extension officer.",
      save: "Save to field history", saved: "Saved to field history", print: "Print note", libraryTitle: "Crop library", librarySub: "Browse common crop concerns and their visible signs. Guidance is educational; confirm suspected problems with an extension officer.",
      searchLibrary: "Search crops, conditions or symptoms…", allCrops: "All crops", severityHigh: "Monitor closely", severityModerate: "Keep an eye out",
      historyTitle: "Field history", historySub: "Your notes are stored locally in this browser on this device.", searchHistory: "Search your field notes…", noHistory: "No field notes yet. Explore a sample scenario or save a photo for review.",
      voiceTitle: "A little help, out loud.", voiceSub: "Hear practical crop-safety reminders read in your browser language. This guide does not diagnose plants.",
      voicePlaceholder: "Ask about crop care or safety…", ask: "Ask guide", speak: "Read safety reminder", voiceIntro: "Tell me what crop you are caring for. I can share general observation and safety tips, but I cannot diagnose a plant or prescribe a treatment.",
      voiceSafe: "I can help with general crop-care guidance. Take clear photos in natural light, note when symptoms started, and compare affected plants with healthy ones. For a suspected disease or any spray decision, contact your local agricultural extension officer.",
      profileTitle: "Farmer profile", profileSub: "Keep a few field details on this device to personalize your notebook.", name: "Farmer name", district: "District / region", primaryCrop: "Main crops", saveProfile: "Save profile", profileSaved: "Profile saved on this device.",
      alertsTitle: "Local field watch", alertsSub: "A reminder to check local extension advisories; this demo has no live outbreak feed.", alertText: "No live outbreak data is connected. Check with your local agricultural extension office for current regional alerts.",
      delete: "Remove", healthy: "Healthy sample", suspected: "Example concern", review: "Review needed", date: "Recorded", citations: "Reference", emptySearch: "Nothing found. Try another search.", photoName: "Photo attached", reportNote: "Created from a browser sample scenario",
      healthyDesc: "An example of a healthy-looking leaf for comparison. This does not assess your own plant.",
      healthySymptom1: "Even green colour across the leaf", healthySymptom2: "No obvious spots, curling or unusual growth",
      healthyAction1: "Continue routine field observations.", healthyAction2: "Compare plants at different points in the field.",
    },
    ur: {
      home: "جائزہ", scanner: "نیا فیلڈ نوٹ", history: "فیلڈ ریکارڈ", knowledge: "فصلوں کی رہنمائی", voice: "آواز کی رہنمائی", profile: "کسان کا پروفائل",
      menu: "فیلڈ نوٹ بک", offline: "آف لائن استعمال", offlineText: "آپ کے نوٹس اسی آلے پر محفوظ ہیں۔", profileRole: "کسان · فیلڈ نوٹ بک",
      welcome: "فیلڈ نوٹ بک · پاکستان", title: "اپنی فصلوں کا جائزہ لیں۔", tagline: "کھیت کے مشاہدات محفوظ رکھیں۔ تعلیمی مثالیں اور محفوظ زرعی رہنمائی دیکھیں۔",
      scan: "نیا فیلڈ نوٹ", library: "فصلوں کی رہنمائی", recorded: "محفوظ نوٹس", examples: "نمونہ مثالیں", needsReview: "جائزہ درکار",
      latest: "حالیہ فیلڈ نوٹس", seeHistory: "تمام نوٹس", common: "زرعی رہنمائی", guideSub: "علامات سمجھنے کے لیے تعلیمی معلومات۔",
      newNote: "نیا فیلڈ نوٹ", noteSub: "نمونہ دیکھیں یا تصویر شامل کرکے مقامی فیلڈ نوٹ بنائیں۔", crop: "فصل", chooseCrop: "فصل منتخب کریں", upload: "کھیت کی تصویر شامل کریں",
      uploadHint: "تصویر اسی براؤزر میں دیکھی جائے گی؛ اسے اپ لوڈ یا تشخیص نہیں کیا جائے گا۔", choosePhoto: "تصویر منتخب کریں",
      sampleTitle: "نمونہ صورتحال دیکھیں", sampleSub: "نمونہ صرف تعلیمی مثال ہے، تصویر کا تجزیہ نہیں۔", analyze: "نمونہ رہنمائی دیکھیں", clear: "انتخاب ختم کریں",
      demoWarning: "اہم: اس ویب ایپ میں تصویر پہچاننے کی سہولت منسلک نہیں۔ اپ لوڈ کی گئی تصویر کی تشخیص نہیں ہوتی۔ نمونے صرف تعلیمی مثالیں ہیں۔",
      savePhotoReview: "جائزے کا نوٹ محفوظ کریں", photoNotStored: "تصویر محفوظ نہیں",
      reportTitle: "فیلڈ نوٹ", example: "تعلیمی نمونہ", photoReview: "تصویر محفوظ · جائزہ درکار", confidence: "مثالی صورتحال · تشخیص نہیں",
      symptoms: "کیا دیکھیں", actions: "عملی اگلے اقدامات", prevention: "بچاؤ کی معلومات", safety: "حفاظت پہلے",
      safetyText: "یہ ابتدائی عمومی رہنمائی ہے، لیبارٹری رپورٹ یا علاج کا نسخہ نہیں۔ اس ایپ کی بنیاد پر کیمیائی اسپرے کا انتخاب یا مقدار طے نہ کریں۔ مقامی زرعی افسر سے مشورہ کریں۔",
      save: "فیلڈ ریکارڈ میں محفوظ کریں", saved: "فیلڈ ریکارڈ میں محفوظ", print: "نوٹ پرنٹ کریں", libraryTitle: "فصلوں کی رہنمائی", librarySub: "عام فصلوں کی علامات دیکھیں۔ مشتبہ بیماری کی تصدیق زرعی افسر سے کریں۔",
      searchLibrary: "فصل، بیماری یا علامات تلاش کریں…", allCrops: "تمام فصلیں", severityHigh: "خاص نگرانی کریں", severityModerate: "نظر رکھیں",
      historyTitle: "فیلڈ ریکارڈ", historySub: "نوٹس اس آلے کے براؤزر میں مقامی طور پر محفوظ ہیں۔", searchHistory: "فیلڈ نوٹس تلاش کریں…", noHistory: "ابھی کوئی نوٹ نہیں۔ نمونہ دیکھیں یا جائزے کے لیے تصویر محفوظ کریں۔",
      voiceTitle: "آواز میں رہنمائی", voiceSub: "براؤزر میں محفوظ زرعی یاددہانیاں سنیں۔ یہ رہنمائی پودوں کی تشخیص نہیں کرتی۔",
      voicePlaceholder: "فصل کی دیکھ بھال یا حفاظت کے بارے میں پوچھیں…", ask: "رہنمائی پوچھیں", speak: "حفاظتی یاددہانی سنیں",
      voiceIntro: "فصل کے بارے میں بتائیں۔ میں عمومی مشاہدے اور حفاظت کی رہنمائی دے سکتا ہوں، لیکن پودے کی تشخیص یا علاج تجویز نہیں کر سکتا۔",
      voiceSafe: "میں فصل کی عمومی دیکھ بھال کے بارے میں بتا سکتا ہوں۔ قدرتی روشنی میں واضح تصاویر لیں، علامات شروع ہونے کا وقت نوٹ کریں، اور متاثرہ پودوں کا صحت مند پودوں سے موازنہ کریں۔ بیماری کے شبہے یا اسپرے کے فیصلے کے لیے مقامی زرعی افسر سے رابطہ کریں۔",
      profileTitle: "کسان کا پروفائل", profileSub: "اپنی چند معلومات اس آلے پر محفوظ کریں۔", name: "کسان کا نام", district: "ضلع / علاقہ", primaryCrop: "اہم فصلیں",
      saveProfile: "پروفائل محفوظ کریں", profileSaved: "پروفائل اس آلے پر محفوظ ہے۔", alertsTitle: "مقامی زرعی معلومات",
      alertsSub: "علاقائی معلومات کے لیے مقامی زرعی محکمے سے رابطہ کریں۔", alertText: "اس نمونے میں تازہ علاقائی بیماریوں کا ڈیٹا دستیاب نہیں۔ مقامی زرعی افسر سے موجودہ صورتحال معلوم کریں۔",
      delete: "حذف کریں", healthy: "صحت مند نمونہ", suspected: "مثالی خدشہ", review: "جائزہ درکار", date: "ریکارڈ", citations: "حوالہ",
      emptySearch: "کچھ نہیں ملا۔ دوسرا لفظ آزمائیں۔", photoName: "تصویر منسلک", reportNote: "براؤزر کے نمونہ منظرنامے سے بنایا گیا",
      healthyDesc: "صحت مند نظر آنے والے پتے کی تقابلی مثال۔ اس سے آپ کے پودے کا جائزہ نہیں لیا جاتا۔",
      healthySymptom1: "پتے پر یکساں سبز رنگ", healthySymptom2: "واضح دھبے، بل یا غیر معمولی بڑھوتری نہیں",
      healthyAction1: "کھیت کا معمول کے مطابق مشاہدہ جاری رکھیں۔", healthyAction2: "کھیت کے مختلف حصوں کے پودوں کا موازنہ کریں۔",
    },
    sd: {
      home: "جائزو", scanner: "نئون ميداني نوٽ", history: "ميداني رڪارڊ", knowledge: "فصلن جي رهنمائي", voice: "آوازي رهنمائي", profile: "هاري جو پروفائل",
      menu: "ميداني نوٽ بڪ", offline: "آف لائن تيار", offlineText: "توهان جا نوٽ هن ڊوائيس تي رهندا.", profileRole: "هاري · ميداني نوٽ بڪ",
      welcome: "ميداني نوٽ بڪ · پاڪستان", title: "پنهنجن فصلن تي نظر رکو.", tagline: "ٻنيءَ جا مشاهدا محفوظ ڪريو. تعليمي مثال ۽ حفاظت واري زرعي رهنمائي ڏسو.",
      scan: "نئون ميداني نوٽ", library: "فصلن جي رهنمائي", recorded: "محفوظ نوٽ", examples: "نموني مثال", needsReview: "جائزو گهربل",
      latest: "تازا ميداني نوٽ", seeHistory: "سڀ نوٽ", common: "زرعي رهنمائي", guideSub: "علامتن کي سمجهڻ لاءِ تعليمي ڄاڻ.",
      newNote: "نئون ميداني نوٽ", noteSub: "نمونو ڏسو يا تصوير شامل ڪري مقامي نوٽ رکو.", crop: "فصل", chooseCrop: "فصل چونڊيو", upload: "ٻنيءَ جي تصوير شامل ڪريو",
      uploadHint: "تصوير هن برائوزر ۾ ئي ڏسي سگهجي ٿي؛ اها اپلوڊ يا تشخيص نه ٿيندي.", choosePhoto: "تصوير چونڊيو",
      sampleTitle: "نموني صورتحال ڏسو", sampleSub: "نموني نتيجو رڳو تعليمي مثال آهي، تصوير جو تجزيو ناهي.", analyze: "نموني رهنمائي ڏسو", clear: "چونڊ ختم ڪريو",
      demoWarning: "اهم: هن ويب ايپ ۾ تصوير سڃاڻڻ واري سروس ناهي. اپلوڊ ٿيل تصوير جي تشخيص نٿي ٿئي. نمونا رڳو تعليمي مثال آهن.",
      savePhotoReview: "جائزي جو نوٽ محفوظ ڪريو", photoNotStored: "تصوير محفوظ ناهي",
      reportTitle: "ميداني نوٽ", example: "تعليمي نمونو", photoReview: "تصوير محفوظ · جائزو گهربل", confidence: "مثالي صورتحال · تشخيص ناهي",
      symptoms: "ڇا ڏسجي", actions: "عملي ايندڙ قدم", prevention: "بچاءَ جون ڳالهيون", safety: "حفاظت پهرين",
      safetyText: "هي عام ابتدائي رهنمائي آهي، ليبارٽري نتيجو يا علاج جو نسخو ناهي. هن ايپ جي بنياد تي ڪيميائي اسپري يا مقدار جو فيصلو نه ڪريو. مقامي زرعي آفيسر سان صلاح ڪريو.",
      save: "ميداني رڪارڊ ۾ محفوظ ڪريو", saved: "ميداني رڪارڊ ۾ محفوظ ٿيو", print: "نوٽ ڇاپيو", libraryTitle: "فصلن جي رهنمائي", librarySub: "عام فصلن جون نشانيون ڏسو. بيماريءَ جي شڪ جي تصديق زرعي آفيسر کان ڪريو.",
      searchLibrary: "فصل، بيماري يا علامتون ڳوليو…", allCrops: "سڀ فصل", severityHigh: "خاص نظر رکو", severityModerate: "نظر رکو",
      historyTitle: "ميداني رڪارڊ", historySub: "نوٽ هن ڊوائيس جي برائوزر ۾ ئي محفوظ ٿين ٿا.", searchHistory: "ميداني نوٽ ڳوليو…", noHistory: "اڃا ڪو نوٽ ناهي. نمونو ڏسو يا جائزي لاءِ تصوير محفوظ ڪريو.",
      voiceTitle: "آواز ۾ رهنمائي", voiceSub: "برائوزر ۾ عام حفاظتي ڳالهه ٻولهه ٻڌو. هي رهنمائي ٻوٽن جي تشخيص نٿي ڪري.",
      voicePlaceholder: "فصل جي سنڀال يا حفاظت بابت پڇو…", ask: "رهنمائي پڇو", speak: "حفاظتي ياد ڏيارڻ ٻڌو",
      voiceIntro: "فصل بابت ٻڌايو. مان عام مشاهدي ۽ حفاظت ۾ مدد ڪري سگهان ٿو، پر ٻوٽي جي تشخيص يا علاج نٿو ٻڌائي سگهان۔",
      voiceSafe: "مان فصل جي عام سنڀال بابت مدد ڪري سگهان ٿو. قدرتي روشنيءَ ۾ صاف تصويرون وٺو، علامتون شروع ٿيڻ جو وقت نوٽ ڪريو، ۽ متاثر ٻوٽن جو صحتمند ٻوٽن سان ڀيٽ ڪريو. بيماريءَ جي شڪ يا اسپري جي فيصلي لاءِ مقامي زرعي آفيسر سان رابطو ڪريو.",
      profileTitle: "هاري جو پروفائل", profileSub: "پنهنجي ٻنيءَ جون ٿوريون ڄاڻون هن ڊوائيس تي رکو.", name: "هاري جو نالو", district: "ضلعو / علائقو", primaryCrop: "مکيه فصل",
      saveProfile: "پروفائل محفوظ ڪريو", profileSaved: "پروفائل هن ڊوائيس تي محفوظ ٿيو.", alertsTitle: "مقامي زرعي خبرون",
      alertsSub: "علائقي جي خبرن لاءِ مقامي زرعي کاتي سان رابطو ڪريو.", alertText: "هن نموني ۾ نئين علائقائي وبائن جي ڄاڻ ناهي. مقامي زرعي آفيسر کان موجوده صورتحال پڇو.",
      delete: "هٽايو", healthy: "صحتمند نمونو", suspected: "مثالي خدشو", review: "جائزو گهربل", date: "رڪارڊ", citations: "حوالو",
      emptySearch: "ڪجهه نه مليو. ٻيو لفظ لکو.", photoName: "تصوير شامل", reportNote: "برائوزر جي نموني صورتحال مان ٺاهيل",
      healthyDesc: "صحتمند پن جو مثال، ڀيٽ ڪرڻ لاءِ. ان سان توهان جي پنهنجي ٻوٽي جي جاچ نٿي ٿئي.",
      healthySymptom1: "پن جو رنگ يڪسان سائو هجي", healthySymptom2: "واضح داغ، مڙڻ يا غيرمعمولي واڌ نه هجي",
      healthyAction1: "ٻنيءَ جو معمول وارو مشاهدو جاري رکو.", healthyAction2: "ٻنيءَ جي مختلف حصن جي ٻوٽن جو ڀيٽ ڪريو.",
    },
  };

  const diseases = [
    {
      id: "cotton-curl", crop: "cotton", title: "Cotton Leaf Curl Virus (CLCuV)", scientific: "Begomovirus complex · whitefly-transmitted",
      severity: "high", source: "CABI PlantwisePlus; PARC Cotton Research Institute",
      symptoms: ["Leaf margins curl upward or downward", "Veins become thick and dark green", "Small leaf-like outgrowths may appear beneath leaves"],
      prevention: ["Plant certified tolerant varieties where locally recommended.", "Monitor whiteflies and manage weeds around field borders.", "Seek local extension advice if symptoms spread."],
    },
    {
      id: "cotton-blight", crop: "cotton", title: "Bacterial Blight (Angular Leaf Spot)", scientific: "Xanthomonas citri pv. malvacearum",
      severity: "moderate", source: "FAO Crop Protection Compendium; CCRI",
      symptoms: ["Angular, water-soaked spots follow leaf veins", "Spots may darken as they age", "Stem or boll lesions can also occur"],
      prevention: ["Use certified, disease-free seed.", "Rotate crops with non-host crops when practical.", "Avoid prolonged leaf wetness and remove crop residue after harvest."],
    },
    {
      id: "wheat-rust", crop: "wheat", title: "Yellow (Stripe) Rust", scientific: "Puccinia striiformis f. sp. tritici",
      severity: "high", source: "CABI PlantwisePlus; FAO Crop Protection Compendium",
      symptoms: ["Yellow-orange pustules appear in stripes along leaf veins", "Powdery spores may rub off onto fingertips", "Early leaf drying can follow"],
      prevention: ["Choose locally recommended resistant varieties.", "Check fields regularly in cool, humid weather.", "Ask an extension officer to verify suspected rust promptly."],
    },
    {
      id: "wheat-smut", crop: "wheat", title: "Loose Smut", scientific: "Ustilago tritici",
      severity: "moderate", source: "FAO Crop Protection Compendium",
      symptoms: ["Grain heads may be replaced by dark, powdery spores", "Affected heads can emerge earlier than healthy ones", "Spores disperse as the crop flowers"],
      prevention: ["Use certified seed and locally recommended varieties.", "Ask a qualified seed or extension specialist about prevention before sowing."],
    },
    {
      id: "rice-blight", crop: "rice", title: "Bacterial Leaf Blight (BLB)", scientific: "Xanthomonas oryzae pv. oryzae",
      severity: "high", source: "CABI PlantwisePlus; FAO Crop Protection Compendium",
      symptoms: ["Water-soaked streaks start at leaf tips or edges", "Lesions extend along the leaf and turn straw-coloured", "Affected leaves may wilt"],
      prevention: ["Use clean seed and locally recommended resistant varieties.", "Keep field water and crop residue management aligned with local advice.", "Consult extension services when symptoms appear across a field."],
    },
    {
      id: "rice-blast", crop: "rice", title: "Rice Blast", scientific: "Magnaporthe oryzae",
      severity: "high", source: "CABI PlantwisePlus; FAO Crop Protection Compendium",
      symptoms: ["Leaf lesions may be spindle-shaped with pale centres", "Lesions can develop dark borders", "Nodes or panicles may also be affected"],
      prevention: ["Grow locally recommended resistant varieties.", "Avoid excessive nitrogen and keep balanced crop nutrition.", "Seek local guidance if neck or panicle symptoms appear."],
    },
    {
      id: "tomato-blight", crop: "tomato", title: "Early Blight", scientific: "Alternaria solani",
      severity: "moderate", source: "CABI PlantwisePlus",
      symptoms: ["Brown leaf spots may show concentric rings", "Yellowing can develop around older spots", "Lower leaves may be affected first"],
      prevention: ["Rotate crops and remove infected plant debris.", "Water near the soil rather than keeping leaves wet.", "Ask local extension staff to confirm look-alike leaf spots."],
    },
    {
      id: "chili-anthracnose", crop: "chili", title: "Anthracnose (Fruit Rot & Dieback)", scientific: "Colletotrichum spp.",
      severity: "high", source: "CABI PlantwisePlus",
      symptoms: ["Sunken dark lesions can appear on fruit", "Dark spots may show concentric markings", "Twigs or shoots may develop dieback"],
      prevention: ["Use healthy seed and remove affected fruit from the field.", "Avoid handling wet plants where possible.", "Confirm the cause with a local extension specialist."],
    },
    {
      id: "mango-anthracnose", crop: "mango", title: "Mango Anthracnose", scientific: "Colletotrichum gloeosporioides species complex",
      severity: "high", source: "CABI PlantwisePlus",
      symptoms: ["Dark, irregular leaf spots may appear", "Spots can enlarge during wet conditions", "Flowers and fruit may also be affected"],
      prevention: ["Prune for airflow using clean tools.", "Remove infected fallen plant material.", "Seek local advice, especially before flowering."],
    },
    {
      id: "sugarcane-red-rot", crop: "sugarcane", title: "Red Rot of Sugarcane", scientific: "Colletotrichum falcatum",
      severity: "high", source: "CABI PlantwisePlus",
      symptoms: ["Internal cane tissue can show red patches with pale cross-bands", "Affected stalks may dry or show a sour smell", "Leaf midribs may discolour"],
      prevention: ["Plant healthy, certified setts from a reliable source.", "Remove diseased clumps and avoid carrying infected material.", "Consult local extension services about resistant varieties."],
    },
  ];

  const samples = [
    { id: "cotton-curl", crop: "cotton", name: "Cotton · leaf curl", sub: "Curled margins · thick veins", icon: "🍃" },
    { id: "cotton-healthy", crop: "cotton", name: "Cotton · healthy leaf", sub: "A healthy-looking comparison", icon: "🌿", healthy: true },
    { id: "wheat-rust", crop: "wheat", name: "Wheat · stripe rust", sub: "Yellow-orange stripe example", icon: "🌾" },
    { id: "tomato-blight", crop: "tomato", name: "Tomato · early blight", sub: "Concentric spot example", icon: "🍅" },
    { id: "rice-blast", crop: "rice", name: "Rice · blast", sub: "Spindle-shaped spot example", icon: "🌱" },
    { id: "mango-anthracnose", crop: "mango", name: "Mango · anthracnose", sub: "Dark leaf spot example", icon: "🥭" },
  ];

  const savedLanguage = readJSON(STORAGE.language, "en");
  const state = {
    page: "home",
    language: ["en", "ur", "sd"].includes(savedLanguage) ? savedLanguage : "en",
    scans: Array.isArray(readJSON(STORAGE.scans, [])) ? readJSON(STORAGE.scans, []) : [],
    profile: { name: "Field farmer", district: "", primaryCrop: "Cotton, wheat & mango", ...readJSON(STORAGE.profile, {}) },
    selectedCrop: "cotton",
    selectedSample: null,
    photoName: "",
    photoUrl: "",
    result: null,
    isSaved: false,
    librarySearch: "",
    libraryCrop: "all",
    historySearch: "",
    toast: "",
  };

  function readJSON(key, fallback) {
    try {
      const value = JSON.parse(localStorage.getItem(key));
      return value ?? fallback;
    } catch (error) {
      console.warn(`Could not read ${key} from local storage.`, error);
      return fallback;
    }
  }

  function saveJSON(key, value) {
    try {
      localStorage.setItem(key, JSON.stringify(value));
    } catch (error) {
      console.error(`Could not save ${key} to local storage.`, error);
      notify("This browser could not save the change on this device.");
    }
  }

  const t = (key) => labels[state.language][key] ?? labels.en[key] ?? key;
  const cropName = (id) => crops[id]?.[state.language] ?? crops[id]?.en ?? id;
  const cropIcon = (id) => crops[id]?.icon ?? "🌿";
  const safe = (value) => String(value ?? "").replace(/[&<>"']/g, (char) => ({ "&": "&amp;", "<": "&lt;", ">": "&gt;", '"': "&quot;", "'": "&#39;" })[char]);
  const escAttr = safe;
  const diseaseFor = (id) => diseases.find((disease) => disease.id === id);
  const dateText = (value) => {
    const date = new Date(value);
    if (Number.isNaN(date.getTime())) return "";
    return new Intl.DateTimeFormat(state.language === "sd" ? "ur-PK" : state.language === "ur" ? "ur-PK" : "en", { dateStyle: "medium", timeStyle: "short" }).format(date);
  };
  const pageLabel = (page) => t(page);

  function notify(message) {
    state.toast = message;
    render();
    window.setTimeout(() => {
      state.toast = "";
      render();
    }, 2300);
  }

  function navItem(page, icon) {
    return `<button class="nav-item ${state.page === page ? "active" : ""}" data-page="${page}" aria-current="${state.page === page ? "page" : "false"}"><span class="nav-icon" aria-hidden="true">${icon}</span><span>${safe(pageLabel(page))}</span></button>`;
  }

  function shell(content) {
    const profileName = state.profile.name || "Field farmer";
    return `<div class="shell">
      <aside class="sidebar">
        <a class="brand" href="#" data-page="home" aria-label="CropScout home"><span class="brand-icon">⌁</span><span><span class="brand-name">CropScout</span><span class="brand-caption">field notebook</span></span></a>
        <div class="nav-label">${safe(t("menu"))}</div>
        <nav class="nav-list" aria-label="Main navigation">
          ${navItem("home", "⌂")}
          ${navItem("scanner", "◎")}
          ${navItem("history", "▤")}
          ${navItem("knowledge", "▧")}
          ${navItem("voice", "◖")}
        </nav>
        <div class="side-bottom">
          <div class="offline-badge"><span class="online-dot"></span><span><strong>${safe(t("offline"))}</strong><br>${safe(t("offlineText"))}</span></div>
          <div class="profile-mini" data-page="profile" role="button" tabindex="0"><span class="avatar">👩🏽‍🌾</span><span><span class="profile-name">${safe(profileName)}</span><span class="profile-place">${safe(state.profile.district || t("profileRole"))}</span></span></div>
        </div>
      </aside>
      <main class="main">
        <header class="topbar">
          <div class="crumb"><strong>CropScout</strong> <span aria-hidden="true">/</span> ${safe(pageLabel(state.page))}</div>
          <div class="top-actions">
            <label class="sr-only" for="language">Language</label>
            <select class="language-select" id="language" aria-label="Language">
              <option value="en" ${state.language === "en" ? "selected" : ""}>English</option>
              <option value="ur" ${state.language === "ur" ? "selected" : ""}>اردو</option>
              <option value="sd" ${state.language === "sd" ? "selected" : ""}>سنڌي</option>
            </select>
            <button class="top-profile" data-page="profile" aria-label="${safe(t("profile"))}">👩🏽‍🌾</button>
          </div>
        </header>
        <div class="content">${content}</div>
      </main>
      <nav class="mobile-nav" aria-label="Main navigation">${navItem("home", "⌂")}${navItem("scanner", "◎")}${navItem("history", "▤")}${navItem("knowledge", "▧")}${navItem("voice", "◖")}</nav>
      ${state.toast ? `<div class="toast" role="status">${safe(state.toast)}</div>` : ""}
    </div>`;
  }

  function statCard(icon, number, label, color = "") {
    return `<div class="stat-card"><span class="stat-icon ${color}" aria-hidden="true">${icon}</span><span><span class="stat-label">${safe(label)}</span><span class="stat-value">${number}</span></span></div>`;
  }

  function scanStatus(scan) {
    if (scan.status === "healthy") return `<span class="pill">${safe(t("healthy"))}</span>`;
    if (scan.status === "example") return `<span class="pill danger">${safe(t("suspected"))}</span>`;
    return `<span class="pill warn">${safe(t("review"))}</span>`;
  }

  function shortScanRow(scan) {
    return `<div class="scan-row"><span class="crop-avatar">${cropIcon(scan.crop)}</span><span class="scan-row-main"><span class="scan-row-title">${safe(scan.title)}</span><span class="scan-row-date">${safe(dateText(scan.date))}</span></span>${scanStatus(scan)}</div>`;
  }

  function homePage() {
    const count = state.scans.length;
    const reviews = state.scans.filter((scan) => scan.status === "review").length;
    const sampleCount = state.scans.filter((scan) => scan.status !== "review").length;
    return `<div class="eyebrow">${safe(t("welcome"))}</div>
      <section class="home-hero">
        <div class="hero-content">
          <span class="hero-kicker"><span aria-hidden="true">✳</span> ${safe(t("offline"))}</span>
          <h1>${safe(t("title"))}</h1><p>${safe(t("tagline"))}</p>
          <div class="hero-actions"><button class="button light" data-page="scanner">${safe(t("scan"))} <span aria-hidden="true">↗</span></button><button class="button light" data-page="knowledge">${safe(t("library"))}</button></div>
        </div>
      </section>
      <section class="stats-grid" aria-label="${safe(t("historyTitle"))}">
        ${statCard("▤", count, t("recorded"))}${statCard("✳", sampleCount, t("examples"), "blue")}${statCard("◌", reviews, t("needsReview"), "amber")}
      </section>
      <div class="two-col">
        <section class="panel panel-pad">
          <div class="panel-title"><h2>${safe(t("latest"))}</h2><button class="text-link" data-page="history">${safe(t("seeHistory"))} →</button></div>
          ${state.scans.length ? [...state.scans].slice(0, 4).map(shortScanRow).join("") : `<div class="empty-state">${safe(t("noHistory"))}<br><br><button class="button secondary small" data-page="scanner">${safe(t("scan"))}</button></div>`}
        </section>
        <section class="panel panel-pad">
          <div class="panel-title"><h2>${safe(t("common"))}</h2><span>01 — 03</span></div>
          ${diseases.slice(0, 3).map((disease) => `<div class="disease-mini"><div>${cropIcon(disease.crop)} &nbsp;${safe(disease.title)}</div><p>${safe(disease.symptoms[0])}</p></div>`).join("")}
          <button class="text-link" data-page="knowledge">${safe(t("library"))} →</button>
        </section>
      </div>
      <section class="panel panel-pad" style="margin-top:14px">
        <div class="panel-title"><h2>${safe(t("alertsTitle"))}</h2><span>ℹ</span></div>
        <div class="notice"><span aria-hidden="true">ⓘ</span><span>${safe(t("alertText"))}</span></div>
      </section>
      <p class="footer-note">${safe(t("safetyText"))}</p>`;
  }

  function cropOptions(selected, all = false) {
    return `${all ? `<option value="all">${safe(t("allCrops"))}</option>` : ""}${Object.entries(crops).map(([id, crop]) => `<option value="${id}" ${id === selected ? "selected" : ""}>${crop.icon} ${safe(cropName(id))}</option>`).join("")}`;
  }

  function scannerPage() {
    const selected = samples.find((sample) => sample.id === state.selectedSample);
    const selectedDisease = diseaseFor(state.selectedSample);
    const sampleOptions = [
      ...samples,
      ...diseases
        .filter((disease) => !samples.some((sample) => sample.id === disease.id))
        .map((disease) => ({ id: disease.id, crop: disease.crop, name: `${cropName(disease.crop)} · ${disease.title}`, sub: disease.symptoms[0], icon: cropIcon(disease.crop) })),
    ];
    const preview = state.photoUrl
      ? `<img class="photo-preview" src="${escAttr(state.photoUrl)}" alt="Selected leaf photo preview">`
      : `<div><span class="dropzone-icon">▧</span><h3>${safe(t("upload"))}</h3><p>${safe(t("uploadHint"))}</p><label class="button secondary small" for="photo-input">${safe(t("choosePhoto"))}</label></div>`;
    return `<div class="page-intro"><div class="page-intro-copy"><div class="eyebrow">01 / ${safe(t("scanner"))}</div><h1>${safe(t("newNote"))}</h1><p class="subtitle">${safe(t("noteSub"))}</p></div></div>
      <div class="notice red" style="margin-bottom:15px"><span aria-hidden="true">ⓘ</span><span>${safe(t("demoWarning"))}</span></div>
      <div class="scanner-layout">
        <section class="panel panel-pad">
          <div class="panel-title"><h2>${safe(t("upload"))}</h2><span>1 / 2</span></div>
          <div class="dropzone ${state.photoUrl ? "has-image" : ""}" id="dropzone">${preview}</div>
          <input class="sr-only" type="file" id="photo-input" accept="image/*" aria-label="${safe(t("choosePhoto"))}">
          ${state.photoName ? `<div class="report-meta" style="margin-top:9px">${safe(t("photoName"))}: ${safe(state.photoName)}</div>` : ""}
          <label class="form-label" style="margin-top:17px">${safe(t("crop"))}<select class="select-field" id="crop-select"><option value="">${safe(t("chooseCrop"))}</option>${cropOptions(state.selectedCrop)}</select></label>
          <div class="toolbar" style="margin-top:14px;margin-bottom:0"><button class="button secondary small" data-action="clear-photo">${safe(t("clear"))}</button><button class="button secondary small" data-action="save-photo-review" ${state.photoUrl && state.selectedCrop ? "" : "disabled"}>${safe(t("savePhotoReview"))}</button></div>
        </section>
        <section class="panel panel-pad">
          <div class="panel-title"><h2>${safe(t("sampleTitle"))}</h2><span>2 / 2</span></div>
          <p class="subtitle" style="margin:-7px 0 12px">${safe(t("sampleSub"))}</p>
          <div class="sample-grid">${sampleOptions.map((sample) => `<button class="sample-card ${state.selectedSample === sample.id ? "selected" : ""}" data-sample="${sample.id}"><span class="sample-emoji">${sample.icon}</span><span><b>${safe(sample.name)}</b><small>${safe(sample.sub)}</small></span></button>`).join("")}</div>
          <div style="margin-top:13px">${selected || selectedDisease ? `<div class="notice green"><span>✓</span><span>${safe(selected?.name || selectedDisease.title)} — ${safe(t("confidence"))}</span></div>` : `<div class="notice"><span>↳</span><span>${safe(t("sampleSub"))}</span></div>`}</div>
          <div class="toolbar" style="margin-top:13px;margin-bottom:0"><button class="button" data-action="show-result" ${selected || selectedDisease ? "" : "disabled"}>${safe(t("analyze"))} <span aria-hidden="true">→</span></button></div>
        </section>
      </div>
      ${state.result ? resultCard(state.result) : ""}
      <p class="footer-note">${safe(t("safetyText"))}</p>`;
  }

  function resultCard(result) {
    const disease = result.diseaseId ? diseaseFor(result.diseaseId) : null;
    const isPhoto = result.status === "review";
    const title = isPhoto ? t("review") : disease ? disease.title : t("healthy");
    const symptoms = disease ? disease.symptoms : [t("healthySymptom1"), t("healthySymptom2")];
    const actions = disease ? disease.prevention.slice(0, 3) : [t("healthyAction1"), t("healthyAction2")];
    return `<section class="panel panel-pad scan-report" id="scan-report" aria-live="polite">
      <div class="report-heading"><div><div class="eyebrow">${safe(isPhoto ? t("photoReview") : t("example"))}</div><h2>${safe(t("reportTitle"))} · ${safe(title)}</h2><p class="report-meta">${safe(cropName(result.crop))} · ${safe(t("confidence"))}</p></div><span class="pill ${isPhoto ? "warn" : result.status === "healthy" ? "" : "danger"}">${safe(isPhoto ? t("review") : result.status === "healthy" ? t("healthy") : t("suspected"))}</span></div>
      ${isPhoto ? `${result.photoUrl ? `<img class="photo-preview" src="${escAttr(result.photoUrl)}" alt="Selected leaf photo preview" style="margin:14px 0">` : ""}<div class="notice red" style="margin-top:12px"><span>ⓘ</span><span>${safe(t("demoWarning"))} ${safe(result.photoName || "")}</span></div>` : `<div class="report-section" style="margin-top:13px"><h3>${safe(t("symptoms"))}</h3><ul>${symptoms.map((item) => `<li>${safe(item)}</li>`).join("")}</ul></div><div class="report-section"><h3>${safe(t("prevention"))}</h3><ul>${actions.map((item) => `<li>${safe(item)}</li>`).join("")}</ul></div>${disease ? `<div class="report-meta">${safe(t("citations"))}: ${safe(disease.source)}</div>` : ""}`}
      <div class="notice red" style="margin-top:13px"><span>!</span><span><strong>${safe(t("safety"))}</strong>${safe(t("safetyText"))}</span></div>
      <div class="toolbar no-print" style="margin:14px 0 0"><button class="button" data-action="save-result" ${state.isSaved ? "disabled" : ""}>${safe(state.isSaved ? t("saved") : t("save"))}</button><button class="button secondary" data-action="print">${safe(t("print"))}</button></div>
    </section>`;
  }

  function knowledgePage() {
    const query = state.librarySearch.trim().toLocaleLowerCase();
    const results = diseases.filter((disease) => {
      const cropMatches = state.libraryCrop === "all" || disease.crop === state.libraryCrop;
      const haystack = `${disease.title} ${disease.crop} ${disease.scientific} ${disease.symptoms.join(" ")}`.toLocaleLowerCase();
      return cropMatches && (!query || haystack.includes(query));
    });
    return `<div class="page-intro"><div class="page-intro-copy"><div class="eyebrow">02 / ${safe(t("knowledge"))}</div><h1>${safe(t("libraryTitle"))}</h1><p class="subtitle">${safe(t("librarySub"))}</p></div></div>
      <div class="toolbar"><input class="field" id="library-search" type="search" value="${escAttr(state.librarySearch)}" placeholder="${safe(t("searchLibrary"))}" aria-label="${safe(t("searchLibrary"))}"><select class="select-field" id="library-crop" aria-label="${safe(t("crop"))}">${cropOptions(state.libraryCrop, true)}</select></div>
      ${results.length ? `<div class="knowledge-grid">${results.map((disease) => `<article class="knowledge-card"><div class="knowledge-card-top"><div><h3>${cropIcon(disease.crop)} ${safe(disease.title)}</h3><div class="scientific">${safe(disease.scientific)}</div></div><span class="pill ${disease.severity === "high" ? "danger" : "warn"}">${safe(disease.severity === "high" ? t("severityHigh") : t("severityModerate"))}</span></div><p><strong>${safe(t("symptoms"))}:</strong> ${safe(disease.symptoms.join(" · "))}</p><p><strong>${safe(t("prevention"))}:</strong> ${safe(disease.prevention[0])}</p><button class="text-link" data-action="open-sample" data-disease="${disease.id}">${safe(t("sampleTitle"))} →</button></article>`).join("")}</div>` : `<div class="empty-state">${safe(t("emptySearch"))}</div>`}
      <p class="footer-note">${safe(t("safetyText"))}</p>`;
  }

  function historyPage() {
    const query = state.historySearch.trim().toLocaleLowerCase();
    const filtered = state.scans.filter((scan) => `${scan.title} ${scan.crop} ${scan.photoName || ""}`.toLocaleLowerCase().includes(query));
    return `<div class="page-intro"><div class="page-intro-copy"><div class="eyebrow">03 / ${safe(t("history"))}</div><h1>${safe(t("historyTitle"))}</h1><p class="subtitle">${safe(t("historySub"))}</p></div>${state.scans.length ? `<button class="button danger small no-print" data-action="clear-history">${safe(t("delete"))} all</button>` : ""}</div>
      <div class="toolbar"><input class="field" type="search" id="history-search" value="${escAttr(state.historySearch)}" placeholder="${safe(t("searchHistory"))}" aria-label="${safe(t("searchHistory"))}"></div>
      <div class="history-list">${filtered.length ? filtered.map((scan) => `<article class="history-card"><span class="crop-avatar">${cropIcon(scan.crop)}</span><span class="scan-row-main"><strong class="scan-row-title">${safe(scan.title)}</strong><span class="history-detail">${safe(cropName(scan.crop))} · ${safe(t("date"))}: ${safe(dateText(scan.date))}${scan.photoName ? ` · ${safe(t("photoNotStored"))}: ${safe(scan.photoName)}` : ""}</span></span>${scanStatus(scan)}<button class="button ghost small no-print" data-action="delete-scan" data-id="${escAttr(scan.id)}" aria-label="${safe(t("delete"))} ${safe(scan.title)}">${safe(t("delete"))}</button></article>`).join("") : `<div class="empty-state">${safe(state.scans.length ? t("emptySearch") : t("noHistory"))}</div>`}</div>
      <p class="footer-note">${safe(t("safetyText"))}</p>`;
  }

  function voicePage() {
    return `<div class="page-intro"><div><div class="eyebrow">04 / ${safe(t("voice"))}</div><h1>${safe(t("voiceTitle"))}</h1><p class="subtitle">${safe(t("voiceSub"))}</p></div></div>
      <section class="panel voice-box"><div class="voice-orb" aria-hidden="true">◖</div><h2>${safe(t("voice"))}</h2><p class="subtitle">${safe(t("voiceSub"))}</p>
        <div class="toolbar"><input class="field" id="voice-question" type="text" placeholder="${safe(t("voicePlaceholder"))}"><button class="button" data-action="ask-voice">${safe(t("ask"))}</button></div>
        <div class="toolbar" style="justify-content:center"><button class="button secondary" data-action="speak">${safe(t("speak"))}　🔊</button></div>
        <div class="voice-response" id="voice-response" aria-live="polite">${safe(t("voiceIntro"))}</div>
      </section><div class="notice green"><span>ⓘ</span><span>${safe(t("demoWarning"))}</span></div>`;
  }

  function profilePage() {
    return `<div class="page-intro"><div><div class="eyebrow">05 / ${safe(t("profile"))}</div><h1>${safe(t("profileTitle"))}</h1><p class="subtitle">${safe(t("profileSub"))}</p></div></div>
      <div class="profile-grid"><section class="panel panel-pad"><form id="profile-form" class="form-stack">
        <label class="form-label">${safe(t("name"))}<input class="field" name="name" maxlength="80" value="${escAttr(state.profile.name)}"></label>
        <label class="form-label">${safe(t("district"))}<input class="field" name="district" maxlength="100" value="${escAttr(state.profile.district)}"></label>
        <label class="form-label">${safe(t("primaryCrop"))}<input class="field" name="primaryCrop" maxlength="120" value="${escAttr(state.profile.primaryCrop)}"></label>
        <button class="button" type="submit">${safe(t("saveProfile"))}</button>
        <div class="report-meta" id="profile-message" aria-live="polite"></div>
      </form></section><section class="panel panel-pad"><div class="panel-title"><h2>${safe(t("offline"))}</h2><span>✓</span></div>
        <p class="subtitle">${safe(t("offlineText"))}</p><div class="notice green" style="margin-top:14px"><span>ⓘ</span><span>${safe(t("safetyText"))}</span></div>
      </section></div>`;
  }

  function render() {
    document.documentElement.lang = state.language === "sd" ? "ur" : state.language;
    document.documentElement.dir = state.language === "en" ? "ltr" : "rtl";
    let content;
    switch (state.page) {
      case "scanner": content = scannerPage(); break;
      case "history": content = historyPage(); break;
      case "knowledge": content = knowledgePage(); break;
      case "voice": content = voicePage(); break;
      case "profile": content = profilePage(); break;
      default: content = homePage();
    }
    document.getElementById("app").innerHTML = shell(content);
  }

  function addScan(result) {
    const scan = {
      id: `${Date.now()}-${Math.random().toString(36).slice(2, 8)}`,
      crop: result.crop,
      title: result.status === "review" ? t("review") : result.diseaseId ? diseaseFor(result.diseaseId).title : t("healthy"),
      status: result.status,
      photoName: result.photoName || "",
      date: new Date().toISOString(),
    };
    state.scans = [scan, ...state.scans].slice(0, 100);
    state.isSaved = true;
    saveJSON(STORAGE.scans, state.scans);
    render();
  }

  function choosePhoto(file) {
    if (!file) return;
    if (!file.type.startsWith("image/")) {
      notify("Please choose an image file.");
      return;
    }
    if (state.photoUrl) URL.revokeObjectURL(state.photoUrl);
    state.photoName = file.name.slice(0, 160);
    state.photoUrl = URL.createObjectURL(file);
    state.selectedSample = null;
    state.result = null;
    state.isSaved = false;
    render();
  }

  function showVoiceResponse() {
    const input = document.getElementById("voice-question");
    const query = (input?.value || "").trim();
    const response = query ? t("voiceSafe") : t("voiceIntro");
    const responseNode = document.getElementById("voice-response");
    if (responseNode) responseNode.textContent = response;
    if ("speechSynthesis" in window) {
      window.speechSynthesis.cancel();
      const utterance = new SpeechSynthesisUtterance(response);
      utterance.lang = state.language === "sd" ? "ur-PK" : state.language === "ur" ? "ur-PK" : "en";
      window.speechSynthesis.speak(utterance);
    } else {
      notify("Voice playback is not available in this browser.");
    }
  }

  document.addEventListener("click", (event) => {
    const target = event.target instanceof Element ? event.target.closest("[data-page], [data-action], [data-sample]") : null;
    if (!target) return;
    const page = target.getAttribute("data-page");
    if (page) {
      if (state.photoUrl) {
        URL.revokeObjectURL(state.photoUrl);
        if (state.result?.status === "review") {
          state.result = null;
          state.isSaved = false;
        }
      }
      state.photoUrl = "";
      state.photoName = "";
      state.page = page;
      render();
      return;
    }
    const sampleId = target.getAttribute("data-sample");
    if (sampleId) {
      state.selectedSample = sampleId;
      state.selectedCrop = samples.find((sample) => sample.id === sampleId)?.crop ?? state.selectedCrop;
      state.result = null;
      state.isSaved = false;
      render();
      return;
    }
    switch (target.getAttribute("data-action")) {
      case "show-result": {
        const sample = samples.find((item) => item.id === state.selectedSample);
        const disease = diseaseFor(state.selectedSample);
        if (!sample && !disease) return;
        const crop = sample?.crop ?? disease.crop;
        state.selectedCrop = crop;
        state.result = { crop, status: sample?.healthy ? "healthy" : "example", diseaseId: sample?.healthy ? null : sample?.id ?? disease.id, photoName: "" };
        state.isSaved = false;
        render();
        document.getElementById("scan-report")?.scrollIntoView({ behavior: "smooth", block: "start" });
        break;
      }
      case "save-photo-review":
        if (!state.photoUrl || !state.selectedCrop) return;
        state.result = { crop: state.selectedCrop, status: "review", photoName: state.photoName, photoUrl: state.photoUrl };
        state.isSaved = false;
        render();
        document.getElementById("scan-report")?.scrollIntoView({ behavior: "smooth", block: "start" });
        break;
      case "save-result":
        if (state.result && !state.isSaved) addScan(state.result);
        break;
      case "clear-photo":
        if (state.photoUrl) URL.revokeObjectURL(state.photoUrl);
        state.photoUrl = "";
        state.photoName = "";
        state.result = null;
        state.isSaved = false;
        render();
        break;
      case "print":
        window.print();
        break;
      case "delete-scan":
        state.scans = state.scans.filter((scan) => scan.id !== target.getAttribute("data-id"));
        saveJSON(STORAGE.scans, state.scans);
        render();
        break;
      case "clear-history":
        if (window.confirm("Remove all field notes saved in this browser?")) {
          state.scans = [];
          saveJSON(STORAGE.scans, state.scans);
          render();
        }
        break;
      case "open-sample": {
        const diseaseId = target.getAttribute("data-disease");
        const disease = diseaseFor(diseaseId);
        if (!disease) return;
        state.selectedCrop = disease.crop;
        state.selectedSample = diseaseId;
        state.page = "scanner";
        state.result = null;
        state.isSaved = false;
        render();
        break;
      }
      case "ask-voice":
      case "speak":
        showVoiceResponse();
        break;
    }
  });

  document.addEventListener("change", (event) => {
    const target = event.target;
    if (!(target instanceof HTMLElement)) return;
    if (target.id === "language") {
      state.language = target.value;
      saveJSON(STORAGE.language, state.language);
      render();
    } else if (target.id === "crop-select") {
      state.selectedCrop = target.value;
    } else if (target.id === "photo-input") {
      choosePhoto(target.files?.[0]);
    } else if (target.id === "library-crop") {
      state.libraryCrop = target.value;
      render();
    }
  });

  document.addEventListener("input", (event) => {
    const target = event.target;
    if (!(target instanceof HTMLElement)) return;
    if (target.id === "library-search") {
      const position = target.selectionStart;
      state.librarySearch = target.value;
      render();
      const replacement = document.getElementById("library-search");
      replacement?.focus();
      if (replacement && position !== null) replacement.setSelectionRange(position, position);
    } else if (target.id === "history-search") {
      const position = target.selectionStart;
      state.historySearch = target.value;
      render();
      const replacement = document.getElementById("history-search");
      replacement?.focus();
      if (replacement && position !== null) replacement.setSelectionRange(position, position);
    }
  });

  document.addEventListener("submit", (event) => {
    if (event.target?.id !== "profile-form") return;
    event.preventDefault();
    const form = new FormData(event.target);
    state.profile = {
      name: String(form.get("name") || "").trim() || "Field farmer",
      district: String(form.get("district") || "").trim(),
      primaryCrop: String(form.get("primaryCrop") || "").trim(),
    };
    saveJSON(STORAGE.profile, state.profile);
    render();
    const message = document.getElementById("profile-message");
    if (message) message.textContent = t("profileSaved");
  });

  document.addEventListener("keydown", (event) => {
    if (event.key === "Enter" && event.target.matches(".profile-mini[role='button']")) {
      state.page = "profile";
      render();
    }
  });

  render();
})();
