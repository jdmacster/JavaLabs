package cs440.c4;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * As the main starting class for this app, we configure a view of the connect 4
 * game. We work with a game controller which maintains a game board model. The
 * game allows the user to play by dropping a checker in a specified column
 * followed by our AI agent's play.
 */
public class ConnectFourApp extends Application {

	private static final int INSTRUCTION_FONT_SIZE = 30;

	private static final Color COLOR_EMPTY_SPACE = Color.ANTIQUEWHITE;
	private static final Color COLOR_USER_SPACE = Color.RED;
	private static final Color COLOR_AGENT_SPACE = Color.YELLOW;

	public static final int MAX_COLS = 8;
	public static final int MAX_ROWS = 7;
	public static final float DISC_SIZE = 20.0f;

	// convenient handles for each of the disks in the board.
	Circle[][] checkers = new Circle[MAX_ROWS][MAX_COLS];

	// handles for the user buttons allowing the user to choose which channel
	// into which the user drops the checker/disk.
	Button[] userButtons = new Button[MAX_COLS];

	// important ui elements
	private Button btnQuit;
	private Button btnPlayAgain;

	// a pool of threads we can use when allowing the agent to compute so
	// we do not block the UI thread.
	ExecutorService executorService = Executors.newFixedThreadPool(1);

	// a critical UI componet by which we can communicate with the operator.
	Label instruction;

	/**
	 * The game controller instance hosts the game logic.
	 */
	GameController gc;
	
	public MinimaxAgent mini;
	public ImprovedMinimaxAgent betterMini;
	GameBoard board;

