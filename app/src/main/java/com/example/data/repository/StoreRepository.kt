package com.example.data.repository

import com.example.R
import com.example.data.model.Category
import com.example.data.model.CustomerReview
import com.example.data.model.Product
import com.example.data.model.ProductSpec
import com.example.data.model.ShowroomItem
import com.example.data.model.StoreFeatureItem

object StoreRepository {

    const val STORE_PHONE = "01020931398"
    const val STORE_WHATSAPP_NUMBER = "201020931398"
    const val STORE_WHATSAPP_URL = "https://wa.me/201020931398"
    const val BRAND_NAME = "𝑯𝑨𝑴𝑶 𝑨𝑳𝑴𝑩𝑹𝑴𝑱"
    const val STORE_TITLE = "للأدوات الصحية والسباكة"
    const val STORE_ADDRESS = "محافظة اسيوط مركز ديروط قريه ساو انزل وسال قول عايز محل المعلم حماده رزق محل السباكه"
    const val STORE_HOURS = "يوميًا 9:00 ص – 11:00 م (ما عدا الجمعة: من بعد الظهر حتى 11:00 م)"

    // بيانات صاحب المحل
    const val OWNER_TITLE = "صاحب المحل"
    const val OWNER_NAME = "المعلم حماده رزق"
    const val OWNER_PHONE = "01020931398"
    const val OWNER_INFO_LABEL = "صاحب المحل المعلم حماده رزق رقم الفون 01020931398 مكلمات وتس"

    // بيانات المطور
    const val DEVELOPER_TITLE = "المطور"
    const val DEVELOPER_NAME = "𝑯𝑨𝑴𝑶 𝑨𝑳𝑴𝑩𝑹𝑴𝑱"
    const val DEVELOPER_PHONE = "01096700804"
    const val DEVELOPER_WHATSAPP_NUMBER = "201096700804"
    const val DEVELOPER_WHATSAPP_URL = "https://wa.me/201096700804"
    const val DEVELOPER_CONTACT_LABEL = "التواصل مع المطور وتس 01096700804"
    const val DEVELOPER_PASSWORD = "UcCMOsqxm%be*a!MWMzUx5uD"

    val features = listOf(
        StoreFeatureItem(
            id = "f1",
            titleAr = "أسعار جملة تنافسية",
            descriptionAr = "أفضل أسعار للجملة والقطاعي للمقاولين وأصحاب المنازل",
            iconType = "price"
        ),
        StoreFeatureItem(
            id = "f2",
            titleAr = "جودة وضمان معتمد",
            descriptionAr = "خامات أصلية 100% مع ضمان استبدال فوري",
            iconType = "verified"
        ),
        StoreFeatureItem(
            id = "f3",
            titleAr = "توصيل سريع",
            descriptionAr = "شحن وتوريد لجميع قرى ديروط وأسيوط ومحافظات الصعيد",
            iconType = "shipping"
        ),
        StoreFeatureItem(
            id = "f4",
            titleAr = "استشارات سباكة مجانية",
            descriptionAr = "خبرة المعلم حماده رزق في حساب كميات التأسيس والتشطيب",
            iconType = "support"
        )
    )

