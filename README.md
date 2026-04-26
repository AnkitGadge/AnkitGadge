### Hi there 👋

<!--
**AnkitGadge/AnkitGadge** is a ✨ _special_ ✨ repository because its `README.md` (this file) appears on your GitHub profile.

Here are some ideas to get you started:

- 🔭 I’m currently working on ...
- 🌱 I’m currently learning ...
- 👯 I’m looking to collaborate on ...
- 🤔 I’m looking for help with ...
- 💬 Ask me about ...
- 📫 How to reach me: ...
- 😄 Pronouns: ...
- ⚡ Fun fact: ...
-->

## Snake Game

A playable browser-based Snake game is available in `snake-game.html`.

## Recommended launch (works with Python directly)

If `launch-snake.cmd` is not recognized, use this command from the repo root:

```bat
python launch-snake.py 8000
```

Then open:

```text
http://localhost:8000/snake-game.html
```

## Windows CMD launcher

Windows-specific launcher file:

- `launch-snake.cmd`

### CMD launch steps

1. Open **Command Prompt**.
2. Go to the repository root:

   ```bat
   cd C:\path\to\AnkitGadge
   ```

3. Confirm launcher files exist:

   ```bat
   dir launch-snake.cmd launch-snake.py snake-game.html
   ```

4. Run one of these:

   ```bat
   launch-snake.cmd 8000
   ```

   or

   ```bat
   python launch-snake.py 8000
   ```

5. Open `http://localhost:8000/snake-game.html`.

### Fix for `'launch-snake.cmd' is not recognized...`

- You are likely not in the project directory.
- Run `cd C:\path\to\AnkitGadge` first.
- Verify file exists with `dir launch-snake.cmd`.
- If still failing, use `python launch-snake.py 8000` (does not rely on `.cmd` lookup).
