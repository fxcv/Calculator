import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calc{
    private JTextField textField;
    private String[] elements = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "+", "-", "*", "/", "C", "="};
    public void go(){
        setUpGui();
    }

    private void setUpGui(){
        JFrame frame = new JFrame("Calculator");
        ActionListener buttonListener = new buttonListener();
        Container c = frame.getContentPane();
        JPanel box = new JPanel();
        textField = new JTextField("");

        textField.setPreferredSize(new Dimension(200, 200));
        textField.setEditable(false);
        textField.setFont(new Font("Courier", Font.PLAIN, 50));
        for(int i = 0; i<elements.length; i++){
            JButton button = new JButton(elements[i]);
            button.addActionListener(buttonListener);
            button.setPreferredSize(new Dimension(150, 100));
            box.add(button);
        }
        frame.add(BorderLayout.NORTH, textField);
        frame.add(BorderLayout.CENTER, box);
        frame.setSize(600, 900);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void add(StringBuilder lastNumber, String equation, StringBuilder number){
        if(lastNumber.length()>0 && equation.length()==1){
            int a = Integer.parseInt(number.toString());
            int b = Integer.parseInt(lastNumber.toString());
            int result = 0;
            switch(equation){
                case "+": result = a + b; break;
                case "-": result = a - b; break;
                case "*": result = a * b; break;
                case "/": result = a / b;
            }
            number.delete(0, number.length());
            number.append(result);
            textField.setText(number.toString());
        }
    }

    private class buttonListener implements ActionListener{
        private String equation = "";
        private StringBuilder number = new StringBuilder();
        private StringBuilder lastNumber = new StringBuilder();
        private boolean isLastResult = false;
        private boolean isLastNumber = false;
        private boolean isLastEquation = false;

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            String text = button.getText();
            if(text.equals("+") || text.equals("-") || text.equals("*") || text.equals("/")){
                if(isLastResult){
                    lastNumber.delete(0, lastNumber.length());
                }
                if(equation.length()==1 && lastNumber.length()>0){
                    add(lastNumber, equation, number);
                    lastNumber.delete(0, lastNumber.length());
                }
                equation = text;
                isLastNumber = false;
                isLastEquation = true;
                isLastResult = false;
            }
            else if(text.equals("C")){

            }
            else if(text.equals("=")){
                add(lastNumber, equation, number);
                isLastNumber = false;
                isLastEquation = false;
                isLastResult = true;
            }
            else{
                if(isLastResult){
                    lastNumber.delete(0, lastNumber.length());
                    number.delete(0, number.length());
                    equation = "";
                }
                else if(number.length()>0 && !isLastNumber){
                    lastNumber.delete(0, lastNumber.length());
                    lastNumber.append(number.toString());
                    number.delete(0, number.length());
                }
                number.append(text);
                textField.setText(number.toString());
                isLastNumber = true;
                isLastEquation = false;
                isLastResult = false;
            }
        }
    }
}
