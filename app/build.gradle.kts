import com.android.build.api.dsl.ApplicationBuildType
import java.text.SimpleDateFormat
import java.util.Date

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.hilt)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.firebase.pref)
    alias(libs.plugins.google.services)
}

fun ApplicationBuildType.stringConfigField(key: String, value: String) =
    this.buildConfigField("String", key, "\"$value\"")

fun ApplicationBuildType.booleanConfigField(key: String, value: Boolean) =
    this.buildConfigField("Boolean", key, value.toString())

android {
    namespace = "com.duylt.basejetpackcompoase"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.duylt.basejetpackcompoase"
        minSdk = 26
        targetSdk = 35
        versionCode = 100
        versionName = "1.0.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val simpleDateFormat = SimpleDateFormat("dd.M.yyyy")
        val formattedDate = simpleDateFormat.format(Date())
        base.archivesName.set("Base_Jetpack_compose_vName${versionName}_vCode${versionCode}_$formattedDate")
    }

    bundle {
        language {
            enableSplit = false
        }
    }

    buildTypes {
        val devMode = "DEV_MODE"

        debug {
            manifestPlaceholders["app_id"] = "ca-app-pub-6691965685689933~7974834840"

            booleanConfigField("build_debug", true)
            booleanConfigField(devMode, true)

            isMinifyEnabled = false
            versionNameSuffix = "-dev"
        }

        release {
            manifestPlaceholders["app_id"] = "ca-app-pub-6691965685689933~7974834840"

            booleanConfigField("build_debug", false)
            booleanConfigField(devMode, false)

            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            firebaseCrashlytics {
                mappingFileUploadEnabled = true
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    lint {
        baseline = file("lint-baseline.xml")
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // navigation component
    implementation(libs.navigation.component)
    // accompanist pager
    implementation(libs.accompanist.pager)
    // hilt
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.hilt.lifecycle.viewmodel)
    ksp(libs.hilt.android.compiler)
    ksp(libs.hilt.compiler)
    // room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)
    // run time
    implementation(libs.runtime)
    implementation(libs.runtime.livedata)
    // constraint layout
    implementation(libs.cts.compose)
    // coil compose
    implementation(libs.coil.compose)
    // firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.config.ktx)
    implementation(libs.firebase.crashlytics.ktx)
    implementation(libs.firebase.message.ktx)
    implementation(libs.firebase.pref)
    // leak
    implementation(libs.leak.cannary.android)
    // custom crash
    implementation(libs.custom.crash)
    // view model
    implementation(libs.lifecyle.viewmodel.compose)
    implementation(libs.lifecyle.viewmodel.savedstate)
    implementation(libs.lifecyle.viewmodel.ktx)
}