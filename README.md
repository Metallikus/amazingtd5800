# AMazingTD — Amazing TD for Android

A remake of **Amazing TD** — a free-to-play J2ME tower-defense game created by **Johan Krüger in 2010** for the
Nokia 5800 XpressMusic (the jar's manifest: `MIDlet-Name: AMazingTD`, `MIDlet-Vendor: Johan Kruger`,
`MIDlet-Version: 0.99.33`) — as a normal Android app. I made it because my wife really loved that game, and
there is no way left to play it.

The port is a line-of-logic port of the original jar (decompiled with CFR), class by class. There are no
per-screen Activities and no XML layouts: one `Activity` hosts one `Screen`, a `View` that draws in the
original **360×640** design space (the 5800 XpressMusic's portrait screen) and scales it to the device, mapping touches back
through the same scale. Every ported class keeps an `Original: <obfuscated name>` tag in its Javadoc, so any
line here can be traced back to the decompiled source.

Everything the original had is here: **19 levels**, **11 towers**, **39 enemy types**, the main menu, level
select, instructions, options, high scores, the pause menu, and the cheat code.

## What differs from the original

1. **Fonts.** The original drew its text from PNG glyph sheets and relied on the phone's system fonts. Both are
   replaced with free (SIL Open Font License 1.1) TrueType fonts, bundled in `app/src/main/assets/fonts/`:
   *Pixeloid Sans* replaces the 13 px pixel sheet, *Liberation Sans* (Regular) replaces the 16 px MS-Sans-Serif
   sheet. `TrueTypeFont` picks a `textSize` whose `ascent + descent` equals the old glyph-box height, so nothing
   on screen shifts by a pixel.
2. **Splash damage is decoupled from the refresh rate.** The game advances on a fixed **50 ms** tick
   (`Screen.TICK = 50`, 20 steps/second) and explosion/splash damage is applied once per tick, never once per
   rendered frame. That is exactly what the original phone did by default — its loop slept between ticks and
   gave the game one tick each time — so a mortar shell on a 144 Hz phone now costs an enemy exactly what it
   cost on the 5800. Drawing rate and game rate are independent.
3. **Backlight flash → vibration.** The original flashed the keypad backlight; here that is a vibration pulse of
   the same length (and, as in the original, the "Vibration" setting does not gate it).
4. **Everything else is as close to the original as I could get** — the same pixel coordinates, the same 8 px
   grid snap, the same two-layer road with direction arrows, the same quirks (including the original's `Random`
   quirk and the original's habit of handing a drawer's leftover colour to the next drawer), the same obfuscated
   tower names in the shop (Autobow, Slow Tower, Mortar, Chaingun, Force Field Tower, Pulsed Laser, Detector,
   Money Tower, Sniper Tower, Tracking Laser, Missile Tower) and the same save format.

## Cheat code: unlock every level and every tower

The original's cheat is a four-corner tap gesture on the **level select** screen. All four corners are invisible
50×50 hotspots in the design space, and they must be tapped **in this exact order**:

| Step | Corner | Effect |
|------|--------|--------|
| 1 | top-left | advances the gesture, pulses 100 ms (the old backlight flash) |
| 2 | top-right | advances the gesture, pulses again |
| 3 | bottom-left | advances the gesture, pulses again |
| 4 | **bottom-right** | **toggles "all levels unlocked"** |

A wrong corner resets the sequence to step 1, so the order matters. Only the fourth tap does anything visible:
every level node lights up and becomes selectable, the four star nodes (levels 8, 10, 13, 19) and their
connector lines appear, and the two towers that are normally earned by clearing levels *without deaths* become
available in the shop immediately — Tracking Laser (normally 1 cleared level) and Missile Tower (normally 3).
Tap the same corner sequence again to switch back to your real progress.

The flag lives in memory only, exactly as in the original: cleared levels are re-read from the save file every
time the screen opens, and the cheat resets when the app restarts.

## Build

```bash
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk   # AGP 8.2 rejects JDK 8
export ANDROID_HOME=/opt/android-sdk
./gradlew assembleDebug
```

The APK lands in `app/build/outputs/apk/debug/app-debug.apk`. `minSdk 24`, `compileSdk`/`targetSdk 34`,
Java 17. `assembleRelease` needs network (the `lint-gradle` artifact is not in the local Gradle cache, so
`--offline` fails for it); `release` is debug-signed and un-minified. There are no unit or instrumentation
tests — the port is verified by building it and comparing pixels and taps against the original jar on an emulator.

## Layout

```
app/src/main/java/com/amazingtd5800/   one flat package, one file per ported class
app/src/main/assets/                   the jar's own resources: fonts/, images/, sounds/
app/src/main/res/                      launcher icon + theme only
icon/                                launcher icon SVG sources
```

Save data keeps the original's RMS record stores as private files in `getFilesDir()`, same names, same binary
format: `ATDsettings`, `clearedLevelsEasy`, `clearedLevels`, `clearedLevelsHard`. Difficulty switching deletes
the old store, as it did on the phone.

## Credits

This is a fan port of someone else's game, and almost none of the content here is mine. The same credits are
shown in-game on the **About** screen:

| Role | Who |
|------|-----|
| Original Symbian game (2010) | Johan Krüger |
| This Android remake | Pavel Chalov |
| Graphics | Zuul |
| Sound effects | James Tubbritt (Fxhome.com), Brettsta (Fxhome.com) |
| Testing and balancing original game | Zuul, XRC Kingkoning |
| Level design | level 15 — jinsk8er · level 16 — XRC Kingkoning · level 17 — Zuul |

The sprites, enemy sheets, backgrounds and sounds under `app/src/main/assets/` came out of the jar exactly as
they were, unmodified; the port claims no authorship over them. The only assets the port swaps in are the two
fonts, both free and both shipped with their licence — *Pixeloid Sans* (GGBotNet) and *Liberation Sans* (Red
Hat), each under the SIL Open Font License 1.1; see the `License.txt`/`LICENSE.txt` next to each font. The
original shipped no licence files for its art or audio (it was a hobby freeware game that used the phone's
built-in system fonts as-is), so this port ships none either.
