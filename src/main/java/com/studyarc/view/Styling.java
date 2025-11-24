package com.studyarc.view;

import java.awt.*;

public class Styling {

    private static final Font mainFont = new Font("Roboto Slab", Font.PLAIN, 26);
    private static final Font subFont = new Font("Roboto", Font.PLAIN, 20);
    private static final Font bodyFont = new Font("Lato", Font.PLAIN, 14);

    public static Font  getMainFont() {
        return mainFont;
    }
    public static Font  getSubFont() {
        return subFont;
    }
    public static Font  getBodyFont() {
        return bodyFont;
    }
}
