/**
 * Игра "Крестики-Нолики" на доске 3x3
 */

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TicTacToe extends Applet implements ActionListener {

    JButton squares[];
    JButton newGameButton;
    JLabel score;
    JLabel winsCounter;
    JLabel losesConter;
    int emptySquaresLeft = 9;

    /**
     * Метод init() -- это конструктор апплета
     */
    public void init() {
        
        // менеджер расположения апплета, шрифт и цвет
        this.setLayout(new BorderLayout());
        this.setBackground(Color.CYAN);
        Font appletFont = new Font("Monospaced", Font.BOLD, 20);
        this.setFont(appletFont);

        // New game button:
        newGameButton = new JButton("New Game");
        newGameButton.addActionListener(this);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2,1));
        topPanel.add(newGameButton);

        winsCounter = new JLabel("0");
        losesConter = new JLabel("0");
        
        JPanel scorePanel = new JPanel();
        scorePanel.setLayout(new GridLayout(1, 4));
        Font countersFont = new Font("Monospaced", Font.BOLD, 14);
        scorePanel.setFont(countersFont);
        scorePanel.add(new JLabel("Wins"));
        scorePanel.add(winsCounter);
        scorePanel.add(new JLabel("Loses"));
        scorePanel.add(losesConter);
        topPanel.add(scorePanel);
        
        this.add(topPanel, "North");
        
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(3,3));
        this.add(centerPanel, "Center");

        score = new JLabel("Your turn!");
        this.add(score, "South");

        // массив из 9 кнопок
        squares = new JButton[9];

        // создаем кнопки
        for (int i=0; i<9; i++) {

            squares[i] = new JButton();
            squares[i].addActionListener(this);
            squares[i].setBackground(Color.ORANGE);
            centerPanel.add(squares[i]);
        }
    }

    /**
     * этот метод будет обрабатывать все события
     * @param ActionEvent объект
     */
    public void actionPerformed(ActionEvent e) {

        JButton theButton = (JButton) e.getSource();
        
        // нажата кнопка <New Game!>?
        if (theButton == newGameButton) {

            for (int i=0; i<9; i++) {

                squares[i].setEnabled(true);
                squares[i].setText("");
                squares[i].setBackground(Color.ORANGE);
            }

            emptySquaresLeft = 9;
            score.setText("Your turn!");
            newGameButton.setEnabled(false);

            return;
        }

        String winner = "";

        // нажата одна из клеток?
        for (int i=0; i<9; i++) {

            if (theButton == squares[i]) {

            	if (squares[i].getText() == "X" || squares[i].getText() == "O")
            		break;
            		
                squares[i].setText("X");
                winner = lookForWinner();

                if (!"".equals(winner)) {

                    endTheGame();
                }
                else {

                    computerMove();
                    winner = lookForWinner();

                    if (!"".equals(winner)) {

                        endTheGame();
                    }
                }

                break;
            }
        } // end of for (...)

        if (winner.equals("X")) {
            score.setText("You won!");
            int wins = Integer.parseInt(winsCounter.getText());
            wins++;
            winsCounter.setText(""+wins);
        }
        else if (winner.equals("O")) {
        	score.setText("You lost!");
        	int loses = Integer.parseInt(losesConter.getText());
        	loses++;
        	losesConter.setText(""+loses);
        }
        else if (winner.equals("T")) {
            score.setText("It's a tie!");
        }
    } // end of actionPerformed()

    /**
     * Этот метод вызывается после каждого хода, чтобы узнать,
     * есть ли победитель. Он сканирует все ряды и диагонали.
     * @return "X", "O", "T" - ничья, "" - еще нет победителя
     */
    String lookForWinner() {
    	
    	String theWinner = "";
    	emptySquaresLeft--;
    	
    	if (emptySquaresLeft==0) {
    		return "T"; // ничья (tie)
    	}
    	
    	// Ряд 1 (элементы: 0,1,2)
    	if (!squares[0].getText().equals("")
    		&& squares[0].getText().equals(squares[1].getText())
    		&& squares[0].getText().equals(squares[2].getText())) {
    		
    		theWinner = squares[0].getText();
    		highlightWinner(0,1,2);
    	
    	// Ряд 2 (элементы: 3,4,5)
    	} else if (!squares[3].getText().equals("")
       		&& squares[3].getText().equals(squares[4].getText())
       		&& squares[3].getText().equals(squares[5].getText())) {
     
    		theWinner = squares[3].getText();
    		highlightWinner(3,4,5);
    		
    	// Ряд 3 (элементы: 6,7,8)
    	} else if (!squares[6].getText().equals("")
         	&& squares[6].getText().equals(squares[7].getText())
           	&& squares[6].getText().equals(squares[8].getText())) {
         
        	theWinner = squares[6].getText();
        	highlightWinner(6,7,8);
        	
        // Колонка 1 (элементы: 0,3,6)
        } else if (!squares[0].getText().equals("")
            && squares[0].getText().equals(squares[3].getText())
            && squares[0].getText().equals(squares[6].getText())) {
             
            theWinner = squares[0].getText();
            highlightWinner(0,3,6);

        // Колонка 2 (элементы: 1,4,7)
        } else if (!squares[1].getText().equals("")
            && squares[1].getText().equals(squares[4].getText())
            && squares[1].getText().equals(squares[7].getText())) {
                 
            theWinner = squares[1].getText();
            highlightWinner(1,4,7);

        // Колонка 3 (элементы: 2,5,8)
        } else if (!squares[2].getText().equals("")
            && squares[2].getText().equals(squares[5].getText())
            && squares[2].getText().equals(squares[8].getText())) {
                 
            theWinner = squares[2].getText();
            highlightWinner(2,5,8);
        
        // Диагональ 1 (элементы: 0,4,8)
        } else if (!squares[0].getText().equals("")
            && squares[0].getText().equals(squares[4].getText())
            && squares[0].getText().equals(squares[8].getText())) {
                         
            theWinner = squares[0].getText();
            highlightWinner(0,4,8);

        // Диагональ 2 (элементы: 2,4,6)
        } else if (!squares[2].getText().equals("")
            && squares[2].getText().equals(squares[4].getText())
            && squares[2].getText().equals(squares[6].getText())) {
                         
            theWinner = squares[2].getText();
            highlightWinner(2,4,6);
        }
    	
    	return theWinner;
    }
    
    /**
     * Этот метод ищет лучший компьютерный ход
     */
    void computerMove() {
    	
    	int selectedSquare;
    	
    	// сначала ищем строку где нехватает нолика
    	selectedSquare = findEmptySquare("O");
    	
    	// если нет, то пытаемся не дать оппоненту поставить крестик
    	if (selectedSquare == -1) {
    		selectedSquare = findEmptySquare("X");
    	}
    	
    	// иначе, пытаемся занять центральную клетку
    	if ( (selectedSquare == -1)
    			&& (squares[4].getText().equals("")) ) {
    		
    		selectedSquare = 4;
    	}
    	
    	// иначе просто занимаем рандомную свободную
    	if (selectedSquare == -1) {
    		selectedSquare = getRandomSquare();
    	}
    	
    	squares[selectedSquare].setText("O");    	
    }
    
    /**
     * Этот метод проверяет каждый ряд, колонку или диагональ
     * чтобы узнать, есть ли там две клетки с одинаковыми
     * надписями и пустой клеткой
     * @param		player - "X" или "O"
     * @return		номер пустой клетки
     * 				или -1 если ничего не найдено
     */
    int findEmptySquare(String player) {

    	int weight[] = new int[9];

        for (int i=0; i<9; i++) {

            if (squares[i].getText().equals("O")) {
                weight[i] = -1;
            } else if (squares[i].getText().equals("X")) {
                weight[i] = 1;
            } else {
                weight[i] = 0;
            }
        }

        int twoWeights = player.equals("O") ? -2 : 2;

        // есть ли в ряду один две одинаковые клетки и одна пустая
        if (weight[0] + weight[1] + weight[2] == twoWeights) {

            if (weight[0] == 0)
                return 0;
            else if (weight[1] == 0)
                return 1;
            else
                return 2;
        }
        // есть ли в ряду два две одинаковые клетки и одна пустая
        if (weight[3] + weight[4] + weight[5] == twoWeights) {

            if (weight[3] == 0)
                return 3;
            else if (weight[4] == 0)
                return 4;
            else
                return 5;
        }
        // есть ли в ряду три две одинаковые клетки и одна пустая
        if (weight[6] + weight[7] + weight[8] == twoWeights) {

            if (weight[6] == 0)
                return 6;
            else if (weight[7] == 0)
                return 7;
            else
                return 8;
        }

        // есть ли в колонке один две одинаковые клетки и одна пустая
        if (weight[0] + weight[3] + weight[6] == twoWeights) {

            if (weight[0] == 0)
                return 0;
            else if (weight[3] == 0)
                return 3;
            else
                return 6;
        }
        // есть ли в колонке два две одинаковые клетки и одна пустая
        if (weight[1] + weight[4] + weight[7] == twoWeights) {

            if (weight[1] == 0)
                return 1;
            else if (weight[4] == 0)
                return 4;
            else
                return 7;
        }
        // есть ли в колонке три две одинаковые клетки и одна пустая
        if (weight[2] + weight[5] + weight[8] == twoWeights) {

            if (weight[2] == 0)
                return 2;
            else if (weight[5] == 0)
                return 5;
            else
                return 8;
        }

        // есть ли в первой диагонали две одинаковые клетки и одна пустая
        if (weight[0] + weight[4] + weight[8] == twoWeights) {

            if (weight[0] == 0)
                return 0;
            else if (weight[4] == 0)
                return 4;
            else
                return 8;
        }
        // есть ли во второй диагонали две одинаковые клетки и одна пустая
        if (weight[2] + weight[4] + weight[6] == twoWeights) {

            if (weight[2] == 0)
                return 2;
            else if (weight[4] == 0)
                return 4;
            else
                return 6;
        }
        
        // не найдено двух одинаковых соседних клеток
        return -1;
    }

    int getRandomSquare() {

        boolean gotEmptySquare = false;

        int selectedSquare = -1;

        do {

            selectedSquare = (int) (Math.random() * 9);

            if (squares[selectedSquare].getText().equals("")) {
                gotEmptySquare = true; // чтобы закончить цикл
            }
        } while (!gotEmptySquare);

        return selectedSquare;
    }

    void highlightWinner(int win1, int win2, int win3) {

        squares[win1].setBackground(Color.CYAN);
        squares[win2].setBackground(Color.CYAN);
        squares[win3].setBackground(Color.CYAN);
    }

    void endTheGame() {

        newGameButton.setEnabled(true);

        for (int i=0; i<9; i++) {

            squares[i].setEnabled(false);
        }
    }
    
    public void main(String[] args) {
    	
    	this.init();
    }
    
}
