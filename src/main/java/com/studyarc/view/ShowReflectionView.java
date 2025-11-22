package com.studyarc.view;

import com.studyarc.entity.Reflection;
import com.studyarc.entity.StudyPlan;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ShowReflectionView extends JDialog {
    private final JTextArea reflectionArea;

    public ShowReflectionView(Window owner) {
        super(owner, "All Reflections", ModalityType.MODELESS);

        reflectionArea = new JTextArea();
        reflectionArea.setEditable(false);
        reflectionArea.setLineWrap(true);
        reflectionArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(reflectionArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(scrollPane);

        setSize(350, 250);
        setLocationRelativeTo(owner);
    }

    public void refresh(List<Reflection> reflections) {
        StringBuilder sb = new StringBuilder();

        int i = 1;
        for (Reflection r : reflections) {
            sb.append(i++).append(". ")
                    .append(r.getContents())
                    .append("\n\n");
        }

        reflectionArea.setText(sb.toString());
    }
}