    val categories = listOf(
        Category(
            id = "cat_pipes",
            nameAr = "مواسير مياه",
            subtitleAr = "PPR / PVC / بولي بروبيلين / حديد مجلفن",
            iconName = "pipes",
            itemCount = 28
        ),
        Category(
            id = "cat_mixers",
            nameAr = "خلاطات وحنفيات",
            subtitleAr = "مطبخ / حمام / دش / شطافات نحاس",
            iconName = "faucet",
            itemCount = 35
        ),
        Category(
            id = "cat_sanitary",
            nameAr = "أدوات صحية",
            subtitleAr = "أحواض / قواعد حمام / بانيو / كبائن",
            iconName = "toilet",
            itemCount = 22
        ),
        Category(
            id = "cat_fittings",
            nameAr = "وصلات وكيعان",
            subtitleAr = "أكواع / تيهات / جلب سن / محابس زاوية",
            iconName = "fittings",
            itemCount = 44
        ),
        Category(
            id = "cat_drainage",
            nameAr = "الصرف ومواسير أرضية",
            subtitleAr = "سيفونات / بلاعات صرف / جرجوري 3 و4 بوصة",
            iconName = "drain",
            itemCount = 19
        ),
        Category(
            id = "cat_tools",
            nameAr = "مستلزمات السباك",
            subtitleAr = "مفاتيح أنابيب / بنس / ماكينات لحام / تفلون",
            iconName = "wrench",
            itemCount = 31
        ),
        Category(
            id = "cat_heaters",
            nameAr = "سخانات ودفايات",
            subtitleAr = "سخانات غاز وكهرباء / قطع غيار أصلية",
            iconName = "heater",
            itemCount = 14
        ),
        Category(
            id = "cat_pumps",
            nameAr = "طلمبات ومواتير مياه",
            subtitleAr = "مواتير 1/2 و1 و1.5 حصان كالبيدا وأوتوماتيك",
            iconName = "pump",
            itemCount = 16
        )
    )

