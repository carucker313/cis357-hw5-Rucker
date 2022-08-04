// Homework 5: Cash Register Program GUI
// Course: CIS357
// Due date: 8/3/2022
// Name: Christopher Rucker
// Instructor: Il-Hyung Cho
//youtube link: https://youtu.be/7ZYdjQ_6sYw

// Program description: This program implements the POS system.
/* Program features: o	Conformance to the OO Design: YES
	Support of item change: full
	Support of random access file: no
	Javadoc conformed comments on the classes, methods, and attributes: full
	Handling wrong input and invalid input: full
	Program does not crash with exceptions: does not crash
	Correct handling of payment and taxes: partial
	Overall layout of GUI and ease of use: good enough
 */
package com.example.hw5;

import javafx.application.Application;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EventListener;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

public class HW5Rucker extends Application implements EventListener {
    /**Initiliazing the variables im going to use**/
    int ctr = 0;
    float amountPaid = 0f;
    float subTotal = 0f;
    float totalSale = 0f;
    String qty;
    float totalAmountWithTax = 0f;
    float salesTax = 0.06f;
    float totalItemPrice = 0;
    boolean isValid;
    ProductCatalog pc = new ProductCatalog();

    Register register = new Register(pc);

    /**This method calculates the price after taxes**/
    public static String computePrice(double price, double tax) {

        double salesTax = price * tax;
        double total = salesTax + price;
        return String.valueOf(Math.round(total*100)/100.0);

    }
    @Override

