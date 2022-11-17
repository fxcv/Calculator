import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calc{
    private JTextField textField;
    private String[] elements = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "+", "-", "*", "/", "C", "=", "."};
    public void go(){
        setUpGui();
    }

    private void setUpGui(){
        JFrame frame = new JFrame("Calculator");
        ActionListener buttonListener = new buttonListener();
        Container c = frame.getContentPane();
        JPanel box = new JPanel();
        textField = new JTextField("");

        box.setBackground(Color.GRAY);
        textField.setBackground(Color.LIGHT_GRAY);
        textField.setPreferredSize(new Dimension(200, 200));
        textField.setEditable(false);
        textField.setFont(new Font("Courier", Font.PLAIN, 50));
        for(int i = 0; i<elements.length; i++){
            JButton button = new JButton(elements[i]);
            button.addActionListener(buttonListener);
            button.setPreferredSize(new Dimension(150, 100));
            box.add(button);
        }
        c.add(BorderLayout.NORTH, textField);
        c.add(BorderLayout.CENTER, box);
        frame.setSize(600, 900);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void add(StringBuilder number1, String equation, StringBuilder number2){
        double result = 0;
        try{
            if(number2.length()>0 && equation.length()==1){
                double a = Double.parseDouble(number1.toString());
                double b = Double.parseDouble(number2.toString());
                switch(equation){
                    case "+": result = a + b; break;
                    case "-": result = a - b; break;
                    case "*": result = a * b; break;
                    case "/": result = a / b;
                }
            }
        } catch (ArithmeticException ex){
            System.out.println("Nie dziel przez 0");
        } finally{
            number1.delete(0, number1.length());
            number1.append(result);
            textField.setText(number1.toString());
        }

    }

    private class buttonListener implements ActionListener{
        private String equation = "";
        private StringBuilder number1 = new StringBuilder();
        private StringBuilder number2 = new StringBuilder();
        private boolean isLastResult = false;

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            String text = button.getText();
            if(text.equals("+") || text.equals("-") || text.equals("*") || text.equals("/")){
                if(number1.length()>0){
                    if(isLastResult){
                        number2.delete(0, number2.length());
                    }
                    else if(number2.length()>0 && equation.length()>0){
                        add(number1, equation, number2);
                        number2.delete(0, number2.length());
                    }
                    equation = text;
                    isLastResult = false;
                }
            }
            else if(text.equals("C")){
                reset();
                textField.setText("");
            }
            else if(text.equals("=")){
                add(number1, equation, number2);
                isLastResult = true;
            }
            else{
                if(isLastResult){
                    reset();
                    number1.append(text);
                    textField.setText(number1.toString());
                }
                else if(equation.length() == 0){
                    if(number1.toString().contains(".") && text.equals(".")){ }
                    else{
                        number1.append(text);
                        textField.setText(number1.toString());
                    }
                }
                else{
                    if(number2.toString().contains(".") && text.equals(".")){ }
                    else{
                        number2.append(text);
                        textField.setText(number2.toString());
                    }
                }
                isLastResult = false;
            }
        }
        private void reset(){
            number1.delete(0, number1.length());
            number2.delete(0, number2.length());
            equation = "";
        }
    }
}
