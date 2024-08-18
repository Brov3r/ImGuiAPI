package io.github.brov3r.imgui.events;

import com.avrix.events.Event;
import imgui.ImGuiIO;

/**
 * Represents an event that is triggered when ImGui is initialized.
 */
public abstract class OnImGuiInitializeEvent extends Event {
    /**
     * Getting the event name
     *
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() {
        return "OnImGuiInitialize";
    }

    /**
     * Handles the event using the provided {@link ImGuiIO} object.
     *
     * @param imGuiIO the {@link ImGuiIO} object that provides input/output
     *                controls and configuration settings for ImGui.
     */
    public abstract void handleEvent(ImGuiIO imGuiIO);
}