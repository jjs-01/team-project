package com.studyarc.view;

import com.studyarc.entity.Reflection;
import com.studyarc.entity.StudyPlan;

import javax.swing.*;
import java.awt.*;

public class ShowReflectionView extends JDialog {
    public ShowReflectionView(Window owner, StudyPlan plan) {
        super(owner, "Reflections for " + plan.getTitle(), ModalityType.APPLICATION_MODAL);

        setSize(400, 300);
        setLocationRelativeTo(owner);

        JTextArea area = new JTextArea();
        area.setEditable(false);

        StringBuilder sb = new StringBuilder();
        int i = 1;

        for (Reflection r : plan.getReflections()) {
            sb.append(i++)
                    .append(". ")
                    .append(r.getContents())
                    .append("\n\n");
        }

        area.setText(sb.toString());
        add(new JScrollPane(area));
    }
}
