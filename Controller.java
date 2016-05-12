package calculator;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.input.KeyEvent;

public class Controller {

	enum CalcAction {
		ADD, SUBTRACT, MULTIPLY, DIVIDE,
	};

	private CalcAction nextAction = CalcAction.ADD; // the next action to be
													// done
	private boolean clearInputField = false; // is the input field to be
												// cleared?
	private boolean resultPressed = false; // is the result button pressed
											// immediately before?
	private int value = 0;

	private boolean dbg = true; // For debugging messages

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button nine;

	@FXML
	private Button six;

	@FXML
	private Button divideButton;
	final private String divideButtonLabel = "/"; // final because of switch!

	@FXML
	private Button one;

	@FXML
	private Button seven;

	@FXML
	private Button addButton;
	final private String addButtonLabel = "+"; // these are the labels for the
												// buttons
	// Why I do this? At first it is a security reason for the fxml file. If
	// someone edits it later
	// the whole program would not work as expected.
	// Then I want to use the content of the button as an indicator for the
	// wanted operation. So I
	// have to make sure that the compared String is _always_ identical to the
	// label of the button.
	// switch() allows only constants to compare with. So I have to use them
	// final.

	@FXML
	private Button two;

	@FXML
	private Button three;

	@FXML
	private Button eight;

	@FXML
	private Button zero;

	@FXML
	private Label ioput;

	@FXML
	private Button subtractButton;
	final private String subtractButtonLabel = "-";

	@FXML
	private Button four;

	@FXML
	private Button eraseButton;
	final private String eraseButtonLabel = "C";

	@FXML
	private Button resultButton;
	final private String resultButtonLabel = "=";

	@FXML
	private Button five;

	@FXML
	private Button multiplyButton;
	final private String multiplyButtonLabel = "*";

	@FXML
	private BorderPane border;

	/**
	 * Triggered when the ENTER-key is pressed or the =-Sign is clicked.
	 * 
	 * @param event
	 * @throws Exception
	 *             Should never happen!
	 */
	@FXML
	void resultButtonPressed(ActionEvent event) throws Exception {
		if (dbg)
			System.out.println("Result pressed.");
		lastAction();
		ioput.setText("" + value);
		clearInputField = true;
		resultPressed = true;
	}

	/**
	 * Triggered when the C-Button is clicked or the ESC/BACKSPACE key is
	 * pressed.
	 * 
	 * @param event
	 */
	@FXML
	void cancelButtonPressed(ActionEvent event) {
		if (dbg)
			System.out.println("Cancel pressed.");
		ioput.setText("");
		value = 0;
		resultPressed = false;
		clearInputField = false;
		nextAction = CalcAction.ADD;
	}

	/**
	 * This function is triggered when a button with calculating value is
	 * clicked or the specific key is pressed.
	 * 
	 * @param event
	 * @throws Exception
	 *             Should never happen!
	 */
	@FXML
	void calcButtonPressed(ActionEvent event) throws Exception {
		lastAction(); // do the last Action (from nextAction:CalcAction)
		switch (((Button) event.getSource()).getText()) {
		case addButtonLabel:
			if (dbg)
				System.out.println("Add button pressed.");
			nextAction = CalcAction.ADD;
			break;
		case subtractButtonLabel:
			if (dbg)
				System.out.println("Subtract button pressed.");
			nextAction = CalcAction.SUBTRACT;
			break;
		case multiplyButtonLabel:
			if (dbg)
				System.out.println("Multiply button pressed.");
			nextAction = CalcAction.MULTIPLY;
			break;
		case divideButtonLabel:
			if (dbg)
				System.out.println("Divide button pressed.");
			nextAction = CalcAction.DIVIDE;
			break;
		default:
			throw new UnsupportedOperationException("Programmierfehler!");
		}
		ioput.setText("" + value);
		clearInputField = true;
		resultPressed = false;
	}

	/**
	 * Here is all the action: the calculating process.
	 * 
	 * @throws Exception
	 *             Should never happen!
	 */
	private void lastAction() throws Exception {
		int input = ioput.getText().length() == 0 ? 0 : Integer.parseInt(ioput.getText());
		switch (nextAction) {
		case ADD:
			if (!clearInputField)
				value += input;
			break;
		case SUBTRACT:
			if (!clearInputField)
				value -= input;
			break;
		case MULTIPLY:
			if (!clearInputField)
				value *= input;
			break;
		case DIVIDE:
			if (!clearInputField && input != 0)
				value /= input;
			break;
		default:
			throw new IllegalStateException("Programmierfehler!");
		}
	}

