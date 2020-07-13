/*
 * Junior Green
 * Mr. Bulhao
 * 14 September 2019
 * ICS 4U1
 */

package application;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Optional;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Main extends Application {

	
	Image moneyLogo = new Image("file:tip_clipart.png"); //Declare and initialize  image object for scene graphic
	ImageView moneyLogoIV = new ImageView(moneyLogo); //Declare and initialize ImageView object that takes image object to be rendered onto scene
	DecimalFormat dfFinal = new DecimalFormat("$,##0.00"); //Declare and initialize DecimalFormat object for rounding final calculations in form ($0.00)
	
	Optional<ButtonType> result; //Declare Optional object for taking users input from alert objects
	
	boolean valid = false; //Declare and initialize boolean variable
	double grandTotalA, tipAmountA, splitPersonA; //Declares 3 double variable which are used to hold values post-calculation
	double fieldTest; //Test variable which is used to test for any errors in any of the text fields
	
	public void start(Stage primaryStage) {
		try {
			
			dfFinal.setRoundingMode(RoundingMode.CEILING);
			
			//Declares and initializes Pane and scene objects
			Pane root = new Pane(); 
			Scene scene = new Scene(root, 800, 700);
			
			moneyLogoIV.setLayoutX(30); //Sets imageView object's x - coordinate
			moneyLogoIV.setLayoutY(root.getHeight() / 2 - moneyLogo.getHeight() / 2 - 60); //Sets imageView object's y coordinate relative to the Pane's dimensions 

			Alert closeWindowRequest = new Alert(AlertType.CONFIRMATION); //Declare and initialize alert object which handles when the user is trying to close application
			closeWindowRequest.initModality(Modality.APPLICATION_MODAL); //Sets window pop-up priority
			// Sets title and text within alert
			closeWindowRequest.setTitle("Tip Calculator");
			closeWindowRequest.setContentText("Are you sure you want to exit?");
			//Clears current button types and adds a YES and NO button
			closeWindowRequest.getButtonTypes().clear();
			closeWindowRequest.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
			
			Alert invalid = new Alert(AlertType.WARNING); //Declare and initialize alert object which handles user input error including exception errors
			//Sets default value of the title and text within the alert
			invalid.setContentText("Please fill in the required fields!");
			invalid.setTitle("Invalid data!");

			Label title = new Label("TIP CALCULATOR"); //Declare and initialize label for the title of program
			title.getStyleClass().add("title"); //Gets style class from CSS style sheet
			title.setPrefWidth(520);//Sets width dimension of Label
			title.setLayoutX(root.getWidth() / 2 - 260); //Sets x coordinate relative to the Pane's width
			title.setLayoutY(20); //Sets y coordinate of Label

			Label totalBill = new Label("Total bill ($):"); //Declare and initialize a label for first text field
			totalBill.getStyleClass().add("field-labels"); //Gets style class from CSS style sheet
			totalBill.setLayoutX(moneyLogoIV.getLayoutX() + moneyLogo.getWidth() + 5);//Sets x coordinate 
			totalBill.setLayoutY(moneyLogoIV.getLayoutY()); //Sets y coordinate
			
			Label totalPeople = new Label("Total people (ppl):"); //Declares and initializes a label for second text field
			totalPeople.getStyleClass().add("field-labels"); //Gets style class from CSS style sheet
			totalPeople.setLayoutX(totalBill.getLayoutX()); //Sets x coordinate of Label 
			totalPeople.setLayoutY((totalBill.getLayoutY() + (40 * 2))); //Sets y coordinate of label relative to the totalBill Label

			Label tipPercentage = new Label("Tip percentage (%):"); //Declares and initializes a label for third text field
			tipPercentage.getStyleClass().add("field-labels"); //Gets style class from CSS style sheet
			tipPercentage.setLayoutX(totalBill.getLayoutX()); //Sets x coordinates of label
			tipPercentage.setLayoutY((totalBill.getLayoutY() + (40 * 4))); //Sets y coordinate of label relative to the totalBill Label
			
			Button okay = new Button("OK"); //Declares and initializes a Button for a Button to initiate calculations
			okay.setPrefSize(200, 45); //Sets dimensions for button
			okay.setLayoutX(totalBill.getLayoutX()); //Sets x coordinate relative to the totalBill
			okay.setLayoutY(tipPercentage.getLayoutY() + 70); //Sets y coordinate relative to the tip percentage y coordinate
			okay.getStyleClass().add("button"); //Gets style class from CSS style sheet

			Button clear = new Button("CLEAR"); //Declare and initializes a Button that clears all texts fields 
			clear.setPrefSize(200, 45); //Sets dimensions of the button
			clear.setLayoutX(totalBill.getLayoutX() + okay.getPrefWidth() + 50); //Sets x coordinate of button relative to the totalBill text field
			clear.setLayoutY(tipPercentage.getLayoutY() + tipPercentage.getPrefHeight() + 70); //Sets y coordinate of Button relative to the tip percentage field
			clear.getStyleClass().add("button"); //Gets style class from CSS style sheet


			Label grandTotal = new Label("GRANDTOTAL:"); //Declares and initializes a Label for fourth text fields
			grandTotal.getStyleClass().add("field-labels"); //Gets style class from CSS style sheet
			grandTotal.setLayoutX(totalBill.getLayoutX()); //Sets x coordinate of label relative to the totalBill
			grandTotal.setLayoutY(okay.getLayoutY() + okay.getPrefHeight() + 50); //Sets y coordinate of label relative to the "okay" button

			Label tipAmount = new Label("TIPAMOUNT:"); // Declares and initializes a Label object for the fifth text field
			tipAmount.getStyleClass().add("field-labels"); //Gets style class from CSS style sheet
			tipAmount.setLayoutX(totalBill.getLayoutX()); //Sets x coordinate relative to the totalBill label
			tipAmount.setLayoutY(okay.getLayoutY() + okay.getPrefHeight() + 50 + (40 * 2)); //Sets y coordinate of label relative to the "okay" button

			Label splitPerson = new Label("TOTAL/PERSON:"); //Declares and initializes a label for the sixth text field
			splitPerson.getStyleClass().add("field-labels"); //Gets style class from CSS style sheet
			splitPerson.setLayoutX(totalBill.getLayoutX()); //Sets x coordinate relative to the totalBill label
			splitPerson.setLayoutY(okay.getLayoutY() + okay.getPrefHeight() + 50 + (40 * 4)); //Sets y coordinate of label relative to the "okay" button

			TextField one = new TextField("0"); //Declares and initializes first text field
			one.getStyleClass().add("text-field"); //Gets style class from CSS style sheet*
			one.setPrefWidth(250); //Sets width dimension*
			one.setLayoutX(okay.getLayoutX() + okay.getPrefWidth()); //Sets x coordinate relative to the okay button*
			one.setLayoutY(totalBill.getLayoutY()); //Sets y coordinate of label relative to the total bill label*

			TextField two = new TextField("1"); //Declares and initializes second text field
			two.getStyleClass().add("text-field");//*
			two.setPrefWidth(250);//*
			two.setLayoutX(okay.getLayoutX() + okay.getPrefWidth());
			two.setLayoutY(totalPeople.getLayoutY());//*

			TextField three = new TextField("0");//Declares and initializes third text field
			three.getStyleClass().add("text-field");//*
			three.setPrefWidth(250);//*
			three.setLayoutX(okay.getLayoutX() + okay.getPrefWidth());//*
			three.setLayoutY(tipPercentage.getLayoutY());//*

			TextField four = new TextField("0"); //Declares and initializes fourth text field
			four.getStyleClass().add("text-field");//*
			four.setPrefWidth(250);//*
			four.setLayoutX(okay.getLayoutX() + okay.getPrefWidth());//*
			four.setLayoutY(grandTotal.getLayoutY() - 5);//*

			TextField five = new TextField("0"); //Declares and initializes fifth text field
			five.getStyleClass().add("text-field");//*
			five.setPrefWidth(250);//*
			five.setLayoutX(okay.getLayoutX() + okay.getPrefWidth());//*
			five.setLayoutY(tipAmount.getLayoutY() - 5);//*

			TextField six = new TextField("0");//Declares and initializes sixth text field
			six.getStyleClass().add("text-field");//*
			six.setPrefWidth(250);//*
			six.setLayoutX(okay.getLayoutX() + okay.getPrefWidth());//*
			six.setLayoutY(splitPerson.getLayoutY() - 5);//*

			okay.setOnAction(new EventHandler<ActionEvent>() //all calculations when user clicks the okay button
			{
				public void handle(ActionEvent e) 
				{
					try //Tries to convert string in first text field to a double
					{	
						fieldTest = Double.parseDouble(one.getText());
						valid = true;
					} 
					catch (NumberFormatException e2)  //Catches exception and informs user of error by changing text field background and showing an alert
					{
						one.requestFocus();
						one.setStyle("-fx-control-inner-background: red");
						invalid.setContentText("Please fill in the required fields!");
						invalid.showAndWait();
						valid = false;
					}
					if (valid == true) //If first text field works do same process for second text field
					{
						try 
						{
							fieldTest = Double.parseDouble(two.getText());
							valid = true;
						}
						catch (NumberFormatException e2) 
						{
							two.requestFocus();
							two.setStyle("-fx-control-inner-background: red");
							invalid.setContentText("Please fill in the required fields!");
							invalid.showAndWait();
							valid = false;
						}
					}
					if (valid == true) //If second text field works do same process for third text field
					{
						try 
						{
							fieldTest = Double.parseDouble(three.getText());
							valid = true;
						} 
						catch (NumberFormatException e2) 
						{
							three.requestFocus();
							invalid.setContentText("Please fill in the required fields!");
							invalid.showAndWait();
							valid = false;
						}
					}
					
					if (valid == true) //If the 3 text fields do not generate an NumberFormat exception 
					{
						do
						{
							if (Double.parseDouble(one.getText()) < 0) //If user enters a value less that 0 pop up with an alert and informs user of error and cancels calculation process
							{
								invalid.setContentText("The bill amount cannot be < 0");
								invalid.showAndWait();
								one.requestFocus();
								valid = false;
								break;
							}
							else if (Double.parseDouble(two.getText()) < 1) //If user enters a value less that 1 pop up with an alert and informs user of error and cancels calculation process
							{
								invalid.setContentText("You cannot enter less than 1 person");
								invalid.showAndWait();
								two.requestFocus();
								valid = false;
								break;
							}
							else if (Double.parseDouble(three.getText()) < 0) //If user enters a value less that 0 pop up with an alert and informs user of error and cancels calculation process
							{
								invalid.setContentText("The tip percentage cannot be < 0");
								invalid.showAndWait();
								three.requestFocus();
								valid = false;
								break;
							}
							else //If all checks preceding this are correct go through with calculations
							{
								valid = true;
								break;
							}
						}
						while(valid == true);
						
						//Back-up check to ensure everything user inputs is in correct format
						if(one.getStyle().contains("-fx-control-inner-background: red"))
						{
							 invalid.setContentText("Please enter amount in correct format.");
							 invalid.showAndWait();
							 one.requestFocus();
							 valid = false;
						}
						
						else if(two.getStyle().contains("-fx-control-inner-background: red"))
						{
							 invalid.setContentText("Please enter amount in correct format.");
							 invalid.showAndWait();
							 two.requestFocus();
							 valid = false;
						}
						
						else if(three.getStyle().contains("-fx-control-inner-background: red"))
						{
							 invalid.setContentText("Please enter amount in correct format.");
							 invalid.showAndWait();
							 three.requestFocus();
							 valid = false;
						}
						
						 if (valid == true)//If everything goes through proceed with calculations
						{
							grandTotalA = (Double.parseDouble(one.getText())) * ((100.0 + (Double.parseDouble(three.getText()))) / 100.0); //Calculates grand total
							tipAmountA = (Double.parseDouble(one.getText())) * ((Double.parseDouble(three.getText()) / 100.0)); //Calculates tip amount
							splitPersonA = grandTotalA / Double.parseDouble(two.getText()); //calculates cost per person
	
							four.setText(dfFinal.format(grandTotalA)); //Displays grand total in fourth text field
							five.setText(dfFinal.format(tipAmountA)); //Displays tip amount in fifth text field
							six.setText(dfFinal.format(splitPersonA)); //Displays cost per person in sixth text field
							}
					}
				}
			});
			
			one.textProperty().addListener(new ChangeListener<String>() //when user enters in an incorrect value or format turns background red and turns back white if corrected
			{
				public void changed(ObservableValue<? extends String> observable,
				String oldValue, String newValue) 
				{
					 if(one.getText().matches("[0-9]+.[0-9]{2}") || one.getText().matches("[0-9]+"))
					  { 
						 one.setStyle("-fx-control-inner-background: white"); 
					  } 
					 else
					 {
						 one.setStyle("-fx-control-inner-background: red");
					 }
				
				}
				});
			
			two.textProperty().addListener(new ChangeListener<String>() { //when user enters in an incorrect value or format turns background red and turns back white if corrected
				public void changed(ObservableValue<? extends String> observable,
				String oldValue, String newValue) 
				{
					 if((two.getText().matches("0*[1-9]+[0-9]*")))
					  { 
						 two.setStyle("-fx-control-inner-background: white"); 
					  } 
					 else
					 {
						 two.setStyle("-fx-control-inner-background: red");
					 }
				
				}
				});
			
			three.textProperty().addListener(new ChangeListener<String>() { //when user enters in an incorrect value or format turns background red and turns back white if corrected
				public void changed(ObservableValue<? extends String> observable,
				String oldValue, String newValue) 
				{
					 if(three.getText().matches("[0-9]+.[0-9]+") || three.getText().matches("[0-9]+"))
					  { 
						 three.setStyle("-fx-control-inner-background: white"); 
					  } 
					 else
					 {
					  three.requestFocus();
					  three.setStyle("-fx-control-inner-background: red");
					 }
				}
				});

			clear.setOnAction(new EventHandler<ActionEvent>() { //Clears all strings in each text field and sets focus to first text field
				public void handle(ActionEvent e) {
					
					one.setText("0");
					two.setText("1");
					three.setText("0");
					four.clear();
					five.clear();
					six.clear();
					one.requestFocus();
				}
			});
			
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			root.getChildren().addAll(moneyLogoIV, title, totalBill, totalPeople, tipPercentage, grandTotal, tipAmount,
					splitPerson, okay, clear, one, two, three, four, five, six); //Adds all components to scene
			
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() //If user makes request to close window shows confirmation alert
			{
				public void handle(WindowEvent e) 
				{
					result = closeWindowRequest.showAndWait();
					
					if (result.get() == ButtonType.YES) //Closes if user selects yes
					{
						Platform.exit();
						System.exit(0);
					}
					else //Cancels operation otherwise
					{
						e.consume();
					}	
				}
			});
			primaryStage.getIcons().add(new Image("file:title_icon.png")); //Sets task manager icon and window icon
			primaryStage.setResizable(false); //Prevents from resizing 
			primaryStage.centerOnScreen(); //Centers window on screen
			primaryStage.setTitle("Tip Calculator"); //Sets title of window 
			primaryStage.setScene(scene); //Sets current scene
			primaryStage.show(); //Makes scene visible
		} catch (Exception e) //catches any exceptions and prints it to the console
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
