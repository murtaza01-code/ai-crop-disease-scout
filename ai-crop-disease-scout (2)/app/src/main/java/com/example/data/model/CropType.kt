package com.example.data.model

/**
 * Botanical & FAO World Programme for Census of Agriculture (WCA) Appendix 4 Crops
 * Contains English, Urdu, Sindhi names, botanical name, ICC crop code, and category.
 */
enum class CropType(
    val id: String,
    val englishName: String,
    val urduName: String,
    val sindhiName: String,
    val iconEmoji: String,
    val botanicalName: String = "",
    val iccCode: String = "",
    val category: String = "General"
) {
    AUTO_DETECT("auto", "Auto-Detect", "خودکار شناخت", "پاڻمرادو سڃاڻپ", "🔍", "", "", "All"),

    // --- Core Major Field Crops (Pakistan & Global) ---
    COTTON("cotton", "Cotton", "کپاس", "ڪپھ", "🌱", "Gossypium spp.", "9211", "Fiber"),
    WHEAT("wheat", "Wheat", "گندم", "ڪڻڪ", "🌾", "Triticum spp.", "0111", "Cereals"),
    RICE("rice", "Rice", "چاول", "چانور", "🌾", "Oryza sativa", "0113", "Cereals"),
    SUGARCANE("sugarcane", "Sugarcane", "کماد", "ڪمند", "🎋", "Saccharum officinarum", "0180", "Sugar"),
    MAIZE("maize", "Maize / Corn", "مکئی", "مڪئي", "🌽", "Zea mays", "0112", "Cereals"),
    BARLEY("barley", "Barley", "جَو", "جَوَ", "🌾", "Hordeum vulgare", "0115", "Cereals"),
    SORGHUM("sorghum", "Sorghum / Jowar", "جوار", "جوار", "🌾", "Sorghum bicolor", "0114", "Cereals"),
    MILLET("millet", "Pearl Millet / Bajra", "باجرہ", "باجهري", "🌾", "Pennisetum glaucum", "0116", "Cereals"),
    OATS("oats", "Oats / Jai", "جئی", "جئي", "🌾", "Avena sativa", "0119", "Cereals"),

    // --- Vegetables & Horticultural Crops ---
    TOMATO("tomato", "Tomato", "ٹماٹر", "ٽماٽو", "🍅", "Solanum lycopersicum", "0123", "Vegetables"),
    CHILI("chili", "Chili Pepper", "مرچ", "مرچ", "🌶️", "Capsicum frutescens / annuum", "0142", "Vegetables"),
    POTATO("potato", "Potato", "آلو", "پٽاٽا", "🥔", "Solanum tuberosum", "0151", "Vegetables"),
    ONION("onion", "Onion", "پیاز", "بصر", "🧅", "Allium cepa", "0125", "Vegetables"),
    GARLIC("garlic", "Garlic", "لہسن", "ٿوم", "🧄", "Allium sativum", "0126", "Vegetables"),
    OKRA("okra", "Okra / Lady Finger", "بھنڈی", "ڀينڊي", "🥬", "Abelmoschus esculentus", "0129", "Vegetables"),
    EGGPLANT("eggplant", "Eggplant / Brinjal", "بینگن", "وانڱڻ", "🍆", "Solanum melongena", "0124", "Vegetables"),
    CABBAGE("cabbage", "Cabbage", "بند گوبھی", "بند گوبي", "🥬", "Brassica oleracea var. capitata", "0121", "Vegetables"),
    CAULIFLOWER("cauliflower", "Cauliflower", "پھول گوبھی", "ڦل گوبي", "🥦", "Brassica oleracea var. botrytis", "0122", "Vegetables"),
    CARROT("carrot", "Carrot", "گاجر", "گاجر", "🥕", "Daucus carota", "0153", "Vegetables"),
    RADISH("radish", "Radish / Mooli", "مولی", "مُري", "🌱", "Raphanus sativus", "0154", "Vegetables"),
    SPINACH("spinach", "Spinach / Palak", "پالک", "پالڪ", "🥬", "Spinacia oleracea", "0127", "Vegetables"),
    CUCUMBER("cucumber", "Cucumber / Kheera", "کھیرا", "کيرو", "🥒", "Cucumis sativus", "0128", "Vegetables"),
    PUMPKIN("pumpkin", "Pumpkin / Kaddu", "کدو / حلوہ کدو", "ڪدو", "🎃", "Cucurbita spp.", "0129", "Vegetables"),
    BITTER_GOURD("bitter_gourd", "Bitter Gourd / Karela", "کریلا", "ڪريلو", "🥒", "Momordica charantia", "0129", "Vegetables"),
    BOTTLE_GOURD("bottle_gourd", "Bottle Gourd / Lauki", "لوکی", "ڪدو لوڪي", "🥒", "Lagenaria siceraria", "0129", "Vegetables"),
    PEA("pea", "Green Pea / Matar", "مٹر", "مٽر", "🫛", "Pisum sativum", "0129", "Vegetables"),

    // --- Legumes & Pulses ---
    CHICKPEA("chickpea", "Chickpea / Gram", "چنا", "چڻا", "🧆", "Cicer arietinum", "0171", "Pulses"),
    LENTIL("lentil", "Lentil / Masoor", "مسور", "مسور", "🥣", "Lens culinaris", "0172", "Pulses"),
    MUNG_BEAN("mung_bean", "Mung Bean / Moong", "مونگ", "مڱ", "🌱", "Vigna radiata", "0173", "Pulses"),
    BLACK_GRAM("black_gram", "Black Gram / Mash", "ماش", "ماھه", "🌱", "Vigna mungo", "0174", "Pulses"),
    PIGEON_PEA("pigeon_pea", "Pigeon Pea / Arhar", "ارہر / تور دال", "ارهر", "🌱", "Cajanus cajan", "0175", "Pulses"),
    COWPEA("cowpea", "Cowpea / Lobia", "لوبیا", "چوئلا", "🫘", "Vigna unguiculata", "0179", "Pulses"),

    // --- Oilseeds ---
    MUSTARD("mustard", "Mustard / Sarson", "سرسوں / رایا", "سنهن / ڄامڀو", "🌼", "Brassica juncea / napus", "0143", "Oilseeds"),
    SUNFLOWER("sunflower", "Sunflower", "سورج مکھی", "سج مکي", "🌻", "Helianthus annuus", "0144", "Oilseeds"),
    SESAME("sesame", "Sesame / Til", "تل", "تِر", "🌱", "Sesamum indicum", "0145", "Oilseeds"),
    SOYBEAN("soybean", "Soybean", "سویا بین", "سويا بين", "🫘", "Glycine max", "0141", "Oilseeds"),
    GROUNDNUT("groundnut", "Groundnut / Peanut", "مونگ پھلی", "مڱ ڦري", "🥜", "Arachis hypogaea", "0142", "Oilseeds"),
    CANOLA("canola", "Canola / Rapeseed", "کینولا", "ڪينولا", "🌼", "Brassica napus", "0143", "Oilseeds"),
    LINSEED("linseed", "Linseed / Flax", "السی", "السِي", "🌱", "Linum usitatissimum", "0149", "Oilseeds"),
    CASTOR_BEAN("castor_bean", "Castor Bean / Arand", "ارنڈ", "ارنڊ", "🌱", "Ricinus communis", "0149", "Oilseeds"),

    // --- Fruits & Tree Orchards ---
    MANGO("mango", "Mango", "آم", "انب", "🥭", "Mangifera indica", "0133", "Fruits"),
    BANANA("banana", "Banana", "کیلا", "ڪيلا", "🍌", "Musa acuminata", "0132", "Fruits"),
    CITRUS("citrus", "Citrus / Kinnow / Orange", "مالٹا / کنو", "موسمي / نارنگي", "🍊", "Citrus sinensis / reticulata", "0131", "Fruits"),
    LEMON("lemon", "Lemon / Lime", "لیموں", "ليمون", "🍋", "Citrus limon / aurantifolia", "0131", "Fruits"),
    GUAVA("guava", "Guava / Amrood", "امرود", "امرود", "🍈", "Psidium guajava", "0134", "Fruits"),
    DATE_PALM("date_palm", "Date Palm / Khajoor", "کھجور", "کجين / کجور", "🌴", "Phoenix dactylifera", "0135", "Fruits"),
    APPLE("apple", "Apple / Saib", "سیب", "صوف", "🍎", "Malus domestica", "0134", "Fruits"),
    PEACH("peach", "Peach / Aru", "آڑو", "آڙو", "🍑", "Prunus persica", "0134", "Fruits"),
    PLUM("plum", "Plum / Aaloo Bukhara", "آلو بخارا", "الو بخارا", "🫐", "Prunus domestica", "0134", "Fruits"),
    APRICOT("apricot", "Apricot / Khubani", "خوبانی", "خوباني", "🍑", "Prunus armeniaca", "0134", "Fruits"),
    GRAPE("grape", "Grape / Angoor", "انگور", "انگور", "🍇", "Vitis vinifera", "0133", "Fruits"),
    POMEGRANATE("pomegranate", "Pomegranate / Anar", "انار", "ڏاڙهون", "🍎", "Punica granatum", "0134", "Fruits"),
    PAPAYA("papaya", "Papaya / Papita", "پپیتا", "پپيتو", "🍈", "Carica papaya", "0134", "Fruits"),
    WATERMELON("watermelon", "Watermelon / Tarbooz", "تربوز", "ڇانهين", "🍉", "Citrullus lanatus", "0123", "Fruits"),
    MELON("melon", "Melon / Kharbooza", "خربوزہ", "گدرو", "🍈", "Cucumis melo", "0123", "Fruits"),
    BER("ber", "Jujube / Ber", "بیر", "بير", "🍒", "Ziziphus mauritiana", "0134", "Fruits"),
    JAMUN("jamun", "Black Plum / Jamun", "جامن", "ڄمون", "🫐", "Syzygium cumini", "0134", "Fruits"),
    FIG("fig", "Fig / Anjeer", "انجیر", "انجير", "🫐", "Ficus carica", "0134", "Fruits"),
    ALMOND("almond", "Almond / Badam", "بادام", "بادام", "🌰", "Prunus dulcis", "0136", "Nuts"),
    PISTACHIO("pistachio", "Pistachio / Pista", "پستہ", "پستو", "🥜", "Pistacia vera", "0136", "Nuts"),
    WALNUT("walnut", "Walnut / Akhrot", "اخروٹ", "اکروٽ", "🌰", "Juglans regia", "0136", "Nuts"),
    OLIVE("olive", "Olive / Zaitoon", "زیتون", "زيتون", "🫒", "Olea europaea", "0137", "Fruits"),

    // --- Spices, Stimulants & Beverage Crops ---
    GINGER("ginger", "Ginger / Adrak", "ادرک", "ادرڪ", "🫚", "Zingiber officinale", "0142", "Spices"),
    TURMERIC("turmeric", "Turmeric / Haldi", "ہلدی", "هئڊ", "🟡", "Curcuma longa", "0142", "Spices"),
    CORIANDER("coriander", "Coriander / Dhania", "دھنیا", "ڌاڻا", "🌿", "Coriandrum sativum", "0142", "Spices"),
    CUMIN("cumin", "Cumin / Zeera", "زیرہ", "زيرو", "🧂", "Cuminum cyminum", "0142", "Spices"),
    FENNEL("fennel", "Fennel / Saunf", "سونف", "سؤنف", "🌿", "Foeniculum vulgare", "0142", "Spices"),
    FENUGREEK("fenugreek", "Fenugreek / Methi", "میتھی", "ميتي", "🌱", "Trigonella foenum-graecum", "0142", "Spices"),
    MINT("mint", "Mint / Podina", "پودینہ", "ڦودنو", "🌿", "Mentha spicata", "0142", "Spices"),
    TOBACCO("tobacco", "Tobacco / Tambaku", "تمباکو", "تنباکو", "🍂", "Nicotiana tabacum", "0193", "Stimulants"),
    TEA("tea", "Tea / Chai", "چائے", "چانهه", "🍵", "Camellia sinensis", "0161", "Beverages"),
    COFFEE("coffee", "Coffee", "کافی", "ڪافي", "☕", "Coffea arabica / robusta", "0162", "Beverages"),

    // --- Fodder & Fiber Crops (Appendix 4 Botanical) ---
    ALFALFA("alfalfa", "Alfalfa / Lucerne", "لوسرن", "لوسرن", "🌿", "Medicago sativa", "0191", "Fodder"),
    BERSEEM("berseem", "Berseem Clover", "برسیم", "برسيم", "🍀", "Trifolium alexandrinum", "0191", "Fodder"),
    JUTE("jute", "Jute / Pat", "پٹ سن", "پٽ سن", "🎋", "Corchorus olitorius / capsularis", "9212", "Fiber"),
    SUNN_HEMP("sunn_hemp", "Sunn Hemp / Sanai", "سن / سنئی", "سڻ", "🌱", "Crotalaria juncea", "9214", "Fiber");

    fun getDisplayName(language: AppLanguage): String {
        return when (language) {
            AppLanguage.ENGLISH -> englishName
            AppLanguage.URDU -> urduName
            AppLanguage.SINDHI -> sindhiName
        }
    }

    companion object {
        fun fromId(id: String): CropType {
            return entries.find { it.id.equals(id, ignoreCase = true) } ?: AUTO_DETECT
        }

        fun search(query: String): List<CropType> {
            val q = query.trim().lowercase()
            if (q.isEmpty()) return entries.filter { it != AUTO_DETECT }
            return entries.filter { crop ->
                crop != AUTO_DETECT && (
                    crop.englishName.lowercase().contains(q) ||
                    crop.urduName.contains(q) ||
                    crop.sindhiName.contains(q) ||
                    crop.botanicalName.lowercase().contains(q) ||
                    crop.iccCode.contains(q) ||
                    crop.category.lowercase().contains(q)
                )
            }
        }
    }
}
