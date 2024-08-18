package io.github.brov3r.imgui;

import imgui.ImGui;
import imgui.ImVec2;

/**
 * Represents a generic widget in the ImGui framework.
 * This is an abstract class that provides basic functionalities such as visibility control,
 * hover detection, and management of the widget's lifecycle on the screen.
 * Subclasses must implement the {@link #render()} method to define the widget's specific rendering behavior.
 */
public abstract class ImGuiWidget {
    /**
     * Indicates whether the widget is visible on the screen.
     * If {@code true}, the widget is visible; otherwise, it is hidden.
     */
    protected boolean visible = true;

    /**
     * Indicates whether the widget is currently being hovered over by the mouse cursor.
     * This is updated based on the current mouse position relative to the widget's position and size.
     */
    protected boolean hover = false;

    /**
     * Checks if the widget is currently being hovered over by the mouse cursor.
     *
     * @return {@code true} if the widget is being hovered over, {@code false} otherwise.
     */
    public boolean isHover() {
        return hover;
    }

    /**
     * Checks if the widget is currently visible on the screen.
     *
     * @return {@code true} if the widget is visible, {@code false} if it is hidden.
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Sets the visibility of the widget.
     *
     * @param visible {@code true} to make the widget visible, {@code false} to hide it.
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * Adds the widget to the screen.
     * This method registers the widget with the {@link ImGuiManager}, allowing it to be rendered.
     * This method is final and cannot be overridden.
     */
    public void addToScreen() {
        ImGuiManager.addWidget(this);
    }

    /**
     * Removes the widget from the screen.
     * This method unregisters the widget from the {@link ImGuiManager}, preventing it from being rendered.
     * This method is final and cannot be overridden.
     */
    public void removeFromScreen() {
        ImGuiManager.removeWidget(this);
    }

    /**
     * Updates the widget's hover state based on the current mouse position.
     * This method checks if the mouse cursor is within the bounds of the widget
     * and updates the {@link #hover} field accordingly.
     * This method is final and cannot be overridden.
     */
    public void captureMouseFocus() {
        ImVec2 windowSize = ImGui.getWindowSize();
        ImVec2 windowPos = ImGui.getWindowPos();

        ImVec2 mousePos = ImGui.getMousePos();

        hover = mousePos.x >= windowPos.x && mousePos.y >= windowPos.y &&
                mousePos.x <= windowPos.x + windowSize.x && mousePos.y <= windowPos.y + windowSize.y;
    }

    /**
     * Updates the widget's state.
     * This method can be overridden by subclasses to implement custom update logic.
     * It is called before rendering each frame.
     */
    public void update() {
    }

    /**
     * Renders the widget on the screen.
     * This is an abstract method that must be implemented by subclasses to define
     * the specific rendering logic of the widget.
     */
    public abstract void render();
}