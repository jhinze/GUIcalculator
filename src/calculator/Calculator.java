/*Justin Hinze
 *csc4380
 *9-4-15
 *Assignment1 - calculator
 */
package calculator;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class Calculator {

    private final JFrame frame = new JFrame("Calculator");
    private final StringBuilder display = new StringBuilder();
    private final JPanel panel = new JPanel(new GridLayout(0, 4));
    private final JPanel disp = new JPanel(new GridLayout(2, 1));
    private final JTextField prev = new JTextField();
    private final JTextField text = new JTextField();
    private final JButton one = new JButton("1");
    private final JButton two = new JButton("2");
    private final JButton three = new JButton("3");
    private final JButton four = new JButton("4");
    private final JButton five = new JButton("5");
    private final JButton six = new JButton("6");
    private final JButton seven = new JButton("7");
    private final JButton eight = new JButton("8");
    private final JButton nine = new JButton("9");
    private final JButton zero = new JButton("0");
    private final JButton plus = new JButton("+");
    private final JButton minus = new JButton("-");
    private final JButton multiply = new JButton("*");
    private final JButton divide = new JButton("/");
    private final JButton equals = new JButton("=");
    private final JButton clear = new JButton("clr");
    private final JButton closeParen = new JButton(")");
    private final JButton point = new JButton(".");
    private final JButton openParen = new JButton("(");
    private final JButton sqrt = new JButton("√");
    private final JButton power = new JButton("^");
    private final Container content = frame.getContentPane();
    private final InfixEvaluator evaluator = new InfixEvaluator(22);
    private final String prevEntry = "";

    public Calculator() {
        this.buttonListeners();
        this.keyListener();
        this.buildGUI();
    }

    //returns contents of calculator display
    public String getDisplayContents() {
        return display.toString();
    }

    //build and format buttons, text fields, and panels
    private void buildGUI() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 400);
        frame.setLocation(100, 100);
        content.setLayout(new BorderLayout());
        text.setPreferredSize(new Dimension(frame.getWidth(), 30));
        text.setFont(new Font("serif", Font.BOLD, 24));
        text.setHorizontalAlignment(SwingConstants.RIGHT);
        prev.setPreferredSize(new Dimension(frame.getWidth(), 30));
        prev.setFont(new Font("serif", Font.BOLD, 24));
        prev.setHorizontalAlignment(SwingConstants.RIGHT);
        text.setText("0");
        prev.setText("");
        prev.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        text.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        prev.setEditable(false);
        frame.setResizable(false);
        text.setEditable(false);
        one.setFont(new Font("serif", Font.BOLD, 20));
        two.setFont(new Font("serif", Font.BOLD, 20));
        three.setFont(new Font("serif", Font.BOLD, 20));
        four.setFont(new Font("serif", Font.BOLD, 20));
        five.setFont(new Font("serif", Font.BOLD, 20));
        six.setFont(new Font("serif", Font.BOLD, 20));
        seven.setFont(new Font("serif", Font.BOLD, 20));
        eight.setFont(new Font("serif", Font.BOLD, 20));
        nine.setFont(new Font("serif", Font.BOLD, 20));
        zero.setFont(new Font("serif", Font.BOLD, 20));
        plus.setFont(new Font("serif", Font.BOLD, 20));
        minus.setFont(new Font("serif", Font.BOLD, 20));
        multiply.setFont(new Font("serif", Font.BOLD, 20));
        divide.setFont(new Font("serif", Font.BOLD, 20));
        equals.setFont(new Font("serif", Font.BOLD, 20));
        equals.setPreferredSize(new Dimension(frame.getWidth(), 30));
        clear.setFont(new Font("serif", Font.BOLD, 20));
        closeParen.setFont(new Font("serif", Font.BOLD, 20));
        point.setFont(new Font("serif", Font.BOLD, 20));
        openParen.setFont(new Font("serif", Font.BOLD, 20));
        sqrt.setFont(new Font("serif", Font.BOLD, 20));
        power.setFont(new Font("serif", Font.BOLD, 20));
        disp.add(prev);
        disp.add(text);
        frame.add(disp, BorderLayout.PAGE_START);
        panel.add(openParen);
        panel.add(closeParen);
        panel.add(sqrt);
        panel.add(power);
        panel.add(seven);
        panel.add(eight);
        panel.add(nine);
        panel.add(minus);
        panel.add(four);
        panel.add(five);
        panel.add(six);
        panel.add(plus);
        panel.add(one);
        panel.add(two);
        panel.add(three);
        panel.add(multiply);
        panel.add(clear);
        panel.add(zero);
        panel.add(point);
        panel.add(divide);
        frame.add(panel, BorderLayout.CENTER);
        frame.add(equals, BorderLayout.PAGE_END);
        frame.pack();
        panel.setVisible(true);
        frame.setVisible(true);
    }

    //create button listeners for buttons
    private void buttonListeners() {
        one.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                input("1");
            }
        });
        two.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                input("2");
            }
        });
        three.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                input("3");
            }
        });
        four.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                input("4");
            }
        });
        five.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                input("5");
            }
        });
        six.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                input("6");
            }
        });
        seven.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                input("7");
            }
        });
        eight.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                input("8");
            }
        });
        nine.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                input("9");
            }
        });
        zero.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                input("0");
            }
        });
        plus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (display.length() == 0) {
                    return;
                } else {
                    input("+");
                }
            }
        });
        minus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                input("-");
            }
        });
        multiply.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (display.length() == 0) {
                    return;
                } else {
                    input("*");
                }
            }
        });
        divide.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (display.length() == 0) {
                    return;
                } else {
                    input("/");
                }
            }
        });
        //TODO...
        equals.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                prev.setText(display.toString() + "=");
                display.replace(0, display.length(), evaluator.evaluate(display.toString()));
                text.setText(display.toString());
            }
        });
        clear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                prev.setText("");
                display.replace(0, display.length(), "0");
                text.setText("0");
            }
        });
        closeParen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                input(")");
            }
        });
        point.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                input(".");
            }
        });
        openParen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                input("(");
            }
        });
        power.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (display.length() == 0) {
                    return;
                } else {
                    input("^");
                }
            }
        });
        sqrt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                input("√");
            }
        });
    }

    //create key listener so buttons can be controlled from keyboard, this is easier than
    //creating a listener for each button
    private void keyListener() {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                if (e.getID() == KeyEvent.KEY_TYPED) {
                    if ((int) e.getKeyChar() == 127) {
                        clear.doClick();
                        return true;
                    }
                    switch (e.getKeyChar()) {
                        case 's':
                            sqrt.doClick();
                            break;
                        case '1':
                            one.doClick();
                            break;
                        case '2':
                            two.doClick();
                            break;
                        case '3':
                            three.doClick();
                            break;
                        case '4':
                            four.doClick();
                            break;
                        case '5':
                            five.doClick();
                            break;
                        case '6':
                            six.doClick();
                            break;
                        case '7':
                            seven.doClick();
                            break;
                        case '8':
                            eight.doClick();
                            break;
                        case '9':
                            nine.doClick();
                            break;
                        case '0':
                            zero.doClick();
                            break;
                        case '.':
                            point.doClick();
                            break;
                        case '(':
                            openParen.doClick();
                            break;
                        case ')':
                            closeParen.doClick();
                            break;
                        case '^':
                            power.doClick();
                            break;
                        case '=':
                            equals.doClick();
                            break;
                        case '\n':
                            equals.doClick();
                            break;
                        case '\b':
                            backspace();
                            break;
                        case '+':
                            plus.doClick();
                            break;
                        case '-':
                            minus.doClick();
                            break;
                        case '/':
                            divide.doClick();
                            break;
                        case '*':
                            multiply.doClick();
                            break;
                        default:
                            break;
                    }
                }
                return true;
            }
        });
    }

    //action when an entry button is pushed. screen is cleared if zero or
    //error from previous calculation. otherwise the entry is amended to the
    //value in display
    private void input(String s) {
        if (display.toString().equals("0")
                || display.toString().equals("Lexical error")
                || display.toString().equals("Computational error")
                || display.toString().equals("∞")) {
            display.replace(0, display.length(), "");
            text.setText(display.toString());
        }
        display.append(s);
        text.setText(display.toString());
    }

    //hidden action. there is not a button for this but it can be performed
    //by pressing the keyboard backspace key. removes the last character in display
    private void backspace() {
        if (display.length() > 0) {
            display.deleteCharAt(display.length() - 1);
        }
        text.setText(display.toString());
    }

    public static void main(String[] args) {
        Calculator calc = new Calculator();

    }

}
