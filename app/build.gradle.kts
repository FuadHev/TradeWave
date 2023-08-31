plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs")
    id("kotlin-parcelize")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.fuadhev.tradewave"
    compileSdk = 33

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
    defaultConfig {
        applicationId = "com.fuadhev.tradewave"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    val navVersion = "2.6.0"
    val lifecycleVersion = "2.6.1"
    val lottieVersion = "5.2.0"
    val roomVersion = "2.5.2"


    //Navigation Components
    implementation("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navVersion")

    //Retrofit
    implementation("com.google.code.gson:gson:2.10")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    //MVVM
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")

    //Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")

    //Okhttp
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.2")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2")

    //Hilt
    implementation("com.google.dagger:hilt-android:2.46")
    kapt("com.google.dagger:hilt-android-compiler:2.44")

    //Firebase
    implementation("com.google.firebase:firebase-auth-ktx:22.1.1")
    implementation("com.google.android.gms:play-services-auth:20.6.0")
    implementation("com.google.firebase:firebase-firestore-ktx:24.7.1")
    implementation("com.google.firebase:firebase-storage-ktx:20.2.1")
    implementation("com.google.firebase:firebase-messaging-ktx:23.2.1")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")

    // Glide
    implementation("com.github.bumptech.glide:glide:4.15.1")
    ksp("com.github.bumptech.glide:compiler:4.14.2")

    //Lottie
    implementation("com.airbnb.android:lottie:$lottieVersion")

    //Security
    implementation("androidx.security:security-crypto-ktx:1.1.0-alpha06")

    //MotionToast
    implementation("com.github.Spikeysanju:MotionToast:1.4")

    //Indicator
    implementation("com.tbuonomo:dotsindicator:5.0")

    //Viewpager Implementation
    implementation("androidx.viewpager2:viewpager2:1.0.0")

    //CircleIndicator
    implementation("me.relex:circleindicator:2.1.6")

    //YoutubePlayer
    implementation("com.pierfrancescosoffritti.androidyoutubeplayer:core:12.0.0")

    //Room
    implementation("androidx.room:room-ktx:$roomVersion")
    implementation("androidx.room:room-runtime:$roomVersion")
    annotationProcessor("androidx.room:room-compiler:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")

    //Dialogs
    implementation("com.github.gabriel-TheCode:AestheticDialogs:1.3.8")

    //Fancy toast
    implementation("io.github.shashank02051997:FancyToast:2.0.2")

    //ImagePicker
    implementation("com.github.dhaval2404:imagepicker:2.1")

    //Timer
    implementation("com.github.happysingh23828:HappyTimer:1.0.1")

    //Spinner
    implementation("com.github.chivorns:smartmaterialspinner:1.5.0")

    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}