	/**
	 * Builds the main user interface.
	 * @throws Exception 
	 */
	@Override
	public void start(Stage stage) throws Exception {

		gc = new GameController(MAX_ROWS, MAX_COLS);
		
		mini = new MinimaxAgent();
		board = new ConnectBoard(MAX_ROWS, MAX_COLS);
		
		
		try {
			mini.initializeWithBoard(board);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		/*
		 * First we populate our view with disks that can be colored as the game
		 * progresses
		 */

		GridPane gridPane = new GridPane();
		gridPane.setGridLinesVisible(true);

		// Setting the padding around the exterior.
		gridPane.setPadding(new Insets(10, 10, 10, 10));

		// Setting the vertical and horizontal gaps between the columns
		gridPane.setVgap(5);
		gridPane.setHgap(5);

		// Our background color is blue. Shouldn't we be doing ALL
		// our styling in a separate CSS file?
		gridPane.setStyle("-fx-background-color: #1111FF;");

		gridPane.setAlignment(Pos.CENTER);

		/*
		 * At the start, we initialize our grid pane.
		 */
		for (int r = 0; r < MAX_ROWS; r++) {
			for (int c = 0; c < MAX_COLS; c++) {
				Circle disk = new Circle();
				disk.setRadius(DISC_SIZE);
				gridPane.add(disk, c, r + 2);
				checkers[r][c] = disk;
			}
		}

		/*
		 * We are about to make column buttons allowing the user to select a column in
		 * which to crop their checker. We label them and set the button's action
		 * handler. See private class below.
		 */
		EventHandler<ActionEvent> btnEventHandler = new ButtonEventHandler();

		/*
		 * Still initializing stuff. We place the buttons with suitable labels for the
		 * human operator
		 */

		for (int c = 0; c < MAX_COLS; c++) {
			Button btn = new Button();
			ImageView imageView = new ImageView(
					getClass().getResource("/cs440/c4/images/down_arrow.png").toExternalForm());
			btn.setGraphic(imageView);
			btn.getProperties().put("col", c);
			btn.setOnAction(btnEventHandler);

			this.userButtons[c] = btn;
			gridPane.add(btn, c, 0);
		}

		/*
		 * The top section of our interface will contain the instructions to the
		 * operator.
		 */
		FlowPane topPane = new FlowPane();
		topPane.setAlignment(Pos.TOP_CENTER);
		instruction = new Label("Your turn");

		instruction.setFont(Font.font(INSTRUCTION_FONT_SIZE));

		topPane.getChildren().add(instruction);

		/*
		 * The bottom section of our interface will contain buttons for quitting or
		 * restarting...not related to game play.
		 */
		FlowPane bottomPane = new FlowPane();
		bottomPane.setAlignment(Pos.TOP_CENTER);

		btnQuit = new Button("Quit");
		btnQuit.setFont(Font.font(INSTRUCTION_FONT_SIZE));
		btnQuit.setOnAction((ActionEvent ev) -> {
			executorService.shutdownNow(); // kill pending thread to exit immediately
			Platform.exit();
		});
		bottomPane.getChildren().add(btnQuit);
		
		btnPlayAgain = new Button("Replay");
		btnPlayAgain.setFont(Font.font(INSTRUCTION_FONT_SIZE));
		btnPlayAgain.setOnAction((ActionEvent ev) -> {
			executorService.shutdownNow(); // kill pending thread to exit immediately
			
			try {
				executorService.awaitTermination(100, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			ExecutorService executorService = Executors.newFixedThreadPool(1);
			
			gc.initGame();
			
			//userButtons
			
			//instruction
		});
		bottomPane.getChildren().add(btnPlayAgain);

		/*
		 * Ready to layout the main human interface
		 */
		BorderPane mainPane = new BorderPane();
		mainPane.setTop(topPane);
		mainPane.setCenter(gridPane);
		mainPane.setBottom(bottomPane);

		var scene = new Scene(mainPane, 800, 450);

		stage.setScene(scene);

		stage.setOnCloseRequest(ev -> {
			System.out.println("Stage is closing");
			executorService.shutdownNow();
		});

		gc.initGame();
		updateView();

		stage.show();
	}

	/**
	 * When the user view is updated, we re-color the board according to the game
	 * controller's game board state. We also announce the winner, but only if the
	 * game is over.
	 */
	private void updateView() {

		/*
		 * first we set the fill color for each of circles in our view of the board to
		 * match the game board.
		 */
		for (int r = 0; r < checkers.length; r++)
			for (int c = 0; c < checkers[r].length; c++) {

				if (gc.boardAt(r, c) == GameBoard.AVAIL) {
					checkers[r][c].setFill(COLOR_EMPTY_SPACE);
				} else if (gc.boardAt(r, c) == GameBoard.USER) {
					checkers[r][c].setFill(COLOR_USER_SPACE);
				} else {
					checkers[r][c].setFill(COLOR_AGENT_SPACE);
				}
			}

		/*
		 * Announce winner if needed...
		 */
		if (gc.gameIsOver()) {

			instruction.textProperty().unbind();
			if (gc.winner() == GameBoard.USER)
				instruction.setText("You win!");
			else
				instruction.setText("You lose!");

			disableUserPlayButtons(true);
		}

	}

	/**
	 * Helper method for enable and disabling the user drop checker buttons. Also
	 * keeps button disabled if the column if full of checkers.
	 * 
	 * @param state
	 */
	private void disableUserPlayButtons(boolean state) {
		for (int c = 0; c < userButtons.length; c++) {

			if (gc.boardAt(0, c) != GameBoard.AVAIL)
				userButtons[c].setDisable(true);

			else
				userButtons[c].setDisable(state);
		}
	}

	/**
	 * Instance of this class reacts to the user play when they click on a column's
	 * drop checker button. We determine the column on the board for that button and
	 * delegate to our game controller to let the user play. The game controller
	 * will maintain the game state. If the game is not over, we allow our AI
	 * adversary agent to play. In both cases, we update the view to reflect the
	 * state of our game and declare a winner if needed.
	 */
	private class ButtonEventHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent e) {
			// TODO Auto-generated method stub
			Button btnClicked = (Button) e.getSource(); // which button?
			int col = (Integer) btnClicked.getProperties().get("col"); // which move?
			// System.out.println("click"+col);

			/*
			 * The user is dropping a checker in the specified color identified by the
			 * button causing the event.
			 */
			gc.userPlay(col);
			updateView();

			if (gc.gameIsOver())
				return; // no need to let agent respond

			/*
			 * Prepare a new agent task to be run in response to the user's play. We
			 */
			Task task = new Task() {

				@Override
				protected Object call() throws Exception {

					updateMessage("Agent is thinking...");
					// int agentCol = mini.nextAction();
					gc.playAgent();
					updateMessage("Your turn");

					return null;
				}

			};

			/*
			 * before the task gets started, we disable various UI controls to force the
			 * user to wait their turn.
			 */
			task.setOnRunning((succeesesEvent) -> {
				disableUserPlayButtons(true);
			});

			/*
			 * when the task returns, we update the view
			 */
			task.setOnSucceeded((succeededEvent) -> {
				disableUserPlayButtons(false);
				updateView();
			});

			// if the task changes the text property, it will automatically
			// be reflected by the instruction label text.
			instruction.textProperty().bind(task.messageProperty());

			// ready to run,...in a separate thread from our thread pool.
			executorService.execute(task);

		}

	}

	public static void main(String[] args) {
		launch();
	}

}