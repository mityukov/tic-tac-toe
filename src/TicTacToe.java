/**
 * Игра "Крестики-Нолики" на доске 3x3
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TicTacToe implements ActionListener {

    JButton squares[][];
    JButton newGameButton;
    JLabel score;
    JLabel winsCounter;
    JLabel losesConter;
    int emptySquaresLeft = 9;
    
    final int matrixWalker[][][] = {
    		{ {0,0},{0,1},{0,2} }, // ряд 1
    		{ {1,0},{1,1},{1,2} }, // ряд 2
    		{ {2,0},{2,1},{2,2} }, // ряд 3
    		{ {0,0},{1,0},{2,0} }, // столбец 1
    		{ {0,1},{1,1},{2,1} }, // столбец 2
    		{ {0,2},{1,2},{2,2} }, // столбец 3
    		{ {0,0},{1,1},{2,2} }, // диагональ 1
    		{ {0,2},{1,1},{2,0} }  // диагональ 2
    };

    /**
     * Метод init() -- это конструктор апплета
     * => будет конструктором класса
     */
    TicTacToe () {
        
        JPanel windowContent = new JPanel();
    	
        // разметка окна, шрифт и цвет
        windowContent.setLayout(new BorderLayout());
        windowContent.setBackground(Color.CYAN);
        Font appletFont = new Font("Monospaced", Font.BOLD, 20);
        windowContent.setFont(appletFont);

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
        
        windowContent.add(topPanel, "North");
        
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(3,3));
        windowContent.add(centerPanel, "Center");

        score = new JLabel("Your turn!");
        windowContent.add(score, "South");

        // массив из 9 кнопок
        squares = new JButton[3][3];

        // создаем кнопки
        for (int i=0; i<3; i++) {
        	for (int j=0; j<3; j++) {

            squares[i][j] = new JButton();
            squares[i][j].addActionListener(this);
            squares[i][j].setBackground(Color.ORANGE);
            squares[i][j].setText(" "); // fix too narrow buttons
            centerPanel.add(squares[i][j]);
        	}
        }
        
        JFrame frame = new JFrame();
        frame.setContentPane(windowContent);
        frame.pack();
        frame.setVisible(true);
        // fix too narrow buttons, step 2;
        for (int i=0; i<3; i++) {
        	for (int j=0; j<3; j++) {
                squares[i][j].setText("");
        	}
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

        	for (int i=0; i<3; i++) {
            	for (int j=0; j<3; j++) {

                    squares[i][j].setEnabled(true);
                    squares[i][j].setText("");
                    squares[i][j].setBackground(Color.ORANGE);
            	}
            }

            emptySquaresLeft = 9;
            score.setText("Your turn!");
            newGameButton.setEnabled(false);

            return;
        }

        String winner = "";

        // нажата одна из клеток?
        for (int i=0; i<3; i++) {
        	for (int j=0; j<3; j++) {

	            if (theButton == squares[i][j]) {
	
	            	if (squares[i][j].getText() == "X" || squares[i][j].getText() == "O")
	            		break;
	            		
	                squares[i][j].setText("X");
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
    	
    	for (int mw=0; mw<8; mw++) { // проверяются 8 строк matrixWalker (каждая откомментирована в объявлении)
    		
    		// 1й, 2й, 3й элементы строки, столбца или диагонали:
    		String elem1 = squares[matrixWalker[mw][0][0]][matrixWalker[mw][0][1]].getText();
    		String elem2 = squares[matrixWalker[mw][1][0]][matrixWalker[mw][1][1]].getText();
    		String elem3 = squares[matrixWalker[mw][2][0]][matrixWalker[mw][2][1]].getText();
    		if (!elem1.equals("") && elem1.equals(elem2) && elem1.equals(elem3)) {
    			
    			theWinner = squares[matrixWalker[mw][0][0]][matrixWalker[mw][0][1]].getText();
    			highlightWinner(matrixWalker[mw][0][0], matrixWalker[mw][0][1],
    							matrixWalker[mw][1][0], matrixWalker[mw][1][1],
    							matrixWalker[mw][2][0], matrixWalker[mw][2][1]);
    		}
    	}
    	
    	return theWinner;
    }
    
    /**
     * Этот метод ищет лучший компьютерный ход
     */
    void computerMove() {
    	
    	int selectedSquare[];
    	
    	// сначала ищем строку где нехватает нолика
    	selectedSquare = findEmptySquare("O");
    	
    	// если нет, то пытаемся не дать оппоненту поставить крестик
    	if (selectedSquare[0] == -1) {
    		selectedSquare = findEmptySquare("X");
    	}
    	
    	// иначе, пытаемся занять центральную клетку
    	if ( (selectedSquare[0] == -1)
    			&& (squares[1][1].getText().equals("")) ) {
    		
    		selectedSquare[0] = 1; selectedSquare[1] = 1;
    	}
    	
    	// иначе просто занимаем рандомную свободную
    	if (selectedSquare[0] == -1) {
    		selectedSquare = getRandomSquare();
    	}
    	
    	
    	squares[selectedSquare[0]][selectedSquare[1]].setText("O");    	
    }
    
    /**
     * Этот метод проверяет каждый ряд, колонку или диагональ
     * чтобы узнать, есть ли там две клетки с одинаковыми
     * надписями и пустой клеткой
     * @param		player - "X" или "O"
     * @return		массив с номером пустой клетки {i,j}
     * 				или -1 в первом элементе если ничего не найдено
     */
    int [] findEmptySquare(String player) {

    	int weight[][] = new int[3][3];
    	int res[] = new int[2];

        for (int i=0; i<3; i++) {
        	for (int j=0; j<3; j++) {

                if (squares[i][j].getText().equals("O")) {
                    weight[i][j] = -1;
                } else if (squares[i][j].getText().equals("X")) {
                    weight[i][j] = 1;
                } else {
                    weight[i][j] = 0;
                }
        	}
        }

        int twoWeights = player.equals("O") ? -2 : 2;

    	for (int mw=0; mw<8; mw++) { // проверяются 8 строк matrixWalker (каждая откомментирована в объявлении)
    		
    		// 1й, 2й, 3й элементы строки, столбца или диагонали:
    		int elem1 = weight[matrixWalker[mw][0][0]][matrixWalker[mw][0][1]];
    		int elem2 = weight[matrixWalker[mw][1][0]][matrixWalker[mw][1][1]];
    		int elem3 = weight[matrixWalker[mw][2][0]][matrixWalker[mw][2][1]];
    		if (elem1 + elem2 + elem3 == twoWeights) {
    			
    			if (elem1 == 0)
    				return matrixWalker[mw][0];
    			else if (elem2 == 0)
    				return matrixWalker[mw][1];
    			else
    				return matrixWalker[mw][2];
    		}
    	}
        
        // не найдено двух одинаковых соседних клеток
        return new int[] {-1, -1};
    }

    /**
     * Этот метод выбирает случайную ячейку
     * @return         массив с номером ячейки {i,j}
     */ 
    int [] getRandomSquare() {

        boolean gotEmptySquare = false;

        int res[] = new int[2];

        do {

            res[0] = (int) (Math.random() * 3);
            res[1] = (int) (Math.random() * 3);

            if (squares[res[0]][res[1]].getText().equals("")) {
                gotEmptySquare = true; // чтобы закончить цикл
            }
        } while (!gotEmptySquare);

        return res;
    }

    /**
     * этот метод подсвечивает выигравшие кнопки
     * @param           три пары индексов двумерного массива buttons: i1,j1, i2,j2, i3,j3
     */
    void highlightWinner(int win1i, int win1j, int win2i, int win2j, int win3i, int win3j) {

        squares[win1i][win1j].setBackground(Color.CYAN);
        squares[win2i][win2j].setBackground(Color.CYAN);
        squares[win3i][win3j].setBackground(Color.CYAN);
    }

    /**
     * Этот метод активирует кнопку New Game и блокирует все кнопки
     */
    void endTheGame() {

        newGameButton.setEnabled(true);

        for (int i=0; i<3; i++) {
        	for (int j=0; j<3; j++) {

                squares[i][j].setEnabled(false);
        	}
        }
    }
    
    public static void main(String[] args) {
    	
    	TicTacToe theGame = new TicTacToe();
    }
    
}
