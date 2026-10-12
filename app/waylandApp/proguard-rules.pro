-keep class dev.dreaght.talkster.MainKt {
    public static void main(java.lang.String[]);
}

-dontwarn java.lang.invoke.MethodHandle
-keep class com.fromwau.kortex.wayland.** { *; }
-keepclassmembers class com.fromwau.kortex.wayland.** { *; }

-dontwarn kotlinx.serialization.**

-keep class com.fromwau.kern.result.** { *; }
-keepclassmembers class com.fromwau.kern.result.** { *; }
