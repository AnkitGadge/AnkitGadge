import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {
    private static final int TILE_SIZE = 20;
    private static final int GRID_WIDTH = 30;
    private static final int GRID_HEIGHT = 20;
    private static final int WINDOW_WIDTH = GRID_WIDTH * TILE_SIZE;
    private static final int WINDOW_HEIGHT = GRID_HEIGHT * TILE_SIZE;
    private static final int INITIAL_DELAY_MS = 120;

    private enum Direction {UP, DOWN, LEFT, RIGHT}

    private final Deque<Point> snake = new ArrayDeque<>();
    private final Random random = new Random();
    private final Timer timer;

    private Direction direction = Direction.RIGHT;
    private Direction queuedDirection = Direction.RIGHT;
    private Point food;
    private boolean running = true;
    private int score = 0;

    public SnakeGame() {
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        snake.add(new Point(6, 10));
        snake.add(new Point(5, 10));
        snake.add(new Point(4, 10));

        spawnFood();
        timer = new Timer(INITIAL_DELAY_MS, this);
        timer.start();
    }

    private void spawnFood() {
        Point candidate;
        do {
            candidate = new Point(random.nextInt(GRID_WIDTH), random.nextInt(GRID_HEIGHT));
        } while (snake.contains(candidate));
        food = candidate;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(new Color(35, 35, 35));
        for (int x = 0; x < WINDOW_WIDTH; x += TILE_SIZE) {
            g.drawLine(x, 0, x, WINDOW_HEIGHT);
        }
        for (int y = 0; y < WINDOW_HEIGHT; y += TILE_SIZE) {
            g.drawLine(0, y, WINDOW_WIDTH, y);
        }

        g.setColor(Color.RED);
        g.fillOval(food.x * TILE_SIZE, food.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);

        boolean isHead = true;
        for (Point segment : snake) {
            g.setColor(isHead ? new Color(57, 255, 20) : new Color(0, 180, 0));
            g.fillRect(segment.x * TILE_SIZE, segment.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            isHead = false;
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.BOLD, 18));
        g.drawString("Score: " + score, 10, 22);

        if (!running) {
            g.setFont(new Font("SansSerif", Font.BOLD, 36));
            String over = "Game Over";
            FontMetrics fm = g.getFontMetrics();
            int x = (WINDOW_WIDTH - fm.stringWidth(over)) / 2;
            int y = WINDOW_HEIGHT / 2;
            g.drawString(over, x, y);

            g.setFont(new Font("SansSerif", Font.PLAIN, 18));
            String restart = "Press R to restart";
            FontMetrics fm2 = g.getFontMetrics();
            int x2 = (WINDOW_WIDTH - fm2.stringWidth(restart)) / 2;
            g.drawString(restart, x2, y + 35);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!running) {
            repaint();
            return;
        }

        direction = queuedDirection;
        Point head = snake.peekFirst();
        Point next = new Point(head);

        switch (direction) {
            case UP -> next.y--;
            case DOWN -> next.y++;
            case LEFT -> next.x--;
            case RIGHT -> next.x++;
        }

        if (next.x < 0 || next.x >= GRID_WIDTH || next.y < 0 || next.y >= GRID_HEIGHT || snake.contains(next)) {
            running = false;
            repaint();
            return;
        }

        snake.addFirst(next);

        if (next.equals(food)) {
            score += 10;
            spawnFood();
        } else {
            snake.removeLast();
        }

        repaint();
    }

    private void restartGame() {
        snake.clear();
        snake.add(new Point(6, 10));
        snake.add(new Point(5, 10));
        snake.add(new Point(4, 10));
        direction = Direction.RIGHT;
        queuedDirection = Direction.RIGHT;
        score = 0;
        running = true;
        spawnFood();
        repaint();
    }

    private boolean isOpposite(Direction a, Direction b) {
        return (a == Direction.UP && b == Direction.DOWN)
                || (a == Direction.DOWN && b == Direction.UP)
                || (a == Direction.LEFT && b == Direction.RIGHT)
                || (a == Direction.RIGHT && b == Direction.LEFT);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        Direction candidate = null;

        if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) candidate = Direction.UP;
        else if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) candidate = Direction.DOWN;
        else if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) candidate = Direction.LEFT;
        else if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) candidate = Direction.RIGHT;
        else if (key == KeyEvent.VK_R && !running) {
            restartGame();
            return;
        }

        if (candidate != null && !isOpposite(direction, candidate)) {
            queuedDirection = candidate;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Snake Game");
            SnakeGame gamePanel = new SnakeGame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.add(gamePanel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            gamePanel.requestFocusInWindow();
        });
    }
}
