package Graphing_calculator;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.*;

// import java.util.ArrayList;

public class GraphInputs implements ActionListener{

    private JButton submit = new JButton("Submit");
    private JTextArea xInputText = new JTextArea("Width: ", 1, 5);
    private JTextArea yInputText = new JTextArea("Height: ", 1, 5);
    private JDialog mainFrame;

    public GraphInputs(){
        mainFrame = new JDialog();
        mainFrame.setSize(500, 100);
        mainFrame.setLayout(new GridLayout(1, 2));
        mainFrame.setVisible(true);
        // getInputs();
    }

    // public Point getInputs(){
    //     submit.setSize(100, 100);
    //     submit.addActionListener(this);

    //     xInputText.setLineWrap(true);
    //     xInputText.setWrapStyleWord(true);
    //     yInputText.setLineWrap(true);
    //     yInputText.setWrapStyleWord(true);

    //     mainFrame.add(xInputText);
    //     mainFrame.add(yInputText);
    //     mainFrame.add(submit);
    //     mainFrame.setVisible(true);
    // }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit){
            String xValue = xInputText.getText().substring(xInputText.getText().indexOf(' ')+1); 
            String yValue = yInputText.getText().substring(yInputText.getText().indexOf(' ')+1); 
            if(xValue.equals("") || yValue.equals("")){
                System.out.println("Empty value found");
            }
            else{
                System.out.println(xValue + "x" + yValue);
            }
        }
    }
}