    val products = listOf(
        // Cat 1: Pipes
        Product(
            id = "p_ppr_32",
            categoryId = "cat_pipes",
            nameAr = "ماسورة مياه PPR بولي بروبيلين 32 مم (1 بوصة)",
            brand = "𝑯𝑨𝑴𝑶 𝑨𝑳𝑴𝑩𝑹𝑴𝑱 Premium",
            price = 145.0,
            wholesalePrice = 125.0,
            unitAr = "عود 4 متر",
            descriptionAr = "ماسورة PPR مياه ساخنة وباردة عالية الكثافة، معتمدة ومقاومة للتكلس والضغط العالي PN25. أفضل اختيار لتأسيس شبكات السباكة الحديثة.",
            imageRes = R.drawable.img_pipes_fittings,
            galleryImages = listOf(R.drawable.img_pipes_fittings, R.drawable.img_hero_banner),
            specs = listOf(
                ProductSpec("القطر الخارجي", "32 مم (1 بوصة)"),
                ProductSpec("طول العود", "4 متر"),
                ProductSpec("المادة", "بولي بروبيلين عالي النقاوة (PPR-Type 3)"),
                ProductSpec("مقاومة الضغط", "PN 25 بار"),
                ProductSpec("درجة الحرارة", "حتى 95 درجة مئوية"),
                ProductSpec("بلد المنشأ", "مصر بخامات ألمانية معتمدة"),
                ProductSpec("الاستخدام", "تغذية المياه الساخنة والباردة وشبكات التدفئة")
            ),
            isFeatured = true,
            inStock = true,
            warrantyYears = 10,
            tagAr = "الأكثر مبيعاً"
        ),
        Product(
            id = "p_ppr_25",
            categoryId = "cat_pipes",
            nameAr = "ماسورة مياه PPR بولي بروبيلين 25 مم (3/4 بوصة)",
            brand = "𝑯𝑨𝑴𝑶 𝑨𝑳𝑴𝑩𝑹𝑴𝑱",
            price = 110.0,
            wholesalePrice = 95.0,
            unitAr = "عود 4 متر",
            descriptionAr = "ماسورة تغذية داخلية للحمامات والمطابخ، خامة ثلاثية الطبقات تمنع تكون الطحالب والروائح داخل شبكة المياه.",
            imageRes = R.drawable.img_pipes_fittings,
            specs = listOf(
                ProductSpec("القطر الخارجي", "25 مم (3/4 بوصة)"),
                ProductSpec("طول العود", "4 متر"),
                ProductSpec("الضغط الاسمي", "PN 20"),
                ProductSpec("الضمان", "10 سنوات ضمان استبدال")
            ),
            isFeatured = false,
            inStock = true,
            warrantyYears = 10,
            tagAr = "تأسيس"
        ),
        Product(
            id = "p_pvc_4inch",
            categoryId = "cat_pipes",
            nameAr = "ماسورة صرف PVC رمادي 4 بوصة سمك 3.2 مم",
            brand = "للأدوات الصحية والسباكة",
            price = 285.0,
            wholesalePrice = 250.0,
            unitAr = "عود 4 متر",
            descriptionAr = "ماسورة صرف رئيسي وقواعد حمام معتمدة ضد الصدمات والمواد الكيميائية وحرارة الصيف الشديدة.",
            imageRes = R.drawable.img_pipes_fittings,
            specs = listOf(
                ProductSpec("القطر", "4 بوصة (110 مم)"),
                ProductSpec("السمك", "3.2 مم ضغط عالي"),
                ProductSpec("المادة", "UPVC نقي"),
                ProductSpec("الاستخدام", "صرف قواعد الحمام والمزاريب والأعمدة الرئيسية")
            ),
            isFeatured = false,
            inStock = true,
            tagAr = "صرف متين"
        ),

        // Cat 2: Mixers
        Product(
            id = "p_mixer_basin_gold",
            categoryId = "cat_mixers",
            nameAr = "خلاط حوض شجرة مودرن ذهبي مطفي PVD فاخر",
            brand = "𝑯𝑨𝑴𝑶 𝑨𝑳𝑴𝑩𝑹𝑴𝑱 Luxury",
            price = 1350.0,
            wholesalePrice = 1180.0,
            unitAr = "قطعة كاملة بالوصلات",
            descriptionAr = "خلاط حوض ديكور نحاس إيطالي ثقيل، طلاء ذهبي PVD بتقنية الليزر مضاد للخدش والبهتان. مزود بقلب سيراميك ألماني موفر للمياه.",
            imageRes = R.drawable.img_faucets_mixer,
            galleryImages = listOf(R.drawable.img_faucets_mixer, R.drawable.img_hero_banner),
            specs = listOf(
                ProductSpec("النوع", "خلاط حوض شجرة عالي"),
                ProductSpec("المادة الأساسية", "نحاس نقي 100% خالي من الرصاص"),
                ProductSpec("التشطيب", "ذهبي بروشيد مطفي PVD"),
                ProductSpec("نوع القلب", "سيراميك 35 مم أوروبي"),
                ProductSpec("محتويات العلبة", "الخلاط + وصلا 60 سم استانلس + طقم التثبيت"),
                ProductSpec("الضمان", "10 سنوات ضمان شامل")
            ),
            isFeatured = true,
            inStock = true,
            warrantyYears = 10,
            discountPercent = 15,
            tagAr = "تشطيب VIP"
        ),
        Product(
            id = "p_mixer_bath_chrome",
            categoryId = "cat_mixers",
            nameAr = "خلاط بانيو ودش كروم لامع نحاس مع طقم السماعة",
            brand = "𝑯𝑨𝑴𝑶 𝑨𝑳𝑴𝑩𝑹𝑴𝑱",
            price = 1550.0,
            wholesalePrice = 1350.0,
            unitAr = "طقم كامل",
            descriptionAr = "خلاط بانيو ثقيل بطلاء كروم ناصع 3 طبقات ومحول دش سلس. يشمل مسطرة وسماعة دش متعددة الحركات وخرطوم مرن مرن غير قابل للالتواء.",
            imageRes = R.drawable.img_faucets_mixer,
            specs = listOf(
                ProductSpec("النوع", "خلاط حائط بانيو ودش"),
                ProductSpec("المادة", "نحاس أحمر أصلي عالي النقاء"),
                ProductSpec("الطلاء", "كروم لامع نيكل كروميوم"),
                ProductSpec("الملحقات", "سماعة دش + خرطوم 1.5 م + كبش نحاس"),
                ProductSpec("الضمان", "5 سنوات ضمان المعلم حماده رزق")
            ),
            isFeatured = true,
            inStock = true,
            warrantyYears = 5,
            tagAr = "ضمان معتمد"
        ),
        Product(
            id = "p_mixer_kitchen_flexible",
            categoryId = "cat_mixers",
            nameAr = "خلاط مطبخ شجرة رقبة متحركة سيليكون سوستة",
            brand = "𝑯𝑨𝑴𝑶 𝑨𝑳𝑴𝑩𝑹𝑴𝑱",
            price = 1150.0,
            wholesalePrice = 980.0,
            unitAr = "قطعة",
            descriptionAr = "خلاط حوض مطبخ عملي جداً برقبة سيليكون مرنة تدور 360 درجة، مع رأس دش بنظامين للرش (فقاعات ورذاذ قوي لغسيل الأواني).",
            imageRes = R.drawable.img_faucets_mixer,
            specs = listOf(
                ProductSpec("النوع", "خلاط مجلى مطبخ"),
                ProductSpec("المادة", "جسم نحاسي ورقبة مرنة ألمانية"),
                ProductSpec("أنظمة المياه", "نظام انسيابي + دش شلال"),
                ProductSpec("الدوران", "360 درجة لجميع الاتجاهات")
            ),
            isFeatured = true,
            inStock = true,
            warrantyYears = 5,
            tagAr = "الأكثر طلباً"
        ),

        // Cat 3: Sanitary Ware
        Product(
            id = "p_toilet_set",
            categoryId = "cat_sanitary",
            nameAr = "طقم حمام كامل قاعدة سيديلي ذاتي الهبوط + حوض بالعامود",
            brand = "للأدوات الصحية والسباكة",
            price = 3450.0,
            wholesalePrice = 3100.0,
            unitAr = "طقم حمام متكامل",
            descriptionAr = "خزف بورسلين عالي النقاء ومقاوم للبقع والاصفرار، نظام شطف تورنيدو قوي وموفر للمياه، وغطاء هيدروليك ناعم الإغلاق.",
            imageRes = R.drawable.img_hero_banner,
            specs = listOf(
                ProductSpec("المكونات", "مرحاض + سيفون + ماكينة مزدوجة + حوض + عامود"),
                ProductSpec("الخامة", "سيراميك فيتريوس تشاينا عالي الجودة"),
                ProductSpec("نظام الشطف", "Dual Flush توفير مياه"),
                ProductSpec("الغطاء", "سوفت كلوز ناعم"),
                ProductSpec("بلد الصنع", "مصر بجودة تصدير")
            ),
            isFeatured = true,
            inStock = true,
            warrantyYears = 10,
            tagAr = "عرض خاص"
        ),
        Product(
            id = "p_counter_basin",
            categoryId = "cat_sanitary",
            nameAr = "حوض حمام ديكور فوق الرخامة بيضاوي أبيض ناصع",
            brand = "𝑯𝑨𝑴𝑶 𝑨𝑳𝑴𝑩𝑹𝑴𝑱",
            price = 980.0,
            wholesalePrice = 850.0,
            unitAr = "قطعة",
            descriptionAr = "حوض مودرن سطحي يوضع فوق رخامة الحمام، حواف رقيقة وتشطيب لامع يعطي فخامة للحمام والضيوف.",
            imageRes = R.drawable.img_hero_banner,
            specs = listOf(
                ProductSpec("المقاس", "60 × 42 × 14 سم"),
                ProductSpec("نوع التركيب", "فوق السطح (Countertop)"),
                ProductSpec("اللون", "أبيض لامع عالي الجودة")
            ),
            isFeatured = false,
            inStock = true,
            tagAr = "مودرن"
        ),

        // Cat 4: Fittings & Elbows
        Product(
            id = "p_elbow_ppr_brass",
            categoryId = "cat_fittings",
            nameAr = "كوع بسن داخلي نحاس PPR مقاس 25 × 1/2 بوصة",
            brand = "𝑯𝑨𝑴𝑶 𝑨𝑳𝑴𝑩𝑹𝑴𝑱",
            price = 42.0,
            wholesalePrice = 34.0,
            unitAr = "قطعة",
            descriptionAr = "كوع لحام بولي مع جلبه نحاسية معالجة من الداخل لمنع تسرب المياه نهائياً وربط محابس الزاوية والخلاطات بكل أمان.",
            imageRes = R.drawable.img_pipes_fittings,
            specs = listOf(
                ProductSpec("المقاس", "25 مم لحام × 1/2 بوصة سن سن نحاس"),
                ProductSpec("معدن السن", "نحاس أصفر عالي الصلابة"),
                ProductSpec("الضغط", "PN 25")
            ),
            isFeatured = false,
            inStock = true,
            tagAr = "نحاس أصلي"
        ),
        Product(
            id = "p_tee_ppr",
            categoryId = "cat_fittings",
            nameAr = "تيه بولي PPR متساوي مقاس 32 مم",
            brand = "للأدوات الصحية والسباكة",
            price = 28.0,
            wholesalePrice = 22.0,
            unitAr = "قطعة",
            descriptionAr = "تيه لحام حراري لتوزيع خطوط التغذية الرئيسية دون فقدان ضغط المياه.",
            imageRes = R.drawable.img_pipes_fittings,
            specs = listOf(
                ProductSpec("المقاس", "32 مم (1 بوصة)"),
                ProductSpec("نوع اللحام", "حراري ماكينة مكواة بولي")
            ),
            isFeatured = false,
            inStock = true,
            tagAr = "قطع تأسيس"
        ),
        Product(
            id = "p_angle_valve",
            categoryId = "cat_fittings",
            nameAr = "محبس زاوية نحاس ثقيل شتاير ألماني بلية 1/2 بوصة",
            brand = "شتاير ألماني",
            price = 165.0,
            wholesalePrice = 140.0,
            unitAr = "قطعة بالوش الاستانلس",
            descriptionAr = "محبس زاوية بلية قلب نحاس لتغذية السيفونات والخلاطات، يتحمل الأملاح ولا يتكلس أو يسرب.",
            imageRes = R.drawable.img_pipes_fittings,
            specs = listOf(
                ProductSpec("المقاس", "1/2 × 1/2 بوصة"),
                ProductSpec("القلب", "بلية نحاس كروم مع مقبض معدني"),
                ProductSpec("الضمان", "5 سنوات")
            ),
            isFeatured = true,
            inStock = true,
            warrantyYears = 5,
            tagAr = "عالي الجودة"
        ),

        // Cat 5: Drainage
        Product(
            id = "p_floor_drain",
            categoryId = "cat_drainage",
            nameAr = "بلّاعة صرف حمام استانلس ستيل 304 مع ردّاد مانع روائح وحشرات",
            brand = "𝑯𝑨𝑴𝑶 𝑨𝑳𝑴𝑩𝑹𝑴𝑱",
            price = 240.0,
            wholesalePrice = 195.0,
            unitAr = "قطعة متكاملة",
            descriptionAr = "غطاء بلّاعة وصرف أرضيات استانلس سميك لا يصدأ أبداً مع صمام ذاتي الغلق يمنع تماماً خروج الروائح أو الحشرات من الصرف.",
            imageRes = R.drawable.img_store_interior,
            specs = listOf(
                ProductSpec("المقاس", "15 × 15 سم مخرج 2 بوصة"),
                ProductSpec("الخامة", "استانلس ستيل 304 نقي"),
                ProductSpec("الميزة", "رداد أوتوماتيك مغناطيسي مانع للروائح")
            ),
            isFeatured = true,
            inStock = true,
            tagAr = "مانع روائح"
        ),

        // Cat 6: Plumber Tools
        Product(
            id = "p_pipe_wrench",
            categoryId = "cat_tools",
            nameAr = "مفتاح أنابيب إنجليزي مقاس 14 بوصة فولاذ مقسى شديد التحمل",
            brand = "تول كينج برو",
            price = 320.0,
            wholesalePrice = 275.0,
            unitAr = "قطعة",
            descriptionAr = "مفتاح مواسير متين مصنوع من الفولاذ الكربوني المعالج حرارياً، فكوك مسننة بإحكام لربط وفك أصعب المواسير دون انزلاق.",
            imageRes = R.drawable.img_store_interior,
            specs = listOf(
                ProductSpec("المقاس", "14 بوصة (350 مم)"),
                ProductSpec("الخامة", "صلب Cr-V مقوى ومطلي ضد الصدأ"),
                ProductSpec("الاستخدام", "فك وتركيب مواسير الحديد والبولي والوصلات")
            ),
            isFeatured = false,
            inStock = true,
            tagAr = "عدة معلم"
        ),
        Product(
            id = "p_welding_machine",
            categoryId = "cat_tools",
            nameAr = "ماكينة لحام مواسير بولي PPR قدرة 2000 وات مع 6 لقم نحاس",
            brand = "𝑯𝑨𝑴𝑶 𝑨𝑳𝑴𝑩𝑹𝑴𝑱 Pro",
            price = 1450.0,
            wholesalePrice = 1250.0,
            unitAr = "طقم في شنطة معدنية",
            descriptionAr = "مكواة لحام احترافية للسباكين، ترموستات دقيق حتى 300 مئوية، تشمل لقم مقاسات 20، 25، 32، 40، 50، 63 مم مع مقص مواسير هدية.",
            imageRes = R.drawable.img_store_interior,
            specs = listOf(
                ProductSpec("القدرة", "2000 وات مزدوجة السخان"),
                ProductSpec("اللقم المرفقة", "6 لقم تيفلون ياباني من 20 إلى 63 مم"),
                ProductSpec("الملحقات", "شنطة صاج + مقص مواسير + ميزان مياه + قفازات"),
                ProductSpec("الضمان", "سنتين ضمان صيانة")
            ),
            isFeatured = true,
            inStock = true,
            warrantyYears = 2,
            tagAr = "عدة احترافية"
        ),

        // Cat 7: Heaters
        Product(
            id = "p_water_heater_gas",
            categoryId = "cat_heaters",
            nameAr = "سخان مياه غاز 10 لتر ديجيتال يعمل بأقل ضغط مياه",
            brand = "أوليمبيك / تورنيدو المعتمد",
            price = 4850.0,
            wholesalePrice = 4450.0,
            unitAr = "جهاز بالضمان",
            descriptionAr = "سخان غاز فوري شاشة رقمية لضبط الحرارة، شعلات نحاسية فائقة التوفير وحساسات أمان ثلاثية لفصل الغاز تلقائياً.",
            imageRes = R.drawable.img_hero_banner,
            specs = listOf(
                ProductSpec("السعة", "10 لتر تدفق مستمر"),
                ProductSpec("نوع الغاز", "طبيعي / أسطوانات (متاح التحويل)"),
                ProductSpec("شاشة العرض", "ديجيتال LED"),
                ProductSpec("الضمان", "5 سنوات ضمان توكيل")
            ),
            isFeatured = true,
            inStock = true,
            warrantyYears = 5,
            tagAr = "توفير وأمان"
        ),

        // Cat 8: Water Pumps
        Product(
            id = "p_water_pump_1hp",
            categoryId = "cat_pumps",
            nameAr = "ماتور مياه 1 حصان كالبيدا ملفات نحاس 100% مع فلوماك حماية",
            brand = "كالبيدا إيطالي معتمد",
            price = 3950.0,
            wholesalePrice = 3600.0,
            unitAr = "ماتور + فلوماك أوتوماتيك",
            descriptionAr = "ماتور رفع مياه حتى الطابق العاشر، سلك نحاس نقي يتحمل الحرارة والتشغيل الشاق المستمر، مع جهاز فلوماك يفصل الماتور عند انقطاع المياه لحمايته من الاحتراق.",
            imageRes = R.drawable.img_store_interior,
            specs = listOf(
                ProductSpec("القدرة", "1 حصان (0.75 ك.وات)"),
                ProductSpec("أقصى ارتفاع", "45 متر (حتى الدور العاشر)"),
                ProductSpec("الملفات", "نحاس نقي 100%"),
                ProductSpec("الجهاز المرفق", "فلوماك إلكتروني ذكي للحماية الذاتية"),
                ProductSpec("الضمان", "3 سنوات استبدال")
            ),
            isFeatured = true,
            inStock = true,
            warrantyYears = 3,
            discountPercent = 10,
            tagAr = "قوة وضغط"
        )
    )

