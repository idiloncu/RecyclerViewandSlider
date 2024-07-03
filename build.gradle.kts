// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript{
    val exoplayerVersion by extra { "2.18.5" }

}
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
}