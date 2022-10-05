package com.hfad.newcalc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Author: Samantha Rindgen
 * This program allows Android Studio to generate a calculator app
 * that carries out desired calculator functions.
 */
public class MainActivity extends AppCompatActivity {

    String lastNumber = ""; //The last number in the textview
    boolean opJustPressed = false; //True if an op was just pressed
    ArrayList<String> calculation = new ArrayList<String>(); //Arraylist of the equation

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create access to buttons, spinners, and text views
        Button btnAC = findViewById(R.id.btn_ac);
        Button btnNegative = findViewById(R.id.btn_PosNeg);
        Button btnPercent = findViewById(R.id.btn_percent);
        Button btnDivide = findViewById(R.id.btn_divide);
        Button btnSeven = findViewById(R.id.btn_seven);
        Button btnEight = findViewById(R.id.btn_eight);
        Button btnNine = findViewById(R.id.btn_nine);
        Button btnMultiply = findViewById(R.id.btn_mult);
        Button btnFour = findViewById(R.id.btn_four);
        Button btnFive = findViewById(R.id.btn_five);
        Button btnSix = findViewById(R.id.btn_six);
        Button btnMinus = findViewById(R.id.btn_minus);
        Button btnZero = findViewById(R.id.btn_zero);
        Button btnDecimal = findViewById(R.id.btn_decimal);
        Button btnEquals = findViewById(R.id.btn_equals);
        Button btnOne = findViewById(R.id.one);
        Button btnTwo = findViewById(R.id.two);
        Button btnThree = findViewById(R.id.three);
        Button btnPlus = findViewById(R.id.plus);

        TextView tvCalc = (TextView) findViewById(R.id.tv_calc);

        btnOne.setOnClickListener(view -> buttonLogic(tvCalc,1));
        btnTwo.setOnClickListener(view -> buttonLogic(tvCalc,2));
        btnThree.setOnClickListener(view -> buttonLogic(tvCalc,3));
        btnFour.setOnClickListener(view -> buttonLogic(tvCalc,4));
        btnFive.setOnClickListener(view -> buttonLogic(tvCalc,5));
        btnSix.setOnClickListener(view -> buttonLogic(tvCalc,6));
        btnSeven.setOnClickListener(view -> buttonLogic(tvCalc,7));
        btnEight.setOnClickListener(view -> buttonLogic(tvCalc,8));
        btnNine.setOnClickListener(view -> buttonLogic(tvCalc,9));
        btnZero.setOnClickListener(view -> buttonLogic(tvCalc,0));

        btnPlus.setOnClickListener(view -> addOp(lastNumber, calculation, "plus"));
        btnMinus.setOnClickListener(view -> addOp(lastNumber,calculation, "minus"));
        btnMultiply.setOnClickListener(view -> addOp(lastNumber,calculation, "mult"));
        btnDivide.setOnClickListener(view -> addOp(lastNumber,calculation, "divide"));


