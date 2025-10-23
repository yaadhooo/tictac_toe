import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToeGame extends JFrame {
    private JButton[][] buttons = new JButton[3][3];
    private boolean isXTurn = true;
    private JLabel statusLabel;
    private JPanel gamePanel;
    private int moveCount = 0;

    public TicTacToeGame() {
        setTitle("Tic Tac Toe 🎮");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        showWelcomeScreen();
    }

    private void showWelcomeScreen() {
        JPanel welcomePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth(), h = getHeight();
                
                // Vibrant gradient background
                GradientPaint gp = new GradientPaint(0, 0, new Color(138, 43, 226), 
                                                      w, h, new Color(255, 20, 147));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        
        welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.Y_AXIS));
        welcomePanel.setBorder(BorderFactory.createEmptyBorder(100, 50, 100, 50));

        JLabel greetingLabel = new JLabel("Hi! Welcome to Tic Tac Toe 🎮");
        greetingLabel.setFont(new Font("Arial", Font.BOLD, 28));
        greetingLabel.setForeground(Color.WHITE);
        greetingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Challenge your friend!");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        subtitleLabel.setForeground(new Color(255, 255, 255, 200));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.BOLD, 20));
        startButton.setForeground(Color.WHITE);
        startButton.setBackground(new Color(255, 165, 0));
        startButton.setFocusPainted(false);
        startButton.setBorderPainted(false);
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setMaximumSize(new Dimension(200, 50));
        startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        startButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                startButton.setBackground(new Color(255, 140, 0));
            }
            public void mouseExited(MouseEvent e) {
                startButton.setBackground(new Color(255, 165, 0));
            }
        });

        startButton.addActionListener(e -> {
            getContentPane().removeAll();
            initializeGame();
            revalidate();
            repaint();
        });

        welcomePanel.add(Box.createVerticalGlue());
        welcomePanel.add(greetingLabel);
        welcomePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        welcomePanel.add(subtitleLabel);
        welcomePanel.add(Box.createRigidArea(new Dimension(0, 50)));
        welcomePanel.add(startButton);
        welcomePanel.add(Box.createVerticalGlue());

        add(welcomePanel);
        setVisible(true);
    }

    private void initializeGame() {
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth(), h = getHeight();
                
                // Colorful gradient background for game
                GradientPaint gp = new GradientPaint(0, 0, new Color(34, 193, 195), 
                                                      0, h, new Color(253, 187, 45));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        
        mainPanel.setLayout(new BorderLayout(10, 10));

        // Top panel with status and restart button
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        statusLabel = new JLabel("Player X's Turn", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 26));
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setOpaque(true);
        statusLabel.setBackground(new Color(255, 69, 0));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JButton restartButton = new JButton("🔄 Restart");
        restartButton.setFont(new Font("Arial", Font.BOLD, 16));
        restartButton.setForeground(Color.WHITE);
        restartButton.setBackground(new Color(220, 20, 60));
        restartButton.setFocusPainted(false);
        restartButton.setBorderPainted(false);
        restartButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        restartButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                restartButton.setBackground(new Color(178, 34, 34));
            }
            public void mouseExited(MouseEvent e) {
                restartButton.setBackground(new Color(220, 20, 60));
            }
        });
        restartButton.addActionListener(e -> resetGame());

        topPanel.add(statusLabel, BorderLayout.CENTER);
        topPanel.add(restartButton, BorderLayout.EAST);

        // Game board panel
        gamePanel = new JPanel(new GridLayout(3, 3, 15, 15));
        gamePanel.setOpaque(false);
        gamePanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 30, 30));

        Color[] buttonColors = {
            new Color(255, 99, 132),
            new Color(54, 162, 235),
            new Color(255, 206, 86),
            new Color(75, 192, 192),
            new Color(153, 102, 255),
            new Color(255, 159, 64),
            new Color(199, 236, 238),
            new Color(255, 182, 193),
            new Color(144, 238, 144)
        };

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(new Font("Arial", Font.BOLD, 70));
                buttons[i][j].setFocusPainted(false);
                buttons[i][j].setBackground(buttonColors[i * 3 + j]);
                buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.WHITE, 4, true));
                buttons[i][j].setCursor(new Cursor(Cursor.HAND_CURSOR));
                buttons[i][j].setOpaque(true);
                
                final int row = i;
                final int col = j;
                final Color originalColor = buttonColors[i * 3 + j];
                
                buttons[i][j].addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        if (buttons[row][col].getText().equals("")) {
                            buttons[row][col].setBackground(originalColor.brighter());
                        }
                    }
                    public void mouseExited(MouseEvent e) {
                        if (buttons[row][col].getText().equals("")) {
                            buttons[row][col].setBackground(originalColor);
                        }
                    }
                });
                
                buttons[i][j].addActionListener(new ButtonClickListener(i, j, originalColor));
                gamePanel.add(buttons[i][j]);
            }
        }

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(gamePanel, BorderLayout.CENTER);
        add(mainPanel);
    }

    private class ButtonClickListener implements ActionListener {
        private int row, col;
        private Color originalColor;

        public ButtonClickListener(int row, int col, Color originalColor) {
            this.row = row;
            this.col = col;
            this.originalColor = originalColor;
        }

        public void actionPerformed(ActionEvent e) {
            if (!buttons[row][col].getText().equals("")) {
                return;
            }

            if (isXTurn) {
                buttons[row][col].setText("X");
                buttons[row][col].setForeground(new Color(0, 0, 139));
                statusLabel.setText("Player O's Turn");
                statusLabel.setBackground(new Color(138, 43, 226));
            } else {
                buttons[row][col].setText("O");
                buttons[row][col].setForeground(new Color(139, 0, 0));
                statusLabel.setText("Player X's Turn");
                statusLabel.setBackground(new Color(255, 69, 0));
            }

            moveCount++;
            
            if (checkWinner()) {
                String winner = isXTurn ? "X" : "O";
                highlightWinningCells();
                JOptionPane.showMessageDialog(TicTacToeGame.this, 
                    "🎉 Player " + winner + " wins!", 
                    "Game Over", 
                    JOptionPane.INFORMATION_MESSAGE);
                disableButtons();
            } else if (moveCount == 9) {
                JOptionPane.showMessageDialog(TicTacToeGame.this, 
                    "It's a draw! 🤝", 
                    "Game Over", 
                    JOptionPane.INFORMATION_MESSAGE);
            }

            isXTurn = !isXTurn;
        }
    }

    private boolean checkWinner() {
        String player = isXTurn ? "X" : "O";

        // Check rows and columns
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().equals(player) &&
                buttons[i][1].getText().equals(player) &&
                buttons[i][2].getText().equals(player)) {
                return true;
            }
            if (buttons[0][i].getText().equals(player) &&
                buttons[1][i].getText().equals(player) &&
                buttons[2][i].getText().equals(player)) {
                return true;
            }
        }

        // Check diagonals
        if (buttons[0][0].getText().equals(player) &&
            buttons[1][1].getText().equals(player) &&
            buttons[2][2].getText().equals(player)) {
            return true;
        }
        if (buttons[0][2].getText().equals(player) &&
            buttons[1][1].getText().equals(player) &&
            buttons[2][0].getText().equals(player)) {
            return true;
        }

        return false;
    }

    private void highlightWinningCells() {
        String player = isXTurn ? "X" : "O";
        Color winColor = new Color(255, 215, 0);

        // Check rows and columns
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().equals(player) &&
                buttons[i][1].getText().equals(player) &&
                buttons[i][2].getText().equals(player)) {
                buttons[i][0].setBackground(winColor);
                buttons[i][1].setBackground(winColor);
                buttons[i][2].setBackground(winColor);
                return;
            }
            if (buttons[0][i].getText().equals(player) &&
                buttons[1][i].getText().equals(player) &&
                buttons[2][i].getText().equals(player)) {
                buttons[0][i].setBackground(winColor);
                buttons[1][i].setBackground(winColor);
                buttons[2][i].setBackground(winColor);
                return;
            }
        }

        // Check diagonals
        if (buttons[0][0].getText().equals(player) &&
            buttons[1][1].getText().equals(player) &&
            buttons[2][2].getText().equals(player)) {
            buttons[0][0].setBackground(winColor);
            buttons[1][1].setBackground(winColor);
            buttons[2][2].setBackground(winColor);
            return;
        }
        if (buttons[0][2].getText().equals(player) &&
            buttons[1][1].getText().equals(player) &&
            buttons[2][0].getText().equals(player)) {
            buttons[0][2].setBackground(winColor);
            buttons[1][1].setBackground(winColor);
            buttons[2][0].setBackground(winColor);
        }
    }

    private void disableButtons() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setEnabled(false);
            }
        }
    }

    private void resetGame() {
        isXTurn = true;
        moveCount = 0;
        statusLabel.setText("Player X's Turn");
        statusLabel.setBackground(new Color(255, 69, 0));
        
        Color[] buttonColors = {
            new Color(255, 99, 132),
            new Color(54, 162, 235),
            new Color(255, 206, 86),
            new Color(75, 192, 192),
            new Color(153, 102, 255),
            new Color(255, 159, 64),
            new Color(199, 236, 238),
            new Color(255, 182, 193),
            new Color(144, 238, 144)
        };
        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setEnabled(true);
                buttons[i][j].setBackground(buttonColors[i * 3 + j]);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TicTacToeGame());
    }
}