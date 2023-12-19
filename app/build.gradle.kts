plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-parcelize")
}

android {
    namespace = "com.devplatform.mubarak.notes"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.devplatform.mubarak.notes"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures{
        viewBinding = true
        // dataBinding = true
    }
}

dependencies {

    val roomVersion = "2.6.1"
    val daggerVersion = "2.48"
    val retrofitVersion = "2.9.0"
    val viewmodelVersion = "2.6.2"
    val coroutineVersion = "1.7.3"
    val navVersion = "2.7.4"
    val preference_version = "1.2.1"

    // Android Core (Ui)
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Android Navigation (Navigation Component)
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.5")


    // Hilt (Dependency Injection)
    implementation("com.google.dagger:hilt-android:$daggerVersion")
    ksp("com.google.dagger:hilt-android-compiler:$daggerVersion")

    // Room (Local Database)
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-paging:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")

    // Retrofit (Http Client)
   /* implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")*/

    // Coil (Image Loader)
    //implementation("io.coil-kt:coil:2.4.0")

    // ViewModel / LiveData (MVVM)
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$viewmodelVersion")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$viewmodelVersion")

    // Paging (Pagination)
   // implementation("androidx.paging:paging-runtime-ktx:3.2.1")

    // kotlin Ktx
    implementation ("androidx.fragment:fragment-ktx:1.6.2")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")

    // Coroutines (Asynchronous Task)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutineVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutineVersion")

    // Android 12 Splash
    implementation ("androidx.core:core-splashscreen:1.1.0-alpha02")

    // PrefFragmentCompact (for creating Setting Layout)
   // implementation("androidx.preference:preference-ktx:$preference_version")

    // Android Testing (Espresso, JUnit)
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}