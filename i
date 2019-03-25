#!/usr/bin/env bash
./gradlew :app:installDebug
adb shell monkey -p com.tony.tang.note.app -c android.intent.category.LAUNCHER 1