# Release-notes card sources

The `groovy6_*.png` cards in the parent directory are HTML pages
screenshotted with headless Chrome:

    "/Applications/Google Chrome.app/Contents/MacOS/Google Chrome" --headless=new \
      --disable-gpu --screenshot=../groovy6_summary.png \
      --window-size=1176,648 --force-device-scale-factor=2 --hide-scrollbars \
      file://$PWD/summary.html

Window sizes differ per card (each is trimmed to its content), so use the
height that matches:

| source           | png                     | window size |
| ---------------- | ----------------------- | ----------- |
| `summary.html`   | `groovy6_summary.png`   | 1176x648    |
| `async.html`     | `groovy6_async.png`     | 1176x500    |
| `batteries.html` | `groovy6_batteries.png` | 1176x672    |
| `native.html`    | `groovy6_native.png`    | 1176x486    |
| `nullsafety.html`| `groovy6_null.png`      | 1176x576    |
| `select.html`    | `groovy6_select.png`    | 1176x636    |
| `errorformat.html`| `groovy6_errorformat.png` | 1176x690  |

Palette matches the blog cards: background `#10141d`, panel `#161b26`,
border `#2a3140`, accent `#6cb6ff`, good `#7ee787`/`#4caf7d`,
warn `#f0b35e`, bad `#ffa198`/`#c26049`.

Stats on the summary card are counted, not estimated -- 8 new optional
modules and 13 incubating features from the headings in `groovy-6.0.adoc`,
and 250+ from `@since 6.0.0` markers on public static methods in the
runtime GDK classes. Re-check them if the release notes change.