    public void start(Stage primaryStage) throws IOException {



        ArrayList<String> storedItems = new ArrayList<>();
        /** intializing the panes used for both scenes */
        FlowPane flowPane = new FlowPane();
        GridPane gridPane = new GridPane();

        /** structuring the layout of the gridpane */
        ColumnConstraints firstColumn = new ColumnConstraints();
        firstColumn.setPercentWidth(25);
        ColumnConstraints secondColumn = new ColumnConstraints();
        secondColumn.setPercentWidth(50);
        ColumnConstraints thirdColumn = new ColumnConstraints();
        thirdColumn.setPercentWidth(25);
        gridPane.getColumnConstraints().addAll(firstColumn, secondColumn, thirdColumn);
        gridPane.setAlignment(Pos.TOP_CENTER);
        gridPane.setHgap(20);
        gridPane.setVgap(5);
        gridPane.add(new Label("Item ID:"), 0, 0);


        ;
        ComboBox cBox = new ComboBox();

        /**Adding all the options for the combo box**/
        cBox.getItems().addAll(
                pc.getSpecification("A001").getID(),
                pc.getSpecification("A002").getID(),
                pc.getSpecification("A003").getID(),
                pc.getSpecification("A004").getID(),
                pc.getSpecification("A005").getID(),
                pc.getSpecification("A006").getID(),
                pc.getSpecification("A007").getID(),
                pc.getSpecification("B001").getID(),
                pc.getSpecification("B002").getID(),
                pc.getSpecification("B003").getID());



        /**Setting the properties of the combo box**/

        cBox.setPrefHeight(10);
        cBox.setPrefWidth(200);
        cBox.setScaleZ(10);
        gridPane.add(cBox, 1, 0);
        gridPane.add(new Label("Item Name:"), 0, 1);
        Label txtItemName = new Label("NA");
        gridPane.add(txtItemName, 1, 1);

        Label txtItemPrice = new Label("$0.00");
        gridPane.add(new Label("Item Price:"), 0, 2);
        gridPane.add(txtItemPrice, 1, 2);

        /**This method calculates the price after taxes**/
        cBox.setOnAction((e)->{
            txtItemName.setText(pc.getSpecification(cBox.getValue().toString()).getDescription());
            txtItemPrice.setText(" $"+pc.getSpecification(cBox.getValue().toString()).getPrice());

        });

        gridPane.add(new Label("Quantity:"), 0, 3);
        TextField quantity = new TextField();


        quantity.setPrefWidth(65);
        quantity.setPrefHeight(10);
        gridPane.add(quantity, 1, 3);
        gridPane.add(new Label("Item Total: "), 0, 4);
        Label itemTotal = new Label("$0.00");
        gridPane.add(itemTotal, 1, 4);


        Button addBtn = new Button();
        addBtn.setText("Add");
        addBtn.setPrefWidth(300);



        gridPane.add(addBtn, 1, 6);

        TextArea textArea = new TextArea();
        textArea.setPrefHeight(200);
        textArea.setPrefWidth(400);
        gridPane.add(textArea, 1, 7);


        gridPane.add(new Label("Sale Sub Total: "), 0, 8);
        Label txtSaleSubTotal = new Label("$0.00");
        gridPane.add(txtSaleSubTotal, 1, 8);

        Label txtSaleTaxTotal = new Label("$0.00");
        gridPane.add(new Label("Sale Tax Total (6%): "), 0, 9);
        gridPane.add(txtSaleTaxTotal, 1, 9);

        gridPane.add(new Label("Tendered:"), 0, 10);
        Button checkoutBtn = new Button();
        checkoutBtn.setText("Checkout");
        checkoutBtn.setPrefWidth(250);
        checkoutBtn.setScaleZ(10);
        gridPane.add(checkoutBtn, 2, 10);

        gridPane.add(new Label("Change:"), 0, 11);

        Label txtChange = new Label ("$0.00");
        gridPane.add(txtChange, 1, 11);
        /**This event gets the value that is in quantity, puts the item that was input by the user into an arraylist*/
        addBtn.setOnAction((e)->{
            try {

                qty = quantity.getText();
                totalSale = totalSale+totalAmountWithTax;

                storedItems.add(" \t" + qty + " \t" + pc.getSpecification(cBox.getValue().toString()).getDescription() + " $" + pc.getSpecification(cBox.getValue().toString()).getPrice()+"\n");

                totalItemPrice = pc.getSpecification(cBox.getValue().toString()).getPrice()* Integer.parseInt(qty);
                itemTotal.setText("$"+totalItemPrice);
                /**Calculating and setting the text to those calculations**/
                subTotal = totalItemPrice+subTotal;
                txtSaleSubTotal.setText(String.valueOf(subTotal));
                totalAmountWithTax = Float.parseFloat(computePrice(subTotal,salesTax));
                txtSaleTaxTotal.setText((computePrice(subTotal,salesTax)));
                register.enterItem(cBox.getValue().toString(),Integer.parseInt(qty));
                textArea.appendText(storedItems.get(ctr));



                /**Keeps track of the positions in the array**/
                ctr = ctr+1;



            }catch (Exception ex){
                /**Throws an exception if the wrong input is put in and informs the user**/
                ctr =0;
                storedItems.clear();


                System.out.println("!! invalid data type: enter a valid quantity");




            }



        });


        TextField checkoutBox = new TextField();
        checkoutBox.setPrefWidth(65);
        checkoutBox.setPrefHeight(10);

        checkoutBtn.setOnAction((e)->{
            try {
                /**Checks if the item list is empty, if its not then the user cant buy anything and it informs them to enter some items**/
                if (!storedItems.isEmpty()) {
                    float tenderedAmt =Float.parseFloat(checkoutBox.getText());
                    Payment payment = new Payment(tenderedAmt);
                    /**This "if" block checks if the user has enough money to purchase the item**/
                    if (payment.getAmount()>=totalAmountWithTax){
                        float change = payment.getAmount() - totalAmountWithTax;
                        txtChange.setText(String.valueOf(change));

                        /**Clearing out the GUI so the user can buy another list of items after checking out**/
                        System.out.println("Transaction Completed");
                        textArea.clear();
                        txtChange.setText("");
                        txtSaleSubTotal.setText("$0.00");
                        txtSaleTaxTotal.setText("$0.00");
                        checkoutBox.clear();
                        quantity.clear();
                        txtItemName.setText("NA");
                        storedItems.clear();
                        subTotal =0;
                        ctr =0;
                    }
                    else{
                        System.out.println("! ! not enough");
                    }
                }else {
                    System.out.println("!! There are no items");
                }



                /**informs the user if they dont enter a valid number**/
            }catch (NumberFormatException ex){
                System.out.println("!! Enter a valid amount");
            }




        });

        gridPane.add(checkoutBox, 1, 10);
        primaryStage.setTitle("POST Register");

        Button doneBtn = new Button();
        doneBtn.setText("Done");
        doneBtn.setPrefWidth(100);
        gridPane.add(doneBtn, 1, 13);

        Button saleBtn = new Button();
        saleBtn.setText("New Sale");
        saleBtn.setStyle("-fx-border-color: black;");
        saleBtn.setPrefWidth(150);
        saleBtn.setPrefHeight(35);

        /** Creating the starting scene */
        Label label = new Label();
        label.setText("Welcome to Rucker's Store !!!");
        label.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 15));
        Label label2 = new Label();
        label2.setFont(Font.font("Comic Sans Ms", FontWeight.BOLD, 15));
        label2.setText("Total Sale for today is: $0.00");


        /** Adds all the nodes to the pane and aligns it */
        flowPane.getChildren().addAll(label, saleBtn, label2);
        flowPane.setOrientation(Orientation.VERTICAL);
        flowPane.setVgap(60);
        flowPane.setAlignment(Pos.CENTER);


        /** Creating the scene */

        Scene scene = new Scene(flowPane, 300, 250);
        Scene scene2 = new Scene(gridPane, 600, 500);

        /**Event handlers that switches the scene when the buttons are clicked */
        saleBtn.setOnAction((e) -> {
            primaryStage.setScene(scene2);
            primaryStage.setTitle("New Sale");
            register.makeNewSale();



        });
        /**This event clears out all the data when the user is done so a ne sale can begin**/
        doneBtn.setOnAction((e) -> {
            label2.setText("Total Sale for today is:" +totalSale);

            totalSale =0;
            subTotal=0;



            primaryStage.setScene(scene);
            primaryStage.setTitle("POST Register");
            textArea.clear();
            txtChange.setText("");
            txtSaleSubTotal.setText("$0.00");
            txtSaleTaxTotal.setText("$0.00");
            checkoutBox.clear();
            quantity.clear();
            txtItemName.setText("NA");
            storedItems.clear();
            ctr =0;





        });


        /** Shows the welcome scene */
        primaryStage.setScene(scene);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch();
    }
}