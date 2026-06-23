import java.awt.*;
import java.awt.event.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;

public class DigitalClockWithAlarm extends JFrame implements ActionListener {

    private JLabel clockLabel;
    private JTextField alarmField;
    private JButton setAlarmButton;
    private JButton clearAlarmButton;

    private String alarmTime = "";
    private boolean alarmSet = false;

    public DigitalClockWithAlarm() {

        setTitle("Digital Clock with Alarm");
        setSize(500, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        clockLabel = new JLabel();
        clockLabel.setFont(new Font("Arial", Font.BOLD, 40));
        add(clockLabel);

        add(new JLabel("Set Alarm (HH:mm:ss):"));

        alarmField = new JTextField(10);
        add(alarmField);

        setAlarmButton = new JButton("Set Alarm");
        setAlarmButton.addActionListener(this);
        add(setAlarmButton);

        clearAlarmButton = new JButton("Clear Alarm");
        clearAlarmButton.addActionListener(this);
        add(clearAlarmButton);

        startClock();

        setVisible(true);
    }

    private void startClock() {

        Timer timer = new Timer(1000, e -> {

            LocalTime currentTime = LocalTime.now();

            String time =
                    currentTime.format(
                            DateTimeFormatter.ofPattern("HH:mm:ss"));

            clockLabel.setText(time);

            if (alarmSet && time.equals(alarmTime)) {

                Toolkit.getDefaultToolkit().beep();

                JOptionPane.showMessageDialog(
                        this,
                        "⏰ Alarm Time Reached!",
                        "Alarm",
                        JOptionPane.INFORMATION_MESSAGE
                );

                alarmSet = false;
            }
        });

        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == setAlarmButton) {

            alarmTime = alarmField.getText().trim();

            if (alarmTime.matches("\\d{2}:\\d{2}:\\d{2}")) {

                alarmSet = true;

                JOptionPane.showMessageDialog(
                        this,
                        "Alarm set for " + alarmTime
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Enter time in HH:mm:ss format",
                        "Invalid Format",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }

        if (e.getSource() == clearAlarmButton) {

            alarmSet = false;
            alarmField.setText("");

            JOptionPane.showMessageDialog(
                    this,
                    "Alarm Cleared"
            );
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                DigitalClockWithAlarm::new
        );
    }
}
