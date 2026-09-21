# Licences of everything in this repository except the Java sources

The Java sources are MIT (`LICENSE`), and that licence does not reach past them. What follows is what the rest
of the tree is actually under. The original game shipped no licence files at all — its jar contains only
`META-INF/MANIFEST.MF` — so this file records what the port could establish, not what a lawyer would sign off on.
Nothing here is legal advice, and nothing here claims a right the port does not have.

**If you hold a right listed below and want it handled differently, write to metalarchus@gmail.com** — this is a
non-commercial fan port, and anything it carries will be credited as you ask, replaced, or taken out.

## The game itself

| What | Whose | Terms |
|------|-------|-------|
| The original game's code — the classes this port re-expresses line by line | Johan Krüger (2010) | No licence was granted |
| The level layouts hard-coded in `Level*.java`: 15, 16, 17 | jinsk8er, XRC Kingkoning, Zuul | No licence was granted |
| The title "Amazing TD" | no one's | A title, not a mark |

All of it is carried along as a fan port, credited in the in-game About screen under the handles the original's
own credits use, and would be removed or replaced on request.

The title is the odd one out and is deliberately not claimed as anyone's property: several unrelated games are
called *Amazing TD*, and a title is not in any case what copyright protects. The port uses the name only to say
which game it reproduces — no mark is claimed in it, and no affiliation with or endorsement by anyone named above
is implied.

## Art under `assets/` — no licence granted

| Files | Author | Terms |
|-------|--------|-------|
| `app/src/main/assets/images/**` — enemy sheets, tower sheets, level backgrounds, buttons, HUD, `AmazingTD.png` | Zuul: the original's credits credit one person for **all** of the art (see below) | All rights reserved, carried along unmodified |
| `app/src/main/res/**`, `icon/**` — launcher icon and theme | the port author | MIT (`LICENSE`) |

A handle does not weaken copyright: a rights holder working under a nickname is still a rights holder, whose real
name this port simply does not know. That is why the names above are handles — they are copied from the original's
own credits rather than guessed at.

## Sounds — CC BY 3.0

All 11 files under `app/src/main/assets/sounds/` (`fireArrow`, `fireBullet`, `fireLaser`, `forceField`,
`bombExplosion`, `fireSniper`, `fireDetector`, `fireSlow`, `fireMoney`, `fireTracking`, `coinBonus` — each `.wav`)
were taken from **FxHome** (`fxhome.com/sounds`, now offline) and are used under **Creative Commons Attribution
3.0**: <https://creativecommons.org/licenses/by/3.0/>.

* **Attribution** — satisfied: the original game credited "James Tubbritt, Fxhome.com" and "Brettsta,
  Fxhome.com" on its About screen, and this file repeats the same credit. The wav files themselves carry no
  metadata (their RIFF `LIST/INFO` chunk is empty), so the credit has to live in the app and in the repo, which
  is where it lives.
* **Changes** — none. Every file is byte-identical to the copy in the original jar.
* CC BY is not share-alike, so it places no obligation on the port's own code.

## Fonts — SIL Open Font License 1.1

Both fonts the app actually loads are under OFL 1.1, which requires the licence text and the copyright notice to
travel with the font. They do, side by side in the same directory, so nothing else is needed here:

| Shipped | Author / notice | Replaces |
|---------|-----------------|----------|
| `app/src/main/assets/fonts/Pixeloid Font 0.5/PixeloidSans.ttf` | GGBotNet, Reserved Font Name "Pixeloid" — `License.txt` | the original `PF_Tempesta_Seven_13px.png` glyph sheet |
| `app/src/main/assets/fonts/Liberation Sans/LiberationSans-Regular.ttf` | © 2010 Google Corp., © 2012 Red Hat — `LICENSE.txt`, `COPYRIGHT.txt` | the original `MS_Sans_Serif_16px.png` sheet and the phone's system font |

Unmodified, so the Reserved Font Name rule (a *modified* version may not keep the name) does not come up. The
other faces and formats from those two upstream families, and the two families the port ended up not using, are
not shipped.

## The original game's own credits

Shown in-game on the About screen, repeated here so the repo carries them on its own: original game Johan
Krüger; testing and balancing Zuul, XRC Kingkoning; graphics Zuul; sound effects James Tubbritt and Brettsta
(FxHome); level design 15 — jinsk8er, 16 — XRC Kingkoning, 17 — Zuul.