    val showroomGallery = listOf(
        ShowroomItem(
            id = "g1",
            titleAr = "معرض الأدوات الصحية الرئيسي",
            descriptionAr = "تشكيلة متكاملة من أطقم الحمامات والمطابخ والخلاطات المودرن الفاخرة",
            imageRes = R.drawable.img_hero_banner
        ),
        ShowroomItem(
            id = "g2",
            titleAr = "مخزن مواسير البولي والصرف",
            descriptionAr = "جميع مقاسات الـ PPR والـ PVC ووصلات السباكة للجملة وتوريدات المشروعات",
            imageRes = R.drawable.img_pipes_fittings
        ),
        ShowroomItem(
            id = "g3",
            titleAr = "جناح الخلاطات والأطقم الديكورية",
            descriptionAr = "خلاطات إيطالية وترموكوبل ذهبي وكروم وأسود مطفي مع أعلى فترات ضمان",
            imageRes = R.drawable.img_faucets_mixer
        ),
        ShowroomItem(
            id = "g4",
            titleAr = "قسم المواتير والعدد وماكينات اللحام",
            descriptionAr = "أقوى مواتير المياه والشنط الاحترافية المعتمدة لسباكين أسيوط وديروط",
            imageRes = R.drawable.img_store_interior
        )
    )