        /**
         * Equals button allows for the calculation to be completed and
         * display the result
         **/
        btnEquals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                equalsE(lastNumber); //factor in the op
                tvCalc.setText(calculation.get(0).toString());
            }

        });

        /**
         * AC button clears the textview and clears the arraylist
         **/
        btnAC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lastNumber = "";
                calculation.clear();
                tvCalc.setText("0");
            }

        });

        /**
         * Decimal button adds a decimal to the current number if a decimal
         * does not exist currently
         */
        btnDecimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tvCalc.getText().toString().contains("."))
                {
                    //Do nothing if the decimal exists
                }
                else
                    tvCalc.setText(tvCalc.getText() + ".");
            }

        });

        /**
         * Negative button allows the user to change the inputted number to
         * negative or positive as they please
         */
        btnNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double change = Double.parseDouble(tvCalc.getText() + "");

                if(change > 0) //If the number is positive
                {
                    tvCalc.setText(String.valueOf(-(change)));
                }
                else //If the number is negative
                    tvCalc.setText(String.valueOf(Math.abs(change)));
            }

        });

        /**
         * Percent button turns the current number into a percentage
         */
        btnPercent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvCalc.setText(String.valueOf(Double.parseDouble(tvCalc.getText() + "") * .01));
            }
        });
    }

    /**
     * buttonLogic goes through and adds a number to the current number if a certain
     * number is pressed
     * @param tv the TextView
     * @param num the currently selected num
     */
    public void buttonLogic(TextView tv, int num)
    {
        for(int i = 0; i < 10; i++)
        {
            if (num == i)
            {
                lastNumber += i; //Add the current number to the end of the string
            }
        }
        tv.setText(lastNumber);
    }

    /**
     * addOp add the operation to the arraylist
     * @param last
     * @param calculation
     * @param op
     */
    public void addOp(String last, ArrayList<String> calculation, String op)
    {
        if (last.equals("")) //if no number is present
        {
            calculation.add(op);

            if (calculation.size() == 1) //If there is one other number in the arraylist
            {
                if(calculation.get(0).toString().equals("plus") || calculation.get(0).toString().equals("minus") || calculation.get(0).toString().equals("mult") || calculation.get(0).toString().equals("divide"))
                {
                    calculation.remove(0);
                    calculation.add("0");
                    calculation.add(op);
                }
            }
        }
       else //Add the last number, then add the op
        {
            calculation.add(last);
            calculation.add(op);
        }

       if(isPrevNum(calculation)) //Error checking in the arraylist
       {
           calculation.remove(calculation.size() - 2);
           lastNumber = "";
       }
       else //Any other instance, clear the current number
       {
           lastNumber = "";
       }
    }

    /**
     * isPrevNum confirms whether or not the current value was an operation
     * @param calculation the arraylist
     * @return true if the last num is an operation
     */
    private boolean isPrevNum(ArrayList<String> calculation)
    {
        int size = calculation.size();
        opJustPressed = false;

        if(calculation.size() == 1)
        {
            return opJustPressed;
        }
        else if (calculation.get(calculation.size() - 2).toString().equals("plus") || calculation.get(calculation.size() - 2).toString().equals("minus") || calculation.get(calculation.size() - 2).toString().equals("mult") || calculation.get(calculation.size() - 2).toString().equals("divide"))
        {
            opJustPressed = true;
        }
        return opJustPressed;
    }

    /**
     * mdsp calculates the current evalution called from the equalsE method
     * @param i the current operation in the arraylist being calculated
     */
    public void mdsp(int i)
    {
        String num1 = calculation.get(i - 1).toString(); //Left of operation
        String num2 = calculation.get(i + 1).toString(); //Right of operation
        String op = calculation.get(i).toString(); //Operation

        double result = 0; //Result to be printed in TextView

        if(op.equals("mult"))
        {
            result = Double.parseDouble(num1) * Double.parseDouble(num2);
        }
        else if(op.equals("divide"))
        {
            result = Double.parseDouble(num1) / Double.parseDouble(num2);
        }
        else if(op.equals("plus"))
        {
            result = Double.parseDouble(num1) + Double.parseDouble(num2);
        }
        else
        {
            result = Double.parseDouble(num1) - Double.parseDouble(num2);
        }
        //Remove the calculated numbers
        calculation.remove(i + 1);
        calculation.remove(i - 1);
        String s = String.format("%.0f", result);

        calculation.set(i - 1, s);
    }

    /**
     * equalsE evaluates multiplication and division first and carries out pemdas.
     * @param last the current value/last number
     */
    public void equalsE(String last)
    {
       if (last.equals(""))
       {
           if (calculation.size() == 1)
           {

           }
           else
           {
               calculation.remove(calculation.size() - 1);
           }
       }
       else
       {
           calculation.add(last);
       }

       //Evaluate multiplication and division
       int indexM = 0;
       int indexD = 0;
       int indexSmall = 0;

       indexM = calculation.indexOf("mult");
       indexD = calculation.indexOf("divide");

       while (indexM != -1 || indexD != -1)
       {
           indexSmall = findMinIndex("mult", "divide");
           mdsp(indexSmall);

           indexM = calculation.indexOf("mult");
           indexD = calculation.indexOf("divide");

       }

       //Evaluate addition and subtraction
        int indexP = 0;
        int indexMI = 0;

        indexP = calculation.indexOf("plus");
        indexMI = calculation.indexOf("minus");

        while (indexP != -1 || indexMI != -1)
        {
            indexSmall = findMinIndex("plus", "minus");
            mdsp(indexSmall);

            indexP = calculation.indexOf("plus");
            indexMI = calculation.indexOf("minus");

        }
       lastNumber = ""; //Empty the current number
    }

    /**
     *findMinIndex looks for the lowest possible value for pemdas
     * @param op1 The first operation
     * @param op2 The second operation
     * @return The Index desired
     */
    private int findMinIndex(String op1, String op2) {

        int indexM = 0;
        int indexD = 0;
        int indexSmall = 0;

        indexM = calculation.indexOf(op1);
        indexD = calculation.indexOf(op2);

        //If that operation is no longer present
        if(indexM == -1)
        {
            indexSmall = indexD;
        }
        else if (indexD == -1)
        {
            indexSmall = indexM;
        }
        else if (indexM < indexD)
        {
            indexSmall = indexM;
        }
        else
        {
            indexSmall = indexD;
        }
        return indexSmall;
    }

}

