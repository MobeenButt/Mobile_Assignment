import shutil, os

base = r"D:\6 Sem\Mobile\app\src\main\res\layout"

# ── activity_splash.xml ──────────────────────────────────────────────
splash = r"""<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@drawable/bg_splash">
    <View android:id="@+id/viewLogoGlow" android:layout_width="180dp" android:layout_height="180dp"
        android:background="@drawable/success_glow_bg" android:alpha="0.4"
        app:layout_constraintStart_toStartOf="parent" app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toTopOf="parent" app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintVerticalBias="0.33" />
    <ImageView android:id="@+id/ivSplashLogo" android:layout_width="110dp" android:layout_height="110dp"
        android:src="@drawable/ic_logo" android:scaleType="fitCenter" android:contentDescription="App Logo"
        app:layout_constraintStart_toStartOf="parent" app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toTopOf="parent" app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintVerticalBias="0.33" />
    <TextView android:id="@+id/tvSplashAppName" android:layout_width="wrap_content" android:layout_height="wrap_content"
        android:layout_marginTop="22dp" android:text="@string/splash_app_name" android:textColor="#F0B429"
        android:textSize="20sp" android:textStyle="bold" android:letterSpacing="0.14"
        android:fontFamily="sans-serif-condensed"
        app:layout_constraintStart_toStartOf="parent" app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toBottomOf="@id/ivSplashLogo" />
    <View android:id="@+id/dividerSplash" android:layout_width="72dp" android:layout_height="1.5dp"
        android:layout_marginTop="14dp" android:background="#F0B429"
        app:layout_constraintStart_toStartOf="parent" app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toBottomOf="@id/tvSplashAppName" />
    <TextView android:id="@+id/tvSplashWelcome" android:layout_width="280dp" android:layout_height="wrap_content"
        android:layout_marginTop="18dp" android:text="@string/splash_welcome" android:textColor="#C0B8D8"
        android:textSize="15sp" android:gravity="center" android:lineSpacingMultiplier="1.35"
        app:layout_constraintStart_toStartOf="parent" app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toBottomOf="@id/dividerSplash" />
    <TextView android:id="@+id/tvSplashTagline" android:layout_width="wrap_content" android:layout_height="wrap_content"
        android:layout_marginTop="10dp" android:text="@string/splash_tagline" android:textColor="#5A5A8A"
        android:textSize="12sp" android:textStyle="italic"
        app:layout_constraintStart_toStartOf="parent" app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toBottomOf="@id/tvSplashWelcome" />
    <TextView android:id="@+id/tvVersion" android:layout_width="wrap_content" android:layout_height="wrap_content"
        android:layout_marginBottom="18dp" android:text="v 1.0" android:textColor="#30506070"
        android:textSize="10sp"
        app:layout_constraintStart_toStartOf="parent" app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintBottom_toBottomOf="parent" />
</androidx.constraintlayout.widget.ConstraintLayout>"""

# ── activity_registration.xml  (redirect to same content as event_registration) ──
registration = r"""<?xml version="1.0" encoding="utf-8"?>
<!-- Redirect: actual layout is activity_event_registration.xml -->
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@color/colorBg"
    android:orientation="vertical" />"""

with open(os.path.join(base, "activity_splash.xml"), "w", encoding="utf-8") as f:
    f.write(splash)
print("splash.xml written")

with open(os.path.join(base, "activity_registration.xml"), "w", encoding="utf-8") as f:
    f.write(registration)
print("registration.xml written")