	/**
	 * This function decides what to do when a number button is triggered.
	 * Dependent of several requirements it expands the number in the label or
	 * clears it completely, leaving just the latest number.
	 * 
	 * @param event
	 */
	@FXML
	void numberButtonPressed(ActionEvent event) {
		if (dbg)
			System.out.println("Number" + ((Button) event.getSource()).getText() + " pressed.");
		if (resultPressed) {
			value = 0;
			nextAction = CalcAction.ADD;
		}
		if (clearInputField) {
			ioput.setText(((Button) event.getSource()).getText());
		} else {
			if (ioput.getText().length() < 10)
				ioput.setText(ioput.getText() + ((Button) event.getSource()).getText());
		}
		clearInputField = false;
		resultPressed = false;
	}

	/**
	 * This function is just for input via keyboard.
	 * 
	 * @param event
	 */
	@FXML
	void globalKeyPressed(KeyEvent event) {
		switch (event.getCode()) {
		case NUMPAD0:
		case DIGIT0:
			zero.fire();
			break;
		case NUMPAD1:
		case DIGIT1:
			one.fire();
			break;
		case NUMPAD2:
		case DIGIT2:
			two.fire();
			break;
		case NUMPAD3:
		case DIGIT3:
			three.fire();
			break;
		case NUMPAD4:
		case DIGIT4:
			four.fire();
			break;
		case NUMPAD5:
		case DIGIT5:
			five.fire();
			break;
		case NUMPAD6:
		case DIGIT6:
			six.fire();
			break;
		case NUMPAD7:
		case DIGIT7:
			seven.fire();
			break;
		case NUMPAD8:
		case DIGIT8:
			eight.fire();
			break;
		case NUMPAD9:
		case DIGIT9:
			nine.fire();
			break;
		case ENTER:
			resultButton.fire();
			break;
		case ADD:
		case PLUS:
			addButton.fire();
			break;
		case SUBTRACT:
		case MINUS:
			subtractButton.fire();
			break;
		case MULTIPLY:
		case ASTERISK:
			multiplyButton.fire();
			break;
		case DIVIDE:
		case SLASH:
			divideButton.fire();
			break;
		case BACK_SPACE:
		case ESCAPE:
			eraseButton.fire();
			break;
		default:
		}
	}

	/**
	 * After the default initialization the labels for the calculating buttons
	 * are set.
	 */
	@FXML
	void initialize() {
		assert nine != null : "fx:id=\"nine\" was not injected: check your FXML file 'View.fxml'.";
		assert six != null : "fx:id=\"six\" was not injected: check your FXML file 'View.fxml'.";
		assert divideButton != null : "fx:id=\"divideButton\" was not injected: check your FXML file 'View.fxml'.";
		assert one != null : "fx:id=\"one\" was not injected: check your FXML file 'View.fxml'.";
		assert seven != null : "fx:id=\"seven\" was not injected: check your FXML file 'View.fxml'.";
		assert addButton != null : "fx:id=\"addButton\" was not injected: check your FXML file 'View.fxml'.";
		assert two != null : "fx:id=\"two\" was not injected: check your FXML file 'View.fxml'.";
		assert three != null : "fx:id=\"three\" was not injected: check your FXML file 'View.fxml'.";
		assert eight != null : "fx:id=\"eight\" was not injected: check your FXML file 'View.fxml'.";
		assert zero != null : "fx:id=\"zero\" was not injected: check your FXML file 'View.fxml'.";
		assert ioput != null : "fx:id=\"ioput\" was not injected: check your FXML file 'View.fxml'.";
		assert subtractButton != null : "fx:id=\"subtractButton\" was not injected: check your FXML file 'View.fxml'.";
		assert four != null : "fx:id=\"four\" was not injected: check your FXML file 'View.fxml'.";
		assert eraseButton != null : "fx:id=\"eraseButton\" was not injected: check your FXML file 'View.fxml'.";
		assert resultButton != null : "fx:id=\"resultButton\" was not injected: check your FXML file 'View.fxml'.";
		assert five != null : "fx:id=\"five\" was not injected: check your FXML file 'View.fxml'.";
		assert multiplyButton != null : "fx:id=\"multiplyButton\" was not injected: check your FXML file 'View.fxml'.";
		assert border != null : "fx:id=\"border\" was not injected: check your FXML file 'View.fxml'.";

		divideButton.setText(divideButtonLabel);
		addButton.setText(addButtonLabel);
		subtractButton.setText(subtractButtonLabel);
		eraseButton.setText(eraseButtonLabel);
		resultButton.setText(resultButtonLabel);
		multiplyButton.setText(multiplyButtonLabel);
	}
}