    val reviews = listOf(
        CustomerReview(
            id = "r1",
            customerName = "الأسطى محمود عبد العال",
            roleOrLocation = "مقاول سباكة — ديروط",
            rating = 5,
            comment = "أفضل محل سباكة في ديروط وقرى ساو كلها. المعلم حماده رزق راجل محترم وخاماته كلها أصلية وسعر الجملة عنده مفيش زيه في أسيوط كلها.",
            dateAr = "منذ أسبوعين"
        ),
        CustomerReview(
            id = "r2",
            customerName = "المهندس أحمد حسنين",
            roleOrLocation = "تشطيب فيلات — أسيوط",
            rating = 5,
            comment = "أخذت من عند المعلم حماده رزق للأدوات الصحية بضاعة تأسيس فيلا كاملة من بولي وخلاطات PVD ومواتير كالبيدا. الضمان حقيقي والمعاملة في منتهى الأمانة والسرعة.",
            dateAr = "منذ شهر"
        ),
        CustomerReview(
            id = "r3",
            customerName = "الحاج إبراهيم الديري",
            roleOrLocation = "قرية ساو — ديروط",
            rating = 5,
            comment = "محل المعلم حماده رزق معروف بالسمعة الطيبة وحسن النية. لو نزلت ساو وسألت أي حد هيدلك عليه فوراً. البضاعة ممتازة والأسعار رحيمة بالناس.",
            dateAr = "منذ 3 أيام"
        ),
        CustomerReview(
            id = "r4",
            customerName = "محمد كمال الخياط",
            roleOrLocation = "عميل قطاعي — صنبو / ديروط",
            rating = 5,
            comment = "طلبت خلاطين وسخان عن طريق الواتساب وصلوني في نفس اليوم بنفس المواصفات وبتغليف محترم جداً. شكراً براند HAMO ALMBRMJ.",
            dateAr = "منذ أسبوع"
        )
    )

    fun getCategoryById(id: String): Category? = categories.find { it.id == id }

    fun getProductById(id: String): Product? = products.find { it.id == id }
}
