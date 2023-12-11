import Components.Memory;
import Components.RegisterFile;

import javax.swing.*;
import javax.swing.GroupLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.awt.Dimension;
import java.awt.Toolkit;

/*
 * Created by JFormDesigner on Sun Dec 10 21:53:52 EET 2023
 */

/**
 * @author omar_
 */
public class GUI extends JPanel {

    Processor micronegusProcessor;

    boolean started = false;
    public GUI() {
        initComponents();
    }

    public static void main(String[] args) {
        GUI gui = new GUI();
        JFrame frame = new JFrame("Micronegus Processor");
        frame.setContentPane(gui);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - frame.getWidth()) / 2;
        int y = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(x, y);
        frame.setVisible(true);

        gui.ziGetInputCykaBlyat();
    }



    private void ziGetInputCykaBlyat() {
        Scanner scanner = new Scanner(System.in);

        JOptionPane.showMessageDialog(null, "Enter the latencies of each instruction (in cycles) ...", "Welcome!", JOptionPane.INFORMATION_MESSAGE);

        System.out.print("Memory Latency: ");
        //JOptionPane.showMessageDialog(null, "Memory Latency: ", "Memory Latency", JOptionPane.QUESTION_MESSAGE);
        int MemLatency = Integer.parseInt(JOptionPane.showInputDialog(null, "Memory Latency: ", "Memory Latency", JOptionPane.QUESTION_MESSAGE));


        System.out.print("ADD.D Latency: ");
        //JOptionPane.showMessageDialog(null, "ADD.D Latency: ", "ADD.D Latency", JOptionPane.QUESTION_MESSAGE);
        int Add_DLatency = Integer.parseInt(JOptionPane.showInputDialog(null, "ADD.D Latency: ", "ADD.D Latency", JOptionPane.QUESTION_MESSAGE));


        //JOptionPane.showMessageDialog(null, "SUB.D Latency: ", "SUB.D Latency", JOptionPane.QUESTION_MESSAGE);
        int Sub_DLatency = Integer.parseInt(JOptionPane.showInputDialog(null, "SUB.D Latency: ", "SUB.D Latency", JOptionPane.QUESTION_MESSAGE));

        //JOptionPane.showMessageDialog(null, "MUL.D Latency: ", "MUL.D Latency", JOptionPane.QUESTION_MESSAGE);
        int Mul_DLatency = Integer.parseInt(JOptionPane.showInputDialog(null, "MUL.D Latency: ", "MUL.D Latency", JOptionPane.QUESTION_MESSAGE));

       // JOptionPane.showMessageDialog(null, "DIV.D Latency: ", "DIV.D Latency", JOptionPane.QUESTION_MESSAGE);
        int Div_DLatency = Integer.parseInt(JOptionPane.showInputDialog(null, "DIV.D Latency: ", "DIV.D Latency", JOptionPane.QUESTION_MESSAGE));


        //JOptionPane.showMessageDialog(null, "DADD Latency: ", "DADD Latency", JOptionPane.QUESTION_MESSAGE);
        int DAddLatency = Integer.parseInt(JOptionPane.showInputDialog(null, "DADD Latency: ", "DADD Latency", JOptionPane.QUESTION_MESSAGE));

        System.out.print("SUBI Latency: ");
        //JOptionPane.showMessageDialog(null, "SUBI Latency: ", "SUBI Latency", JOptionPane.QUESTION_MESSAGE);
        int SubILatency = Integer.parseInt(JOptionPane.showInputDialog(null, "SUBI Latency: ", "SUBI Latency", JOptionPane.QUESTION_MESSAGE));


        System.out.println("Latencies updated successfully!");
        JOptionPane.showMessageDialog(null, "Latencies updated successfully!", "Latencies updated successfully!", JOptionPane.INFORMATION_MESSAGE);

        System.out.println("Enter the size of each component ...");
        JOptionPane.showMessageDialog(null, "Enter the size of each component ...", "Enter the size of each component ...", JOptionPane.INFORMATION_MESSAGE);

        System.out.print("Add/Sub Reservation Station: ");
        //JOptionPane.showMessageDialog(null, "Add/Sub Reservation Station: ", "Add/Sub Reservation Station", JOptionPane.QUESTION_MESSAGE);
        int addSubReservationStationSize = Integer.parseInt(JOptionPane.showInputDialog(null, "Add/Sub Reservation Station: ", "Add/Sub Reservation Station", JOptionPane.QUESTION_MESSAGE));

        System.out.print("Mul/Div Reservation Station: ");
        //JOptionPane.showMessageDialog(null, "Mul/Div Reservation Station: ", "Mul/Div Reservation Station", JOptionPane.QUESTION_MESSAGE);
        int mulDivReservationStationSize = Integer.parseInt(JOptionPane.showInputDialog(null, "Mul/Div Reservation Station: ", "Mul/Div Reservation Station", JOptionPane.QUESTION_MESSAGE));

        System.out.print("Load Buffer: ");
        //JOptionPane.showMessageDialog(null, "Load Buffer: ", "Load Buffer", JOptionPane.QUESTION_MESSAGE);
        int loadBuffersSize = Integer.parseInt(JOptionPane.showInputDialog(null, "Load Buffer: ", "Load Buffer", JOptionPane.QUESTION_MESSAGE));

        System.out.print("Store Buffer: ");
        //JOptionPane.showMessageDialog(null, "Store Buffer: ", "Store Buffer", JOptionPane.QUESTION_MESSAGE);
        int storeBuffersSize = Integer.parseInt(JOptionPane.showInputDialog(null, "Store Buffer: ", "Store Buffer", JOptionPane.QUESTION_MESSAGE));

        System.out.print("Memory Size: ");
        //JOptionPane.showMessageDialog(null, "Memory Size: ", "Memory Size", JOptionPane.QUESTION_MESSAGE);
        int memorySize = Integer.parseInt(JOptionPane.showInputDialog(null, "Memory Size: ", "Memory Size", JOptionPane.QUESTION_MESSAGE));

        Memory memory = new Memory(memorySize);

        System.out.println("Sizes updated successfully!");
        JOptionPane.showMessageDialog(null, "Sizes updated successfully!", "Sizes updated successfully!", JOptionPane.INFORMATION_MESSAGE);

        boolean preLoadMemory = false;
        boolean preLoadRegisterFile = false;

        System.out.println("Do you want to pre-load the memory? (Y/N)");
        String choice = JOptionPane.showInputDialog(null, "Do you want to pre-load the memory? (Y/N)", "Do you want to pre-load the memory? (Y/N)", JOptionPane.QUESTION_MESSAGE);

        if (choice.equalsIgnoreCase("Y")) {
            preLoadMemory = true;
            int numberOfMemoryLocations = Integer.parseInt(JOptionPane.showInputDialog(null, "number of memory locations to pre-load: ", "number of memory locations to pre-load", JOptionPane.QUESTION_MESSAGE));

            for (int i = 0; i < numberOfMemoryLocations; i++) {
                System.out.println("Enter the value of memory location: ");
                int index = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter the value of memory location: ", "Enter the value of memory location", JOptionPane.QUESTION_MESSAGE));

                if (index >= memorySize) {
                    JOptionPane.showMessageDialog(null, "Invalid memory location!", "Invalid memory location!", JOptionPane.ERROR_MESSAGE);
                    i--;
                    continue;
                }

                double value = Double.parseDouble(JOptionPane.showInputDialog(null, "Enter the value: ", "Enter the value", JOptionPane.QUESTION_MESSAGE));
                memory.setMemoryItem(index, value);
            }

        }

        choice = JOptionPane.showInputDialog(null, "Do you want to pre-load the register file? (Y/N)", "Do you want to pre-load the register file? (Y/N)", JOptionPane.QUESTION_MESSAGE);

        RegisterFile registerFile = new RegisterFile();

        if (choice.equalsIgnoreCase("Y")) {
            preLoadRegisterFile = true;
            System.out.println("number of registers to pre-load: ");
            int numberOfregisters = Integer.parseInt(JOptionPane.showInputDialog(null, "number of registers to pre-load: ", "number of registers to pre-load", JOptionPane.QUESTION_MESSAGE));


            for (int i = 0; i < numberOfregisters; i++) {
                System.out.println("Enter the register name (FNN/RNN | N = {0...31}): ");
                String regName = JOptionPane.showInputDialog(null, "Enter the register name (FNN/RNN | N = {0...31}): ", "Enter the register name (FNN/RNN | N = {0...31})", JOptionPane.QUESTION_MESSAGE);

                if (regName.charAt(0) == 'R' || regName.charAt(0) == 'r') {
                    int regNumber = Integer.parseInt(regName.substring(1));
                    if (regNumber < 0 || regNumber > 31) {
                        System.out.println("Invalid register number!");
                        JOptionPane.showMessageDialog(null, "Invalid register number!", "Invalid register number!", JOptionPane.ERROR_MESSAGE);
                        i--;
                        continue;
                    } else {
                        System.out.println("Enter the value: ");
                        double value = Double.parseDouble(JOptionPane.showInputDialog(null, "Enter the value: ", "Enter the value", JOptionPane.QUESTION_MESSAGE));
                        registerFile.setRegister("R"+regNumber, value);
                    }

                } else if (regName.charAt(0) == 'F' || regName.charAt(0) == 'f') {
                    int regNumber = Integer.parseInt(regName.substring(1));
                    if (regNumber < 0 || regNumber > 31) {
                        System.out.println("Invalid register number!");
                        JOptionPane.showMessageDialog(null, "Invalid register number!", "Invalid register number!", JOptionPane.ERROR_MESSAGE);
                        i--;
                        continue;
                    } else {
                        System.out.println("Enter the value: ");
                        double value = Double.parseDouble(JOptionPane.showInputDialog(null, "Enter the value: ", "Enter the value", JOptionPane.QUESTION_MESSAGE));
                        registerFile.setRegister("F"+regNumber, value);
                    }

                } else {
                    System.out.println("Invalid register name!");
                    JOptionPane.showMessageDialog(null, "Invalid register name!", "Invalid register name!", JOptionPane.ERROR_MESSAGE);
                    i--;
                    continue;
                }

            }

        }


        System.out.println("Processor setup complete!");

        if (preLoadMemory && preLoadRegisterFile) {
            micronegusProcessor = new Processor(addSubReservationStationSize, mulDivReservationStationSize, loadBuffersSize, storeBuffersSize, memory, registerFile, Mul_DLatency, Div_DLatency, Add_DLatency, Sub_DLatency, DAddLatency, SubILatency, MemLatency);
        } else if (preLoadMemory) {
            micronegusProcessor = new Processor(addSubReservationStationSize, mulDivReservationStationSize, loadBuffersSize, storeBuffersSize, memory, Mul_DLatency, Div_DLatency, Add_DLatency, Sub_DLatency, DAddLatency, SubILatency, MemLatency);
        } else if (preLoadRegisterFile) {
            micronegusProcessor = new Processor(addSubReservationStationSize, mulDivReservationStationSize, loadBuffersSize, storeBuffersSize, memorySize, registerFile, Mul_DLatency, Div_DLatency, Add_DLatency, Sub_DLatency, DAddLatency, SubILatency, MemLatency);
        } else {
            micronegusProcessor = new Processor(addSubReservationStationSize, mulDivReservationStationSize, loadBuffersSize, storeBuffersSize, memorySize, Mul_DLatency, Div_DLatency, Add_DLatency, Sub_DLatency, DAddLatency, SubILatency, MemLatency);
        }

        JOptionPane.showMessageDialog(null, "Processor setup complete!", "Processor setup complete!", JOptionPane.INFORMATION_MESSAGE);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - Omar Nour
        label1 = new JLabel();
        scrollPane1 = new JScrollPane();
        codeTextBox = new JTextArea();
        runButton = new JButton();
        stepButton = new JButton();
        label2 = new JLabel();
        label3 = new JLabel();
        label4 = new JLabel();
        label5 = new JLabel();
        label6 = new JLabel();
        label7 = new JLabel();
        label8 = new JLabel();
        scrollPane2 = new JScrollPane();
        regFileTextBox = new JTextArea();
        scrollPane3 = new JScrollPane();
        memTextBox = new JTextArea();
        scrollPane4 = new JScrollPane();
        addSubTextbox = new JTextArea();
        scrollPane5 = new JScrollPane();
        mulDivTextbox = new JTextArea();
        scrollPane6 = new JScrollPane();
        loadBufferTextbox = new JTextArea();
        scrollPane7 = new JScrollPane();
        storeBufferTextbox = new JTextArea();
        scrollPane8 = new JScrollPane();
        logTextBox = new JTextArea();

        //======== this ========
        setBorder(new javax.swing.border.CompoundBorder(new javax.swing.border.TitledBorder(new javax.swing.border
        .EmptyBorder(0,0,0,0), "JFor\u006dDesi\u0067ner \u0045valu\u0061tion",javax.swing.border.TitledBorder.CENTER,javax
        .swing.border.TitledBorder.BOTTOM,new java.awt.Font("Dia\u006cog",java.awt.Font.BOLD,
        12),java.awt.Color.red), getBorder())); addPropertyChangeListener(new java.beans
        .PropertyChangeListener(){@Override public void propertyChange(java.beans.PropertyChangeEvent e){if("bord\u0065r".equals(e.
        getPropertyName()))throw new RuntimeException();}});

        //---- label1 ----
        label1.setText("Code");

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(codeTextBox);
        }

        //---- runButton ----
        runButton.setText("Run");

        // runClick
        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // write to code.txt
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter("code.txt"));
                    writer.write(codeTextBox.getText());
                    writer.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                try {
                    micronegusProcessor.codeParser();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                // run
                while (!(micronegusProcessor.instructionQueue.getCurrentIndex() >= micronegusProcessor.instructionQueue.size() && micronegusProcessor.addSubReservationStation.isEmpty() && micronegusProcessor.mulDivReservationStation.isEmpty() && micronegusProcessor.loadStoreBuffers.isEmpty())) {
                    micronegusProcessor.issue();
                    micronegusProcessor.execute();
                    micronegusProcessor.writeBack();
                    micronegusProcessor.cycleCounter++;
                }



                regFileTextBox.setText(micronegusProcessor.registerFile.toString());
                memTextBox.setText(micronegusProcessor.memory.toString());
                addSubTextbox.setText(micronegusProcessor.addSubReservationStation.forGui());
                mulDivTextbox.setText(micronegusProcessor.mulDivReservationStation.forGUI());
                loadBufferTextbox.setText(micronegusProcessor.loadStoreBuffers.loadBufferForGui());
                storeBufferTextbox.setText(micronegusProcessor.loadStoreBuffers.storeBufferForGui());
                logTextBox.setText(micronegusProcessor.log.toString());

                JOptionPane.showMessageDialog(null, "Program complete! (" + (micronegusProcessor.cycleCounter - 1) + "cycles)" +"\n" + micronegusProcessor.instructionQueue.forGUI(), "Program complete!", JOptionPane.INFORMATION_MESSAGE);

            }
        });

        stepButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // write to code.txt
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter("code.txt"));
                    writer.write(codeTextBox.getText());
                    writer.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                if (!started) {
                    try {
                        micronegusProcessor.codeParser();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    started = true;
                }

                // show message
                JOptionPane.showMessageDialog(null, "Cycle " + micronegusProcessor.cycleCounter + " complete!\n" + micronegusProcessor.instructionQueue.forGUI(), "Cycle " + micronegusProcessor.cycleCounter + " complete!", JOptionPane.INFORMATION_MESSAGE);


                micronegusProcessor.issue();
                micronegusProcessor.execute();
                micronegusProcessor.writeBack();
                micronegusProcessor.cycleCounter++;

                micronegusProcessor.log += "==================================\n";



                regFileTextBox.setText(micronegusProcessor.registerFile.toString());
                memTextBox.setText(micronegusProcessor.memory.toString());
                addSubTextbox.setText(micronegusProcessor.addSubReservationStation.forGui());
                mulDivTextbox.setText(micronegusProcessor.mulDivReservationStation.forGUI());
                loadBufferTextbox.setText(micronegusProcessor.loadStoreBuffers.loadBufferForGui());
                storeBufferTextbox.setText(micronegusProcessor.loadStoreBuffers.storeBufferForGui());
                logTextBox.setText(micronegusProcessor.log.toString());

                if (micronegusProcessor.instructionQueue.getCurrentIndex() >= micronegusProcessor.instructionQueue.size() && micronegusProcessor.addSubReservationStation.isEmpty() && micronegusProcessor.mulDivReservationStation.isEmpty() && micronegusProcessor.loadStoreBuffers.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Program finished!", "Program finished!", JOptionPane.INFORMATION_MESSAGE);
                    stepButton.setEnabled(false);
                }

            }
        });

        //---- stepButton ----
        stepButton.setText("Step");

        //---- label2 ----
        label2.setText("Register File");

        //---- label3 ----
        label3.setText("Memory");

        //---- label4 ----
        label4.setText("AddSub Reservation Stations");

        //---- label5 ----
        label5.setText("MulDiv Reservation Stations");

        //---- label6 ----
        label6.setText("Load Buffer");

        //---- label7 ----
        label7.setText("Store Buffer");

        //---- label8 ----
        label8.setText("Log");

        //======== scrollPane2 ========
        {
            scrollPane2.setViewportView(regFileTextBox);
        }

        //======== scrollPane3 ========
        {
            scrollPane3.setViewportView(memTextBox);
        }

        //======== scrollPane4 ========
        {
            scrollPane4.setViewportView(addSubTextbox);
        }

        //======== scrollPane5 ========
        {
            scrollPane5.setViewportView(mulDivTextbox);
        }

        //======== scrollPane6 ========
        {
            scrollPane6.setViewportView(loadBufferTextbox);
        }

        //======== scrollPane7 ========
        {
            scrollPane7.setViewportView(storeBufferTextbox);
        }

        //======== scrollPane8 ========
        {
            scrollPane8.setViewportView(logTextBox);
        }

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addGap(18, 18, 18)
                    .addGroup(layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                            .addGap(120, 120, 120)
                            .addComponent(runButton)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(stepButton)
                            .addGap(0, 0, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(label1)
                            .addGap(259, 259, 259)
                            .addComponent(label2)
                            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 280, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup()
                                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup()
                                        .addComponent(label6)
                                        .addComponent(scrollPane6, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createParallelGroup()
                                        .addGroup(layout.createSequentialGroup()
                                            .addGap(18, 18, 18)
                                            .addComponent(label7)
                                            .addGap(152, 152, 152))
                                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(scrollPane7, GroupLayout.PREFERRED_SIZE, 214, GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)))
                                    .addGroup(layout.createParallelGroup()
                                        .addComponent(label8)
                                        .addComponent(scrollPane8)))
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(404, 404, 404)
                                    .addComponent(label3))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(scrollPane2, GroupLayout.PREFERRED_SIZE, 386, GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(scrollPane3, GroupLayout.PREFERRED_SIZE, 396, GroupLayout.PREFERRED_SIZE))
                                .addComponent(label4)
                                .addComponent(scrollPane4, GroupLayout.PREFERRED_SIZE, 800, GroupLayout.PREFERRED_SIZE)
                                .addComponent(label5)
                                .addComponent(scrollPane5, GroupLayout.PREFERRED_SIZE, 800, GroupLayout.PREFERRED_SIZE))
                            .addGap(51, 51, 51))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(label1)
                        .addComponent(label2)
                        .addComponent(label3))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup()
                                .addComponent(scrollPane2, GroupLayout.PREFERRED_SIZE, 209, GroupLayout.PREFERRED_SIZE)
                                .addComponent(scrollPane3, GroupLayout.PREFERRED_SIZE, 209, GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(label4)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(scrollPane4, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(label5)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(scrollPane5, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label6)
                                .addComponent(label7)
                                .addComponent(label8))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                .addComponent(scrollPane6, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                                .addComponent(scrollPane7, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                                .addComponent(scrollPane8, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)))
                        .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 691, Short.MAX_VALUE))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(stepButton)
                        .addComponent(runButton))
                    .addContainerGap())
        );
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - Omar Nour
    private JLabel label1;
    private JScrollPane scrollPane1;
    private JTextArea codeTextBox;
    private JButton runButton;
    private JButton stepButton;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private JLabel label5;
    private JLabel label6;
    private JLabel label7;
    private JLabel label8;
    private JScrollPane scrollPane2;
    private JTextArea regFileTextBox;
    private JScrollPane scrollPane3;
    private JTextArea memTextBox;
    private JScrollPane scrollPane4;
    private JTextArea addSubTextbox;
    private JScrollPane scrollPane5;
    private JTextArea mulDivTextbox;
    private JScrollPane scrollPane6;
    private JTextArea loadBufferTextbox;
    private JScrollPane scrollPane7;
    private JTextArea storeBufferTextbox;
    private JScrollPane scrollPane8;
    private JTextArea logTextBox;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
