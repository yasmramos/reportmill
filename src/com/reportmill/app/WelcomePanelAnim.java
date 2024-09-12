package com.reportmill.app;
import com.reportmill.base.ReportMill;
import snap.util.SnapUtils;
import snap.view.*;

/**
 * Manages WelcomePanelAnim view.
 */
public class WelcomePanelAnim extends ViewOwner {

    // The main view
    private ChildView _mainView;

    /**
     * Constructor.
     */
    public WelcomePanelAnim()
    {
        super();
    }

    /**
     * Initialize UI.
     */
    @Override
    protected void initUI()
    {
        // Get MainView and Configure to call setMinimized() on click
        _mainView = getUI(ChildView.class);
        _mainView.addEventHandler(e -> setMinimized(!isMinimized()), View.MouseRelease);

        // Configure MainTitleText
        TextArea mainTitleText = getView("MainTitleText", TextArea.class);
        mainTitleText.setDefaultTextStyleString("Font: Futura Condensed Medium 18; Border: #00;");
        mainTitleText.addChars("ReportMill 16");

        // Configure TagLineText, TagLineText2
        TextArea tagLineTextArea = getView("TagLineText", TextArea.class);
        tagLineTextArea.setDefaultTextStyleString("Font: Arial Bold 16");
        tagLineTextArea.addChars("The Premier Java Reporting System");
        TextArea tagLineTextArea2 = getView("TagLineText2", TextArea.class);
        tagLineTextArea2.setDefaultTextStyleString("Font: Arial Bold 16; Color: #FF");
        tagLineTextArea2.addChars("The Premier Java Reporting System");

        // Configure JVMText
        TextArea jvmText = getView("JVMText", TextArea.class);
        jvmText.setDefaultTextStyleString("Font: Arial Bold 10; Color: #FF");
        jvmText.addChars("JVM: " + (SnapUtils.isTeaVM ? "TeaVM" : System.getProperty("java.runtime.version")));

        // Configure BuildText
        TextArea buildText = getView("BuildText", TextArea.class);
        buildText.setDefaultTextStyleString("Font: Arial Bold 10; Color: #FF");
        buildText.addChars("Build: " + SnapUtils.getBuildInfo().trim());

        // Configure LicenseText
        TextArea licText = getView("LicenseText", TextArea.class);
        licText.setDefaultTextStyleString("Font: Arial Bold 10; Color: #FF");
        licText.setText(ReportMill.getLicense() == null ? "Unlicensed Copy" : "License: " + ReportMill.getLicense());
    }

    /**
     * Returns whether view is minimized.
     */
    public boolean isMinimized()
    {
        return getUI().getHeight() < 200;
    }

    /**
     * Sets whether view is minimized.
     */
    public void setMinimized(boolean aValue)
    {
        // Just return if already set
        if (aValue == isMinimized()) return;

        // Show/hide views below the minimize size
        _mainView.getChild(2).setVisible(!aValue);
        ColView topGraphicColView = (ColView) _mainView.getChild(1);
        for (int i = 2; i < topGraphicColView.getChildCount(); i++)
            topGraphicColView.getChild(i).setVisible(!aValue);

        // Handle Minimize: Size PrefHeight down
        if (aValue)
            _mainView.getAnimCleared(600).setPrefHeight(140);

            // Handle normal: Size PrefHeight up
        else {
            _mainView.setClipToBounds(true);
            _mainView.getAnimCleared(600).setPrefHeight(240);
        }

        // Start anim
        _mainView.playAnimDeep();
    }
}
